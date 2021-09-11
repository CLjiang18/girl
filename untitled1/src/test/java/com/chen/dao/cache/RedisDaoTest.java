package com.chen.dao.cache;

import com.chen.dao.SeckillMapper;
import com.chen.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件'
@ContextConfiguration({"classpath:spring/appcationContext.xml"})
public class RedisDaoTest {
    private long id=1001;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillMapper seckillMapper;
    @Test
    public void getSeckill() {
        Seckill seckill=redisDao.getSeckill(id);
        System.out.println(seckill==null);
        if(seckill==null){
            Seckill seckill2 = seckillMapper.queryById(id);
            System.out.println(seckill2);
            System.out.println("====断点====");
            if(seckill2!=null){
                String result = redisDao.putSeckill(seckill2);
                System.out.println(result);
                seckill2=redisDao.getSeckill(id);
                System.out.println("22222===>>>>");
                System.out.println(seckill2);
            }
        }
    }


    @Test
    public void putSeckill() {
    }
}