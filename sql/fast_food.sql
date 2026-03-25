

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `name`) VALUES
                                       (1, 'Phú Thịnh'),
                                       (2, 'Trọng Bình'),
                                       (3, 'Hữu Trường'),
                                       (4, 'Phước Nhân'),
                                       (5, 'Phú Quốc');



