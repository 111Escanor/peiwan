-- 创建数据库
CREATE DATABASE IF NOT EXISTS peiwan_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE peiwan_db;

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `real_name` VARCHAR(50),
  `phone` VARCHAR(20),
  `role` ENUM('player', 'master', 'admin') DEFAULT 'player',
  `status` TINYINT DEFAULT 1 COMMENT '1启用 0禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 陪玩师详情表
DROP TABLE IF EXISTS `master`;
CREATE TABLE `master` (
  `user_id` BIGINT PRIMARY KEY,
  `game_level` VARCHAR(20),
  `price_per_hour` DECIMAL(10,2),
  `tags` VARCHAR(200),
  `total_orders` INT DEFAULT 0,
  `rating` DECIMAL(2,1) DEFAULT 5.0,
  `auth_status` ENUM('pending','approved','rejected') DEFAULT 'pending',
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);

-- 订单表
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `player_id` BIGINT NOT NULL,
  `master_id` BIGINT,
  `amount` DECIMAL(10,2) NOT NULL,
  `status` ENUM('pending','paid','in_service','completed','cancelled') DEFAULT 'pending',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `pay_time` DATETIME,
  `complete_time` DATETIME,
  FOREIGN KEY (`player_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`master_id`) REFERENCES `user`(`id`)
);

-- 评价表
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `rating` INT CHECK (rating BETWEEN 1 AND 5),
  `comment` TEXT,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE
);

-- 支付记录表（模拟支付）
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `amount` DECIMAL(10,2),
  `status` ENUM('success','failed') DEFAULT 'success',
  `transaction_id` VARCHAR(100),
  `pay_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`)
);

-- 添加索引
CREATE INDEX idx_orders_player ON orders(player_id);
CREATE INDEX idx_orders_master ON orders(master_id);
CREATE INDEX idx_orders_status ON orders(status);