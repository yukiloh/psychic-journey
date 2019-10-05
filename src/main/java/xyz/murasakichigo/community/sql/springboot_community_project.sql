-- auto-generated definition
CREATE TABLE ip_table
(
    id    INT AUTO_INCREMENT
        PRIMARY KEY,
    ip    VARCHAR(20) NULL,
    COUNT INT         NULL
);

-- auto-generated definition
CREATE TABLE quest_table
(
    id             INT AUTO_INCREMENT
        PRIMARY KEY,
    title          VARCHAR(50)  NULL,
    description    TEXT         NULL,
    gmt_create     DATETIME     NULL,
    gmt_modified   DATETIME     NULL,
    author_user_id INT          NULL,
    comment_count  INT          NULL,
    view_count     INT          NULL,
    like_count     INT          NULL,
    tag            VARCHAR(256) NULL,
    author_name    VARCHAR(20)  NULL
);

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

