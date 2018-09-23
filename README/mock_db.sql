CREATE DATABASE  IF NOT EXISTS `groupred` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `groupred`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 172.17.0.3    Database: groupred
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp9msm7mx8nk2qo2xs8td3clg7` (`task_id`),
  CONSTRAINT `FKp9msm7mx8nk2qo2xs8td3clg7` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2hsytmxysatfvt0p1992cw449` (`user_id`),
  CONSTRAINT `FK2hsytmxysatfvt0p1992cw449` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`, `description`, `status`, `title`, `user_id`) VALUES (1,'some text','STARTED','a title',NULL),(2,'some text','STARTED','a title 22',NULL),(3,'some text','UNSTARTED','IMPORTANT',NULL),(4,'some text','STARTED','here',NULL),(5,'difference','STARTED','mja',NULL);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_helpers`
--

DROP TABLE IF EXISTS `task_helpers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_helpers` (
  `task_id` bigint(20) NOT NULL,
  `helpers_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`helpers_id`),
  KEY `FKc4fh6wr77ukv8471ul61xmre6` (`helpers_id`),
  CONSTRAINT `FKarxl1ytvdvec6g6amv9pro1fg` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKc4fh6wr77ukv8471ul61xmre6` FOREIGN KEY (`helpers_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_helpers`
--

LOCK TABLES `task_helpers` WRITE;
/*!40000 ALTER TABLE `task_helpers` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_helpers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `max_users` int(11) NOT NULL DEFAULT '10',
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` (`id`, `active`, `max_users`, `name`) VALUES (1,'\0',10,'ze bestest team'),(2,'\0',10,'banana team');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_number` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pao7fvyhbiu7kg0ubf1copfps` (`user_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `active`, `first_name`, `last_name`, `user_name`, `user_number`) VALUES (1,'','Richard222','Hagstrom22','Totoro2210',1233),(2,'','Tomas','Hagstrom22','Alltid1011',1234),(3,'\0','Leva','Boden','lboden',1235),(4,'','Rocko','Balboa','balboa44',1236),(5,'','Thomas','Adrian','tardyLimit',1237);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_teams`
--

DROP TABLE IF EXISTS `user_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_teams` (
  `user_id` bigint(20) NOT NULL,
  `teams_id` bigint(20) NOT NULL,
  KEY `FKjqux92pssgho3pl0eh3vfx25l` (`teams_id`),
  KEY `FKoglhsqr2v8q94gjkchmu2c2ab` (`user_id`),
  CONSTRAINT `FKjqux92pssgho3pl0eh3vfx25l` FOREIGN KEY (`teams_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FKoglhsqr2v8q94gjkchmu2c2ab` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_teams`
--

LOCK TABLES `user_teams` WRITE;
/*!40000 ALTER TABLE `user_teams` DISABLE KEYS */;
INSERT INTO `user_teams` (`user_id`, `teams_id`) VALUES (1,1);
/*!40000 ALTER TABLE `user_teams` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-06  9:18:46
