/*
SQLyog Enterprise Trial - MySQL GUI v7.11 
MySQL - 5.7.18-log : Database - minibox
*********************************************************************
*/
DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `admin_name` VARCHAR(20) NOT NULL,
  `password`   VARCHAR(20) NOT NULL,
  PRIMARY KEY (`admin_id`)
);

DROP TABLE IF EXISTS `box_info`;

CREATE TABLE `box_info` (
  `box_id` int(10) NOT NULL,
  `box_size` varchar(4) NOT NULL,
  `box_status` varchar(4) NOT NULL,
  `group_id` int(6) NOT NULL
);

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `money` int(5) DEFAULT NULL,
  `deadline_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`coupon_id`)
);

DROP TABLE IF EXISTS `group_info`;

CREATE TABLE `group_info` (
  `group_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL DEFAULT '40',
  `empty` int(11) NOT NULL,
  `lng` double NOT NULL,
  `lat` double NOT NULL,
  `position` varchar(40) NOT NULL,
  PRIMARY KEY (`group_id`)
);

DROP TABLE IF EXISTS `order_info`;

CREATE TABLE `order_info` (
  `order_id` int(12) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL ,
  `group_id` int(8) NOT NULL,
  `box_id` int(10) NOT NULL,
  `pay_time` datetime DEFAULT NULL,
  `money` DOUBLE NOT NULL DEFAULT '0.0',
  `order_time` datetime NOT NULL,
  `del_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`order_id`)
);

DROP TABLE IF EXISTS `reservation_info`;

CREATE TABLE `reservation_info` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(5) NOT NULL,
  `open_time` datetime NOT NULL,
  `use_time` int(5) NOT NULL,
  `user_name` varchar(10) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `group_id` int(10) unsigned DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT '0',
  `box_id` int(5) NOT NULL,
  `box_size` char(2) NOT NULL,
  `exp_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`reservation_id`)
);

DROP TABLE IF EXISTS `sale_info`;

CREATE TABLE `sale_info` (
  `sale_info_id` int(5) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `box_id` int(6) NOT NULL,
  `group_id` int(4) NOT NULL,
  `pay_time` datetime NOT NULL,
  `order_time` datetime NOT NULL,
  `cost` DOUBLE NOT NULL,
  PRIMARY KEY (`sale_info_id`)
);

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `true_name` varchar(10) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `verify_code` char(6) DEFAULT NULL,
  `credibility` int(11) NOT NULL DEFAULT '100',
  `use_time` int(5) NOT NULL DEFAULT '0',
  `image` varchar(255) DEFAULT NULL,
  `taken` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`username`),
  UNIQUE(`username`),
  UNIQUE(`phone_number`),
);

DROP TABLE IF EXISTS `verifycode`;

CREATE TABLE `verifycode` (
  `phone_number` char(11) NOT NULL,
  `verify_code` char(6) NOT NULL,
  PRIMARY KEY (`phone_number`,`verify_code`)
);


DROP TABLE IF EXISTS `transportation_info`;

CREATE TABLE transportation_info
(
  transportation_id      INT AUTO_INCREMENT
    PRIMARY KEY,
  user_id                INT                 NOT NULL,
  start_place            VARCHAR(50)         NOT NULL,
  end_place              VARCHAR(50)         NOT NULL,
  name                   VARCHAR(10)         NOT NULL,
  phone_number           VARCHAR(11)         NOT NULL,
  receive_time           DATE                NOT NULL,
  goods_type             VARCHAR(100)        NOT NULL,
  company                VARCHAR(50)         NOT NULL,
  transportation_comment TEXT                NULL,
  cost                   DECIMAL(5, 2)       NULL,
  score                  INT                 NULL,
  transportation_status  TINYINT DEFAULT '0' NULL
  COMMENT '0 表示没有被运送 1 表示正在被运送 2 表示运送结束',
  finished_flag          TINYINT DEFAULT '0' NULL
  COMMENT '0 表示没有完成 1 用户已经确认完成',
  del_flag               TINYINT DEFAULT '0' NULL
)
  ENGINE = InnoDB
  CHARSET = utf8;
