/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_tnews_member

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2017-02-24 13:54:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `nickname` varchar(15) DEFAULT NULL,
  `reg_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `head_img` varchar(50) DEFAULT NULL,
  `invite_code` varchar(20) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `credits` int(11) NOT NULL DEFAULT '0',
  `vip` tinyint(1) NOT NULL DEFAULT '0',
  `sex` tinyint(1) NOT NULL DEFAULT '0',
  `personalized_signature` varchar(255) DEFAULT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户表';


-- ----------------------------
-- Table structure for t_member_log
-- ----------------------------
DROP TABLE IF EXISTS `t_member_log`;
CREATE TABLE `t_member_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `opt` tinyint(1) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `op_user` varchar(20) DEFAULT NULL,
  `member_id` int(11) NOT NULL,
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_member_third
-- ----------------------------
DROP TABLE IF EXISTS `t_member_third`;
CREATE TABLE `t_member_third` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL,
  `open_id` varchar(50) NOT NULL,
  `member_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_member_favorite
-- ----------------------------
DROP TABLE IF EXISTS `t_member_favorite`;
CREATE TABLE `t_member_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `favorite_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

