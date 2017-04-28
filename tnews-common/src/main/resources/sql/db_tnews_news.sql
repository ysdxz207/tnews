/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50520
Source Host           : 127.0.0.1:3306
Source Database       : db_tnews

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-02-02 11:51:43
*/

SET FOREIGN_KEY_CHECKS=0;


-- ----------------------------
-- Table structure for t_api_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_api_channel`;
CREATE TABLE `t_api_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(50) DEFAULT NULL,
  `channel_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `index_name` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_news
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `content` mediumtext,
  `content_with_imgs` mediumtext,
  `source` varchar(50) DEFAULT NULL,
  `all_list` mediumtext,
  `html` mediumtext,
  `face_url` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `channel_id` varchar(50) DEFAULT NULL,
  `news_channel_id` int(11) DEFAULT '0',
  `channel_name` varchar(50) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  `imageurls` varchar(1000) DEFAULT NULL,
  `pub_date` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator_id` int(11) DEFAULT '0',
  `status` tinyint(1) DEFAULT '1',
  `face_url_keys` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `index_name` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_news_api_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_news_api_channel`;
CREATE TABLE `t_news_api_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_channel_id` int(11) DEFAULT NULL,
  `news_channel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_name` (`api_channel_id`,`news_channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_news_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_news_channel`;
CREATE TABLE `t_news_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(50) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
