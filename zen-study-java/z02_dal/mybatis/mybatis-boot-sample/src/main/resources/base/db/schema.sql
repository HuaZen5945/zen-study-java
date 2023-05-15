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


-- 消费者表
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    `id` bigint  COMMENT '主键',
    `order_time` datetime  COMMENT '主键',
    `amount` double  COMMENT '金额',
    `user_id` bigint COMMENT '消费者id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';



