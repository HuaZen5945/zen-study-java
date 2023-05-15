DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS children;

CREATE TABLE children
(
  id BIGINT NOT NULL COMMENT '主键ID',
  name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  user_id BIGINT NULL DEFAULT NULL COMMENT '上级ID',
  primary key (id)
);

-- 逻辑删除测试表
DROP TABLE IF EXISTS logic_data;

CREATE TABLE logic_data (
    id BIGINT NOT NULL ,
    name VARCHAR(20) NOT NULL ,
    deleted INT NOT NULL ,
    delete_time datetime DEFAULT NULL,
    primary key (id)
);