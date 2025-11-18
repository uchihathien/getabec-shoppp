USE greeny_shop;

-- 1️⃣ Xóa user admin cũ nếu có
DELETE FROM users_roles WHERE user_id IN (SELECT user_id FROM user WHERE email='admin@gmail.com');
DELETE FROM user WHERE email='admin@gmail.com';

-- 2️⃣ Đảm bảo role ADMIN tồn tại
INSERT INTO role (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name='ROLE_ADMIN');

-- 3️⃣ Tạo lại tài khoản admin với password 'admin123'
INSERT INTO user (name, email, password, avatar, address, register_date, status)
VALUES ('Administrator', 'admin@gmail.com', '$2a$10$rC71KCzj1tSJuQr/mxjIUeroktrbc0DL.6pc.DJa5txn1pcickU0S', '', '', CURDATE(), TRUE);

-- 4️⃣ Gán quyền ADMIN
INSERT INTO users_roles (user_id, role_id)
SELECT u.user_id, r.id
FROM user u
         JOIN role r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'admin@gmail.com';






INSERT INTO categories (category_name, category_image)
VALUES
    ('Lò Hơi Công Nghiệp', NULL),
    ('Bộ Sấy Khí – Economizer', NULL),
    ('Thiết Bị Đốt – Burner', NULL),
    ('Bơm Cấp Nước Lò Hơi', NULL),
    ('Hệ Thống Xử Lý Nước', NULL),
    ('Van Công Nghiệp', NULL),
    ('Đường Ống – Phụ Kiện Ống', NULL),
    ('Thiết Bị Điều Khiển – PLC', NULL),
    ('Quạt Hút – Quạt Thổi', NULL),
    ('Phụ Tùng – Vật Tư Bảo Trì', NULL);


ALTER TABLE categories CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;




INSERT INTO products
(product_name, product_code, quantity, price, discount, product_image, description, entered_date, status, favorite, is_deleted, category_id)
VALUES
    ('Van an toàn lò hơi 34035061', 'P34035061', 50, 950000, 10, '/images/product/34035061-300x300.jpg', 'Van an toàn dùng cho hệ thống lò hơi công nghiệp', NOW(), 1, 0, 0, 1),
    ('Van an toàn 34035064', 'P34035064', 50, 990000, 12, '/images/product/34035064-300x300.jpg', 'Van an toàn áp suất cao', NOW(), 1, 0, 0, 1),
    ('Cảm biến áp suất 36105025', 'P36105025', 40, 780000, 5, '/images/product/36105025-300x300.jpg', 'Pressure sensor chuẩn công nghiệp', NOW(), 1, 0, 0, 3),
    ('Cảm biến nhiệt độ 36105029', 'P36105029', 40, 850000, 7, '/images/product/36105029-300x300.jpg', 'Temperature sensor cho hệ hơi', NOW(), 1, 0, 0, 3),
    ('Van 36105063', 'P36105063', 60, 620000, 8, '/images/product/36105063-300x300.jpg', 'Van công nghiệp tiêu chuẩn DIN', NOW(), 1, 0, 0, 1),
    ('Công tắc áp suất 36105097', 'P36105097', 30, 890000, 10, '/images/product/36105097-300x300.jpg', 'Pressure switch kiểm soát áp lực', NOW(), 1, 0, 0, 3),
    ('Đồng hồ áp suất 36106007', 'P36106007', 100, 350000, 5, '/images/product/36106007-300x300.jpg', 'Đồng hồ áp suất chân đứng', NOW(), 1, 0, 0, 2),
    ('Đồng hồ áp suất 36106008', 'P36106008', 100, 420000, 6, '/images/product/36106008-300x300.jpg', 'Đồng hồ áp suất chân sau', NOW(), 1, 0, 0, 2),
    ('Đồng hồ 36106017', 'P36106017', 100, 430000, 5, '/images/product/36106017-300x300.jpg', 'Gauge đo áp tiêu chuẩn Châu Âu', NOW(), 1, 0, 0, 2),
    ('Đồng hồ 36106024', 'P36106024', 120, 390000, 5, '/images/product/36106024-300x300.png', 'Đồng hồ đo áp chính xác cao', NOW(), 1, 0, 0, 2),
    ('Đồng hồ 36106038', 'P36106038', 70, 410000, 10, '/images/product/36106038-300x300.jpg', 'Đồng hồ hơi công nghiệp', NOW(), 1, 0, 0, 2),
    ('Cảm biến mức 36106039', 'P36106039', 30, 1100000, 12, '/images/product/36106039-300x300.jpg', 'Level sensor nước/hoá chất', NOW(), 1, 0, 0, 3),
    ('Cảm biến nhiệt 36108046', 'P36108046', 45, 960000, 8, '/images/product/36108046-300x300.jpg', 'Cảm biến nhiệt độ vỏ thép', NOW(), 1, 0, 0, 3),
    ('Van bi thép 36110148', 'P36110148', 60, 560000, 5, '/images/product/36110148-300x300.jpg', 'Van bi ren tiêu chuẩn JIS', NOW(), 1, 0, 0, 7),
    ('Van bướm 36217030', 'P36217030', 80, 750000, 6, '/images/product/36217030-300x300.jpg', 'Van bướm tay gạt thân gang', NOW(), 1, 0, 0, 7),
    ('Van bướm 36217031', 'P36217031', 80, 780000, 8, '/images/product/36217031-300x300.jpg', 'Van bướm tay quay', NOW(), 1, 0, 0, 7),
    ('Van cầu 36217035', 'P36217035', 55, 540000, 5, '/images/product/36217035-300x300.jpg', 'Van cầu thép rèn', NOW(), 1, 0, 0, 7),
    ('Van y lọc 36217044', 'P36217044', 70, 460000, 6, '/images/product/36217044-300x263.jpg', 'Y lọc inox lưới lọc 304', NOW(), 1, 0, 0, 7),
    ('Van y lọc 36217080', 'P36217080', 70, 480000, 7, '/images/product/36217080-300x263.jpg', 'Van y lọc hơi nóng', NOW(), 1, 0, 0, 7),
    ('Cảm biến 36217631', 'P36217631', 35, 1050000, 10, '/images/product/36217631-300x300.jpg', 'Sensor đo mức điện dung', NOW(), 1, 0, 0, 3),
    ('Transmitter 36217736', 'P36217736', 35, 1450000, 10, '/images/product/36217736-Pressure-Transmitter-Front-300x300.jpg', 'Bộ truyền áp suất công nghiệp', NOW(), 1, 0, 0, 3),
    ('Cảm biến 36217810', 'P36217810', 35, 1480000, 10, '/images/product/36217810-rotated-e1733143006661-300x300.jpg', 'Cảm biến đo áp độ nhạy cao', NOW(), 1, 0, 0, 3),
    ('Bộ điều khiển 37175026', 'P37175026', 30, 1750000, 12, '/images/product/37175026-300x300.jpg', 'Bộ điều khiển áp hơi', NOW(), 1, 0, 0, 6),
    ('Switch áp suất 37175046', 'P37175046', 30, 950000, 8, '/images/product/37175046-300x300.jpg', 'Công tắc áp suất', NOW(), 1, 0, 0, 6),
    ('Switch hơi 37175101', 'P37175101', 30, 1150000, 10, '/images/product/37175101-Pressure-Switch-Side-300x300.jpg', 'Switch kiểm soát hệ thống hơi', NOW(), 1, 0, 0, 6),
    ('Van hơi 37178029', 'P37178029', 80, 850000, 10, '/images/product/37178029-300x300.jpg', 'Van hơi thép đúc', NOW(), 1, 0, 0, 1),
    ('Van 37178056', 'P37178056', 80, 890000, 8, '/images/product/37178056-300x300.jpg', 'Van cổng đồng', NOW(), 1, 0, 0, 1),
    ('Van 37178060', 'P37178060', 80, 990000, 10, '/images/product/37178060-300x300.jpg', 'Van gang chịu nhiệt', NOW(), 1, 0, 0, 1),
    ('Van 37178064', 'P37178064', 80, 920000, 9, '/images/product/37178064-300x300.jpg', 'Van thép WCB', NOW(), 1, 0, 0, 1),
    ('Van 37178067', 'P37178067', 80, 960000, 12, '/images/product/37178067-300x300.jpg', 'Van điều khiển tuyến tính', NOW(), 1, 0, 0, 1),
    ('Van 37178077', 'P37178077', 80, 1050000, 11, '/images/product/37178077-300x300.jpg', 'Van điều khiển khí nén', NOW(), 1, 0, 0, 1),
    ('Van 37178068', 'P37178068', 85, 1080000, 12, '/images/product/37178068-300x300.jpg', 'Van inox áp cao', NOW(), 1, 0, 0, 1);



SET SQL_SAFE_UPDATES = 0;
UPDATE products
SET product_image = REPLACE(product_image, '/images/product/', '')
WHERE product_image LIKE '/images/product/%';

