### psychic-journey
我也不知道为什么起这个名字，系统给的


### 关于mysql

新增一个remote账号
create user onlyforremote@'%' identified by 'TAof2P1OBG24OG!P3iQi';
修改了密码
ALTER USER 'onlyforremote'@'%' IDENTIFIED WITH mysql_native_password BY '24OG!P3iQi';
赋予权限
grant all privileges on *.* to 'onlyforremote'@'%'  with grant option;


###Linux一些操作
克隆git项目
git clone https://....
初始化
mvn clean compile package

取回本地
git pull origin master
启动项目
mvn spirng-boot:run