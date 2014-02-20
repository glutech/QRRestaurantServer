/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50535
Source Host           : localhost:3306
Source Database       : new_rq

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2014-02-20 18:08:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrators
-- ----------------------------
DROP TABLE IF EXISTS `administrators`;
CREATE TABLE `administrators` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(32) NOT NULL,
  `admin_pwd` varchar(32) NOT NULL,
  `admin_nickname` varchar(8) NOT NULL,
  `rest_id_nullabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_name` (`admin_name`),
  KEY `rest_id_nullabled` (`rest_id_nullabled`),
  CONSTRAINT `administrators_ibfk_1` FOREIGN KEY (`rest_id_nullabled`) REFERENCES `restaurants` (`rest_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(32) NOT NULL,
  `book_tel` varchar(32) NOT NULL,
  `book_time` datetime NOT NULL,
  `book_memo` tinytext NOT NULL,
  `order_id` int(11) NOT NULL,
  PRIMARY KEY (`book_id`),
  KEY `books_ibfk_1` (`order_id`),
  KEY `book_time` (`book_time`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comments_for_dish
-- ----------------------------
DROP TABLE IF EXISTS `comments_for_dish`;
CREATE TABLE `comments_for_dish` (
  `cmt_id` int(11) NOT NULL AUTO_INCREMENT,
  `cmt_content` tinytext NOT NULL,
  `cmt_date` date NOT NULL,
  `cmt_rate` tinyint(4) NOT NULL,
  `user_id` int(11) NOT NULL,
  `dish_id` int(11) NOT NULL,
  PRIMARY KEY (`cmt_id`),
  KEY `comments_for_dish_ibfk_2` (`dish_id`),
  KEY `comments_for_dish_ibfk_1` (`user_id`),
  CONSTRAINT `comments_for_dish_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comments_for_dish_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comments_for_rest
-- ----------------------------
DROP TABLE IF EXISTS `comments_for_rest`;
CREATE TABLE `comments_for_rest` (
  `cmt_id` int(11) NOT NULL AUTO_INCREMENT,
  `cmt_content` tinyint(4) NOT NULL,
  `cmt_date` date NOT NULL,
  `cmt_rate` tinyint(4) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rest_id` int(11) NOT NULL,
  PRIMARY KEY (`cmt_id`),
  KEY `comments_for_rest_ibfk_2` (`rest_id`),
  KEY `comments_for_rest_ibfk_1` (`user_id`),
  CONSTRAINT `comments_for_rest_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comments_for_rest_ibfk_2` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dishes
-- ----------------------------
DROP TABLE IF EXISTS `dishes`;
CREATE TABLE `dishes` (
  `dish_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_status` enum('enabled','disabled') NOT NULL,
  `dish_name` varchar(32) NOT NULL,
  `dish_price` double NOT NULL,
  `dish_pic` varchar(255) NOT NULL,
  `dish_desc` tinytext NOT NULL,
  `rest_dish_cat_id` int(11) NOT NULL,
  PRIMARY KEY (`dish_id`),
  UNIQUE KEY `dish_status` (`dish_status`),
  UNIQUE KEY `dish_name` (`dish_name`,`rest_dish_cat_id`),
  KEY `dishes_ibfk_1` (`rest_dish_cat_id`),
  CONSTRAINT `dishes_ibfk_1` FOREIGN KEY (`rest_dish_cat_id`) REFERENCES `rest_dish_catgory_terms` (`rest_dish_cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dish_rates
-- ----------------------------
DROP TABLE IF EXISTS `dish_rates`;
CREATE TABLE `dish_rates` (
  `dish_id` int(11) NOT NULL,
  `ordered_num` int(11) NOT NULL,
  `recommend_num` int(11) NOT NULL,
  `ranking_num` int(11) NOT NULL,
  `ranking_value` float NOT NULL,
  PRIMARY KEY (`dish_id`),
  KEY `ranking_value` (`ranking_value`),
  CONSTRAINT `dish_rates_ibfk_1` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dish_tags_map
-- ----------------------------
DROP TABLE IF EXISTS `dish_tags_map`;
CREATE TABLE `dish_tags_map` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_id` int(11) NOT NULL,
  `dish_tag_id` int(11) NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `dish_id` (`dish_id`),
  KEY `dish_tag_map_ibfk_2` (`dish_tag_id`),
  CONSTRAINT `dish_tags_map_ibfk_1` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dish_tags_map_ibfk_2` FOREIGN KEY (`dish_tag_id`) REFERENCES `dish_tag_terms` (`dish_tag_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dish_tag_terms
-- ----------------------------
DROP TABLE IF EXISTS `dish_tag_terms`;
CREATE TABLE `dish_tag_terms` (
  `dish_tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_tag_name` varchar(8) NOT NULL,
  PRIMARY KEY (`dish_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_status` enum('booked','serving','completed') NOT NULL,
  `order_time` datetime NOT NULL,
  `order_due_price` double NOT NULL,
  `order_actual_price` double NOT NULL,
  `order_memo` tinytext NOT NULL,
  `creator_user_id_nullabled` int(11) DEFAULT NULL,
  `active_table_id_nullabled` int(11) DEFAULT NULL,
  `rest_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `active_table_id_nullabled` (`active_table_id_nullabled`),
  KEY `orders_ibfk_1` (`rest_id`),
  KEY `creator_user_id_nullabled` (`creator_user_id_nullabled`),
  KEY `order_status` (`order_status`),
  KEY `order_time` (`order_time`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`active_table_id_nullabled`) REFERENCES `tables` (`table_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`creator_user_id_nullabled`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_active_dishes_map
-- ----------------------------
DROP TABLE IF EXISTS `order_active_dishes_map`;
CREATE TABLE `order_active_dishes_map` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `dish_id` int(11) NOT NULL,
  `dish_count` tinyint(4) NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `order_id` (`order_id`),
  KEY `dish_id` (`dish_id`),
  CONSTRAINT `order_active_dishes_map_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_active_dishes_map_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_statement_dish_items
-- ----------------------------
DROP TABLE IF EXISTS `order_statement_dish_items`;
CREATE TABLE `order_statement_dish_items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_count` tinyint(4) NOT NULL,
  `order_id` int(11) NOT NULL,
  `dish_name` varchar(32) NOT NULL,
  `dish_price` double NOT NULL,
  `dish_pic` varchar(255) NOT NULL,
  `dish_desc` tinytext NOT NULL,
  `dish_cat_name` varchar(8) NOT NULL,
  `source_dish_id_nullabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `order_id` (`order_id`),
  KEY `source_dish_id_nullabled` (`source_dish_id_nullabled`),
  CONSTRAINT `order_statement_dish_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_statement_dish_items_ibfk_2` FOREIGN KEY (`source_dish_id_nullabled`) REFERENCES `dishes` (`dish_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_statement_table_items
-- ----------------------------
DROP TABLE IF EXISTS `order_statement_table_items`;
CREATE TABLE `order_statement_table_items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `table_name` varchar(32) NOT NULL,
  `table_type_name` varchar(8) NOT NULL,
  `source_table_id_nullabled` int(255) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `order_id` (`order_id`),
  KEY `source_table_id_nullabled` (`source_table_id_nullabled`),
  CONSTRAINT `order_statement_table_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_statement_table_items_ibfk_2` FOREIGN KEY (`source_table_id_nullabled`) REFERENCES `tables` (`table_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_users_map
-- ----------------------------
DROP TABLE IF EXISTS `order_users_map`;
CREATE TABLE `order_users_map` (
  `map_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `order_users_map_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_users_map_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for restaurants
-- ----------------------------
DROP TABLE IF EXISTS `restaurants`;
CREATE TABLE `restaurants` (
  `rest_id` int(11) NOT NULL AUTO_INCREMENT,
  `rest_name` varchar(32) NOT NULL,
  `rest_type_id` int(11) NOT NULL,
  `rest_desc` tinytext NOT NULL,
  `rest_addr` varchar(128) NOT NULL,
  `rest_tel` varchar(32) NOT NULL,
  `rest_upid_nullabled` int(20) DEFAULT NULL,
  PRIMARY KEY (`rest_id`),
  UNIQUE KEY `rest_name` (`rest_name`),
  KEY `rest_type_id` (`rest_type_id`),
  KEY `rest_upid_nullabled` (`rest_upid_nullabled`),
  CONSTRAINT `restaurants_ibfk_1` FOREIGN KEY (`rest_type_id`) REFERENCES `rest_type_terms` (`rest_type_id`) ON UPDATE CASCADE,
  CONSTRAINT `restaurants_ibfk_2` FOREIGN KEY (`rest_upid_nullabled`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rest_dish_catgory_terms
-- ----------------------------
DROP TABLE IF EXISTS `rest_dish_catgory_terms`;
CREATE TABLE `rest_dish_catgory_terms` (
  `rest_dish_cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `rest_dish_cat_name` varchar(8) NOT NULL,
  `rest_id` int(11) NOT NULL,
  PRIMARY KEY (`rest_dish_cat_id`),
  KEY `rest_dish_catgory_terms_ibfk_1` (`rest_id`),
  CONSTRAINT `rest_dish_catgory_terms_ibfk_1` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rest_rates
-- ----------------------------
DROP TABLE IF EXISTS `rest_rates`;
CREATE TABLE `rest_rates` (
  `rest_id` int(11) NOT NULL,
  `ordered_num` int(11) NOT NULL,
  `ranking_num` int(11) NOT NULL,
  `ranking_value` float NOT NULL,
  PRIMARY KEY (`rest_id`),
  KEY `ranking_value` (`ranking_value`),
  CONSTRAINT `rest_rates_ibfk_1` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rest_type_terms
-- ----------------------------
DROP TABLE IF EXISTS `rest_type_terms`;
CREATE TABLE `rest_type_terms` (
  `rest_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `rest_type_name` varchar(8) CHARACTER SET gbk NOT NULL,
  PRIMARY KEY (`rest_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `table_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_status` enum('free','booked','serving','controlled','disabled') NOT NULL,
  `table_name` varchar(32) NOT NULL,
  `table_type_id` int(11) NOT NULL,
  `table_sort` int(11) NOT NULL,
  `rest_id` int(20) NOT NULL,
  PRIMARY KEY (`table_id`),
  UNIQUE KEY `table_name` (`table_name`,`rest_id`),
  KEY `table_type_id` (`table_type_id`),
  KEY `tables_ibfk_2` (`rest_id`),
  KEY `table_status` (`table_status`),
  KEY `table_sort` (`table_sort`),
  CONSTRAINT `tables_ibfk_1` FOREIGN KEY (`table_type_id`) REFERENCES `table_type_terms` (`table_type_id`) ON UPDATE CASCADE,
  CONSTRAINT `tables_ibfk_2` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for table_type_terms
-- ----------------------------
DROP TABLE IF EXISTS `table_type_terms`;
CREATE TABLE `table_type_terms` (
  `table_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_type_name` varchar(8) CHARACTER SET gbk NOT NULL,
  PRIMARY KEY (`table_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_category` enum('device','register') NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_pwd` varchar(32) DEFAULT NULL,
  `user_device_id` varchar(255) DEFAULT NULL,
  `user_nickname` varchar(32) NOT NULL,
  `user_contact` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `user_device_id` (`user_device_id`) USING BTREE,
  KEY `user_category` (`user_category`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
