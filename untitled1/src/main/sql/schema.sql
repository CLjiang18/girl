create database miaosha;
use miaosha;
create table seckill(
    seckill_id bigint not null auto_increment comment '商品库存id',
    name varchar(120) not null comment '商品名称',
    number int not null comment '库存数量',
    start_time timestamp not null comment '秒杀开启时间',
    end_time timestamp not null comment '秒杀结束时间',
    create_time timestamp not null default current_timestamp comment '创建时间',
    primary key (seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT  CHARSET = utf8 COMMENT ='秒杀库存表';

insert into seckill (name, number, start_time, end_time)
    VALUES
        ('1000元秒杀iphoneX',100,'2021-09-08 00:00:00','2021-09-09 00:00:00'),
        ('1000元秒杀ipad2',200,'2021-09-08 00:00:00','2021-09-09 00:00:00'),
        ('1000元秒杀小米8',300,'2021-09-08 00:00:00','2021-09-09 00:00:00'),
        ('1000元秒杀红米note',400,'2021-09-08 00:00:00','2021-09-09 00:00:00');
# 秒杀成功明细表
# 用户登陆相关信息
    create table success_killed(
    seckill_id bigint not null comment '秒杀商品id',
    user_phone bigint not null comment '用户电话',
    state tinyint not null default -1 comment '状态：-1：无效 0：成功 1：已付款 2：已发货',
    create_time timestamp not null comment '创建时间',
    primary key (seckill_id,user_phone),/*联合主键*/
    key idx_create_time(create_time)
    )ENGINE =InnoDB DEFAULT CHARSET =utf8 COMMENT ='秒杀成功明细表';