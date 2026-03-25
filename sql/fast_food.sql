-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 25, 2026 at 04:55 AM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fast_food`
--

-- --------------------------------------------------------

--
-- Table structure for table `combo_details`
--

DROP TABLE IF EXISTS `combo_details`;
CREATE TABLE IF NOT EXISTS `combo_details` (
  `combo_item_id` bigint NOT NULL,
  `component_item_id` bigint NOT NULL,
  `quantity` int DEFAULT '1',
  PRIMARY KEY (`combo_item_id`,`component_item_id`),
  KEY `fk_combo_component` (`component_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `combo_details`
--

INSERT INTO `combo_details` (`combo_item_id`, `component_item_id`, `quantity`) VALUES
(10, 1, 1),
(10, 7, 1),
(11, 2, 2),
(11, 3, 1),
(11, 7, 2),
(12, 4, 1),
(12, 5, 1),
(12, 7, 1),
(12, 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `menu_categories`
--

DROP TABLE IF EXISTS `menu_categories`;
CREATE TABLE IF NOT EXISTS `menu_categories` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `slug` varchar(150) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `slug` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `menu_categories`
--

INSERT INTO `menu_categories` (`category_id`, `category_name`, `slug`, `description`, `created_at`) VALUES
(1, 'Gà Rán', 'ga-ran', 'Gà tươi chiên giòn mỗi ngày', '2026-03-24 15:57:32'),
(2, 'Burger', 'burger', 'Burger bò Mỹ và gà phi lê', '2026-03-24 15:57:32'),
(3, 'Pizza', 'pizza', 'Pizza đế tươi nướng củi', '2026-03-24 15:57:32'),
(4, 'Thức Uống', 'thuc-uong', 'Nước ngọt và trà giải nhiệt', '2026-03-24 15:57:32'),
(5, 'Combo Tiết Kiệm', 'combo-tiet-kiem', 'Gói ăn nhóm ưu đãi đến 30%', '2026-03-24 15:57:32');

-- --------------------------------------------------------

--
-- Table structure for table `menu_items`
--

DROP TABLE IF EXISTS `menu_items`;
CREATE TABLE IF NOT EXISTS `menu_items` (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint DEFAULT NULL,
  `item_name` varchar(200) NOT NULL,
  `slug` varchar(150) DEFAULT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `base_price` double DEFAULT NULL,
  `discount_percent` int DEFAULT NULL,
  `sale_price` double DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT '1',
  `is_combo` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `slug` (`slug`),
  KEY `fk_menu_item_category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `menu_items`
--

INSERT INTO `menu_items` (`item_id`, `category_id`, `item_name`, `slug`, `description`, `image_url`, `base_price`, `discount_percent`, `sale_price`, `is_available`, `is_combo`, `created_at`) VALUES
(1, 1, 'Gà Rán Truyền Thống (1 miếng)', 'ga-ran-1-mieng', 'Gà rán giòn rụm với công thức 11 loại thảo mộc truyền thống, giữ trọn độ ẩm bên trong.', 'images/1.jpg', 35000, 0, 35000, 1, 0, '2026-03-24 15:57:33'),
(2, 1, 'Gà Rán Cay (1 miếng)', 'ga-ran-cay-1-mieng', 'Vị cay bùng nổ kết hợp cùng lớp vỏ giòn tan, dành cho tín đồ ăn cay chính hiệu.', 'images/2.jpg', 39000, 5, 37050, 1, 0, '2026-03-24 15:57:33'),
(3, 1, 'Khoai Tây Chiên (Vừa)', 'khoai-tay-chien', 'Khoai tây tươi cắt múi, chiên vàng đều và rắc thêm một chút muối tinh khiết.', 'images/3.jpg', 25000, 0, 25000, 1, 0, '2026-03-24 15:57:33'),
(4, 2, 'Burger Bò Phô Mai', 'burger-bo-pho-mai', 'Thịt bò nhập khẩu nướng lửa hồng, kết hợp cùng phô mai Cheddar tan chảy và rau tươi.', 'images/4.jpg', 59000, 0, 59000, 1, 0, '2026-03-24 15:57:33'),
(5, 3, 'Pizza Hải Sản (Size S)', 'pizza-hai-san-s', 'Tôm, mực tươi ngon trên nền sốt Pesto xanh mát và lớp phô mai Mozzarella dẻo mịn.', 'images/5.jpg', 129000, 10, 116100, 1, 0, '2026-03-24 15:57:33'),
(6, 3, 'Pizza Gà Nướng (Size S)', 'pizza-ga-nuong-s', 'Gà nướng mật ong thơm phức kết hợp cùng hành tây và ớt chuông xanh giòn ngọt.', 'images/6.jpg', 119000, 10, 107100, 1, 0, '2026-03-24 15:57:33'),
(7, 4, 'Pepsi (Lớn)', 'pepsi-lon', 'Nước giải khát Pepsi mát lạnh, sảng khoái, sự kết hợp hoàn hảo cho mọi bữa ăn.', 'images/7.jpg', 19000, 0, 19000, 1, 0, '2026-03-24 15:57:33'),
(8, 4, '7Up (Lớn)', '7up-lon', 'Vị chanh tự nhiên, tươi mát, giúp xua tan cơn khát tức thì.', 'images/8.jpg', 19000, 0, 19000, 1, 0, '2026-03-24 15:57:33'),
(9, 4, 'Trà Đào Cam Sả', 'tra-dao-cam-sa', 'Sự kết hợp tuyệt vời giữa trà đào thơm ngọt, cam tươi và hương sả nồng nàn.', 'images/9.jpg', 29000, 0, 29000, 1, 0, '2026-03-24 15:57:33'),
(10, 5, 'Combo Đơn Thân', 'combo-don-than', 'Phần ăn dành cho 1 người gồm: 1 Burger Bò, 1 Khoai tây chiên size vừa và 1 Pepsi.', 'images/10.jpg', 65000, 15, 55250, 1, 1, '2026-03-24 15:57:33'),
(11, 5, 'Combo Cặp Đôi', 'combo-cap-doi', 'Bữa ăn lãng mạn cho 2 người: 2 Gà rán truyền thống, 1 Pizza Hải sản size S và 2 ly 7Up.', 'images/11.jpg', 150000, 20, 120000, 1, 1, '2026-03-24 15:57:33'),
(12, 5, 'Combo Party Pizza', 'combo-party-pizza', 'Tiệc vui bất tận: 2 Pizza lớn tùy chọn, 1 hộp Gà rán 5 miếng và 1 chai Pepsi 1.5L.', 'images/12.jpg', 250000, 25, 187500, 1, 1, '2026-03-24 15:57:33');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`) VALUES
(1, 'Phú Thịnh'),
(2, 'Trọng Bình'),
(3, 'Hữu Trường'),
(4, 'Phước Nhân'),
(5, 'Phú Quốc'),
(8, 'Thầy Hansi Flick ');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `combo_details`
--
ALTER TABLE `combo_details`
  ADD CONSTRAINT `fk_combo_component` FOREIGN KEY (`component_item_id`) REFERENCES `menu_items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_combo_parent` FOREIGN KEY (`combo_item_id`) REFERENCES `menu_items` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `menu_items`
--
ALTER TABLE `menu_items`
  ADD CONSTRAINT `fk_menu_item_category` FOREIGN KEY (`category_id`) REFERENCES `menu_categories` (`category_id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
