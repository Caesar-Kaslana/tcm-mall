CREATE DATABASE IF NOT EXISTS tcm_mall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE tcm_mall;

-- 用户表
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    status TINYINT DEFAULT 1 COMMENT '状态 0禁用 1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    code VARCHAR(50) NOT NULL COMMENT '角色编码',
    description VARCHAR(255) DEFAULT NULL COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 权限表
CREATE TABLE permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '权限名称',
    code VARCHAR(50) NOT NULL COMMENT '权限编码',
    url VARCHAR(100) DEFAULT NULL COMMENT '请求URL',
    type TINYINT DEFAULT 1 COMMENT '类型 1菜单 2按钮',
    parent_id BIGINT DEFAULT 0 COMMENT '父ID',
    sort INT DEFAULT 0 COMMENT '排序',
    icon VARCHAR(50) DEFAULT NULL COMMENT '图标',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE role_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 商品分类表
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID 0为一级',
    sort INT DEFAULT 0 COMMENT '排序',
    icon VARCHAR(100) DEFAULT NULL COMMENT '图标',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE goods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    category_name VARCHAR(50) DEFAULT NULL COMMENT '分类名称',
    cover VARCHAR(255) DEFAULT NULL COMMENT '封面图',
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '售价',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存',
    sales INT DEFAULT 0 COMMENT '销量',
    description TEXT COMMENT '商品描述',
    effect TEXT COMMENT '中医功效',
    `usage` TEXT COMMENT '用法用量',
    contraindication TEXT COMMENT '禁忌',
    status TINYINT DEFAULT 1 COMMENT '状态 0下架 1上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 购物车表
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    goods_name VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
    goods_cover VARCHAR(255) DEFAULT NULL COMMENT '商品封面',
    goods_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品单价',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 订单表
CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    receiver_name VARCHAR(50) DEFAULT NULL COMMENT '收货人姓名',
    receiver_phone VARCHAR(20) DEFAULT NULL COMMENT '收货人电话',
    receiver_address VARCHAR(255) DEFAULT NULL COMMENT '收货地址',
    total_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '订单总金额',
    status TINYINT DEFAULT 0 COMMENT '状态 0待付款 1待发货 2待收货 3已完成 4已取消',
    pay_time DATETIME DEFAULT NULL COMMENT '支付时间',
    ship_time DATETIME DEFAULT NULL COMMENT '发货时间',
    receive_time DATETIME DEFAULT NULL COMMENT '收货时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单详情表
CREATE TABLE order_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    goods_name VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
    goods_cover VARCHAR(255) DEFAULT NULL COMMENT '商品封面',
    goods_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品单价',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    subtotal DECIMAL(12,2) DEFAULT 0.00 COMMENT '小计金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- 采购入库表
CREATE TABLE purchase (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_no VARCHAR(50) NOT NULL UNIQUE COMMENT '入库单号',
    goods_id BIGINT NOT NULL COMMENT '商品ID',
    goods_name VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
    quantity INT NOT NULL DEFAULT 0 COMMENT '入库数量',
    unit_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '采购单价',
    total_price DECIMAL(12,2) DEFAULT 0.00 COMMENT '采购总价',
    supplier VARCHAR(100) DEFAULT NULL COMMENT '供应商',
    remark VARCHAR(255) DEFAULT NULL COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购入库表';

-- 收货地址表
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    receiver_name VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    receiver_phone VARCHAR(20) NOT NULL COMMENT '收货人电话',
    province VARCHAR(50) DEFAULT NULL COMMENT '省',
    city VARCHAR(50) DEFAULT NULL COMMENT '市',
    district VARCHAR(50) DEFAULT NULL COMMENT '区',
    detail_address VARCHAR(255) NOT NULL COMMENT '详细地址',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认 0否 1是',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- 轮播图表
CREATE TABLE banner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) DEFAULT NULL COMMENT '标题',
    image VARCHAR(255) DEFAULT NULL COMMENT '图片URL',
    link_url VARCHAR(255) DEFAULT NULL COMMENT '链接地址',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 公告表
CREATE TABLE notice (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- 插入角色数据
INSERT INTO role (name, code, description, status) VALUES
('系统管理员', 'admin', '拥有所有权限', 1),
('商品管理员', 'goods_admin', '管理商品和进销存', 1),
('订单管理员', 'order_admin', '管理订单', 1),
('普通用户', 'user', '普通用户', 1);

-- 插入权限数据
INSERT INTO permission (name, code, url, type, parent_id, sort, icon, status) VALUES
('后台管理', 'admin', '', 1, 0, 1, 'Setting', 1),
('仪表盘', 'dashboard', 'dashboard', 1, 1, 1, 'Odometer', 1),
('用户管理', 'user', 'user', 1, 1, 2, 'User', 1),
('角色管理', 'role', 'role', 1, 1, 3, 'UserFilled', 1),
('权限管理', 'permission', 'permission', 1, 1, 4, 'Lock', 1),
('商品分类', 'category', 'category', 1, 1, 5, 'Grid', 1),
('商品管理', 'goods', 'goods', 1, 1, 6, 'Goods', 1),
('订单管理', 'order', 'order', 1, 1, 7, 'List', 1),
('进销存管理', 'purchase', 'purchase', 1, 1, 8, 'Box', 1),
('轮播图管理', 'banner', 'banner', 1, 1, 9, 'Picture', 1),
('公告管理', 'notice', 'notice', 1, 1, 10, 'Bell', 1);

-- 给系统管理员分配所有权限
INSERT INTO role_permission (role_id, permission_id)
SELECT 1, id FROM permission WHERE status = 1;

-- 给商品管理员分配商品相关权限
INSERT INTO role_permission (role_id, permission_id) VALUES
(2, 1), (2, 5), (2, 6), (2, 7), (2, 9);

-- 给订单管理员分配订单相关权限
INSERT INTO role_permission (role_id, permission_id) VALUES
(3, 1), (3, 2), (3, 7), (3, 8);

-- 给普通用户分配基础权限
INSERT INTO role_permission (role_id, permission_id) VALUES
(4, 1);

-- 插入测试账号 (密码均为123456的MD5值)
INSERT INTO user (username, password, nickname, phone, email, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800138000', 'admin@tcmmall.com', 1),
('goodsadmin', 'e10adc3949ba59abbe56e057f20f883e', '商品管理员', '13800138001', 'goods@tcmmall.com', 1),
('orderadmin', 'e10adc3949ba59abbe56e057f20f883e', '订单管理员', '13800138002', 'order@tcmmall.com', 1),
('user', 'e10adc3949ba59abbe56e057f20f883e', '普通用户', '13800138003', 'user@tcmmall.com', 1);

-- 分配用户角色
INSERT INTO user_role (user_id, role_id) VALUES
(1, 1), (2, 2), (3, 3), (4, 4);

-- 插入商品分类
INSERT INTO category (name, parent_id, sort, status) VALUES
('中药材', 0, 1, 1),
('养生器材', 0, 2, 1),
('药食同源', 0, 3, 1),
('人参', 1, 1, 1),
('黄芪', 1, 2, 1),
('当归', 1, 3, 1),
('枸杞', 1, 4, 1),
('艾灸仪器', 2, 1, 1),
('按摩器材', 2, 2, 1),
('养生茶', 3, 1, 1),
('滋补汤料', 3, 2, 1);

-- 插入商品数据
INSERT INTO goods (name, category_id, category_name, cover, price, stock, sales, description, effect, `usage`, contraindication, status) VALUES
('长白山野山参', 4, '人参', 'https://picsum.photos/300/300?random=1', 299.00, 100, 15, '精选长白山野山参，生长年限15年以上，参体完整，芦头粗壮。', '大补元气，复脉固脱，补脾益肺，生津养血，安神益智。', '每日3-9克，可炖服、嚼食、磨粉、泡酒或泡茶。', '实热证、湿热证及正气不虚者慎用。', 1),
('特级黄芪片', 5, '黄芪', 'https://picsum.photos/300/300?random=2', 68.00, 200, 42, '内蒙古特级黄芪片，切片均匀，色泽金黄，豆香味浓。', '补气升阳，固表止汗，利水消肿，生津养血。', '每日9-30克，煎汤内服，或入丸散。', '表实邪盛、气滞湿阻、食积停滞、阴虚阳亢者慎用。', 1),
('甘肃当归头片', 6, '当归', 'https://picsum.photos/300/300?random=3', 88.00, 150, 28, '甘肃岷县当归头片，油性充足，香气浓郁。', '补血活血，调经止痛，润肠通便。', '每日6-12克，煎汤内服，或入丸散，也可泡酒。', '湿盛中满、大便溏泄者忌服。', 1),
('宁夏特级枸杞', 7, '枸杞', 'https://picsum.photos/300/300?random=4', 45.00, 300, 65, '宁夏中宁特级枸杞，颗粒饱满，色泽暗红，甘甜可口。', '滋补肝肾，益精明目。', '每日6-12克，泡茶、煮粥、煲汤或直接嚼食。', '外感实热、脾虚泄泻者慎服。', 1),
('智能艾灸仪', 8, '艾灸仪器', 'https://picsum.photos/300/300?random=5', 399.00, 50, 12, '智能控温艾灸仪，无烟设计，多档位温度调节。', '温经散寒，行气通络，扶阳固脱，升阳举陷。', '每次15-30分钟，对准穴位进行艾灸。', '高热、昏迷、极度疲劳、皮肤破损处不宜使用。', 1),
('颈椎按摩器', 9, '按摩器材', 'https://picsum.photos/300/300?random=6', 199.00, 80, 23, '多功能颈椎按摩器，仿真人手按摩，热敷功能。', '舒筋活络，缓解肌肉疲劳，促进血液循环。', '每日使用15-20分钟，力度适中。', '颈部外伤、肿瘤、感染部位禁用。', 1),
('菊花枸杞养生茶', 10, '养生茶', 'https://picsum.photos/300/300?random=7', 35.00, 250, 55, '菊花枸杞养生茶，清肝明目，适合长期用眼人群。', '散风清热，平肝明目，清热解毒。', '每日1-2包，开水冲泡5-10分钟后饮用。', '脾胃虚寒者不宜多饮。', 1),
('四物汤料包', 11, '滋补汤料', 'https://picsum.photos/300/300?random=8', 58.00, 120, 18, '经典四物汤料包，包含当归、川芎、白芍、熟地。', '补血和血，调经化瘀。', '每周2-3次，加水煎煮30-40分钟后饮用。', '阴虚发热、血崩气脱者禁用。', 1);

-- 插入轮播图
INSERT INTO banner (title, image, link_url, sort, status) VALUES
('中医养生节', 'https://picsum.photos/1200/400?random=10', '/goods', 1, 1),
('新品尝鲜', 'https://picsum.photos/1200/400?random=11', '/goods', 2, 1),
('艾灸特惠', 'https://picsum.photos/1200/400?random=12', '/goods', 3, 1);

-- 插入公告
INSERT INTO notice (title, content, sort, status) VALUES
('关于中医商城系统上线的通知', '中医商城管理系统正式上线，欢迎广大用户选购优质中医产品。', 1, 1),
('春季养生指南', '春季养肝正当时，推荐枸杞、菊花等养肝明目产品。', 2, 1),
('全场满199包邮', '活动期间全场订单满199元即可享受包邮服务。', 3, 1);

-- 给普通用户插入收货地址
INSERT INTO address (user_id, receiver_name, receiver_phone, province, city, district, detail_address, is_default) VALUES
(4, '张三', '13800138003', '北京市', '北京市', '朝阳区', '建国路88号SOHO现代城1号楼', 1);
