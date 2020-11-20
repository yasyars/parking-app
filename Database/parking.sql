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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_parkir`
--

LOCK TABLES `area_parkir` WRITE;
/*!40000 ALTER TABLE `area_parkir` DISABLE KEYS */;
INSERT INTO `area_parkir` VALUES (1,'Area 1'),(7,'Area 07'),(8,'INI HAPUS');
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `garage`
--

LOCK TABLES `garage` WRITE;
/*!40000 ALTER TABLE `garage` DISABLE KEYS */;
INSERT INTO `garage` VALUES (12,'Garage Baru','Senin','07:00:00','21:00:00',1,2000.00,5000.00,0),(15,'Garage04','Senin','07:00:00','21:00:00',1,3000.00,5000.00,0);
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
INSERT INTO `kendaraan` VALUES ('AB 123 AC','Motor',21,0),('AB 1233 C','Mobil',21,0),('DC 123 AB','Motor',22,0),('DC 1234 A','Mobil',22,1),('FF 123 AA','Motor',25,0);
/*!40000 ALTER TABLE `kendaraan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkir`
--

DROP TABLE IF EXISTS `parkir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `parkir` (
  `id_parkir` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_area` int NOT NULL,
  `id_garage` int NOT NULL,
  `plat_no` varchar(45) NOT NULL,
  `waktu_masuk` datetime NOT NULL,
  PRIMARY KEY (`id_parkir`),
  UNIQUE KEY `id_transaksi_UNIQUE` (`id_parkir`),
  UNIQUE KEY `id_user_UNIQUE` (`id_user`),
  KEY `id_garage` (`id_garage`),
  KEY `id_parkir` (`id_area`),
  KEY `id_user` (`id_user`,`id_area`,`plat_no`,`id_garage`),
  KEY `fk_kendaraan_parkir_idx` (`plat_no`),
  CONSTRAINT `fk_area_parkir` FOREIGN KEY (`id_area`) REFERENCES `area_parkir` (`id_area`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_garage_parkir` FOREIGN KEY (`id_garage`) REFERENCES `garage` (`id_garage`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_kendaraan_parkir` FOREIGN KEY (`plat_no`) REFERENCES `kendaraan` (`plat_no`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user_parkir` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkir`
--

LOCK TABLES `parkir` WRITE;
/*!40000 ALTER TABLE `parkir` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkir` ENABLE KEYS */;
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
  `durasi` int NOT NULL,
  `total_harga` decimal(19,2) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_parkir`
--

LOCK TABLES `transaksi_parkir` WRITE;
/*!40000 ALTER TABLE `transaksi_parkir` DISABLE KEYS */;
INSERT INTO `transaksi_parkir` VALUES (11,22,1,12,'2020-11-16 10:58:00','2020-11-16 11:00:00',1,14000.00,'DC 123 AB'),(12,22,1,12,'2020-11-16 11:10:00','2020-11-16 11:15:00',1,2000.00,'DC 123 AB'),(13,22,1,12,'2020-11-16 11:30:00','2020-11-16 12:00:00',1,2000.00,'DC 123 AB'),(14,22,1,12,'2020-11-16 13:00:00','2020-11-16 15:00:00',2,4000.00,'DC 123 AB'),(15,22,1,12,'2020-12-07 15:00:00','2020-12-07 15:10:00',1,2000.00,'DC 123 AB'),(16,22,1,12,'2021-01-04 15:10:00','2021-01-04 15:20:00',1,2000.00,'DC 123 AB'),(17,22,1,12,'2021-02-01 07:00:00','2021-02-01 07:20:00',1,2000.00,'DC 123 AB'),(18,22,1,12,'2021-03-01 07:20:00','2021-03-01 08:00:00',1,14000.00,'DC 123 AB'),(19,22,1,12,'2021-03-08 08:00:00','2021-03-08 08:02:00',1,2000.00,'DC 123 AB'),(20,21,1,12,'2020-11-16 08:05:00','2020-11-16 10:00:00',2,12000.00,'AB 1233 C'),(21,21,1,12,'2020-11-16 10:11:00','2020-11-16 11:00:00',1,7000.00,'AB 1233 C'),(22,25,1,12,'2020-11-16 12:45:00','2020-11-16 13:30:00',1,4000.00,'FF 123 AA'),(23,22,1,12,'2021-03-08 08:20:00','2021-03-08 08:25:00',1,2000.00,'DC 123 AB'),(24,22,1,15,'2021-03-08 08:26:00','2021-03-08 08:27:00',1,3000.00,'DC 123 AB'),(25,21,1,12,'2020-11-16 11:30:00','2020-11-16 11:35:00',1,7000.00,'AB 1233 C'),(26,21,1,15,'2020-11-16 11:36:00','2020-11-16 11:37:00',1,7000.00,'AB 1233 C'),(27,21,1,15,'2020-11-16 11:38:00','2020-11-16 11:39:00',1,5000.00,'AB 123 AC');
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (20,'yasy','bumi','yuser@a.com','876dfefef3ef6f4548f48bbddd856cef',0,NULL),(21,'Yasya','bumi','yuser@p.com','876dfefef3ef6f4548f48bbddd856cef',0,'Easy'),(22,'Yoyo Hakusho','yuyu','yuser@m.com','876dfefef3ef6f4548f48bbddd856cef',0,'Plus'),(23,'u','u','yadmin@m.com','876dfefef3ef6f4548f48bbddd856cef',1,NULL),(24,'Yasya','Bmi','123@m.com','876dfefef3ef6f4548f48bbddd856cef',1,NULL),(25,'Yasya','yasua','yasya@mail.com','876dfefef3ef6f4548f48bbddd856cef',0,'Easy'),(26,'yasya','yuyu','yasya@u.com','876dfefef3ef6f4548f48bbddd856cef',0,'Plus');
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

-- Dump completed on 2020-11-20 11:18:12
