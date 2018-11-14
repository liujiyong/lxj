/*
SQLyog Ultimate - MySQL GUI v8.21 
MySQL - 5.6.36 : Database - lxj
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lxj` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `lxj`;

/*Table structure for table `t_news` */

DROP TABLE IF EXISTS `t_news`;

CREATE TABLE `t_news` (
  `id` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '公告ID',
  `title` varchar(256) NOT NULL COMMENT '公告标题',
  `content` varchar(4000) DEFAULT NULL COMMENT '公告内容',
  `create_user` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '公告创建者',
  `create_time` datetime NOT NULL COMMENT '公告创建时间',
  `update_user` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '公告更新者',
  `update_time` datetime NOT NULL COMMENT '公告更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_news` */

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `id` varchar(45) NOT NULL COMMENT '权限ID',
  `permission_name` varchar(256) DEFAULT NULL COMMENT '权限名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `t_permission` */

insert  into `t_permission`(`id`,`permission_name`) values ('add','添加权限'),('delete','删除权限'),('edit','修改权限'),('view','查看权限');

/*Table structure for table `t_resources_auth` */

DROP TABLE IF EXISTS `t_resources_auth`;

CREATE TABLE `t_resources_auth` (
  `id` varchar(45) NOT NULL COMMENT '资源权限ID',
  `resource` varchar(128) NOT NULL COMMENT '资源URI',
  `resource_description` varchar(256) DEFAULT NULL COMMENT '资源描述',
  `authorized` varchar(128) NOT NULL COMMENT '授权字符串',
  `create_user` varchar(45) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user` varchar(45) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`resource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_resources_auth` */

insert  into `t_resources_auth`(`id`,`resource`,`resource_description`,`authorized`,`create_user`,`create_time`,`update_user`,`update_time`) values ('2','/api/v1/user/**','用户管理相关接口','jwt,roles[admin]','admin','2018-11-13 00:00:00','admin','2018-11-13 00:00:00'),('1','/authentication/**','登录登出接口','anon','admin','2018-11-13 00:00:00','admin','2018-11-13 00:00:00');

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` varchar(45) NOT NULL COMMENT '角色ID',
  `role_name` varchar(256) NOT NULL COMMENT '角色名称',
  `permission_id` varchar(256) NOT NULL COMMENT '角色权限ID（多个权限逗号隔开）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`role_name`,`permission_id`) values ('admin','管理员','view,edit,delete,add'),('user','用户','view');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` varchar(45) NOT NULL COMMENT '用户UUID',
  `user_id` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `role_id` varchar(45) DEFAULT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`user_id`),
  KEY `FK_ROLE_ID_IDX` (`role_id`),
  CONSTRAINT `FK_t_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`user_id`,`password`,`nick_name`,`email`,`role_id`,`create_time`,`last_login_time`,`status`) values ('1','admin','admin','大波斯',NULL,'admin',NULL,NULL,1),('2','user','123456','小虾米',NULL,'user',NULL,NULL,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
