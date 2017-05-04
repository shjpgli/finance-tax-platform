-- CREATE DATABASE IF NOT EXISTS `abc12366_gateway` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `abc12366_gateway`;

# DROP TABLE IF EXISTS gw_api_log;
# DROP TABLE IF EXISTS gw_blacklist;
# DROP TABLE IF EXISTS gw_api;
# DROP TABLE IF EXISTS gw_app;
# DROP TABLE IF EXISTS gw_app_setting;

CREATE TABLE IF NOT EXISTS gw_api_log (
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

CREATE TABLE IF NOT EXISTS gw_blacklist (
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

CREATE TABLE IF NOT EXISTS gw_api (
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

CREATE TABLE IF NOT EXISTS gw_app (
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

CREATE TABLE IF NOT EXISTS gw_app_setting (
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

DROP TABLE IF EXISTS uc_user;
DROP TABLE IF EXISTS uc_user_extend;
DROP TABLE IF EXISTS uc_user_nsr;

create table IF NOT EXISTS uc_user(
  id VARCHAR(64) PRIMARY KEY NOT NULL COMMENT '用户ID',
  username varchar(32) not null COMMENT '用户名',
  phone VARCHAR(11) NOT NULL COMMENT '手机号码',
  password VARCHAR(64) not null COMMENT '密码',
  token VARCHAR(64) COMMENT '用户令牌',
  lastPasswordResetDate DATETIME COMMENT '上次重置密码时间',
  regMail VARCHAR(200) COMMENT '注册邮箱',
  userPicturePath VARCHAR(100) COMMENT'用户图片路径',
  regIP VARCHAR(50) COMMENT'注册IP',
  salt VARCHAR(10) COMMENT '密码干扰串用来和密码进行配合验证，防止被暴力破解',
  realName VARCHAR(200) COMMENT '真实名称',
  status BOOLEAN not null COMMENT '激活状态',
  createTime DATETIME COMMENT '创建时间',
  lastUpdate DATETIME COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '用户信息表';

create table IF NOT EXISTS uc_user_extend(
  id VARCHAR(64) PRIMARY KEY NOT NULL COMMENT '主键',
  userId VARCHAR(64) NOT NULL COMMENT '用户表主键',
  signature VARCHAR(200) COMMENT'个性签名',
  nickname VARCHAR(32) COMMENT'昵称',
  sex CHAR(1) COMMENT'性别，0：女，1：男',
  birthday DATE COMMENT'生日',
  bloodType VARCHAR (2) COMMENT'血型',
  weight VARCHAR(10) COMMENT'体重',
  height VARCHAR(10) COMMENT'身高',
  marital VARCHAR(6) COMMENT'婚姻状况',
  education VARCHAR(32) COMMENT'教育程度',
  graduate VARCHAR(100) COMMENT'毕业院校',
  occupation VARCHAR(100) COMMENT'职业',
  income VARCHAR(20) COMMENT'收入水平',
  postAddress VARCHAR(200) COMMENT'通讯地址',
  realName VARCHAR(16) COMMENT'真实姓名',
  experence VARCHAR(20) COMMENT'经验等级',
  weixin VARCHAR(50) COMMENT'微信账号',
  qq VARCHAR(50) COMMENT'QQ账号',
  safeQuestion VARCHAR(200) COMMENT'密保问题',
  safeAnswer VARCHAR(200) COMMENT'密保答案',
  province VARCHAR(50) COMMENT'所在地（省）',
  city VARCHAR(30) COMMENT'所在（市）',
  area VARCHAR(30) COMMENT'所在 （地区）',
  createTime DATETIME COMMENT '创建时间',
  lastUpdate DATETIME COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '用户信息扩展表';

create table IF NOT EXISTS uc_user_nsr(
  id VARCHAR(64) PRIMARY KEY NOT NULL COMMENT '纳税人信息表主键',
  userId VARCHAR (64) NOT NULL COMMENT '用户编号',
  djxh VARCHAR(64) COMMENT '登记序号',
  nsrsbh VARCHAR(32) COMMENT '纳税人识别号',
  shxydm VARCHAR(32) COMMENT '社会信用代码',
  status BOOLEAN NOT NULL COMMENT'绑定状态，0：未绑定，1：已绑定',
  createTime DATETIME COMMENT '创建时间',
  lastUpdate DATE COMMENT'最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '用户与纳税人绑定表';