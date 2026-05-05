-- ========================================
-- 用户表：存储所有账号信息
-- ========================================
CREATE TABLE `user` (
    `id`            INT PRIMARY KEY AUTO_INCREMENT,   -- 用户ID，自增主键
    `username`      VARCHAR(50) NOT NULL UNIQUE,      -- 登录名，唯一不可重复
    `password_hash` VARCHAR(255) NOT NULL,            -- bcrypt 哈希后的密码（不是明文）
    `nickname`      VARCHAR(50) NOT NULL DEFAULT '',  -- 显示昵称
    `avatar`        VARCHAR(500) NOT NULL DEFAULT '', -- 头像 URL
    `role`          VARCHAR(20) NOT NULL DEFAULT 'user'  -- 角色：user(普通用户) / companion(陪玩) / admin(管理员)
                    COMMENT 'user / companion / admin',
    `status`        VARCHAR(20) NOT NULL DEFAULT 'active' -- 状态：active(正常) / disabled(被封禁)
                    COMMENT 'active / disabled',
    `created_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,       -- 注册时间，自动填入
    `updated_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP       -- 更新时间，修改行时自动更新
                    ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- ENGINE=InnoDB：支持事务和外键
-- utf8mb4：支持 emoji 和全部 Unicode 字符


-- ========================================
-- 陪玩信息表：每个陪玩的详细资料
-- 一对一关系，一个用户只有一条陪玩资料
-- ========================================
CREATE TABLE `companion_profile` (
    `id`              INT PRIMARY KEY AUTO_INCREMENT,   -- 主键
    `user_id`         INT NOT NULL,                     -- 关联用户ID
    `game_rank`       VARCHAR(50) NOT NULL DEFAULT '',  -- 游戏段位（黑铁/青铜/白银/黄金/铂金/钻石/超凡/神话/赋能）
                      COMMENT '段位',
    `price_per_hour`  DECIMAL(10,2) NOT NULL DEFAULT 0, -- 每小时价格，DECIMAL 精确无浮点误差
    `description`     TEXT NOT NULL,                    -- 个人介绍，不限长度
    `tags`            VARCHAR(500) NOT NULL DEFAULT '', -- 标签，逗号分隔（如 "带妹,上分,语音"）
                      COMMENT '逗号分隔',
    `status`          VARCHAR(20) NOT NULL DEFAULT 'offline' -- 在线状态：online / offline / busy
                      COMMENT 'online / offline / busy',
    `game_level`      INT NOT NULL DEFAULT 0,           -- 游戏等级
    `win_rate`        FLOAT NOT NULL DEFAULT 0,         -- 胜率，0~1（如 0.65 = 65%）
    `games_played`    INT NOT NULL DEFAULT 0,           -- 总游戏场次
    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 外键：user_id -> user.id
    -- ON DELETE CASCADE：用户被删除时，自动删除他的陪玩资料
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ========================================
-- 订单表：用户下单找陪玩的记录
-- 注意：order 是 MySQL 保留字，要用反引号包住
-- ========================================
CREATE TABLE `order` (
    `id`            INT PRIMARY KEY AUTO_INCREMENT,   -- 订单ID
    `customer_id`   INT NOT NULL,                     -- 下单用户（买家）
    `companion_id`  INT NOT NULL,                     -- 接单陪玩（卖家）
    `hours`         DECIMAL(10,1) NOT NULL DEFAULT 1, -- 约玩时长，精确到 0.1 小时（6分钟）
    `total_price`   DECIMAL(10,2) NOT NULL DEFAULT 0, -- 总价 = 陪玩单价 × 时长
    `status`        VARCHAR(20) NOT NULL DEFAULT 'pending' -- 订单状态流转：
                    COMMENT 'pending / confirmed / completed / cancelled',
    --   pending(待确认) ──陪玩确认→ confirmed(已接单) ──陪玩完成→ completed(已完成)
    --       │                        │
    --       └──用户取消──────────────┘→ cancelled(已取消)
    `message`       TEXT NOT NULL,                    -- 下单留言
    `created_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 两个外键都指向 user 表
    FOREIGN KEY (`customer_id`)  REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`companion_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
