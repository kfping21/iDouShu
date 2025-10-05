iDouShu 后端服务器
项目简介
iDouShu 是一个仿小红书风格的社交平台后端服务器，基于 Spring Boot 3.x 开发，提供完整的用户管理、内容发布、社交互动等功能。

技术栈
后端框架: Spring Boot 3.2.0

数据库: MySQL 8.0+

ORM框架: Spring Data JPA (Hibernate)

构建工具: Gradle

Java版本: 17+

功能特性
用户系统
用户注册、登录

个人资料管理（昵称、头像、简介等）

账号设置（私密账号、通知开关）

内容系统
帖子发布、编辑、删除

图片上传支持

内容浏览（用户帖子和全站时间线）

社交互动
点赞系统: 点赞、取消点赞、点赞统计

关注系统: 关注、取消关注、粉丝和关注列表

评论系统: 发表、编辑、删除评论

快速开始
环境要求
Java 17 或更高版本

MySQL 8.0 或更高版本

Gradle 7.x

数据库配置
创建 MySQL 数据库：

sql
CREATE DATABASE iDouShu CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
修改 application.properties 中的数据库连接信息：

properties
spring.datasource.url=jdbc:mysql://localhost:3306/iDouShu
spring.datasource.username=your_username
spring.datasource.password=your_password
运行项目
克隆项目到本地

配置数据库连接

运行项目：

bash
./gradlew bootRun
访问 http://localhost:8080 查看 API 文档

API 接口概览
用户相关
POST /api/users/register - 用户注册

POST /api/users/login - 用户登录

GET /api/users/profile - 获取个人资料

PUT /api/users/profile - 更新个人资料

PUT /api/users/settings - 更新账号设置

帖子相关
POST /api/posts/create - 发布帖子

GET /api/posts - 获取所有帖子（时间线）

GET /api/posts/user/{userId} - 获取用户帖子

GET /api/posts/{id} - 获取帖子详情

PUT /api/posts/{id} - 更新帖子

DELETE /api/posts/{id} - 删除帖子

点赞相关
POST /api/likes - 点赞帖子

DELETE /api/likes - 取消点赞

GET /api/likes/post/{postId} - 获取帖子点赞列表

GET /api/likes/user/{userId} - 获取用户点赞记录

关注相关
POST /api/follows - 关注用户

DELETE /api/follows - 取消关注

GET /api/follows/following/{userId} - 获取关注列表

GET /api/follows/followers/{userId} - 获取粉丝列表

评论相关
POST /api/comments - 发表评论

PUT /api/comments/{commentId} - 更新评论

DELETE /api/comments/{commentId} - 删除评论

GET /api/comments/post/{postId} - 获取帖子评论

数据库结构
主要数据表
users - 用户表

posts - 帖子表

likes - 点赞表

follows - 关注表

comments - 评论表

表关系
用户 ↔ 帖子：一对多

用户 ↔ 点赞：一对多

用户 ↔ 关注：多对多（通过关注表）

帖子 ↔ 点赞：一对多

帖子 ↔ 评论：一对多

测试数据
项目包含完整的测试数据：

5个测试用户（美食、旅行、时尚、科技博主）

15篇精选帖子

30个点赞记录

15个关注关系

30条评论记录

开发说明
项目结构
text
src/main/java/com/example/serveridoushu/
├── controller/     # 控制器层
├── service/        # 业务逻辑层
├── repository/     # 数据访问层
├── model/          # 实体类
├── dto/            # 数据传输对象
└── IdoushuApplication.java  # 主启动类
核心实体类
User - 用户实体

Post - 帖子实体

Like - 点赞实体

Follow - 关注实体

Comment - 评论实体

常见问题
1. 数据库连接失败
   检查 MySQL 服务是否启动

确认数据库连接信息是否正确

确保数据库已创建

2. 数据重复错误
   修改 application.properties 中的 spring.jpa.hibernate.ddl-auto=update

设置 spring.sql.init.mode=never

3. 端口占用
   修改 application.properties 中的 server.port

部署说明
生产环境配置
修改数据库连接为生产环境

设置 spring.jpa.hibernate.ddl-auto=update

关闭开发工具：spring.devtools.restart.enabled=false

打包部署
bash
./gradlew clean build
java -jar build/libs/serveridoushu-0.0.1-SNAPSHOT.jar
