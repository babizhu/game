# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: localhost    Database: game
# ------------------------------------------------------
# Server version 5.0.22-community-nt

#
# Source for table task_base
#

DROP TABLE IF EXISTS `task_base`;
CREATE TABLE `task_base` (
  `name` varchar(30) NOT NULL default '' COMMENT '玩家id',
  `templet_id` smallint(6) NOT NULL default '0' COMMENT '任务模板id',
  `accept_sec` int(11) NOT NULL default '0' COMMENT '接任务的时间，单位：秒',
  `done_sec` int(11) NOT NULL default '0' COMMENT '完成任务，但尚未领奖的时间，单位：秒',
  `accept_award_sec` int(11) NOT NULL default '0' COMMENT '领奖时间，单位：秒',
  `parm` varchar(255) default '' COMMENT '参数字符串，根据具体的任务而定',
  `status` tinyint(3) NOT NULL default '1' COMMENT '任务状态  1：可接  2、已接  3、完成任务，尚未领奖  3、彻底完成',
  PRIMARY KEY  (`templet_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家的任务表';

#
# Source for table user_base
#

DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `name` varchar(30) NOT NULL default '',
  `nick_name` varchar(30) NOT NULL default '' COMMENT '昵称',
  `sex` tinyint(1) NOT NULL default '0' COMMENT '性别，0:女 1:男  2:人妖',
  `level` smallint(3) default '1' COMMENT '级别',
  `money` int(11) NOT NULL default '0' COMMENT '金币',
  `strength` smallint(6) NOT NULL default '100' COMMENT '体力',
  `status` tinyint(3) NOT NULL default '3' COMMENT '玩家状态，GUEST(1),NEW(2),LOGIN(3),BAN(4)',
  `login_count` int(11) NOT NULL default '0' COMMENT '玩家总的登陆次数',
  `lastlogout_time` int(11) NOT NULL default '0' COMMENT '上次下线时间',
  `create_time` int(11) NOT NULL default '0' COMMENT '创建的时间',
  `is_adult` bit(1) NOT NULL default '' COMMENT '是否成年',
  PRIMARY KEY  (`name`),
  UNIQUE KEY `nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
