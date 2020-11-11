CREATE DATABASE  IF NOT EXISTS `parking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `parking`;
-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: parking
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area_parkir`
--

DROP TABLE IF EXISTS `area_parkir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `area_parkir` (
  `id_area` int NOT NULL AUTO_INCREMENT,
  `nama_area` varchar(45) NOT NULL,
  PRIMARY KEY (`id_area`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_parkir`
--

LOCK TABLES `area_parkir` WRITE;
/*!40000 ALTER TABLE `area_parkir` DISABLE KEYS */;
INSERT INTO `area_parkir` VALUES (1,'Area 05'),(4,'Area 04'),(5,'Area 051'),(6,'Area 06'),(7,'Area 07 - updated');
/*!40000 ALTER TABLE `area_parkir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `garage`
--

DROP TABLE IF EXISTS `garage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `garage` (
  `id_garage` int NOT NULL AUTO_INCREMENT,
  `nama_garage` varchar(45) NOT NULL,
  `hari_operasional` varchar(45) NOT NULL,
  `waktu_buka` time DEFAULT NULL,
  `waktu_tutup` time DEFAULT NULL,
  `id_area` int NOT NULL,
  `tarif_motor` decimal(19,2) NOT NULL,
  `tarif_mobil` decimal(19,2) NOT NULL,
  `isOccupied` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_garage`),
  KEY `id_area_garage_area_idx` (`id_area`),
  CONSTRAINT `id_area_area_garage` FOREIGN KEY (`id_area`) REFERENCES `area_parkir` (`id_area`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `garage`
--

LOCK TABLES `garage` WRITE;
/*!40000 ALTER TABLE `garage` DISABLE KEYS */;
INSERT INTO `garage` VALUES (9,'Garasi di Area 04','Rabu','07:00:00','21:00:00',4,2000.00,0.00,0),(10,'Garasi 051','Kamis','06:00:00','20:00:00',5,2000.00,5000.00,0);
/*!40000 ALTER TABLE `garage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kendaraan`
--

DROP TABLE IF EXISTS `kendaraan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `kendaraan` (
  `plat_no` varchar(45) NOT NULL,
  `tipe_kendaraan` varchar(45) NOT NULL,
  `id_user` int NOT NULL,
  `is_parked` int DEFAULT '0',
  PRIMARY KEY (`plat_no`),
  KEY `user_kendaraan_idx` (`id_user`),
  CONSTRAINT `user_kendaraan` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kendaraan`
--

LOCK TABLES `kendaraan` WRITE;
/*!40000 ALTER TABLE `kendaraan` DISABLE KEYS */;
/*!40000 ALTER TABLE `kendaraan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarif`
--

DROP TABLE IF EXISTS `tarif`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tarif` (
  `id_tarif` int NOT NULL AUTO_INCREMENT,
  `tarif_motor` decimal(19,2) NOT NULL,
  `tarif_mobil` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id_tarif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarif`
--

LOCK TABLES `tarif` WRITE;
/*!40000 ALTER TABLE `tarif` DISABLE KEYS */;
/*!40000 ALTER TABLE `tarif` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaksi_parkir`
--

DROP TABLE IF EXISTS `transaksi_parkir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transaksi_parkir` (
  `id_transaksi` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_area` int NOT NULL,
  `id_garage` int NOT NULL,
  `waktu_masuk` datetime NOT NULL,
  `waktu_keluar` datetime NOT NULL,
  `durasi` time NOT NULL,
  `total_harga` int NOT NULL,
  `plat_no` varchar(45) NOT NULL,
  PRIMARY KEY (`id_transaksi`),
  UNIQUE KEY `id_transaksi_UNIQUE` (`id_transaksi`),
  KEY `id_garage` (`id_garage`),
  KEY `id_parkir` (`id_area`),
  KEY `id_user` (`id_user`,`id_area`,`plat_no`,`id_garage`),
  KEY `fk_kendaraan_transaksi_idx` (`plat_no`),
  CONSTRAINT `fk_area_transaksi` FOREIGN KEY (`id_area`) REFERENCES `area_parkir` (`id_area`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_garage_transaksi` FOREIGN KEY (`id_garage`) REFERENCES `garage` (`id_garage`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_kendaraan_transaksi` FOREIGN KEY (`plat_no`) REFERENCES `kendaraan` (`plat_no`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user_transaksi` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_parkir`
--

LOCK TABLES `transaksi_parkir` WRITE;
/*!40000 ALTER TABLE `transaksi_parkir` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaksi_parkir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(45) DEFAULT NULL,
  `alamat` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `is_admin` int DEFAULT NULL,
  `subscription` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_user_UNIQUE` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Yasya','Bumi Ciluar','yasya@gmail.com','f4dbca808e102c305cb92f192d16ef4d',0,'easy'),(2,'yasya','bumi','yasya@email.com','876dfefef3ef6f4548f48bbddd856cef',1,''),(3,'Yasya','bumi','yasya@ggmail.com','876dfefef3ef6f4548f48bbddd856cef',0,'easy'),(4,'yasya','yasya','y@email.com','876dfefef3ef6f4548f48bbddd856cef',1,''),(5,'yasyy','bci','a@m.com','d41d8cd98f00b204e9800998ecf8427e',0,'easy'),(6,'Ridhal','bumici','r@email.com','f970e2767d0cfe75876ea857f92e319b',0,'easy'),(7,'Ridhal','Bci','a@mail.com','876dfefef3ef6f4548f48bbddd856cef',0,'plus'),(8,'customer','buci','cust@email.com','876dfefef3ef6f4548f48bbddd856cef',0,'easy'),(9,'asdsa','ASda','cus@email.com','876dfefef3ef6f4548f48bbddd856cef',0,'easy'),(10,'yu','ya','yu@m.com','876dfefef3ef6f4548f48bbddd856cef',0,'easy'),(11,'Ya','bumi','yu@me.com','876dfefef3ef6f4548f48bbddd856cef',0,'easy'),(12,'yasy','12','b@e.com','876dfefef3ef6f4548f48bbddd856cef',1,''),(13,'y','123','y@po.com','b8b169fd928a388f713dcd213bec12f8',0,'easy');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-09  8:57:43
