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
  bin/mysqld_safe --user=mysql &
  ```

