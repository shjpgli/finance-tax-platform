-- CREATE DATABASE IF NOT EXISTS `abc12366_core` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `abc12366_core`;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS authority;

create table user(
  id VARCHAR(64) PRIMARY KEY NOT NULL COMMENT 'ID',
  username varchar(32) not null COMMENT '用户名',
  phone VARCHAR(11) NOT NULL COMMENT '手机号码',
  password VARCHAR(64) not null COMMENT '密码',
  enabled BOOLEAN not null COMMENT '激活状态',
  lastPasswordResetDate DATETIME COMMENT '上次重置密码时间',
  createDate DATETIME COMMENT '创建时间',
  modifyDate DATETIME COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '用户信息表';

create table authority (
  userId VARCHAR(64) not null COMMENT '用户ID，关联用户表',
  authority VARCHAR(64) not null COMMENT '用户角色'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '用户角色表';
create unique index idx_auth_id on authority (userId,authority);