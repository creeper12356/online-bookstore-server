-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: bookstore
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `sales` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (144,'Harper Lee','http://localhost:10339/file/upload_46DOZR_to_kill_a_mocking_bird-9899026664180243755.jpg','A gripping tale of racial injustice in the Deep South.','9780061120084',2000,0,40,'To Kill a Mockingbird'),(145,'George Orwell','http://localhost:10339/file/upload_ZLH4jq_1984-5696412432835964195.jpg','A dystopian novel on totalitarianism and surveillance.','9780451524935',1500,1,59,'1984'),(146,'Jane Austen','http://localhost:10339/file/upload_5Bs8JO_pride_and_prejudice-4986229418219809771.jpg','A classic romance between Elizabeth Bennet and Mr. Darcy.','9780141439518',1700,2,68,'Pride and Prejudice'),(147,'F. Scott Fitzgerald','http://localhost:10339/file/upload_LVAqNm_great_gatsby-165308530722256785.jpg','A critique of the American Dream set in the Jazz Age.','9780743273565',1600,0,44,'The Great Gatsby'),(148,'Herman Melville','http://localhost:10339/file/upload_ZzmEXM_moby_dick-17211194448714559772.jpg','A seafaring tale of revenge and obsession.','9781503280786',2100,1,34,'Moby-Dick'),(150,'Homer','http://localhost:10339/file/upload_dVZL4V_odyss-3218693146159528813.png','The adventures of Odysseus as he journeys home from Troy.','9780140268867',1300,0,55,'The Odyssey'),(152,'Fyodor Dostoevsky','http://localhost:10339/file/upload_b8t37L_brother-9306556883667531938.jpeg','A philosophical novel that probes moral questions.','9780374528379',2200,0,40,'The Brothers Karamazov'),(153,'Aldous Huxley','http://localhost:10339/file/upload_wRggDD_brave-1220819474355602540.jpg','A dystopian vision of a technologically controlled future.','9780060850524',1400,0,65,'Brave New World'),(154,'Emily Brontë','http://localhost:10339/file/upload_qnYpdu_wuthering-98124737037916003.jpeg','A dark and passionate story of love and revenge.','9780553212587',1500,0,45,'Wuthering Heights'),(155,'Charlotte Brontë','http://localhost:10339/file/upload_sbYjxy_jane-5524772956977401741.jpg','A Gothic novel about a young woman’s moral growth and love.','9780142437209',1600,0,60,'Jane Eyre'),(156,'Victor Hugo','http://localhost:10339/file/upload_1gEQtI_les-3998518072989014364.jpeg','A story of social injustice and redemption in 19th-century France.','9780451419438',2300,0,30,'Les Misérables'),(157,'J.R.R. Tolkien','http://localhost:10339/file/upload_lsg4NW_hobbit-7219060220498580799.png','A fantasy novel that follows Bilbo Baggins on a great adventure.','9780547928227',1800,0,80,'The Hobbit'),(158,'Leo Tolstoy','http://localhost:10339/file/upload_oz7vtQ_anna_karenina-12282989219130488198.jpg','A tragic love story set in Russian high society.','9780143035008',2000,1,49,'Anna Karenina'),(159,'Dante Alighieri','http://localhost:10339/file/upload_sJ3UAL_divine-15760452621663628008.jpeg','An allegorical journey through Hell, Purgatory, and Heaven.','9780142437223',2500,2,43,'The Divine Comedy'),(160,'Charles Dickens','http://localhost:10339/file/upload_hCcMLz_2city-5173286295625987419.jpg','A novel set during the French Revolution.','9780141439600',1700,0,40,'A Tale of Two Cities'),(161,'Mary Shelley','http://localhost:10339/file/upload_NSXKkS_frank-13314211244328642739.jpeg','The story of a scientist who creates a living being.','9780486282114',1200,0,65,'Frankenstein'),(162,'Oscar Wilde','http://localhost:10339/file/upload_qJXrBI_dorian-8113992443662451354.jpeg','A cautionary tale about vanity and moral corruption.','9780141439570',1300,0,49,'The Picture of Dorian Gray');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_comments`
--

DROP TABLE IF EXISTS `book_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_comments` (
  `book_id` int NOT NULL,
  `comments_id` int NOT NULL,
  UNIQUE KEY `UK_e1jbxs45sxghlxx356a3y2aew` (`comments_id`),
  KEY `FK4xt8nqqhxhlkt026ed9cvki2j` (`book_id`),
  CONSTRAINT `FK4xt8nqqhxhlkt026ed9cvki2j` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKe0ywlmijesq9fovfctx7ip4cq` FOREIGN KEY (`comments_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_comments`
--

LOCK TABLES `book_comments` WRITE;
/*!40000 ALTER TABLE `book_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_tags`
--

DROP TABLE IF EXISTS `book_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_tags` (
  `book_id` int NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FKgrb34gudrjkbeew59bty9sh45` (`book_id`),
  CONSTRAINT `FKgrb34gudrjkbeew59bty9sh45` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_tags`
--

LOCK TABLES `book_tags` WRITE;
/*!40000 ALTER TABLE `book_tags` DISABLE KEYS */;
INSERT INTO `book_tags` VALUES (144,'Classic'),(144,'Historical Fiction'),(144,'Social Issues'),(144,'Legal Drama'),(145,'Dystopian'),(145,'Science Fiction'),(145,'Political'),(145,'Classic'),(146,'Classic'),(146,'Romance'),(146,'Society'),(146,'Historical Fiction'),(147,'Classic'),(147,'American Literature'),(147,'Historical Fiction'),(147,'Tragedy'),(148,'Classic'),(148,'Adventure'),(148,'Seafaring'),(148,'Philosophical'),(150,'Classic'),(150,'Epic'),(150,'Mythology'),(150,'Adventure'),(152,'Classic'),(152,'Philosophical'),(152,'Drama'),(152,'Russian Literature'),(153,'Dystopian'),(153,'Science Fiction'),(153,'Philosophical'),(153,'Classic'),(154,'Classic'),(154,'Romance'),(154,'Gothic'),(154,'Drama'),(155,'Classic'),(155,'Romance'),(155,'Gothic'),(155,'Coming of Age'),(156,'Classic'),(156,'Historical Fiction'),(156,'Social Issues'),(156,'Redemption'),(157,'Fantasy'),(157,'Adventure'),(157,'Epic'),(157,'Classic'),(158,'Classic'),(158,'Romance'),(158,'Drama'),(158,'Russian Literature'),(159,'Classic'),(159,'Epic'),(159,'Philosophical'),(159,'Allegory'),(160,'Classic'),(160,'Historical Fiction'),(160,'Drama'),(160,'Revolution'),(161,'Classic'),(161,'Science Fiction'),(161,'Gothic'),(161,'Horror'),(162,'Classic'),(162,'Gothic'),(162,'Philosophical'),(162,'Morality');
/*!40000 ALTER TABLE `book_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKis5hg85qbs5d91etr4mvd4tx6` (`book_id`),
  KEY `FKjnaj4sjyqjkr4ivemf9gb25w` (`user_id`),
  CONSTRAINT `FKis5hg85qbs5d91etr4mvd4tx6` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKjnaj4sjyqjkr4ivemf9gb25w` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (58,1,157,1),(59,1,145,1),(60,1,148,1),(61,1,153,1),(62,1,150,1),(63,1,159,1),(64,1,146,1),(65,1,147,2),(66,1,146,2),(67,1,145,2),(68,1,154,1),(69,1,152,1);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcpl0mjoeqhxvgeeeq5piwpd3i` (`user_id`),
  CONSTRAINT `FKcpl0mjoeqhxvgeeeq5piwpd3i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (86,'ekjkjk','111','jkjf','2024-11-16 01:33:13.459000',1),(87,'222','22','222','2024-11-16 02:10:45.232000',1),(88,'厦门','creeper','1123343','2024-11-16 02:44:40.072000',1),(89,'kejk','11','jkjkj','2024-11-16 03:10:20.602000',1),(90,'jkfjdkjfkdj','1j1kjkfdjk','kjkfjdkjfkd','2024-11-16 03:10:48.063000',1),(91,'xm','hjt','123456789','2024-11-16 03:33:31.277000',1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb033an1f8qmpbnfl0a6jb5njs` (`book_id`),
  KEY `FKs234mi6jususbx4b37k44cipy` (`order_id`),
  CONSTRAINT `FKb033an1f8qmpbnfl0a6jb5njs` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKs234mi6jususbx4b37k44cipy` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (114,1,162,86),(115,1,147,86),(116,1,158,87),(117,1,148,87),(118,1,145,88),(119,1,159,89),(120,1,146,89),(121,1,146,90),(122,1,159,91);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `balance` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_auth_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_b6973dkj4eo2p8wa04r90l1ld` (`user_auth_id`),
  CONSTRAINT `FKceqgvkipb0do1j0xr4neax6ek` FOREIGN KEY (`user_auth_id`) REFERENCES `user_auth` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'http://localhost:10339/file/upload_eKWnA0_creeper-18342631483744036926.jpg',86000,'creeperhjt@sjtu.edu.cn',_binary '',_binary '\0','creeper',1),(2,'http://localhost:10339/file/upload_zwcHAU_emojimix.app_145_57-17667814927967352218.png',1223,'creeperhjt@example.com',_binary '\0',_binary '\0','creeper22',2),(3,'http://localhost:10339/file/upload_l3FxAi_emojimix.app_160_166-12449074300066888468.png',5000,'rep@example.com',_binary '\0',_binary '\0','user1',3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_auth`
--

DROP TABLE IF EXISTS `user_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_auth`
--

LOCK TABLES `user_auth` WRITE;
/*!40000 ALTER TABLE `user_auth` DISABLE KEYS */;
INSERT INTO `user_auth` VALUES (1,'123456'),(2,'123456'),(3,'123456');
/*!40000 ALTER TABLE `user_auth` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-15 15:57:10
