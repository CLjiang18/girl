package com.chen.service;

import com.chen.dto.Exposer;
import com.chen.dto.SeckillExecution;
import com.chen.exception.KillCloseException;
import com.chen.exception.RepeatKillException;
import com.chen.exception.SeckillException;
import com.chen.pojo.Seckill;

import java.util.List;

/**
 * 站在 使用者 角度定义，不考虑具体实现
 * 参数尽量简单
 * return (类型/异常)
 */
public interface SeckillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 根据id查询商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 暴露秒杀商品地址，否则输出秒杀开始时间和系统时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userphone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId,long userphone,String md5)
                    throws SeckillException, RepeatKillException, KillCloseException;
    /**
     * 优化的方法:调用mysql存储过程,不需要抛异常分辨状态了,mysql端处理了
     * 执行秒杀操作
     * @param seckillId
     * @param userphone
     * @param md5
     * @return
     */
      SeckillExecution executeSeckillProcedure(long seckillId,long userphone,String md5);

}
