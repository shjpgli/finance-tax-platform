/*
Navicat MySQL Data Transfer

Source Server         : abc12366_core
Source Server Version : 50173
Source Host           : 118.118.116.202:3306
Source Database       : abc12366_uc

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-06-13 09:39:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for uc_delivery_method
-- ----------------------------
DROP TABLE IF EXISTS `uc_delivery_method`;
CREATE TABLE `uc_delivery_method` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '配送方式名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) NOT NULL COMMENT '启用状态：true|false',
  `description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '详细介绍',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配送方式';

-- ----------------------------
-- Records of uc_delivery_method
-- ----------------------------
INSERT INTO `uc_delivery_method` VALUES ('764e734b-4f32-4eb6-85c5-4089509c6f75', '快递1', '0', '1', '111《统一地区运费》：全部的地区都使用默认的《重量设置》中的计费方式。《指定地区运费》：单独指定部分地区的运费', '2017-06-06 15:40:58', '2017-06-06 15:40:58');
INSERT INTO `uc_delivery_method` VALUES ('7ccd6b2c-d8cd-43e9-a2b8-af621975a86b', '自提', '0', '1', '《指定地区运费》：单独指定部分地区的运费', '2017-06-06 15:41:28', '2017-06-06 15:41:28');
INSERT INTO `uc_delivery_method` VALUES ('e531f762-41fe-4b15-813e-0b9156c252f7', '自提22', '0', '1', '自提自提', '2017-06-06 15:41:30', '2017-06-06 15:44:06');

-- ----------------------------
-- Table structure for uc_express
-- ----------------------------
DROP TABLE IF EXISTS `uc_express`;
CREATE TABLE `uc_express` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `userOrderNo` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户订单号',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `expressNo` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '快递单号',
  `deliveryId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司ID',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '快递状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expressId_UNIQUE` (`expressNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票快递单';

-- ----------------------------
-- Records of uc_express
-- ----------------------------

-- ----------------------------
-- Table structure for uc_express_comp
-- ----------------------------
DROP TABLE IF EXISTS `uc_express_comp`;
CREATE TABLE `uc_express_comp` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `compCode` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '物流公司代号',
  `compName` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '物流公司名称',
  `compUrl` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '物流公司URL',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `compCode_UNIQUE` (`compCode`),
  UNIQUE KEY `compName_UNIQUE` (`compName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='物流公司表';

-- ----------------------------
-- Records of uc_express_comp
-- ----------------------------
INSERT INTO `uc_express_comp` VALUES ('17dfcc72-8135-4ca7-81a6-4bd71fa70c45', 'STO ', '申通快递', 'http://www.sto.cn', '0', '2017-06-06 15:14:40', '2017-06-06 15:14:40');
INSERT INTO `uc_express_comp` VALUES ('1db1a091-aa0a-4bee-be54-90f5d7fb55ea', 'SF ', '顺丰速运', 'http://www.sf-express.com', '0', '2017-06-06 15:16:11', '2017-06-06 15:16:11');
INSERT INTO `uc_express_comp` VALUES ('26e89491-0453-4887-990b-e8447fe14c65', 'YTO ', '圆通速递', 'http://www.yto.net.cn', '0', '2017-06-06 15:17:22', '2017-06-06 15:17:22');

-- ----------------------------
-- Table structure for uc_express_invoice
-- ----------------------------
DROP TABLE IF EXISTS `uc_express_invoice`;
CREATE TABLE `uc_express_invoice` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `expressId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '快递单ID',
  `invoiceId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '发票ID',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='快递单与发票的关联信息';

-- ----------------------------
-- Records of uc_express_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for uc_goods
-- ----------------------------
DROP TABLE IF EXISTS `uc_goods`;
CREATE TABLE `uc_goods` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '产品名称',
  `imageUrl` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '展示图片URL',
  `introduction` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '产品介绍',
  `details` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '产品详情',
  `categoryId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品分类ID',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否上架：boolean',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `giftPoints` int(11) DEFAULT NULL COMMENT '赠送积分',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `unit` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '计件单位显示',
  `recommendType` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '推荐类型：1.最新产品 2.特价产品 3.热卖产品 4.推荐产品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品表';

-- ----------------------------
-- Records of uc_goods
-- ----------------------------
INSERT INTO `uc_goods` VALUES ('21ae9dd0-70d8-4d5c-aaca-596e9e717c51', '乐视超级电视 ', 'http://shop.aircheng.com/upload/2016/01/22/5694b892N2cb335db.jpg', '超级电视售后安装须知 ', '您签收产品后，我们不建议客户自行拆箱，您只需联系乐视售后人员上门为您服务，客户可以直接通过拨打乐视客服热线', '480aa22a-f56e-4e93-8dfa-ced5b16a504b', '1', '2017-06-05 16:24:21', '2017-06-05 16:24:21', '1000', '1', '元', '3');
INSERT INTO `uc_goods` VALUES ('34dcb3fd-65e3-4cee-aa39-0b5b231f7a3e', '美体小铺 ', 'http://shop.aircheng.com/upload/2016/01/22/5694b892N2cb335db.jpg', '美体小铺（THEBODYSHOP）护肤系列茶树精油20ml ', '美体小铺（THEBODYSHOP）护肤系列茶树精油20ml美体小铺（THEBODYSHOP）护肤系列茶树精油20ml', '616e28da-e887-415b-a385-428fc8cdf4e4', '1', '2017-06-06 09:28:02', '2017-06-06 09:28:02', '50', '1', '元', '3');
INSERT INTO `uc_goods` VALUES ('b6daf618-1081-4ec1-8dab-65144d7f706d', '乐视超级电视11', 'http://shop.aircheng.com/upload/2016/01/22/5694b892N2cb335db.jpg', '超级电视售后安装须知 ', '您签收产品后，我们不建议客户自行拆箱，您只需联系乐视售后人员上门为您服务，客户可以直接通过拨打乐视客服热线', '480aa22a-f56e-4e93-8dfa-ced5b16a504b', '1', '2017-06-05 16:24:39', '2017-06-05 16:24:39', '1000', '1', '元', '3');
INSERT INTO `uc_goods` VALUES ('ddb47cd4-bd27-4594-bea9-5b056e76034e', '乐视超级电视53 ', 'http://shop.aircheng.com/upload/2016/01/22/5694b892N2cb335db.jpg', '超级电视售后安装须知533555 ', '您签收产品后，我们不建议客户自行拆箱，您只需联系乐视售后人员上门为您服务，客户可以直接通过拨打乐视客服热线', '480aa22a-f56e-4e93-8dfa-ced5b16a504b', '1', '2017-06-05 16:25:09', '2017-06-05 17:06:20', '1000', '1', '元', '3');
INSERT INTO `uc_goods` VALUES ('e4bb3c96-c8ce-48b3-9027-90b72945fa55', '帮宝适（Pampers）超薄干爽 婴儿纸尿裤 新生儿NB96片【0-5kg】', 'http://shop.aircheng.com/upload/2017/05/06/56fdf7a6nc5053f90.jpg', 'Pampers帮宝适 超薄干爽婴儿纸尿裤 ', '产品特点：1.具有瞬吸导流层，保持肌肤干爽2.具有神奇锁水珠珠，迅速吸收牢牢锁住尿湿3.2倍弹力侧腰围，怎么动都舒适合身 ', 'ca5a5f45-8585-46e3-9d6a-04bb987a174a', '1', '2017-06-05 16:40:30', '2017-06-05 16:40:30', '100', '1', '积分', '3');

-- ----------------------------
-- Table structure for uc_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `uc_goods_category`;
CREATE TABLE `uc_goods_category` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `category` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '类别名称',
  `parentId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父ID',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品类别表';

-- ----------------------------
-- Records of uc_goods_category
-- ----------------------------
INSERT INTO `uc_goods_category` VALUES ('032f9b63-1926-4b90-8078-658929cd0e10', '母婴用品11', null, '1', '2017-06-02 14:48:46', '2017-06-02 15:54:04');
INSERT INTO `uc_goods_category` VALUES ('3712de22-7465-4e8e-a493-7c33eeab105a', '家具', null, '1', '2017-06-02 14:47:39', '2017-06-02 14:47:39');
INSERT INTO `uc_goods_category` VALUES ('3aa04db3-3920-415d-b991-82470ed9f78a', '扫地机器人', 'f748d1a6-0c49-4af9-bd7b-ff78988bcf64', '1', '2017-06-02 14:54:28', '2017-06-02 14:54:28');
INSERT INTO `uc_goods_category` VALUES ('480aa22a-f56e-4e93-8dfa-ced5b16a504b', '家用电器', null, '1', '2017-06-02 14:47:15', '2017-06-02 14:47:15');
INSERT INTO `uc_goods_category` VALUES ('4ec29679-481d-4d09-bfa2-cad0826672bd', '食品饮料', null, '1', '2017-06-02 14:47:32', '2017-06-02 14:47:32');
INSERT INTO `uc_goods_category` VALUES ('597c5b24-3ba2-4979-80be-79c1db961991', '进口食品', '4ec29679-481d-4d09-bfa2-cad0826672bd', '1', '2017-06-02 14:53:08', '2017-06-02 14:53:08');
INSERT INTO `uc_goods_category` VALUES ('5e6d0f52-41a0-472f-802c-0cd4f3a9681e', '厨房电器', '480aa22a-f56e-4e93-8dfa-ced5b16a504b', '1', '2017-06-02 14:52:39', '2017-06-02 14:52:39');
INSERT INTO `uc_goods_category` VALUES ('616e28da-e887-415b-a385-428fc8cdf4e4', '电风扇', 'f748d1a6-0c49-4af9-bd7b-ff78988bcf64', '1', '2017-06-02 14:54:14', '2017-06-02 14:54:14');
INSERT INTO `uc_goods_category` VALUES ('74c0b4e9-a4dc-4718-94db-3b63b0e48e0e', '奶粉', '032f9b63-1926-4b90-8078-658929cd0e10', '1', '2017-06-02 14:51:53', '2017-06-02 14:51:53');
INSERT INTO `uc_goods_category` VALUES ('83be3809-e094-4c24-abbb-2b14ee19081c', '服装', null, '1', '2017-06-02 14:47:48', '2017-06-02 14:47:48');
INSERT INTO `uc_goods_category` VALUES ('976995f0-0e48-40f8-8718-5f6e1ca6f6f3', '冷风扇', 'f748d1a6-0c49-4af9-bd7b-ff78988bcf64', '1', '2017-06-02 14:54:22', '2017-06-02 14:54:22');
INSERT INTO `uc_goods_category` VALUES ('c16ead7c-79fb-4ebc-906f-2557c45fdfd9', '酒品', '4ec29679-481d-4d09-bfa2-cad0826672bd', '1', '2017-06-02 14:53:17', '2017-06-02 14:53:17');
INSERT INTO `uc_goods_category` VALUES ('ca5a5f45-8585-46e3-9d6a-04bb987a174a', '尿不湿', '032f9b63-1926-4b90-8078-658929cd0e10', '1', '2017-06-02 14:51:26', '2017-06-02 14:51:26');
INSERT INTO `uc_goods_category` VALUES ('d2c13b9d-d10c-466f-a3c6-74fdcb13b14e', '玩具', '032f9b63-1926-4b90-8078-658929cd0e10', '1', '2017-06-02 14:51:59', '2017-06-02 14:51:59');
INSERT INTO `uc_goods_category` VALUES ('f748d1a6-0c49-4af9-bd7b-ff78988bcf64', '生活电器', '480aa22a-f56e-4e93-8dfa-ced5b16a504b', '1', '2017-06-02 14:52:33', '2017-06-02 14:52:33');

-- ----------------------------
-- Table structure for uc_invoice
-- ----------------------------
DROP TABLE IF EXISTS `uc_invoice`;
CREATE TABLE `uc_invoice` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `invoiceNo` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '发票号码',
  `invoiceCode` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '发票代码',
  `name` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发票抬头：1.个人 2.公司',
  `content` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发票内容：1.软件服务费 2.财税咨询费 3.技术服务费 4.财税培训费',
  `compName` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '开票公司名称',
  `amount` double DEFAULT NULL COMMENT '发票金额',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发票类型：1.增值税普通发票 2.增值税专用发票',
  `property` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发票性质：1.纸质发票 2.电子发票',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '发票状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '修改时间',
  `nsrsbh` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人识别号',
  `nsrmc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地址',
  `phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '注册电话',
  `bank` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '开户银行',
  `addressId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户快递地址ID',
  `userOrderNo` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户订单号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `invoiceId_UNIQUE` (`invoiceNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票信息';

-- ----------------------------
-- Records of uc_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for uc_invoice_back
-- ----------------------------
DROP TABLE IF EXISTS `uc_invoice_back`;
CREATE TABLE `uc_invoice_back` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `expressId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '快递ID',
  `reason` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '退货原因',
  `remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `expressNum` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '快递单号',
  `expressComp` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '快递公司',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '快递状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expressNum_UNIQUE` (`expressNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票退票';

-- ----------------------------
-- Records of uc_invoice_back
-- ----------------------------

-- ----------------------------
-- Table structure for uc_invoice_repo
-- ----------------------------
DROP TABLE IF EXISTS `uc_invoice_repo`;
CREATE TABLE `uc_invoice_repo` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `invoiceNo` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '发票号码',
  `invoiceCode` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '发票代码',
  `invoiceName` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '发票名称',
  `property` char(1) COLLATE utf8_bin NOT NULL COMMENT '发票性质：1.纸质发票 2.电子发票',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '发票状态：0.未使用 1.分配中 2.已使用 3.已作废',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发票仓库';

-- ----------------------------
-- Records of uc_invoice_repo
-- ----------------------------

-- ----------------------------
-- Table structure for uc_order
-- ----------------------------
DROP TABLE IF EXISTS `uc_order`;
CREATE TABLE `uc_order` (
  `orderNo` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '订单编号',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `goodsId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品编号',
  `dealPrice` double DEFAULT NULL COMMENT '成交价格',
  `num` int(11) NOT NULL COMMENT '产品数量',
  `orderStatus` char(1) COLLATE utf8_bin NOT NULL COMMENT '订单状态：0.购物车 1.新订单 2.确认订单 3.取消订单 4.作废订单 5.完成订单 6.退款 7.部分退款',
  `payStatus` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付状态：0.未支付 1.已支付 2.待支付',
  `deliveryMethod` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '配送方式',
  `payMethod` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式：WEIXIN、ALIPAY',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `feedback` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '反馈信息',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '产品名称',
  `dictId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字典ID主键',
  `uvipLevel` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '会员等级',
  `unit` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '计件单位显示',
  `categoryId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品分类ID',
  `category` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '产品分类名称',
  PRIMARY KEY (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户订单';

-- ----------------------------
-- Records of uc_order
-- ----------------------------
INSERT INTO `uc_order` VALUES ('20170607154758080565', '0bcef114-725c-44df-a457-3bd620d7eb67', '34dcb3fd-65e3-4cee-aa39-0b5b231f7a3e', '451', '2', '0', '0', '764e734b-4f32-4eb6-85c5-4089509c6f75', null, '2017-06-07 15:47:58', '2017-06-07 15:56:53', '反馈信息123', '美体小铺', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '等级1', '元', '616e28da-e887-415b-a385-428fc8cdf4e4', '电风扇');
INSERT INTO `uc_order` VALUES ('20170607155215491538', '0bcef114-725c-44df-a457-3bd620d7eb67', '21ae9dd0-70d8-4d5c-aaca-596e9e717c51', '1450', '1', '0', '0', '764e734b-4f32-4eb6-85c5-4089509c6f75', null, '2017-06-07 15:52:15', '2017-06-07 15:52:15', 'feedback22222', '乐视超级电视 ', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '等级1', '元', '480aa22a-f56e-4e93-8dfa-ced5b16a504b', '家用电器');
INSERT INTO `uc_order` VALUES ('20170607160846714839', '0bcef114-725c-44df-a457-3bd620d7eb67', '34dcb3fd-65e3-4cee-aa39-0b5b231f7a3e', '451', '1', '0', '0', '764e734b-4f32-4eb6-85c5-4089509c6f75', null, '2017-06-07 16:08:46', '2017-06-07 16:08:46', '反馈信息1', '美体小铺', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '等级2', '元', '616e28da-e887-415b-a385-428fc8cdf4e4', '电风扇');

-- ----------------------------
-- Table structure for uc_order_invoice
-- ----------------------------
DROP TABLE IF EXISTS `uc_order_invoice`;
CREATE TABLE `uc_order_invoice` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `orderId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '订单ID',
  `invoiceId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '发票ID PK',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单与发票关联表';

-- ----------------------------
-- Records of uc_order_invoice
-- ----------------------------
INSERT INTO `uc_order_invoice` VALUES ('79041059-c4de-420b-a89c-da06d6be098d', '84aaea76-990a-4c5c-a477-b96ea5fe5cd4', 'd334536a-8d85-4da8-9188-f8ce9e9882ce', null, '2017-05-18 21:25:03');

-- ----------------------------
-- Table structure for uc_order_log
-- ----------------------------
DROP TABLE IF EXISTS `uc_order_log`;
CREATE TABLE `uc_order_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `orderNo` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '快递单号',
  `action` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '动作',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单日志记录';

-- ----------------------------
-- Records of uc_order_log
-- ----------------------------
INSERT INTO `uc_order_log` VALUES ('1', '20170607154758080565', '1', '2017-06-08 10:24:19', null);
INSERT INTO `uc_order_log` VALUES ('2', '20170607155215491538', '2', '2017-06-08 10:24:33', null);

-- ----------------------------
-- Table structure for uc_product
-- ----------------------------
DROP TABLE IF EXISTS `uc_product`;
CREATE TABLE `uc_product` (
  `goodsId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '产品ID',
  `dictId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典ID主键',
  `stock` int(11) NOT NULL COMMENT '库存',
  `marketPrice` double NOT NULL COMMENT '市场价',
  `sellingPrice` double NOT NULL COMMENT '销售价',
  `costPrice` double DEFAULT NULL COMMENT '成本价',
  `finalPrice` double DEFAULT NULL COMMENT '最终价格',
  `discount` double DEFAULT NULL COMMENT '会员折扣',
  `uvipLevel` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '会员等级',
  `weight` double DEFAULT NULL COMMENT '重量：克',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品参数';

-- ----------------------------
-- Records of uc_product
-- ----------------------------
INSERT INTO `uc_product` VALUES ('21ae9dd0-70d8-4d5c-aaca-596e9e717c51', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '101', '1500', '1450', '1300', '1400', '3', '等级1', '20000', '2017-06-05 16:24:21', '2017-06-05 16:24:21');
INSERT INTO `uc_product` VALUES ('21ae9dd0-70d8-4d5c-aaca-596e9e717c51', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '98', '1600', '1550', '1400', '1500', '2', '等级2', '20000', '2017-06-05 16:24:21', '2017-06-05 16:24:21');
INSERT INTO `uc_product` VALUES ('b6daf618-1081-4ec1-8dab-65144d7f706d', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '101', '1500', '1450', '1300', '1400', '3', '等级1', '20000', '2017-06-05 16:24:39', '2017-06-05 16:24:39');
INSERT INTO `uc_product` VALUES ('b6daf618-1081-4ec1-8dab-65144d7f706d', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '98', '1600', '1550', '1400', '1500', '2', '等级2', '20000', '2017-06-05 16:24:39', '2017-06-05 16:24:39');
INSERT INTO `uc_product` VALUES ('e4bb3c96-c8ce-48b3-9027-90b72945fa55', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '101', '150', '145', '130', '140', '3', '等级1', '10000', '2017-06-05 16:40:30', '2017-06-05 16:40:30');
INSERT INTO `uc_product` VALUES ('e4bb3c96-c8ce-48b3-9027-90b72945fa55', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '22', '160', '155', '140', '150', '2', '等级2', '10000', '2017-06-05 16:40:30', '2017-06-05 16:40:30');
INSERT INTO `uc_product` VALUES ('ddb47cd4-bd27-4594-bea9-5b056e76034e', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '101', '1501', '1451', '1301', '1401', '3', '等级1', '20000', null, '2017-06-05 17:06:20');
INSERT INTO `uc_product` VALUES ('ddb47cd4-bd27-4594-bea9-5b056e76034e', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '98', '1601', '1551', '1401', '1501', '2', '等级2', '20000', null, '2017-06-05 17:06:20');
INSERT INTO `uc_product` VALUES ('34dcb3fd-65e3-4cee-aa39-0b5b231f7a3e', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '200', '501', '451', '301', '401', '3', '等级1', '5000', '2017-06-06 09:28:02', '2017-06-06 09:28:02');
INSERT INTO `uc_product` VALUES ('34dcb3fd-65e3-4cee-aa39-0b5b231f7a3e', 'f2d269dd-7b8f-40ea-8555-188ceee319d4', '198', '601', '551', '401', '501', '2', '等级2', '5000', '2017-06-06 09:28:02', '2017-06-06 09:28:02');

-- ----------------------------
-- Table structure for uc_sys_task
-- ----------------------------
DROP TABLE IF EXISTS `uc_sys_task`;
CREATE TABLE `uc_sys_task` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '任务名称',
  `startTime` datetime DEFAULT NULL COMMENT '任务开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '任务结束时间',
  `rule` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '任务规则',
  `points` int(11) DEFAULT NULL COMMENT '任务积分',
  `type` char(1) COLLATE utf8_bin NOT NULL COMMENT '任务类型：新手任务、日常任务、特殊任务、帮帮任务',
  `status` tinyint(4) NOT NULL COMMENT '数据状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `imageUrl` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '主题图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of uc_sys_task
-- ----------------------------
INSERT INTO `uc_sys_task` VALUES ('0e6db078-de8d-4ea8-ac47-86be243c8277', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '0', '2017-05-24 11:24:34', '2017-05-24 11:24:34', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', 'task02', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'dsf', '232', 'a', '1', '2017-05-24 11:24:34', '2017-05-24 11:32:32', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('1794e71c-4458-4ace-9dfe-fb92c8b5d325', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '0', '2017-05-24 11:24:35', '2017-05-24 11:24:35', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('4802e460-fa09-430f-8679-9a15921b0794', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:33', '2017-05-24 11:24:33', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('53b8288e-4edc-456b-9065-5dd681cf9fad', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:33', '2017-05-24 11:24:33', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('56b3eb9f-226d-44df-a5fd-7cc63fb44050', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:35', '2017-05-24 11:24:35', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('6757895b-017d-4085-9a94-fda40208e9c3', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '0', '2017-05-24 11:24:32', '2017-05-24 11:24:32', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('6a1e571c-d080-44e2-a9cf-d015b0b212f4', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:36', '2017-05-24 11:24:36', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('80acfaaa-c0f0-43db-bbb8-a30164415a70', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:33', '2017-05-24 11:24:33', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('8a5809ac-e3d4-439f-8d0e-1110a0881d0b', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:35', '2017-05-24 11:24:35', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('ab91cc32-8d0c-4577-86c6-07050cfeaa88', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:12', '2017-05-24 11:24:12', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('ce263fd1-6c28-49b7-8259-d8d08b126ad6', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:33', '2017-05-24 11:24:33', '/fdsf/fdsf/dfdsfdsf');
INSERT INTO `uc_sys_task` VALUES ('e47adcfa-f21b-4308-86ce-cc7eaab83538', 'task01', '2017-05-19 17:35:11', '2017-06-19 17:35:11', 'fjdsjfldsjfljdfljdslj', '111', 'a', '1', '2017-05-24 11:24:33', '2017-05-24 11:24:33', '/fdsf/fdsf/dfdsfdsf');

-- ----------------------------
-- Table structure for uc_token
-- ----------------------------
DROP TABLE IF EXISTS `uc_token`;
CREATE TABLE `uc_token` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `appId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '应用ID',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `token` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '令牌',
  `lastTokenResetTime` datetime DEFAULT NULL COMMENT '上次重置时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户token表';

-- ----------------------------
-- Records of uc_token
-- ----------------------------
INSERT INTO `uc_token` VALUES ('70207d2b-7624-40e7-ad44-a6076ece61fb', 'f266b34f-afda-4deb-b980-4eb9ce088706', '76585baa-e473-4336-9984-15310478e0c8', '34c1e554381bb36ae440d62ffbee2513', '2017-06-12 10:51:35');
INSERT INTO `uc_token` VALUES ('7e097a18-3ede-4ad0-837c-4144be21c3f8', 'f266b34f-afda-4deb-b980-4eb9ce088706', '5aca5af8-0d88-4065-92ae-5ea5c477d52d', '1910b22aee980345a5c4f60ff57de60d', '2017-06-05 17:42:42');
INSERT INTO `uc_token` VALUES ('c15ea598-f35a-4423-80fd-aee4df7da611', 'f266b34f-afda-4deb-b980-4eb9ce088706', '0bcef114-725c-44df-a457-3bd620d7eb67', '545414f3748ebc63bf57ac135d5a2b89', '2017-05-25 16:45:25');
INSERT INTO `uc_token` VALUES ('d55decfc-9b87-46da-8fd9-58b241634f26', 'f266b34f-afda-4deb-b980-4eb9ce088706', 'b2d3e85a-b143-4eb4-9f0e-1fd040651350', '3447520abdbcbed7aa3b3e12d1cd1dd5', '2017-06-02 15:55:03');

-- ----------------------------
-- Table structure for uc_trade_log
-- ----------------------------
DROP TABLE IF EXISTS `uc_trade_log`;
CREATE TABLE `uc_trade_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `orderNo` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `amount` double NOT NULL COMMENT '交易金额',
  `tradeStatus` char(1) COLLATE utf8_bin NOT NULL COMMENT '交易状态：1.交易中 2.交易成功 3.交易失败 4.取消交易',
  `tradeTime` datetime NOT NULL COMMENT '交易时间',
  `payMethod` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '交易方式：WEIXIN、ALIPAY',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `compareStatus` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '对账状态：1.已对账 2.手工作废 3.手工完成',
  `compareTime` datetime DEFAULT NULL COMMENT '对账时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易记录';

-- ----------------------------
-- Records of uc_trade_log
-- ----------------------------

-- ----------------------------
-- Table structure for uc_uexp_level
-- ----------------------------
DROP TABLE IF EXISTS `uc_uexp_level`;
CREATE TABLE `uc_uexp_level` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '等级名称',
  `value` int(11) DEFAULT NULL COMMENT '成长值',
  `topPerDay` int(11) DEFAULT NULL COMMENT '每日封顶',
  `status` tinyint(4) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户经验值等级';

-- ----------------------------
-- Records of uc_uexp_level
-- ----------------------------
INSERT INTO `uc_uexp_level` VALUES ('5f040faa-866a-4c88-a485-84212aac94ea', 'test02', '33333', '11', '0', '2017-05-23 10:52:09', '2017-05-23 10:57:19');
INSERT INTO `uc_uexp_level` VALUES ('ea8338ee-46ab-421f-a41d-14e4f7e87068', 'test01', '332333', '11', '0', '2017-05-23 10:57:53', '2017-05-23 10:57:53');

-- ----------------------------
-- Table structure for uc_uexp_log
-- ----------------------------
DROP TABLE IF EXISTS `uc_uexp_log`;
CREATE TABLE `uc_uexp_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ruleId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '经验值规则ID',
  `income` int(11) DEFAULT NULL COMMENT '挣取的经验值',
  `outgo` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '失去的经验值',
  `usableExp` bigint(20) DEFAULT NULL COMMENT '可用经验值',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人会员经验值记录表';

-- ----------------------------
-- Records of uc_uexp_log
-- ----------------------------
INSERT INTO `uc_uexp_log` VALUES ('00e43045-5672-46d4-9aac-0d7395b43abf', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'c6da5dbe-7d31-41ad-b8a2-d71feb78b38b', '11', '22', '222', '2017-05-23 15:00:34');
INSERT INTO `uc_uexp_log` VALUES ('01ec491f-9717-4ac5-850f-02204ba65e3d', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'c6da5dbe-7d31-41ad-b8a2-d71feb78b38b', '111', '22', '78', '2017-05-23 15:48:55');
INSERT INTO `uc_uexp_log` VALUES ('04df0e5d-5fa1-47df-8036-6d963e08f53c', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:19');
INSERT INTO `uc_uexp_log` VALUES ('07a1fd4c-c55e-47ce-8955-b8a09272db64', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:20');
INSERT INTO `uc_uexp_log` VALUES ('3fb6549f-1747-43db-9a96-7c0d1eeab720', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:19');
INSERT INTO `uc_uexp_log` VALUES ('47a1226d-f18d-40ba-8a13-0f495f902bd4', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:20');
INSERT INTO `uc_uexp_log` VALUES ('4d79aaa9-a259-4f0b-a6b5-c7e095c20099', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:13');
INSERT INTO `uc_uexp_log` VALUES ('5b70bd1c-3495-49cd-8384-eb3cc2837cd3', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:20');
INSERT INTO `uc_uexp_log` VALUES ('8eca1b6c-200d-48bf-8163-1fb01667723f', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'c6da5dbe-7d31-41ad-b8a2-d71feb78b38b', '11', '22', '222', '2017-05-23 15:00:35');
INSERT INTO `uc_uexp_log` VALUES ('d72e5a22-06f8-449e-8b1f-8e8b7d827730', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'b5fc49d3-2e33-4090-a39f-186399fa6f16', '11', '22', '222', '2017-05-23 14:56:20');
INSERT INTO `uc_uexp_log` VALUES ('e2424f61-b11d-46ab-99ba-fe3d6460f702', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'c6da5dbe-7d31-41ad-b8a2-d71feb78b38b', '11', '22', '222', '2017-05-23 15:00:35');
INSERT INTO `uc_uexp_log` VALUES ('f088321d-2e2c-4c26-8e0c-1d3a0f448078', '53b167d8-631d-4cd8-86b4-c07974a204cf', 'c6da5dbe-7d31-41ad-b8a2-d71feb78b38b', '11', '22', '-11', '2017-05-23 15:48:15');

-- ----------------------------
-- Table structure for uc_uexp_rule
-- ----------------------------
DROP TABLE IF EXISTS `uc_uexp_rule`;
CREATE TABLE `uc_uexp_rule` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `code` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '规则代码',
  `exp` int(11) NOT NULL COMMENT '经验值，可以为负数',
  `description` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '规则描述',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL,
  `status` tinyint(4) NOT NULL COMMENT '数据状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='经验值规则表';

-- ----------------------------
-- Records of uc_uexp_rule
-- ----------------------------
INSERT INTO `uc_uexp_rule` VALUES ('b5fc49d3-2e33-4090-a39f-186399fa6f16', 'rule01', 'rule01', '1', 'fdsfdsf', 'd', '1', '2017-05-23 11:32:49', '2017-05-23 11:32:49');
INSERT INTO `uc_uexp_rule` VALUES ('c6da5dbe-7d31-41ad-b8a2-d71feb78b38b', 'rule02', 'rule02', '11', 'fdsffdsfddsf', 'c', '1', '2017-05-23 11:33:14', '2017-05-23 11:43:28');
INSERT INTO `uc_uexp_rule` VALUES ('d6dad270-986a-4646-a5d1-15e5a02afaf8', 'rule01', 'rule01', '1', 'fdsfdsf', 'd', '1', '2017-05-23 11:35:45', '2017-05-23 11:35:45');

-- ----------------------------
-- Table structure for uc_upoints_log
-- ----------------------------
DROP TABLE IF EXISTS `uc_upoints_log`;
CREATE TABLE `uc_upoints_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `ruleId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '积分规则ID',
  `income` int(11) DEFAULT NULL COMMENT '挣取的积分',
  `outgo` int(11) DEFAULT NULL COMMENT '消费的积分',
  `usablePoints` bigint(20) DEFAULT NULL COMMENT '可用积分',
  `createTime` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人用户积分记录表';

-- ----------------------------
-- Records of uc_upoints_log
-- ----------------------------
INSERT INTO `uc_upoints_log` VALUES ('1648c94c-e8df-4bcf-9ce6-080c355c6860', '5aca5af8-0d88-4065-92ae-5ea5c477d52d', '0bd3d173-ab55-4cb5-8077-5b059ef51732', '11', '1', '20', '2017-05-23 15:55:02');
INSERT INTO `uc_upoints_log` VALUES ('60acce8e-8fed-40f5-b1a1-145622c56a72', '0bcef114-725c-44df-a457-3bd620d7eb67', '0bd3d173-ab55-4cb5-8077-5b059ef51732', '0', '0', '0', '2017-05-17 09:39:20');
INSERT INTO `uc_upoints_log` VALUES ('71b22097-3586-4b7c-bdcd-3162423ce950', '0bcef114-725c-44df-a457-3bd620d7eb67', '0bd3d173-ab55-4cb5-8077-5b059ef51732', '0', '0', '0', '2017-05-17 09:36:51');
INSERT INTO `uc_upoints_log` VALUES ('bd516db9-7a53-4fea-8ad0-268def6ef109', '5aca5af8-0d88-4065-92ae-5ea5c477d52d', '0bd3d173-ab55-4cb5-8077-5b059ef51732', '11', '1', '10', '2017-05-23 15:54:52');

-- ----------------------------
-- Table structure for uc_upoints_rule
-- ----------------------------
DROP TABLE IF EXISTS `uc_upoints_rule`;
CREATE TABLE `uc_upoints_rule` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `code` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '规则代码',
  `points` int(11) NOT NULL COMMENT '积分，可以为负数',
  `description` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '规则描述',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '积分规则类型：帮帮积分、充值积分、用户违规扣分、用户中心积分',
  `status` tinyint(4) NOT NULL COMMENT '数据状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='积分规则表';

-- ----------------------------
-- Records of uc_upoints_rule
-- ----------------------------
INSERT INTO `uc_upoints_rule` VALUES ('0bd3d173-ab55-4cb5-8077-5b059ef51732', 'rule01改', 'rule01改', '10022', 'rule01改', '1', '0', '2017-05-16 17:19:08', '2017-05-16 17:43:46');
INSERT INTO `uc_upoints_rule` VALUES ('40e6ecd2-6c54-468d-8cc7-a9a876fe5ebd', 'rule01', 'rule03', '1000', 'rule01', '', '1', '2017-05-16 17:37:55', '2017-05-16 17:37:55');
INSERT INTO `uc_upoints_rule` VALUES ('9417aca8-a287-41af-ab35-9294c448dc13', 'rule01', 'rule02', '1000', 'rule01', '', '1', '2017-05-16 17:36:31', '2017-05-16 17:36:31');
INSERT INTO `uc_upoints_rule` VALUES ('ed9e10e4-670a-4602-b41f-d5b9531befd9', 'rule01', 'rule04', '1000', 'rule01', '', '1', '2017-05-17 11:09:03', '2017-05-17 11:25:34');

-- ----------------------------
-- Table structure for uc_user
-- ----------------------------
DROP TABLE IF EXISTS `uc_user`;
CREATE TABLE `uc_user` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号码',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `regMail` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '注册邮箱',
  `regIP` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '注册IP',
  `salt` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '密码干扰串用来和密码进行配合验证，防止被暴力破解',
  `nickname` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `status` tinyint(1) NOT NULL COMMENT '激活状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '修改时间',
  `userPicturePath` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '原始用户图片路径',
  `maxUserPicturePath` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '大尺寸用户头像路径',
  `midUserPicturePath` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '中尺寸用户头像路径',
  `minUserPicturePath` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '小尺寸用户头像路径',
  `points` bigint(20) DEFAULT NULL COMMENT '累计积分',
  `exp` bigint(20) DEFAULT NULL COMMENT '累计经验值',
  `vipLevel` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

-- ----------------------------
-- Records of uc_user
-- ----------------------------
INSERT INTO `uc_user` VALUES ('1018b7c6-d80f-4646-9954-e938a41b6298', 'tM9fFeIG9fSaD0MA', '18888888889', '4b77049c29f856f124362a58fa203398', '435720953@qq.com', '127.0.0.1', '377250', null, '1', '2017-05-18 11:26:25', '2017-05-18 11:26:25', null, null, null, null, '0', '0', null);
INSERT INTO `uc_user` VALUES ('53b167d8-631d-4cd8-86b4-c07974a204cf', 'username18888880088', '18888880088', '5c18a2383ee0fb960deee63185979176', '435720953@qq.com', '127.0.0.1', '222162', null, '1', '2017-05-18 16:44:59', '2017-05-18 16:44:59', null, null, null, null, '0', '78', null);
INSERT INTO `uc_user` VALUES ('5aca5af8-0d88-4065-92ae-5ea5c477d52d', 'username12888888888', '12888888888', '8dc4986b1a99204bd6a1a012515b11d1', '435720953@qq.com', '255.0.0.0', '842872', null, '1', '2017-05-18 17:04:11', '2017-06-05 17:42:42', null, null, null, null, '20', '0', null);
INSERT INTO `uc_user` VALUES ('64ef6712-3f86-47ad-bf5d-d33835f8929e', 'e8FfVuaVxCd5hb0I', '18888888888', '6396df58b4cdcc378bf0002e8e11c6b9', '435720953@qq.com', '127.0.0.1', '177403', null, '1', '2017-05-18 11:05:02', '2017-05-18 11:05:02', null, null, null, null, '0', '0', null);
INSERT INTO `uc_user` VALUES ('76585baa-e473-4336-9984-15310478e0c8', 'wcrQtNUq3KcTPTHi', '13278849423', 'bd5b4e99d7f85dce9c01f66380386061', '435720953@qq.com', '127.0.0.1', '213443', null, '1', '2017-05-18 11:59:46', '2017-06-05 16:50:15', null, null, null, null, '0', '0', null);

-- ----------------------------
-- Table structure for uc_user_address
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_address`;
CREATE TABLE `uc_user_address` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '收货人姓名',
  `province` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '市',
  `area` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '区',
  `detail` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号码',
  `isDefault` tinyint(4) DEFAULT NULL COMMENT '是否默认地址',
  `status` tinyint(4) NOT NULL COMMENT '地址状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='收货地址信息';

-- ----------------------------
-- Records of uc_user_address
-- ----------------------------
INSERT INTO `uc_user_address` VALUES ('546b73d4-0f1e-49e5-89e6-af0b9b9c1e2a', '0bcef114-725c-44df-a457-3bd620d7eb67', '收货人2', '湖南省', '长沙市', '天心区', '详细地址详细地址2', '13510103333', '1', '1', '2017-05-23 14:28:54', '2017-05-23 14:28:54');

-- ----------------------------
-- Table structure for uc_user_dzsb
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_dzsb`;
CREATE TABLE `uc_user_dzsb` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '纳税人信息表主键',
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  `djxh` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登记序号',
  `nsrsbh` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人识别号',
  `nsrmc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人名称',
  `shxydm` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '社会信用代码',
  `swjgMc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '税务机关名称',
  `swjgDm` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '税务机关代码',
  `status` tinyint(1) NOT NULL COMMENT '绑定状态，0：未绑定，1：已绑定',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户与国税电子申报身份绑定表';

-- ----------------------------
-- Records of uc_user_dzsb
-- ----------------------------
INSERT INTO `uc_user_dzsb` VALUES ('496e4361-15a6-4e17-967c-6c4fe92bc6d2', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '0', '2017-05-15 16:00:34', '2017-05-15 16:48:52');
INSERT INTO `uc_user_dzsb` VALUES ('721711d9-4a3e-47dc-9d52-5876d3d3beea', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '1', '2017-05-15 15:57:55', '2017-05-15 15:57:55');
INSERT INTO `uc_user_dzsb` VALUES ('735b0dc3-0235-4608-b4cb-af5d059b8480', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '1', '2017-05-15 16:01:56', '2017-05-15 16:01:56');
INSERT INTO `uc_user_dzsb` VALUES ('a1ddf769-71af-4552-9be1-b84e8145679e', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '1', '2017-05-15 16:03:36', '2017-05-15 16:03:36');
INSERT INTO `uc_user_dzsb` VALUES ('a79d0a3e-9213-4201-9564-8e1a862a066c', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '1', '2017-05-15 16:02:23', '2017-05-15 16:02:23');
INSERT INTO `uc_user_dzsb` VALUES ('f661b846-b75e-43d2-922a-4eea7638a39c', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '1', '2017-05-15 16:06:17', '2017-05-15 16:06:17');
INSERT INTO `uc_user_dzsb` VALUES ('fdcd3456-6598-4105-8d6d-c3be041c1026', '0bcef114-725c-44df-a457-3bd620d7eb67', '4324324', '5435345', 'gdfg', '34534', 'dfgf', '54354', '0', '2017-05-17 11:29:51', '2017-05-17 11:26:11');

-- ----------------------------
-- Table structure for uc_user_extend
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_extend`;
CREATE TABLE `uc_user_extend` (
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户表主键',
  `signature` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '个性签名',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别，0：女，1：男',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `bloodType` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '血型',
  `weight` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '体重',
  `height` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '身高',
  `marital` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '教育程度',
  `graduate` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '毕业院校',
  `occupation` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '职业',
  `income` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '收入水平',
  `postAddress` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '通讯地址',
  `realName` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `weixin` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '微信账号',
  `qq` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ账号',
  `safeQuestion` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '密保问题',
  `safeAnswer` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '密保答案',
  `province` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '所在地（省）',
  `city` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '所在（市）',
  `area` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '所在 （地区）',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '修改时间',
  `tags` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '擅长领域',
  `idcard` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `frontImage` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证正面',
  `backImage` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证反面',
  `startTime` date DEFAULT NULL COMMENT '实名认证有效期起',
  `endTime` date DEFAULT NULL COMMENT '实名认证有效期止',
  `validStatus` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '实名认证状态',
  `validTime` datetime DEFAULT NULL COMMENT '实名认证时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息扩展表';

-- ----------------------------
-- Records of uc_user_extend
-- ----------------------------
INSERT INTO `uc_user_extend` VALUES ('0bcef114-725c-44df-a457-3bd620d7eb67', '刘贵尧gai', '1', '2017-05-12', 'A型', '60', '175', '未婚', '本科', '林科大', 'IT', '100', '地税局宿舍', '六大个', '13278849423', '435720953', '我的电脑型号是', '联想Y470', '湖南', '长沙', '芙蓉区', '2017-05-12 16:56:46', '2017-05-17 17:43:36', 'p', '430224', 'das', 'fdsf', '2017-05-12', '2017-05-12', '1', '2017-05-12 17:23:10');

-- ----------------------------
-- Table structure for uc_user_hnds
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_hnds`;
CREATE TABLE `uc_user_hnds` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `username` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '许可用户',
  `subuser` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '子用户',
  `nsrmc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人名称',
  `nsrsbh` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人识别号',
  `djxh` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登记序号',
  `shxydm` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '社会信用代码',
  `swjgMc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '税务机关名称',
  `swjgDm` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '税务机关代码',
  `status` tinyint(4) NOT NULL COMMENT '绑定状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户与湖南地税网厅身份绑定表';

-- ----------------------------
-- Records of uc_user_hnds
-- ----------------------------
INSERT INTO `uc_user_hnds` VALUES ('b7edb8a2-df3b-4b30-ac96-089a8bd06204', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '', '', '', '0', '2017-05-15 17:28:54', '2017-05-15 17:26:51');
INSERT INTO `uc_user_hnds` VALUES ('e74a1fbc-939c-486f-aaec-73c8060aff10', '0bcef114-725c-44df-a457-3bd620d7eb67', 'lgy', 'dsf', 'df', '34324', '342432', '4324', 'fdsf', '4324324', '0', '2017-05-17 11:41:19', '2017-05-17 11:33:58');

-- ----------------------------
-- Table structure for uc_user_hngs
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_hngs`;
CREATE TABLE `uc_user_hngs` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `djxh` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登记序号',
  `nsrsbh` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人识别号',
  `bsy` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '办税员类型',
  `nsrmc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '纳税人名称',
  `shxydm` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `smrzzt` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '实名认证状态',
  `swjgMc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '税务机关名称',
  `swjgDm` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '税务机关代码',
  `status` tinyint(4) NOT NULL COMMENT '绑定状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户与国税网上办税身份绑定表';

-- ----------------------------
-- Records of uc_user_hngs
-- ----------------------------
INSERT INTO `uc_user_hngs` VALUES ('6e0778d7-5463-4871-84bd-25e016ae4b63', '0bcef114-725c-44df-a457-3bd620d7eb67', '343432', '43242', 'fsfsdfs', 'fdsfdf', 'fdsfsd', '0', 'fdsfds', '53432', '0', '2017-05-17 11:36:55', '2017-05-17 11:29:44');
INSERT INTO `uc_user_hngs` VALUES ('be64630b-957c-4758-a249-2909637f5147', '0bcef114-725c-44df-a457-3bd620d7eb67', '', '', '', '', '', '0', '', '', '0', '2017-05-15 17:12:00', '2017-05-15 17:03:51');

-- ----------------------------
-- Table structure for uc_user_task
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_task`;
CREATE TABLE `uc_user_task` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `userId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `taskId` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '任务ID',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '任务状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户与任务关联表';

-- ----------------------------
-- Records of uc_user_task
-- ----------------------------
INSERT INTO `uc_user_task` VALUES ('34adda66-ed63-4ef3-82eb-60997ed0add4', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '0', '2017-05-24 15:41:26', '2017-05-24 15:42:44');
INSERT INTO `uc_user_task` VALUES ('41a360ef-1f76-4971-a056-c70aedc2ac66', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '1', '2017-05-24 15:41:35', '2017-05-24 15:41:35');
INSERT INTO `uc_user_task` VALUES ('45a48eb9-d7a8-4520-a9fd-b2a28980dc33', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '1', '2017-05-24 15:41:34', '2017-05-24 15:41:34');
INSERT INTO `uc_user_task` VALUES ('748c4b1c-60e7-473b-9246-612aeddd8b83', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '1', '2017-05-24 15:41:35', '2017-05-24 15:41:35');
INSERT INTO `uc_user_task` VALUES ('b922e739-787e-4a90-bed1-76be48f7365b', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '1', '2017-05-24 15:41:34', '2017-05-24 15:41:34');
INSERT INTO `uc_user_task` VALUES ('c783cb20-c4ca-477b-9e3d-ae9566aa5ca8', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '1', '2017-05-24 15:41:35', '2017-05-24 15:41:35');
INSERT INTO `uc_user_task` VALUES ('ff8f2c21-7861-4c4e-92d6-9ec68ae137d6', '53b167d8-631d-4cd8-86b4-c07974a204cf', '0fa3b58e-da7f-4dd1-a846-ec55cdc61f4c', '1', '2017-05-24 15:41:34', '2017-05-24 15:41:34');

-- ----------------------------
-- Table structure for uc_uvip_level
-- ----------------------------
DROP TABLE IF EXISTS `uc_uvip_level`;
CREATE TABLE `uc_uvip_level` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `level` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '等级名称',
  `factor` double DEFAULT NULL COMMENT '权重',
  `status` tinyint(4) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `level_UNIQUE` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of uc_uvip_level
-- ----------------------------
INSERT INTO `uc_uvip_level` VALUES ('44d91749-3fd5-470f-a021-901b6e44f29b', '2', '1', '0', '2017-05-19 17:09:32', '2017-05-19 17:09:32');

-- ----------------------------
-- Table structure for uc_uvip_log
-- ----------------------------
DROP TABLE IF EXISTS `uc_uvip_log`;
CREATE TABLE `uc_uvip_log` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `userId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `source` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '来源',
  `levelId` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '会员等级ID',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户会员记录';

-- ----------------------------
-- Records of uc_uvip_log
-- ----------------------------
INSERT INTO `uc_uvip_log` VALUES ('b5ea049e-269d-480d-ac07-58317b56df69', '5aca5af8-0d88-4065-92ae-5ea5c477d52d', '22', '44d91749-3fd5-470f-a021-901b6e44f29b', '2017-05-19 17:35:11');

-- ----------------------------
-- Table structure for uc_uvip_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uc_uvip_privilege`;
CREATE TABLE `uc_uvip_privilege` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'PK',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '特权名称',
  `level` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '会员等级',
  `remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '特权说明',
  `status` tinyint(4) NOT NULL COMMENT '数据状态',
  `createTime` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of uc_uvip_privilege
-- ----------------------------
INSERT INTO `uc_uvip_privilege` VALUES ('41b4df64-3a78-4ff4-9473-e9357584aa2f', 'test02', '43', 'test02', '1', '2017-05-23 10:20:42', '2017-05-23 10:21:05');
INSERT INTO `uc_uvip_privilege` VALUES ('ca8522a7-f8d7-4403-a38b-dd60889ca033', 'test01', '43', 'test01', '1', '2017-05-23 10:04:32', '2017-05-23 10:17:05');
INSERT INTO `uc_uvip_privilege` VALUES ('cd30f56d-877f-470b-9ea2-2653c521de62', '', '', '', '1', '2017-05-23 09:30:53', '2017-05-23 09:30:53');
