#!/bin/bash

HOSTNAME="localhost"
PORT="3306"
NAME="root"
PASSWORD=$1
DATABASE="blog"

# echo "connect mysql"
#mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} -e ""

echo "create database"
dropCreateData="DROP DATABASE IF EXISTS ${DATABASE};"
createStr="CREATE DATABASE IF NOT EXISTS ${DATABASE} DEFAULT CHARACTER SET utf8;"
#mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} -e "${dropCreateData}"
mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} -e "${createStr}"

# 分组（角色）
echo "create table role"
tableUser="create table IF NOT EXISTS role (
    id int(11) not null AUTO_INCREMENT,
    name varchar(255) default null,
    parent int(11) default null,
    createTime datetime default null,
    desction varchar(255) default null,
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"


mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

#权限
echo "create table permission"
tableUser="create table IF NOT EXISTS permission (
    id int(11) not null AUTO_INCREMENT,
    name varchar(255) default null,
    desction varchar(255) default null,
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"


#用户
echo "create table user"
tableUser="create table IF NOT EXISTS user (
    id int(11) not null AUTO_INCREMENT,
    name varchar(255) default null,
    password varchar(255) default null,
    salt varchar(255) default null,
    nickname varchar(255) default null,
    createTime datetime default null,
    lastloginTime datetime default null,
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

#用户-角色
echo "create table user_role"
tableUser="create table IF NOT EXISTS user_role (
    id int(11) not null AUTO_INCREMENT,
    userId int(11) not null,
	groupId int(11) not null,	
    PRIMARY KEY(id),
    CONSTRAINT fk_user_role_user foreign key(userId) references user (id),
    CONSTRAINT fk_user_role_role foreign key(groupId) references role (id)
)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

#角色-权限
echo "create table role_permission"
tableUser="create table IF NOT EXISTS role_permission (
    id int(11) not null AUTO_INCREMENT,
    permissionId int(11) not null,
	groupId int(11) not null,	
    PRIMARY KEY(id),
    CONSTRAINT fk_role_permission_permission foreign key(permissionId) references permission (id),
    CONSTRAINT fk_role_permission_role foreign key(groupId) references role (id)
)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"


#标签
echo "create table tags"
tableUser="create table IF NOT EXISTS tags (
    id int(11) not null AUTO_INCREMENT,
    name varchar(255) default null,
    desction varchar(255) default null,
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"


#分类
echo "create table category"
tableUser="create table IF NOT EXISTS category (
    id int(11) not null AUTO_INCREMENT,
    name varchar(255) default null,
    desction varchar(255) default null,
    PRIMARY KEY(id)
)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"



#文章
echo "create table article"
tableUser="create table IF NOT EXISTS article (
    id int(11) not null AUTO_INCREMENT,
    userId int(11) not null,
    categoryId int(11) not null,
	title varchar(255) default null,
	author varchar(255) default null,
	content varchar(4000) default null,
	contentMd varchar(4000) default null,
	isMarkdown tinyint default 0,
	topImg varchar(255) default null,
	createTime datetime default null,
	updateTime datetime default null,
    PRIMARY KEY(id),
    CONSTRAINT fk_article_user foreign key(userId) references user (id),
    CONSTRAINT fk_article_category foreign key(categoryId) references category (id)

)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

#文章-标签
echo "create table article_tags"
tableUser="create table IF NOT EXISTS article_tags (
    id int(11) not null AUTO_INCREMENT,
    articleId int(11) default null,
    tagId int(11) default null,
    PRIMARY KEY(id),
    CONSTRAINT fk_article_tags_article foreign key(articleId) references article (id),
    CONSTRAINT fk_article_tags_tags foreign key(tagId) references tags (id)

)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

#评论
echo "create table comment"
tableUser="create table IF NOT EXISTS comment (
    id int(11) not null AUTO_INCREMENT,
    userId int(11) default null,
    nickname varchar(255) default null,
    articleId int(11) not null,
	content varchar(4000) default null,
	createTime datetime default null,
	updateTime datetime default null,
	status varchar(255) default null,
	ip varchar(255) default null,
    PRIMARY KEY(id),
    CONSTRAINT fk_comment_user foreign key(userId) references user (id),
    CONSTRAINT fk_comment_article foreign key(articleId) references article (id)

)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"

#浏览记录
echo "create table article_look"
tableUser="create table IF NOT EXISTS article_look (
    id int(11) not null AUTO_INCREMENT,
    userId int(11) default null,
    articleId int(11) not null,
	createTime datetime default null,
	ip varchar(255) default null,
    PRIMARY KEY(id),
    CONSTRAINT fk_article_look_user foreign key(userId) references user (id),
    CONSTRAINT fk_article_look_article foreign key(articleId) references article (id)

)ENGINE=InnoDB default charset=utf8;"

mysql -h ${HOSTNAME} -P ${PORT} -u ${NAME} -p${PASSWORD} ${DATABASE} -e "${tableUser}"
read -p "Press any key to continue." var