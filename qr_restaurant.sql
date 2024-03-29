/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50535
Source Host           : localhost:3306
Source Database       : new_rq

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2014-03-16 14:23:52
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrators
-- ----------------------------
INSERT INTO `administrators` VALUES ('1', 'admin', 'admin', '管理员', '1');

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
  `user_id` int(11) NOT NULL,
  `rest_id` int(11) NOT NULL,
  `order_id_nullabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `book_time` (`book_time`),
  KEY `rest_id` (`rest_id`),
  KEY `order_id_nullabled` (`order_id_nullabled`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `books_ibfk_2` FOREIGN KEY (`order_id_nullabled`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `books_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES ('1', 'test', '123', '2014-03-16 14:18:42', '...', '1', '1', null);

-- ----------------------------
-- Table structure for book_dishes_temp_map
-- ----------------------------
DROP TABLE IF EXISTS `book_dishes_temp_map`;
CREATE TABLE `book_dishes_temp_map` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `dish_id` int(11) NOT NULL,
  `dish_count` tinyint(4) NOT NULL,
  PRIMARY KEY (`map_id`),
  UNIQUE KEY `bookid_dishid_unique` (`book_id`,`dish_id`) USING BTREE,
  KEY `dish_id` (`dish_id`),
  KEY `book_id` (`book_id`) USING BTREE,
  CONSTRAINT `book_dishes_temp_map_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_dishes_temp_map_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_dishes_temp_map
-- ----------------------------
INSERT INTO `book_dishes_temp_map` VALUES ('1', '1', '2', '1');

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
-- Records of comments_for_dish
-- ----------------------------

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
-- Records of comments_for_rest
-- ----------------------------

-- ----------------------------
-- Table structure for dishes
-- ----------------------------
DROP TABLE IF EXISTS `dishes`;
CREATE TABLE `dishes` (
  `dish_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_status` enum('normal','active','blocked') NOT NULL,
  `dish_name` varchar(32) NOT NULL,
  `dish_price` double NOT NULL,
  `dish_pic` varchar(255) NOT NULL,
  `dish_desc` tinytext NOT NULL,
  `rest_dish_cat_id` int(11) NOT NULL,
  PRIMARY KEY (`dish_id`),
  UNIQUE KEY `dish_name` (`dish_name`,`rest_dish_cat_id`),
  KEY `dishes_ibfk_1` (`rest_dish_cat_id`),
  KEY `dish_status` (`dish_status`) USING BTREE,
  CONSTRAINT `dishes_ibfk_1` FOREIGN KEY (`rest_dish_cat_id`) REFERENCES `rest_dish_category_terms` (`rest_dish_cat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dishes
-- ----------------------------
INSERT INTO `dishes` VALUES ('1', 'normal', '菜品一号', '11', 'http://wu-kui2.qiniudn.com/FlXdhUll3kzjOsdi3M5LoeAAKF8s', '菜品一号的描述', '1');
INSERT INTO `dishes` VALUES ('2', 'normal', '菜品二号', '22', 'http://wu-kui2.qiniudn.com/Fiona_W0BCGpK5eXQ3WV7fUZUbxq', '菜品二号描述', '2');
INSERT INTO `dishes` VALUES ('3', 'normal', '菜品三号', '33', 'http://wu-kui2.qiniudn.com/Fip9iTfs_y7Q5ywzWqLMSvmj43By', '菜品三号的描述', '1');

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
-- Records of dish_rates
-- ----------------------------

-- ----------------------------
-- Table structure for dish_tags_map
-- ----------------------------
DROP TABLE IF EXISTS `dish_tags_map`;
CREATE TABLE `dish_tags_map` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_id` int(11) NOT NULL,
  `dish_tag_id` int(11) NOT NULL,
  PRIMARY KEY (`map_id`),
  UNIQUE KEY `dish_id_2` (`dish_id`,`dish_tag_id`),
  KEY `dish_id` (`dish_id`),
  KEY `dish_tag_map_ibfk_2` (`dish_tag_id`),
  CONSTRAINT `dish_tags_map_ibfk_1` FOREIGN KEY (`dish_id`) REFERENCES `dishes` (`dish_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dish_tags_map_ibfk_2` FOREIGN KEY (`dish_tag_id`) REFERENCES `dish_tag_terms` (`dish_tag_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dish_tags_map
-- ----------------------------
INSERT INTO `dish_tags_map` VALUES ('2', '1', '1');
INSERT INTO `dish_tags_map` VALUES ('1', '1', '2');
INSERT INTO `dish_tags_map` VALUES ('4', '2', '1');
INSERT INTO `dish_tags_map` VALUES ('3', '2', '3');
INSERT INTO `dish_tags_map` VALUES ('5', '3', '1');

-- ----------------------------
-- Table structure for dish_tag_terms
-- ----------------------------
DROP TABLE IF EXISTS `dish_tag_terms`;
CREATE TABLE `dish_tag_terms` (
  `dish_tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `dish_tag_name` varchar(8) NOT NULL,
  PRIMARY KEY (`dish_tag_id`),
  UNIQUE KEY `dish_tag_name` (`dish_tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dish_tag_terms
-- ----------------------------
INSERT INTO `dish_tag_terms` VALUES ('2', '一号');
INSERT INTO `dish_tag_terms` VALUES ('3', '二号');
INSERT INTO `dish_tag_terms` VALUES ('1', '菜品');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', 'serving', '2014-03-16 14:18:16', '0', '0', '', '1', '2', '1');

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
-- Records of order_active_dishes_map
-- ----------------------------

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
-- Records of order_statement_dish_items
-- ----------------------------

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
-- Records of order_statement_table_items
-- ----------------------------

-- ----------------------------
-- Table structure for order_users_map
-- ----------------------------
DROP TABLE IF EXISTS `order_users_map`;
CREATE TABLE `order_users_map` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`map_id`),
  UNIQUE KEY `order_id_2` (`order_id`,`user_id`),
  KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `order_users_map_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_users_map_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_users_map
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of restaurants
-- ----------------------------
INSERT INTO `restaurants` VALUES ('1', '示例餐厅', '2', '这是一个示例餐厅', '示例地址', '示例电话', null);

-- ----------------------------
-- Table structure for rest_dish_category_terms
-- ----------------------------
DROP TABLE IF EXISTS `rest_dish_category_terms`;
CREATE TABLE `rest_dish_category_terms` (
  `rest_dish_cat_id` int(11) NOT NULL AUTO_INCREMENT,
  `rest_dish_cat_name` varchar(16) NOT NULL,
  `rest_id` int(11) NOT NULL,
  PRIMARY KEY (`rest_dish_cat_id`),
  UNIQUE KEY `rest_dish_cat_name` (`rest_dish_cat_name`,`rest_id`),
  KEY `rest_dish_catgory_terms_ibfk_1` (`rest_id`),
  CONSTRAINT `rest_dish_category_terms_ibfk_1` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rest_dish_category_terms
-- ----------------------------
INSERT INTO `rest_dish_category_terms` VALUES ('1', '分类1', '1');
INSERT INTO `rest_dish_category_terms` VALUES ('2', '分类2', '1');

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
-- Records of rest_rates
-- ----------------------------

-- ----------------------------
-- Table structure for rest_type_terms
-- ----------------------------
DROP TABLE IF EXISTS `rest_type_terms`;
CREATE TABLE `rest_type_terms` (
  `rest_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `rest_type_name` varchar(8) CHARACTER SET gbk NOT NULL,
  PRIMARY KEY (`rest_type_id`),
  UNIQUE KEY `rest_type_name` (`rest_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rest_type_terms
-- ----------------------------
INSERT INTO `rest_type_terms` VALUES ('2', '川菜');
INSERT INTO `rest_type_terms` VALUES ('1', '滇菜');
INSERT INTO `rest_type_terms` VALUES ('3', '西餐厅');

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `table_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_status` enum('free','booked','serving','controlled','blocked') NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tables
-- ----------------------------
INSERT INTO `tables` VALUES ('2', 'serving', '一号餐桌', '1', '0', '1');
INSERT INTO `tables` VALUES ('3', 'free', '二号餐桌', '1', '0', '1');
INSERT INTO `tables` VALUES ('4', 'free', '三号餐桌', '1', '0', '1');
INSERT INTO `tables` VALUES ('5', 'free', '四号餐桌', '1', '0', '1');

-- ----------------------------
-- Table structure for table_type_terms
-- ----------------------------
DROP TABLE IF EXISTS `table_type_terms`;
CREATE TABLE `table_type_terms` (
  `table_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_type_name` varchar(8) CHARACTER SET gbk NOT NULL,
  PRIMARY KEY (`table_type_id`),
  UNIQUE KEY `table_type_name` (`table_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_type_terms
-- ----------------------------
INSERT INTO `table_type_terms` VALUES ('1', '预留');

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
  `user_nickname` varchar(32) DEFAULT NULL,
  `user_contact` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `user_device_id` (`user_device_id`) USING BTREE,
  KEY `user_category` (`user_category`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'device', null, null, '6b44c44790a326ea', null, null);
