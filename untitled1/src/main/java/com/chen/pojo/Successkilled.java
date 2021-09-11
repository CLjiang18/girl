package com.chen.pojo;

import java.util.Date;

public class Successkilled {
    private Long seckillId;
    private Long userphone;
    private Short state;
    private Date createtime;
    private Seckill seckill;

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Long getUserphone() {
        return userphone;
    }

    public void setUserphone(Long userphone) {
        this.userphone = userphone;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    @Override
    public String toString() {
        return "Successkilled{" +
                "seckillId=" + seckillId +
                ", userphone=" + userphone +
                ", state=" + state +
                ", createtime=" + createtime +
                ", seckill=" + seckill +
                '}';
    }
}
