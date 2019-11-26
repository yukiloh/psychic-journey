#### 项目概述
一款社交问答的网站，用户进行话题或问题的发起，与其他用户进行讨论；
注册方面接入GitHub的OAuth认证，亦可在本地进行账号注册；
用户登陆融合了Shiro进行用户的账号权限认证；
提供个人用户问题发起功能，附件上传，针对问题进行回复；


#### 部署环境
操作系统：家用NAS （QNAP，32位） //因被电信警告禁止开放web页面，已停用
虚拟化技术：Docker
数据库：MariaDB（5.5）
Java Version：11(Docker-OpenJDK)

#### 项目工具
开发工具：Intellij IDEA
项目构建：Maven
代码仓库：GitHub


#### 后台主要技术栈
核心框架：Spring Boot
视图框架：Spring MVC
页面引擎：Thymeleaf
ORM 框架：MyBatis
账号权限认证：Shiro
数据库缓存：Redis（4.0）


#### 前端主要技术栈
前端框架：JavaScript + JQuery
前端模板：Bootstrap


#### 模块规划
服务名称				服务说明
-Cloud
itoken-eureka			服务注册与发现
itoken-config			分布式配置中心
itoken-zipkin			分布式链路追踪
itoken-zuul			分布式路由网关
itoken-admin			分布式系统监控

-Service
itoken-service-admin		管理员服务提供者
itoken-service-redis		数据缓存服务提供者
itoken-service-sso		单点登录服务提供者
itoken-service-posts		文章服务提供者
itoken-service-upload		文件上传服务提供者
itoken-service-digiccy		数字货币服务提供者
itoken-service-collection	数据采集服务提供者

-Web
itoken-web-admin		管理员服务消费者
itoken-web-posts		文章服务消费者
itoken-web-backend		后台服务聚合
