/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50535
Source Host           : localhost:3306
Source Database       : qr_restaurant

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2014-02-13 15:45:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(256) DEFAULT NULL,
  `book_tel` varchar(128) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `fromMenusInBooks` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES ('4', 'glu', '13888765432', '23', '15');
INSERT INTO `books` VALUES ('5', 'glu', '13888765432', '23', '16');

-- ----------------------------
-- Table structure for categories
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
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES ('1', 'test_cat1', '1');
INSERT INTO `categories` VALUES ('2', 'test_cat2', '1');
INSERT INTO `categories` VALUES ('3', '海鲜', '2');
INSERT INTO `categories` VALUES ('4', '川菜', '1');
INSERT INTO `categories` VALUES ('5', '滇菜', '1');

-- ----------------------------
-- Table structure for cmmap
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cmmap
-- ----------------------------
INSERT INTO `cmmap` VALUES ('2', '23', '10');
INSERT INTO `cmmap` VALUES ('3', '23', '12');
INSERT INTO `cmmap` VALUES ('5', '21', '10');
INSERT INTO `cmmap` VALUES ('6', '32', '16');
INSERT INTO `cmmap` VALUES ('8', '36', '18');
INSERT INTO `cmmap` VALUES ('9', '36', '19');
INSERT INTO `cmmap` VALUES ('10', '36', '20');
INSERT INTO `cmmap` VALUES ('11', '36', '21');
INSERT INTO `cmmap` VALUES ('12', '36', '22');
INSERT INTO `cmmap` VALUES ('13', '36', '23');
INSERT INTO `cmmap` VALUES ('14', '36', '24');
INSERT INTO `cmmap` VALUES ('15', '36', '25');
INSERT INTO `cmmap` VALUES ('16', '36', '26');
INSERT INTO `cmmap` VALUES ('17', '35', '27');
INSERT INTO `cmmap` VALUES ('19', '35', '29');
INSERT INTO `cmmap` VALUES ('20', '35', '30');
INSERT INTO `cmmap` VALUES ('21', '35', '33');
INSERT INTO `cmmap` VALUES ('22', '35', '34');
INSERT INTO `cmmap` VALUES ('23', '35', '35');
INSERT INTO `cmmap` VALUES ('24', '35', '36');
INSERT INTO `cmmap` VALUES ('25', '35', '37');
INSERT INTO `cmmap` VALUES ('26', '35', '38');
INSERT INTO `cmmap` VALUES ('27', '35', '39');
INSERT INTO `cmmap` VALUES ('28', '35', '40');
INSERT INTO `cmmap` VALUES ('29', '35', '41');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_content` varchar(2048) DEFAULT NULL,
  `comment_date` date NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('6', 'asfsfsdf', '2014-01-13', '1', '34', '4');
INSERT INTO `comments` VALUES ('7', 'adfsdfdfsf', '2014-01-13', '1', '34', '5');
INSERT INTO `comments` VALUES ('9', 'Not bad', '2014-01-13', '1', '34', '5');
INSERT INTO `comments` VALUES ('10', 'Not bad, it\'s really not bad', '2014-01-13', '1', '34', '5');
INSERT INTO `comments` VALUES ('12', 'hdhjd', '2014-01-14', '1', '35', '4');

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(256) DEFAULT NULL,
  `customer_pwd` varchar(256) DEFAULT NULL,
  `customer_deviceid` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO `customers` VALUES ('20', null, null, null);
INSERT INTO `customers` VALUES ('21', null, null, null);
INSERT INTO `customers` VALUES ('22', null, '123456', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('23', 'lugehao', '8888', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('24', 'zhaoshenme', '123456', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('25', 'gaoya', '123456', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('26', 'gaoyayyy', '123456', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('27', 'goodwill', '123456', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('28', 'uutype', '123456', 'sdlfjsldjflsdjfls');
INSERT INTO `customers` VALUES ('29', 'xxssxxssxxss', '0', 'xxssxxssxxss');
INSERT INTO `customers` VALUES ('30', 'utuytutyuyt', '0', 'utuytutyuyt');
INSERT INTO `customers` VALUES ('31', 'ssdeadeasdasda', '0', 'ssdeadeasdasda');
INSERT INTO `customers` VALUES ('32', 'ssdeadeasdas777', '0', 'ssdeadeasdas777');
INSERT INTO `customers` VALUES ('33', 'uutypew', '123456', '中华人民');
INSERT INTO `customers` VALUES ('34', '96b94382f8785e0e', '0', '96b94382f8785e0e');
INSERT INTO `customers` VALUES ('35', '2f98fafe4d899780', '0', '2f98fafe4d899780');
INSERT INTO `customers` VALUES ('36', '6b44c44790a326ea', '0', '6b44c44790a326ea');

-- ----------------------------
-- Table structure for dishes
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
-- Records of dishes
-- ----------------------------
INSERT INTO `dishes` VALUES ('1', 'test_dish1', 'test description', 'i don\'t know', '32', 'chinese', '1', '566', '455', '1', '1');
INSERT INTO `dishes` VALUES ('2', 'test_dish2', 'test description', 'i love to eat', '33', 'japanese', '1', '55', '55', '1', '1');
INSERT INTO `dishes` VALUES ('3', 'test_dish3', 'test description', 'i ove to fuck', '55', 'hhahah', '1', '88', '99', '1', '1');
INSERT INTO `dishes` VALUES ('4', '麻婆豆腐', '麻婆豆腐好吃呢', '持久的身份了设计费', '66', '好吃', '1', '90', '2', '2', '1');

-- ----------------------------
-- Table structure for menudishmap
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
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menudishmap
-- ----------------------------
INSERT INTO `menudishmap` VALUES ('10', '9', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('11', '9', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('12', '9', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('13', '10', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('14', '10', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('15', '10', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('16', '11', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('17', '11', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('18', '11', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('19', '12', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('20', '12', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('21', '12', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('22', '13', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('23', '13', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('24', '13', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('25', '14', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('26', '14', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('27', '14', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('28', '15', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('29', '15', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('30', '15', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('31', '16', '1', '0', '0');
INSERT INTO `menudishmap` VALUES ('32', '16', '2', '0', '0');
INSERT INTO `menudishmap` VALUES ('33', '16', '3', '0', '0');
INSERT INTO `menudishmap` VALUES ('34', '19', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('35', '19', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('36', '19', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('37', '19', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('38', '20', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('39', '20', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('40', '20', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('41', '20', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('42', '21', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('43', '21', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('44', '21', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('45', '21', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('46', '22', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('47', '22', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('48', '22', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('49', '22', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('50', '23', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('51', '23', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('52', '23', '3', '3', '165');
INSERT INTO `menudishmap` VALUES ('53', '23', '4', '3', '198');
INSERT INTO `menudishmap` VALUES ('54', '24', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('55', '24', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('56', '24', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('57', '24', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('58', '25', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('59', '25', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('60', '25', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('61', '25', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('62', '26', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('63', '26', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('64', '26', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('65', '26', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('66', '27', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('67', '27', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('68', '27', '3', '3', '165');
INSERT INTO `menudishmap` VALUES ('69', '27', '4', '3', '198');
INSERT INTO `menudishmap` VALUES ('70', '28', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('71', '28', '2', '7', '231');
INSERT INTO `menudishmap` VALUES ('72', '28', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('73', '28', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('74', '29', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('75', '29', '2', '5', '165');
INSERT INTO `menudishmap` VALUES ('76', '29', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('77', '29', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('78', '30', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('79', '30', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('80', '30', '3', '3', '165');
INSERT INTO `menudishmap` VALUES ('81', '30', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('82', '33', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('83', '33', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('84', '33', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('85', '33', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('86', '34', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('87', '34', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('88', '34', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('89', '34', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('90', '35', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('91', '35', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('92', '35', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('93', '35', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('94', '36', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('95', '36', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('96', '36', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('97', '36', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('98', '37', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('99', '37', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('100', '37', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('101', '37', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('102', '38', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('103', '38', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('104', '38', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('105', '38', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('106', '39', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('107', '39', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('108', '39', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('109', '39', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('110', '40', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('111', '40', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('112', '40', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('113', '40', '3', '1', '55');
INSERT INTO `menudishmap` VALUES ('114', '41', '4', '1', '66');
INSERT INTO `menudishmap` VALUES ('115', '41', '1', '1', '32');
INSERT INTO `menudishmap` VALUES ('116', '41', '2', '1', '33');
INSERT INTO `menudishmap` VALUES ('117', '41', '3', '1', '55');

-- ----------------------------
-- Table structure for menus
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
  CONSTRAINT `fromRestaurants` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES ('8', '3', '2013-12-13 15:32:56', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('9', '3', '2013-12-13 15:37:32', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('10', '3', '2013-12-13 15:38:32', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('11', '3', '2013-12-13 15:39:21', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('12', '3', '2013-12-13 15:46:06', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('13', '3', '2013-12-13 15:51:00', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('14', '3', '2013-12-13 15:56:57', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('15', '3', '2013-12-13 16:00:25', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('16', '3', '2013-12-13 16:14:40', '0', '0', '1', '1');
INSERT INTO `menus` VALUES ('17', '1', '2014-01-13 17:13:21', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('18', '1', '2014-01-15 11:00:32', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('19', '1', '2014-01-15 11:48:55', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('20', '1', '2014-01-15 11:51:58', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('21', '1', '2014-01-15 12:01:40', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('22', '1', '2014-01-15 12:03:22', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('23', '1', '2014-01-15 12:06:57', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('24', '1', '2014-01-15 16:14:22', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('25', '1', '2014-01-15 16:44:29', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('26', '1', '2014-01-16 09:45:33', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('27', '1', '2014-01-16 13:33:42', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('28', '1', '2014-01-16 13:51:07', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('29', '1', '2014-01-16 13:52:04', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('30', '1', '2014-01-16 15:26:21', '186', '0', '2', '1');
INSERT INTO `menus` VALUES ('33', '1', '2014-01-16 16:48:59', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('34', '1', '2014-01-16 16:50:44', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('35', '1', '2014-01-16 16:50:59', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('36', '1', '2014-01-16 16:51:00', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('37', '1', '2014-01-16 16:51:55', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('38', '1', '2014-01-16 16:53:54', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('39', '1', '2014-01-16 16:55:32', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('40', '1', '2014-01-16 17:03:47', '186', '0', '0', '1');
INSERT INTO `menus` VALUES ('41', '1', '2014-01-16 17:05:51', '186', '0', '0', '1');

-- ----------------------------
-- Table structure for rcomments
-- ----------------------------
DROP TABLE IF EXISTS `rcomments`;
CREATE TABLE `rcomments` (
  `rcomment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rcomment_content` varchar(2048) DEFAULT NULL,
  `rcomment_date` date NOT NULL,
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
-- Records of rcomments
-- ----------------------------
INSERT INTO `rcomments` VALUES ('1', '吃些什么呀，难吃死了。', '2013-12-19', '1', '23', null);
INSERT INTO `rcomments` VALUES ('2', '好吃又如何。', '2013-12-19', '1', '23', null);
INSERT INTO `rcomments` VALUES ('3', '吃死你', '2013-12-19', '1', '23', null);

-- ----------------------------
-- Table structure for restaurants
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
-- Records of restaurants
-- ----------------------------
INSERT INTO `restaurants` VALUES ('1', '测试餐厅1', '我就是测试餐厅', '滇菜', '昆明市人民东路2号', '68865588', null);
INSERT INTO `restaurants` VALUES ('2', '测试餐厅2', '测试餐厅就是我', '川菜', '昆明东风东路121号', '13888444888', null);
INSERT INTO `restaurants` VALUES ('3', 'test restaurant 3', '3 is me', '西餐', 'England', '8888888', null);
INSERT INTO `restaurants` VALUES ('4', 'Beef restaurant 4', '4 is me', '西餐', 'France', '777777', null);

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) NOT NULL,
  `table_type` varchar(128) DEFAULT NULL,
  `table_sort` int(1) NOT NULL,
  `table_status` int(1) NOT NULL,
  `rest_id` bigint(20) NOT NULL,
  PRIMARY KEY (`table_id`),
  UNIQUE KEY `name_unique` (`table_name`,`rest_id`) USING BTREE,
  KEY `rest_id` (`rest_id`),
  KEY `sort_index_for_table_page` (`table_id`,`table_sort`) USING BTREE,
  CONSTRAINT `fromRestaurantsInTables` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tables
-- ----------------------------
INSERT INTO `tables` VALUES ('1', 'preorder', 'baoxiang', '0', '1', '1');
INSERT INTO `tables` VALUES ('2', 'b2', 'baoxiang', '0', '1', '1');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `user_pwd` varchar(255) NOT NULL,
  `user_nickname` varchar(255) NOT NULL,
  `rest_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_unique` (`user_name`) USING BTREE,
  UNIQUE KEY `nickname_unique` (`user_nickname`,`rest_id`) USING BTREE,
  KEY `rest_id` (`rest_id`),
  CONSTRAINT `fromRestaurantInUsers` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'glu', '123', 'glu', '1');
