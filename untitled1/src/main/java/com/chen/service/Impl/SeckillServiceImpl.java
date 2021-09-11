package com.chen.service.Impl;

import com.chen.dao.SeckillMapper;
import com.chen.dao.SuccesskilledMapper;
import com.chen.dao.cache.RedisDao;
import com.chen.dto.Exposer;
import com.chen.dto.SeckillExecution;
import com.chen.enums.SeckillStaEnum;
import com.chen.exception.KillCloseException;
import com.chen.exception.RepeatKillException;
import com.chen.exception.SeckillException;
import com.chen.pojo.Seckill;
import com.chen.pojo.Successkilled;
import com.chen.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SuccesskilledMapper successkilledMapper;
    @Autowired
    private RedisDao redisDao;

    private final String slat="bdsjfbisb%%^6$$##$$#####";

    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
//        优化之前的方法
//        Seckill seckill = seckillMapper.queryById(seckillId);
//        if(seckill==null){
//            return new Exposer(false,seckillId);
//        }
        //优化点:缓存优化 一致性维护:超时维护
        Seckill seckill=redisDao.getSeckill(seckillId);
        if(seckill == null){
            //2.访问数据库
            seckill=seckillMapper.queryById(seckillId);
            if (seckill == null){
                return new Exposer(false,seckillId);
            }else {
                //3.放入redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime=seckill.getStarttime();
        Date endTime=seckill.getEndtime();
        //系统当前时间
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    @Transactional
    /**
     * 使用注解事务优点：
     * 1.统一开发风格
     * 2.事务操作时间尽可能短，不要穿插RPC/Http操作(时间久)，如果不可避免就剥离出来，构建一个更上层方法
     * 3.不是所有都需要加事务控制，例如一个插入操作或者查询/只读操作
     */
    //优化点:调整插入明细和调整库存顺序.插入操作有一些过滤重复秒杀,行级锁加在更新操作,加锁与解锁时间变短.
    public SeckillExecution executeSeckill(long seckillId, long userphone, String md5) throws SeckillException, RepeatKillException, KillCloseException {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("数据重写异常===自定义异常抛出");
        }
        //秒杀操作逻辑->减库存+插入秒杀记录
        try{
            int count=successkilledMapper.insertSuccesKilled(seckillId, userphone);
            if(count<=0){
                //重复秒杀
                throw new RepeatKillException("重复秒杀=====");
            }else {
                int update=seckillMapper.reduceNumber(seckillId,new Date());
                if(update<=0){
                    //减库存的更新操作失败
                    throw new KillCloseException("秒杀活动结束->更新操作失败");
                } else{
                    Successkilled successkilled = successkilledMapper.queryByIdWithSeckill(seckillId, userphone);
                    return new SeckillExecution(seckillId, SeckillStaEnum.SUCCESS,successkilled);
                }
            }
        }catch (KillCloseException e1){
            throw e1;
        }
        catch (RepeatKillException e2){
            throw e2;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("内部错误"+e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userphone, String md5) {
        Date killTime=new Date();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userphone);
        map.put("killTime",killTime);
        map.put("result",null);
        //执行存储过程
        try {
            seckillMapper.killByProcedure(map);
            //获取result
            Integer result = MapUtils.getInteger(map, "result", -2);
            if(result==1){
                Successkilled sk=successkilledMapper
                        .queryByIdWithSeckill(seckillId,userphone);
                return new SeckillExecution(seckillId,SeckillStaEnum.SUCCESS,sk);
            }else {
                return new SeckillExecution(seckillId,SeckillStaEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStaEnum.INNER_ERROR);
        }
    }
}
