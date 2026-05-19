# 中医商城管理系统 (TCM Mall)

基于 SSM 框架 + Vue 3 的前后端分离中医电商平台，集成 AI 智能问答助手。

## 功能概览

### 前台商城
- 用户注册/登录
- 商品浏览、搜索、分类筛选
- 购物车管理
- 订单创建、支付、收货、取消
- 收货地址管理
- 个人中心

### 后台管理
- 仪表盘数据概览
- 用户管理 + 角色分配
- 角色管理 + 权限分配（RBAC）
- 商品管理 + 商品分类
- 订单管理（发货、完成）
- 采购入库（进销存）
- 轮播图管理
- 公告管理

### AI 智能助手
- 全局浮动按钮，点击即开
- 中医知识问答（功效、禁忌、养生建议等）
- 商城使用帮助
- 对话历史本地保存

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring 5.3.37 + Spring MVC 5.3.37 + MyBatis 3.5.16 |
| 数据库 | MySQL 8.0 + Druid 连接池 |
| 前端 | Vue 3 + Element Plus + Axios + Pinia + Vue Router |
| 构建工具 | Maven (后端) + Vite (前端) |
| AI | DeepSeek API (OpenAI 兼容格式) |
| 运行环境 | JDK 17 + Tomcat 9.0 + Node.js 18+ |

## 项目结构

```
tcm-mall/
├── tcm-mall-backend/           # 后端 SSM 项目
│   └── src/main/
│       ├── java/com/tcmmall/
│       │   ├── controller/     # Spring MVC 控制器
│       │   ├── service/        # 业务逻辑层
│       │   ├── mapper/         # MyBatis 数据访问层
│       │   ├── entity/         # 实体类
│       │   ├── interceptor/    # 登录/权限拦截器
│       │   ├── util/           # 工具类
│       │   └── vo/             # 响应值对象
│       ├── resources/
│       │   ├── mapper/         # MyBatis XML 映射文件
│       │   ├── applicationContext.xml  # Spring 配置
│       │   ├── spring-mvc.xml          # Spring MVC 配置
│       │   ├── db.properties.example   # 数据库配置模板
│       │   └── ai.properties.example   # AI 配置模板
│       └── webapp/WEB-INF/
│           └── web.xml         # Servlet 配置
├── tcm-mall-frontend/          # 前端 Vue 3 项目
│   └── src/
│       ├── api/                # API 接口封装
│       ├── components/         # 公共组件（含 AiFloat）
│       ├── views/
│       │   ├── front/          # 前台页面
│       │   └── admin/          # 后台页面
│       ├── router/             # 路由配置
│       ├── store/              # Pinia 状态管理
│       └── utils/              # 工具（Axios 实例）
└── tcm-mall-sql/
    └── tcm_mall.sql            # 数据库初始化脚本
```

## 环境要求

| 软件 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | 推荐 17.0.10 |
| Tomcat | 9.0.x | 推荐 9.0.87+ |
| MySQL | 8.0.x | 需开启 utf8mb4 |
| Node.js | 18+ | 推荐 18.18 LTS |
| Maven | 3.6+ | |

## 快速部署

### 1. 克隆仓库

```bash
git clone <your-repo-url>
cd tcm-mall
```

### 2. 初始化数据库

打开 MySQL，执行以下操作：

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS tcm_mall
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

-- 导入表结构和初始数据
-- 方法 A：命令行
mysql -u root -p tcm_mall < tcm-mall-sql/tcm_mall.sql

-- 方法 B：MySQL Workbench / Navicat → 打开 tcm_mall.sql → 执行
```

### 3. 配置密钥

```bash
cd tcm-mall-backend/src/main/resources

# 创建 secrets.properties，写入你的真实密码和 API Key
cat > secrets.properties << 'EOF'
# MySQL 密码
jdbc.password=你的MySQL密码

# DeepSeek API Key (可选，不影响项目运行)
deepseek.api.key=你的DeepSeek_API_Key
EOF
```

> 如果尚未配置 DeepSeek API Key，AI 浮标仍会显示，但发送消息会提示服务不可用。可在 [platform.deepseek.com](https://platform.deepseek.com) 注册获取。

### 4. 构建并启动后端

```bash
# 编译打包
cd tcm-mall-backend
mvn clean package -DskipTests

# 将生成的 target/tcm-mall.war 部署到 Tomcat
# 方式 A：IDEA → 配置 Tomcat 9 → 部署 war exploded
# 方式 B：手动复制到 Tomcat webapps 目录
cp target/tcm-mall.war /path/to/tomcat/webapps/

# 启动 Tomcat
/path/to/tomcat/bin/startup.sh    # Linux/Mac
/path/to/tomcat/bin/startup.bat   # Windows
```

后端启动后访问 `http://localhost:8080/tcm-mall/api/goods/list` 验证。

### 5. 启动前端

```bash
cd tcm-mall-frontend

# 安装依赖（首次）
npm install

# 启动开发服务器
npm run dev
```

浏览器打开 `http://localhost:5173` 即可访问。

> 前端开发服务器已配置代理，`/api` 请求自动转发到 `http://localhost:8080/tcm-mall`。
> 如需修改后端地址，编辑 `vite.config.js` 中的 `proxy.target`。

## 测试账号

| 用户名 | 密码 | 角色 | 权限 |
|--------|------|------|------|
| admin | 123456 | 系统管理员 | 全部权限 |
| goodsadmin | 123456 | 商品管理员 | 商品、分类、订单、进销存 |
| orderadmin | 123456 | 订单管理员 | 仪表盘、订单、进销存 |
| user | 123456 | 普通用户 | 前台购物 |


## 常见问题

**Q: 启动 Tomcat 后访问 404？**
A: 检查 WAR 是否成功部署到 `webapps/` 目录，Tomcat 日志有无错误。

**Q: 前端请求后端接口报 CORS 错误？**
A: 确认后端 `spring-mvc.xml` 中的 `allowed-origins` 与前端地址一致。

**Q: 注册后无法登录？**
A: 确认 `db.properties` 中的数据库密码正确，且数据库中存在角色数据（重新执行 `tcm_mall.sql`）。

**Q: AI 助手无响应？**
A: 检查 `ai.properties` 中的 API Key 是否有效，以及网络是否能访问 `api.deepseek.com`。

## License

本项目仅供学习交流使用。