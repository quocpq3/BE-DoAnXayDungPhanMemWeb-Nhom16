
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 25, 2026 at 03:10 PM
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
(1, 1, 'Gà Rán Truyền Thống (1 miếng)', 'ga-ran-1-mieng', 'Gà rán giòn rụm với công thức 11 loại thảo mộc truyền thống, giữ trọn độ ẩm bên trong.', 'https://dulichmy.com.vn/wp-content/uploads/2021/12/ga-ran-7.jpg', 35000, 0, 35000, 1, 0, '2026-03-24 15:57:33'),
(2, 1, 'Gà Rán Cay (1 miếng)', 'ga-ran-cay-1-mieng', 'Vị cay bùng nổ kết hợp cùng lớp vỏ giòn tan, dành cho tín đồ ăn cay chính hiệu.', 'https://cdn.tgdd.vn/Files/2018/12/18/1138838/cung-thuc-hien-mon-ga-cay-han-quoc-cuc-ky-dua-com-202209051440125046.jpg', 39000, 5, 37050, 1, 0, '2026-03-24 15:57:33'),
(3, 1, 'Khoai Tây Chiên (Vừa)', 'khoai-tay-chien', 'Khoai tây tươi cắt múi, chiên vàng đều và rắc thêm một chút muối tinh khiết.', 'https://cdn.tgdd.vn/Files/2015/03/01/615221/bi-quyet-lam-moi-khoai-tay-chien-cu-5-760x367.jpg', 25000, 0, 25000, 1, 0, '2026-03-24 15:57:33'),
(4, 2, 'Burger Bò Phô Mai', 'burger-bo-pho-mai', 'Thịt bò nhập khẩu nướng lửa hồng, kết hợp cùng phô mai Cheddar tan chảy và rau tươi.', 'https://www.shutterstock.com/image-photo/fresh-craft-beef-burger-cucumber-600nw-2721098677.jpg', 59000, 0, 59000, 1, 0, '2026-03-24 15:57:33'),
(5, 3, 'Pizza Hải Sản (Size S)', 'pizza-hai-san-s', 'Tôm, mực tươi ngon trên nền sốt Pesto xanh mát và lớp phô mai Mozzarella dẻo mịn.', 'https://img.dominos.vn/Pizzaminsea-Hai-San-Nhiet-Doi-Xot-Tieu.jpg', 129000, 10, 116100, 1, 0, '2026-03-24 15:57:33'),
(6, 3, 'Pizza Gà Nướng (Size S)', 'pizza-ga-nuong-s', 'Gà nướng mật ong thơm phức kết hợp cùng hành tây và ớt chuông xanh giòn ngọt.', 'https://file.hstatic.net/200000700229/article/lam-pizza-ga-pho-mai-bang-noi-chien-khong-dau_2fd987541e3e4ea7b16e94bdd1a73764.jpg', 119000, 10, 107100, 1, 0, '2026-03-24 15:57:33'),
(7, 4, 'Pepsi (Lớn)', 'pepsi-lon', 'Nước giải khát Pepsi mát lạnh, sảng khoái, sự kết hợp hoàn hảo cho mọi bữa ăn.', 'https://hd1.hotdeal.vn/images/uploads/2016/Thang%2011/5/303546/303546-combo-burger-dui-ga-bbq-chicken-pizza-my-dinh-body-6.jpg', 19000, 0, 19000, 1, 0, '2026-03-24 15:57:33'),
(8, 4, '7Up (Lớn)', '7up-lon', 'Vị chanh tự nhiên, tươi mát, giúp xua tan cơn khát tức thì.', 'https://sieuthihoaba.com.vn/wp-content/uploads/2020/08/nuoc-ngot-7-up-vi-chanh-330ml-201905301056152288.jpg', 19000, 0, 19000, 1, 0, '2026-03-24 15:57:33'),
(9, 4, 'Trà Đào Cam Sả', 'tra-dao-cam-sa', 'Sự kết hợp tuyệt vời giữa trà đào thơm ngọt, cam tươi và hương sả nồng nàn.', 'https://cdn.tgdd.vn/Files/2018/08/14/1109521/cach-lam-tra-dao-cam-sa-giai-nhiet-hieu-qua-ngay-tai-nha-202112301524304318.jpg', 29000, 0, 29000, 1, 0, '2026-03-24 15:57:33'),
(10, 5, 'Combo Đơn Thân', 'combo-don-than', 'Phần ăn dành cho 1 người gồm: 1 Burger Bò, 1 Khoai tây chiên size vừa và 1 Pepsi.', 'https://vnsupermark.com/uploads_vnsupermark/uploads/catalog/fa297da80ab080684f5c90b6b6c568ce-ef625f4658.jpg', 65000, 15, 55250, 1, 1, '2026-03-24 15:57:33'),
(11, 5, 'Combo Cặp Đôi', 'combo-cap-doi', 'Bữa ăn lãng mạn cho 2 người: 2 Gà rán truyền thống, 1 Pizza Hải sản size S và 2 ly 7Up.', 'https://static.hotdeal.vn/images/1424/1423772/500x500/321879-he-thong-lotteria-combo-ban-chay-nhat-cho-2-nguoi-sl-co-han.jpg', 150000, 20, 120000, 1, 1, '2026-03-24 15:57:33'),
(12, 5, 'Combo Party Pizza', 'combo-party-pizza', 'Tiệc vui bất tận: 2 Pizza lớn tùy chọn, 1 hộp Gà rán 5 miếng và 1 chai Pepsi 1.5L.', 'https://thepizzacompany.vn/images/thumbs/000/0003997_chat-vibe_500.jpg', 250000, 25, 187500, 1, 1, '2026-03-24 15:57:33');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--


DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
                                          `payment_id` bigint NOT NULL AUTO_INCREMENT,
                                          `order_id` bigint NOT NULL,
                                          `transaction_id` varchar(255) DEFAULT NULL,
    `payment_method` varchar(255) DEFAULT NULL,
    `amount` decimal(38,2) DEFAULT NULL,
    `status` varchar(255) DEFAULT NULL,
    `payment_date` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`payment_id`),
    KEY `FKjvm1wv27g1k37fxvr9k1yb9db` (`order_id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `order_id`, `transaction_id`, `payment_method`, `amount`, `status`, `payment_date`) VALUES
                                                                                                                              (1, 1, 'VNP12345678', 'VNPAY', 81050.00, 'SUCCESS', '2026-04-05 04:48:07'),
                                                                                                                              (2, 2, NULL, 'CASH', 97000.00, 'SUCCESS', '2026-04-05 04:54:13'),
                                                                                                                              (3, 108, 'MOMO_TEST_123456', 'MOMO', 160000.00, 'SUCCESS', '2026-04-05 05:00:37'),
                                                                                                                              (4, 109, 'CASH_109_1775365249519', 'CASH', 930000.00, 'SUCCESS', '2026-04-05 05:00:50'),
                                                                                                                              (5, 110, '123456789', 'MOMO', 950000.00, 'SUCCESS', '2026-04-05 07:14:25'),
                                                                                                                              (6, 111, 'CASH_ORD-326569', 'CASH', 790000.00, 'SUCCESS', '2026-04-05 07:14:58'),
                                                                                                                              (7, 112, 'CASH_ORD-8708610', 'CASH', 130000.00, 'SUCCESS', '2026-04-05 07:16:43'),
                                                                                                                              (8, 113, 'CASH_ORD-6073611', 'CASH', 910000.00, 'SUCCESS', '2026-04-05 07:19:32'),
                                                                                                                              (9, 114, 'MM_ORD-7021212_1775373635187', 'MOMO', 810000.00, 'SUCCESS', '2026-04-05 07:20:35'),
                                                                                                                              (10, 115, 'MM_ORD-8741313_1775373904264', 'MOMO', 980000.00, 'SUCCESS', '2026-04-05 07:25:04');
COMMIT;

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
`
-- --------------------------------------------------------
--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `order_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_code` VARCHAR(50) NOT NULL,
  `user_id` BIGINT DEFAULT NULL,
  `customer_name` VARCHAR(100) NOT NULL,
  `customer_phone` VARCHAR(20) DEFAULT NULL,
  `delivery_address` VARCHAR(255) DEFAULT NULL,
  `order_status` VARCHAR(30) NOT NULL DEFAULT 'PENDING',
  `payment_method` VARCHAR(20) NOT NULL DEFAULT 'CASH',
  `delivery_method` VARCHAR(20) NOT NULL DEFAULT 'PICKUP',
  `total_amount` DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  `note` TEXT DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_detail_order_code` (`order_code`),
  KEY `idx_order_detail_user_id` (`user_id`),

  CONSTRAINT `fk_order_detail_user`
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `order_items` (
  `order_item_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `item_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `unit_price` DECIMAL(12,2) NOT NULL,
  `line_total` DECIMAL(12,2) NOT NULL,

  PRIMARY KEY (`order_item_id`),
  KEY `idx_order_items_order_id` (`order_id`),
  KEY `idx_order_items_item_id` (`item_id`),

  CONSTRAINT `fk_order_items_order_detail`
    FOREIGN KEY (`order_id`) REFERENCES `order_detail` (`order_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CONSTRAINT `fk_order_items_menu_item`
    FOREIGN KEY (`item_id`) REFERENCES `menu_items` (`item_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `order_items`
(
  `order_id`,
  `item_id`,
  `quantity`,
  `unit_price`,
  `line_total`
)
VALUES
(1, 2, 1, 37050.00, 37050.00),
(1, 3, 1, 25000.00, 25000.00),
(1, 7, 1, 19000.00, 19000.00),

(2, 4, 1, 59000.00, 59000.00),
(2, 8, 2, 19000.00, 38000.00);

INSERT INTO `order_detail`
(
  `order_code`,
  `user_id`,
  `customer_name`,
  `customer_phone`,
  `delivery_address`,
  `order_status`,
  `payment_method`,
  `delivery_method`,
  `total_amount`,
  `note`
)
VALUES
(
  'ORD20260404001',
  1,
  'Nguyễn Minh Tân',
  '0123456789',
  NULL,
  'PENDING',
  'CASH',
  'PICKUP',
  81050.00,
  'Khách đến lấy'
),
(
  'ORD20260404002',
  2,
  'Trần Văn A',
  '0988888888',
  '12 Trần Hưng Đạo, Long Xuyên',
  'COMPLETED',
  'BANK_TRANSFER',
  'DELIVERY',
  97000.00,
  'Giao đi'
);
ALTER TABLE order_detail
  DROP COLUMN customer_name;
`OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
