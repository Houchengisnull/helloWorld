[toc]

# 单实例安装

- **环境**
  - 操作系统	centos7
  - MySQL	mysql-5.7.9-linux-glibc2.5-x86_64.tar.gz

``` shell
cp /soft/mysql-5.7.9-linux-glibc2.5-x86_64.tar.gz /usr/local
cd /usr/local
tar -zxvf mysql-5.7.9-linux-glibc2.5-x86_64.tar.gz

groupadd mysql
useradd -r -g mysql mysql
# 下载依赖
# centos7默认已安装
# yum install libaio

# 创建软链接
ln -s mysql-5.7.9-linux-glibc2.5-x86_64.tar.gz mysql

cd mysql
mkdir mysql-files
chmod 770 mysql-files
chown -R mysql .
chgrp -R mysql .

mkdir data
# 2022-04-05T11:11:29.872062Z 1 [Note] A temporary password is generated for root@localhost: Fyniy;_P)6H2
# Fyniy;_P)6H2
bin/mysqld --initialize --user=mysql
bin/mysql_ssl_rsa_setup 
chown -R root .
chown -R mysql data mysql-files

# 启动
# 如果没有写日志的权限则删除默认生成的/etc/my.cnf
# 例如: mv my.cnf my.cnf.bak
bin/mysqld_safe --user=mysql &

########## 配置开机启动 ##########
cp support-files/mysql.server /etc/init.d/mysql.server
# 查看开机启动项
chkconfig --list
chkconfig mysql.server on

########## 配置环境变量 ##########
vi /etc/profile
# 在最后一行加上
export PATH=/usr/local/mysql/bin:$PATH
source /etc/profile


########## 配置MySQL ##########
# 登录MySQL
# 修改密码
mysql -uroot -p'Fyniy;_P)6H2'
set password='root';
flush privileges;
# 远程登录
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root';
flush privileges;
```

## 配置文件

- 加载顺序

  ``` shell 
  /usr/local/mysql/bin/mysqld --verbose --help |grep -A 1 'Default options’
  
  # 配置文件加载顺序
  Default options are read from the following files in the given order:
  /etc/my.cnf /etc/mysql/my.cnf /usr/local/mysql/etc/my.cnf ~/.my.cnf 
  ```

  在windows环境下配置文件为`my.ini`。

# 多实例安装

``` shell
########## 创建并修改/etc/my.cnf ########## 
cd /etc
touch my.cnf

vi my.cnf
[mysqld] 
sql_mode="STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION,NO_ZERO_DATE,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER" 

[mysqld_multi] 
mysqld=/usr/local/mysql/bin/mysqld_safe
mysqladmin=/usr/local/mysql/bin/mysqladmin
log=/var/log/mysqld_multi.log
# 若需要一键关闭多实例,则配置多实例用户名及密码
# user=root
# pass=root

[mysqld1]
# server-id仅限数字
server-id=11
socket=/tmp/mysql.sock1
port=3307
datadir=/data1 
user=mysql
performance_schema=off
innodb_buffer_pool_size=32M
skip_name_resolve=1
log_error=error.log
pid-file=/data1/mysql.pid1

[mysqld2] 
server-id=12
socket=/tmp/mysql.sock2
port=3308
datadir=/data2 
user=mysql
performance_schema=off
innodb_buffer_pool_size=32M
skip_name_resolve=1
log_error=error.log
pid-file=/data2/mysql.pid2 

########## 创建数据目录 ########## 
mkdir /data1
mkdir /data2
chown mysql.mysql /data{1..2}

# jU8n*eeGvq#;
mysqld --initialize --user=mysql --datadir=/data1
# 1m4V7Lehe&>/
mysqld --initialize --user=mysql --datadir=/data2

########## 配置开机启动 ##########
cp /usr/local/mysql/support-files/mysqld_multi.server /etc/init.d/mysqld_multid
chkconfig mysqld_multid on

########## 安装perl依赖 ##########
yum -y install perl perl-devel

########## mysqld_multi ##########
# 查看状态
mysqld_multi report
# 启动
mysqld_multi start

########## 修改密码 ##########
mysql -u root -S /tmp/mysql.sock1 -p -P3307 
mysql -u root -S /tmp/mysql.sock2 -p -P3308
set password = 'root';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root'; 
flush privileges;
```

## 开机启动

由于`mysqld_multi`脚本缺少对环境变量的引用，我们可以自定义脚本实现MySQL多实例开机启动。

``` shell
#!/bin/bash
# chkconfig: 2345 10 90
export PATH=/user/local/mysql/bin:$PATH
mysqld_multi start
```

## 关闭多实例

- 命令关闭

``` shell
mysqld_multi report -u root -p root
```

- 配置用户名/密码

``` shell
vi /etc/my.cnf

[mysqld_multi]
user=root
pass=root
```

