# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: game
# ------------------------------------------------------
# Server version 5.0.22-community-nt

#
# Source for table user_base
#

DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `name` char(35) NOT NULL default '',
  `nick_name` varchar(35) NOT NULL default '' COMMENT '昵称',
  `sex` tinyint(1) NOT NULL default '0' COMMENT '性别，0:女 1:男  2:人妖',
  `money` int(11) NOT NULL default '0' COMMENT '金币',
  `strength` smallint(6) NOT NULL default '100' COMMENT '体力',
  `status` tinyint(3) NOT NULL default '1' COMMENT '1:正常登陆 2:被ban用户',
  `login_count` int(11) NOT NULL default '0' COMMENT '玩家总的登陆次数',
  `lastlogout_time` int(11) NOT NULL default '0' COMMENT '上次下线时间',
  `create_time` int(11) NOT NULL default '0' COMMENT '用户创建的时间',
  `is_adult` bit(1) NOT NULL default '' COMMENT '是否成年',
  PRIMARY KEY  (`name`),
  UNIQUE KEY `nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table user_base
#

LOCK TABLES `user_base` WRITE;
/*!40000 ALTER TABLE `user_base` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_base` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
