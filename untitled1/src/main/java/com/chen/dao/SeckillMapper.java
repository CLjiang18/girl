package com.chen.dao;

import com.chen.pojo.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillMapper {
    /**
     * 减库存
     * @param seckillId
     * @param killtime
     * @return 更新记录的行数
     */
    int reduceNumber(@Param("seckillId")long seckillId,@Param("killtime") Date killtime);

    /**
     * 查询库存
     * @param seckillId
     * @return
     */
    Seckill queryById(Long seckillId);

    List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);

    /**
     * 调用存储过程
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
