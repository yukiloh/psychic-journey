-- 可能会遇到无法插入中文字符问题,可以尝试↓
-- alter table TABLE_NAME convert to character set utf8mb4 collate utf8mb4_bin;


-- auto-generated definition
CREATE TABLE ip_table
(
    id    INT AUTO_INCREMENT
        PRIMARY KEY,
    ip    VARCHAR(20) NULL,
    COUNT INT         NULL
);

-- auto-generated definition
-- updated：2019年10月17日
create table if not exists springboot_community_project.quest_table
(
	id int auto_increment
		primary key,
	title varchar(50) null,
	description mediumtext null,
	gmt_create varchar(30) null comment '使用datetime会导致出现毫秒，但是后端没有提供毫秒值所以会显示为0，最终前端接收到一个HH:MM:SS.0的数据',
	gmt_modified varchar(30) null,
	author_user_id int null,
	comment_count int null,
	view_count int null,
	like_count int null,
	tag varchar(256) null,
	author_name varchar(20) null
)
comment 'it should be ''question''' collate=utf8mb4_bin;



-- auto-generated definition
CREATE TABLE reply_table
(
    reply_id           INT AUTO_INCREMENT
        PRIMARY KEY,
    parent_id          INT          NOT NULL COMMENT '父类问题id',
    reply_description  VARCHAR(512) NULL,
    critic_id          INT          NOT NULL COMMENT '评论者的id',
    gmt_reply_create   DATETIME     NULL,
    gmt_reply_modified DATETIME     NULL
);

-- auto-generated definition
CREATE TABLE user_table
(
    id                INT AUTO_INCREMENT
        PRIMARY KEY,
    username          VARCHAR(20)  NULL,
    github_account_id INT          NULL,
    token             VARCHAR(50)  NULL,
    gmt_create        DATETIME     NULL COMMENT '本地创建时间',
    gmt_modified      DATETIME     NULL,
    gmt_last_login    DATETIME     NULL,
    avatar_url        VARCHAR(100) NULL
);

-- 用于储存上传图片的地址
create table if not exists springboot_community_project.question_img_table
(
	id int auto_increment
		primary key,
	question_id int null,
	question_addr varchar(100) null
);

-- 储存验证信息
create table if not exists springboot_community_project.verification_table
(
	id int auto_increment
		primary key,
	question varchar(100) null,
	answer varchar(50) null
)
charset=utf8;



