## 数据库设计

### user（用户表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT PK | 主键 |
| username | VARCHAR(50) UNIQUE | 用户名 |
| password_hash | VARCHAR(255) | bcrypt 哈希 |
| nickname | VARCHAR(50) | 昵称 |
| avatar | VARCHAR(500) | 头像 URL |
| role | VARCHAR(20) | 角色: user / companion / admin |
| status | VARCHAR(20) | 状态: active / disabled |
| created_at | DATETIME | 注册时间 |
| updated_at | DATETIME | 更新时间 |

### companion_profile（陪玩信息表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT PK | 主键 |
| user_id | INT FK | 关联用户 |
| game_rank | VARCHAR(50) | 段位: 黑铁~赋能 |
| price_per_hour | DECIMAL(10,2) | 单价（元/小时） |
| description | TEXT | 个人介绍 |
| tags | VARCHAR(500) | 标签（逗号分隔） |
| status | VARCHAR(20) | online / offline |
| game_level | INT | 游戏等级 |
| win_rate | FLOAT | 胜率 |
| games_played | INT | 总场次 |
| created_at | DATETIME | 创建时间 |
| updated_at | DATETIME | 更新时间 |

### order（订单表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT PK | 主键 |
| customer_id | INT FK | 下单用户 |
| companion_id | INT FK | 接单陪玩 |
| hours | DECIMAL(10,1) | 时长（小时） |
| total_price | DECIMAL(10,2) | 总价 |
| status | VARCHAR(20) | pending / confirmed / completed / cancelled |
| message | TEXT | 留言 |
| created_at | DATETIME | 下单时间 |
| updated_at | DATETIME | 更新时间 |
