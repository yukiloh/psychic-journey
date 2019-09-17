### psychic-journey

我也不知道为什么起这个名字，系统给的


### 关于mysql

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


###Linux一些操作
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
