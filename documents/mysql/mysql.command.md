[TOC]

# 安装

- **参考**
- [Windows下安装MySQL详细教程](https://www.jianshu.com/p/e676799ed3af)

## mysqld --initialize

- **初始化**

``` mysql
# 生成随机密码 root用户 的初始化
mysqld --initialize
# 生成空密码 root用户 的初始化
mysqld --initialize-insecure
```

初始化将创建`root`用户及`data`文件夹

## mysqld --console

可用于在mysql server 发生错误时查看错误信息。有效排除错误。

## mysqld --install

- 将`mysql`注册为`Windows`服务：

``` mysql
mysqld --install %server_name%
```

- 卸载`mysql`服务

``` mysql
mysqld --uninstall %server_name%
```

## 启动

- windows

``` bash
# 启动mysql服务
net start mysql

# 关闭mysql服务
net stop mysql 
```

- linux

``` shell
# 5.0
service mysqld start
# 5.7
service mysql start
```

## Faq

### 缺少环境组件redistrubutable

- 报错信息

  `This application requires Visual Studio 2019 Redistributable. Please install the Redistributable then run this installer again.`

`mysql`运行在`VC++`环境下，而`redistrubutable`为`VC++`环境必要组件。

- 参考

- [mysql8安装This application requires Visual Studio 2019 Redistributable问题及连接navicat时1251问题的解决](https://blog.csdn.net/mengjie0617/article/details/105148847/)

- `redistrubutable`下载地址

  [The latest supported Visual C++ downloads](https://support.microsoft.com/en-us/topic/the-latest-supported-visual-c-downloads-2647da03-1eea-4433-9aff-95f26a218cc0)

### MySQL8.0安装

在安装`MySQL8.0`时，没有`MySQL5.7`那样简单，只要点击`next`就ok。

需要先执行`mysqld --initialize`初始化，再执行`net start mysql`来启动`MySQL`服务。

- **参考**
- [mysql8.0.20安装配置教程](https://www.cnblogs.com/2020javamianshibaodian/p/12906655.html)

# 查看版本

``` mysql
SELECT version();
```

# 用户

## 查询密码

## 修改密码

进入`mysql`客户端后，输入：

``` mysql
alter user 'root'@'localhost' identified by 'new_password'
```

或者使用`mysqladmin`程序修改：

``` mysql
mysqladmin -u root -p password newPassword
```

# 开启IP限制

## 5.6版本

- [设置mysql允许外部IP连接的解决方法及遇到的坑说明](https://blog.csdn.net/weixin_42392874/article/details/80584624)

- **授权**

  ``` mysql
  GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;
  ```

  以上第一个`root`是用户名，第二个`root`是密码。

- **刷新权限**

  ``` mysql
  flush privileges;
  ```

- 查询`user`表检查是否修改成功

  ```` mysql
  use mysql;
  select user, host
  from user;
  ````


## 8.0版本

- [is not allowed to connect to this mysql server](https://blog.csdn.net/iiiiiilikangshuai/article/details/100905996)

  ```  mysql
  use mysql;
  
  -- 检查用户关联host
  select host from user where user='root';
  
  -- 允许所有ip登录
  update user set host='%' where user='root';
  
  -- 刷新配置
  flush privileges;
  ```

  

# 最大连接数和修改最大连接数

[MYSQL 查看最大连接数和修改最大连接数](https://blog.csdn.net/wzygis/article/details/52461007)

# 接收数据包大小查看与设置

show VARIABLES like '%max_allowed_packet%’

> 设置方式1 : set global max_allowed_packet = #{max_allowed_packet};
> 设置方式2 : 修改配置文件 my.cfg

# 显示所有与mysql连接进程

`show full processlist`

# Err:1205 Lock wait timeout exceeded

- [Mysql Error:1205错误诊断](https://www.cnblogs.com/cchust/p/3585847.html)

## 普通方式

  show full processlist;
  kill #{pid}

## 升级1

```
/* 显示当前事务 */
select * from information_schema.INNODB_trx; 
select * from information_schema.INNODB_locks;
/* 查询锁等待情况 */
select * from information_schema.INNODB_locks_waits;
```

  kill #{pid};

## 升级2

```
SELECT r.trx_id waiting_trx_id,
       r.trx_query waiting_query,
       b.trx_id blocking_trx_id,
       b.trx_query blocking_query,
       b.trx_mysql_thread_id blocking_thread,
       b.trx_started,
       b.trx_wait_started
FROM information_schema.innodb_lock_waits w
       INNER JOIN information_schema.innodb_trx b
               ON b.trx_id = w.blocking_trx_id
       INNER JOIN information_schema.innodb_trx r
               ON r.trx_id = w.requesting_trx_id
```

# 日志

## 查看日志

``` bash
show variables like 'log_%';
show variables like 'general%';
```

## 开启日志

``` bash
set GLOBAL general_log = 'OFF'
```

## 查看二进制日志(用于恢复database)

``` bash
show master log; show binary log;
```

## 查询慢查询属性

``` bash
show VARIABLES LIKE '%slow%';
```

- **查询慢查询限制时间**

``` bash
show VARIABLES LIKE 'long_query_time';
```

- **开启慢查询日志**

``` bash
set GLOBAL slow_query_log = OFF;
```

- **查询执行时间95名之后statements**

``` sql
select * 
from sys.statements_with_runtimes_in_95th_percentile 
ORDER BY avg_latency desc;
```

# 性能查询

``` bash
SHOW　GLOBAL STATUS LIKE 'Questions';
SHOW GLOBAL STATUS LIKE 'Com_select';
SHOW GLOBAL STATUS LIKE 'Com_insert'; SHOW GLOBAL STATUS LIKE 'Com_delete'; SHOW GLOBAL STATUS LIKE 'Com_update'; SHOW GLOBAL STATUS LIKE 'Com_commit'; SHOW GLOBAL STATUS LIKE 'Com_rollback';
```

# 引擎

## show useful engine

``` mysql
show engines;
```

## 显示表引擎

``` sql
show table status from #{database};
```

## 修改表引擎

``` sql
ALTER TABLE #{tablename} ENGINE=InnoDB;
ALTER TABLE #{tablename} ENGINE=MEMORY;
```

# MySQL缓存

## 查询缓存开启

``` mysql
show variables like '%query_cache_type%'
```

# 索引

## 查看表索引

``` mysql
show keys from #{table}
```

## 查看索引

``` mysql
show index from #{table}
```

