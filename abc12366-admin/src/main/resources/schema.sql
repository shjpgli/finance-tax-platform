-- CREATE DATABASE IF NOT EXISTS `abc12366_admin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `abc12366_admin`;

DROP TABLE IF EXISTS sys_dict;
DROP TABLE IF EXISTS sys_menu;

create table sys_dict(
  dictId VARCHAR(64) PRIMARY KEY NOT NULL COMMENT '字典ID',
  dictName VARCHAR(64) NOT NULL COMMENT '字典名称',
  fieldKey VARCHAR(64) NOT NULL COMMENT '键名',
  fieldValue VARCHAR(64) NOT NULL COMMENT '键值',
  status BOOLEAN NOT NULL COMMENT '状态：0无效，1有效',
  createTime DATETIME COMMENT '创建时间',
  createUser VARCHAR(64) COMMENT '创建人ID',
  lastUpdate DATETIME COMMENT '最后修改时间',
  lastUser   VARCHAR(64) COMMENT '修改人ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '公共字典表';

create table sys_menu (
  menuId VARCHAR(64) PRIMARY KEY NOT NULL COMMENT '功能ID',
  menuName VARCHAR(64) NOT NULL COMMENT '功能名称',
  menuUrl VARCHAR(256) NOT NULL COMMENT '功能地址',
  parentId VARCHAR(64) NOT NULL COMMENT '父菜单ID',
  hierarchy INT NOT NULL COMMENT '级次',
  perms VARCHAR(500) COMMENT '授权（多个用逗号分割，如：user:list,user:create)',
  type VARCHAR(2) COMMENT '类型：1目录，2菜单，3按钮',
  icon VARCHAR(64) COMMENT '图标样式',
  sort INT COMMENT '排序',
  status BOOLEAN NOT NULL COMMENT '状态：0无效，1有效',
  remark VARCHAR(200) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '系统菜单表';