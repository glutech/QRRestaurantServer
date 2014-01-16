# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.14)
# Database: qr_restaurant
# Generation Time: 2014-01-16 09:24:52 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table books
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;

INSERT INTO `books` (`book_id`, `book_name`, `book_tel`, `customer_id`, `menu_id`)
VALUES
	(4,'glu','13888765432',23,15),
	(5,'glu','13888765432',23,16);

/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table categories
# ------------------------------------------------------------

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `cat_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(128) NOT NULL,
  `rest_id` bigint(20) NOT NULL,
  PRIMARY KEY (`cat_id`),
  KEY `rest_id` (`rest_id`),
  CONSTRAINT `fromRestaurantInCategories` FOREIGN KEY (`rest_id`) REFERENCES `restaurants` (`rest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;

INSERT INTO `categories` (`cat_id`, `cat_name`, `rest_id`)
VALUES
	(1,'test_cat1',1),
	(2,'test_cat2',1),
	(3,'海鲜',2),
	(4,'川菜',1),
	(5,'滇菜',1);

/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cmmap
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `cmmap` WRITE;
/*!40000 ALTER TABLE `cmmap` DISABLE KEYS */;

INSERT INTO `cmmap` (`cmmap_id`, `customer_id`, `menu_id`)
VALUES
	(2,23,10),
	(3,23,12),
	(5,21,10),
	(6,32,16),
	(8,36,18),
	(9,36,19),
	(10,36,20),
	(11,36,21),
	(12,36,22),
	(13,36,23),
	(14,36,24),
	(15,36,25),
	(16,36,26),
	(17,35,27),
	(19,35,29),
	(20,35,30),
	(21,35,33),
	(22,35,34),
	(23,35,35),
	(24,35,36),
	(25,35,37),
	(26,35,38),
	(27,35,39),
	(28,35,40),
	(29,35,41);

/*!40000 ALTER TABLE `cmmap` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table comments
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;

INSERT INTO `comments` (`comment_id`, `comment_content`, `comment_date`, `dish_id`, `customer_id`, `dish_rate`)
VALUES
	(6,'asfsfsdf','2014-01-13',1,34,4),
	(7,'adfsdfdfsf','2014-01-13',1,34,5),
	(9,'Not bad','2014-01-13',1,34,5),
	(10,'Not bad, it\'s really not bad','2014-01-13',1,34,5),
	(12,'hdhjd','2014-01-14',1,35,4);

/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table customers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(256) DEFAULT NULL,
  `customer_pwd` varchar(256) DEFAULT NULL,
  `customer_deviceid` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;

INSERT INTO `customers` (`customer_id`, `customer_name`, `customer_pwd`, `customer_deviceid`)
VALUES
	(20,NULL,NULL,NULL),
	(21,NULL,NULL,NULL),
	(22,NULL,'123456','sdlfjsldjflsdjfls'),
	(23,'lugehao','8888','sdlfjsldjflsdjfls'),
	(24,'zhaoshenme','123456','sdlfjsldjflsdjfls'),
	(25,'gaoya','123456','sdlfjsldjflsdjfls'),
	(26,'gaoyayyy','123456','sdlfjsldjflsdjfls'),
	(27,'goodwill','123456','sdlfjsldjflsdjfls'),
	(28,'uutype','123456','sdlfjsldjflsdjfls'),
	(29,'xxssxxssxxss','0','xxssxxssxxss'),
	(30,'utuytutyuyt','0','utuytutyuyt'),
	(31,'ssdeadeasdasda','0','ssdeadeasdasda'),
	(32,'ssdeadeasdas777','0','ssdeadeasdas777'),
	(33,'uutypew','123456','中华人民'),
	(34,'96b94382f8785e0e','0','96b94382f8785e0e'),
	(35,'2f98fafe4d899780','0','2f98fafe4d899780'),
	(36,'6b44c44790a326ea','0','6b44c44790a326ea');

/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table dishes
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;

INSERT INTO `dishes` (`dish_id`, `dish_name`, `dish_desc`, `dish_pic`, `dish_price`, `dish_tag`, `dish_status`, `dish_recommend`, `dish_ordered`, `cat_id`, `rest_id`)
VALUES
	(1,'test_dish1','test description','i don\'t know',32,'chinese',1,566,455,1,1),
	(2,'test_dish2','test description','i love to eat',33,'japanese',1,55,55,1,1),
	(3,'test_dish3','test description','i ove to fuck',55,'hhahah',1,88,99,1,1),
	(4,'麻婆豆腐','麻婆豆腐好吃呢','持久的身份了设计费',66,'好吃',1,90,2,2,1);

/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table menudishmap
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `menudishmap` WRITE;
/*!40000 ALTER TABLE `menudishmap` DISABLE KEYS */;

INSERT INTO `menudishmap` (`map_id`, `menu_id`, `dish_id`, `map_num`, `map_price`)
VALUES
	(10,9,1,0,0),
	(11,9,2,0,0),
	(12,9,3,0,0),
	(13,10,1,0,0),
	(14,10,2,0,0),
	(15,10,3,0,0),
	(16,11,1,0,0),
	(17,11,2,0,0),
	(18,11,3,0,0),
	(19,12,1,0,0),
	(20,12,2,0,0),
	(21,12,3,0,0),
	(22,13,1,0,0),
	(23,13,2,0,0),
	(24,13,3,0,0),
	(25,14,1,0,0),
	(26,14,2,0,0),
	(27,14,3,0,0),
	(28,15,1,0,0),
	(29,15,2,0,0),
	(30,15,3,0,0),
	(31,16,1,0,0),
	(32,16,2,0,0),
	(33,16,3,0,0),
	(34,19,1,1,32),
	(35,19,2,1,33),
	(36,19,3,1,55),
	(37,19,4,1,66),
	(38,20,1,1,32),
	(39,20,2,1,33),
	(40,20,3,1,55),
	(41,20,4,1,66),
	(42,21,1,1,32),
	(43,21,2,1,33),
	(44,21,3,1,55),
	(45,21,4,1,66),
	(46,22,1,1,32),
	(47,22,2,1,33),
	(48,22,3,1,55),
	(49,22,4,1,66),
	(50,23,1,1,32),
	(51,23,2,1,33),
	(52,23,3,3,165),
	(53,23,4,3,198),
	(54,24,1,1,32),
	(55,24,2,1,33),
	(56,24,3,1,55),
	(57,24,4,1,66),
	(58,25,1,1,32),
	(59,25,2,1,33),
	(60,25,3,1,55),
	(61,25,4,1,66),
	(62,26,1,1,32),
	(63,26,2,1,33),
	(64,26,3,1,55),
	(65,26,4,1,66),
	(66,27,1,1,32),
	(67,27,2,1,33),
	(68,27,3,3,165),
	(69,27,4,3,198),
	(70,28,1,1,32),
	(71,28,2,7,231),
	(72,28,3,1,55),
	(73,28,4,1,66),
	(74,29,1,1,32),
	(75,29,2,5,165),
	(76,29,3,1,55),
	(77,29,4,1,66),
	(78,30,1,1,32),
	(79,30,2,1,33),
	(80,30,3,3,165),
	(81,30,4,1,66),
	(82,33,4,1,66),
	(83,33,1,1,32),
	(84,33,2,1,33),
	(85,33,3,1,55),
	(86,34,4,1,66),
	(87,34,1,1,32),
	(88,34,2,1,33),
	(89,34,3,1,55),
	(90,35,4,1,66),
	(91,35,1,1,32),
	(92,35,2,1,33),
	(93,35,3,1,55),
	(94,36,4,1,66),
	(95,36,1,1,32),
	(96,36,2,1,33),
	(97,36,3,1,55),
	(98,37,4,1,66),
	(99,37,1,1,32),
	(100,37,2,1,33),
	(101,37,3,1,55),
	(102,38,4,1,66),
	(103,38,1,1,32),
	(104,38,2,1,33),
	(105,38,3,1,55),
	(106,39,4,1,66),
	(107,39,1,1,32),
	(108,39,2,1,33),
	(109,39,3,1,55),
	(110,40,4,1,66),
	(111,40,1,1,32),
	(112,40,2,1,33),
	(113,40,3,1,55),
	(114,41,4,1,66),
	(115,41,1,1,32),
	(116,41,2,1,33),
	(117,41,3,1,55);

/*!40000 ALTER TABLE `menudishmap` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table menus
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;

INSERT INTO `menus` (`menu_id`, `menu_status`, `menu_time`, `menu_price`, `menu_type`, `table_id`, `rest_id`)
VALUES
	(8,3,'2013-12-13 15:32:56',0,0,1,1),
	(9,3,'2013-12-13 15:37:32',0,0,1,1),
	(10,3,'2013-12-13 15:38:32',0,0,1,1),
	(11,3,'2013-12-13 15:39:21',0,0,1,1),
	(12,3,'2013-12-13 15:46:06',0,0,1,1),
	(13,3,'2013-12-13 15:51:00',0,0,1,1),
	(14,3,'2013-12-13 15:56:57',0,0,1,1),
	(15,3,'2013-12-13 16:00:25',0,0,1,1),
	(16,3,'2013-12-13 16:14:40',0,0,1,1),
	(17,1,'2014-01-13 17:13:21',186,0,2,1),
	(18,1,'2014-01-15 11:00:32',186,0,2,1),
	(19,1,'2014-01-15 11:48:55',186,0,2,1),
	(20,1,'2014-01-15 11:51:58',186,0,2,1),
	(21,1,'2014-01-15 12:01:40',186,0,2,1),
	(22,1,'2014-01-15 12:03:22',186,0,2,1),
	(23,1,'2014-01-15 12:06:57',186,0,2,1),
	(24,1,'2014-01-15 16:14:22',186,0,2,1),
	(25,1,'2014-01-15 16:44:29',186,0,2,1),
	(26,1,'2014-01-16 09:45:33',186,0,2,1),
	(27,1,'2014-01-16 13:33:42',186,0,2,1),
	(28,1,'2014-01-16 13:51:07',186,0,2,1),
	(29,1,'2014-01-16 13:52:04',186,0,2,1),
	(30,1,'2014-01-16 15:26:21',186,0,2,1),
	(33,1,'2014-01-16 16:48:59',186,0,0,1),
	(34,1,'2014-01-16 16:50:44',186,0,0,1),
	(35,1,'2014-01-16 16:50:59',186,0,0,1),
	(36,1,'2014-01-16 16:51:00',186,0,0,1),
	(37,1,'2014-01-16 16:51:55',186,0,0,1),
	(38,1,'2014-01-16 16:53:54',186,0,0,1),
	(39,1,'2014-01-16 16:55:32',186,0,0,1),
	(40,1,'2014-01-16 17:03:47',186,0,0,1),
	(41,1,'2014-01-16 17:05:51',186,0,0,1);

/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rcomments
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `rcomments` WRITE;
/*!40000 ALTER TABLE `rcomments` DISABLE KEYS */;

INSERT INTO `rcomments` (`rcomment_id`, `rcomment_content`, `rcomment_date`, `rest_id`, `customer_id`, `rest_rate`)
VALUES
	(1,'吃些什么呀，难吃死了。','2013-12-19',1,23,NULL),
	(2,'好吃又如何。','2013-12-19',1,23,NULL),
	(3,'吃死你','2013-12-19',1,23,NULL);

/*!40000 ALTER TABLE `rcomments` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table restaurants
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;

INSERT INTO `restaurants` (`rest_id`, `rest_name`, `rest_desc`, `rest_type`, `rest_addr`, `rest_tel`, `rest_upid`)
VALUES
	(1,'测试餐厅1','我就是测试餐厅','滇菜','昆明市人民东路2号','68865588',NULL),
	(2,'测试餐厅2','测试餐厅就是我','川菜','昆明东风东路121号','13888444888',NULL),
	(3,'test restaurant 3','3 is me','西餐','England','8888888',NULL),
	(4,'Beef restaurant 4','4 is me','西餐','France','777777',NULL);

/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tables
# ------------------------------------------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tables` WRITE;
/*!40000 ALTER TABLE `tables` DISABLE KEYS */;

INSERT INTO `tables` (`table_id`, `table_name`, `table_type`, `table_status`, `rest_id`)
VALUES
	(1,'preorder','baoxiang',1,1),
	(2,'b2','baoxiang',1,1);

/*!40000 ALTER TABLE `tables` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

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

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`user_id`, `user_name`, `user_pwd`, `rest_id`)
VALUES
	(0,'glu','123',NULL);

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
