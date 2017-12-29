-- ------------------------------
-- The update
-- ------------------------------
alter table order_info change username user_id int not null;
alter table sale_info change username user_id int not null;



CREATE TABLE `reservation_info` (
                    `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
                    `user_id` int(5) NOT NULL,
                    `open_time` datetime NOT NULL,
                    `use_time` int(5) not NULL,
                    `user_name` varchar(10) not NULL,
                    `phone_number` varchar(11) not NULL,
                    `position` varchar(50) NOT NULL,
                    `del_flag` tinyint(1) DEFAULT '0',
                    `box_id` int(5) not NULL,
                    `box_size` char(2) not NULL,
                    `exp_flag` tinyint(1) DEFAULT '0',
                    PRIMARY KEY (`reservation_id`)
                  ) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8


alter table coupon change deadline_time deadline_time timestamp;
alter table order_info change order_time order_time timestamp;
alter table reservation_info change open_time open_time timestamp;sale_info;
alter table sale_info change pay_time pay_time timestamp;
alter table sale_info change order_time order_time timestamp;
alter table user_info change use_time use_time int(5) default '0';
alter table user_info modify credibility int(5) default '100';
  -- ------------------------------
-- The update
-- ------------------------------
  alter table user_info add taken varchar(255)
-- ------------------------------
-- The update
-- ------------------------------
alter table user_info change image image varchar(255);
alter table reservation_info drop box_num;
alter table reservation_info add box_id int(5);
alter table reservation_info add box_size char(2);
alter table reservation_info add user_id int(5) not null after reservation_id;

-- ------------------------------
-- The update
-- ------------------------------
alter table `user_info` add unique(`phone_number`);
alter table reservation_info change user user_name varchar(10);
alter table reservation_info drop uere_id;
alter table reservation_info change use_time use_time int(5);
alter table reservation_info change phone_nunber phone_number varchar(11);
-- ------------------------------
-- The change of user_info
-- ------------------------------
alter table user_info add phone_number varchar(11);
alter table user_info add age varchar(4);
alter table user_info add email varchar(30);
alter table user_info add credibility int(5);
alter table user_info add user_time int(11);
alter table user_info add image varchar(100);


-- ------------------------------
-- Table structure for coupon
-- ------------------------------
 CREATE TABLE `coupon` (
 `coupon_id` INT (11) PRIMARY KEY AUTO_INCREMENT,
`user_id` int(11) DEFAULT NULL,
`money` int(5) DEFAULT NULL,
`deadline_time` varchar(20) DEFAULT NULL
`del_flag` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8


-- ------------------------------
-- The change of sale_info
-- ------------------------------
alter table sale_info change pad_time pay_time varchar(40)




reservation_info  CREATE TABLE `reservation_info` (
                    `box_num` int(5) NOT NULL,
                    `open_time` varchar(50) NOT NULL,
                    `use_time` varchar(50) NOT NULL,
                    `use_person` varchar(20) NOT NULL,
                    `phone_nunber` varchar(11) NOT NULL,
                    `position` varchar(50) NOT NULL
                  ) ENGINE=InnoDB DEFAULT CHARSET=utf8

