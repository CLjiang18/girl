package com.chen.dao;

import com.chen.pojo.Successkilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件'
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccesskilledMapperTest {
    @Resource
    private SuccesskilledMapper successkilledMapper;
    @Test
    public void insertSuccesKilled() throws Exception{
        long id=1000L;
        long phone = 1111116L;
        successkilledMapper.insertSuccesKilled(id,phone);
    }

    @Test
    public void queryByIdWithSeckill() {
        Successkilled successkilled = successkilledMapper.queryByIdWithSeckill(1000L, 123456L);
        System.out.println(successkilled);
        System.out.println(successkilled.getSeckill());
    }
}