-- 基础curd测试
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  `name` VARCHAR(128) NOT NULL UNIQUE COMMENT '用户名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 消费者表
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer` (
        `id` bigint  COMMENT '主键',
        `user_name` VARCHAR(128) COMMENT '用户名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消费者表';


-- 订单表
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    `id` bigint  COMMENT '主键',
    `order_time` datetime  COMMENT '主键',
    `amount` double  COMMENT '金额',
    `user_id` bigint COMMENT '消费者id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';


-- Map表
DROP TABLE IF EXISTS `user_map`;
CREATE TABLE `user_map` (
    `id` bigint  COMMENT '主键',
    `name` VARCHAR(128) COMMENT '用户名',
    `sex` varchar(16) COMMENT '性别',
    `type` varchar(32) COMMENT '类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消费者表';

-- 测试JSON数据
drop table tab_json;
-- 创建测试表
CREATE TABLE `tab_json`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `data` json DEFAULT NULL COMMENT 'json字符串',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建咖啡表
drop table t_coffee;
create table t_coffee (
    id bigint not null auto_increment,
    name varchar(255),
    price bigint not null,
    create_time timestamp,
    update_time timestamp,
    primary key (id)
);