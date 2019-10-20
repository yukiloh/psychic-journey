基于springboot、bootstrap、thymeleaf、mysql/mariaDB、redis开发的一个简单论坛项目；





###Linux上关于复制项目的操作
克隆git项目

git clone https://....

初始化

mvn clean compile package

取回本地

git pull origin master

启动项目

mvn spring-boot:run

###移至其他项目需要更改的内容

application.yml中，数据库信息和github登陆信息

messages.properties中，用于login.html登陆github认证的重定向url




### ----以下弃用----
### 关于远程服务器上得mysql(2019年10月6日-已禁用)

新增一个remote账号

create user onlyforremote@'%' identified by 'TAof2P1OBG24OG!P3iQi';

修改了密码

ALTER USER 'onlyforremote'@'%' IDENTIFIED WITH mysql_native_password BY '24OG!P3iQi';

赋予权限

grant all privileges on *.* to 'onlyforremote'@'%'  with grant option;

数据库配置配置文件

vi /etc/mysql/my.cnf

重启服务

service mysql restart

###4个SQL的语句(已移至sql包内)

ip_table:

create table ip_table
(
    id    int auto_increment
        primary key,
    ip    varchar(20) null,
    count int         null
);

quest_table:

create table ip_table
(
    id    int auto_increment
        primary key,
    ip    varchar(20) null,
    count int         null
);

reply_table:

create table reply_table
(
    reply_id           int auto_increment
        primary key,
    parent_id          int          not null comment '父类问题id',
    reply_description  varchar(512) null,
    critic_id          int          not null comment '评论者的id',
    gmt_reply_create   datetime     null,
    gmt_reply_modified datetime     null
);

user_table:

create table user_table
(
    id                int auto_increment
        primary key,
    username          varchar(20)  null,
    github_account_id int          null,
    token             varchar(50)  null,
    gmt_create        datetime     null comment '本地创建时间',
    gmt_modified      datetime     null,
    gmt_last_login    datetime     null,
    avatar_url        varchar(100) null
);


