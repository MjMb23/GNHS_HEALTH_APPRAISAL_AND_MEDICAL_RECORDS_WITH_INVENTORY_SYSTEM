CREATE DATABASE  IF NOT EXISTS `gnhs_system_db` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gnhs_system_db`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: gnhs_system_db
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `batch`
--

DROP TABLE IF EXISTS `batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `unit` varchar(45) NOT NULL,
  `entry_date` date NOT NULL,
  `expiration_date` date NOT NULL,
  `num_of_dispensed` int NOT NULL,
  `remaining` int NOT NULL,
  `remarks` varchar(255) NOT NULL,
  `status` varchar(45) NOT NULL DEFAULT 'good',
  `items_item_id` int NOT NULL,
  PRIMARY KEY (`batch_id`,`items_item_id`),
  KEY `fk_batch_items_idx` (`items_item_id`),
  CONSTRAINT `fk_batch_items` FOREIGN KEY (`items_item_id`) REFERENCES `items` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch`
--

LOCK TABLES `batch` WRITE;
/*!40000 ALTER TABLE `batch` DISABLE KEYS */;
INSERT INTO `batch` VALUES (55,100,'pieces','2021-06-07','2023-06-30',12,88,'remarks here','good',35);
/*!40000 ALTER TABLE `batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) NOT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disposal_history`
--

DROP TABLE IF EXISTS `disposal_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disposal_history` (
  `disposal_history_id` int NOT NULL AUTO_INCREMENT,
  `disposal_date` date NOT NULL,
  `batch_id` int NOT NULL,
  PRIMARY KEY (`disposal_history_id`,`batch_id`),
  KEY `fk_disposal_history_batch1_idx` (`batch_id`),
  CONSTRAINT `fk_disposal_history_batch1` FOREIGN KEY (`batch_id`) REFERENCES `batch` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disposal_history`
--

LOCK TABLES `disposal_history` WRITE;
/*!40000 ALTER TABLE `disposal_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `disposal_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `item_type` varchar(100) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (35,'Paracetamol','Medicine');
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_history`
--

DROP TABLE IF EXISTS `medical_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_history` (
  `medical_history_id` int NOT NULL AUTO_INCREMENT,
  `history_description` varchar(255) NOT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`medical_history_id`,`patient_id`),
  KEY `fk_medical_history_patients1_idx` (`patient_id`),
  CONSTRAINT `fk_medical_history_patients1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_history`
--

LOCK TABLES `medical_history` WRITE;
/*!40000 ALTER TABLE `medical_history` DISABLE KEYS */;
INSERT INTO `medical_history` VALUES (32,'Hypertension',17),(33,'Diabetes Mellitus',17),(55,'Hypertension',26),(56,'Hypertension',28),(57,'Pet Allergy',32),(58,'Hypertension',32),(59,'Diabetes Mellitus',32),(60,'Brochial Asthma',32),(61,'Drug Allergy',38),(62,'Food Allergy',38),(63,'Pet Allergy',38),(64,'Hypertension',38),(65,'Diabetes Mellitus',38),(66,'Brochial Asthma',38),(67,'Mold Allergy',39),(68,'Pollen Allergy',39),(69,'Brochial Asthma',39),(70,'Pollen Allergy',40),(71,'Hypertension',42),(72,'Food Allergy',43),(73,'Hypertension',43),(74,'Drug Allergy',44),(75,'Food Allergy',44),(76,'Insect Allergy',44),(77,'Latex Allergy',44),(78,'Cancer',44),(79,'Food Allergy',47),(80,'Hyertension',50),(81,'Hypertension',51),(82,'Diabetes Mellitus',51),(83,'Hypertension',59),(84,'Hypertension',60),(85,'Hypertension',61),(86,'Hypertension',62),(87,'hypertension',63),(88,'Brochial Asthma',64),(89,'Food Allergy',64),(90,'Immuno Deficiency',67),(91,'Drug Allergy',69),(92,'Brochial Asthma',69),(93,'Hypertension',72),(94,'Immuno Deficiency',73),(95,'Latex Allergy',74),(96,'Mold Allergy',74),(97,'Pollen Allergy',74),(98,'Brochial Allergy',74),(99,'Mold Allergy',75),(100,'Pet Allergy',75),(101,'Pollen Allergy',75),(102,'Hypertension',75),(103,'Brochial Asthma',75),(104,'Immuno Deficiency',75),(105,'Food Allergy',79),(106,'Hypertension',79),(107,'Diabetes Mellitus',79),(108,'Mold Allergy',82),(109,'Mold Allergy',84),(110,'Hypertension',85),(111,'Food Allergy',87),(112,'Food Allergy',90),(113,'Pollen Allergy',92),(114,'Food Allergy',96),(115,'Hypertension',96),(116,'Heart Disease',96),(117,'Brochial Asthma',96),(118,'Food Allergy',97),(119,'Pet Allergy',97),(120,'Mold Allergy',97),(121,'Brochial Asthma',98),(122,'Brochial Asthma',102),(123,'Hypertension',103),(124,'Brochial Asthma',103),(125,'Food Allergy',104),(126,'Pet Allergy',104),(127,'Pollen Allergy',104),(128,'Hypertension',104),(129,'Heart Disease',104),(130,'Diabetes Mellitus',104),(132,'Drug Allergy',107),(133,'Heart Disease',107),(134,'Diabetes Mellitus',107),(135,'Hypertension',108),(136,'Hypertension',109),(137,'Hypertension',110),(138,'Food Allergy',111),(139,'Hypertension',112),(140,'Insect Allergy',113),(141,'Insect Allergy',114),(142,'Pet Allergy',114),(143,'Pollen Allergy',114),(144,'Mold Allergy',115),(145,'Pet Allergy',115),(146,'Pollen Allergy',115),(147,'Hypertension',115),(148,'Food Allergy',116),(149,'Immuno Deficiency',116),(150,'Drug Allergy',117),(151,'Food Allergy',117),(152,'Insect Allergy',117),(153,'Mold Allergy',117),(154,'Hypertension',117),(155,'Diabetes Mellitus',117),(156,'Brochial Asthma',117),(157,'Immuno Deficiency',117),(158,'Kidney Disease',119),(159,'Brochial Asthma',122),(160,'Hypertension',123),(161,'Diabetes Mellitus',123),(162,'Brochial Asthma',124),(163,'Drug Allergy',126),(164,'Food Allergy',126),(165,'Insect Allergy',126),(166,'Latex Allergy',126),(167,'Pet Allergy',126),(168,'Pollen Allergy',126),(169,'Hypertension',126),(170,'Kidney Disease',126),(171,'Hypertension',127),(172,'Kidney Disease',128),(173,'Kidney Disease',131),(174,'Cancer',131),(175,'Food Allergy',133),(176,'Food Allergy',134),(177,'Insect Allergy',134),(178,'Food Allergy',135),(179,'Hypertension',135),(180,'Kidney Disease',135),(181,'Drug Allergy',139),(182,'Hypertension',139);
/*!40000 ALTER TABLE `medical_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_or_equipment_given`
--

DROP TABLE IF EXISTS `medicine_or_equipment_given`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine_or_equipment_given` (
  `id` int NOT NULL AUTO_INCREMENT,
  `items_item_id` int NOT NULL,
  `quantity` int NOT NULL,
  `record_record_id` int NOT NULL,
  PRIMARY KEY (`id`,`items_item_id`,`record_record_id`),
  KEY `fk_dispsense_history_record1_idx` (`record_record_id`),
  KEY `fk_items_item_id_idx` (`items_item_id`),
  CONSTRAINT `fk_dispsense_history_record1` FOREIGN KEY (`record_record_id`) REFERENCES `record` (`record_id`),
  CONSTRAINT `fk_items_item_id` FOREIGN KEY (`items_item_id`) REFERENCES `items` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_or_equipment_given`
--

LOCK TABLES `medicine_or_equipment_given` WRITE;
/*!40000 ALTER TABLE `medicine_or_equipment_given` DISABLE KEYS */;
INSERT INTO `medicine_or_equipment_given` VALUES (28,35,12,48);
/*!40000 ALTER TABLE `medicine_or_equipment_given` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) NOT NULL,
  `sex` varchar(20) NOT NULL,
  `birthdate` date NOT NULL,
  `age` int DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `bmi` double DEFAULT NULL,
  `current_condition` varchar(255) DEFAULT NULL,
  `status` varchar(45) DEFAULT 'active',
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (14,'Rosally','Brosoto','Barana','Female','1974-02-15',47,0,0,0,'','active'),(15,'Princess Ruhama','Bucasas','Pozon','Female','1984-12-06',36,0,0,0,'','active'),(16,'Ma Joy Rosette','Cabubas','Dael','Female','1994-04-27',27,0,0,0,'','active'),(17,'Ellen ','Casaclang','Abad','Female','1965-08-25',55,0,0,0,'','active'),(18,'Iris','De Leon','Sitchon','Female','1983-06-07',37,0,0,0,'','active'),(19,'Ma. Lourdes','Dela Cruz','Mallari','Female','1978-05-16',43,0,0,0,'','active'),(20,'Edwin','Guanko','Magat','Male','1991-06-11',29,0,0,0,'','active'),(25,'Juan','Dela Cruz','Delos Santos','Male','2000-12-25',20,60,165,22.04,'stable','active'),(26,'Roxanne','Requillas','Bas','Female','1988-10-04',32,0,0,0,'','active'),(27,'Ricson Paul','Rotairo','Dueñas','Male','1994-06-20',26,0,0,0,'','active'),(28,'Angelita','Soriano','Ronquillo','Female','1978-08-05',42,0,0,0,'','active'),(29,'Melanie','Trinidad','Tagudin','Female','1983-03-13',38,0,0,0,'','active'),(30,'Teresita','Waje','Flores','Female','1963-10-15',57,0,0,0,'','active'),(31,'Rosemarie','Walker','Talion','Female','1961-10-10',59,0,0,0,'','active'),(32,'Carmencita','Labrador','Sangalang','Female','1960-07-18',60,0,0,0,'','active'),(33,'Shiella Mae','Abellon','Yray','Female','1996-06-26',24,0,0,0,'','active'),(34,'Ronel','Santiago','Cabanlit','Male','1991-03-12',30,0,0,0,'','active'),(35,'Sally','Queddeng','Bonete','Female','1963-06-07',58,0,0,0,'','active'),(36,'Glandy','Vinca','Ladringan','Female','1986-09-14',34,0,0,0,'','active'),(37,'Angelica','Dela Cruz','De Leon','Female','1992-01-28',29,0,0,0,'','active'),(38,'Ryan Jay','Napalan','Del Rosario','Male','1982-09-25',38,0,0,0,'','active'),(39,'Ailene','Gonzales','Lira','Female','1980-05-27',41,0,0,0,'','active'),(40,'Rizza','Castillo','Panuga','Female','1972-02-16',49,0,0,0,'','active'),(41,'Gina','Bustamante','Gracia','Female','1983-07-30',37,0,0,0,'','active'),(42,'Cynthia','Mora','Josafat','Female','1960-05-15',61,0,0,0,'','active'),(43,'Mary Joy','Acebedo','Crisolo','Female','1993-03-24',28,0,0,0,'','active'),(44,'Princess Kathlene','Conception','Migano','Female','1985-12-19',35,0,0,0,'','active'),(45,'Jason','Alde','Elad','Male','1985-02-14',36,0,0,0,'','active'),(46,'Julie','Soriano','Empuerto','Female','1981-05-05',40,0,0,0,'','removed'),(47,'Julie','Soriano','Empuerto','Female','1981-05-05',40,0,0,0,'','active'),(48,'Rolly','Bitoon','Dumali','Male','1963-09-25',57,0,0,0,'','active'),(49,'Rommel','Macayan','Rodriguez','Male','1986-04-23',35,0,0,0,'','active'),(50,'Cezar','Villanueva','Fortin','Male','1967-04-16',54,0,0,0,'','active'),(51,'Ruel','Beltran','Sabino','Male','1969-02-17',52,0,0,0,'','active'),(52,'Reynante','Basa','Dalindin','Male','1980-12-18',40,0,0,0,'','active'),(53,'Aireen','Basa','Iglesia','Female','1985-03-20',36,0,0,0,'','active'),(54,'Glenn Albert','Ebido','Elampado','Male','1986-12-04',34,0,0,0,'','active'),(55,'Reynalyn','Marticio','Ebal','Female','1980-12-23',40,0,0,0,'','active'),(56,'Leif Richard','Bautista','Camara','Male','1982-12-28',38,0,0,0,'','active'),(57,'Estela','Narcisa','Gallardo','Female','1968-11-01',52,0,0,0,'','active'),(58,'Rodelyn','Atiz','Puno','Female','1996-10-20',24,0,0,0,'','active'),(59,'Roxanne Mae','Paunan','Bobadilla','Female','1986-10-26',34,0,0,0,'','active'),(60,'Alejandria','Syfu','Tamondong','Female','1965-04-21',56,0,0,0,'','active'),(61,'Milagros','Guiaya','Trimor','Female','1959-11-11',61,0,0,0,'','active'),(62,'Maxima','Munio','Maxilom','Female','1966-07-12',54,0,0,0,'','active'),(63,'Alma','Ganancial','Sitchon','Female','1976-11-08',44,0,0,0,'','active'),(64,'Marcelle','Millena','Rivera','Female','1980-01-12',41,0,0,0,'','active'),(65,'Julieta','Andrade','Domingo','Female','1970-01-10',51,0,0,0,'','active'),(66,'Rowena','Honra','Santos','Female','1964-12-24',56,0,0,0,'','active'),(67,'Roilan Joshua','Arañas','Levantino','Male','1998-10-04',22,0,0,0,'','active'),(68,'Amihan','Eglesias','Icamina','Female','1983-03-26',38,0,0,0,'','active'),(69,'Rachelle','Bagsic','Romero','Female','1983-10-10',37,0,0,0,'','active'),(70,'Jean','Pasag','Garcia','Female','1978-02-19',43,0,0,0,'','active'),(71,'Efren','Hertez','Sanao','Male','1963-01-14',58,0,0,0,'','active'),(72,'Ceasar Ryan','Ulanday','Lomeda','Male','1988-03-18',33,0,0,0,'','active'),(73,'Rodeliza','Villarosa','Puno','Female','1978-11-26',42,0,0,0,'','active'),(74,'Rovelyn','Nacpil','Diwa','Female','1977-06-14',43,0,0,0,'','active'),(75,'Glenda','Tolentino','Dela Cruz','Female','1974-01-14',47,0,0,0,'','active'),(76,'Emily','Costales','Valdez','Female','1978-07-15',42,0,0,0,'','active'),(77,'Michael','Tuyay','Paule','Male','1990-08-14',30,0,0,0,'','active'),(78,'Eduardo','Velasco','Brier','Male','1990-10-01',30,0,0,0,'','active'),(79,'Michael','Ting','Padua','Male','1971-09-08',49,0,0,0,'','active'),(80,'Edward','Martos','None','Male','1995-10-09',25,0,0,0,'','active'),(81,'Ma. Victoria','Mapilisan','Balbio','Female','1971-04-13',50,0,0,0,'','active'),(82,'Rose Arrianne','Cosina','Oli','Female','1991-10-09',29,0,0,0,'','active'),(83,'Ronald','Pasag','Muyano','Male','1974-01-03',47,0,0,0,'','active'),(84,'Gloria','Conception','De Guzman','Female','1969-05-06',52,0,0,0,'','active'),(85,'Jose Lemuel','Delos Trinos','Soliven','Male','1985-07-08',35,0,0,0,'','active'),(86,'Ferdie','Labanda','Bernal','Male','1993-09-19',27,0,0,0,'','active'),(87,'Zyra Marie','Francia','Posados','Female','1997-04-19',24,0,0,0,'','active'),(88,'Christian Carl','Denotua','Dolorzo','Male','1993-05-13',28,0,0,0,'','active'),(89,'Christian Carl','Denotua','Dolorozo','Male','1993-05-13',28,0,0,0,'','active'),(90,'Arnold','Alvaro','Dolojan','Male','1984-01-10',37,0,0,0,'','active'),(91,'Mara Jean','Jimenez','Gutierrez','Female','1997-01-24',24,0,0,0,'','active'),(92,'Rizzel Ann','Laparan','Esteban','Female','1996-05-16',25,0,0,0,'','active'),(93,'Many Jaycee','Flores','Fabian','Male','1989-11-03',31,0,0,0,'','active'),(94,'Ticia Divine','Belnas','Oñate','Female','1996-10-16',24,0,0,0,'','active'),(95,'Janette','Pielago','Oandasan','Female','1974-01-19',47,0,0,0,'','active'),(96,'Lourdes','Laparan','Esteban','Female','1966-05-15',55,0,0,0,'','active'),(97,'Maria Christina','Bernal','Fernandez','Female','1985-12-25',35,0,0,0,'','active'),(98,'Angelica','Miguel','Ricardo','Female','1991-01-22',30,0,0,0,'','active'),(99,'Mark Joel','Morales','Quinto','Male','1994-11-26',26,0,0,0,'','active'),(100,'Abegail','Reyes','Cerico','Female','1990-09-11',30,0,0,0,'','active'),(101,'Amelia','Gallardo','Jadulco','Female','1968-08-14',52,0,0,0,'','active'),(102,'Cristalyn','Guttierez','Cruz','Female','1994-09-30',26,0,0,0,'','active'),(103,'Teresita','Poli','G','Female','1964-11-27',56,0,0,0,'','active'),(104,'Jennifer ','Ching','Pacheco','Female','1968-05-16',53,0,0,0,'','active'),(105,'Jocelyn','Garcia','Dagang','Female','1965-07-26',55,0,0,0,'','active'),(106,'Chello','Ann','Pelaez','Female','1979-04-06',42,0,0,0,'','active'),(107,'Elizabeth','Soriano','Tagos','Female','1960-03-03',61,0,0,0,'','active'),(108,'Myrna','Montejo','Falaminiano','Female','1961-12-27',59,0,0,0,'','active'),(109,'Lilibeth','Jimenez','Santos','Female','1961-08-21',59,0,0,0,'','active'),(110,'Jingle','Silvido','De Guzman','Female','1972-08-04',48,0,0,0,'','active'),(111,'Lino','Eduarte','Ignacio','Male','1985-02-13',36,0,0,0,'','active'),(112,'Willy','Antigo','Rance','Male','1979-03-04',42,0,0,0,'','active'),(113,'Villarose','Difuntorum','Lago','Female','1986-01-15',35,0,0,0,'','active'),(114,'Mariedel','Repuesto','Merza','Female','1980-11-08',40,0,0,0,'','active'),(115,'Maxima','Dinglas','Umali','Female','1962-03-26',59,0,0,0,'','active'),(116,'Annalyn','Andrade','Verzosa','Female','1976-02-16',45,0,0,0,'','active'),(117,'Elizabeth','Mendoza','Macaspac','Female','1969-05-19',52,0,0,0,'','active'),(118,'Christina','Factor','Mayo','Female','1987-12-09',33,0,0,0,'','active'),(119,'Jolina Poala','Bautista','Beltran','Female','1995-07-09',25,0,0,0,'','active'),(120,'Cecilio','Montareal','Santiago','Male','1983-12-22',37,0,0,0,'','active'),(121,'Simeon','Alcantara','Biron','Male','1973-11-18',47,0,0,0,'','active'),(122,'Jo Ann','Ancheta','Mayo','Female','1982-11-05',38,0,0,0,'','active'),(123,'Marissa','Aranda','Caimol','Female','1970-02-16',51,0,0,0,'','active'),(124,'Mary Grace','Dela Cruz','Labitan','Female','1996-09-14',24,0,0,0,'','active'),(125,'Nelson','Elemos','Flores','Male','1972-11-24',48,0,0,0,'','active'),(126,'Esperidion','Ordonio','Ferido','Male','1962-01-26',59,0,0,0,'','active'),(127,'Nova','Sotelo','Candelosas','Female','1971-04-11',50,0,0,0,'','active'),(128,'Ruel','Eclarinal','Alog','Male','1971-01-13',50,0,0,0,'','active'),(129,'Danilo','Banaag','Padlan','Male','1950-02-16',71,0,0,0,'','active'),(130,'Randy','Altizo','Barles','Male','1980-04-14',41,0,0,0,'','active'),(131,'Vivian','Alcantara','Galarpe','Female','1973-08-04',47,0,0,0,'','active'),(132,'Jimerce','Aparel','Lapuri','Male','1983-06-03',38,0,0,0,'','active'),(133,'Yvette Marie','Muyco','Rosas','Female','1977-09-17',43,0,0,0,'','active'),(134,'Joyce','Ong','Galang','Female','1981-12-23',39,0,0,0,'','active'),(135,'Riogel','Santiago','Lacson','Male','1989-09-20',31,0,0,0,'','active'),(136,'Naeimah','Balanon','Bawarie','Male','1990-10-14',30,0,0,0,'','active'),(137,'Floyd','Aquino','Gomez','Male','1988-08-10',32,0,0,0,'','active'),(138,'Alester','Oca','De Guzman','Male','1983-04-11',38,0,0,0,'','active'),(139,'Resurrecion','Miranda','Recaido','Female','1967-04-07',54,0,0,0,'','active'),(140,'Chastine Joy','Anfone','Pascual','Female','1980-09-27',40,0,0,0,'','active'),(141,'Elmer','Dava','Adamos','Male','1979-08-21',41,0,0,0,'','active'),(142,'Kiel','Mago','None','Male','2021-06-15',0,0,0,0,'','active'),(143,'Mariane','Sapuay','Sanchez','Female','2021-06-15',0,0,0,0,'','active');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `bp` varchar(45) DEFAULT NULL,
  `temp` float DEFAULT NULL,
  `rr` int DEFAULT NULL,
  `pr` int DEFAULT NULL,
  `o2` int DEFAULT NULL,
  `chief_complaint` varchar(45) NOT NULL,
  `management_or_treatment` varchar(45) DEFAULT NULL,
  `date_taken` date NOT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`record_id`,`patient_id`),
  KEY `fk_record_patients1_idx` (`patient_id`),
  CONSTRAINT `fk_record_patients1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (48,'120/80',37,90,90,20,'complaint','complaint','2021-06-08',25);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers` (
  `teacher_id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaccination_or_immunization_history`
--

DROP TABLE IF EXISTS `vaccination_or_immunization_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaccination_or_immunization_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vaccination_description` varchar(255) NOT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`id`,`patient_id`),
  KEY `fk_vaccination_or_immunization_history_patients1_idx` (`patient_id`),
  CONSTRAINT `fk_vaccination_or_immunization_history_patients1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaccination_or_immunization_history`
--

LOCK TABLES `vaccination_or_immunization_history` WRITE;
/*!40000 ALTER TABLE `vaccination_or_immunization_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `vaccination_or_immunization_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gnhs_system_db'
--

--
-- Dumping routines for database 'gnhs_system_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-15 11:15:29
