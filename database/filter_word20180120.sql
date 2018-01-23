/*
Navicat MySQL Data Transfer

Source Server         : 阿里云服务器
Source Server Version : 50635
Source Host           : 120.77.245.103:3306
Source Database       : filter_word

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-12-13 14:18:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for filter_wd
-- ----------------------------
DROP TABLE IF EXISTS `filter_wd`;
CREATE TABLE IF NOT EXISTS `filter_wd` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `keywords` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `keywords_unique_filter` (`keywords`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- ----------------------------
-- Records of filter_wd
-- ----------------------------

-- ----------------------------
-- Table structure for stop_wd
-- ----------------------------
DROP TABLE IF EXISTS `stop_wd`;
CREATE TABLE `stop_wd` (
  `keywords` varchar(10) DEFAULT NULL,
  UNIQUE KEY `keywords_unique` (`keywords`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stop_wd
-- ----------------------------
INSERT INTO `stop_wd` VALUES ('!');
INSERT INTO `stop_wd` VALUES ('\"');
INSERT INTO `stop_wd` VALUES ('#');
INSERT INTO `stop_wd` VALUES ('$');
INSERT INTO `stop_wd` VALUES ('%');
INSERT INTO `stop_wd` VALUES ('&');
INSERT INTO `stop_wd` VALUES ('(');
INSERT INTO `stop_wd` VALUES (')');
INSERT INTO `stop_wd` VALUES ('*');
INSERT INTO `stop_wd` VALUES (',');
INSERT INTO `stop_wd` VALUES ('.');
INSERT INTO `stop_wd` VALUES ('/');
INSERT INTO `stop_wd` VALUES (';');
INSERT INTO `stop_wd` VALUES ('?');
INSERT INTO `stop_wd` VALUES ('@');
INSERT INTO `stop_wd` VALUES ('|');
