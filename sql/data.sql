USE peiwan_db;

-- BCrypt 加密的 "123456" 对应密码: $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi
INSERT INTO `user` (username, password, real_name, phone, role, status) VALUES
('player1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试玩家', '13800000001', 'player', 1),
('master1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '金牌陪玩', '13800000002', 'master', 1),
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', '13800000000', 'admin', 1);

-- 插入陪玩师详情 (master1 对应 user.id=2)
INSERT INTO `master` (user_id, game_level, price_per_hour, tags, auth_status) VALUES
(2, '钻石', 50.00, '技术好,声音好听,可教学', 'approved');

-- 插入几条模拟订单（方便演示）
INSERT INTO `orders` (player_id, master_id, amount, status) VALUES
(1, 2, 100.00, 'completed'),
(1, NULL, 80.00, 'pending');

-- 插入评价（针对已完成订单）
INSERT INTO `review` (order_id, rating, comment) VALUES (1, 5, '技术很棒，沟通顺畅');