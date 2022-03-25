[TOC]

# JDBC连接DB2

## Z/OS

**依赖包**

- `db2jcc4.jar`
- `db2jcc_license_cisuz.jar`

## LUW

**依赖包**

- `db2jcc.jar`

# 事务日志已满

## 清理日志

1、首先执行命令`db2 get db cfg for MICRO_11`查看事务日志地址

2、将事务日志目录下日志删除

## 增大事务日志

`db2 update db cfg for MICRO_11using LOGFILSIZ 8192`

## 增大事务日志文件个数

`db2 update db cfg for MICRO_11 using LOGPRIMARY 15`

## 增大辅助日志个数

`db2 update db cfg for MICRO_11using LOGSECOND 10`

## 重启数据库

``` shell
db2 force applications all
// 应用程序句柄 通过db2 list applications获取
db2 force application #{应用程序句柄}
db2stop
db2start
```

## 参考

- [db2事务日志已满解决办法](https://www.cnblogs.com/revo/p/8817758.html)