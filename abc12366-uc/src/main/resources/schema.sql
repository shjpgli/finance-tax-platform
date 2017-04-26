-- CREATE DATABASE IF NOT EXISTS `abc12366_gateway` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `abc12366_gateway`;

DROP TABLE IF EXISTS gw_api_log;
DROP TABLE IF EXISTS gw_blacklist;
DROP TABLE IF EXISTS gw_api;
DROP TABLE IF EXISTS gw_app;
DROP TABLE IF EXISTS gw_app_setting;

CREATE TABLE gw_api_log (
  id        VARCHAR(64) PRIMARY KEY NOT NULL
  COMMENT 'ID',
  uri       VARCHAR(256)            NOT NULL
  COMMENT '访问接口地址',
  userAgent VARCHAR(256)            NOT NULL
  COMMENT '用户代理',
  userId    VARCHAR(64) COMMENT '接入userId',
  appId     VARCHAR(64) COMMENT '接入AppId',
  ip        VARCHAR(20) COMMENT '接入IP地址',
  inTime    LONG COMMENT '访问时间',
  outTime   LONG COMMENT '响应时间',
  status    VARCHAR(4) COMMENT '结果代码',
  message   VARCHAR(256) COMMENT '附言'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '接口调用日志表';

CREATE TABLE gw_blacklist (
  id         VARCHAR(64) PRIMARY KEY NOT NULL
  COMMENT 'ID',
  userId     VARCHAR(64) COMMENT 'userId',
  ip         VARCHAR(20)             NOT NULL
  COMMENT 'IP地址',
  startTime  DATETIME                NOT NULL
  COMMENT '锁定开始时间',
  endTime    DATETIME                NOT NULL
  COMMENT '锁定结束时间',
  status     BOOLEAN                 NOT NULL
  COMMENT '状态（1正常、0锁定）',
  remark     VARCHAR(256) COMMENT '备注',
  createTime DATETIME COMMENT '创建时间',
  createUser VARCHAR(64) COMMENT '创建人ID'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '接口调用日志表';

CREATE TABLE gw_api (
  id             VARCHAR(64) PRIMARY KEY NOT NULL
  COMMENT 'ID',
  name           VARCHAR(256)            NOT NULL
  COMMENT '接口名称',
  uri            VARCHAR(256)            NOT NULL
  COMMENT '接口地址',
  method         VARCHAR(10)             NOT NULL
  COMMENT '接口方法',
  version        INT                     NOT NULL
  COMMENT '版本',
  appId          VARCHAR(64) COMMENT '接口所属系统',
  authentication BOOLEAN                 NOT NULL
  COMMENT '是否需要验证用户身份: 0不需要，1需要',
  status         BOOLEAN                 NOT NULL
  COMMENT '接口状态：0停用，1启用',
  createTime     DATETIME COMMENT '创建时间',
  lastUpdate     DATETIME COMMENT '最后修改时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '服务接口表';

CREATE TABLE gw_app (
  id                 VARCHAR(64) PRIMARY KEY NOT NULL
  COMMENT 'appId',
  name               VARCHAR(32)             NOT NULL
  COMMENT '授权应用名称',
  password           VARCHAR(64)             NOT NULL
  COMMENT '授权应用密码',
  accessToken        VARCHAR(400) COMMENT '访问授权码',
  lastResetTokenTime DATETIME COMMENT '上次重置授权码时间',
  startTime          DATETIME COMMENT '授权时间起',
  endTime            DATETIME COMMENT '授权时间止',
  status             BOOLEAN                 NOT NULL
  COMMENT '状态',
  remark             VARCHAR(400) COMMENT '备注',
  createTime         DATETIME COMMENT '创建时间',
  lastUpdate         DATETIME COMMENT '修改时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '接入应用表';

CREATE TABLE gw_app_setting (
  id             VARCHAR(64) PRIMARY KEY NOT NULL
  COMMENT 'ID',
  appId          VARCHAR(64)             NOT NULL
  COMMENT 'APPID',
  apiId          VARCHAR(64)             NOT NULL
  COMMENT '接口ID',
  timesPerMinute INT COMMENT '每分钟允许访问次数',
  timesPerHour   INT COMMENT '每小时允许访问次数',
  timesPerDay    INT COMMENT '每天允许访问次数',
  status         BOOLEAN                 NOT NULL
  COMMENT '启停状态: 0停用，1启用',
  createTime     DATETIME COMMENT '创建时间',
  lastUpdate     DATETIME COMMENT '修改时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '接入应用设置表';

-- CREATE DATABASE IF NOT EXISTS `abc12366_core` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `abc12366_uc`;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS authority;

CREATE TABLE user (
  id                    VARCHAR(64) PRIMARY KEY NOT NULL
  COMMENT 'ID',
  username              VARCHAR(32)             NOT NULL
  COMMENT '用户名',
  phone                 VARCHAR(11)             NOT NULL
  COMMENT '手机号码',
  password              VARCHAR(64)             NOT NULL
  COMMENT '密码',
  enabled               BOOLEAN                 NOT NULL
  COMMENT '激活状态',
  lastPasswordResetDate DATETIME COMMENT '上次重置密码时间',
  createDate            DATETIME COMMENT '创建时间',
  modifyDate            DATETIME COMMENT '修改时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '用户信息表';

CREATE TABLE authority (
  userId    VARCHAR(64) NOT NULL
  COMMENT '用户ID，关联用户表',
  authority VARCHAR(64) NOT NULL
  COMMENT '用户角色'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT '用户角色表';
CREATE UNIQUE INDEX idx_auth_id ON authority (userId, authority);