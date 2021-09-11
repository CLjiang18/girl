package com.chen.dao;

import com.chen.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
//配置spring junit整合
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件'
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillMapperTest {
    @Resource
    private SeckillMapper seckillMapper;

    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill=seckillMapper.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception{
        List<Seckill> seckills = seckillMapper.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }
    @Test
    public void reduceNumber() {
        Date date = new Date();
        seckillMapper.reduceNumber(1003L,date);

    }


}