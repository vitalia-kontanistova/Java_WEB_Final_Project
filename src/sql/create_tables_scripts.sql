-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_info`
--

DROP TABLE IF EXISTS `access_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_info` (
  `ai_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ai_email` varchar(50) NOT NULL,
  `ai_password` varchar(150) NOT NULL,
  `u_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ai_id`,`u_id`),
  UNIQUE KEY `ai_id_UNIQUE` (`ai_id`),
  UNIQUE KEY `ai_e_mail_UNIQUE` (`ai_email`),
  KEY `fk_access_info_users1_idx` (`u_id`),
  CONSTRAINT `fk_access_info_users1` FOREIGN KEY (`u_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `a_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `a_surname` varchar(150) NOT NULL,
  `a_name` varchar(150) NOT NULL,
  `a_patronymic` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`a_id`),
  UNIQUE KEY `a_id_UNIQUE` (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=636 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `b_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `b_title` varchar(300) NOT NULL,
  `b_year` year(4) NOT NULL,
  `b_lang` enum('EN','EO','ES','FR','LV','RU','DE','BE','PL') NOT NULL,
  `b_pages` int(11) DEFAULT NULL,
  `b_quantity` int(11) NOT NULL,
  `b_available_books` int(11) NOT NULL,
  `b_annotation` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`b_id`),
  UNIQUE KEY `b_id_UNIQUE` (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=652 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `books_has_authors`
--

DROP TABLE IF EXISTS `books_has_authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books_has_authors` (
  `b_id` bigint(20) NOT NULL,
  `a_id` bigint(20) NOT NULL,
  PRIMARY KEY (`b_id`,`a_id`),
  KEY `fk_books_has_authors_books1` (`b_id`),
  KEY `fk_books_has_authors_authors1_idx` (`a_id`),
  CONSTRAINT `fk_books_has_authors_authors1` FOREIGN KEY (`a_id`) REFERENCES `authors` (`a_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_books_has_authors_books1` FOREIGN KEY (`b_id`) REFERENCES `books` (`b_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `card_notes`
--

DROP TABLE IF EXISTS `card_notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card_notes` (
  `cn_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cn_initial_date` date NOT NULL,
  `cn_final_date` date NOT NULL,
  `cn_destination` enum('READING ROOM','CARD','ORDER') NOT NULL,
  `cn_is_active` enum('Y','N') NOT NULL,
  `b_id` bigint(20) NOT NULL,
  `u_id` bigint(20) NOT NULL,
  PRIMARY KEY (`cn_id`,`b_id`,`u_id`),
  UNIQUE KEY `cn_id_UNIQUE` (`cn_id`),
  KEY `fk_card_notes_books1_idx` (`b_id`),
  KEY `fk_card_notes_user_idx_idx` (`u_id`),
  CONSTRAINT `fk_card_notes_books1` FOREIGN KEY (`b_id`) REFERENCES `books` (`b_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_card_notes_user_idx` FOREIGN KEY (`u_id`) REFERENCES `users` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1508 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `u_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_surname` varchar(45) NOT NULL,
  `u_name` varchar(45) NOT NULL,
  `u_patronymic` varchar(45) NOT NULL,
  `u_birthday` date NOT NULL,
  `u_phone` varchar(45) NOT NULL,
  `u_role` enum('READER','LIBRARIAN','ADMIN') NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_id_UNIQUE` (`u_id`) /*!80000 INVISIBLE */,
  UNIQUE KEY `u_phone_UNIQUE` (`u_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'library'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-24 17:34:17
