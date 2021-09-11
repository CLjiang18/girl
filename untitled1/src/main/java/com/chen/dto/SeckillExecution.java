package com.chen.dto;

import com.chen.enums.SeckillStaEnum;
import com.chen.pojo.Successkilled;

/**
 * 执行秒杀操作自定义的返回值类型
 */
public class SeckillExecution {
    private long seckillId;
    private int state;
    private String stateInfo;
    private Successkilled successkilled;

    public SeckillExecution(long seckillId, SeckillStaEnum seckillStaEnum, Successkilled successkilled) {
        this.seckillId = seckillId;
        this.state = seckillStaEnum.getState();
        this.stateInfo = seckillStaEnum.getStateInfo();
        this.successkilled = successkilled;
    }

    public SeckillExecution(long seckillId, SeckillStaEnum seckillStaEnum) {
        this.seckillId = seckillId;
        this.state = seckillStaEnum.getState();
        this.stateInfo = seckillStaEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Successkilled getSuccesskilled() {
        return successkilled;
    }

    public void setSuccesskilled(Successkilled successkilled) {
        this.successkilled = successkilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successkilled=" + successkilled +
                '}';
    }
}
