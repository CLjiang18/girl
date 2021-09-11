package com.chen.service;

import com.chen.dto.Exposer;
import com.chen.dto.SeckillExecution;
import com.chen.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件'
@ContextConfiguration({"classpath:spring/appcationContext.xml"})
public class SeckillServiceTest {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
            logger.info("List={}",seckillList);

    }

    @Test
    public void getById() {
        logger.info("List={}",seckillService.getById(1000l));
    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.exportSeckillUrl(1000l);
       logger.info("Exposer={}",exposer);
        System.out.println(exposer);
    }

    @Test
    public void executeSeckill() {
        long id=1000l;
        long phone=98765432111l;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        String md5 = exposer.getMd5();
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
        if(seckillExecution!=null){
            System.out.println("=======秒杀成功=======");
            System.out.println(seckillExecution);
        }
    }
}