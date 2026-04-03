-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th4 03, 2026 lúc 01:57 PM
-- Phiên bản máy phục vụ: 8.4.7
-- Phiên bản PHP: 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `fast_food`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `combo_details`
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
-- Đang đổ dữ liệu cho bảng `combo_details`
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
-- Cấu trúc bảng cho bảng `menu_categories`
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
-- Đang đổ dữ liệu cho bảng `menu_categories`
--

INSERT INTO `menu_categories` (`category_id`, `category_name`, `slug`, `description`, `created_at`) VALUES
                                                                                                        (1, 'Gà Rán', 'ga-ran', 'Gà tươi chiên giòn mỗi ngày', '2026-03-24 15:57:32'),
                                                                                                        (2, 'Burger', 'burger', 'Burger bò Mỹ và gà phi lê', '2026-03-24 15:57:32'),
                                                                                                        (3, 'Pizza', 'pizza', 'Pizza đế tươi nướng củi', '2026-03-24 15:57:32'),
                                                                                                        (4, 'Thức Uống', 'thuc-uong', 'Nước ngọt và trà giải nhiệt', '2026-03-24 15:57:32'),
                                                                                                        (5, 'Combo Tiết Kiệm', 'combo-tiet-kiem', 'Gói ăn nhóm ưu đãi đến 30%', '2026-03-24 15:57:32');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `menu_items`
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
-- Đang đổ dữ liệu cho bảng `menu_items`
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
-- Cấu trúc bảng cho bảng `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
                                        `order_id` bigint NOT NULL AUTO_INCREMENT,
                                        `created_at` datetime(6) DEFAULT NULL,
    `customer_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `customer_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `note` text COLLATE utf8mb4_unicode_ci,
    `order_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    `status` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
    `total_amount` decimal(12,2) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `UKdhk2umg8ijjkg4njg6891trit` (`order_code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_details`
--

DROP TABLE IF EXISTS `order_details`;
CREATE TABLE IF NOT EXISTS `order_details` (
                                               `order_detail_id` bigint NOT NULL AUTO_INCREMENT,
                                               `line_total` decimal(12,2) NOT NULL,
    `menu_item_id` bigint NOT NULL,
    `quantity` int NOT NULL,
    `unit_price` decimal(12,2) NOT NULL,
    `order_id` bigint NOT NULL,
    PRIMARY KEY (`order_detail_id`),
    KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
                                       (1, 'ROLE_ADMIN'),
                                       (2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `name` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `name`, `password`) VALUES
                                                   (1, 'Phú Thịnh', '$2b$10$wH8Q9v9u0Zr7lqVbXnYv5eGQ6cC0zZK5Zy6Yg9cVf0XzQzF2Vb8nG'),
                                                   (2, 'Trọng Bình', '$2b$10$wH8Q9v9u0Zr7lqVbXnYv5eGQ6cC0zZK5Zy6Yg9cVf0XzQzF2Vb8nG'),
                                                   (4, 'phuocnhan', '$2a$10$lgTa10z/S1mGr2ZppelWzes8anQ07JyAiA.Bd5hAmMSJCcPv0bMS6'),
                                                   (5, 'Phú Quốc', '$2b$10$wH8Q9v9u0Zr7lqVbXnYv5eGQ6cC0zZK5Zy6Yg9cVf0XzQzF2Vb8nG'),
                                                   (13, 'nhanvang', '$2a$10$lgTa10z/S1mGr2ZppelWzes8anQ07JyAiA.Bd5hAmMSJCcPv0bMS6'),
                                                   (14, 'huutruong', '$2a$10$3HMQIQs8Uw1aNXakOWhVZ.t.706dq3f0.Lp6lBThjnFu6w/kXEGua');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
                                            `user_id` bigint NOT NULL,
                                            `role_id` bigint NOT NULL,
                                            PRIMARY KEY (`user_id`,`role_id`),
    KEY `fk_role` (`role_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
                                                    (4, 1),
                                                    (1, 2),
                                                    (2, 2),
                                                    (5, 2),
                                                    (13, 2),
                                                    (14, 2);

--
-- Ràng buộc đối với các bảng kết xuất
--

--
-- Ràng buộc cho bảng `combo_details`
--
ALTER TABLE `combo_details`
    ADD CONSTRAINT `FKcrki7jyoj4hfu3tomkthm31np` FOREIGN KEY (`component_item_id`) REFERENCES `menu_items` (`item_id`),
  ADD CONSTRAINT `FKlw6xiri07pulxk14mu75pdpgs` FOREIGN KEY (`combo_item_id`) REFERENCES `menu_items` (`item_id`);

--
-- Ràng buộc cho bảng `menu_items`
--
ALTER TABLE `menu_items`
    ADD CONSTRAINT `FK4pc1grgsms7nqm2i6oig37pro` FOREIGN KEY (`category_id`) REFERENCES `menu_categories` (`category_id`);

--
-- Ràng buộc cho bảng `order_details`
--
ALTER TABLE `order_details`
    ADD CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Ràng buộc cho bảng `user_roles`
--
ALTER TABLE `user_roles`
    ADD CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
