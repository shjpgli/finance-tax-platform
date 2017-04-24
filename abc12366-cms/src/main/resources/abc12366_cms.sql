/*
Navicat MySQL Data Transfer

Source Server         : abc_expert
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : cszjcms

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-24 11:56:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_channel
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel`;
CREATE TABLE `cms_channel` (
  `channelId` varchar(64) NOT NULL,
  `modelId` varchar(64) NOT NULL COMMENT '模型ID',
  `siteId` varchar(64) NOT NULL COMMENT '站点ID',
  `parentId` varchar(64) DEFAULT NULL COMMENT '父栏目ID',
  `channelPath` varchar(30) DEFAULT NULL COMMENT '访问路径',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `isDisplay` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  PRIMARY KEY (`channelId`),
  KEY `fk_jc_channel_model` (`modelId`),
  KEY `fk_jc_channel_parent` (`parentId`),
  KEY `fk_jc_channel_site` (`siteId`),
  CONSTRAINT `fk_jc_channel_model` FOREIGN KEY (`modelId`) REFERENCES `cms_model` (`modelId`),
  CONSTRAINT `fk_jc_channel_parent` FOREIGN KEY (`parentId`) REFERENCES `cms_channel` (`channelId`),
  CONSTRAINT `fk_jc_channel_site` FOREIGN KEY (`siteId`) REFERENCES `cms_site` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目表';

-- ----------------------------
-- Records of cms_channel
-- ----------------------------
INSERT INTO `cms_channel` VALUES ('75', '1', '1', null, 'news', '10', '1');
INSERT INTO `cms_channel` VALUES ('76', '5', '1', null, 'pic', '10', '1');
INSERT INTO `cms_channel` VALUES ('77', '6', '1', null, 'video', '10', '1');
INSERT INTO `cms_channel` VALUES ('78', '4', '1', null, 'download', '10', '1');
INSERT INTO `cms_channel` VALUES ('79', '8', '1', null, 'job', '10', '1');
INSERT INTO `cms_channel` VALUES ('80', '2', '1', null, 'survey', '11', '1');
INSERT INTO `cms_channel` VALUES ('82', '2', '1', null, 'message', '10', '1');
INSERT INTO `cms_channel` VALUES ('90', '4', '1', '78', 'xtrj', '10', '1');
INSERT INTO `cms_channel` VALUES ('91', '4', '1', '78', 'mtzs', '10', '1');
INSERT INTO `cms_channel` VALUES ('92', '4', '1', '78', 'jptj', '9', '1');
INSERT INTO `cms_channel` VALUES ('93', '4', '1', '78', 'yxyl', '10', '1');

-- ----------------------------
-- Table structure for cms_channel_attr
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel_attr`;
CREATE TABLE `cms_channel_attr` (
  `channelId` varchar(64) NOT NULL,
  `attrName` varchar(30) NOT NULL COMMENT '名称',
  `attrValue` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_attr_channel` (`channelId`),
  CONSTRAINT `fk_jc_attr_channel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目扩展属性表';

-- ----------------------------
-- Records of cms_channel_attr
-- ----------------------------

-- ----------------------------
-- Table structure for cms_channel_count
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel_count`;
CREATE TABLE `cms_channel_count` (
  `channelId` varchar(64) NOT NULL,
  `views` int(11) NOT NULL DEFAULT '0' COMMENT '总访问数',
  `viewsMonth` int(11) NOT NULL DEFAULT '0' COMMENT '月访问数',
  `viewsWeek` int(11) NOT NULL DEFAULT '0' COMMENT '周访问数',
  `viewsDay` int(11) NOT NULL DEFAULT '0' COMMENT '日访问数',
  `contentCountTotal` int(11) NOT NULL DEFAULT '0' COMMENT '内容发布数',
  `contentCountDay` int(11) NOT NULL DEFAULT '0' COMMENT '内容今日发布数',
  `contentCountWeek` int(11) NOT NULL DEFAULT '0' COMMENT '内容本周发布数',
  `contentCountMonth` int(11) NOT NULL DEFAULT '0' COMMENT '内容本月发布数',
  `contentCountYear` int(11) NOT NULL DEFAULT '0' COMMENT '内容今年发布数',
  PRIMARY KEY (`channelId`),
  CONSTRAINT `fk_jc_count_channel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目访问量计数表';

-- ----------------------------
-- Records of cms_channel_count
-- ----------------------------
INSERT INTO `cms_channel_count` VALUES ('75', '443', '126', '126', '0', '24', '18', '18', '18', '24');
INSERT INTO `cms_channel_count` VALUES ('76', '203', '18', '17', '1', '11', '0', '0', '0', '11');
INSERT INTO `cms_channel_count` VALUES ('77', '38', '24', '24', '0', '15', '0', '0', '0', '15');
INSERT INTO `cms_channel_count` VALUES ('78', '25', '5', '5', '0', '14', '0', '0', '0', '14');
INSERT INTO `cms_channel_count` VALUES ('79', '49', '19', '19', '0', '10', '0', '0', '0', '10');
INSERT INTO `cms_channel_count` VALUES ('80', '33', '6', '6', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_channel_count` VALUES ('82', '6', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_channel_count` VALUES ('90', '12', '1', '1', '0', '4', '0', '0', '0', '4');
INSERT INTO `cms_channel_count` VALUES ('91', '2', '2', '2', '0', '8', '0', '0', '0', '8');
INSERT INTO `cms_channel_count` VALUES ('92', '12', '7', '7', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_channel_count` VALUES ('93', '2', '0', '0', '0', '2', '0', '0', '0', '2');

-- ----------------------------
-- Table structure for cms_channel_ext
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel_ext`;
CREATE TABLE `cms_channel_ext` (
  `channelId` varchar(64) NOT NULL,
  `channelName` varchar(100) NOT NULL COMMENT '名称',
  `isStaticChannel` char(1) NOT NULL DEFAULT '0' COMMENT '是否栏目静态化',
  `isStaticContent` char(1) NOT NULL DEFAULT '0' COMMENT '是否内容静态化',
  `pageSize` int(11) NOT NULL DEFAULT '20' COMMENT '每页多少条记录',
  `link` varchar(255) DEFAULT NULL COMMENT '外部链接',
  `tplChannel` varchar(100) DEFAULT NULL COMMENT '栏目页模板',
  `tplContent` varchar(100) DEFAULT NULL COMMENT '内容页模板',
  `titleImg` varchar(100) DEFAULT NULL COMMENT '缩略图',
  `contentImg` varchar(100) DEFAULT NULL COMMENT '内容图',
  `hasTitleImg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '内容是否有缩略图',
  `hasContentImg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '内容是否有内容图',
  `titleImgWidth` int(11) NOT NULL DEFAULT '139' COMMENT '内容标题图宽度',
  `titleImgHeight` int(11) NOT NULL DEFAULT '139' COMMENT '内容标题图高度',
  `contentImgWidth` int(11) NOT NULL DEFAULT '310' COMMENT '内容内容图宽度',
  `contentImgHeight` int(11) NOT NULL DEFAULT '310' COMMENT '内容内容图高度',
  `commentControl` int(11) NOT NULL DEFAULT '0' COMMENT '评论(0:匿名;1:会员一次;2:关闭,3会员多次)',
  `allowUpdown` tinyint(1) NOT NULL DEFAULT '1' COMMENT '顶踩(true:开放;false:关闭)',
  `isBlank` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否新窗口打开',
  `allowShare` tinyint(1) NOT NULL DEFAULT '0' COMMENT '分享(true:开放;false:关闭)',
  PRIMARY KEY (`channelId`),
  CONSTRAINT `fk_jc_ext_channel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目内容表';

-- ----------------------------
-- Records of cms_channel_ext
-- ----------------------------
INSERT INTO `cms_channel_ext` VALUES ('75', '新闻', '0', '0', '10', null, '/WEB-INF/t/cms/www/default/channel/news.html', null, null, null, '0', '0', '510', '288', '310', '310', '0', '1', '0', '1');
INSERT INTO `cms_channel_ext` VALUES ('76', '图库', '0', '0', '10', null, null, null, null, null, '0', '0', '510', '288', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('77', '视频', '0', '0', '10', null, null, null, null, null, '1', '0', '510', '288', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('78', '下载', '0', '0', '10', null, null, null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('79', '招聘', '0', '0', '10', null, null, null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('80', '网络调查', '0', '0', '10', null, '/WEB-INF/t/cms/www/default/alone/alone_survey.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('82', '留言板', '0', '0', '10', '/guestbook.jspx', null, null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('90', '系统软件', '0', '0', '10', null, '/WEB-INF/t/cms/www/default/channel/download_child.html', null, null, null, '1', '1', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('91', '媒体助手', '0', '0', '10', null, '/WEB-INF/t/cms/www/default/channel/download_child.html', null, null, null, '1', '1', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('92', '精品推荐', '0', '0', '10', null, '/WEB-INF/t/cms/www/default/channel/download_recommend.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '0');
INSERT INTO `cms_channel_ext` VALUES ('93', '游戏娱乐', '0', '0', '10', null, '/WEB-INF/t/cms/www/default/channel/download_child.html', null, null, null, '1', '1', '139', '139', '310', '310', '0', '1', '0', '0');

-- ----------------------------
-- Table structure for cms_channel_model
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel_model`;
CREATE TABLE `cms_channel_model` (
  `channelId` varchar(64) NOT NULL,
  `modelId` varchar(64) NOT NULL COMMENT '模型id',
  `tplContent` varchar(100) DEFAULT NULL COMMENT '内容模板',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  PRIMARY KEY (`channelId`,`priority`),
  KEY `fk_jc_model_channel_m` (`modelId`),
  CONSTRAINT `fk_jc_channel_model_c` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`),
  CONSTRAINT `fk_jc_model_channel_m` FOREIGN KEY (`modelId`) REFERENCES `cms_model` (`modelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目可选内容模型表';

-- ----------------------------
-- Records of cms_channel_model
-- ----------------------------
INSERT INTO `cms_channel_model` VALUES ('75', '1', '/WEB-INF/t/cms/www/default/content/news2.html', '0');
INSERT INTO `cms_channel_model` VALUES ('76', '5', '', '0');
INSERT INTO `cms_channel_model` VALUES ('77', '6', '', '0');
INSERT INTO `cms_channel_model` VALUES ('79', '8', '', '0');
INSERT INTO `cms_channel_model` VALUES ('90', '4', '', '0');
INSERT INTO `cms_channel_model` VALUES ('91', '4', '', '0');
INSERT INTO `cms_channel_model` VALUES ('92', '4', '', '0');
INSERT INTO `cms_channel_model` VALUES ('93', '4', '', '0');

-- ----------------------------
-- Table structure for cms_channel_user
-- ----------------------------
DROP TABLE IF EXISTS `cms_channel_user`;
CREATE TABLE `cms_channel_user` (
  `channelId` varchar(64) NOT NULL,
  `userId` varchar(64) NOT NULL,
  PRIMARY KEY (`channelId`,`userId`),
  KEY `fk_jc_channel_user` (`userId`),
  CONSTRAINT `fk_jc_user_channel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目用户关联表';

-- ----------------------------
-- Records of cms_channel_user
-- ----------------------------

-- ----------------------------
-- Table structure for cms_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_comment`;
CREATE TABLE `cms_comment` (
  `commentId` varchar(64) NOT NULL,
  `commentUserId` int(11) DEFAULT NULL COMMENT '评论用户ID',
  `replyUserId` int(11) DEFAULT NULL COMMENT '回复用户ID',
  `contentId` varchar(64) NOT NULL COMMENT '内容ID',
  `siteId` varchar(64) NOT NULL COMMENT '站点ID',
  `createTime` datetime NOT NULL COMMENT '评论时间',
  `replyTime` datetime DEFAULT NULL COMMENT '回复时间',
  `ups` smallint(6) NOT NULL DEFAULT '0' COMMENT '支持数',
  `downs` smallint(6) NOT NULL DEFAULT '0' COMMENT '反对数',
  `isRecommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `isChecked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否审核',
  `score` int(11) DEFAULT NULL COMMENT '评分',
  `parentId` int(11) DEFAULT NULL COMMENT '父级评论',
  `replyCount` int(11) DEFAULT '0' COMMENT '回复数',
  PRIMARY KEY (`commentId`),
  KEY `fk_jc_comment_content` (`contentId`),
  KEY `fk_jc_comment_reply` (`replyUserId`),
  KEY `fk_jc_comment_site` (`siteId`),
  KEY `fk_jc_comment_user` (`commentUserId`),
  KEY `reply_user_id` (`contentId`),
  CONSTRAINT `fk_jc_comment_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`),
  CONSTRAINT `fk_jc_comment_site` FOREIGN KEY (`siteId`) REFERENCES `cms_site` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS评论表';

-- ----------------------------
-- Records of cms_comment
-- ----------------------------
INSERT INTO `cms_comment` VALUES ('11', null, null, '30', '1', '2016-10-10 08:50:25', null, '0', '0', '1', '1', null, null, '0');
INSERT INTO `cms_comment` VALUES ('13', null, null, '39', '1', '2016-10-10 08:56:21', null, '0', '0', '1', '1', null, null, '0');
INSERT INTO `cms_comment` VALUES ('14', null, null, '130', '1', '2016-10-10 14:00:12', null, '0', '0', '1', '1', null, null, '0');
INSERT INTO `cms_comment` VALUES ('15', null, null, '132', '1', '2016-10-10 14:01:13', null, '0', '0', '1', '1', null, null, '0');

-- ----------------------------
-- Table structure for cms_comment_ext
-- ----------------------------
DROP TABLE IF EXISTS `cms_comment_ext`;
CREATE TABLE `cms_comment_ext` (
  `commentId` varchar(64) NOT NULL,
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `text` longtext COMMENT '评论内容',
  `reply` longtext COMMENT '回复内容',
  KEY `fk_jc_ext_comment` (`commentId`),
  CONSTRAINT `fk_jc_ext_comment` FOREIGN KEY (`commentId`) REFERENCES `cms_comment` (`commentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS评论扩展表';

-- ----------------------------
-- Records of cms_comment_ext
-- ----------------------------
INSERT INTO `cms_comment_ext` VALUES ('11', '117.41.152.66', '早就该这样了，不知道多少不法分子利用不实名手机号进行诈骗。', '');
INSERT INTO `cms_comment_ext` VALUES ('13', '117.41.152.66', '四川人民伤不起，忘国家加紧开展救援以及灾后重建工作。', '');
INSERT INTO `cms_comment_ext` VALUES ('14', '117.41.152.66', '保持定力：坚持走中国特色解决民族问题的正确道路', '');
INSERT INTO `cms_comment_ext` VALUES ('15', '117.41.152.66', '燃烧吧小宇宙，中国大妈征服世界，哈哈哈哈...', '');

-- ----------------------------
-- Table structure for cms_content
-- ----------------------------
DROP TABLE IF EXISTS `cms_content`;
CREATE TABLE `cms_content` (
  `contentId` varchar(64) NOT NULL,
  `channelId` varchar(64) NOT NULL COMMENT '栏目ID',
  `typeId` varchar(64) NOT NULL COMMENT '属性ID',
  `modelId` varchar(64) NOT NULL COMMENT '模型ID',
  `siteId` varchar(64) NOT NULL COMMENT '站点ID',
  `sortDate` datetime NOT NULL COMMENT '排序日期',
  `topLevel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '固顶级别',
  `hasTitleImg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有标题图',
  `isRecommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '状态(0:草稿;1:审核中;2:审核通过;3:回收站;4:投稿;5:归档)',
  `viewsDay` int(11) NOT NULL DEFAULT '0' COMMENT '日访问数',
  `commentsDay` smallint(6) NOT NULL DEFAULT '0' COMMENT '日评论数',
  `downloadsDay` smallint(6) NOT NULL DEFAULT '0' COMMENT '日下载数',
  `upsDay` smallint(6) NOT NULL DEFAULT '0' COMMENT '日顶数',
  `recommendLevel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '推荐级别',
  PRIMARY KEY (`contentId`),
  KEY `fk_jc_content_site` (`siteId`),
  KEY `fk_jc_content_type` (`typeId`),
  KEY `fk_jc_contentchannel` (`channelId`),
  KEY `fk_jc_content_model` (`modelId`),
  KEY `index_jc_content_top_level_sort` (`topLevel`,`sortDate`),
  KEY `index_jc_content_status` (`status`),
  KEY `index_jc_content_sort_date` (`sortDate`),
  KEY `index_jc_content_is_recommend` (`isRecommend`),
  KEY `index_jc_content_recommend_level` (`recommendLevel`),
  CONSTRAINT `fk_jc_content_model` FOREIGN KEY (`modelId`) REFERENCES `cms_model` (`modelId`),
  CONSTRAINT `fk_jc_content_site` FOREIGN KEY (`siteId`) REFERENCES `cms_site` (`siteId`),
  CONSTRAINT `fk_jc_content_type` FOREIGN KEY (`typeId`) REFERENCES `cms_content_type` (`typeId`),
  CONSTRAINT `fk_jc_contentchannel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容表';

-- ----------------------------
-- Records of cms_content
-- ----------------------------
INSERT INTO `cms_content` VALUES ('100', '93', '2', '4', '1', '2016-10-10 11:09:06', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('101', '91', '2', '4', '1', '2016-10-10 11:16:31', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('102', '90', '1', '4', '1', '2016-10-10 11:19:18', '0', '1', '1', '2', '0', '0', '39', '0', '1');
INSERT INTO `cms_content` VALUES ('103', '90', '1', '4', '1', '2016-10-10 11:21:37', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('104', '93', '1', '4', '1', '2016-10-10 11:27:17', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('105', '91', '1', '4', '1', '2016-10-10 11:28:52', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('106', '91', '1', '4', '1', '2016-10-10 11:30:54', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('107', '91', '1', '4', '1', '2016-10-10 11:32:35', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('108', '91', '1', '4', '1', '2016-10-10 11:34:05', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('109', '91', '1', '4', '1', '2016-10-10 11:35:26', '0', '1', '1', '2', '0', '0', '21', '0', '1');
INSERT INTO `cms_content` VALUES ('11', '75', '1', '1', '1', '2016-07-15 16:23:32', '0', '0', '0', '2', '0', '0', '0', '0', '0');
INSERT INTO `cms_content` VALUES ('110', '91', '1', '4', '1', '2016-10-10 11:36:32', '0', '1', '1', '2', '1', '0', '2', '0', '1');
INSERT INTO `cms_content` VALUES ('111', '75', '2', '1', '1', '2016-10-10 11:45:47', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('112', '75', '2', '1', '1', '2016-10-10 11:48:08', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('113', '75', '4', '1', '1', '2016-10-10 11:50:03', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('114', '76', '2', '5', '1', '2016-10-10 13:11:12', '0', '0', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('115', '76', '2', '5', '1', '2016-10-10 13:15:37', '0', '0', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('116', '76', '2', '5', '1', '2016-10-10 13:19:22', '0', '0', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('117', '76', '2', '5', '1', '2016-10-10 13:22:07', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('118', '76', '2', '5', '1', '2016-10-10 13:23:50', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('119', '76', '2', '5', '1', '2016-10-10 13:26:38', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('12', '75', '1', '1', '1', '2016-07-15 17:22:47', '0', '0', '0', '2', '0', '0', '0', '0', '0');
INSERT INTO `cms_content` VALUES ('120', '76', '2', '5', '1', '2016-10-10 13:35:17', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('121', '77', '2', '6', '1', '2016-10-10 13:38:11', '1', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('122', '77', '2', '6', '1', '2016-10-10 13:40:05', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('123', '77', '2', '6', '1', '2016-10-10 13:41:03', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('124', '77', '2', '6', '1', '2016-10-10 13:42:48', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('125', '77', '2', '6', '1', '2016-10-10 13:44:47', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('126', '77', '2', '6', '1', '2016-10-10 13:46:01', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('127', '77', '2', '6', '1', '2016-10-10 13:46:26', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('128', '77', '2', '6', '1', '2016-10-10 13:46:52', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('129', '77', '2', '6', '1', '2016-10-10 13:47:24', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('13', '76', '2', '5', '1', '2016-07-19 14:12:48', '1', '0', '1', '2', '0', '0', '0', '0', '0');
INSERT INTO `cms_content` VALUES ('130', '75', '1', '1', '1', '2016-10-10 13:51:07', '1', '0', '0', '2', '1', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('131', '75', '1', '1', '1', '2016-10-10 13:54:05', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('132', '75', '1', '1', '1', '2016-10-10 13:55:46', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('14', '75', '2', '1', '1', '2016-07-19 14:26:30', '0', '0', '0', '2', '0', '0', '0', '0', '0');
INSERT INTO `cms_content` VALUES ('140', '79', '1', '8', '1', '2016-10-10 14:27:44', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('141', '76', '2', '5', '1', '2016-10-11 09:44:23', '0', '0', '0', '0', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('2', '76', '2', '5', '1', '2016-07-15 14:47:24', '0', '0', '0', '2', '0', '0', '0', '0', '0');
INSERT INTO `cms_content` VALUES ('23', '75', '1', '1', '1', '2016-09-13 17:16:04', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('27', '75', '2', '1', '1', '2016-09-22 16:30:13', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('28', '75', '2', '1', '1', '2016-09-22 16:56:46', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('29', '75', '1', '1', '1', '2016-09-23 09:07:33', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('30', '75', '1', '1', '1', '2016-09-23 09:09:41', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('32', '75', '2', '1', '1', '2016-09-23 09:19:38', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('33', '75', '2', '1', '1', '2016-09-23 09:23:39', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('36', '75', '2', '1', '1', '2016-09-23 09:40:21', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('37', '75', '2', '1', '1', '2016-09-23 09:43:34', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('39', '75', '2', '1', '1', '2016-09-23 09:54:17', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('43', '79', '1', '8', '1', '2016-09-26 09:33:15', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('44', '79', '1', '8', '1', '2016-09-26 09:34:35', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('45', '79', '1', '8', '1', '2016-09-26 09:38:39', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('46', '79', '1', '8', '1', '2016-09-26 09:45:02', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('47', '79', '1', '8', '1', '2016-09-26 09:47:54', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('48', '79', '1', '8', '1', '2016-09-26 09:50:04', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('49', '79', '1', '8', '1', '2016-09-26 09:51:13', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('50', '79', '1', '8', '1', '2016-09-26 09:54:56', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('51', '79', '1', '8', '1', '2016-09-26 09:56:01', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('53', '77', '2', '6', '1', '2016-09-26 10:19:13', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('55', '77', '2', '6', '1', '2016-09-26 10:20:11', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('57', '77', '2', '6', '1', '2016-09-26 10:21:19', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('59', '77', '2', '6', '1', '2016-09-26 10:22:24', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('61', '77', '2', '6', '1', '2016-09-26 10:29:06', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('63', '77', '2', '6', '1', '2016-09-26 10:30:05', '0', '0', '0', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('69', '90', '1', '4', '1', '2016-09-26 16:37:28', '0', '1', '1', '2', '0', '0', '1', '0', '1');
INSERT INTO `cms_content` VALUES ('7', '76', '2', '5', '1', '2016-07-15 15:33:26', '0', '0', '1', '2', '0', '0', '0', '0', '0');
INSERT INTO `cms_content` VALUES ('71', '91', '1', '4', '1', '2016-09-27 09:47:27', '0', '1', '1', '2', '0', '0', '0', '0', '1');
INSERT INTO `cms_content` VALUES ('72', '90', '2', '4', '1', '2016-09-27 10:18:46', '0', '1', '1', '2', '0', '0', '0', '0', '1');

-- ----------------------------
-- Table structure for cms_contenttag
-- ----------------------------
DROP TABLE IF EXISTS `cms_contenttag`;
CREATE TABLE `cms_contenttag` (
  `contentId` varchar(64) NOT NULL,
  `tagId` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL,
  KEY `fk_jc_content_tag` (`tagId`),
  KEY `fk_jc_tag_content` (`contentId`),
  CONSTRAINT `fk_jc_content_tag` FOREIGN KEY (`tagId`) REFERENCES `cms_content_tag` (`tagId`),
  CONSTRAINT `fk_jc_tag_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容标签关联表';

-- ----------------------------
-- Records of cms_contenttag
-- ----------------------------
INSERT INTO `cms_contenttag` VALUES ('2', '8', '0');
INSERT INTO `cms_contenttag` VALUES ('2', '9', '1');
INSERT INTO `cms_contenttag` VALUES ('2', '10', '2');
INSERT INTO `cms_contenttag` VALUES ('2', '11', '3');
INSERT INTO `cms_contenttag` VALUES ('2', '12', '4');
INSERT INTO `cms_contenttag` VALUES ('2', '13', '5');
INSERT INTO `cms_contenttag` VALUES ('2', '14', '6');
INSERT INTO `cms_contenttag` VALUES ('2', '15', '7');
INSERT INTO `cms_contenttag` VALUES ('2', '16', '8');
INSERT INTO `cms_contenttag` VALUES ('2', '17', '9');
INSERT INTO `cms_contenttag` VALUES ('2', '18', '10');
INSERT INTO `cms_contenttag` VALUES ('2', '19', '11');
INSERT INTO `cms_contenttag` VALUES ('7', '61', '0');
INSERT INTO `cms_contenttag` VALUES ('7', '62', '1');
INSERT INTO `cms_contenttag` VALUES ('7', '3', '2');
INSERT INTO `cms_contenttag` VALUES ('7', '63', '3');
INSERT INTO `cms_contenttag` VALUES ('7', '64', '4');
INSERT INTO `cms_contenttag` VALUES ('7', '65', '5');
INSERT INTO `cms_contenttag` VALUES ('7', '66', '6');
INSERT INTO `cms_contenttag` VALUES ('7', '67', '7');
INSERT INTO `cms_contenttag` VALUES ('7', '68', '8');
INSERT INTO `cms_contenttag` VALUES ('12', '102', '0');
INSERT INTO `cms_contenttag` VALUES ('12', '8', '1');
INSERT INTO `cms_contenttag` VALUES ('12', '9', '2');
INSERT INTO `cms_contenttag` VALUES ('12', '103', '3');
INSERT INTO `cms_contenttag` VALUES ('12', '104', '4');
INSERT INTO `cms_contenttag` VALUES ('12', '105', '5');
INSERT INTO `cms_contenttag` VALUES ('12', '107', '6');
INSERT INTO `cms_contenttag` VALUES ('12', '108', '7');
INSERT INTO `cms_contenttag` VALUES ('12', '109', '8');
INSERT INTO `cms_contenttag` VALUES ('12', '15', '9');
INSERT INTO `cms_contenttag` VALUES ('12', '110', '10');
INSERT INTO `cms_contenttag` VALUES ('13', '111', '0');
INSERT INTO `cms_contenttag` VALUES ('13', '112', '1');
INSERT INTO `cms_contenttag` VALUES ('13', '113', '2');
INSERT INTO `cms_contenttag` VALUES ('13', '71', '3');
INSERT INTO `cms_contenttag` VALUES ('13', '114', '4');
INSERT INTO `cms_contenttag` VALUES ('13', '115', '5');
INSERT INTO `cms_contenttag` VALUES ('13', '116', '6');
INSERT INTO `cms_contenttag` VALUES ('13', '117', '7');
INSERT INTO `cms_contenttag` VALUES ('14', '102', '0');
INSERT INTO `cms_contenttag` VALUES ('14', '118', '1');
INSERT INTO `cms_contenttag` VALUES ('14', '119', '2');
INSERT INTO `cms_contenttag` VALUES ('14', '120', '3');
INSERT INTO `cms_contenttag` VALUES ('14', '121', '4');
INSERT INTO `cms_contenttag` VALUES ('14', '122', '5');
INSERT INTO `cms_contenttag` VALUES ('14', '123', '6');
INSERT INTO `cms_contenttag` VALUES ('14', '124', '7');
INSERT INTO `cms_contenttag` VALUES ('23', '139', '0');
INSERT INTO `cms_contenttag` VALUES ('23', '140', '1');
INSERT INTO `cms_contenttag` VALUES ('23', '141', '2');
INSERT INTO `cms_contenttag` VALUES ('23', '142', '3');
INSERT INTO `cms_contenttag` VALUES ('23', '143', '4');
INSERT INTO `cms_contenttag` VALUES ('23', '144', '5');
INSERT INTO `cms_contenttag` VALUES ('23', '145', '6');
INSERT INTO `cms_contenttag` VALUES ('27', '149', '0');
INSERT INTO `cms_contenttag` VALUES ('27', '150', '1');
INSERT INTO `cms_contenttag` VALUES ('27', '102', '2');
INSERT INTO `cms_contenttag` VALUES ('27', '151', '3');
INSERT INTO `cms_contenttag` VALUES ('27', '152', '4');
INSERT INTO `cms_contenttag` VALUES ('27', '153', '5');
INSERT INTO `cms_contenttag` VALUES ('27', '154', '6');
INSERT INTO `cms_contenttag` VALUES ('28', '155', '0');
INSERT INTO `cms_contenttag` VALUES ('28', '156', '1');
INSERT INTO `cms_contenttag` VALUES ('28', '157', '2');
INSERT INTO `cms_contenttag` VALUES ('28', '158', '3');
INSERT INTO `cms_contenttag` VALUES ('28', '159', '4');
INSERT INTO `cms_contenttag` VALUES ('28', '160', '5');
INSERT INTO `cms_contenttag` VALUES ('28', '161', '6');
INSERT INTO `cms_contenttag` VALUES ('28', '162', '7');
INSERT INTO `cms_contenttag` VALUES ('28', '163', '8');
INSERT INTO `cms_contenttag` VALUES ('29', '164', '0');
INSERT INTO `cms_contenttag` VALUES ('29', '165', '1');
INSERT INTO `cms_contenttag` VALUES ('29', '166', '2');
INSERT INTO `cms_contenttag` VALUES ('29', '167', '3');
INSERT INTO `cms_contenttag` VALUES ('29', '168', '4');
INSERT INTO `cms_contenttag` VALUES ('29', '169', '5');
INSERT INTO `cms_contenttag` VALUES ('29', '170', '6');
INSERT INTO `cms_contenttag` VALUES ('29', '171', '7');
INSERT INTO `cms_contenttag` VALUES ('29', '172', '8');
INSERT INTO `cms_contenttag` VALUES ('29', '173', '9');
INSERT INTO `cms_contenttag` VALUES ('29', '174', '10');
INSERT INTO `cms_contenttag` VALUES ('29', '175', '11');
INSERT INTO `cms_contenttag` VALUES ('30', '176', '0');
INSERT INTO `cms_contenttag` VALUES ('30', '177', '1');
INSERT INTO `cms_contenttag` VALUES ('30', '178', '2');
INSERT INTO `cms_contenttag` VALUES ('30', '179', '3');
INSERT INTO `cms_contenttag` VALUES ('30', '180', '4');
INSERT INTO `cms_contenttag` VALUES ('30', '181', '5');
INSERT INTO `cms_contenttag` VALUES ('30', '182', '6');
INSERT INTO `cms_contenttag` VALUES ('30', '183', '7');
INSERT INTO `cms_contenttag` VALUES ('30', '184', '8');
INSERT INTO `cms_contenttag` VALUES ('30', '185', '9');
INSERT INTO `cms_contenttag` VALUES ('30', '186', '10');
INSERT INTO `cms_contenttag` VALUES ('30', '187', '11');
INSERT INTO `cms_contenttag` VALUES ('30', '188', '12');
INSERT INTO `cms_contenttag` VALUES ('30', '189', '13');
INSERT INTO `cms_contenttag` VALUES ('33', '199', '0');
INSERT INTO `cms_contenttag` VALUES ('33', '200', '1');
INSERT INTO `cms_contenttag` VALUES ('33', '201', '2');
INSERT INTO `cms_contenttag` VALUES ('33', '24', '3');
INSERT INTO `cms_contenttag` VALUES ('33', '202', '4');
INSERT INTO `cms_contenttag` VALUES ('33', '203', '5');
INSERT INTO `cms_contenttag` VALUES ('33', '204', '6');
INSERT INTO `cms_contenttag` VALUES ('33', '205', '7');
INSERT INTO `cms_contenttag` VALUES ('33', '206', '8');
INSERT INTO `cms_contenttag` VALUES ('33', '207', '9');
INSERT INTO `cms_contenttag` VALUES ('33', '208', '10');
INSERT INTO `cms_contenttag` VALUES ('33', '209', '11');
INSERT INTO `cms_contenttag` VALUES ('33', '210', '12');
INSERT INTO `cms_contenttag` VALUES ('36', '230', '0');
INSERT INTO `cms_contenttag` VALUES ('36', '231', '1');
INSERT INTO `cms_contenttag` VALUES ('36', '232', '2');
INSERT INTO `cms_contenttag` VALUES ('36', '233', '3');
INSERT INTO `cms_contenttag` VALUES ('36', '234', '4');
INSERT INTO `cms_contenttag` VALUES ('36', '235', '5');
INSERT INTO `cms_contenttag` VALUES ('36', '236', '6');
INSERT INTO `cms_contenttag` VALUES ('36', '235', '7');
INSERT INTO `cms_contenttag` VALUES ('36', '237', '8');
INSERT INTO `cms_contenttag` VALUES ('36', '238', '9');
INSERT INTO `cms_contenttag` VALUES ('36', '239', '10');
INSERT INTO `cms_contenttag` VALUES ('36', '240', '11');
INSERT INTO `cms_contenttag` VALUES ('36', '241', '12');
INSERT INTO `cms_contenttag` VALUES ('36', '242', '13');
INSERT INTO `cms_contenttag` VALUES ('36', '243', '14');
INSERT INTO `cms_contenttag` VALUES ('36', '244', '15');
INSERT INTO `cms_contenttag` VALUES ('36', '245', '16');
INSERT INTO `cms_contenttag` VALUES ('36', '246', '17');
INSERT INTO `cms_contenttag` VALUES ('37', '247', '0');
INSERT INTO `cms_contenttag` VALUES ('37', '248', '1');
INSERT INTO `cms_contenttag` VALUES ('37', '249', '2');
INSERT INTO `cms_contenttag` VALUES ('37', '250', '3');
INSERT INTO `cms_contenttag` VALUES ('37', '251', '4');
INSERT INTO `cms_contenttag` VALUES ('37', '252', '5');
INSERT INTO `cms_contenttag` VALUES ('37', '253', '6');
INSERT INTO `cms_contenttag` VALUES ('37', '254', '7');
INSERT INTO `cms_contenttag` VALUES ('37', '255', '8');
INSERT INTO `cms_contenttag` VALUES ('37', '256', '9');
INSERT INTO `cms_contenttag` VALUES ('37', '257', '10');
INSERT INTO `cms_contenttag` VALUES ('37', '258', '11');
INSERT INTO `cms_contenttag` VALUES ('39', '42', '0');
INSERT INTO `cms_contenttag` VALUES ('39', '271', '1');
INSERT INTO `cms_contenttag` VALUES ('39', '10', '2');
INSERT INTO `cms_contenttag` VALUES ('39', '272', '3');
INSERT INTO `cms_contenttag` VALUES ('39', '273', '4');
INSERT INTO `cms_contenttag` VALUES ('39', '274', '5');
INSERT INTO `cms_contenttag` VALUES ('39', '275', '6');
INSERT INTO `cms_contenttag` VALUES ('39', '276', '7');
INSERT INTO `cms_contenttag` VALUES ('43', '286', '0');
INSERT INTO `cms_contenttag` VALUES ('43', '287', '1');
INSERT INTO `cms_contenttag` VALUES ('43', '288', '2');
INSERT INTO `cms_contenttag` VALUES ('44', '289', '0');
INSERT INTO `cms_contenttag` VALUES ('44', '290', '1');
INSERT INTO `cms_contenttag` VALUES ('44', '291', '2');
INSERT INTO `cms_contenttag` VALUES ('45', '292', '0');
INSERT INTO `cms_contenttag` VALUES ('45', '293', '1');
INSERT INTO `cms_contenttag` VALUES ('46', '436', '0');
INSERT INTO `cms_contenttag` VALUES ('47', '436', '0');
INSERT INTO `cms_contenttag` VALUES ('47', '437', '1');
INSERT INTO `cms_contenttag` VALUES ('47', '298', '2');
INSERT INTO `cms_contenttag` VALUES ('47', '438', '3');
INSERT INTO `cms_contenttag` VALUES ('48', '436', '0');
INSERT INTO `cms_contenttag` VALUES ('48', '437', '1');
INSERT INTO `cms_contenttag` VALUES ('48', '298', '2');
INSERT INTO `cms_contenttag` VALUES ('49', '436', '0');
INSERT INTO `cms_contenttag` VALUES ('49', '298', '1');
INSERT INTO `cms_contenttag` VALUES ('49', '439', '2');
INSERT INTO `cms_contenttag` VALUES ('50', '436', '0');
INSERT INTO `cms_contenttag` VALUES ('50', '437', '1');
INSERT INTO `cms_contenttag` VALUES ('51', '298', '0');
INSERT INTO `cms_contenttag` VALUES ('51', '438', '1');
INSERT INTO `cms_contenttag` VALUES ('53', '322', '0');
INSERT INTO `cms_contenttag` VALUES ('53', '323', '1');
INSERT INTO `cms_contenttag` VALUES ('53', '324', '2');
INSERT INTO `cms_contenttag` VALUES ('53', '325', '3');
INSERT INTO `cms_contenttag` VALUES ('53', '326', '4');
INSERT INTO `cms_contenttag` VALUES ('53', '327', '5');
INSERT INTO `cms_contenttag` VALUES ('53', '328', '6');
INSERT INTO `cms_contenttag` VALUES ('53', '329', '7');
INSERT INTO `cms_contenttag` VALUES ('55', '340', '0');
INSERT INTO `cms_contenttag` VALUES ('55', '269', '1');
INSERT INTO `cms_contenttag` VALUES ('55', '341', '2');
INSERT INTO `cms_contenttag` VALUES ('55', '342', '3');
INSERT INTO `cms_contenttag` VALUES ('57', '351', '0');
INSERT INTO `cms_contenttag` VALUES ('57', '352', '1');
INSERT INTO `cms_contenttag` VALUES ('57', '353', '2');
INSERT INTO `cms_contenttag` VALUES ('57', '354', '3');
INSERT INTO `cms_contenttag` VALUES ('57', '355', '4');
INSERT INTO `cms_contenttag` VALUES ('57', '59', '5');
INSERT INTO `cms_contenttag` VALUES ('57', '356', '6');
INSERT INTO `cms_contenttag` VALUES ('57', '357', '7');
INSERT INTO `cms_contenttag` VALUES ('57', '358', '8');
INSERT INTO `cms_contenttag` VALUES ('59', '365', '0');
INSERT INTO `cms_contenttag` VALUES ('59', '366', '1');
INSERT INTO `cms_contenttag` VALUES ('59', '367', '2');
INSERT INTO `cms_contenttag` VALUES ('59', '368', '3');
INSERT INTO `cms_contenttag` VALUES ('59', '369', '4');
INSERT INTO `cms_contenttag` VALUES ('59', '370', '5');
INSERT INTO `cms_contenttag` VALUES ('61', '375', '0');
INSERT INTO `cms_contenttag` VALUES ('61', '376', '1');
INSERT INTO `cms_contenttag` VALUES ('61', '377', '2');
INSERT INTO `cms_contenttag` VALUES ('61', '378', '3');
INSERT INTO `cms_contenttag` VALUES ('61', '379', '4');
INSERT INTO `cms_contenttag` VALUES ('63', '388', '0');
INSERT INTO `cms_contenttag` VALUES ('63', '21', '1');
INSERT INTO `cms_contenttag` VALUES ('63', '389', '2');
INSERT INTO `cms_contenttag` VALUES ('63', '390', '3');
INSERT INTO `cms_contenttag` VALUES ('63', '391', '4');
INSERT INTO `cms_contenttag` VALUES ('63', '392', '5');
INSERT INTO `cms_contenttag` VALUES ('63', '393', '6');
INSERT INTO `cms_contenttag` VALUES ('63', '394', '7');
INSERT INTO `cms_contenttag` VALUES ('63', '395', '8');
INSERT INTO `cms_contenttag` VALUES ('63', '396', '9');
INSERT INTO `cms_contenttag` VALUES ('48', '438', '3');
INSERT INTO `cms_contenttag` VALUES ('48', '439', '4');
INSERT INTO `cms_contenttag` VALUES ('47', '439', '4');
INSERT INTO `cms_contenttag` VALUES ('46', '437', '1');
INSERT INTO `cms_contenttag` VALUES ('46', '298', '2');
INSERT INTO `cms_contenttag` VALUES ('46', '438', '3');
INSERT INTO `cms_contenttag` VALUES ('46', '439', '4');
INSERT INTO `cms_contenttag` VALUES ('111', '500', '0');
INSERT INTO `cms_contenttag` VALUES ('111', '212', '1');
INSERT INTO `cms_contenttag` VALUES ('111', '501', '2');
INSERT INTO `cms_contenttag` VALUES ('111', '502', '3');
INSERT INTO `cms_contenttag` VALUES ('111', '503', '4');
INSERT INTO `cms_contenttag` VALUES ('111', '212', '5');
INSERT INTO `cms_contenttag` VALUES ('111', '504', '6');
INSERT INTO `cms_contenttag` VALUES ('111', '505', '7');
INSERT INTO `cms_contenttag` VALUES ('111', '506', '8');
INSERT INTO `cms_contenttag` VALUES ('111', '507', '9');
INSERT INTO `cms_contenttag` VALUES ('111', '508', '10');
INSERT INTO `cms_contenttag` VALUES ('112', '509', '0');
INSERT INTO `cms_contenttag` VALUES ('112', '510', '1');
INSERT INTO `cms_contenttag` VALUES ('112', '511', '2');
INSERT INTO `cms_contenttag` VALUES ('112', '512', '3');
INSERT INTO `cms_contenttag` VALUES ('112', '513', '4');
INSERT INTO `cms_contenttag` VALUES ('112', '514', '5');
INSERT INTO `cms_contenttag` VALUES ('112', '515', '6');
INSERT INTO `cms_contenttag` VALUES ('112', '516', '7');
INSERT INTO `cms_contenttag` VALUES ('112', '517', '8');
INSERT INTO `cms_contenttag` VALUES ('112', '518', '9');
INSERT INTO `cms_contenttag` VALUES ('113', '519', '0');
INSERT INTO `cms_contenttag` VALUES ('113', '520', '1');
INSERT INTO `cms_contenttag` VALUES ('113', '521', '2');
INSERT INTO `cms_contenttag` VALUES ('113', '522', '3');
INSERT INTO `cms_contenttag` VALUES ('113', '523', '4');
INSERT INTO `cms_contenttag` VALUES ('113', '524', '5');
INSERT INTO `cms_contenttag` VALUES ('113', '525', '6');
INSERT INTO `cms_contenttag` VALUES ('113', '526', '7');
INSERT INTO `cms_contenttag` VALUES ('113', '527', '8');
INSERT INTO `cms_contenttag` VALUES ('114', '501', '0');
INSERT INTO `cms_contenttag` VALUES ('114', '528', '1');
INSERT INTO `cms_contenttag` VALUES ('114', '529', '2');
INSERT INTO `cms_contenttag` VALUES ('114', '530', '3');
INSERT INTO `cms_contenttag` VALUES ('114', '531', '4');
INSERT INTO `cms_contenttag` VALUES ('114', '532', '5');
INSERT INTO `cms_contenttag` VALUES ('114', '533', '6');
INSERT INTO `cms_contenttag` VALUES ('114', '534', '7');
INSERT INTO `cms_contenttag` VALUES ('114', '535', '8');
INSERT INTO `cms_contenttag` VALUES ('114', '536', '9');
INSERT INTO `cms_contenttag` VALUES ('114', '537', '10');
INSERT INTO `cms_contenttag` VALUES ('115', '42', '0');
INSERT INTO `cms_contenttag` VALUES ('115', '538', '1');
INSERT INTO `cms_contenttag` VALUES ('115', '537', '2');
INSERT INTO `cms_contenttag` VALUES ('115', '539', '3');
INSERT INTO `cms_contenttag` VALUES ('115', '540', '4');
INSERT INTO `cms_contenttag` VALUES ('115', '541', '5');
INSERT INTO `cms_contenttag` VALUES ('115', '542', '6');
INSERT INTO `cms_contenttag` VALUES ('115', '543', '7');
INSERT INTO `cms_contenttag` VALUES ('115', '544', '8');
INSERT INTO `cms_contenttag` VALUES ('116', '545', '0');
INSERT INTO `cms_contenttag` VALUES ('116', '546', '1');
INSERT INTO `cms_contenttag` VALUES ('116', '547', '2');
INSERT INTO `cms_contenttag` VALUES ('116', '548', '3');
INSERT INTO `cms_contenttag` VALUES ('116', '549', '4');
INSERT INTO `cms_contenttag` VALUES ('116', '550', '5');
INSERT INTO `cms_contenttag` VALUES ('116', '551', '6');
INSERT INTO `cms_contenttag` VALUES ('116', '552', '7');
INSERT INTO `cms_contenttag` VALUES ('116', '553', '8');
INSERT INTO `cms_contenttag` VALUES ('116', '59', '9');
INSERT INTO `cms_contenttag` VALUES ('116', '554', '10');
INSERT INTO `cms_contenttag` VALUES ('117', '555', '0');
INSERT INTO `cms_contenttag` VALUES ('117', '556', '1');
INSERT INTO `cms_contenttag` VALUES ('117', '3', '2');
INSERT INTO `cms_contenttag` VALUES ('117', '557', '3');
INSERT INTO `cms_contenttag` VALUES ('117', '558', '4');
INSERT INTO `cms_contenttag` VALUES ('117', '559', '5');
INSERT INTO `cms_contenttag` VALUES ('117', '560', '6');
INSERT INTO `cms_contenttag` VALUES ('118', '561', '0');
INSERT INTO `cms_contenttag` VALUES ('118', '562', '1');
INSERT INTO `cms_contenttag` VALUES ('118', '563', '2');
INSERT INTO `cms_contenttag` VALUES ('118', '564', '3');
INSERT INTO `cms_contenttag` VALUES ('118', '565', '4');
INSERT INTO `cms_contenttag` VALUES ('118', '566', '5');
INSERT INTO `cms_contenttag` VALUES ('118', '567', '6');
INSERT INTO `cms_contenttag` VALUES ('118', '568', '7');
INSERT INTO `cms_contenttag` VALUES ('118', '569', '8');
INSERT INTO `cms_contenttag` VALUES ('118', '570', '9');
INSERT INTO `cms_contenttag` VALUES ('118', '269', '10');
INSERT INTO `cms_contenttag` VALUES ('118', '571', '11');
INSERT INTO `cms_contenttag` VALUES ('119', '572', '0');
INSERT INTO `cms_contenttag` VALUES ('119', '21', '1');
INSERT INTO `cms_contenttag` VALUES ('119', '573', '2');
INSERT INTO `cms_contenttag` VALUES ('119', '574', '3');
INSERT INTO `cms_contenttag` VALUES ('119', '24', '4');
INSERT INTO `cms_contenttag` VALUES ('119', '575', '5');
INSERT INTO `cms_contenttag` VALUES ('119', '576', '6');
INSERT INTO `cms_contenttag` VALUES ('119', '577', '7');
INSERT INTO `cms_contenttag` VALUES ('119', '578', '8');
INSERT INTO `cms_contenttag` VALUES ('119', '579', '9');
INSERT INTO `cms_contenttag` VALUES ('119', '580', '10');
INSERT INTO `cms_contenttag` VALUES ('120', '581', '0');
INSERT INTO `cms_contenttag` VALUES ('120', '582', '1');
INSERT INTO `cms_contenttag` VALUES ('120', '583', '2');
INSERT INTO `cms_contenttag` VALUES ('120', '584', '3');
INSERT INTO `cms_contenttag` VALUES ('120', '585', '4');
INSERT INTO `cms_contenttag` VALUES ('120', '586', '5');
INSERT INTO `cms_contenttag` VALUES ('120', '587', '6');
INSERT INTO `cms_contenttag` VALUES ('120', '588', '7');
INSERT INTO `cms_contenttag` VALUES ('120', '589', '8');
INSERT INTO `cms_contenttag` VALUES ('121', '590', '0');
INSERT INTO `cms_contenttag` VALUES ('121', '398', '1');
INSERT INTO `cms_contenttag` VALUES ('121', '399', '2');
INSERT INTO `cms_contenttag` VALUES ('121', '591', '3');
INSERT INTO `cms_contenttag` VALUES ('121', '592', '4');
INSERT INTO `cms_contenttag` VALUES ('121', '593', '5');
INSERT INTO `cms_contenttag` VALUES ('121', '594', '6');
INSERT INTO `cms_contenttag` VALUES ('121', '398', '7');
INSERT INTO `cms_contenttag` VALUES ('121', '595', '8');
INSERT INTO `cms_contenttag` VALUES ('121', '596', '9');
INSERT INTO `cms_contenttag` VALUES ('121', '597', '10');
INSERT INTO `cms_contenttag` VALUES ('121', '598', '11');
INSERT INTO `cms_contenttag` VALUES ('121', '399', '12');
INSERT INTO `cms_contenttag` VALUES ('121', '599', '13');
INSERT INTO `cms_contenttag` VALUES ('122', '600', '0');
INSERT INTO `cms_contenttag` VALUES ('122', '601', '1');
INSERT INTO `cms_contenttag` VALUES ('122', '602', '2');
INSERT INTO `cms_contenttag` VALUES ('122', '603', '3');
INSERT INTO `cms_contenttag` VALUES ('122', '604', '4');
INSERT INTO `cms_contenttag` VALUES ('122', '414', '5');
INSERT INTO `cms_contenttag` VALUES ('122', '414', '6');
INSERT INTO `cms_contenttag` VALUES ('122', '567', '7');
INSERT INTO `cms_contenttag` VALUES ('122', '605', '8');
INSERT INTO `cms_contenttag` VALUES ('122', '606', '9');
INSERT INTO `cms_contenttag` VALUES ('122', '607', '10');
INSERT INTO `cms_contenttag` VALUES ('122', '608', '11');
INSERT INTO `cms_contenttag` VALUES ('122', '609', '12');
INSERT INTO `cms_contenttag` VALUES ('123', '610', '0');
INSERT INTO `cms_contenttag` VALUES ('123', '611', '1');
INSERT INTO `cms_contenttag` VALUES ('123', '612', '2');
INSERT INTO `cms_contenttag` VALUES ('123', '613', '3');
INSERT INTO `cms_contenttag` VALUES ('123', '614', '4');
INSERT INTO `cms_contenttag` VALUES ('123', '615', '5');
INSERT INTO `cms_contenttag` VALUES ('123', '616', '6');
INSERT INTO `cms_contenttag` VALUES ('123', '617', '7');
INSERT INTO `cms_contenttag` VALUES ('123', '618', '8');
INSERT INTO `cms_contenttag` VALUES ('123', '619', '9');
INSERT INTO `cms_contenttag` VALUES ('123', '620', '10');
INSERT INTO `cms_contenttag` VALUES ('123', '621', '11');
INSERT INTO `cms_contenttag` VALUES ('123', '622', '12');
INSERT INTO `cms_contenttag` VALUES ('123', '623', '13');
INSERT INTO `cms_contenttag` VALUES ('124', '519', '0');
INSERT INTO `cms_contenttag` VALUES ('124', '624', '1');
INSERT INTO `cms_contenttag` VALUES ('124', '155', '2');
INSERT INTO `cms_contenttag` VALUES ('124', '625', '3');
INSERT INTO `cms_contenttag` VALUES ('124', '626', '4');
INSERT INTO `cms_contenttag` VALUES ('124', '627', '5');
INSERT INTO `cms_contenttag` VALUES ('124', '628', '6');
INSERT INTO `cms_contenttag` VALUES ('124', '629', '7');
INSERT INTO `cms_contenttag` VALUES ('124', '630', '8');
INSERT INTO `cms_contenttag` VALUES ('124', '631', '9');
INSERT INTO `cms_contenttag` VALUES ('124', '632', '10');
INSERT INTO `cms_contenttag` VALUES ('125', '633', '0');
INSERT INTO `cms_contenttag` VALUES ('125', '634', '1');
INSERT INTO `cms_contenttag` VALUES ('125', '635', '2');
INSERT INTO `cms_contenttag` VALUES ('125', '636', '3');
INSERT INTO `cms_contenttag` VALUES ('125', '637', '4');
INSERT INTO `cms_contenttag` VALUES ('125', '638', '5');
INSERT INTO `cms_contenttag` VALUES ('125', '639', '6');
INSERT INTO `cms_contenttag` VALUES ('125', '640', '7');
INSERT INTO `cms_contenttag` VALUES ('125', '641', '8');
INSERT INTO `cms_contenttag` VALUES ('126', '642', '0');
INSERT INTO `cms_contenttag` VALUES ('126', '643', '1');
INSERT INTO `cms_contenttag` VALUES ('126', '644', '2');
INSERT INTO `cms_contenttag` VALUES ('126', '645', '3');
INSERT INTO `cms_contenttag` VALUES ('126', '646', '4');
INSERT INTO `cms_contenttag` VALUES ('126', '647', '5');
INSERT INTO `cms_contenttag` VALUES ('127', '648', '0');
INSERT INTO `cms_contenttag` VALUES ('127', '649', '1');
INSERT INTO `cms_contenttag` VALUES ('127', '650', '2');
INSERT INTO `cms_contenttag` VALUES ('127', '651', '3');
INSERT INTO `cms_contenttag` VALUES ('128', '652', '0');
INSERT INTO `cms_contenttag` VALUES ('128', '653', '1');
INSERT INTO `cms_contenttag` VALUES ('128', '345', '2');
INSERT INTO `cms_contenttag` VALUES ('128', '654', '3');
INSERT INTO `cms_contenttag` VALUES ('128', '655', '4');
INSERT INTO `cms_contenttag` VALUES ('128', '656', '5');
INSERT INTO `cms_contenttag` VALUES ('128', '345', '6');
INSERT INTO `cms_contenttag` VALUES ('128', '657', '7');
INSERT INTO `cms_contenttag` VALUES ('128', '658', '8');
INSERT INTO `cms_contenttag` VALUES ('129', '322', '0');
INSERT INTO `cms_contenttag` VALUES ('129', '628', '1');
INSERT INTO `cms_contenttag` VALUES ('129', '659', '2');
INSERT INTO `cms_contenttag` VALUES ('129', '660', '3');
INSERT INTO `cms_contenttag` VALUES ('129', '661', '4');
INSERT INTO `cms_contenttag` VALUES ('129', '662', '5');
INSERT INTO `cms_contenttag` VALUES ('129', '559', '6');
INSERT INTO `cms_contenttag` VALUES ('129', '663', '7');
INSERT INTO `cms_contenttag` VALUES ('129', '664', '8');
INSERT INTO `cms_contenttag` VALUES ('129', '546', '9');
INSERT INTO `cms_contenttag` VALUES ('129', '665', '10');
INSERT INTO `cms_contenttag` VALUES ('129', '666', '11');
INSERT INTO `cms_contenttag` VALUES ('129', '667', '12');
INSERT INTO `cms_contenttag` VALUES ('130', '668', '0');
INSERT INTO `cms_contenttag` VALUES ('130', '637', '1');
INSERT INTO `cms_contenttag` VALUES ('130', '669', '2');
INSERT INTO `cms_contenttag` VALUES ('130', '670', '3');
INSERT INTO `cms_contenttag` VALUES ('130', '671', '4');
INSERT INTO `cms_contenttag` VALUES ('130', '672', '5');
INSERT INTO `cms_contenttag` VALUES ('130', '641', '6');
INSERT INTO `cms_contenttag` VALUES ('131', '673', '0');
INSERT INTO `cms_contenttag` VALUES ('131', '674', '1');
INSERT INTO `cms_contenttag` VALUES ('131', '675', '2');
INSERT INTO `cms_contenttag` VALUES ('131', '676', '3');
INSERT INTO `cms_contenttag` VALUES ('131', '677', '4');
INSERT INTO `cms_contenttag` VALUES ('131', '678', '5');
INSERT INTO `cms_contenttag` VALUES ('132', '679', '0');
INSERT INTO `cms_contenttag` VALUES ('132', '680', '1');
INSERT INTO `cms_contenttag` VALUES ('132', '681', '2');
INSERT INTO `cms_contenttag` VALUES ('132', '682', '3');
INSERT INTO `cms_contenttag` VALUES ('132', '683', '4');
INSERT INTO `cms_contenttag` VALUES ('132', '684', '5');
INSERT INTO `cms_contenttag` VALUES ('132', '685', '6');
INSERT INTO `cms_contenttag` VALUES ('140', '298', '0');
INSERT INTO `cms_contenttag` VALUES ('140', '437', '1');
INSERT INTO `cms_contenttag` VALUES ('140', '438', '2');
INSERT INTO `cms_contenttag` VALUES ('141', '711', '0');
INSERT INTO `cms_contenttag` VALUES ('141', '712', '1');
INSERT INTO `cms_contenttag` VALUES ('141', '713', '2');
INSERT INTO `cms_contenttag` VALUES ('141', '714', '3');
INSERT INTO `cms_contenttag` VALUES ('141', '715', '4');

-- ----------------------------
-- Table structure for cms_content_attachment
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_attachment`;
CREATE TABLE `cms_content_attachment` (
  `contentId` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `attachmentPath` varchar(255) NOT NULL COMMENT '附件路径',
  `attachmentName` varchar(100) NOT NULL COMMENT '附件名称',
  `filename` varchar(100) DEFAULT NULL COMMENT '文件名',
  `downloadCount` int(11) NOT NULL DEFAULT '0' COMMENT '下载次数',
  KEY `fk_jc_attachment_content` (`contentId`),
  CONSTRAINT `fk_jc_attachment_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容附件表';

-- ----------------------------
-- Records of cms_content_attachment
-- ----------------------------
INSERT INTO `cms_content_attachment` VALUES ('69', '0', '/u/cms/www/201609/261635496465.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('71', '0', '/u/cms/www/201609/271002022fag.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('72', '0', '/u/cms/www/201609/27102743993k.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('100', '0', '/u/cms/www/201610/10110756cg7o.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('101', '0', '/u/cms/www/201610/10111518cqda.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('102', '0', '/u/cms/www/201610/10111758q1kj.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('103', '0', '/u/cms/www/201610/101120397iez.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('104', '0', '/u/cms/www/201610/10112554wirt.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('105', '0', '/u/cms/www/201610/10112814u17l.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('106', '0', '/u/cms/www/201610/101130257966.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('107', '0', '/u/cms/www/201610/101131571wkz.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('108', '0', '/u/cms/www/201610/101133341nc3.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('109', '0', '/u/cms/www/201610/10113459gvbx.zip', '演示.zip', null, '0');
INSERT INTO `cms_content_attachment` VALUES ('110', '0', '/u/cms/www/201610/101136270k36.zip', '演示.zip', null, '0');

-- ----------------------------
-- Table structure for cms_content_attr
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_attr`;
CREATE TABLE `cms_content_attr` (
  `contentId` varchar(64) NOT NULL,
  `attrName` varchar(30) NOT NULL COMMENT '名称',
  `attrValue` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_attr_content` (`contentId`),
  CONSTRAINT `fk_jc_attr_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展属性表';

-- ----------------------------
-- Records of cms_content_attr
-- ----------------------------
INSERT INTO `cms_content_attr` VALUES ('14', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('14', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('11', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('11', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('12', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('12', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('23', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('23', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('27', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('27', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('28', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('28', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('29', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('29', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('30', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('30', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('32', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('32', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('43', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('43', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('43', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('43', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('43', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('43', 'salary', '5000-8000');
INSERT INTO `cms_content_attr` VALUES ('43', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('43', 'workplace', '南昌');
INSERT INTO `cms_content_attr` VALUES ('43', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('44', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('44', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('44', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('44', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('44', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('44', 'salary', '3000-5000');
INSERT INTO `cms_content_attr` VALUES ('44', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('44', 'workplace', '上海');
INSERT INTO `cms_content_attr` VALUES ('44', 'nums', '3-5人');
INSERT INTO `cms_content_attr` VALUES ('45', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('45', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('45', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('45', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('45', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('45', 'salary', '1500-2000');
INSERT INTO `cms_content_attr` VALUES ('45', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('45', 'workplace', '北京');
INSERT INTO `cms_content_attr` VALUES ('45', 'nums', '5-10人');
INSERT INTO `cms_content_attr` VALUES ('46', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('46', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('46', 'hasmanage', '要求');
INSERT INTO `cms_content_attr` VALUES ('46', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('46', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('46', 'salary', '面议');
INSERT INTO `cms_content_attr` VALUES ('46', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('46', 'workplace', '广州');
INSERT INTO `cms_content_attr` VALUES ('46', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('47', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('47', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('47', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('47', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('47', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('47', 'salary', '1500-2000');
INSERT INTO `cms_content_attr` VALUES ('47', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('47', 'workplace', '重庆');
INSERT INTO `cms_content_attr` VALUES ('47', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('48', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('48', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('48', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('48', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('48', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('48', 'salary', '5000-8000');
INSERT INTO `cms_content_attr` VALUES ('48', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('48', 'workplace', '杭州');
INSERT INTO `cms_content_attr` VALUES ('48', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('49', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('49', 'nature', '兼职');
INSERT INTO `cms_content_attr` VALUES ('49', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('49', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('49', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('49', 'salary', '1500-2000');
INSERT INTO `cms_content_attr` VALUES ('49', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('49', 'workplace', '重庆');
INSERT INTO `cms_content_attr` VALUES ('49', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('50', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('50', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('50', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('50', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('50', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('50', 'salary', '1500-2000');
INSERT INTO `cms_content_attr` VALUES ('50', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('50', 'workplace', '南京');
INSERT INTO `cms_content_attr` VALUES ('50', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('51', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('51', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('51', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('51', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('51', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('51', 'salary', '2000-3000');
INSERT INTO `cms_content_attr` VALUES ('51', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('51', 'workplace', '西安');
INSERT INTO `cms_content_attr` VALUES ('51', 'nums', '1-3人');
INSERT INTO `cms_content_attr` VALUES ('53', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('53', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('53', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('55', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('55', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('55', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('57', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('57', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('57', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('59', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('59', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('59', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('61', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('61', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('61', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('63', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('63', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('63', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('69', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('69', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('69', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('69', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('69', 'edition', '8.3.18038.0');
INSERT INTO `cms_content_attr` VALUES ('69', 'system', 'WinXP/Win2003/Vista/Win7/Win8');
INSERT INTO `cms_content_attr` VALUES ('69', 'size', '54.3M');
INSERT INTO `cms_content_attr` VALUES ('69', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('71', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('71', 'system', 'win8/win7/vista/win2003/winxp');
INSERT INTO `cms_content_attr` VALUES ('71', 'size', '10.9 MB');
INSERT INTO `cms_content_attr` VALUES ('71', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('71', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('71', 'edition', 'V5.2.7');
INSERT INTO `cms_content_attr` VALUES ('71', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('71', 'bit', '32位');
INSERT INTO `cms_content_attr` VALUES ('72', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('72', 'system', 'WinXP(SP2以上) / Vista / Win7 / Win8 / Win8.1');
INSERT INTO `cms_content_attr` VALUES ('72', 'size', '112M');
INSERT INTO `cms_content_attr` VALUES ('72', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('72', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('72', 'edition', '360杀毒V5.0.0.7092');
INSERT INTO `cms_content_attr` VALUES ('72', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('72', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('100', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('100', 'system', 'win8/win7/vista/win2003/winxp');
INSERT INTO `cms_content_attr` VALUES ('100', 'size', '298.68 MB');
INSERT INTO `cms_content_attr` VALUES ('100', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('100', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('100', 'edition', '3.00.401');
INSERT INTO `cms_content_attr` VALUES ('100', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('100', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('101', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('101', 'system', 'WinXP/Win2003/Vista/Win7/Win8/Win10');
INSERT INTO `cms_content_attr` VALUES ('101', 'size', '40.4M');
INSERT INTO `cms_content_attr` VALUES ('101', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('101', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('101', 'edition', '9.0.16.408');
INSERT INTO `cms_content_attr` VALUES ('101', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('101', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('102', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('102', 'system', 'Win2000 WinXP Win2003 Vista Win8 Win7 Win10');
INSERT INTO `cms_content_attr` VALUES ('102', 'size', '1.29MB');
INSERT INTO `cms_content_attr` VALUES ('102', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('102', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('102', 'edition', '10.3.0.2009 官方正式版');
INSERT INTO `cms_content_attr` VALUES ('102', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('102', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('103', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('103', 'system', 'WinXP/Win2003/Vista/Win7/Win8/Win10');
INSERT INTO `cms_content_attr` VALUES ('103', 'size', '37.5M');
INSERT INTO `cms_content_attr` VALUES ('103', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('103', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('103', 'edition', '8.0.0.8381');
INSERT INTO `cms_content_attr` VALUES ('103', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('103', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('104', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('104', 'system', 'WinXP/Win2003/Vista/Win7/Win8');
INSERT INTO `cms_content_attr` VALUES ('104', 'size', '22.6M');
INSERT INTO `cms_content_attr` VALUES ('104', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('104', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('104', 'edition', '8.13.0.0');
INSERT INTO `cms_content_attr` VALUES ('104', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('104', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('105', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('105', 'system', 'WinXP/Win2003/Vista/Win7/Win8');
INSERT INTO `cms_content_attr` VALUES ('105', 'size', '61.4M');
INSERT INTO `cms_content_attr` VALUES ('105', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('105', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('105', 'edition', '2.0.0.1');
INSERT INTO `cms_content_attr` VALUES ('105', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('105', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('106', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('106', 'system', 'WinXP/Win2003/Vista/Win7/Win8/Win10');
INSERT INTO `cms_content_attr` VALUES ('106', 'size', '33.3M');
INSERT INTO `cms_content_attr` VALUES ('106', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('106', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('106', 'edition', '4.3.3.27');
INSERT INTO `cms_content_attr` VALUES ('106', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('106', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('107', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('107', 'system', 'Win7/Win8/Win10');
INSERT INTO `cms_content_attr` VALUES ('107', 'size', '46.5M');
INSERT INTO `cms_content_attr` VALUES ('107', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('107', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('107', 'edition', '53.0.2785.143');
INSERT INTO `cms_content_attr` VALUES ('107', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('107', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('108', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('108', 'system', 'Win7/WinVista/WinXP/Win8兼容软件');
INSERT INTO `cms_content_attr` VALUES ('108', 'size', '9.7M');
INSERT INTO `cms_content_attr` VALUES ('108', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('108', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('108', 'edition', '8.1');
INSERT INTO `cms_content_attr` VALUES ('108', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('108', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('109', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('109', 'system', 'Win2000/WinXP/Win2003/Vista/Win7/Win8/Win10');
INSERT INTO `cms_content_attr` VALUES ('109', 'size', '32.7M');
INSERT INTO `cms_content_attr` VALUES ('109', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('109', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('109', 'edition', '版本：3.7.0.0011');
INSERT INTO `cms_content_attr` VALUES ('109', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('109', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('110', 'demoUrl', 'http://');
INSERT INTO `cms_content_attr` VALUES ('110', 'system', 'WinXP/Win2003/Vista/Win7/Win8/Win10');
INSERT INTO `cms_content_attr` VALUES ('110', 'size', '32.5M');
INSERT INTO `cms_content_attr` VALUES ('110', 'relatedLink', 'http://');
INSERT INTO `cms_content_attr` VALUES ('110', 'softType', '国产软件');
INSERT INTO `cms_content_attr` VALUES ('110', 'edition', '9.15.1596.0');
INSERT INTO `cms_content_attr` VALUES ('110', 'warrant', '免费版');
INSERT INTO `cms_content_attr` VALUES ('110', 'bit', '32/64');
INSERT INTO `cms_content_attr` VALUES ('114', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('114', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('114', 'pic3', '');
INSERT INTO `cms_content_attr` VALUES ('13', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('13', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('13', 'pic3', '');
INSERT INTO `cms_content_attr` VALUES ('7', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('7', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('7', 'pic3', '');
INSERT INTO `cms_content_attr` VALUES ('115', 'pic1', '');
INSERT INTO `cms_content_attr` VALUES ('115', 'pic2', '');
INSERT INTO `cms_content_attr` VALUES ('115', 'pic3', '');
INSERT INTO `cms_content_attr` VALUES ('121', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('121', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('121', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('122', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('122', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('122', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('123', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('123', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('123', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('124', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('124', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('124', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('125', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('125', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('125', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('126', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('126', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('126', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('127', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('127', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('127', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('128', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('128', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('128', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('129', 'Starring', '');
INSERT INTO `cms_content_attr` VALUES ('129', 'Video', '正片');
INSERT INTO `cms_content_attr` VALUES ('129', 'Director', '');
INSERT INTO `cms_content_attr` VALUES ('140', 'education', '专科');
INSERT INTO `cms_content_attr` VALUES ('140', 'nature', '全职');
INSERT INTO `cms_content_attr` VALUES ('140', 'hasmanage', '不要求');
INSERT INTO `cms_content_attr` VALUES ('140', 'deadline', '');
INSERT INTO `cms_content_attr` VALUES ('140', 'experience', '1-3年');
INSERT INTO `cms_content_attr` VALUES ('140', 'salary', '1500-2000');
INSERT INTO `cms_content_attr` VALUES ('140', 'category', '项目主管');
INSERT INTO `cms_content_attr` VALUES ('140', 'workplace', '重庆');
INSERT INTO `cms_content_attr` VALUES ('140', 'nums', '1-3人');

-- ----------------------------
-- Table structure for cms_content_channel
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_channel`;
CREATE TABLE `cms_content_channel` (
  `channelId` varchar(64) NOT NULL,
  `contentId` varchar(64) NOT NULL,
  PRIMARY KEY (`channelId`,`contentId`),
  KEY `fk_jc_channel_content` (`contentId`),
  CONSTRAINT `fk_jc_channel_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`),
  CONSTRAINT `fk_jc_content_channel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容栏目关联表';

-- ----------------------------
-- Records of cms_content_channel
-- ----------------------------
INSERT INTO `cms_content_channel` VALUES ('93', '100');
INSERT INTO `cms_content_channel` VALUES ('91', '101');
INSERT INTO `cms_content_channel` VALUES ('90', '102');
INSERT INTO `cms_content_channel` VALUES ('90', '103');
INSERT INTO `cms_content_channel` VALUES ('93', '104');
INSERT INTO `cms_content_channel` VALUES ('91', '105');
INSERT INTO `cms_content_channel` VALUES ('91', '106');
INSERT INTO `cms_content_channel` VALUES ('91', '107');
INSERT INTO `cms_content_channel` VALUES ('91', '108');
INSERT INTO `cms_content_channel` VALUES ('91', '109');
INSERT INTO `cms_content_channel` VALUES ('75', '11');
INSERT INTO `cms_content_channel` VALUES ('91', '110');
INSERT INTO `cms_content_channel` VALUES ('75', '111');
INSERT INTO `cms_content_channel` VALUES ('75', '112');
INSERT INTO `cms_content_channel` VALUES ('75', '113');
INSERT INTO `cms_content_channel` VALUES ('76', '114');
INSERT INTO `cms_content_channel` VALUES ('76', '115');
INSERT INTO `cms_content_channel` VALUES ('76', '116');
INSERT INTO `cms_content_channel` VALUES ('76', '117');
INSERT INTO `cms_content_channel` VALUES ('76', '118');
INSERT INTO `cms_content_channel` VALUES ('76', '119');
INSERT INTO `cms_content_channel` VALUES ('75', '12');
INSERT INTO `cms_content_channel` VALUES ('76', '120');
INSERT INTO `cms_content_channel` VALUES ('77', '121');
INSERT INTO `cms_content_channel` VALUES ('77', '122');
INSERT INTO `cms_content_channel` VALUES ('77', '123');
INSERT INTO `cms_content_channel` VALUES ('77', '124');
INSERT INTO `cms_content_channel` VALUES ('77', '125');
INSERT INTO `cms_content_channel` VALUES ('77', '126');
INSERT INTO `cms_content_channel` VALUES ('77', '127');
INSERT INTO `cms_content_channel` VALUES ('77', '128');
INSERT INTO `cms_content_channel` VALUES ('77', '129');
INSERT INTO `cms_content_channel` VALUES ('76', '13');
INSERT INTO `cms_content_channel` VALUES ('75', '130');
INSERT INTO `cms_content_channel` VALUES ('75', '131');
INSERT INTO `cms_content_channel` VALUES ('75', '132');
INSERT INTO `cms_content_channel` VALUES ('75', '14');
INSERT INTO `cms_content_channel` VALUES ('79', '140');
INSERT INTO `cms_content_channel` VALUES ('76', '141');
INSERT INTO `cms_content_channel` VALUES ('76', '2');
INSERT INTO `cms_content_channel` VALUES ('75', '23');
INSERT INTO `cms_content_channel` VALUES ('75', '27');
INSERT INTO `cms_content_channel` VALUES ('75', '28');
INSERT INTO `cms_content_channel` VALUES ('75', '29');
INSERT INTO `cms_content_channel` VALUES ('75', '30');
INSERT INTO `cms_content_channel` VALUES ('75', '32');
INSERT INTO `cms_content_channel` VALUES ('75', '33');
INSERT INTO `cms_content_channel` VALUES ('75', '36');
INSERT INTO `cms_content_channel` VALUES ('75', '37');
INSERT INTO `cms_content_channel` VALUES ('75', '39');
INSERT INTO `cms_content_channel` VALUES ('79', '43');
INSERT INTO `cms_content_channel` VALUES ('79', '44');
INSERT INTO `cms_content_channel` VALUES ('79', '45');
INSERT INTO `cms_content_channel` VALUES ('79', '46');
INSERT INTO `cms_content_channel` VALUES ('79', '47');
INSERT INTO `cms_content_channel` VALUES ('79', '48');
INSERT INTO `cms_content_channel` VALUES ('79', '49');
INSERT INTO `cms_content_channel` VALUES ('79', '50');
INSERT INTO `cms_content_channel` VALUES ('79', '51');
INSERT INTO `cms_content_channel` VALUES ('77', '53');
INSERT INTO `cms_content_channel` VALUES ('77', '55');
INSERT INTO `cms_content_channel` VALUES ('77', '57');
INSERT INTO `cms_content_channel` VALUES ('77', '59');
INSERT INTO `cms_content_channel` VALUES ('77', '61');
INSERT INTO `cms_content_channel` VALUES ('77', '63');
INSERT INTO `cms_content_channel` VALUES ('90', '69');
INSERT INTO `cms_content_channel` VALUES ('76', '7');
INSERT INTO `cms_content_channel` VALUES ('91', '71');
INSERT INTO `cms_content_channel` VALUES ('90', '72');

-- ----------------------------
-- Table structure for cms_content_check
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_check`;
CREATE TABLE `cms_content_check` (
  `contentId` varchar(64) NOT NULL,
  `checkStep` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核步数',
  `checkOpinion` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `isRejected` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否退回',
  `reviewer` int(11) DEFAULT NULL,
  `checkDate` datetime DEFAULT NULL COMMENT '终审时间',
  PRIMARY KEY (`contentId`),
  CONSTRAINT `fk_jc_check_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容审核信息表';

-- ----------------------------
-- Records of cms_content_check
-- ----------------------------
INSERT INTO `cms_content_check` VALUES ('100', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('101', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('102', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('103', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('104', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('105', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('106', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('107', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('108', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('109', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('11', '-1', null, '0', '1', '2016-09-28 14:17:39');
INSERT INTO `cms_content_check` VALUES ('110', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('111', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('112', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('113', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('114', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('115', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('116', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('117', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('118', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('119', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('12', '-1', null, '0', '1', '2016-09-28 14:17:39');
INSERT INTO `cms_content_check` VALUES ('120', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('121', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('122', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('123', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('124', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('125', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('126', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('127', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('128', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('129', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('13', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('130', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('131', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('132', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('14', '-1', null, '0', '1', '2016-09-28 14:17:39');
INSERT INTO `cms_content_check` VALUES ('140', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('141', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('2', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('23', '-1', null, '0', '1', '2016-09-28 14:17:39');
INSERT INTO `cms_content_check` VALUES ('27', '-1', null, '0', '1', '2016-09-28 14:17:38');
INSERT INTO `cms_content_check` VALUES ('28', '-1', null, '0', '1', '2016-09-28 14:17:38');
INSERT INTO `cms_content_check` VALUES ('29', '-1', null, '0', '1', '2016-09-28 14:17:38');
INSERT INTO `cms_content_check` VALUES ('30', '-1', null, '0', '1', '2016-09-28 14:17:38');
INSERT INTO `cms_content_check` VALUES ('32', '-1', null, '0', '1', '2016-09-28 14:17:37');
INSERT INTO `cms_content_check` VALUES ('33', '-1', null, '0', '1', '2016-09-28 14:17:37');
INSERT INTO `cms_content_check` VALUES ('36', '-1', null, '0', '1', '2016-09-28 14:17:37');
INSERT INTO `cms_content_check` VALUES ('37', '-1', null, '0', '1', '2016-09-28 14:17:37');
INSERT INTO `cms_content_check` VALUES ('39', '-1', null, '0', '1', '2016-09-28 14:17:37');
INSERT INTO `cms_content_check` VALUES ('43', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('44', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('45', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('46', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('47', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('48', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('49', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('50', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('51', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('53', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('55', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('57', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('59', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('61', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('63', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('69', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('7', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('71', '3', null, '0', null, null);
INSERT INTO `cms_content_check` VALUES ('72', '3', null, '0', null, null);

-- ----------------------------
-- Table structure for cms_content_count
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_count`;
CREATE TABLE `cms_content_count` (
  `contentId` varchar(64) NOT NULL,
  `views` int(11) NOT NULL DEFAULT '0' COMMENT '总访问数',
  `viewsMonth` int(11) NOT NULL DEFAULT '0' COMMENT '月访问数',
  `viewsWeek` int(11) NOT NULL DEFAULT '0' COMMENT '周访问数',
  `viewsDay` int(11) NOT NULL DEFAULT '0' COMMENT '日访问数',
  `comments` int(11) NOT NULL DEFAULT '0' COMMENT '总评论数',
  `commentsMonth` int(11) NOT NULL DEFAULT '0' COMMENT '月评论数',
  `commentsWeek` smallint(6) NOT NULL DEFAULT '0' COMMENT '周评论数',
  `commentsDay` smallint(6) NOT NULL DEFAULT '0' COMMENT '日评论数',
  `downloads` int(11) NOT NULL DEFAULT '0' COMMENT '总下载数',
  `downloadsMonth` int(11) NOT NULL DEFAULT '0' COMMENT '月下载数',
  `downloadsWeek` smallint(6) NOT NULL DEFAULT '0' COMMENT '周下载数',
  `downloadsDay` smallint(6) NOT NULL DEFAULT '0' COMMENT '日下载数',
  `ups` int(11) NOT NULL DEFAULT '0' COMMENT '总顶数',
  `upsMonth` int(11) NOT NULL DEFAULT '0' COMMENT '月顶数',
  `upsWeek` smallint(6) NOT NULL DEFAULT '0' COMMENT '周顶数',
  `upsDay` smallint(6) NOT NULL DEFAULT '0' COMMENT '日顶数',
  `downs` int(11) NOT NULL DEFAULT '0' COMMENT '总踩数',
  PRIMARY KEY (`contentId`),
  KEY `index_jc_content_count_views_day` (`viewsDay`),
  KEY `index_jc_content_count_views_week` (`viewsWeek`),
  KEY `index_jc_content_count_views_month` (`viewsMonth`),
  KEY `index_jc_content_count_views` (`views`),
  KEY `index_jc_content_count_comments_day` (`commentsDay`),
  KEY `index_jc_content_count_comments_month` (`commentsMonth`),
  KEY `index_jc_content_count_comments_week` (`commentsWeek`),
  KEY `index_jc_content_count_comments` (`comments`),
  KEY `index_jc_content_count_downloads_day` (`downloadsDay`),
  KEY `index_jc_content_count_downloads_month` (`downloadsMonth`),
  KEY `index_jc_content_count_downloads_week` (`downloadsWeek`),
  KEY `index_jc_content_count_downloads` (`downloads`),
  KEY `index_jc_content_count_ups_day` (`upsDay`),
  KEY `index_jc_content_count_ups_week` (`upsWeek`),
  KEY `index_jc_content_count_ups_month` (`upsMonth`),
  KEY `index_jc_content_count_ups` (`ups`),
  CONSTRAINT `fk_jc_count_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容计数表';

-- ----------------------------
-- Records of cms_content_count
-- ----------------------------
INSERT INTO `cms_content_count` VALUES ('100', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('101', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('102', '16', '16', '16', '16', '0', '0', '0', '0', '39', '39', '1', '39', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('103', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('104', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('105', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('106', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('107', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('108', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('109', '16', '16', '16', '16', '0', '0', '0', '0', '21', '21', '1', '21', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('11', '21', '20', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('110', '4', '4', '4', '4', '0', '0', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('111', '16', '16', '16', '16', '0', '0', '0', '0', '0', '0', '0', '0', '12', '12', '12', '12', '0');
INSERT INTO `cms_content_count` VALUES ('112', '17', '17', '17', '17', '0', '0', '0', '0', '0', '0', '0', '0', '16', '16', '16', '16', '0');
INSERT INTO `cms_content_count` VALUES ('113', '29', '29', '29', '29', '0', '0', '0', '0', '0', '0', '0', '0', '13', '13', '13', '13', '0');
INSERT INTO `cms_content_count` VALUES ('114', '18', '18', '18', '18', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('115', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('116', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('117', '18', '18', '18', '18', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('118', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('119', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('12', '10', '10', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('120', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('121', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('122', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('123', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('124', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('125', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('126', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('127', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('128', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('129', '18', '18', '18', '18', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('13', '20', '15', '3', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('130', '22', '22', '22', '22', '1', '1', '1', '1', '0', '0', '0', '0', '12', '12', '12', '12', '0');
INSERT INTO `cms_content_count` VALUES ('131', '17', '17', '17', '17', '0', '0', '0', '0', '0', '0', '0', '0', '15', '15', '15', '15', '0');
INSERT INTO `cms_content_count` VALUES ('132', '7', '7', '7', '7', '2', '2', '2', '2', '0', '0', '0', '0', '1', '1', '1', '1', '0');
INSERT INTO `cms_content_count` VALUES ('14', '58', '44', '21', '21', '0', '0', '0', '0', '0', '0', '0', '0', '14', '14', '14', '14', '0');
INSERT INTO `cms_content_count` VALUES ('140', '18', '18', '18', '18', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('141', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('2', '14', '13', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('23', '16', '16', '16', '16', '0', '0', '0', '0', '0', '0', '0', '0', '12', '12', '12', '12', '0');
INSERT INTO `cms_content_count` VALUES ('27', '9', '9', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '2', '2', '2', '2', '0');
INSERT INTO `cms_content_count` VALUES ('28', '9', '9', '4', '4', '0', '0', '0', '0', '0', '0', '0', '0', '2', '2', '2', '2', '0');
INSERT INTO `cms_content_count` VALUES ('29', '3', '3', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '0');
INSERT INTO `cms_content_count` VALUES ('30', '63', '63', '54', '54', '1', '1', '1', '1', '0', '0', '0', '0', '44', '44', '44', '44', '0');
INSERT INTO `cms_content_count` VALUES ('32', '5', '5', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '2', '2', '1', '1', '1');
INSERT INTO `cms_content_count` VALUES ('33', '54', '54', '18', '18', '0', '0', '0', '0', '0', '0', '0', '0', '18', '18', '18', '18', '0');
INSERT INTO `cms_content_count` VALUES ('36', '4', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('37', '12', '12', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '2', '2', '1', '1', '0');
INSERT INTO `cms_content_count` VALUES ('39', '34', '34', '27', '27', '1', '1', '1', '1', '0', '0', '0', '0', '17', '17', '17', '17', '0');
INSERT INTO `cms_content_count` VALUES ('43', '3', '3', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('44', '2', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('45', '19', '19', '17', '17', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('46', '19', '19', '17', '17', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('47', '16', '16', '16', '16', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('48', '3', '3', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('49', '18', '18', '16', '16', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('50', '16', '16', '16', '16', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('51', '17', '17', '5', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('53', '3', '3', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('55', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('57', '4', '4', '4', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('59', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('61', '2', '2', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('63', '2', '2', '2', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('69', '48', '48', '16', '16', '0', '0', '0', '0', '1', '1', '1', '1', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('7', '38', '33', '17', '17', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('71', '5', '5', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `cms_content_count` VALUES ('72', '6', '6', '4', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for cms_content_ext
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_ext`;
CREATE TABLE `cms_content_ext` (
  `contentId` varchar(64) NOT NULL,
  `title` varchar(150) NOT NULL COMMENT '标题',
  `shortTitle` varchar(150) DEFAULT NULL COMMENT '简短标题',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `origin` varchar(100) DEFAULT NULL COMMENT '来源',
  `originUrl` varchar(255) DEFAULT NULL COMMENT '来源链接',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `releaseDate` datetime NOT NULL COMMENT '发布日期',
  `mediaPath` varchar(255) DEFAULT NULL COMMENT '媒体路径',
  `mediaType` varchar(20) DEFAULT NULL COMMENT '媒体类型',
  `isBold` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否加粗',
  `titleImg` varchar(100) DEFAULT NULL COMMENT '标题图片',
  `contentImg` varchar(100) DEFAULT NULL COMMENT '内容图片',
  `typeImg` varchar(100) DEFAULT NULL COMMENT '类型图片',
  `link` varchar(255) DEFAULT NULL COMMENT '外部链接',
  `tplContent` varchar(100) DEFAULT NULL COMMENT '指定模板',
  `needRegenerate` tinyint(1) NOT NULL DEFAULT '1' COMMENT '需要重新生成静态页',
  `toplevelDate` datetime DEFAULT NULL COMMENT '固顶到期日期',
  `pigeonholeDate` datetime DEFAULT NULL COMMENT '归档日期',
  PRIMARY KEY (`contentId`),
  CONSTRAINT `fk_jc_ext_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展表';

-- ----------------------------
-- Records of cms_content_ext
-- ----------------------------
INSERT INTO `cms_content_ext` VALUES ('100', '诺亚传说', '诺亚传说', null, null, null, '《诺亚传说》是尚游游戏自主研发的大型角色扮演网游，新资料片《诺亚传说前传-亚特兰蒂斯》火爆发布。', '2016-10-10 11:09:06', null, null, '0', '/u/cms/www/201610/10110634gxcz.jpg', '/u/cms/www/201610/10110628vfwz.jpg', '/u/cms/www/201610/10110654vib6.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('101', '迅雷9', null, null, null, null, '迅雷9是“深圳市迅雷网络技术有限公司”于2016年推出的“迅雷”系列下载软件的最新换代产品。迅雷9采用全新下载引擎，对百兆光纤宽带网络环境进行诸多针对性的优化，让用户获得更卓越的下载体验；全新的界面功能布局，承载了更丰富的内容，打造找、下、用的一站式娱乐消费平台。', '2016-10-10 11:16:31', null, null, '0', '/u/cms/www/201610/10111618rhdk.jpg', '/u/cms/www/201610/10111549rupn.jpg', '/u/cms/www/201610/101116034e14.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('102', '360安全卫士', null, null, null, null, null, '2016-10-10 11:19:18', null, null, '0', '/u/cms/www/201610/101119092w8r.jpg', '/u/cms/www/201610/101119026ukx.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('103', '搜狗输入法', null, null, null, null, null, '2016-10-10 11:21:37', null, null, '0', '/u/cms/www/201610/101120330mpz.jpg', '/u/cms/www/201610/10112023kqfp.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('104', 'YY语音', null, null, null, null, null, '2016-10-10 11:27:17', null, null, '0', '/u/cms/www/201610/10112706jiii.jpg', '/u/cms/www/201610/10112657zklb.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('105', '阿里旺旺', null, null, null, null, null, '2016-10-10 11:28:52', null, null, '0', '/u/cms/www/201610/10112845wryq.jpg', '/u/cms/www/201610/10112839cvwx.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('106', '爱奇艺视频', null, null, null, null, null, '2016-10-10 11:30:54', null, null, '0', '/u/cms/www/201610/101130399dbt.jpg', '/u/cms/www/201610/10113032zu0k.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('107', '谷歌浏览器', null, null, null, null, null, '2016-10-10 11:32:35', null, null, '0', '/u/cms/www/201610/10113226knhj.jpg', '/u/cms/www/201610/10113218ya2p.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('108', '360浏览器', null, null, null, null, null, '2016-10-10 11:34:05', null, null, '0', '/u/cms/www/201610/10113400utfn.jpg', '/u/cms/www/201610/10113354g71e.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('109', 'PPTV聚力', null, null, null, null, null, '2016-10-10 11:35:26', null, null, '0', '/u/cms/www/201610/1011351336l0.jpg', '/u/cms/www/201610/10113506jz07.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('11', '全国28省遭灾 已投入抗洪抢险777万人次', '全国28省遭灾 已投入抗洪抢险777万人次', null, null, null, '据初步统计，截至7月13日，长江中下游湖北、湖南、江西、安徽、江苏等5省堤防巡查防守和抢险共投入62.2万人(含部队3.08万人)，6月30日以来已累计投入抗洪抢险777万人次。', '2016-07-15 16:23:32', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('110', '腾讯视频', null, null, null, null, null, '2016-10-10 11:36:32', null, null, '0', '/u/cms/www/201610/101135560ne4.jpg', '/u/cms/www/201610/10113551yru8.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('111', '专访百度科学家徐伟：百度比谷歌的人工智能平台更易上手', null, null, null, null, '“开发者在使用时，学习成本较低，这是我们平台易用性的体现。”负责搭建百度深度学习平台PaddlePaddle的百度美国研究院科学家徐伟22日在接受腾讯科技专访时表示。', '2016-10-10 11:45:47', null, 'CK', '0', null, null, '/u/cms/www/201610/10114452yjhh.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('112', '电信诈骗多发产生“蝴蝶效应”虚拟运营商融资受波及', null, null, null, null, '自2013年12月首批企业获牌以来，虚拟运营商发展迅速，目前已有42家企业获得牌照。9月22日，在“ICT中国· 2016高层论坛”移动转售分论坛上，中国通信企业协会披露，目前移动转售业务用户数已超3500万，占全国移动用户人数2.67%。', '2016-10-10 11:48:08', null, 'CK', '0', null, null, '/u/cms/www/201610/10114704dsba.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('113', '李克强联合国承诺援助难民：这是责任和道义的担当', null, null, null, null, '李克强总理首赴联合国，首场活动便是出席由联合国倡议举行的联大解决难移民大规模流动问题高级别会议。', '2016-10-10 11:50:03', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('114', '科学家研制“隐身潜水服” 跟鲨鱼同游也不怕被发现', null, null, null, null, null, '2016-10-10 13:11:12', null, null, '0', null, null, '/u/cms/www/201610/101308178wzr.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('115', '四川石渠发现至少30万公顷珍贵泥炭湿地资源', null, null, null, null, null, '2016-10-10 13:15:37', null, null, '0', null, null, '/u/cms/www/201610/101314450fs1.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('116', '全世界最孤独的咖啡馆：海拔4860米 达古冰川之巅', null, null, null, null, null, '2016-10-10 13:19:22', null, null, '0', null, null, '/u/cms/www/201610/10131742wzhv.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('117', '中俄“合作－2016”联合反恐训练圆满结束', null, null, null, null, null, '2016-10-10 13:22:07', null, null, '0', null, null, '/u/cms/www/201610/101320442qbi.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('118', '探访燕城监狱：蔬菜自己种 午餐两菜一汤', null, null, null, null, null, '2016-10-10 13:23:50', null, null, '0', null, null, '/u/cms/www/201610/10132253w0rj.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('119', '摄影师拍摄巨鳄破水而出吞食猎物 上演一箭双雕', null, null, null, null, null, '2016-10-10 13:26:38', null, null, '0', null, null, '/u/cms/www/201610/101326014zdc.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('12', '习近平就法国尼斯恐袭事件向法总统致慰问电', null, null, null, null, '当地时间2016年7月14日，法国国庆日，据法国媒体报道，法国尼斯一辆货车突然冲击人群发动恐怖袭击，据最新消息称，有84人丧生，目前事故原因仍在进一步调查之中。', '2016-07-15 17:22:47', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('120', '宛如置身黑客帝国 “啤酒丛林”惊艳青岛海滨夜空', null, null, null, null, null, '2016-10-10 13:35:17', null, null, '0', null, null, '/u/cms/www/201610/10133449hwwr.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('121', '《半妖倾城》爱与恨，追与逃，人与妖再一次掀起一段的惊人的倾城传奇', null, null, null, null, '清末，八国联军攻入北京，聂如风带着妻子应蝶和二个女儿躲避追杀，应蝶策马将如风和女儿赶走，独自迎敌，应蝶突然长出雪白的翅膀飞了起来——原来她竟然是妖.', '2016-10-10 13:38:11', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', '/u/cms/www/201610/10133842aiyf.png', null, '/u/cms/www/201610/10133710to3f.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('122', '《那年青春我们正好》刘诗诗、种丹妮演绎闺蜜', null, null, null, null, '《那年青春我们正好》在浙江卫视、东方卫视热播。种丹妮饰演的韩露“花痴”肖小军（郑恺），与闺蜜刘婷（刘诗诗）同爱一人而“掰面”。', '2016-10-10 13:40:05', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', '/u/cms/www/201610/101340004te2.png', null, '/u/cms/www/201610/10133954jfoc.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('123', '《奔跑吧兄弟》收官对决一触即发 邓超被岳云鹏压垮惨叫', null, null, null, null, '跑男搭档助阵勇士,力争宇宙最强者称号,究竟“跑男团们都有着怎样神奇的能力？最终谁又将在“助力勇士”的帮助下赢得本季“宇宙最强者”的称号呢？', '2016-10-10 13:41:03', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', '/u/cms/www/201610/101340393eav.png', null, '/u/cms/www/201610/10134048htcl.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('124', '李克强出席加拿大总理家宴 小小特鲁多表演后空翻', null, null, null, null, null, '2016-10-10 13:42:48', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', null, null, '/u/cms/www/201610/10134238qppo.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('125', '习主席和中央军委领导推进军队战斗力建设纪实', null, null, null, null, null, '2016-10-10 13:44:47', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', null, null, '/u/cms/www/201610/10134440wmng.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('126', '西北第一村白哈巴的早秋', null, null, null, null, null, '2016-10-10 13:46:01', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', null, null, '/u/cms/www/201610/101345564pok.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('127', '美国金秋不止红叶', null, null, null, null, null, '2016-10-10 13:46:26', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', null, null, '/u/cms/www/201610/101346227ayf.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('128', '德国举行南瓜称重比赛 763公斤南瓜获得加冕', null, null, null, null, null, '2016-10-10 13:46:52', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', null, null, '/u/cms/www/201610/101346473kic.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('129', '北京特战队员魔鬼周训练 挑战50项最严苛极限项目', null, null, null, null, null, '2016-10-10 13:47:24', '/u/cms/www/201610/101338015yhf.mp4', 'CK', '0', null, null, '/u/cms/www/201610/10134720z8a5.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('13', '中国空军航空兵赴南海常态化战斗巡航', null, null, null, null, null, '2016-07-19 14:12:48', null, null, '0', null, null, '/u/cms/www/201607/19141129f6g4.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('130', '党中央推进民族工作创新发展纪实', null, null, null, null, '奏响新形势下民族工作新乐章（治国理政新思想新实践）党的十八大以来以习近平同志为总书记的党中央推进民族工作创新发展纪实。', '2016-10-10 13:51:07', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('131', '楼市调控应跳出周期性怪圈', null, null, null, null, '每一轮调控政策都是为稳定住房价格，但调控过后，总有一轮快速上涨行情，越让普通百姓感叹房子越来越买不起了。显然，目前楼市调控实质已陷入越调越高和政府“助涨”的尴尬局面。', '2016-10-10 13:54:05', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('132', '黄金接连下跌难道又等大妈来托盘', null, null, null, null, '三年前，中国大妈们抢购黄金的记忆还历历在目，没想到如今的黄金又开始了一轮又一轮的下跌。', '2016-10-10 13:55:46', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('14', '习近平宁夏考察第一天：长征永远在路上', null, null, null, null, '习近平考察宁夏首站到固原，冒雨向红军长征会师纪念碑敬献花篮。', '2016-07-19 14:26:30', null, null, '0', null, null, '/u/cms/www/201607/19142033fu5h.jpg', null, null, '0', null, null);
INSERT INTO `cms_content_ext` VALUES ('140', '安卓游戏开发5险1金', null, null, null, null, null, '2016-10-10 14:27:44', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('141', '乌兰布统牧歌秋韵', null, null, null, null, null, '2016-10-11 09:44:23', null, null, '0', null, null, '/u/cms/www/201610/11093237z5e8.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('2', '法国尼斯发生汽车冲撞事故 已致75人死上百人伤', null, null, null, null, null, '2016-07-15 14:47:24', null, null, '0', null, null, '/u/cms/www/201607/15154249ucra.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('23', '“十一”黄金周凯里地区文化旅游活动精彩纷呈', null, null, '黔东南新闻网', 'http://qdnrbs.cn/htmls/shzh/20160913/137451.html', null, '2016-09-13 17:16:04', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('27', '“一带一路”：习近平打开的“筑梦空间”', null, null, null, null, '【学习进行时】在不久前举行的推进“一带一路”建设工作座谈会上，习近平要求：“以钉钉子精神抓下去，一步一步把‘一带一路’建设推向前进，让‘一带一路’建设造福沿线各国人民。”', '2016-09-22 16:30:13', null, 'CK', '0', null, null, '/u/cms/www/201609/23150119m7z0.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('28', '加拿大同意与中国协商引渡条约 转变抵制态度', null, null, null, null, '该项目声明，“中国专家将被邀请协助核查不被允许从中国内地来加拿大的人员的身份，”以便将他们遣返回国。', '2016-09-22 16:56:46', null, 'CK', '0', null, null, '/u/cms/www/201609/22165418ezkw.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('29', '深圳二手房卖主悔约不服判决 纠集60余人冲击法院', null, null, null, null, '新华社深圳9月22日专电（记者孙飞）记者22日从深圳市公安局福田分局获悉，深圳数名二手房卖主，因房价上涨不愿履行协议与买家发生纠纷，并对深圳市中院相关判决不满，聚集60余人前往深圳市中院闹访。', '2016-09-23 09:07:33', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('30', '不实名手机号停机在即 移动：不对任何社会渠道授权网络售卡', null, null, null, null, '中新网北京9月23日电 (吴涛)中新网从三大运营商处获悉，此前传北京地区未实名手机用户10月15日将被停机，实际是分批执行，10月15日开始，最晚至10月31日截止。', '2016-09-23 09:09:41', null, 'CK', '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('32', '台北故宫将拆除成龙所捐12兽首复制品 成龙回应', null, null, null, null, '成龙捐给台北故宫(微博)南院的12生肖兽首，面临斩首命运。', '2016-09-23 09:19:38', null, 'CK', '0', null, null, '/u/cms/www/201609/23091850z61y.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('33', '亚马逊股价首破800美元大关 市值稳居全球上市公司第四', null, null, null, null, '亚马逊的股价在纽约当地时间周四上午首次突破每股800美元大关。亚马逊以3860亿美元的市值稳居全球上市公司第四的位置，仅次于苹果、谷歌(微博)母公司Alphabet和微软', '2016-09-23 09:23:39', null, 'CK', '0', null, null, '/u/cms/www/201609/23092240hxg8.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('36', '海银系接盘匹凸匹谜局待解：P2P业务惨淡收场 多次被调查', null, null, null, null, '9月20日，匹凸匹投资者索赔案开庭，有十多位投资者向匹凸匹发起索赔。今年3月，匹凸匹公告，因未及时披露多项对外重大担保、重大诉讼事项及2013年年报中未披露对外重大事项，证监会对匹凸匹处40万元罚款，对鲜言处30万元罚款。前述投资者认为因虚假陈述行为而受到损失。', '2016-09-23 09:40:21', null, 'CK', '0', null, null, '/u/cms/www/201609/23093922giys.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('37', '苹果研发神秘新设备：体积类似Apple TV 支持NFC蓝牙', null, null, null, null, '近日，美国联邦通信委员会意外曝光了苹果正在研发的一款新设备，其体积类似于苹果机顶盒（Apple TV），具体的用途尚不得而知。', '2016-09-23 09:43:34', null, 'CK', '0', null, null, '/u/cms/www/201609/230942139uh5.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('39', '四川理塘县发生4.9级地震 震源深度19千米', null, null, null, null, '中国地震台网正式测定：09月23日00时47分在四川甘孜州理塘县（北纬30.09度，东经99.64度）发生4.9级地震，震源深度19千米。', '2016-09-23 09:54:17', null, 'CK', '0', null, null, '/u/cms/www/201609/23095358y6s6.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('43', 'web前端开发人员', null, null, null, null, null, '2016-09-26 09:33:15', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('44', 'asp.net开发工程师', null, null, null, null, null, '2016-09-26 09:34:35', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('45', '文案编辑', null, null, null, null, null, '2016-09-26 09:38:39', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('46', '项目经理', null, null, null, null, null, '2016-09-26 09:45:02', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('47', '3k计算机实习生双休', null, null, null, null, null, '2016-09-26 09:47:54', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('48', '急聘Android开发', null, null, null, null, null, '2016-09-26 09:50:04', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('49', 'IT公司招软件工程师助理', null, null, null, null, null, '2016-09-26 09:51:13', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('50', '聘网页前端设计3K双休', null, null, null, null, null, '2016-09-26 09:54:56', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('51', '诚聘微信推广专员', null, null, null, null, null, '2016-09-26 09:56:01', null, null, '0', null, null, null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('53', '北京天安门广场“祝福祖国”大花篮吊装完毕', null, null, null, null, null, '2016-09-26 10:19:13', '/u/cms/www/201610/08150638q3zr.mp4', 'CK', '0', null, null, '/u/cms/www/201609/26101908i5ds.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('55', '墨西哥一油轮失火', null, null, null, null, null, '2016-09-26 10:20:11', '/u/cms/www/201610/08150638q3zr.mp4', 'CK', '0', null, null, '/u/cms/www/201609/26102008qcao.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('57', '本网记者体验360行之【183】另类“复制”', null, null, null, null, null, '2016-09-26 10:21:19', '/u/cms/www/201610/08150638q3zr.mp4', 'CK', '0', null, null, '/u/cms/www/201609/26102115joze.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('59', '旅比大熊猫宝宝取名“天宝”', null, null, null, null, null, '2016-09-26 10:22:24', '/u/cms/www/201610/08150638q3zr.mp4', 'CK', '0', null, null, '/u/cms/www/201609/261022192w54.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('61', '莫斯科举办国际灯光节', null, null, null, null, null, '2016-09-26 10:29:06', '/u/cms/www/201610/08150638q3zr.mp4', 'CK', '0', null, null, '/u/cms/www/201609/26102904f2l8.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('63', '宇航员拍摄地球夜景：灯火辉煌灿烂 海水平滑如镜', null, null, null, null, null, '2016-09-26 10:30:05', '/u/cms/www/201610/08150638q3zr.mp4', 'CK', '0', null, null, '/u/cms/www/201609/26103003hrib.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('69', '腾讯QQ', null, null, null, null, null, '2016-09-26 16:37:28', null, null, '0', '/u/cms/www/201609/26164039i3uv.jpg', '/u/cms/www/201609/261640457r2n.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('7', '震撼的视角！2016年度无人机摄影大赛佳作大赏', null, null, null, null, null, '2016-07-15 15:33:26', null, null, '0', null, null, '/u/cms/www/201607/151530033koq.jpg', null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('71', '百度云管家', null, null, null, null, '百度公司推出的一款云服务产品。支持便捷地查看、上传、下载百度云端各类数据。通过百度云管家存入的文件，不会占用本地空间。上传、下载文件过程更稳定。', '2016-09-27 09:47:27', null, null, '0', '/u/cms/www/201609/27095816s3y8.jpg', '/u/cms/www/201609/270958122ljv.jpg', null, null, null, '1', null, null);
INSERT INTO `cms_content_ext` VALUES ('72', '360杀毒 5.0.0.7092 官方正式版', '360杀毒', null, null, null, '专业防护，专心为您。\r\n增强敲诈者病毒查杀，最新木马一网打尽;\r\n优化白名单机制，减少打扰;\r\n完善文件实时防护用户体验。', '2016-09-27 10:18:46', null, null, '0', '/u/cms/www/201609/27101524dxj7.jpg', '/u/cms/www/201609/27101514u0i9.jpg', '/u/cms/www/201609/27101510bh0o.jpg', null, null, '1', null, null);

-- ----------------------------
-- Table structure for cms_content_picture
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_picture`;
CREATE TABLE `cms_content_picture` (
  `contentId` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `imgPath` varchar(100) NOT NULL COMMENT '图片地址',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`contentId`,`priority`),
  CONSTRAINT `fk_jc_picture_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容图片表';

-- ----------------------------
-- Records of cms_content_picture
-- ----------------------------
INSERT INTO `cms_content_picture` VALUES ('114', '0', '/u/cms/www/201610/101310383u27.jpg', '科学家研制“隐身潜水服” 跟鲨鱼同游也不怕被发现');
INSERT INTO `cms_content_picture` VALUES ('114', '1', '/u/cms/www/201610/101311003aey.jpg', '科学家研制“隐身潜水服” 跟鲨鱼同游也不怕被发现');
INSERT INTO `cms_content_picture` VALUES ('114', '2', '/u/cms/www/201610/101311098e8w.jpg', '科学家研制“隐身潜水服” 跟鲨鱼同游也不怕被发现');
INSERT INTO `cms_content_picture` VALUES ('115', '0', '/u/cms/www/201610/10131516fhw7.jpg', '四川石渠发现至少30万公顷珍贵泥炭湿地资源');
INSERT INTO `cms_content_picture` VALUES ('115', '1', '/u/cms/www/201610/101315235s7b.jpg', '四川石渠发现至少30万公顷珍贵泥炭湿地资源');
INSERT INTO `cms_content_picture` VALUES ('115', '2', '/u/cms/www/201610/101315314qdt.jpg', '四川石渠发现至少30万公顷珍贵泥炭湿地资源');
INSERT INTO `cms_content_picture` VALUES ('116', '0', '/u/cms/www/201610/10131759kpti.jpg', '/u/cms/www/201610/10131742wzhv.jpg');
INSERT INTO `cms_content_picture` VALUES ('116', '1', '/u/cms/www/201610/10131811udfh.jpg', '/u/cms/www/201610/10131742wzhv.jpg');
INSERT INTO `cms_content_picture` VALUES ('117', '0', '/u/cms/www/201610/1013205987uh.jpg', '中俄“合作－2016”联合反恐训练圆满结束');
INSERT INTO `cms_content_picture` VALUES ('117', '1', '/u/cms/www/201610/10132108kvcd.jpg', '中俄“合作－2016”联合反恐训练圆满结束');
INSERT INTO `cms_content_picture` VALUES ('117', '2', '/u/cms/www/201610/10132117q0se.jpg', '中俄“合作－2016”联合反恐训练圆满结束');
INSERT INTO `cms_content_picture` VALUES ('117', '3', '/u/cms/www/201610/10132128e9mz.jpg', '中俄“合作－2016”联合反恐训练圆满结束');
INSERT INTO `cms_content_picture` VALUES ('117', '4', '/u/cms/www/201610/101321427ybi.jpg', '中俄“合作－2016”联合反恐训练圆满结束');
INSERT INTO `cms_content_picture` VALUES ('118', '0', '/u/cms/www/201610/10132256evzu.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '1', '/u/cms/www/201610/10132301wue2.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '2', '/u/cms/www/201610/101323052xt2.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '3', '/u/cms/www/201610/10132310d08y.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '4', '/u/cms/www/201610/10132315avlw.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '5', '/u/cms/www/201610/10132322exj1.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '6', '/u/cms/www/201610/10132327lk40.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '7', '/u/cms/www/201610/10132336658k.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '8', '/u/cms/www/201610/10132342mxeq.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('118', '9', '/u/cms/www/201610/10132347qr6a.jpg', '探访燕城监狱：蔬菜自己种 午餐两菜一汤');
INSERT INTO `cms_content_picture` VALUES ('119', '0', '/u/cms/www/201610/101326217xkx.jpg', '摄影师拍摄巨鳄破水而出吞食猎物 上演一箭双雕');
INSERT INTO `cms_content_picture` VALUES ('119', '1', '/u/cms/www/201610/10132629r00n.jpg', '摄影师拍摄巨鳄破水而出吞食猎物 上演一箭双雕');
INSERT INTO `cms_content_picture` VALUES ('119', '2', '/u/cms/www/201610/10132635f63e.jpg', '摄影师拍摄巨鳄破水而出吞食猎物 上演一箭双雕');
INSERT INTO `cms_content_picture` VALUES ('120', '0', '/u/cms/www/201610/10133454sydj.jpg', '宛如置身黑客帝国 “啤酒丛林”惊艳青岛海滨夜空');
INSERT INTO `cms_content_picture` VALUES ('120', '1', '/u/cms/www/201610/10133506taqx.jpg', '宛如置身黑客帝国 “啤酒丛林”惊艳青岛海滨夜空');
INSERT INTO `cms_content_picture` VALUES ('120', '2', '/u/cms/www/201610/10133511s7r1.jpg', '宛如置身黑客帝国 “啤酒丛林”惊艳青岛海滨夜空');
INSERT INTO `cms_content_picture` VALUES ('120', '3', '/u/cms/www/201610/101335147ye6.jpg', '宛如置身黑客帝国 “啤酒丛林”惊艳青岛海滨夜空');
INSERT INTO `cms_content_picture` VALUES ('13', '0', '/u/cms/www/201607/19141216070j.jpg', '7月18日，中国空军新闻发言人申进科大校在北京宣布：中国空军近日组织了航空兵赴南海战斗巡航。这次南海战巡，空军出动轰-6K飞机赴黄岩岛等岛礁附近空域进行了巡航。');
INSERT INTO `cms_content_picture` VALUES ('13', '1', '/u/cms/www/201607/19141242pppk.jpg', '申进科介绍，中国空军航空兵此次赴南海例行性战斗巡航，紧贴使命任务和实战准备，轰-6K和歼击机、侦察机、空中加油机等遂行战巡任务，以空中侦察、对抗空战和岛礁巡航为主要样式组织行动，达成了战斗巡航目的。\r\n申进科表示：“根据有效履行空军使命任务的需要，空军航空兵赴南海战斗巡航，将继续常态化进行。”');
INSERT INTO `cms_content_picture` VALUES ('141', '0', '/u/cms/www/201610/11094215kqsv.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '1', '/u/cms/www/201610/11094229szn0.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '2', '/u/cms/www/201610/11094234lj78.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '3', '/u/cms/www/201610/11094239ard1.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '4', '/u/cms/www/201610/11094243v534.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '5', '/u/cms/www/201610/11094246016h.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '6', '/u/cms/www/201610/11094250fq4t.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '7', '/u/cms/www/201610/11094254usxs.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '8', '/u/cms/www/201610/11094257nm5d.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '9', '/u/cms/www/201610/11094309iwnb.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('141', '10', '/u/cms/www/201610/11094419duy6.jpg', '乌兰布统牧歌秋韵');
INSERT INTO `cms_content_picture` VALUES ('2', '0', '/u/cms/www/201607/15154302mvu4.jpg', '当地时间2016年7月5日报道，美国宇航局“朱诺”号探测器成功进入环绕木星轨道，将展开探寻木星起源的任务。');
INSERT INTO `cms_content_picture` VALUES ('2', '1', '/u/cms/www/201607/15154338b6h9.jpg', '据悉，“朱诺”号将环绕木星运行20个月，收集有关该行星核心的数据，描绘其磁场，并测量大气中水和氨的含量。');
INSERT INTO `cms_content_picture` VALUES ('2', '2', '/u/cms/www/201607/15154354xp50.jpg', '另外，“朱诺”号还会观察木星表面著名的大红斑，一个持续了数百年的风暴，从而揭示其深层的秘密。');
INSERT INTO `cms_content_picture` VALUES ('2', '3', '/u/cms/www/201607/15154421y33m.jpg', '美国“朱诺”号探测器成功进入环绕木星轨道');
INSERT INTO `cms_content_picture` VALUES ('7', '0', '/u/cms/www/201607/15153012bpfd.jpg', '旅行类冠军：浓雾中的意大利阿西西圣弗兰西斯大教堂。摄影：Francesco Cattuto');
INSERT INTO `cms_content_picture` VALUES ('7', '1', '/u/cms/www/201607/15153034m1rr.jpg', '旅行类亚军：澳大利亚凯布尔海滩上的一队骆驼。摄影：Todd Kennedy');
INSERT INTO `cms_content_picture` VALUES ('7', '2', '/u/cms/www/201607/15153052j190.jpg', '旅行类季军：大加纳利岛上色彩斑斓的Playa de Amadores海滩。 摄影：Karolis Janulis');
INSERT INTO `cms_content_picture` VALUES ('7', '3', '/u/cms/www/201607/15153110yn2c.jpg', '自然与野生动物类冠军：丹麦Kalbyris森林。摄影：Michael Bernholdt');
INSERT INTO `cms_content_picture` VALUES ('7', '4', '/u/cms/www/201607/15153127qs41.jpg', '自然与野生动物类亚军：罗马尼亚Marpod公路旁的一大群羊。摄影：Szabolcs Ignacz');
INSERT INTO `cms_content_picture` VALUES ('7', '5', '/u/cms/www/201607/15153212rjvo.jpg', '自然与野生动物类季军：留尼旺岛富尔奈斯活火山喷发的壮观景象。摄影：Jonathan Payet');
INSERT INTO `cms_content_picture` VALUES ('7', '6', '/u/cms/www/201607/1515325323eh.jpg', '体育冒险类冠军：攀登者在美国犹他州莫泊峡谷120米处攀爬。摄影：Max Seigal');
INSERT INTO `cms_content_picture` VALUES ('7', '7', '/u/cms/www/201607/15153307441e.jpg', '体育冒险类亚军：在哥伦比亚库库塔举行的游泳比赛。摄影：Juan Pablo Bayona');
INSERT INTO `cms_content_picture` VALUES ('7', '8', '/u/cms/www/201607/15153321t1g3.jpg', '体育冒险类季军：在美国阿拉斯加州科尔多瓦沙滩上玩滑板。摄影：Tj Balon');

-- ----------------------------
-- Table structure for cms_content_record
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_record`;
CREATE TABLE `cms_content_record` (
  `contentRecordId` varchar(64) NOT NULL,
  `contentId` varchar(64) NOT NULL DEFAULT '0' COMMENT '文章ID',
  `userId` varchar(64) NOT NULL DEFAULT '0' COMMENT '操作人',
  `operateTime` datetime NOT NULL COMMENT '操作时间',
  `operateType` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 新增 1修改 2审核 3退回 4移动 5生成静态页 6删除到回收站 7归档 8出档 9推送共享',
  PRIMARY KEY (`contentRecordId`),
  KEY `fk_index_jc_content_record_content` (`contentId`),
  KEY `fk_index_jc_content_record_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章操作记录';

-- ----------------------------
-- Records of cms_content_record
-- ----------------------------

-- ----------------------------
-- Table structure for cms_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_tag`;
CREATE TABLE `cms_content_tag` (
  `tagId` varchar(64) NOT NULL,
  `tagName` varchar(50) NOT NULL COMMENT 'tag名称',
  `refCounter` int(11) NOT NULL DEFAULT '1' COMMENT '被引用的次数',
  PRIMARY KEY (`tagId`),
  UNIQUE KEY `ak_tag_name` (`tagName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容TAG表';

-- ----------------------------
-- Records of cms_content_tag
-- ----------------------------
INSERT INTO `cms_content_tag` VALUES ('10', '发生', '2');
INSERT INTO `cms_content_tag` VALUES ('102', '习近平', '3');
INSERT INTO `cms_content_tag` VALUES ('103', '恐', '1');
INSERT INTO `cms_content_tag` VALUES ('104', '袭', '1');
INSERT INTO `cms_content_tag` VALUES ('105', '事件', '1');
INSERT INTO `cms_content_tag` VALUES ('107', '向', '1');
INSERT INTO `cms_content_tag` VALUES ('108', '法', '1');
INSERT INTO `cms_content_tag` VALUES ('109', '总统', '1');
INSERT INTO `cms_content_tag` VALUES ('11', '汽车', '1');
INSERT INTO `cms_content_tag` VALUES ('110', '慰问电', '1');
INSERT INTO `cms_content_tag` VALUES ('111', '中国空军', '1');
INSERT INTO `cms_content_tag` VALUES ('112', '航空兵', '1');
INSERT INTO `cms_content_tag` VALUES ('113', '赴', '1');
INSERT INTO `cms_content_tag` VALUES ('114', '常态', '1');
INSERT INTO `cms_content_tag` VALUES ('115', '化', '1');
INSERT INTO `cms_content_tag` VALUES ('116', '战斗', '1');
INSERT INTO `cms_content_tag` VALUES ('117', '巡航', '1');
INSERT INTO `cms_content_tag` VALUES ('118', '宁夏', '1');
INSERT INTO `cms_content_tag` VALUES ('119', '考察', '1');
INSERT INTO `cms_content_tag` VALUES ('12', '冲撞', '1');
INSERT INTO `cms_content_tag` VALUES ('120', '第一天', '1');
INSERT INTO `cms_content_tag` VALUES ('121', '长征', '1');
INSERT INTO `cms_content_tag` VALUES ('122', '永', '1');
INSERT INTO `cms_content_tag` VALUES ('123', '远在', '1');
INSERT INTO `cms_content_tag` VALUES ('124', '路上', '1');
INSERT INTO `cms_content_tag` VALUES ('13', '事故', '1');
INSERT INTO `cms_content_tag` VALUES ('139', '十一', '1');
INSERT INTO `cms_content_tag` VALUES ('14', '已', '1');
INSERT INTO `cms_content_tag` VALUES ('140', '黄金周', '1');
INSERT INTO `cms_content_tag` VALUES ('141', '凯里', '1');
INSERT INTO `cms_content_tag` VALUES ('142', '地区', '1');
INSERT INTO `cms_content_tag` VALUES ('143', '文化', '1');
INSERT INTO `cms_content_tag` VALUES ('144', '旅游活动', '1');
INSERT INTO `cms_content_tag` VALUES ('145', '精彩纷呈', '1');
INSERT INTO `cms_content_tag` VALUES ('149', '一带', '1');
INSERT INTO `cms_content_tag` VALUES ('15', '致', '2');
INSERT INTO `cms_content_tag` VALUES ('150', '一路', '1');
INSERT INTO `cms_content_tag` VALUES ('151', '打开', '1');
INSERT INTO `cms_content_tag` VALUES ('152', '筑', '1');
INSERT INTO `cms_content_tag` VALUES ('153', '梦', '1');
INSERT INTO `cms_content_tag` VALUES ('154', '空间', '1');
INSERT INTO `cms_content_tag` VALUES ('155', '加拿大', '2');
INSERT INTO `cms_content_tag` VALUES ('156', '同意', '1');
INSERT INTO `cms_content_tag` VALUES ('157', '中国', '1');
INSERT INTO `cms_content_tag` VALUES ('158', '协商', '1');
INSERT INTO `cms_content_tag` VALUES ('159', '引渡', '1');
INSERT INTO `cms_content_tag` VALUES ('16', '75人', '1');
INSERT INTO `cms_content_tag` VALUES ('160', '条约', '1');
INSERT INTO `cms_content_tag` VALUES ('161', '转变', '1');
INSERT INTO `cms_content_tag` VALUES ('162', '抵制', '1');
INSERT INTO `cms_content_tag` VALUES ('163', '态度', '1');
INSERT INTO `cms_content_tag` VALUES ('164', '深圳', '1');
INSERT INTO `cms_content_tag` VALUES ('165', '二手房', '1');
INSERT INTO `cms_content_tag` VALUES ('166', '卖主', '1');
INSERT INTO `cms_content_tag` VALUES ('167', '悔', '1');
INSERT INTO `cms_content_tag` VALUES ('168', '约', '1');
INSERT INTO `cms_content_tag` VALUES ('169', '不服', '1');
INSERT INTO `cms_content_tag` VALUES ('17', '死', '1');
INSERT INTO `cms_content_tag` VALUES ('170', '判决', '1');
INSERT INTO `cms_content_tag` VALUES ('171', '纠集', '1');
INSERT INTO `cms_content_tag` VALUES ('172', '60', '1');
INSERT INTO `cms_content_tag` VALUES ('173', '余人', '1');
INSERT INTO `cms_content_tag` VALUES ('174', '冲击', '1');
INSERT INTO `cms_content_tag` VALUES ('175', '法院', '1');
INSERT INTO `cms_content_tag` VALUES ('176', '不实', '1');
INSERT INTO `cms_content_tag` VALUES ('177', '名', '1');
INSERT INTO `cms_content_tag` VALUES ('178', '手机号', '1');
INSERT INTO `cms_content_tag` VALUES ('179', '停机', '1');
INSERT INTO `cms_content_tag` VALUES ('18', '上百人', '1');
INSERT INTO `cms_content_tag` VALUES ('180', '在即', '1');
INSERT INTO `cms_content_tag` VALUES ('181', '移动', '1');
INSERT INTO `cms_content_tag` VALUES ('182', '不对', '1');
INSERT INTO `cms_content_tag` VALUES ('183', '任何', '1');
INSERT INTO `cms_content_tag` VALUES ('184', '社会', '1');
INSERT INTO `cms_content_tag` VALUES ('185', '渠道', '1');
INSERT INTO `cms_content_tag` VALUES ('186', '授权', '1');
INSERT INTO `cms_content_tag` VALUES ('187', '网络', '1');
INSERT INTO `cms_content_tag` VALUES ('188', '售', '1');
INSERT INTO `cms_content_tag` VALUES ('189', '卡', '1');
INSERT INTO `cms_content_tag` VALUES ('19', '伤', '1');
INSERT INTO `cms_content_tag` VALUES ('199', '亚马逊', '1');
INSERT INTO `cms_content_tag` VALUES ('200', '股价', '1');
INSERT INTO `cms_content_tag` VALUES ('201', '首', '1');
INSERT INTO `cms_content_tag` VALUES ('202', '800', '1');
INSERT INTO `cms_content_tag` VALUES ('203', '美元', '1');
INSERT INTO `cms_content_tag` VALUES ('204', '大关', '1');
INSERT INTO `cms_content_tag` VALUES ('205', '市值', '1');
INSERT INTO `cms_content_tag` VALUES ('206', '稳', '1');
INSERT INTO `cms_content_tag` VALUES ('207', '居', '1');
INSERT INTO `cms_content_tag` VALUES ('208', '全球', '1');
INSERT INTO `cms_content_tag` VALUES ('209', '上市公司', '1');
INSERT INTO `cms_content_tag` VALUES ('21', '拍摄', '2');
INSERT INTO `cms_content_tag` VALUES ('210', '第四', '1');
INSERT INTO `cms_content_tag` VALUES ('212', '百度', '2');
INSERT INTO `cms_content_tag` VALUES ('230', '海', '1');
INSERT INTO `cms_content_tag` VALUES ('231', '银', '1');
INSERT INTO `cms_content_tag` VALUES ('232', '系', '1');
INSERT INTO `cms_content_tag` VALUES ('233', '接', '1');
INSERT INTO `cms_content_tag` VALUES ('234', '盘', '1');
INSERT INTO `cms_content_tag` VALUES ('235', '匹', '2');
INSERT INTO `cms_content_tag` VALUES ('236', '凸', '1');
INSERT INTO `cms_content_tag` VALUES ('237', '谜', '1');
INSERT INTO `cms_content_tag` VALUES ('238', '局', '1');
INSERT INTO `cms_content_tag` VALUES ('239', '待', '1');
INSERT INTO `cms_content_tag` VALUES ('24', '破', '2');
INSERT INTO `cms_content_tag` VALUES ('240', '解', '1');
INSERT INTO `cms_content_tag` VALUES ('241', 'p2p', '1');
INSERT INTO `cms_content_tag` VALUES ('242', '业务', '1');
INSERT INTO `cms_content_tag` VALUES ('243', '惨淡', '1');
INSERT INTO `cms_content_tag` VALUES ('244', '收场', '1');
INSERT INTO `cms_content_tag` VALUES ('245', '多次', '1');
INSERT INTO `cms_content_tag` VALUES ('246', '调查', '1');
INSERT INTO `cms_content_tag` VALUES ('247', '苹果', '1');
INSERT INTO `cms_content_tag` VALUES ('248', '研发', '1');
INSERT INTO `cms_content_tag` VALUES ('249', '神秘', '1');
INSERT INTO `cms_content_tag` VALUES ('250', '新', '1');
INSERT INTO `cms_content_tag` VALUES ('251', '设备', '1');
INSERT INTO `cms_content_tag` VALUES ('252', '体积', '1');
INSERT INTO `cms_content_tag` VALUES ('253', '类似', '1');
INSERT INTO `cms_content_tag` VALUES ('254', 'apple', '1');
INSERT INTO `cms_content_tag` VALUES ('255', 'tv', '1');
INSERT INTO `cms_content_tag` VALUES ('256', '支持', '1');
INSERT INTO `cms_content_tag` VALUES ('257', 'nfc', '1');
INSERT INTO `cms_content_tag` VALUES ('258', '蓝牙', '1');
INSERT INTO `cms_content_tag` VALUES ('269', '一', '2');
INSERT INTO `cms_content_tag` VALUES ('271', '理塘县', '1');
INSERT INTO `cms_content_tag` VALUES ('272', '4.9级', '1');
INSERT INTO `cms_content_tag` VALUES ('273', '地震', '1');
INSERT INTO `cms_content_tag` VALUES ('274', '震源', '1');
INSERT INTO `cms_content_tag` VALUES ('275', '深度', '1');
INSERT INTO `cms_content_tag` VALUES ('276', '19千米', '1');
INSERT INTO `cms_content_tag` VALUES ('286', 'web', '1');
INSERT INTO `cms_content_tag` VALUES ('287', '前端', '1');
INSERT INTO `cms_content_tag` VALUES ('288', '开发人员', '1');
INSERT INTO `cms_content_tag` VALUES ('289', 'asp.net', '1');
INSERT INTO `cms_content_tag` VALUES ('290', '开发', '1');
INSERT INTO `cms_content_tag` VALUES ('291', '工程师', '1');
INSERT INTO `cms_content_tag` VALUES ('292', '文案', '1');
INSERT INTO `cms_content_tag` VALUES ('293', '编辑', '1');
INSERT INTO `cms_content_tag` VALUES ('298', '双休', '6');
INSERT INTO `cms_content_tag` VALUES ('3', '2016', '2');
INSERT INTO `cms_content_tag` VALUES ('322', '北京', '2');
INSERT INTO `cms_content_tag` VALUES ('323', '天安门广场', '1');
INSERT INTO `cms_content_tag` VALUES ('324', '祝福', '1');
INSERT INTO `cms_content_tag` VALUES ('325', '祖国', '1');
INSERT INTO `cms_content_tag` VALUES ('326', '大', '1');
INSERT INTO `cms_content_tag` VALUES ('327', '花篮', '1');
INSERT INTO `cms_content_tag` VALUES ('328', '吊装', '1');
INSERT INTO `cms_content_tag` VALUES ('329', '完毕', '1');
INSERT INTO `cms_content_tag` VALUES ('340', '墨西哥', '1');
INSERT INTO `cms_content_tag` VALUES ('341', '油轮', '1');
INSERT INTO `cms_content_tag` VALUES ('342', '失火', '1');
INSERT INTO `cms_content_tag` VALUES ('345', '南瓜', '2');
INSERT INTO `cms_content_tag` VALUES ('351', '本', '1');
INSERT INTO `cms_content_tag` VALUES ('352', '网', '1');
INSERT INTO `cms_content_tag` VALUES ('353', '记者', '1');
INSERT INTO `cms_content_tag` VALUES ('354', '体验', '1');
INSERT INTO `cms_content_tag` VALUES ('355', '360行', '1');
INSERT INTO `cms_content_tag` VALUES ('356', '183', '1');
INSERT INTO `cms_content_tag` VALUES ('357', '另类', '1');
INSERT INTO `cms_content_tag` VALUES ('358', '复制', '1');
INSERT INTO `cms_content_tag` VALUES ('365', '旅', '1');
INSERT INTO `cms_content_tag` VALUES ('366', '比大', '1');
INSERT INTO `cms_content_tag` VALUES ('367', '熊猫', '1');
INSERT INTO `cms_content_tag` VALUES ('368', '宝宝', '1');
INSERT INTO `cms_content_tag` VALUES ('369', '取名', '1');
INSERT INTO `cms_content_tag` VALUES ('370', '天宝', '1');
INSERT INTO `cms_content_tag` VALUES ('375', '莫斯科', '1');
INSERT INTO `cms_content_tag` VALUES ('376', '举办', '1');
INSERT INTO `cms_content_tag` VALUES ('377', '国际', '1');
INSERT INTO `cms_content_tag` VALUES ('378', '灯光', '1');
INSERT INTO `cms_content_tag` VALUES ('379', '节', '1');
INSERT INTO `cms_content_tag` VALUES ('388', '宇航员', '1');
INSERT INTO `cms_content_tag` VALUES ('389', '地球', '1');
INSERT INTO `cms_content_tag` VALUES ('390', '夜景', '1');
INSERT INTO `cms_content_tag` VALUES ('391', '灯火', '1');
INSERT INTO `cms_content_tag` VALUES ('392', '辉煌灿烂', '1');
INSERT INTO `cms_content_tag` VALUES ('393', '海水', '1');
INSERT INTO `cms_content_tag` VALUES ('394', '平滑', '1');
INSERT INTO `cms_content_tag` VALUES ('395', '如', '1');
INSERT INTO `cms_content_tag` VALUES ('396', '镜', '1');
INSERT INTO `cms_content_tag` VALUES ('398', '妖', '2');
INSERT INTO `cms_content_tag` VALUES ('399', '倾城', '2');
INSERT INTO `cms_content_tag` VALUES ('414', '诗', '2');
INSERT INTO `cms_content_tag` VALUES ('42', '四川', '2');
INSERT INTO `cms_content_tag` VALUES ('435', '高性能', '1');
INSERT INTO `cms_content_tag` VALUES ('436', '年终奖', '5');
INSERT INTO `cms_content_tag` VALUES ('437', '五险一金', '5');
INSERT INTO `cms_content_tag` VALUES ('438', '带薪年假', '5');
INSERT INTO `cms_content_tag` VALUES ('439', '节日福利', '4');
INSERT INTO `cms_content_tag` VALUES ('500', '专访', '1');
INSERT INTO `cms_content_tag` VALUES ('501', '科学家', '2');
INSERT INTO `cms_content_tag` VALUES ('502', '徐', '1');
INSERT INTO `cms_content_tag` VALUES ('503', '伟', '1');
INSERT INTO `cms_content_tag` VALUES ('504', '谷歌', '1');
INSERT INTO `cms_content_tag` VALUES ('505', '人工智能', '1');
INSERT INTO `cms_content_tag` VALUES ('506', '平台', '1');
INSERT INTO `cms_content_tag` VALUES ('507', '更易', '1');
INSERT INTO `cms_content_tag` VALUES ('508', '上手', '1');
INSERT INTO `cms_content_tag` VALUES ('509', '电信', '1');
INSERT INTO `cms_content_tag` VALUES ('510', '诈骗', '1');
INSERT INTO `cms_content_tag` VALUES ('511', '多发', '1');
INSERT INTO `cms_content_tag` VALUES ('512', '产生', '1');
INSERT INTO `cms_content_tag` VALUES ('513', '蝴蝶效应', '1');
INSERT INTO `cms_content_tag` VALUES ('514', '虚拟', '1');
INSERT INTO `cms_content_tag` VALUES ('515', '运营商', '1');
INSERT INTO `cms_content_tag` VALUES ('516', '融资', '1');
INSERT INTO `cms_content_tag` VALUES ('517', '受', '1');
INSERT INTO `cms_content_tag` VALUES ('518', '波及', '1');
INSERT INTO `cms_content_tag` VALUES ('519', '李克强', '2');
INSERT INTO `cms_content_tag` VALUES ('520', '联合国', '1');
INSERT INTO `cms_content_tag` VALUES ('521', '承诺', '1');
INSERT INTO `cms_content_tag` VALUES ('522', '援助', '1');
INSERT INTO `cms_content_tag` VALUES ('523', '难民', '1');
INSERT INTO `cms_content_tag` VALUES ('524', '这是', '1');
INSERT INTO `cms_content_tag` VALUES ('525', '责任', '1');
INSERT INTO `cms_content_tag` VALUES ('526', '道义', '1');
INSERT INTO `cms_content_tag` VALUES ('527', '担当', '1');
INSERT INTO `cms_content_tag` VALUES ('528', '研制', '1');
INSERT INTO `cms_content_tag` VALUES ('529', '隐身', '1');
INSERT INTO `cms_content_tag` VALUES ('530', '潜水', '1');
INSERT INTO `cms_content_tag` VALUES ('531', '服', '1');
INSERT INTO `cms_content_tag` VALUES ('532', '跟', '1');
INSERT INTO `cms_content_tag` VALUES ('533', '鲨鱼', '1');
INSERT INTO `cms_content_tag` VALUES ('534', '同游', '1');
INSERT INTO `cms_content_tag` VALUES ('535', '也', '1');
INSERT INTO `cms_content_tag` VALUES ('536', '不怕', '1');
INSERT INTO `cms_content_tag` VALUES ('537', '发现', '2');
INSERT INTO `cms_content_tag` VALUES ('538', '石渠', '1');
INSERT INTO `cms_content_tag` VALUES ('539', '至少', '1');
INSERT INTO `cms_content_tag` VALUES ('540', '30万公顷', '1');
INSERT INTO `cms_content_tag` VALUES ('541', '珍贵', '1');
INSERT INTO `cms_content_tag` VALUES ('542', '泥炭', '1');
INSERT INTO `cms_content_tag` VALUES ('543', '湿地', '1');
INSERT INTO `cms_content_tag` VALUES ('544', '资源', '1');
INSERT INTO `cms_content_tag` VALUES ('545', '全世界', '1');
INSERT INTO `cms_content_tag` VALUES ('546', '最', '2');
INSERT INTO `cms_content_tag` VALUES ('547', '孤独', '1');
INSERT INTO `cms_content_tag` VALUES ('548', '咖啡馆', '1');
INSERT INTO `cms_content_tag` VALUES ('549', '海拔', '1');
INSERT INTO `cms_content_tag` VALUES ('550', '4860米', '1');
INSERT INTO `cms_content_tag` VALUES ('551', '达', '1');
INSERT INTO `cms_content_tag` VALUES ('552', '古', '1');
INSERT INTO `cms_content_tag` VALUES ('553', '冰川', '1');
INSERT INTO `cms_content_tag` VALUES ('554', '巅', '1');
INSERT INTO `cms_content_tag` VALUES ('555', '中俄', '1');
INSERT INTO `cms_content_tag` VALUES ('556', '合作', '1');
INSERT INTO `cms_content_tag` VALUES ('557', '联合', '1');
INSERT INTO `cms_content_tag` VALUES ('558', '反恐', '1');
INSERT INTO `cms_content_tag` VALUES ('559', '训练', '2');
INSERT INTO `cms_content_tag` VALUES ('560', '圆满结束', '1');
INSERT INTO `cms_content_tag` VALUES ('561', '探访', '1');
INSERT INTO `cms_content_tag` VALUES ('562', '燕', '1');
INSERT INTO `cms_content_tag` VALUES ('563', '城', '1');
INSERT INTO `cms_content_tag` VALUES ('564', '监狱', '1');
INSERT INTO `cms_content_tag` VALUES ('565', '蔬菜', '1');
INSERT INTO `cms_content_tag` VALUES ('566', '自己', '1');
INSERT INTO `cms_content_tag` VALUES ('567', '种', '2');
INSERT INTO `cms_content_tag` VALUES ('568', '午餐', '1');
INSERT INTO `cms_content_tag` VALUES ('569', '两', '1');
INSERT INTO `cms_content_tag` VALUES ('570', '菜', '1');
INSERT INTO `cms_content_tag` VALUES ('571', '汤', '1');
INSERT INTO `cms_content_tag` VALUES ('572', '摄影师', '1');
INSERT INTO `cms_content_tag` VALUES ('573', '巨', '1');
INSERT INTO `cms_content_tag` VALUES ('574', '鳄', '1');
INSERT INTO `cms_content_tag` VALUES ('575', '水', '1');
INSERT INTO `cms_content_tag` VALUES ('576', '而出', '1');
INSERT INTO `cms_content_tag` VALUES ('577', '吞食', '1');
INSERT INTO `cms_content_tag` VALUES ('578', '猎物', '1');
INSERT INTO `cms_content_tag` VALUES ('579', '上演', '1');
INSERT INTO `cms_content_tag` VALUES ('580', '一箭双雕', '1');
INSERT INTO `cms_content_tag` VALUES ('581', '宛如', '1');
INSERT INTO `cms_content_tag` VALUES ('582', '置身', '1');
INSERT INTO `cms_content_tag` VALUES ('583', '黑客帝国', '1');
INSERT INTO `cms_content_tag` VALUES ('584', '啤酒', '1');
INSERT INTO `cms_content_tag` VALUES ('585', '丛林', '1');
INSERT INTO `cms_content_tag` VALUES ('586', '惊艳', '1');
INSERT INTO `cms_content_tag` VALUES ('587', '青岛', '1');
INSERT INTO `cms_content_tag` VALUES ('588', '海滨', '1');
INSERT INTO `cms_content_tag` VALUES ('589', '夜空', '1');
INSERT INTO `cms_content_tag` VALUES ('59', '之', '2');
INSERT INTO `cms_content_tag` VALUES ('590', '半', '1');
INSERT INTO `cms_content_tag` VALUES ('591', '爱与恨', '1');
INSERT INTO `cms_content_tag` VALUES ('592', '追', '1');
INSERT INTO `cms_content_tag` VALUES ('593', '逃', '1');
INSERT INTO `cms_content_tag` VALUES ('594', '人', '1');
INSERT INTO `cms_content_tag` VALUES ('595', '再一次', '1');
INSERT INTO `cms_content_tag` VALUES ('596', '掀起', '1');
INSERT INTO `cms_content_tag` VALUES ('597', '一段', '1');
INSERT INTO `cms_content_tag` VALUES ('598', '惊人', '1');
INSERT INTO `cms_content_tag` VALUES ('599', '传奇', '1');
INSERT INTO `cms_content_tag` VALUES ('600', '那年', '1');
INSERT INTO `cms_content_tag` VALUES ('601', '青春', '1');
INSERT INTO `cms_content_tag` VALUES ('602', '我们', '1');
INSERT INTO `cms_content_tag` VALUES ('603', '正好', '1');
INSERT INTO `cms_content_tag` VALUES ('604', '刘', '1');
INSERT INTO `cms_content_tag` VALUES ('605', '丹', '1');
INSERT INTO `cms_content_tag` VALUES ('606', '妮', '1');
INSERT INTO `cms_content_tag` VALUES ('607', '演绎', '1');
INSERT INTO `cms_content_tag` VALUES ('608', '闺', '1');
INSERT INTO `cms_content_tag` VALUES ('609', '蜜', '1');
INSERT INTO `cms_content_tag` VALUES ('61', '震撼', '1');
INSERT INTO `cms_content_tag` VALUES ('610', '奔', '1');
INSERT INTO `cms_content_tag` VALUES ('611', '跑吧', '1');
INSERT INTO `cms_content_tag` VALUES ('612', '兄弟', '1');
INSERT INTO `cms_content_tag` VALUES ('613', '收', '1');
INSERT INTO `cms_content_tag` VALUES ('614', '官', '1');
INSERT INTO `cms_content_tag` VALUES ('615', '对决', '1');
INSERT INTO `cms_content_tag` VALUES ('616', '一触即发', '1');
INSERT INTO `cms_content_tag` VALUES ('617', '邓', '1');
INSERT INTO `cms_content_tag` VALUES ('618', '超', '1');
INSERT INTO `cms_content_tag` VALUES ('619', '岳', '1');
INSERT INTO `cms_content_tag` VALUES ('62', '视角', '1');
INSERT INTO `cms_content_tag` VALUES ('620', '云', '1');
INSERT INTO `cms_content_tag` VALUES ('621', '鹏', '1');
INSERT INTO `cms_content_tag` VALUES ('622', '压垮', '1');
INSERT INTO `cms_content_tag` VALUES ('623', '惨叫', '1');
INSERT INTO `cms_content_tag` VALUES ('624', '出席', '1');
INSERT INTO `cms_content_tag` VALUES ('625', '总理', '1');
INSERT INTO `cms_content_tag` VALUES ('626', '家宴', '1');
INSERT INTO `cms_content_tag` VALUES ('627', '小小', '1');
INSERT INTO `cms_content_tag` VALUES ('628', '特', '2');
INSERT INTO `cms_content_tag` VALUES ('629', '鲁', '1');
INSERT INTO `cms_content_tag` VALUES ('63', '年度', '1');
INSERT INTO `cms_content_tag` VALUES ('630', '多', '1');
INSERT INTO `cms_content_tag` VALUES ('631', '表演', '1');
INSERT INTO `cms_content_tag` VALUES ('632', '后空翻', '1');
INSERT INTO `cms_content_tag` VALUES ('633', '习', '1');
INSERT INTO `cms_content_tag` VALUES ('634', '主席', '1');
INSERT INTO `cms_content_tag` VALUES ('635', '中央军委', '1');
INSERT INTO `cms_content_tag` VALUES ('636', '领导', '1');
INSERT INTO `cms_content_tag` VALUES ('637', '推进', '2');
INSERT INTO `cms_content_tag` VALUES ('638', '军队', '1');
INSERT INTO `cms_content_tag` VALUES ('639', '战斗力', '1');
INSERT INTO `cms_content_tag` VALUES ('64', '无人机', '1');
INSERT INTO `cms_content_tag` VALUES ('640', '建设', '1');
INSERT INTO `cms_content_tag` VALUES ('641', '纪实', '2');
INSERT INTO `cms_content_tag` VALUES ('642', '西北', '1');
INSERT INTO `cms_content_tag` VALUES ('643', '第一村', '1');
INSERT INTO `cms_content_tag` VALUES ('644', '白', '1');
INSERT INTO `cms_content_tag` VALUES ('645', '哈', '1');
INSERT INTO `cms_content_tag` VALUES ('646', '巴', '1');
INSERT INTO `cms_content_tag` VALUES ('647', '早秋', '1');
INSERT INTO `cms_content_tag` VALUES ('648', '美国', '1');
INSERT INTO `cms_content_tag` VALUES ('649', '金秋', '1');
INSERT INTO `cms_content_tag` VALUES ('65', '摄影', '1');
INSERT INTO `cms_content_tag` VALUES ('650', '不止', '1');
INSERT INTO `cms_content_tag` VALUES ('651', '红叶', '1');
INSERT INTO `cms_content_tag` VALUES ('652', '德国', '1');
INSERT INTO `cms_content_tag` VALUES ('653', '举行', '1');
INSERT INTO `cms_content_tag` VALUES ('654', '称重', '1');
INSERT INTO `cms_content_tag` VALUES ('655', '比赛', '1');
INSERT INTO `cms_content_tag` VALUES ('656', '763公斤', '1');
INSERT INTO `cms_content_tag` VALUES ('657', '获得', '1');
INSERT INTO `cms_content_tag` VALUES ('658', '加冕', '1');
INSERT INTO `cms_content_tag` VALUES ('659', '战', '1');
INSERT INTO `cms_content_tag` VALUES ('66', '大赛', '1');
INSERT INTO `cms_content_tag` VALUES ('660', '队员', '1');
INSERT INTO `cms_content_tag` VALUES ('661', '魔鬼', '1');
INSERT INTO `cms_content_tag` VALUES ('662', '周', '1');
INSERT INTO `cms_content_tag` VALUES ('663', '挑战', '1');
INSERT INTO `cms_content_tag` VALUES ('664', '50项', '1');
INSERT INTO `cms_content_tag` VALUES ('665', '严苛', '1');
INSERT INTO `cms_content_tag` VALUES ('666', '极限', '1');
INSERT INTO `cms_content_tag` VALUES ('667', '项目', '1');
INSERT INTO `cms_content_tag` VALUES ('668', '党中央', '1');
INSERT INTO `cms_content_tag` VALUES ('669', '民族', '1');
INSERT INTO `cms_content_tag` VALUES ('67', '佳作', '1');
INSERT INTO `cms_content_tag` VALUES ('670', '工作', '1');
INSERT INTO `cms_content_tag` VALUES ('671', '创新', '1');
INSERT INTO `cms_content_tag` VALUES ('672', '发展', '1');
INSERT INTO `cms_content_tag` VALUES ('673', '楼市', '1');
INSERT INTO `cms_content_tag` VALUES ('674', '调控', '1');
INSERT INTO `cms_content_tag` VALUES ('675', '应', '1');
INSERT INTO `cms_content_tag` VALUES ('676', '跳出', '1');
INSERT INTO `cms_content_tag` VALUES ('677', '周期性', '1');
INSERT INTO `cms_content_tag` VALUES ('678', '怪圈', '1');
INSERT INTO `cms_content_tag` VALUES ('679', '黄金', '1');
INSERT INTO `cms_content_tag` VALUES ('68', '大赏', '1');
INSERT INTO `cms_content_tag` VALUES ('680', '接连', '1');
INSERT INTO `cms_content_tag` VALUES ('681', '下跌', '1');
INSERT INTO `cms_content_tag` VALUES ('682', '难道', '1');
INSERT INTO `cms_content_tag` VALUES ('683', '大妈', '1');
INSERT INTO `cms_content_tag` VALUES ('684', '来', '1');
INSERT INTO `cms_content_tag` VALUES ('685', '托盘', '1');
INSERT INTO `cms_content_tag` VALUES ('71', '南海', '1');
INSERT INTO `cms_content_tag` VALUES ('711', '乌兰', '1');
INSERT INTO `cms_content_tag` VALUES ('712', '布', '1');
INSERT INTO `cms_content_tag` VALUES ('713', '统', '1');
INSERT INTO `cms_content_tag` VALUES ('714', '牧歌', '1');
INSERT INTO `cms_content_tag` VALUES ('715', '秋韵', '1');
INSERT INTO `cms_content_tag` VALUES ('8', '法国', '2');
INSERT INTO `cms_content_tag` VALUES ('9', '尼斯', '2');

-- ----------------------------
-- Table structure for cms_content_topic
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_topic`;
CREATE TABLE `cms_content_topic` (
  `contentId` varchar(64) NOT NULL,
  `topicId` varchar(64) NOT NULL,
  PRIMARY KEY (`contentId`,`topicId`),
  KEY `fk_jc_content_topic` (`topicId`),
  CONSTRAINT `fk_jc_content_topic` FOREIGN KEY (`topicId`) REFERENCES `cms_topic` (`topicId`),
  CONSTRAINT `fk_jc_topic_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS专题内容关联表';

-- ----------------------------
-- Records of cms_content_topic
-- ----------------------------
INSERT INTO `cms_content_topic` VALUES ('11', '1');
INSERT INTO `cms_content_topic` VALUES ('112', '1');
INSERT INTO `cms_content_topic` VALUES ('113', '1');
INSERT INTO `cms_content_topic` VALUES ('114', '1');
INSERT INTO `cms_content_topic` VALUES ('115', '1');
INSERT INTO `cms_content_topic` VALUES ('116', '1');
INSERT INTO `cms_content_topic` VALUES ('117', '1');
INSERT INTO `cms_content_topic` VALUES ('118', '1');
INSERT INTO `cms_content_topic` VALUES ('119', '1');
INSERT INTO `cms_content_topic` VALUES ('12', '1');
INSERT INTO `cms_content_topic` VALUES ('120', '1');
INSERT INTO `cms_content_topic` VALUES ('13', '1');
INSERT INTO `cms_content_topic` VALUES ('130', '1');
INSERT INTO `cms_content_topic` VALUES ('131', '1');
INSERT INTO `cms_content_topic` VALUES ('132', '1');
INSERT INTO `cms_content_topic` VALUES ('14', '1');
INSERT INTO `cms_content_topic` VALUES ('141', '1');
INSERT INTO `cms_content_topic` VALUES ('2', '1');
INSERT INTO `cms_content_topic` VALUES ('27', '1');
INSERT INTO `cms_content_topic` VALUES ('28', '1');
INSERT INTO `cms_content_topic` VALUES ('32', '1');
INSERT INTO `cms_content_topic` VALUES ('7', '1');
INSERT INTO `cms_content_topic` VALUES ('11', '2');
INSERT INTO `cms_content_topic` VALUES ('112', '2');
INSERT INTO `cms_content_topic` VALUES ('113', '2');
INSERT INTO `cms_content_topic` VALUES ('114', '2');
INSERT INTO `cms_content_topic` VALUES ('115', '2');
INSERT INTO `cms_content_topic` VALUES ('116', '2');
INSERT INTO `cms_content_topic` VALUES ('117', '2');
INSERT INTO `cms_content_topic` VALUES ('118', '2');
INSERT INTO `cms_content_topic` VALUES ('119', '2');
INSERT INTO `cms_content_topic` VALUES ('12', '2');
INSERT INTO `cms_content_topic` VALUES ('120', '2');
INSERT INTO `cms_content_topic` VALUES ('13', '2');
INSERT INTO `cms_content_topic` VALUES ('131', '2');
INSERT INTO `cms_content_topic` VALUES ('132', '2');
INSERT INTO `cms_content_topic` VALUES ('14', '2');
INSERT INTO `cms_content_topic` VALUES ('2', '2');
INSERT INTO `cms_content_topic` VALUES ('27', '2');
INSERT INTO `cms_content_topic` VALUES ('28', '2');
INSERT INTO `cms_content_topic` VALUES ('32', '2');
INSERT INTO `cms_content_topic` VALUES ('7', '2');
INSERT INTO `cms_content_topic` VALUES ('11', '3');
INSERT INTO `cms_content_topic` VALUES ('112', '3');
INSERT INTO `cms_content_topic` VALUES ('113', '3');
INSERT INTO `cms_content_topic` VALUES ('114', '3');
INSERT INTO `cms_content_topic` VALUES ('115', '3');
INSERT INTO `cms_content_topic` VALUES ('116', '3');
INSERT INTO `cms_content_topic` VALUES ('117', '3');
INSERT INTO `cms_content_topic` VALUES ('118', '3');
INSERT INTO `cms_content_topic` VALUES ('119', '3');
INSERT INTO `cms_content_topic` VALUES ('12', '3');
INSERT INTO `cms_content_topic` VALUES ('120', '3');
INSERT INTO `cms_content_topic` VALUES ('13', '3');
INSERT INTO `cms_content_topic` VALUES ('131', '3');
INSERT INTO `cms_content_topic` VALUES ('132', '3');
INSERT INTO `cms_content_topic` VALUES ('14', '3');
INSERT INTO `cms_content_topic` VALUES ('2', '3');
INSERT INTO `cms_content_topic` VALUES ('27', '3');
INSERT INTO `cms_content_topic` VALUES ('28', '3');
INSERT INTO `cms_content_topic` VALUES ('32', '3');
INSERT INTO `cms_content_topic` VALUES ('7', '3');

-- ----------------------------
-- Table structure for cms_content_txt
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_txt`;
CREATE TABLE `cms_content_txt` (
  `contentId` varchar(64) NOT NULL,
  `txt` longtext COMMENT '文章内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY (`contentId`),
  CONSTRAINT `fk_jc_txt_content` FOREIGN KEY (`contentId`) REFERENCES `cms_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容文本表';

-- ----------------------------
-- Records of cms_content_txt
-- ----------------------------
INSERT INTO `cms_content_txt` VALUES ('100', '<p>《诺亚传说》是尚游游戏自主研发的大型角色扮演网游，新资料片《诺亚传说前传-亚特兰蒂斯》火爆发布。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('101', '<p>迅雷9是“深圳市迅雷网络技术有限公司”于2016年推出的“迅雷”系列下载软件的最新换代产品。迅雷9采用全新下载引擎，对百兆光纤宽带网络环境进行诸多针对性的优化，让用户获得更卓越的下载体验；全新的界面功能布局，承载了更丰富的内容，打造找、下、用的一站式娱乐消费平台。</p><p>开发商：深圳市迅雷网络技术有限公司</p><p>软件官网：&nbsp;http://xl9.xunlei.com</p><p>新版特征</p><p>1、全新上线资源评论功能，进入资源详情页，参与精彩讨论；<br/>2、新增磁盘缓存设置功能；<br/>3、个性化中心新增“人气”“精品”“超级会员”提示，帮你找到个性化皮肤和头像。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('102', '<p>软件介绍：<br/>&nbsp; 1.360安全卫士是一款由奇虎360公司推出的功能强、效果好、受用户欢迎的安全杀毒软件<br/>&nbsp; 2.目前4.2亿中国网民中，首选安装360的已超过3亿<br/>&nbsp; 3.拥有查杀木马、清理插件、修复漏洞、电脑体检、电脑救援、保护隐私，电脑专家，清理垃圾，清理痕迹多种功能<br/>&nbsp; 4.独创了&amp;ldquo;木马防火墙&quot;&amp;ldquo;360密盘&amp;rdquo;等功能<br/>&nbsp; 5.依靠抢先侦测和云端鉴别，可全面、智能地拦截各类木马，保护用户的帐号、隐私等重要信息<br/>&nbsp; 6.内含的360软件管家还可帮助用户轻松下载、升级和强力卸载各种应用软件</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('103', '<p>搜狗（sougou）拼音输入法是一款Windows平台下的汉字拼音输入法。搜狗拼音输入法是基于搜索引擎技术的、特别适合网民使用的、新一代的输入法产品，用户可以通过互联网备份自己的个性化词库和配置信息。</p><p>开发商：搜狗</p><p>软件官网：&nbsp;http://pinyin.sogou.com/</p><p>新版特征</p><p>升级日志：<br/>1、标点补全：对（）、“”等成对的符号自动补全；<br/>2、图片表情：<br/>a）更新emoji资源和候选位置；<br/>b）支持表情包名搜索；<br/>3、拆分输入：增加更多拆分输入数据；<br/>4、网址直达：增加更多的网址数据；<br/>5、工具箱：更新工具箱图标，界面更美观；<br/>6、解决自定义短语、固定首位等删除之后同步又出现的问题。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('104', '<p>歪歪语音是广州多玩信息技术有限公司研发的一款基于Internet 团队语音通信平台，功能强大、音质清晰、安全稳定、不占资源、适应游戏玩家的免费语音软件。在网络上通常用YY表示。</p><p>开发商：多玩游戏</p><p>软件官网：&nbsp;http://www.yy.com/index/t/download</p><p>新版特征</p><p>优化了部分内容。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('105', '<p>阿里旺旺专为淘宝会员量身定做的个人交易沟通软件，方便买家和卖家在交易过程实时进行沟通。可以进行文字聊天、语音聊天、视频聊天、文件传输、发送离线文件等。有了它，用户就算有万水千山阻隔亦可零距离地与卖家交流宝贝细节，尽情“砍价”了！</p><p>开发商：阿里巴巴</p><p>软件官网：&nbsp;http://www.taobao.com/wangwang/</p><p>新版特征</p><p>1. 全新界面，清晰的圆形头像、轻薄的图标设计、界面结构更加简洁；<br/>2. 全新会话面板，汇总各类消息提醒，处理更高效；&nbsp;<br/>3. 皮肤设置升级，新增多款皮肤主题，带给你全新感觉；<br/>4. 拟物化登陆动画，简洁、灵动，一试难忘；<br/>5. 新增”星标好友“分组，方便找到重要联系人；<br/>6. 主面板新增快捷店铺入口，快速访问好友店铺；<br/>7. 消息管理器优化，重新梳理消息类型和菜单项，分类清晰.查找便捷；<br/>8. 旺旺个人资料和名片全新设计，支持设置个性背景图；<br/>9. 软件升级优化，让您第一时间了解新版本的功能；<br/>10. 文件发送完成后自动关闭发送框，在聊天窗口中实时穿透传输结果；<br/>11. 聊天窗口合并优化，同时和多人聊天更方便；<br/>12. 应用平台全新升级，访问更便捷，应用消息更直观。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('106', '<p>爱奇艺视频桌面版3.0是一款专注视频播放的客户端软件，免费下载安装，观看高清正版影视，可在线享受爱奇艺网站内全部免费高清正版视频、最新影视大片、最独家的综艺、旅游、纪录片，支持全网搜索，是最个性化、时尚化的视频客户端。</p><p>开发商：北京爱奇艺科技有限公司</p><p>软件官网：&nbsp;http://www.iqiyi.com/</p><p>新版特征</p><p>1. 提升系统稳定性。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('107', '<p>谷歌浏览器稳定版（Chrome）是一个由Google（谷歌）公司开发的开放原始码网页浏览器。该浏览器是基于其他开放原始码软件所撰写，包括WebKit和Mozilla，目标是提升稳定性、速度和安全性，并创造出简单且有效率的使用者界面。</p><p>开发商：谷歌</p><p>软件官网：&nbsp;http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html</p><p>新版特征</p><p>1. 修复了部分bug问题。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('108', '<p>360浏览器是互联网上最好用、最安全的新一代浏览器，和360安全卫士、360杀毒等软件等产品一同成为360安全中心的系列产品。木马已经取代病毒成为当前互联网上最大的威胁，90%的木马用挂马网站通过普通浏览器入侵，每天有200万用户访问挂马网站中毒。360安全浏览器拥有全国最大的恶意网址库，采用恶意网址拦截技术，可自动拦截挂马、欺诈、网银仿冒等恶意网址。独创沙箱技术，在隔离模式即使访问木马也不会感染。除了在安全方面的特性，360安全浏览器在速度、资源占用、防假死不崩溃等基础特性上表现同样优异，在功能方面拥有翻译、截图、鼠标手势、广告过滤等几十种实用功能，在外观上设计典雅精致，是外观设计最好的浏览器，已成为广大网民使用浏览器的第一选择。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('109', '<p>PPTV网络电视是PPLive旗下产品，一款安装量大的P2P网络电视软件，支持对海量高清影视内容的直播+点播功能。可在线观看电影、电视剧、动漫、综艺、体育直播、游戏竞技、财经资讯等丰富视频娱乐节目。P2P传输，越多人看越流畅、完全免费，是广受网友推崇的上网装机必备软件。</p><p>开发商：上海聚力传媒技术有限公司</p><p>软件官网：&nbsp;http://www.pptv.com</p><p>新版特征</p><p>1、直播回看无限制，想看哪里看哪里；<br/>2、调整注册方式，手机注册更方便；<br/>3、程序猿们日夜兼程，保证您观影更流畅</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('11', '<p style=\"text-indent: 2em;\">记者从国家防总、水利部14日召开的新闻发布会上获悉：据初步统计，截至7月13日，长江中下游湖北、湖南、江西、安徽、江苏等5省堤防巡查防守和抢险共投入62.2万人(含部队3.08万人)，6月30日以来已累计投入抗洪抢险777万人次。</p><p><br/></p><p style=\"text-indent: 2em;\">截至7月13日，今年以来全国已有28省(区、市)1508县遭受洪灾，农作物受灾面积5460.66千公顷，受灾人口6074.67万人，因灾死亡237人、失踪93人，倒塌房屋14.72万间，直接经济损失约1469.80亿元。与2000年以来同期均值相比，今年受灾人口、死亡人口、倒塌房屋分别偏少6%、49%、55%，受灾面积、直接经济损失分别偏多26%、213%。</p><p><br/></p><p style=\"text-indent: 2em;\">此外，中央气象台14日继续发布暴雨蓝色预警，未来3天，强降雨将自西向东影响四川盆地、江汉、江淮、江南北部及黄淮南部，上述地区将有大到暴雨，部分地区有大暴雨，局部伴有短时雷雨大风等强对流天气。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('110', '<p>腾讯视频播放器是腾讯视频旗下的客户端产品，是一款支持丰富内容的在线点播及电视台直播的软件。它采用了先进的P2P流媒体播放技术，可以确保在大量用户同时观看节目的情况下，节目依然流畅清晰；同时具有很强的防火墙穿透能力，为用户在任何网络环境下收看流畅的视频节目提供了有力保障。</p><p>开发商：腾讯</p><p>软件官网：&nbsp;http://v.qq.com/index.html</p><p>新版特征</p><p>1. 客户端专享1080P蓝光画质，全员免费观看和下载；<br/>2. 鼠标停留在播放进度条上可看到预览画面；<br/>3. 启动时提示上次观看内容；<br/>4. 优化了搜索的体验；<br/>5. 精选频道点击栏目标题可跳转；<br/>6. 优化了VIP会员频道的分类筛选。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('111', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201610/10114510l7x1.jpg\" title=\"138683743.jpg\" alt=\"138683743.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">本月初的百度世界大会上，百度正式对外宣布，开源其深度学习平台PaddlePaddle，这也让百度成为国内首家开放深度学习技术平台的科技公司。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">徐伟表示，百度希望通过开放深度学习平台的方式，营造开发者社区，推动人工智能行业的发展。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">据徐伟介绍，该平台最初是百度研发的深度学习内部平台，项目于2013年启动，主要用于支持的百度产品，目前百度有超过30个产品应用到该深度学习平台，包括预测外卖送达时间、图文问答、商家好感度模型等。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在行业开放的大趋势下，百度决定将这一平台开源，现在所有的从事深度学习开发的开发人员均可以下载安装并使用百度的这一深度学习工具。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">除了百度以外，谷歌(微博)在去年底宣布开放其深度学习平台Tensorflow，此外，业内主流的深度学习平台还包括Facebook的Torch，加州伯克利大学的Caffe等。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">徐伟说，百度的PaddlePaddle平台具备易用、高效、灵活、可伸缩等特点，能够满足多种不同的应用开发需求。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在接受采访时，徐伟重点强调了该平台的易用性特征，他表示，相比谷歌的Tensorflow来说，开发者在使用PaddlePaddle时更容易上手，尤其是对于序列输入、稀疏输入和大规模数据的模型训练有着良好的支持，支持GPU运算、数据并行和模型并行，仅需少量代码就能训练深度学习模型，大大降低了用户使用深度学习技术的成本。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">徐伟说，让开发者决定使用哪个平台进行开发的决定因素主要包含几个方面，首先是该平台能否实现自己想实现的功能，其次是使用某平台实现起来的难度有多大，最后是效率有多高。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">本月底，PaddlePaddle将迎来开源后的首次重大更新，徐伟介绍称，此次更新主要是进一步完善对不同操作系统的支持、进一步完善说明文档以及解决不同安全环境下的问题等。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">百度开源人工智能技术引起了开发者社区的关注，Facebook深度学习研究员、曾参与谷歌Tensorflow研发的贾扬清评价称，PaddlePaddle有高质量的GPU代码、非常好的RNN（回归神经网络）设计，并且设计很干净，没有太多的抽象表达，这一点比Tensorflow好很多。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">徐伟表示，正式开源后，下一步的目标是进一步完善平台的易用性和性能，并增强与开发者群体的沟通，了解他们的需求。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在谈到人工智能行业的发展时，他认为，人工智能行业这两年出现爆发式增长，主要在语音识别、图像识别等领域出现了显著的突破。在谈到人工智能未来发展时，他表示乐观，并认为，未来可能的突破点在于自然语言识别、理解和处理以及机器翻译等方面，另外基于人工智能的”人工助手”的智能化程度也有望进一步加强。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('112', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201610/10114715p0as.jpg\" title=\"138679801.jpg\" alt=\"138679801.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">自2013年12月首批企业获牌以来，虚拟运营商发展迅速，目前已有42家企业获得牌照。9月22日，在“ICT中国· 2016高层论坛”移动转售分论坛上，中国通信企业协会披露，目前移动转售业务用户数已超3500万，占全国移动用户人数2.67%。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">不过，移动转售行业也因实名制未落实到位出现诸多“乱象”，尤其“170”号段诈骗多发遭诟病，在“徐玉玉受骗致死”案等多个热点事件的舆论声讨中，虚拟运营商声誉受到重大打击，其融资也受到影响。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\"><strong>虚拟运营商“很受伤”</strong></p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">3年前，第一批企业获得牌照进入试点，中国移动(微博)虚拟运营业方缓缓起步，而经过几年的发展，这一行业已小有规模，不过“成长的烦恼”也使其备受困扰。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">中国通信企业协会披露的数据显示，移动转售业的发展速度有所下降。自2015年3月起，移动通信转售业务月净增超过100万户，其中2015年10月起连续6个月净增达到200万，然而自2016年1月以来，增速有所放缓，甚至出现“停滞”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">增速降低与行业乱象不无关联。女大学生徐玉玉被骗后昏聩致死使得社会的目光投向虚拟运营商，尤其是因为“实名制”再次被推上风口浪尖。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">据央广网2016年8月报道，工信部网络安全管理局对虚拟运营商新入网电话用户实名登记工作暗访，暗访的26家转售企业的109个营销网点中，37个网点存在违规行为，违规占比超三成。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">虚拟运营商“实名制”漏洞也成了舆论谴责的“靶子”。中兴视通显然备受折磨，其CEO邓慕超向与会者“倒苦水”，称自己有时“夜不能寐、梦不能求”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">巴士在线CEO王献蜀在业内摸爬滚打多年，也叫苦不迭，“170贴了一个标签，就是诈骗，虚商也变成了诈骗公司。”王献蜀表示，这让虚拟运营商要“很受伤”，并呼吁业内外应给予虚拟运营商“容错空间”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\"><strong>声誉受损融资受波及</strong></p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">中国虚拟运营业起步晚于发达国家，而虚拟运营商们盈利困难早已不是新闻，而今，命途多舛的它们又遇“拦路虎”，这也让资本市场对其发展前景打了个问号，虚拟运营商直接受到冲击。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">王献蜀称，“170”号段的问题影响了虚拟运营的融资。“我们很少听到说哪一家虚商拿了多少融资，然后怎么样，多么风光，虚商这个行业几乎都是每一家虚商，每一个老板，每一个企业真金白银自己从口袋里面往外掏钱。”王献蜀表示，从业几年来，他自己已经投入了1.8亿元。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">蜗牛移动CEO陈艳也深有体会，在她看来，虚拟运营商还是个“婴儿”，她表示，英国发展虚拟运营已有18年，虚拟运营的比例占到全行业的12%，而中国发展2、3年达到2.67%，未来还有很大发展空间，但她却感受到了行业内的恐慌。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">融资困境及舆论压力促进了虚拟运营商的反思。9月22日的论坛上，虚拟运营企业代表均坦承，虚拟运营商也是诈骗案的受害者，落实实名制可促使虚拟运营业健康发展。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">今年4月份，工信部发布了《关于加强规范管理，促进移动通信转售业务健康发展》的通知，向移动转售企业提出了落实移动用户实名登记要求的有关具体规定。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">据中国通信企业协会常务副会长兼秘书长苗建华介绍，整治以来，虚拟运营商采用系统整改、完善手段、有奖举报等一系列措施取得了一定的成效。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('113', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201610/10114919rtom.jpg\" title=\"641.jpg\" alt=\"641.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">李克强总理首赴联合国，首场活动便是出席由联合国倡议举行的联大解决难移民大规模流动问题高级别会议。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">这是自联合国成立以来，首次召开的应对难移民问题的高级别会议。同时尤其值得注意的是，这也是中国领导人首次在此种国际场合阐述对于难移民问题的主张。总理的首场与联合国的首次，两者碰撞出怎样的火花?</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">人道主义的价值观，这是李克强主张的要旨。在联合国的讲台上，中国总理的视野超越国别和地域。他将难民和移民问题视为一场“拷问人类社会良知”的人道主义危机，因此郑重向国际社会发出呼吁：“每一个人的生命都是宝贵的，每一个人的尊严都应得到维护，人道主义精神必须弘扬。”中国传统政治伦理中有着人道主义的丰沛思想资源，李克强将其带到联合国讲台上，从而丰富了中国当代外交与政治的实践。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">与此相应，在联合国这个庄严的讲台上，李克强代表中国宣布的3项举措，实打实地彰显了基于人道主义的国际政治理念。中国将这样做：在原有援助规模的基础上，向有关国家和国际组织提供1亿美元的人道主义援助;积极研究把中国-联合国和平与发展基金的部分资金用于支持发展中国家难民移民工作;积极探讨同有关国际机构和发展中国家开展三方合作。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">怎么做，真金白银，某种意义上比怎么说更触及实质。然而这里面有一个厘清“责任观”的问题。中国作为联合国常任理事国，在难移民这一全球性议题上当然有义不容辞的责任。该出手时就出手，正如李克强在当天会议上所言，中国把人道主义援助视为“道义之举”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">同时，中国经济虽有了很大发展，但仍是发展中国家，愿意承担与自身能力相适应的责任。李克强在联合国所承诺的援助资金，是中国的量力而为，我们决不放空炮;所承诺的援助方式，比如使用和平与发展基金的部分资金、开展三方合作等，也是真正的“务实之举”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">更关键的是，李克强的“说”与“做”，在一个更高层面上实现了统一，那就是发展。战乱冲突、贫穷落后是难移民问题的主要根源，实现真正的包容性增长方为治本之策。中国的主张和举措，牢牢把握住了解决问题最关键点——长远和根本地看，出路还蕴藏在发展这一主题中。这也是李克强一直以来在国际外交舞台上所强调的，以发展弥合伤痕、促进平衡、共同向前。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在联合国的首场活动，阐述理念、承诺硬招，李克强总理在这个最高的国际场合，展示了中国“软实力”。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('12', '<p style=\"text-indent: 2em;\">央视新闻：【习近平就#法国尼斯袭击事件#向法国总统致慰问电】习近平对这一骇人听闻的野蛮行径表示强烈谴责，向不幸遇难者表示深切的哀悼，向伤者和遇难者家属表示诚挚的慰问。习近平指出，中方坚决反对一切形式的恐怖主义，愿同法方深化反恐合作，共同维护中法两国和世界安全和平。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('130', '<p style=\"text-indent: 2em;\">细心的人会观察到，在发表2016年新年贺词的镜头中，习近平总书记办公室有了新变化：新添的7张照片中，有3张记录着总书记和民族地区各族干部群众一起谋发展、话小康的难忘瞬间。</p><p style=\"text-indent: 2em;\"><br/></p><p>　　民族工作关乎大局。党的十八大以来，以习近平同志为总书记的党中央高度重视民族工作，多次深入民族地区调研，体察少数民族群众冷暖。先后召开第二次中央新疆工作座谈会、中央民族工作会议、中央第六次西藏工作座谈会等，对民族工作作出全面部署，力度之大、频次之高、涉面之广、阐述之深，前所未有，一曲新形势下民族工作新乐章在中华大地奏响。</p><p><br/></p><p>　　保持定力：坚持走中国特色解决民族问题的正确道路</p><p><br/></p><p>　　2014年9月，中央民族工作会议在京召开。会上，习近平总书记在坚持党的民族理论政策基本原则的基础上，提出了一系列新思想新观点新举措，为新形势下民族工作提供了行动指南和基本遵循：</p><p><br/></p><p>　　——我国历史演进的这个特点，造就了我国各民族在分布上的交错杂居、文化上的兼收并蓄、经济上的相互依存、情感上的相互亲近，形成了你中有我、我中有你、谁也离不开谁的多元一体格局。</p><p><br/></p><p>　　——新中国成立65年来，党的民族理论和方针政策是正确的，中国特色解决民族问题的道路是正确的，我国民族关系总体是和谐的。</p><p><br/></p><p>　　——同世界上其他国家相比，我国民族工作做得都是最成功的。</p><p><br/></p><p>　　——中华民族和各民族的关系，形象地说，是一个大家庭和家庭成员的关系，各民族的关系是一个大家庭里不同成员的关系。</p><p><br/></p><p>　　——坚持中国特色解决民族问题的正确道路。</p><p><br/></p><p>　　——坚持和完善民族区域自治制度，要做到坚持统一和自治相结合，坚持民族因素和区域因素相结合。</p><p><br/></p><p>　　——做好民族工作，最关键的是搞好民族团结，最管用的是争取人心。</p><p><br/></p><p>　　——城市民族工作要把着力点放在社区，推动建立相互嵌入式的社会结构和社区环境。</p><p><br/></p><p>　　——加强中华民族大团结，长远和根本的是增强文化认同，建设各民族共有精神家园，积极培养中华民族共同体意识。</p><p><br/></p><p>　　——尊重差异、包容多样，通过扩大交往交流交融，创造各族群众共居、共学、共事、共乐的社会条件，让各民族在中华民族大家庭中手足相亲、守望相助。</p><p>　　……</p><p>　　中央民族工作会议的召开，统一思想、坚定信心，在新的历史起点上推动民族团结进步事业踏上新的时代征程。</p><p><br/></p><p>　　大政方针已定，关键就在落实。中央民族工作会议以来，以理论创新为先导，民族工作的政策创新、制度创新、实践创新不断推进。</p><p><br/></p><p>　　——出台贯彻落实《中共中央、国务院关于加强和改进新形势下民族工作的意见》的重要举措分工方案，包括16项47条，条条都是硬举措。相关部委组成联合督查组督促中央民族工作会议精神的贯彻落实。</p><p><br/></p><p>　　——时隔13年召开全国民族教育工作会议，首次召开全国城市民族工作会议……中央民族工作会议精神逐一贯彻，渐次落实。</p><p><br/></p><p>　　——全国31个省、自治区、直辖市和新疆生产建设兵团相继召开贯彻落实中央民族工作会议精神的会议，出台了相关配套文件。</p><p><br/></p><p>　　——为支持民族地区加快发展，输送“真金白银”，研究制定差别化政策：第一次对川甘青交界地区，对南疆四地州，对怒江、凉山、临夏等三个特困自治州，专门制定政策举措。</p><p><br/></p><p>　　——明确少数民族学生高考加分政策，对国家通用语言文字已经普及、教育水平大体相同的地区，逐步缩小差距，逐步做到一律平等；对语言文化差异较大、教育质量还不高的一些地区少数民族学生，除大力普及双语教育、调整专业设置、提高教学质量外，仍是实行高考加分政策，支持少数民族学生取得较好教育水平。</p><p><br/></p><p>　　两年来，中央民族工作会议精神在各地生根发芽、开花结果。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('131', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201610/101353469a1k.jpg\" title=\"3c6d55fbb2fb43161e70f38528a4462308f7d3e6.jpg\" alt=\"3c6d55fbb2fb43161e70f38528a4462308f7d3e6.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">今年的国庆节注定是个不平凡的节日，北京、广州、深圳、苏州、合肥等20个一二线城市相继出台楼市限购限贷政策，“深八条”、“沪六条”等严厉调控措施相继出笼，犹豫在中国大地上引爆了无数颗原子弹，看得人眼花缭乱、心惊肉跳，让人感受到了中国楼市调控“变脸术”之快、之严。&nbsp;</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">社会各界姑且会猜测，这些调控措施付诸实施之后能否成为医治楼市癫狂的灵丹妙药？房地产业服了这些调控药之后是否能走上理性、健康发展轨道？中国民众是否从此不再有楼市猛涨带来的恐慌之虞？回答无疑是否定的。因为这些调控之“药”依然没有摆脱行政窠臼，其结局也就很难跳出行政调控怪圈：“严格管制房价暂时趋缓—放松管制房价暴涨”。也就是说，出台调控措施的这些城市，楼市价格上涨趋势可能暂时会缓一缓，但过后可能又将迎来新一轮量价齐涨周期。因为中国从2005年3月底的“国八条”算起，短短十年间，中国房地产市场虽经历过无数次调控，无论是国务院的各项“通知”，还是九部委、七部委的各种“意见”，都没能阻挡住房价上涨的步伐；不少城市的房价比2005年翻了几番甚至十番都不止。每一轮调控政策都是为稳定住房价格，但调控过后，总有一轮快速上涨行情，越让普通百姓感叹房子越来越买不起了。显然，目前楼市调控实质已陷入越调越高和政府“助涨”的尴尬局面。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">这是为什么呢？道理其实很简单，因为我们楼市调控政策存在严重问题：一是没有理顺楼市调控的目的到底是什么，是控制住房价让老百姓买得起房，还是不要过于严厉让房地产开发商能有钱赚和地方财政有收入可进？正因为楼市调控政策总是处于摇摆不定状态，忽而严厉、忽而放松，缺乏长久可持续调控政策措施，让房地产开发商及各级地方政府形成了“耐药性”，使调控功效被抵消。二是没有割断地方政府与房地产业发展之间的各种利益关系，是让楼市调控步入市场运行机制、让市场充分发挥自发调节作用，还是继续伸出行政权力之手来生硬地管制房价或分割利益？由于这种利益关系没理顺，使一些地方政府难下决心认真调控，致使调控政策得不到认真落实走形变样；同时也会更加诱发一些地方政府竭力追求土地财政、推高房价获得足够收入搞高楼房、宽马路等城市形象政绩工程建设，使调控陷入了“死胡同”。三是没有足够底牌或也不愿意拿出有效手段来对冲楼市上涨带来的压力，使楼市调控政策软弱无力。比如保障房、经济适用房等建设速度缓慢、数量严重短缺，把一大批城市中低收入人群也逼向商品房购买行列，更加剧了楼市非理性上涨；也让政府对楼市非正常上涨现象缺乏必要平抑措施，只能望楼市价格上涨兴叹。四是对楼市上涨失控城市政府调控不力、楼市中存在各种违规行为及投机炒作行为缺乏必要法治惩治手段，使楼市失常状况难得到及时扭转。如调控政策出台之后，没有一个地方政府领导因楼市调控措施落实不力受到严肃问责，丢下官帽；没有一个开发商因弄虚作假、违规开发而被罚得倾家荡产；更没有一个楼市投机炒作者被追究刑责入狱。于是地方政府调控措施执行不力已司空见惯，开发商违规行为让人熟视无睹，投机商哄抬房价打乱楼市现象更是让人见怪不怪。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">由此，目前中国一二线城市楼市调控政策不能只求一时轰动效应，而应把追求实实在在的长久调控效果放在首位，推动楼市调控彻底走出“屡涨屡调，屡调屡涨”怪圈：首先，制定富有弹性的楼市调控政策，确立楼市调控近期目标、中期目标和长远目标，分阶段实施，消除狂风暴雨式调控模式；把楼市调控政策制定要交给各级地方政府，建立目标考核责任制，将其纳入施政目标，让民众来打分评价，对民众不满意或房价涨幅过大的地方政府领导实施行政降级、行政记过、诫勉谈话等问责处罚，增强楼市调控政策的严肃性。其次，进一步厘清行政干预与市场调节的界限，确立地方政府在房地产市场中的责任；将房地产市场纳入法治监管轨道，对楼市调控不力的地方政府、违法违规的房地产开发及哄抬房价扰乱楼市秩序的投机商追究刑事责任，增强楼市调控的法治威慑力。再次，应尽快终结土地财政，加快税收制度改革步伐，将中央政府与地方政府事权与财权改革到位，消除地方政府对土地财政的依赖，楼市疯狂暴涨和挤泡沫才真正有希望。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('132', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201610/10135526bcqb.jpg\" title=\"908fa0ec08fa513da2f6fc21356d55fbb3fbd9f5.jpg\" alt=\"908fa0ec08fa513da2f6fc21356d55fbb3fbd9f5.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">三年前，中国大妈们抢购黄金的记忆还历历在目，没想到如今的黄金又开始了一轮又一轮的下跌。接连的破位下跌，再次有人开始蠢蠢欲动，想着投资抄底，再加上中国房地产市场的调控力度不断加大，那么会不会有一部分投资热钱也会进入到黄金市场呢？其实，投资市场历来有句名言是买涨不买跌。不过，对于黄金这种相对常见的避险投资工具来说，很多人买了就是资产配置和“囤货”，并不是简单的投资或者快速的出手，她们持有的耐心和时间更长，因此任何一个低价的出现，都有可能成为触发市场投资的一种诱惑。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">连续下跌，何时是个头？</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">今年的“十一黄金周”期间，黄金价格已经跌破1,300美元/盎司的重要支撑。10月1日～7日，纽约商业交易所（COMEX）黄金大跌4.5%，创下一年来最大单周跌幅，其中上周二跌幅超过3.3%，也创下了2013年12月以来单日最大跌幅。10月4日，现货金价一度跌破1270美元/盎司关口，白银一度跌破18美元关口，跌幅超过5%，多次刷新英国脱欧公投以来最低。市场对欧佩克限产协议不断炒作，原油看涨情绪升温。这都使黄金的价格不被看好。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">市场对全球央行货币政策转向，担忧流动性逆转是黄金下跌的主要原因。其实在8月份之后，全球资本市场人士普遍猜测，各国将统一行动，主要在结构性改革、财政政策上发力，货币政策可能接近极限，全球的流动性将很快出现逆转。黄金价格的剧烈波动，就是对这一猜测的又一次市场反应。如果全球货币宽松走到尽头，那么利率将缓慢上升，全球的债券牛市也将终结。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">摩根大通统计的数据显示，今年英国退欧公投后，欧英日三大央行的季度资产购买规模连创新高。目前美欧英日四大央行的资产负债表已高达13万亿美元之巨，已占全球GDP的40%。预计在今年最后一个季度，欧英日三大央行将“加印”5060亿美元在市场上购买资产，创2009年美联储首推QE以来的最大季度规模。高盛表示，基于全球经济增长仍面临持续下行风险，同时市场可能仍在质疑货币政策应对任何经济潜在冲击的能力。因此，金价跌破每盎司1250美元可能是一个战略性的购买机会。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">值得关注的是，中国央行一直是购买黄金的主力。最新数据显示，截至9月末，国内黄金储备从2014年6月的1054.1吨大幅增加74%至1838.53吨。据中国黄金协会报导，2015年中国生产黄金515.88吨，黄金产量连续九年保持世界第一，黄金消费量连续三年保持世界第一。今年有望继续保持这一势头。不过，今年的黄金需求较往年有明显的回落。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">来自Wind数据统计显示，目前，共有4家拥有黄金业务的上市公司发布了公司的前三季度业绩预报，而从预报结果来看，金价的变化，让这些公司的投资者暂时松了口气。“预计前三季度归属于上市公司股东的净利润变动幅度为增长350.00%至400.00%；同期归属于上市公司股东的净利润变动区间为9847.85万元至10942.05万元；业绩变动的原因是成本下降，黄金价格上升。”</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('14', '<p style=\"text-indent: 2em;\">【习近平考察宁夏首站到固原，冒雨向红军长征会师纪念碑敬献花篮】7月18日，习近平总书记到宁夏回族自治区考察。从北京直飞固原，驱车70多公里到将台堡，向红军长征会师纪念碑敬献花篮并参观三军会师纪念馆。1936年10月，红军三大主力在会宁和将台堡会师，标志二万五千里长征胜利结束。</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142227i3ee.jpg\" title=\"16464387039280572236.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/1914230968h5.jpg\" title=\"9093755211529617012.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">【习近平：缅怀先烈、不忘初心，走好新的长征路】参观三军会师纪念馆时，习近平说，红军长征创造了中外历史的奇迹。革命理想高于天，不怕牺牲、排除万难去争取胜利，面对形形色色的敌人决一死战、克敌制胜，这些都是长征精神的内涵。我们要继承和弘扬好伟大的长征精神。有了这样的精神，没有什么克服不了的困难。我们完全有信心有决心有恒心实现中华民族伟大复兴的中国梦。</p><p><br/></p><p style=\"text-indent: 2em;\">习近平说，长征永远在路上。这次专程来这里，就是缅怀先烈、不忘初心，走新的长征路。今天是实现“两个一百年”奋斗目标的新长征。我们这一代人要走好我们这一代人的长征路。</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142342h33w.jpg\" title=\"13099311371353179840.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p style=\"text-indent: 2em;\">【习近平：革命传统教育基地不要贪大求洋】习近平说，革命传统和爱国主义教育基地建设一定不要追求高大全，搞得很洋气、很现代化，花很多钱，那就不是革命传统了，革命传统就变味了。可以通过传统教育带动旅游业，但不能失去红色旅游的底色。只有体会到革命年代的艰苦，才能使人们真正受到教育。</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142414bvfg.jpg\" title=\"7973019827669232390.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p><br/></p><p style=\"text-indent: 2em;\">【习近平：我是来看扶贫落实情况的】18日下午，习近平总书记来到泾源县大湾乡杨岭村看望父老乡亲，实地考察精准扶贫情况。总书记察看村容村貌，向当地干部了解村子脱贫情况。走进回族群众马科的家，习近平察看屋内陈设，掀开褥子看炕垒得好不好，问屋顶上铺没铺油毡、会不会漏雨，电视能看多少台。来到厨房，打开灶上的大锅盖，看里面做着什么好吃的。厨房一角有个淋浴的地方，听说安了太阳能热水器，习近平说“挺好”，关心地问家里的小男孩：“你常洗澡吗？”</p><p><br/></p><p style=\"text-indent: 2em;\">墙上张贴着的“建档立卡贫困户精准脱贫信息卡”引起总书记注意。“6口人、劳动力2人，养牛6头，种玉米15亩，牛出栏2头收入7000，劳务输出收入21500，综合收入47000……”习近平逐项察看，一笔一笔算着马科家的收入账。习近平说，信息登记挺细致，关键要抓好落实，我就是来看落实情况的。马科说，我一定努力让今年计划落到口袋里，实现脱贫摘帽的目标。习近平希望马科把孩子教育搞好，学知识、有文化，决不能让他们输在起跑线上。</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142446ch8y.jpg\" title=\"2231079109511926610.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p style=\"text-indent: 2em;\">【习近平：固原的发展脱胎换骨，增强了我们打赢脱贫攻坚战的信心】习近平坐在杨岭村村民马克俊家的炕上，同村干部、党员代表、养牛大户和贫困户代表拉起家常。总书记说，我听你们说说心里话，咱们唠一唠。大家向总书记汇报近几年脱贫攻坚的情况，说党的政策确实确实太好太好了。习近平拉着马克俊的手说，你年纪比我小，是我老弟。看着你和乡亲们的生活越来越好我很高兴。西海固曾经“苦瘠甲天下”。这是我第3次到固原来。我提出再到比较艰苦的农村看一看。通过走访，了解到村里已经解决了饮水问题，住房等生活条件有了明显改善，还开始找到了产业脱贫的路子。全国还有5000万贫困人口，到2020年一定要实现全部脱贫目标。这是我当前最关心的事情。</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142513not2.jpg\" title=\"4804757052710294014.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p style=\"text-indent: 2em;\">【习近平：农村脱贫奔小康，支部很重要】习近平称赞大湾乡杨岭村党支部第一书记兰竹林对村里的情况门儿清，说明工作比较扎实。他说，一个村子建设得好，关键要有一个好党支部。村党支部带领村民脱贫奔小康，只要有规划，有措施，真抓实干，群众拥护，就一定能把工作做好。希望你们把基层党组织和基层政权建设好，团结带领广大群众奔小康。我们还要更上一层楼!</p><p><br/></p><p style=\"text-indent: 2em;\">【总书记为村民算养牛收支账】宁夏固原市大湾乡杨岭村，习近平走进贫困户马克俊家，首先来到小院一角的牛棚。养牛是杨岭村产业脱贫的重要途径，村里的贫困户在政府帮扶下，每家饲养2头安格斯基础母牛、3头育肥牛。习近平询问肉牛出栏育肥、贷款等情况。“过去都是养大黄牛，如今养上了进口牛”，马克俊给总书记详细算起了现在的养牛收支明细账。养牛大户马全龙也来到了小院里，他告诉总书记，自家过去养了5头牛，如今有了10多头，还准备增加到20头。习近平勉励他说：“要发挥好示范带头作用，把好的经验传授给村民。”</p><p><br/></p><p style=\"text-indent: 2em;\">【习近平：防范市场风险，政府要做好信息服务】细雨绵绵，沃野千里。宁夏原州区彭堡镇姚磨村，习近平冒雨视察万亩冷凉蔬菜基地。蔬菜展台前，新鲜采摘的西兰花、紫甘蓝、辣椒、马铃薯等，整齐“列队”。边走边看，习近平问得仔细。“检测标准怎么样？”“一亩地收入多少？”“农活有技术员指导吗？”“喷灌设施一亩地成本多少？”村里的党员干部、种植大户簇拥在总书记身边。习近平和他们聊起土地承包费、务农打工费、入股分红等，询问他们生产中遇到的困难。一位农民说，感觉市场还不大稳定，想更好了解市场需求。习近平表示，防范市场风险，既需要经营个体敏锐把握，也需要政府加强服务，尤其要做好信息服务工作。</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142548un62.jpg\" title=\"2176556949782138032.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p style=\"text-align: center\"><img src=\"/u/cms/www/201607/19142605p9xl.jpg\" title=\"17322238994688503995.jpg\"/></p><p><br/></p><p style=\"text-align: center;\">新华社记者鞠鹏、李涛摄</p><p><br/></p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('140', '<p>任职资格:<br/>1、大专及以上学历，计算机相关专业<br/>2、有一年及以上Java或者安卓开发经验者优先考虑<br/>3、有责任心，能独立思考问题</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('23', '<p>　　为了贯彻落实贵州省委、省政府关于构建全域旅游、推动旅游“井喷式”增长和黔东南州委、州政府“用好生态文化两个宝贝，打造国内外知名的民族文化旅游目的地”的战略部署，凯里市委、市政府突出建设国际旅游城市、国家园林城市、国家创新型城市的功能定位，以“节庆搭台，旅游唱戏”的超常规举措，着力推动凯里旅游从初级阶段快速向中高级阶段演化。2016年“十一”黄金周推出了文化旅游系列活动。</p><p>　　1、一赛一节</p><p>　　“一赛”即“牛霸天下”2016首届中国•凯里斗牛国际标准赛，“一节”即2016中国•凯里牛崇拜文化艺术节。</p><p>　　活动共分为四个场地：(1)斗牛国际标准赛场：凯里市民族文化活动中心(民族体育场)。(2)牛崇拜文化艺术节第一会场：凯里市民族文化活动中心(民族体育场)场外。(3)牛崇拜文化艺术节第二会场：凯里苗侗风情园。(4)牛崇拜文化艺术节第三会场：凯里民族文化园。举办时间：“牛霸天下”2016首届中国•凯里斗牛国际标准赛10月1日至10月6日，每日下午一点开始。2016中国•凯里牛崇拜文化艺术节10月1日至3日，每天白天和晚上在三个会场均有不同活动。</p><p>　　2、“弘扬民族文化，共筑中国梦”苗族刺绣体验</p><p>　　活动时间：10月1日至10月9日。地点：苗妹非遗博物馆。活动内容：游客现场体验、设计、制作自己心仪的作品。</p><p>　　3、2016“国庆节”南花村约你做客</p><p>　　活动时间：10月1日至10月7日。地点：南花苗寨。活动内容：观原生态山水风光、赏苗族歌舞、学苗族刺绣、饮百年神泉、品苗家米酒、体验传统打糍粑。</p><p>　　4、“李宁红双喜杯”2016中国乒乓球协会会员联赛</p><p>　　活动时间：10月1日至10月7日。地点：凯里学院体育馆。来自全国各地600多名中国乒乓球协会会员参赛，世界乒乓球冠军马琳、国家乒乓球队金牌教练吴敬平亲临凯里为比赛开赛。</p><p>　　5、云谷田园观光康体系列活动</p><p>　　活动时间：10月1日至10月7日。地点：舟溪云谷田园生态农业观光园。活动内容：农业观光采摘和钓鱼比赛等趣味体验活动。</p><p>　　2016“十一”黄金周凯里地区文化旅游系列活动内容丰富，时间长、规模大，亮点多、参与性强，欢迎中外游客和广大市民积极参与体验。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('27', '<p style=\"text-indent: 2em; text-align: center;\"><img src=\"/u/cms/www/201609/23140225zalw.jpg\" title=\"641-(1).jpg\" alt=\"641-(1).jpg\"/></p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">【学习进行时】在不久前举行的推进“一带一路”建设工作座谈会上，习近平要求：“以钉钉子精神抓下去，一步一步把‘一带一路’建设推向前进，让‘一带一路’建设造福沿线各国人民。”</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">习近平为何如此看重“一带一路”建设？自他发出沿线国家和地区共建“一带一路”倡议三年来，都取得了哪些进展？</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">6月22日，国家主席习近平在塔什干乌兹别克斯坦最高会议立法院发表题为《携手共创丝绸之路新辉煌》的重要演讲。 新华社记者马占成摄</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">2013年9月7日，习近平在出访中亚国家期间，首次提出共建“丝绸之路经济带”。同年10月，他又提出共同建设21世纪“海上丝绸之路”，二者共同构成了“一带一路”重大倡议。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">而今，三年了。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\"><strong>“一带一路”这三年</strong></p><p style=\"text-indent: 2em;\"><strong><br/></strong></p><p style=\"text-indent: 2em;\">三年，筚路蓝缕，春华秋实。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在“一带一路”倡议提出的当年11月，十八届三中全会通过的《中共中央关于全面深化改革若干重大问题的决定》明确指出：“加快同周边国家和区域基础设施互联互通建设，推进丝绸之路经济带、海上丝绸之路建设，形成全方位开放新格局。”</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">2014年11月的中央财经领导小组第八次会议专门研究了丝绸之路经济带和21世纪海上丝绸之路规划，发起建立亚洲基础设施投资银行和设立丝路基金。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在博鳌亚洲论坛2015年年会上，习近平呼吁各国积极参与“一带一路”建设。随后，中国政府发布《推动共建丝绸之路经济带和21世纪海上丝绸之路的愿景与行动》，明确了“一带一路”的共建原则、框架思路、合作重点、合作机制等。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">2016年3月，国家“十三五”规划纲要正式发布，“推进‘一带一路’建设”成为其中专门一章。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">2016年8月，习近平在推进“一带一路”建设工作座谈会上，进一步提出8项要求。从统一思想到统筹落实，从金融创新到人文合作，从话语体系建设到安全保障，面面俱到。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">三年，蓝图由草创到一步步展开、一笔笔绘就。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">目前，已有100多个国家和国际组织参与到“一带一路”建设中来，中国同30多个沿线国家签署了共建合作协议、同20多个国家开展了国际产能合作，联合国等国际组织也态度积极，以亚投行、丝路基金为代表的金融合作不断深入，一批有影响力的标志性项目逐步落地。“一带一路”建设从无到有、由点及面，进度和成果超出预期。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">“一带一路”东联亚太经济圈，西接欧洲经济圈，跨越高山深海，正在逐步构建世界上最壮美的经济走廊。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('28', '<p style=\"text-indent: 2em;\">9月21日，上海浦东机场，犯罪嫌疑人刘某被上海警方从马来西亚押送回沪。警方供图</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">骗税3400万，潜逃境外3100多天，辗转日本、马来西亚等地。经过上海警方漫长的追捕，2016年9月21日上午，国际红色通缉令出逃人员刘某被缉捕归案，押送回沪。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">澎湃新闻从上海警方获悉，近期“猎狐”行动捷报频传。8月22日，涉嫌巨额诈骗的国际红通犯罪嫌疑人钱某潜逃17年后被警方从南美国家苏里南劝返；8月31日，利用证券账户开设“老鼠仓”牟利、涉案金额高达1.2亿元的犯罪嫌疑人刘某被警方从美国劝返。至此，今年上海警方已经成功缉捕或劝返境外在逃人员71人。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('29', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201609/230906198i4y.jpeg\" title=\"qe.jpeg\" alt=\"qe.jpeg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">新华社深圳9月22日专电（记者孙飞）记者22日从深圳市公安局福田分局获悉，深圳数名二手房卖主，因房价上涨不愿履行协议与买家发生纠纷，并对深圳市中院相关判决不满，聚集60余人前往深圳市中院闹访。目前，5人因涉嫌聚众冲击国家机关罪被福田警方依法刑事拘留，并于9月14日由福田区人民检察院批准逮捕。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">深圳福田警方介绍，今年8月22日上午，在马某某（男，31岁，江西吉安人）、郑某某（男，47岁，广东深圳人）等人带领下，60余人拒不接受现场工作人员安检的要求，冲入深圳市中级人民法院一楼东大厅，扰乱深圳市中级人民法院的单位秩序。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">深圳福田警方接报后，组织警力赶到现场。在深圳市公安局统一指挥下，罗湖区、南山区公安分局警力也赶到现场支援处置工作。通过多方合力，62人被带离深圳中院。民警在现场收缴了大量标语横幅及文化衫等物品。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">深圳福田警方通报称，经查，该案系马某某、郑某某等二手房卖主，在签订二手房买卖协议后因房价上涨不愿履行协议引发纠纷，并对深圳市中院的相关判决不满，带领从东莞雁田等地雇佣的40余人前往深圳中院闹访。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">当日，深圳警方对这62人给予行政处罚，其中，56人分别被处以5至15日行政拘留，6人被处以罚款。后经进一步侦查，马某某、郑某某等5人因涉嫌聚众冲击国家机关罪被深圳福田警方依法刑事拘留，并于9月14日由福田区人民检察院批准逮捕。目前，该案尚在进一步侦办中。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('30', '<p style=\"text-indent: 2em;\">中新网北京9月23日电 (吴涛)中新网从三大运营商处获悉，此前传北京地区未实名手机用户10月15日将被停机，实际是分批执行，10月15日开始，最晚至10月31日截止。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">其实，多地已开始执行不实名就停机政策，中新网(微信公众号：cns2012)对此进行了梳理，大部分地区都是分批执行，如用户在规定时间内未实名，将面临被停机甚至销号的处理。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\"><strong>北京10月底前手机全部实名 否则停机</strong></p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">最近不少手机用户都收到通知，未实名手机用户将被停机。以北京为例，提示短信显示，10月15日起，未实名手机用户将被暂停通信服务。三大运营商相关负责人分别对中新网表示，在北京地区，10月15日起将执行非实名就停机政策，分批执行，最晚至10月底，非实名手机用户全部停机。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">至于其他省份，三大运营商表示，电信运营商省公司可以结合本地实际情况确定执行不实名就停机政策的具体日期。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">中国电信强调，地方分公司提出执行不实名就停机政策的时间节点，不得晚于集团提出的时间节点；中国移动表示，各省通信管理局或当地政府有规定的，优先按当地的规定执行。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">一些地方对手机实名补登记早就出了要求，江苏三家运营商按照省通信管理局要求，于7月22日发布公告称，自11月起暂停未实名手机用户的部分通信服务，12月起，停止仍未实名用户的全部通信服务。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">7月份，山东省公安厅和省通信管理局联合发布通告，自10月起暂停未实名用户的部分通信服务，2017年6月30日前，停止仍未实名用户的全部通信服务。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('32', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201609/230919031knf.jpg\" title=\"138678572.jpg\" alt=\"138678572.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">成龙捐给台北故宫(微博)南院的12生肖兽首，面临斩首命运。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">中国台湾网9月22日讯 据台湾《联合报》报道，台北故宫院长林正仪今天（22日）表示，9月底将移除影星成龙捐赠台北故宫南院的12生肖兽首。对此，成龙经纪人EMMA傍晚传达成龙的意见表示，当初捐兽首给台北故宫，是因为台北故宫是一个“尊重文明，保护文化”的单位，若台北故宫对于“尊重文明，保护文化”有不同态度，“那我们也尊重”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">成龙捐赠给台北故宫南院的12生肖兽首复制品，目前放置在台北故宫南院主建筑物外广场。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">EMMA指出，成龙将兽首捐给台北故宫时，想表达的“尊重文明 保护文化”的态度已经完成，“这整件事是一个态度传达的艺术行为”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">据报道，成龙也曾捐赠一组相同的兽首给新加坡国立亚洲文明博物馆。EMMA指出，成龙捐赠兽首，捐给台北故宫和捐给新加坡国立亚洲文明博物馆，传达的态度是一样的，就是“尊重文明，保护文化”。“我们捐的不是艺术品或工艺品，而是传达一个态度。”捐给台北故宫，是因为它是一个“尊重文明，保护文化”的单位。若台北故宫对于“尊重文明，保护文化”有不同态度，“那我们也尊重”。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('33', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201609/23092247x1xp.jpg\" title=\"138679618.jpg\" alt=\"138679618.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">亚马逊季度利润连续三个财季创下新纪录。随之而来的是，亚马逊股价也打破了记录。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">亚马逊的股价在纽约当地时间周四上午首次突破每股800美元大关。亚马逊以3860亿美元的市值稳居全球上市公司第四的位置，仅次于苹果、谷歌(微博)母公司Alphabet和微软。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在过去的仅仅七个月时间里，亚马逊的股价大涨了40%。推动亚马逊股价增长的主要原因是亚马逊云计算业务AWS的增长及其带来的惊人利润。另外，亚马逊数千万Prime会员也贡献颇多，这些会员要比非会员更经常购物，而且购物支出也比非会员要多得多。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('36', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201609/230939278x02.jpg\" title=\"138679965.jpg\" alt=\"138679965.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">9月20日，匹凸匹投资者索赔案开庭，有十多位投资者向匹凸匹发起索赔。今年3月，匹凸匹公告，因未及时披露多项对外重大担保、重大诉讼事项及2013年年报中未披露对外重大事项，证监会对匹凸匹处40万元罚款，对鲜言处30万元罚款。前述投资者认为因虚假陈述行为而受到损失。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">事实上，这也是匹凸匹原实际控制人鲜言给“海银系”掌门人韩宏伟的遗留问题。去年底，“海银系”以五牛基金为主力从鲜言手中接盘饱受争议的匹凸匹。韩宏伟与韩啸系父子关系。大半年过去，韩氏父子与鲜言的关系也从起初的甜蜜期走到如今对簿公堂。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">对于有着同窗情谊的两者是真翻脸还是另有图谋，投资者更为关心的是，韩氏父子掌控的“海银系”对匹凸匹未来发展有怎样的考虑，是否会注入资产让匹凸匹改头换面，而不是再次陷入“资本漩涡”。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\"><strong>实控人背后关联重重</strong><br/></p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">在资本市场叱咤风云的“海银系”为何要染指匹凸匹。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">近三年来，匹凸匹（原多伦股份）及鲜言在资本市场可谓劣迹斑斑，先后被证监会两次立案调查、两次公开谴责，1次警告、1次罚款，1次行政监管，并多次收到上交所的问询函。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">对于上海一中院开庭审理的十多位投资者诉匹凸匹案，上述投资者的代理律师上海市华荣律师事务所许峰对中国证券报记者表示，通过庭审判断，投资者最终获赔概率较大。其法律依据主要是，去年年底证监会针对匹凸匹未及时披露多项对外重大担保、重大诉讼事项做出了处罚。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">事实上，7月份，中证中小投资者服务中心代理散户起诉匹凸匹，将鲜言及原其他七名高管及匹凸匹公司作为共同被告诉至法院，诉请判令鲜言赔偿经济损失37万余元，其余八被告承担连带赔偿责任。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">有投行人士还对中国证券报记者透露，2014年11月底，鲜言拟将上市公司实际控制权转让给自然人殷群，最终因对方未付款而夭折。在五牛基金入主匹凸匹之前，资本大鳄吴鸣霄也与鲜言谈过买壳，但最终不知为何没有谈拢。值得注意的是，目前颇受关注的ST慧球(13.300, 0.01, 0.08%)第一大自然人股东就是吴鸣霄，而ST慧球被指背后的实际控制人是鲜言。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">上述人士指出，在历经陈隆基、李勇鸿、鲜言的多次进进出出。多伦股份主营业务变更不断，从房地产、金融又回到房产，唯一不变的是业绩不见起色，并一步步走向“空壳”状态。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('37', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201609/23094218axhy.jpg\" title=\"138679463.jpg\" alt=\"138679463.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">近日，美国联邦通信委员会意外曝光了苹果正在研发的一款新设备，其体积类似于苹果机顶盒（Apple TV），具体的用途尚不得而知。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">据美国科技新闻网站AppleInsider报道，联邦通信委员会的数据库中出现了这款苹果尚未对外宣布的新产品，其螺丝位置和设备外壳的大小，类似于第四代苹果机顶盒。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">苹果一直拥有对外保密的传统，而在这款新设备中，苹果也要求不对外泄漏相关信息，因此媒体无法判断到底作何用途。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">这款设备使用的型号A1844，目前并未被苹果发售的商品使用过。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">据称，该设备的电源参数为5.5V到13.2V,输出电流为100毫安，峰值为700毫安。这些参数有别于苹果最新的机顶盒，其电源参数为12V、920毫安。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">这款设备还具有蓝牙通信和NFC（近场通信）功能，美国联邦通信委员会也对这些通信功能进行了测试，相关的无线电通信功能也是这款设备提交到该机构进行测试的原因。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">这一设备没有进行Wi-Fi通信测试，可能意味着会采用目前某个设备的Wi-Fi通信技术，或者根本就不具备Wi-Fi通信功能。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">此前，外部机构曾经对苹果第四代机顶盒进行过拆解，相关的螺丝位置，和此次对外披露的设备有类似之处。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">近些年，苹果的研发资源集中在了智能手机、平板电脑等领域，传统的机顶盒似乎受到了冷落。去年，在长期不更新之后，苹果推出了全新第四代的机顶盒，苹果推出了专有的机顶盒操作系统，在遥控器中植入了语音操控工具Siri。库克也表示，电视的未来是应用软件。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">苹果也围绕机顶盒构建了一个客厅互联网的生态系统，许多第三方开发商正在为苹果机顶盒开发电视端的应用软件。</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em; text-align: center;\"><img src=\"/u/cms/www/201609/230943156ihf.jpg\" title=\"138679317.jpg\" alt=\"138679317.jpg\"/></p><p style=\"text-indent: 2em; text-align: center;\">第四代苹果机顶盒</p><p style=\"text-indent: 2em;\"><br/></p><p style=\"text-indent: 2em;\">此次披露的设备，是否是未来第五代的苹果机顶盒，仍无法判断。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('39', '<p style=\"text-align: center;\"><img src=\"/u/cms/www/201609/230954058tco.jpg\" title=\"138678243.jpg\" alt=\"138678243.jpg\"/></p><p><br/></p><p style=\"text-indent: 2em;\">中国地震台网正式测定：09月23日00时47分在四川甘孜州理塘县（北纬30.09度，东经99.64度）发生4.9级地震，震源深度19千米。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('43', '<p>工作职责：<br/>1、负责Web前端多终端产品的整体框架设计；<br/>2、引导前沿技术的预研与实施，通用模块的搭建与维护；<br/>3、负责Web前端开发规范与流程的指定；<br/>4、负责团队成员的技术发展方向及成长；<br/>5、与相关业务部门沟通，协调内部资源，管理前端外包团队。<br/><br/>任职资格：<br/>1、计算机科学或相关专毕业，5年以上工作经验；<br/>2、精通JavaScript、Html5、Css3、NodeJS等Web开发技术；<br/>3、关注Web前端前沿技术发展，具有根据项目情况进行技术选型的能力；<br/>4、熟悉W3C标准，对表现与数据分离、Web语义化等有深刻理解；<br/>5、具有软件工程意识，对数据结构和算法设计有充分理解；<br/>6、具有良好的沟通能力和团队合作精神、优秀的分析问题和解决问题的能力；<br/>7、熟悉Linux平台，掌握一种后端开发语言（PHPJavaCC++Python等）。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('44', '<p>基本要求：&nbsp;<br/>1、精通ASP.NET（C#），熟悉三层架构，精通Web　Services开发，良好的面向对象开发经验；&nbsp;<br/>2、精通AJAX技术运用；<br/>3、精通SqlServer，熟练编写存储过程；&nbsp;<br/>4、精通div,css前端布局；&nbsp;<br/>5、必须有一年以上ASP.NET开发经验，有3个以上完整div＋css、asp.net网站制作或系统研发作品。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('45', '<p>岗位职责：</p><p>1、大专及以上学历，新闻采编相关专业毕业；</p><p>2、1年以上工作经历，有较好的文字功底；</p><p>3、认真务实，态度端正。</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('46', '<p>岗位职责：<br/>1、负责电信天网工程项目；<br/>2、负责施工小队工程进度、质量管理<br/>3、负责甲方、监理及相关方协调；<br/><br/>任职资格：<br/>1、专科以上学历，有工程管理经验者优先，熟悉电信流程者优先</p><p>2、良好的理解和表达能力，善于沟通，很好的团队合作意识。<br/></p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('47', '<p>1、大专或大专以上应往届毕业生&nbsp;</p><p>2、理工类毕业生，计算机相关专业优先<br/></p><p>3、对软件行业有强烈的兴趣<br/></p><p>4、有良好的执行力，致力于软件行业发展<br/></p><p>5、后期发展从事网页设计，网站美工，网站开发，互联网系统开发等方向</p><p><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('48', '<p>岗位职责：<br/>1、负责Android互联网应用的开发及维护；<br/>2、根据项目需要快速学习并掌握新技术；<br/><br/>职位描述：<br/>1、具有扎实的Java基础，熟练掌握J2ME、J2SE等相关技术及代码优化技巧（容量、内存、速度）；<br/>2、熟悉TCP/IP通信机制，对Socket通信和HTTP通信有较深刻的理解和经验，有网络编程经验；<br/>3、熟悉Android操作系统和AndirodSDK,有一年以上Andriod开发经验优先&nbsp;<br/>4、具备良好的沟通能力和优秀的团队协作能力；<br/>5、优秀的文档编写和语言表达能力，良好的中英文阅读水平；<br/>6、诚恳、踏实、谨慎细致，对工作充满热情，优秀的学习能力，具有良好的自律意识和上进心；<br/>7、有嵌入式LinuxC/C++开发经验优先 ；<br/>8、熟悉MS SQL数据库开发。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('49', '<p>岗位条件：<br/>1、年龄18-28周岁；<br/>2、学历大专及以上，理工科专业毕业优先录用；<br/>3、对互联网行业感兴趣（非销售、非保险岗位），懂编程语言优先考虑，但是也可以接收零基础求职者，有项目经理带团队；<br/>4、工作认真、细致、敬业，责任心强；<br/>5、想获得一份有长远发展、稳定、有晋升空间的工作。</p><p><br/>待遇：<br/>1、转正基本薪资3500起，另有项目奖金和提成；<br/>2、五险一金，双休、法定节假日，正常休息；<br/>3、公司工作环境优雅、氛围好，同事关系融洽，生日派对、聚餐等活动丰富；<br/>4、公司注重员工培养，给予晋升机会，管理层主要员工中培养、提拔；</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('50', '<p>岗位要求：</p><p>1、喜欢从事计算机设计行业；</p><p>2、想获得一份稳定的工作；</p><p>3、好学、细心，喜欢发现事物当中的不足，责任心强。</p><p><br/></p><p>任职要求：</p><p>1、能够尽快入职、长期稳定工作。</p><p>2、大专及大专以上学历。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('51', '<p>岗位职责：<br/>1.熟悉微信的各项功能应用，擅长企业微信的日常操作以及维护<br/>2.根据企业的受众群体可以养成数据分析能力，善于通过数据分析掌握时间段与顾客互动.更新及维护公司企业微信内容，提高各项关注度，提升转化率。&nbsp;<br/>3.负责企业微信的运营策略、活动、话题的制定及策划方案和活动创意、活动宣传，完成微信营销。&nbsp;<br/>4.挖掘和分析粉丝使用习惯，情感及体验感受，及时掌握新闻热点，与用户进行互动。&nbsp;<br/>5.具有一定的市场分析及判断能力，具有良好的客户服务意识，具有亲和力。&nbsp;<br/>6.跟踪微信推广效果，分析数据并反馈，分享微信推广经验，推动及提高团队的网络推广能力。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('69', '<p>腾讯推出的即时通讯工具。支持在线聊天、视频电话、点对点断点续传文件、共享文件、网络硬盘、自定义面板、QQ邮箱等多种功能。免费的通讯平台，QQ2015年给您带来更多沟通乐趣。</p><p><br/></p><p>开发商：腾讯</p><p><br/></p><p>新版特征</p><p>1.文件共享，便捷分享本地文件；</p><p>2.群组通话管理，有序发言，掌控全场；</p><p>3.团队通讯录，快速查看群成员电话。</p><p><br/></p><p><strong>下载说明</strong></p><p><br/></p><p>1、推荐使用迅雷等下载工具下载本站软件，使用 WinRAR v3.10 以上版本解压本站软件。<br/></p><p>2、如果该软件不能下载，请留言报告错误,谢谢合作。</p><p>3、下载本站资源时，如果遇到服务器忙暂不能下载的情况，请过一段时间重试。</p><p>4、如果您有任何意见或建议，欢迎给我们留言，我们将提供更多 、更好的资源。</p><p>5、本站提供的一些商业软件是供学习研究之用，如用于商业用途，请购买正版。</p><p><strong><br/></strong><br/></p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('71', '<p>百度公司推出的一款云服务产品。支持便捷地查看、上传、下载百度云端各类数据。通过百度云管家存入的文件，不会占用本地空间。上传、下载文件过程更稳定。</p>', null, null, null);
INSERT INTO `cms_content_txt` VALUES ('72', '<p>360杀毒软件下载2016官方下载版是永久免费杀毒软件,360杀毒软件下载免费版开创了杀毒软件免费杀毒的先河.本站提供360杀毒软件下载2016官方下载.360杀毒通过对用户反馈的持续关注与分析，我们进一步改进了针对浏览器不能上网的问题的扫描和自动修复逻辑。您通过快速或是全盘扫描就能够对该类问题进行快速修复!还有大幅优化了开机过程中对CPU和IO的占用，大大减少了对系统开机过程的影响。</p>', null, null, null);

-- ----------------------------
-- Table structure for cms_content_type
-- ----------------------------
DROP TABLE IF EXISTS `cms_content_type`;
CREATE TABLE `cms_content_type` (
  `typeId` varchar(64) NOT NULL,
  `typeName` varchar(20) NOT NULL COMMENT '名称',
  `imgWidth` int(11) DEFAULT NULL COMMENT '图片宽',
  `imgHeight` int(11) DEFAULT NULL COMMENT '图片高',
  `hasImage` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有图片',
  `isDisabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容类型表';

-- ----------------------------
-- Records of cms_content_type
-- ----------------------------
INSERT INTO `cms_content_type` VALUES ('1', '普通', '100', '100', '0', '0');
INSERT INTO `cms_content_type` VALUES ('2', '图文', '510', '288', '1', '0');
INSERT INTO `cms_content_type` VALUES ('3', '焦点', '280', '200', '1', '0');
INSERT INTO `cms_content_type` VALUES ('4', '头条', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for cms_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `cms_dictionary`;
CREATE TABLE `cms_dictionary` (
  `id` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT 'name',
  `value` varchar(255) NOT NULL COMMENT 'value',
  `type` varchar(255) NOT NULL COMMENT 'type',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of cms_dictionary
-- ----------------------------
INSERT INTO `cms_dictionary` VALUES ('1', '10-20人', '10-20人', 'scale');
INSERT INTO `cms_dictionary` VALUES ('10', '加工制造', '加工制造', 'industry');
INSERT INTO `cms_dictionary` VALUES ('11', '服务行业', '服务行业', 'industry');
INSERT INTO `cms_dictionary` VALUES ('2', '20-50人', '20-50人', 'scale');
INSERT INTO `cms_dictionary` VALUES ('3', '50-10人', '50-10人', 'scale');
INSERT INTO `cms_dictionary` VALUES ('4', '100人以上', '100人以上', 'scale');
INSERT INTO `cms_dictionary` VALUES ('5', '私企', '私企', 'nature');
INSERT INTO `cms_dictionary` VALUES ('6', '股份制', '股份制', 'nature');
INSERT INTO `cms_dictionary` VALUES ('7', '国企', '国企', 'nature');
INSERT INTO `cms_dictionary` VALUES ('8', '互联网', '互联网', 'industry');
INSERT INTO `cms_dictionary` VALUES ('9', '房地产', '房地产', 'industry');

-- ----------------------------
-- Table structure for cms_file
-- ----------------------------
DROP TABLE IF EXISTS `cms_file`;
CREATE TABLE `cms_file` (
  `filePath` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `fileName` varchar(255) DEFAULT '' COMMENT '文件名字',
  `fileIsvalid` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `contentId` varchar(64) DEFAULT NULL COMMENT '内容id',
  PRIMARY KEY (`filePath`),
  KEY `fk_jc_file_content` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_file
-- ----------------------------

-- ----------------------------
-- Table structure for cms_friendlink
-- ----------------------------
DROP TABLE IF EXISTS `cms_friendlink`;
CREATE TABLE `cms_friendlink` (
  `friendlinkId` varchar(64) NOT NULL,
  `siteId` varchar(64) NOT NULL,
  `friendlinkctgId` varchar(64) NOT NULL,
  `siteName` varchar(150) NOT NULL COMMENT '网站名称',
  `domain` varchar(255) NOT NULL COMMENT '网站地址',
  `logo` varchar(150) DEFAULT NULL COMMENT '图标',
  `email` varchar(100) DEFAULT NULL COMMENT '站长邮箱',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `views` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `isEnabled` char(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  PRIMARY KEY (`friendlinkId`),
  KEY `fk_jc_ctg_friendlink` (`friendlinkctgId`),
  KEY `fk_jc_friendlink_site` (`siteId`),
  CONSTRAINT `fk_jc_ctg_friendlink` FOREIGN KEY (`friendlinkctgId`) REFERENCES `cms_friendlink_ctg` (`friendlinkctgId`),
  CONSTRAINT `fk_jc_friendlink_site` FOREIGN KEY (`siteId`) REFERENCES `cms_site` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS友情链接';

-- ----------------------------
-- Records of cms_friendlink
-- ----------------------------
INSERT INTO `cms_friendlink` VALUES ('1', '1', '1', 'JEECMS官网', 'http://www.jeecms.com', null, 'jeecms@163.com', 'JEECMS是JavaEE版网站管理系统（Java Enterprise Edition Content Manage System）的简称。Java凭借其强大、稳定、安全、高效等多方面的优势，一直是企业级应用的首选。', '35', '1', '1');
INSERT INTO `cms_friendlink` VALUES ('2', '1', '1', 'JEEBBS论坛', 'http://bbs.jeecms.com', null, 'jeecms@163.com', 'JEEBBS论坛', '5', '1', '10');
INSERT INTO `cms_friendlink` VALUES ('3', '1', '2', '京东商城', 'http://www.360buy.com/', '/u/cms/www/201112/1910271036lr.gif', '', '', '4', '1', '10');
INSERT INTO `cms_friendlink` VALUES ('4', '1', '2', '当当网', 'http://www.dangdang.com/', '/u/cms/www/201112/191408344rwj.gif', '', '', '1', '1', '10');
INSERT INTO `cms_friendlink` VALUES ('5', '1', '2', '亚马逊', 'http://www.amazon.cn/', '/u/cms/www/201112/19141012lh2q.gif', '', '', '2', '1', '10');
INSERT INTO `cms_friendlink` VALUES ('6', '1', '2', 'ihush', 'http://www.ihush.com/', '/u/cms/www/201112/19141255yrfb.gif', '', '', '1', '1', '10');

-- ----------------------------
-- Table structure for cms_friendlink_ctg
-- ----------------------------
DROP TABLE IF EXISTS `cms_friendlink_ctg`;
CREATE TABLE `cms_friendlink_ctg` (
  `friendlinkctgId` varchar(64) NOT NULL,
  `siteId` varchar(64) NOT NULL,
  `friendlinkctgName` varchar(50) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  PRIMARY KEY (`friendlinkctgId`),
  KEY `fk_jc_friendlinkctg_site` (`siteId`),
  CONSTRAINT `fk_jc_friendlinkctg_site` FOREIGN KEY (`siteId`) REFERENCES `cms_site` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS友情链接类别';

-- ----------------------------
-- Records of cms_friendlink_ctg
-- ----------------------------
INSERT INTO `cms_friendlink_ctg` VALUES ('1', '1', '文字链接', '1');
INSERT INTO `cms_friendlink_ctg` VALUES ('2', '1', '品牌专区（图片链接）', '2');
INSERT INTO `cms_friendlink_ctg` VALUES ('4', '1', '', '10');

-- ----------------------------
-- Table structure for cms_model
-- ----------------------------
DROP TABLE IF EXISTS `cms_model`;
CREATE TABLE `cms_model` (
  `modelId` varchar(64) NOT NULL,
  `modelName` varchar(100) NOT NULL COMMENT '名称',
  `modelPath` varchar(100) NOT NULL COMMENT '路径',
  `tplChannelPrefix` varchar(20) DEFAULT NULL COMMENT '栏目模板前缀',
  `tplContentPrefix` varchar(20) DEFAULT NULL COMMENT '内容模板前缀',
  `titleImgWidth` int(11) NOT NULL DEFAULT '139' COMMENT '栏目标题图宽度',
  `titleImgHeight` int(11) NOT NULL DEFAULT '139' COMMENT '栏目标题图高度',
  `contentImgWidth` int(11) NOT NULL DEFAULT '310' COMMENT '栏目内容图宽度',
  `contentImgHeight` int(11) NOT NULL DEFAULT '310' COMMENT '栏目内容图高度',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `hasContent` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有内容',
  `isDisabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `isDef` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认模型',
  `isGlobal` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否全站模型',
  `siteId` int(11) DEFAULT NULL COMMENT '非全站模型所属站点',
  PRIMARY KEY (`modelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS模型表';

-- ----------------------------
-- Records of cms_model
-- ----------------------------
INSERT INTO `cms_model` VALUES ('1', '新闻', '1', 'news', 'news', '139', '139', '310', '310', '1', '1', '0', '1', '1', null);
INSERT INTO `cms_model` VALUES ('2', '单页', '2', 'alone', 'alone', '139', '139', '310', '310', '2', '0', '0', '0', '1', null);
INSERT INTO `cms_model` VALUES ('4', '下载', '4', 'download', 'download', '139', '139', '310', '310', '4', '1', '0', '0', '1', null);
INSERT INTO `cms_model` VALUES ('5', '图库', '5', 'pic', 'pic', '139', '139', '310', '310', '5', '1', '0', '0', '1', null);
INSERT INTO `cms_model` VALUES ('6', '视频', '6', 'video', 'video', '139', '139', '310', '310', '10', '1', '0', '0', '1', null);
INSERT INTO `cms_model` VALUES ('8', '招聘', 'job', 'job', 'job', '139', '139', '310', '310', '10', '1', '0', '0', '1', null);

-- ----------------------------
-- Table structure for cms_model_item
-- ----------------------------
DROP TABLE IF EXISTS `cms_model_item`;
CREATE TABLE `cms_model_item` (
  `modelitemId` varchar(64) NOT NULL,
  `modelId` varchar(64) NOT NULL,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `itemLabel` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '70' COMMENT '排列顺序',
  `defValue` varchar(255) DEFAULT NULL COMMENT '默认值',
  `optValue` varchar(255) DEFAULT NULL COMMENT '可选项',
  `textSize` varchar(20) DEFAULT NULL COMMENT '长度',
  `areaRows` varchar(3) DEFAULT NULL COMMENT '文本行数',
  `areaCols` varchar(3) DEFAULT NULL COMMENT '文本列数',
  `help` varchar(255) DEFAULT NULL COMMENT '帮助信息',
  `helpPosition` varchar(1) DEFAULT NULL COMMENT '帮助位置',
  `dataType` int(11) NOT NULL DEFAULT '1' COMMENT '数据类型',
  `isSingle` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否独占一行',
  `isChannel` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否栏目模型项',
  `isCustom` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否自定义',
  `isDisplay` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `isRequired` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否必填项',
  `imageWidth` int(11) DEFAULT NULL COMMENT '图片宽度',
  `imageHeight` int(11) DEFAULT NULL COMMENT '图片宽度',
  PRIMARY KEY (`modelitemId`),
  KEY `fk_jc_item_model` (`modelId`),
  CONSTRAINT `fk_jc_item_model` FOREIGN KEY (`modelId`) REFERENCES `cms_model` (`modelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS模型项表';

-- ----------------------------
-- Records of cms_model_item
-- ----------------------------
INSERT INTO `cms_model_item` VALUES ('1', '1', 'name', '栏目名称', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('10', '1', 'priority', '排列顺序', '11', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('100', '4', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('101', '4', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('102', '4', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('103', '4', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('104', '4', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('105', '4', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('107', '4', 'channelId', '栏目', '1', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('108', '4', 'title', '软件名称', '2', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('109', '4', 'shortTitle', '软件简称', '3', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('11', '1', 'display', '显示', '11', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('110', '4', 'titleColor', '标题颜色', '4', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('111', '4', 'description', '摘要', '5', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('112', '4', 'author', '发布人', '6', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('113', '4', 'viewGroupIds', '浏览权限', '7', null, null, null, null, null, null, null, '7', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('114', '4', 'topLevel', '固顶级别', '8', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('115', '4', 'releaseDate', '发布时间', '9', null, null, null, null, null, null, null, '5', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('116', '4', 'typeId', '内容类型', '9', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('117', '4', 'tplContent', '指定模板', '22', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('118', '4', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('119', '4', 'attachments', '资源上传', '11', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('12', '1', 'docImg', '文档图片', '11', null, null, null, null, null, null, null, '8', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('120', '4', 'txt', '软件介绍', '20', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('121', '4', 'softType', '软件类型', '12', '国产软件', '国产软件,国外软件', '100', '3', '30', null, null, '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('122', '4', 'warrant', '软件授权', '13', '免费版', '免费版,共享版', '', '3', '30', '', '', '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('123', '4', 'relatedLink', '相关链接', '14', 'http://', '', '50', '3', '30', '', '', '1', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('124', '4', 'demoUrl', '演示地址', '15', 'http://', null, '60', '3', '30', null, null, '1', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('125', '5', 'name', '栏目名称', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('126', '5', 'path', '访问路径', '8', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('127', '5', 'title', 'meta标题', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('128', '5', 'keywords', 'meta关键字', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('129', '5', 'description', 'meta描述', '8', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('130', '5', 'tplChannel', '栏目模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('131', '5', 'tplContent', '选择模型模板', '10', null, null, null, null, null, null, null, '6', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('132', '5', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('133', '5', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('134', '5', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('135', '5', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('136', '5', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('138', '5', 'afterCheck', '审核后', '11', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('139', '5', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('14', '1', 'afterCheck', '审核后', '12', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('140', '5', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('141', '5', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('142', '5', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('144', '5', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('145', '5', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('146', '5', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('147', '5', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('148', '5', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('149', '5', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('15', '1', 'commentControl', '评论', '11', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('150', '5', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('151', '5', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('152', '5', 'description', '摘要', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('153', '5', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('154', '5', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('155', '5', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('156', '5', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('157', '5', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('158', '5', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('159', '5', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('16', '1', 'allowUpdown', '顶踩', '11', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('160', '5', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('161', '5', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('162', '5', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('163', '5', 'pictures', '图片集', '11', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('164', '6', 'name', '栏目名称', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('165', '6', 'path', '访问路径', '8', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('166', '6', 'title', 'meta标题', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('167', '6', 'keywords', 'meta关键字', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('168', '6', 'description', 'meta描述', '8', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('169', '6', 'tplChannel', '栏目模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('17', '1', 'viewGroupIds', '浏览权限', '11', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('170', '6', 'tplContent', '选择模型模板', '10', null, null, null, null, null, null, null, '6', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('171', '6', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('172', '6', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('173', '6', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('174', '6', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('175', '6', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('177', '6', 'afterCheck', '审核后', '11', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('178', '6', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('179', '6', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('18', '1', 'contriGroupIds', '投稿权限', '11', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('180', '6', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('181', '6', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('183', '6', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('184', '6', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('185', '6', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('186', '6', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('187', '6', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('188', '6', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('189', '6', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('190', '6', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('191', '6', 'description', '内容简介', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('192', '6', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('193', '6', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('194', '6', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('195', '6', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('196', '6', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('197', '6', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('198', '6', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('199', '6', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('2', '1', 'path', '访问路径', '8', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('20', '1', 'link', '外部链接', '11', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('200', '6', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('201', '6', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('202', '6', 'attachments', '附件', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('203', '6', 'media', '多媒体', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('21', '1', 'titleImg', '标题图', '11', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('23', '1', 'title', '标题', '9', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('24', '1', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('246', '4', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('249', '8', 'name', '栏目名称', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('25', '1', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('250', '8', 'path', '访问路径', '8', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('251', '8', 'title', 'meta标题', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('252', '8', 'keywords', 'meta关键字', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('253', '8', 'description', 'meta描述', '8', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('254', '8', 'tplChannel', '栏目模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('255', '8', 'tplContent', '选择模型模板', '9', null, null, null, null, null, null, null, '6', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('256', '8', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('257', '8', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('258', '8', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('259', '8', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('26', '1', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('260', '8', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('262', '8', 'afterCheck', '审核后', '11', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('263', '8', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('264', '8', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('265', '8', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('266', '8', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('268', '8', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('269', '8', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('27', '1', 'description', '摘要', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('270', '8', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('271', '8', 'channelId', '栏目', '1', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('272', '8', 'title', '岗位名称', '1', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('275', '8', 'tagStr', 'Tag标签', '7', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('279', '8', 'viewGroupIds', '浏览权限', '8', null, null, null, null, null, null, null, '7', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('28', '1', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('280', '8', 'topLevel', '固顶级别', '7', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('281', '8', 'releaseDate', '发布时间', '2', null, null, null, null, null, null, null, '5', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('282', '8', 'typeId', '内容类型', '7', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('283', '8', 'tplContent', '指定模板', '8', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('289', '8', 'txt', '职位描述', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('29', '1', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('290', '8', 'deadline', '截止日期', '2', null, null, null, '3', '30', '留空永久有效', null, '5', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('291', '8', 'experience', '工作经验', '3', null, '1-3年,3-5年,5年以上,不限', null, '3', '30', null, null, '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('292', '8', 'education', '最低学历', '3', '', '专科,本科,硕士,不限', '', '3', '30', '', '', '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('293', '8', 'salary', '职位月薪', '4', null, '1500-2000,2000-3000,3000-5000,5000-8000,8000以上,面议', null, '3', '30', null, null, '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('294', '8', 'workplace', '工作地点', '4', '', '北京,上海,深圳,广州,重庆,南京,杭州,西安,南昌', '', '3', '30', '', '', '7', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('295', '8', 'nature', '工作性质', '5', '', '全职,兼职', '', '3', '30', '', '', '8', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('296', '8', 'hasmanage', '管理经验', '5', '', '要求,不要求', '', '3', '30', '', '', '8', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('297', '8', 'nums', '招聘人数', '6', '', '1-3人,3-5人,5-10人,若干', '', '3', '30', '', '', '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('298', '8', 'category', '职位类别', '6', null, '项目主管,java高级工程师,java工程师,网页设计师,测试人员,技术支持', null, '3', '30', null, null, '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('3', '1', 'title', 'meta标题', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('30', '1', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('31', '1', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('32', '1', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('33', '1', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('335', '1', 'workflowId', '工作流', '12', null, null, null, null, null, '留空则继承上级栏目设置，顶层为空无需审核', null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('338', '4', 'workflowId', '工作流', '10', null, null, null, null, null, '留空则继承上级栏目设置，顶层为空无需审核', null, '6', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('339', '5', 'workflowId', '工作流', '11', null, null, null, null, null, '留空则继承上级栏目设置，顶层为空无需审核', null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('34', '1', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('340', '6', 'workflowId', '工作流', '11', null, null, null, null, null, '留空则继承上级栏目设置，顶层为空无需审核', null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('342', '8', 'workflowId', '工作流', '11', null, null, null, null, null, '留空则继承上级栏目设置，顶层为空无需审核', null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('344', '1', 'channelId', '栏目', '8', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('35', '1', 'typeImg', '类型图', '11', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('36', '1', 'titleImg', '标题图', '11', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('37', '1', 'contentImg', '内容图', '11', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('38', '1', 'attachments', '附件', '11', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('39', '1', 'media', '多媒体', '11', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('4', '1', 'keywords', 'meta关键字', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('40', '1', 'txt', '内容', '11', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('403', '6', 'Director', '导演', '10', '', '', '', '1', '2', '', '', '1', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('404', '6', 'Starring', '主演', '10', '', '', '', '1', '30', '', '', '1', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('405', '6', 'VideoType', '视频类型', '10', null, '历史,古装,都市,喜剧,悬疑,动作,谍战,伦理,战争,惊悚', null, '3', '30', null, null, '7', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('406', '6', 'Video', '影片信息', '10', '', '正片,预告片', '', '3', '30', '', '', '6', '0', '0', '1', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('408', '1', 'contentImg', '内容图', '11', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('409', '5', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '0', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('410', '6', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('412', '1', 'allowShare', '分享', '11', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('413', '1', 'allowScore', '评分', '11', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('414', '1', 'pictures', '图片集', '12', null, null, null, null, null, null, null, '4', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('416', '1', 'tplMobileChannel', '栏目手机模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('417', '1', 'tplMobileContent', '指定手机模板', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('418', '2', 'tplMobileChannel', '栏目手机模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('419', '2', 'channelStatic', '栏目静态化', '9', null, null, null, null, null, null, null, '4', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('42', '2', 'name', '栏目名称', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('420', '2', 'contentStatic', '内容静态化', '9', null, null, null, null, null, null, null, '4', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('421', '4', 'tplMobileChannel', '栏目手机模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('422', '4', 'channelStatic', '栏目静态化', '9', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('423', '4', 'contentStatic', '内容静态化', '9', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('424', '4', 'tplMobileContent', '指定手机模板', '9', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('425', '5', 'tplMobileChannel', '栏目手机模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('426', '6', 'tplMobileChannel', '栏目手机模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('43', '2', 'path', '访问路径', '8', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('431', '8', 'tplMobileChannel', '栏目手机模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('432', '8', 'tplMobileContent', '指定手机模板', '8', null, null, null, null, null, null, null, '6', '0', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('438', '1', 'charge', '是否收费', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('44', '2', 'title', 'meta标题', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('442', '4', 'size', '大小', '10', '', '', '', '', '', '', '', '1', '0', '0', '1', '1', '0', '100', '100');
INSERT INTO `cms_model_item` VALUES ('443', '4', 'edition', '版本', '10', '', '', '', '', '', '', '', '1', '0', '0', '1', '1', '0', '100', '100');
INSERT INTO `cms_model_item` VALUES ('444', '4', 'system', '支持系统', '10', '', '', '', '', '', '', '', '1', '0', '0', '1', '1', '0', '100', '100');
INSERT INTO `cms_model_item` VALUES ('445', '4', 'bit', '位数', '10', '', '', '', '', '', '', '', '1', '0', '0', '1', '1', '0', '100', '100');
INSERT INTO `cms_model_item` VALUES ('45', '2', 'keywords', 'meta关键字', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('456', '4', 'charge', '是否收费', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('457', '4', 'typeImg', '类型图', '9', null, null, null, null, null, null, null, '1', '1', '0', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('46', '2', 'description', 'meta描述', '8', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('47', '2', 'tplChannel', '栏目模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('48', '2', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('49', '2', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('5', '1', 'description', 'meta描述', '8', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('51', '2', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('52', '2', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('53', '2', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('6', '1', 'tplChannel', '栏目模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('7', '1', 'tplContent', '选择模型模板', '10', null, null, null, null, null, null, null, '6', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('8', '1', 'channelStatic', '栏目静态化', '11', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('9', '1', 'contentStatic', '内容静态化', '11', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('93', '4', 'name', '栏目名称', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('94', '4', 'path', '访问路径', '8', null, null, null, null, null, null, null, '2', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('95', '4', 'title', 'meta标题', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '1', null, null);
INSERT INTO `cms_model_item` VALUES ('96', '4', 'keywords', 'meta关键字', '8', null, null, null, null, null, null, null, '1', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('97', '4', 'description', 'meta描述', '8', null, null, null, null, null, null, null, '4', '1', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('98', '4', 'tplChannel', '栏目模板', '8', null, null, null, null, null, null, null, '6', '0', '1', '0', '1', '0', null, null);
INSERT INTO `cms_model_item` VALUES ('99', '4', 'tplContent', '选择模型模板', '9', null, null, null, null, null, null, null, '6', '1', '1', '0', '1', '0', null, null);

-- ----------------------------
-- Table structure for cms_origin
-- ----------------------------
DROP TABLE IF EXISTS `cms_origin`;
CREATE TABLE `cms_origin` (
  `originId` varchar(64) NOT NULL,
  `originName` varchar(255) NOT NULL COMMENT '来源名称',
  `refCount` int(11) NOT NULL DEFAULT '0' COMMENT '来源文章总数',
  PRIMARY KEY (`originId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='来源';

-- ----------------------------
-- Records of cms_origin
-- ----------------------------
INSERT INTO `cms_origin` VALUES ('1', '新浪微博', '0');
INSERT INTO `cms_origin` VALUES ('2', '百度', '0');
INSERT INTO `cms_origin` VALUES ('3', '百度论坛', '0');
INSERT INTO `cms_origin` VALUES ('4', '百度贴吧', '0');
INSERT INTO `cms_origin` VALUES ('5', '新浪新闻', '0');
INSERT INTO `cms_origin` VALUES ('6', '腾讯新闻', '0');

-- ----------------------------
-- Table structure for cms_plug
-- ----------------------------
DROP TABLE IF EXISTS `cms_plug`;
CREATE TABLE `cms_plug` (
  `plugId` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '插件名称',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `uploadTime` datetime NOT NULL COMMENT '上传时间',
  `installTime` datetime DEFAULT NULL COMMENT '安装时间',
  `uninstallTime` datetime DEFAULT NULL COMMENT '卸载时间',
  `fileConflict` tinyint(1) NOT NULL DEFAULT '0' COMMENT '包含文件是否冲突',
  `isUsed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '使用状态(0未使用,1使用中)',
  `plugPerms` varchar(255) DEFAULT '' COMMENT '插件权限（,分隔各个权限配置）',
  `plugRepair` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否修复类插件(0 新功能插件 1修复类)',
  PRIMARY KEY (`plugId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='插件';

-- ----------------------------
-- Records of cms_plug
-- ----------------------------
INSERT INTO `cms_plug` VALUES ('1', '应用中心', '/WEB-INF/plug/plugStore.zip', '应用中心', 'jeecms', '2016-09-08 09:05:12', '2016-10-08 16:12:57', '2016-10-08 16:12:39', '0', '1', 'plugStore.center@store:center;storeConfig.edit@storeConfig:v_edit;storeConfig.update@storeConfig:o_update;', '0');

-- ----------------------------
-- Table structure for cms_site
-- ----------------------------
DROP TABLE IF EXISTS `cms_site`;
CREATE TABLE `cms_site` (
  `siteId` varchar(64) NOT NULL,
  `domain` varchar(50) NOT NULL COMMENT '域名',
  `sitePath` varchar(20) NOT NULL COMMENT '路径',
  `siteName` varchar(100) NOT NULL COMMENT '网站名称',
  `shortName` varchar(100) NOT NULL COMMENT '简短名称',
  `protocol` varchar(20) NOT NULL DEFAULT 'http://' COMMENT '协议',
  `dynamicSuffix` varchar(10) NOT NULL DEFAULT '.jhtml' COMMENT '动态页后缀',
  `staticSuffix` varchar(10) NOT NULL DEFAULT '.html' COMMENT '静态页后缀',
  `staticDir` varchar(50) DEFAULT NULL COMMENT '静态页存放目录',
  `isIndexToRoot` char(1) NOT NULL DEFAULT '0' COMMENT '是否使用将首页放在根目录下',
  `isStaticIndex` char(1) NOT NULL DEFAULT '0' COMMENT '是否静态化首页',
  `localeAdmin` varchar(10) NOT NULL DEFAULT 'zh_CN' COMMENT '后台本地化',
  `localeFront` varchar(10) NOT NULL DEFAULT 'zh_CN' COMMENT '前台本地化',
  `tplSolution` varchar(50) NOT NULL DEFAULT 'default' COMMENT '模板方案',
  `finalStep` tinyint(4) NOT NULL DEFAULT '2' COMMENT '终审级别',
  `afterCheck` tinyint(4) NOT NULL DEFAULT '2' COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `isRelativePath` char(1) NOT NULL DEFAULT '1' COMMENT '是否使用相对路径',
  `isRecycleOn` char(1) NOT NULL DEFAULT '1' COMMENT '是否开启回收站',
  `domainAlias` varchar(255) DEFAULT NULL COMMENT '域名别名',
  `domainRedirect` varchar(255) DEFAULT NULL COMMENT '域名重定向',
  `tplIndex` varchar(255) DEFAULT NULL COMMENT '首页模板',
  `keywords` varchar(255) DEFAULT NULL COMMENT '站点关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '站点描述',
  `ftpSyncPageId` int(11) DEFAULT NULL COMMENT '静态页同步FTP',
  `pageIsSyncFtp` tinyint(1) NOT NULL DEFAULT '0' COMMENT '静态页同步发布FTP',
  `resouceIsSyncFtp` tinyint(1) NOT NULL DEFAULT '0' COMMENT '资源同步发布FTP',
  PRIMARY KEY (`siteId`),
  UNIQUE KEY `ak_domain_path` (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点表';

-- ----------------------------
-- Records of cms_site
-- ----------------------------
INSERT INTO `cms_site` VALUES ('1', 'localhost', 'www', 'jeecms演示站', 'jeecms演示站', 'http://', '.jhtml', '.html', '/html', '0', '0', 'zh_CN', 'zh_CN', 'default', '3', '3', '0', '1', '', '', '', 'JEECMS－JAVA网站内容管理系统|开源java cms系统,jsp cms网站管理', 'JEECMS-国内优秀的JAVA(JSP)网站内容管理系统', null, '0', '0');

-- ----------------------------
-- Table structure for cms_topic
-- ----------------------------
DROP TABLE IF EXISTS `cms_topic`;
CREATE TABLE `cms_topic` (
  `topicId` varchar(64) NOT NULL,
  `channelId` varchar(64) DEFAULT NULL,
  `topicName` varchar(150) NOT NULL COMMENT '名称',
  `shortName` varchar(150) DEFAULT NULL COMMENT '简称',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `titleImg` varchar(100) DEFAULT NULL COMMENT '标题图',
  `contentImg` varchar(100) DEFAULT NULL COMMENT '内容图',
  `tplContent` varchar(100) DEFAULT NULL COMMENT '专题模板',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `isRecommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推??',
  PRIMARY KEY (`topicId`),
  KEY `fk_jc_topic_channel` (`channelId`),
  CONSTRAINT `fk_jc_topic_channel` FOREIGN KEY (`channelId`) REFERENCES `cms_channel` (`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS专题表';

-- ----------------------------
-- Records of cms_topic
-- ----------------------------
INSERT INTO `cms_topic` VALUES ('1', null, ' 2016饲料英才网络招聘会', '饲料英才网络招聘会', '', '', '/u/cms/www/201610/10100951y2xy.jpg', '/u/cms/www/201610/11092540p27t.jpg', '', '11', '1');
INSERT INTO `cms_topic` VALUES ('2', null, '互联网+与传统产业升级之道', '互联网+', '', '', '/u/cms/www/201610/10100842hqdk.jpg', '/u/cms/www/201610/110911592mxa.jpg', '', '10', '1');
INSERT INTO `cms_topic` VALUES ('3', null, '2015全国两会', '2015全国两会', '', '', '/u/cms/www/201610/101010021q7v.jpg', '/u/cms/www/201610/11092148co74.jpg', '', '10', '1');
