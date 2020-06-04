[TOC]

# mysqld --initialize

初始化

- `mysqld --initialize`创建随机密码root用户/`mysqld --initialize-insecure`创建无密码root用户
- 创建`data`文件夹

# mysqld --console

可用于在mysql server 发生错误时查看错误信息。有效排除错误。

# 最大连接数和修改最大连接数

<https://blog.csdn.net/wzygis/article/details/52461007>

# 接收数据包大小查看与设置

show VARIABLES like '%max_allowed_packet%’

> 设置方式1 : set global max_allowed_packet = #{max_allowed_packet};
> 设置方式2 : 修改配置文件 my.cfg

# 显示所有与mysql连接进程

show full processlist

# Err:1205 Lock wait timeout exceeded

<https://www.cnblogs.com/cchust/p/3585847.html>

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

show variables like 'log_%';
show variables like 'general%';

## 开启日志

set GLOBAL general_log = 'OFF'

## 查看二进制日志(用于恢复database)

show master log; show binary log;

## 查询慢查询属性

show VARIABLES LIKE '%slow%';
查询慢查询限制时间
show VARIABLES LIKE 'long_query_time';
开启慢查询日志
set GLOBAL slow_query_log = OFF;
查询执行时间95名之后statements
select * from sys.statements_with_runtimes_in_95th_percentile ORDER BY avg_latency desc;

# 性能查询

> SHOW　GLOBAL STATUS LIKE 'Questions';
> SHOW GLOBAL STATUS LIKE 'Com_select';
> SHOW GLOBAL STATUS LIKE 'Com_insert'; SHOW GLOBAL STATUS LIKE 'Com_delete'; SHOW GLOBAL STATUS LIKE 'Com_update'; SHOW GLOBAL STATUS LIKE 'Com_commit'; SHOW GLOBAL STATUS LIKE 'Com_rollback';

# 引擎

## show useful engine

> show engines;

## 显示表引擎

> show table status from #{database};

## 修改表引擎

``` 
ALTER TABLE #{tablename} ENGINE=InnoDB;
ALTER TABLE #{tablename} ENGINE=MEMORY;
```



# MySQL缓存

## 查询缓存开启

> show variables like '%query_cache_type%'

# 索引

## 查看表索引

> show keys from #{table}

## 查看索引

show index from #{table}