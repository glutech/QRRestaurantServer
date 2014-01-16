/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50144
 Source Host           : localhost
 Source Database       : qr_restaurant

 Target Server Type    : MySQL
 Target Server Version : 50144
 File Encoding         : utf-8

 Date: 01/13/2014 17:33:10 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `books`
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(256) DEFAULT NULL,
  `book_tel` varchar(128) DEFAULT NULL,
  `book_time` varchar(20) DEFAULT NULL,
  `book_memo` varchar(512) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `fromMenusInBooks` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `books`
-- ----------------------------
BEGIN;
INSERT INTO `books` VALUES ('4', 'glu', '13888765432', null, null, '23', '15'), ('5', 'glu', '13888765432', null, null, '23', '16');
COMMIT;

-- ----------------------------
--  Table structure for `categories`
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `cat_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(128) NOT NULL,
  `rest_id` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id`),
  KEY `rest_id` (`rest_id`),
  CONSTRAINT `fromRestaurantInCategories` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `categories`
-- ----------------------------
BEGIN;
INSERT INTO `categories` VALUES ('1', 'test_cat1', '1'), ('2', 'test_cat2', '1'), ('3', '海鲜', '2'), ('4', '川菜', '1'), ('5', '滇菜', '1');
COMMIT;

-- ----------------------------
--  Table structure for `cmmap`
-- ----------------------------
DROP TABLE IF EXISTS `cmmap`;
CREATE TABLE `cmmap` (
  `cmmap_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`cmmap_id`),
  KEY `customer_id` (`customer_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `222` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `333` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `cmmap`
-- ----------------------------
BEGIN;
INSERT INTO `cmmap` VALUES ('2', '23', '10'), ('3', '23', '12'), ('5', '21', '10'), ('6', '32', '16');
COMMIT;

-- ----------------------------
--  Table structure for `comments`
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_content` varchar(2048) DEFAULT NULL,
  `comment_date` datetime NOT NULL,
  `dish_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `dish_rate` int(5) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `dish_id` (`dish_id`),
  KEY `customer_id` (`customer_id`),
  KEY `dish_id_2` (`dish_id`),
  KEY `customer_id_2` (`customer_id`),
  CONSTRAINT `fromCustomersInComments` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `fromDishesInComments` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `comments`
-- ----------------------------
BEGIN;
INSERT INTO `comments` VALUES ('1', '真jb难吃啊', '2013-12-12 00:00:00', '1', '24', null), ('2', '好吃呢', '2013-12-02 00:00:00', '2', '23', null), ('3', 'Good to eat', '2013-12-27 00:00:00', '1', '26', null), ('4', 'This is just a little test!', '2010-07-12 00:00:00', '1', '23', null), ('5', '好吃啊', '2014-01-13 17:25:55', '1', '32', '4');
COMMIT;

-- ----------------------------
--  Table structure for `customers`
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(256) DEFAULT NULL,
  `customer_pwd` varchar(256) DEFAULT NULL,
  `customer_deviceid` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `customers`
-- ----------------------------
BEGIN;
INSERT INTO `customers` VALUES ('20', null, null, null), ('21', null, null, null), ('22', null, '123456', 'sdlfjsldjflsdjfls'), ('23', 'lugehao', '8888', 'sdlfjsldjflsdjfls'), ('24', 'zhaoshenme', '123456', 'sdlfjsldjflsdjfls'), ('25', 'gaoya', '123456', 'sdlfjsldjflsdjfls'), ('26', 'gaoyayyy', '123456', 'sdlfjsldjflsdjfls'), ('27', 'goodwill', '123456', 'sdlfjsldjflsdjfls'), ('28', 'uutype', '123456', 'sdlfjsldjflsdjfls'), ('29', 'xxssxxssxxss', '0', 'xxssxxssxxss'), ('30', 'utuytutyuyt', '0', 'utuytutyuyt'), ('31', 'ssdeadeasdasda', '0', 'ssdeadeasdasda'), ('32', 'ssdeadeasdas777', '0', 'ssdeadeasdas777'), ('33', 'uutypew', '123456', '中华人民');
COMMIT;

-- ----------------------------
--  Table structure for `dishes`
-- ----------------------------
DROP TABLE IF EXISTS `dishes`;
CREATE TABLE `dishes` (
  `dish_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dish_name` varchar(512) DEFAULT NULL,
  `dish_desc` varchar(1024) DEFAULT NULL,
  `dish_pic` varchar(512) DEFAULT NULL,
  `dish_price` double DEFAULT NULL,
  `dish_tag` varchar(1024) DEFAULT NULL,
  `dish_status` int(1) DEFAULT NULL,
  `dish_recommend` int(3) DEFAULT NULL,
  `dish_ordered` int(3) DEFAULT NULL,
  `cat_id` bigint(20) DEFAULT NULL,
  `rest_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`dish_id`),
  KEY `rest_id` (`rest_id`),
  KEY `cat_id` (`cat_id`),
  CONSTRAINT `fromCategory` FOREIGN KEY (`cat_id`) REFERENCES `categories` (`cat_id`),
  CONSTRAINT `fromRestaurant` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `dishes`
-- ----------------------------
BEGIN;
INSERT INTO `dishes` VALUES ('1', 'test_dish1', 'test description', 'i don\'t know', '32', 'chinese', '1', '566', '455', '1', '1'), ('2', 'test_dish2', 'test description', 'i love to eat', '33', 'japanese', '1', '55', '55', '1', '1'), ('3', 'test_dish3', 'test description', 'i ove to fuck', '55', 'hhahah', '1', '88', '99', '1', '1'), ('4', '麻婆豆腐', '麻婆豆腐好吃呢', '持久的身份了设计费', '66', '好吃', '1', '90', '2', '2', '1');
COMMIT;

-- ----------------------------
--  Table structure for `menudishmap`
-- ----------------------------
DROP TABLE IF EXISTS `menudishmap`;
CREATE TABLE `menudishmap` (
  `map_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL,
  `dish_id` bigint(20) NOT NULL,
  `map_num` int(2) NOT NULL,
  `map_price` double(6,0) NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `dish_id` (`dish_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `fromDishes` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`),
  CONSTRAINT `fromMenus` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `menudishmap`
-- ----------------------------
BEGIN;
INSERT INTO `menudishmap` VALUES ('10', '9', '1', '0', '0'), ('11', '9', '2', '0', '0'), ('12', '9', '3', '0', '0'), ('13', '10', '1', '0', '0'), ('14', '10', '2', '0', '0'), ('15', '10', '3', '0', '0'), ('16', '11', '1', '0', '0'), ('17', '11', '2', '0', '0'), ('18', '11', '3', '0', '0'), ('19', '12', '1', '0', '0'), ('20', '12', '2', '0', '0'), ('21', '12', '3', '0', '0'), ('22', '13', '1', '0', '0'), ('23', '13', '2', '0', '0'), ('24', '13', '3', '0', '0'), ('25', '14', '1', '0', '0'), ('26', '14', '2', '0', '0'), ('27', '14', '3', '0', '0'), ('28', '15', '1', '0', '0'), ('29', '15', '2', '0', '0'), ('30', '15', '3', '0', '0'), ('31', '16', '1', '0', '0'), ('32', '16', '2', '0', '0'), ('33', '16', '3', '0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `menus`
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_status` int(1) DEFAULT NULL,
  `menu_time` datetime DEFAULT NULL,
  `menu_price` double DEFAULT NULL,
  `menu_type` int(1) DEFAULT NULL,
  `table_id` bigint(20) DEFAULT NULL,
  `rest_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `table_id` (`table_id`),
  KEY `rest_id` (`rest_id`),
  CONSTRAINT `fromRestaurants` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`),
  CONSTRAINT `fromTables` FOREIGN KEY (`table_id`) REFERENCES `tables` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `menus`
-- ----------------------------
BEGIN;
INSERT INTO `menus` VALUES ('8', '3', '2013-12-13 15:32:56', '0', '0', '1', '1'), ('9', '3', '2013-12-13 15:37:32', '0', '0', '1', '1'), ('10', '3', '2013-12-13 15:38:32', '0', '0', '1', '1'), ('11', '3', '2013-12-13 15:39:21', '0', '0', '1', '1'), ('12', '3', '2013-12-13 15:46:06', '0', '0', '1', '1'), ('13', '3', '2013-12-13 15:51:00', '0', '0', '1', '1'), ('14', '3', '2013-12-13 15:56:57', '0', '0', '1', '1'), ('15', '3', '2013-12-13 16:00:25', '0', '0', '1', '1'), ('16', '3', '2013-12-13 16:14:40', '0', '0', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `rcomments`
-- ----------------------------
DROP TABLE IF EXISTS `rcomments`;
CREATE TABLE `rcomments` (
  `rcomment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rcomment_content` varchar(2048) DEFAULT NULL,
  `rcomment_date` datetime NOT NULL,
  `rest_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `rest_rate` int(5) DEFAULT NULL,
  PRIMARY KEY (`rcomment_id`),
  KEY `rest_id` (`rest_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `fromCustomerInRcomment` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `fromRestInRcomments` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `rcomments`
-- ----------------------------
BEGIN;
INSERT INTO `rcomments` VALUES ('1', '吃些什么呀，难吃死了。', '2013-12-19 00:00:00', '1', '23', null), ('2', '好吃又如何。', '2013-12-19 00:00:00', '1', '23', null), ('3', '吃死你', '2013-12-19 00:00:00', '1', '23', null);
COMMIT;

-- ----------------------------
--  Table structure for `restaurants`
-- ----------------------------
DROP TABLE IF EXISTS `restaurants`;
CREATE TABLE `restaurants` (
  `rest_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rest_name` varchar(512) DEFAULT NULL,
  `rest_desc` varchar(2048) DEFAULT NULL,
  `rest_type` varchar(128) DEFAULT NULL,
  `rest_addr` varchar(512) DEFAULT NULL,
  `rest_tel` varchar(128) DEFAULT NULL,
  `rest_upid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`rest_id`),
  KEY `rest_upid` (`rest_upid`),
  CONSTRAINT `fromUpRestaurant` FOREIGN KEY (`rest_upid`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `restaurants`
-- ----------------------------
BEGIN;
INSERT INTO `restaurants` VALUES ('1', '测试餐厅1', '我就是测试餐厅', '滇菜', '昆明市人民东路2号', '68865588', null), ('2', '测试餐厅2', '测试餐厅就是我', '川菜', '昆明东风东路121号', '13888444888', null), ('3', 'test restaurant 3', '3 is me', '西餐', 'England', '8888888', null), ('4', 'Beef restaurant 4', '4 is me', '西餐', 'France', '777777', null);
COMMIT;

-- ----------------------------
--  Table structure for `tables`
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(256) DEFAULT NULL,
  `table_type` varchar(128) DEFAULT NULL,
  `table_status` int(1) DEFAULT NULL,
  `rest_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`table_id`),
  KEY `rest_id` (`rest_id`),
  CONSTRAINT `fromRestaurantsInTables` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tables`
-- ----------------------------
BEGIN;
INSERT INTO `tables` VALUES ('1', 'preorder', 'baoxiang', '1', '1'), ('2', 'b2', 'baoxiang', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `user_pwd` varchar(256) DEFAULT NULL,
  `rest_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `rest_id` (`rest_id`),
  CONSTRAINT `fromRestaurantInUsers` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `users`
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('0', 'glu', '123', null);
COMMIT;

