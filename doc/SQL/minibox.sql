
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2501,40,40,116.3161623489,40.0153746854,'北京市海淀区北京大学(圆明园校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2502,40,40,116.1904453961,40.2469132138,'北京市昌平区北京大学(昌平校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2503,40,40,116.3570401231,39.7578759414,'北京市大兴区北京大学(大兴校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2504,40,40,116.3312298913,39.9798037546,'北京市海淀区北京大学附属中学');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2505,40,40,116.3571734052,39.9837566384,'北京市海淀区北京大学医学部');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2506,40,40,116.3229277045,40.0029548074,'北京市海淀区清华大学工字厅');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2507,40,40,116.3355091598,39.9999648212,'北京市海淀区清华大学美术学院');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2508,40,40,116.3757742838,40.1076647444,'北京市昌平区北京邮电大学(宏福校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2509,40,40,116.3757742838,40.1076647444,'北京市昌平区北京邮电大学(宏福校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2510,40,40,116.2888464268,40.1573991591,'北京市昌平区 北京邮电大学(沙河校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2511,40,40,116.3565445252,39.9605666118,'北京市海淀区北京邮电大学教学楼3');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2512,40,40,116.2702401680,40.1538852612,'北京市昌平区北京航空航天大学(沙河校区)');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2513,40,40,116.3512768375,39.9841386559,'北京市海淀区北京航空航天大学主楼');
insert into group_info(group_id,quantity,empty,lng,lat,position)
values(2514,40,40,116.3471868375,39.9779786559,'北京市海淀区北京航空航天大学-学知楼');
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
  alter table user_info add taken varchar(255);
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

