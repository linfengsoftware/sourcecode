-- MySQL dump 10.13  Distrib 5.7.4-m14, for Win32 (x86)
--
-- Host: localhost    Database: quickstart
-- ------------------------------------------------------
-- Server version	5.7.4-m14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `smdept`
--

DROP TABLE IF EXISTS `smdept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smdept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(128) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smdept`
--

LOCK TABLES `smdept` WRITE;
/*!40000 ALTER TABLE `smdept` DISABLE KEYS */;
INSERT INTO `smdept` VALUES (0,'root',0),(2,'部门一',0),(3,'部门1.1',2),(4,'部门二',0),(5,'部门2.1',4),(6,'部门2.2',4),(7,'部门四',0),(8,'部门2.4',4),(9,'部门2.7',4);
/*!40000 ALTER TABLE `smdept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smtask`
--

DROP TABLE IF EXISTS `smtask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smtask` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smtask`
--

LOCK TABLES `smtask` WRITE;
/*!40000 ALTER TABLE `smtask` DISABLE KEYS */;
INSERT INTO `smtask` VALUES (1,'Study PlayFramework 2.0','http://www.playframework.org/',2),(2,'Study Grails 2.0','http://www.grails.org/',2),(3,'Try SpringFuse','http://www.springfuse.com/',2),(4,'Try Spring Roo','http://www.springsource.org/spring-roo',2),(5,'Release SpringSide 4.0','As soon as posibble.',2),(6,'fdsfsad','fffff',1),(7,'fdsfsadf','sadfdsaf',1),(8,'任务2','测试',1),(9,'yyyy','y',1),(10,'uuu','r',1),(11,'测试数据123','123',1);
/*!40000 ALTER TABLE `smtask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smuser`
--

DROP TABLE IF EXISTS `smuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `roles` varchar(255) NOT NULL,
  `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smuser`
--

LOCK TABLES `smuser` WRITE;
/*!40000 ALTER TABLE `smuser` DISABLE KEYS */;
INSERT INTO `smuser` VALUES (1,'admin','Admin','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','admin','2012-06-03 17:00:00'),(2,'user','Calvin','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','user','2012-06-03 18:00:00'),(3,'linfeng','linfeng','8c183a79d88e10ef02f09aeded34811bcd573c7f','fc5015aa20fbbc72','user','2015-02-27 02:10:24');
/*!40000 ALTER TABLE `smuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-10 20:41:17
