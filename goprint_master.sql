drop database if exists `goprint_user`;
CREATE DATABASE `goprint_user` /*!40100 DEFAULT CHARACTER SET utf8 */;
use `goprint_user`;
drop table if exists `user_detail`;
CREATE TABLE `user_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_al4vy84pj6kshsqrsn9kc63sj` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
drop table if exists `user_note`;
CREATE TABLE `user_note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` longtext NOT NULL,
  `title` varchar(50) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gbu3cfe3u59f1y9adrnijo4bf` (`user_id`),
  CONSTRAINT `FK_gbu3cfe3u59f1y9adrnijo4bf` FOREIGN KEY (`user_id`) REFERENCES `user_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
INSERT INTO `user_detail`
(`create_time`,
`email`,
`password`)
VALUES
(now(),
'user1@yopmail.com',
'password1$'),
(now(),
'user2@yopmail.com',
'password2$'),
(now(),
'user3@yopmail.com',
'password3$'),
(now(),
'user4@yopmail.com',
'password4$');