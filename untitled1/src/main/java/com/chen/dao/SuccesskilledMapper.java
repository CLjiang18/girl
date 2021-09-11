package com.chen.dao;

import com.chen.pojo.Successkilled;
import org.apache.ibatis.annotations.Param;

public interface SuccesskilledMapper {
    /**
     * 插入秒杀成功明细并且过滤重复
     * 因为联合主键
     * @param seckillId
     * @param userphone
     * @return 插入的记录行数
     * xml实现中ignore，如果插入重复的，返回值为0
     */
    int insertSuccesKilled(@Param("seckillId")long seckillId,@Param("userphone") long userphone);

    /**
     * 根据id查询 Successkilled并携带实体seckill
     * @param seckillId
     * @return
     */
    Successkilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userphone") long userphone);
}
