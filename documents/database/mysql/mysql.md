[TOC]

# 国内镜像

- 搜狐

http://mirrors.sohu.com/mysql/MySQL-8.0/

# 数据类型

## int

- 显示长度

  ``` mysql
  create table test_int_n (a int(4) zeorfill);
  insert into test_int_n values(1);
  insert into test_int_n values(123456);
  ```

  其中`a int(4)`中的`4`是该字段的显示长度。

  例如插入1，则显示0001，以0补充。

  当存储数字超过n位时，按照实际存储的数字显示。

| type      | 字节 | 最小值 | 最大值 |
| --------- | ---- | ------ | ------ |
| tinyint   | 1    | -128   | 128    |
| smallint  | 2    |        |        |
| mediumint | 3    |        |        |
| int       | 4    |        |        |
| bigint    | 8    |        |        |

## char

| type    | 说明     | N的含义 | 是否有字符集 | 最大长度 |
| ------- | -------- | ------- | ------------ | -------- |
| char    | 定长字符 | 字符    | 是           | 255      |
| varchar | 变长字符 | 字符    | 是           | 16384    |

## text

| type     | size      |      |
| -------- | --------- | ---- |
| TINYTEXT | 256 bytes |      |
| TEXT | 65,535 bytes | ~64kb |
| MEDIUMTEXT | 16,777,215 bytes | ~16MB  |
| LONGTEXT | 4,294,967,295 bytes | ~4GB   |

## 时间类型

| type      | 占用空间 | 表示范围                                        |
| --------- | -------- | ----------------------------------------------- |
| datetime  | 8        | 1000-01-01 00:00:00 ~ 9999-12-31 23:59:59       |
| date      | 3        | 1000-01-01 ~ 9999-12-31                         |
| timestamp | 4        | 1970-01-01 00:00:00UTC ~ 2038-01-19 03:14:07UTC |
| year      | 1        | YEAR(2):1970-2070, YEAR(4):1901-2155            |
| time      | 3        | -838:59:59 ~ 838:59:59                          |

- datetime与timestamp的区别

  datetime没有时区概念，而timestamp则有时区概念

# character与collation

`字符编码`与`比对方法`

``` mysq
show collation;
```

- [MySQL中character set与collation的理解](https://www.cnblogs.com/EasonJim/p/8128196.html)

# FAQ

## MySQL乱码

### 注意

- `mysql`启动时的字符集,在`my.ini`文件中配置 `default-character-set=字符集` ,放在,[WinMySQLadmin]之上; 

- 数据库建表的过程中设置字符集,在`CREATE语句最后的 CHARSET=字符集` 语句中设置; 

- 在驱动url中的选项中设置字符集,`jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=字符集; `

- 在web显示页中设置显示的字符集, 
- `<%@ page contentType="text/html; charset=字符集" language="java" import="java.sql.*" errorPage="" %>`在servelt中则是`response.setContentType("text/html;charset=字符集");`

> 问题描述
>
> `Server`中显示正常，在将数据插入数据库后中文显示为??

### 解决

使用`注意`中第三点

- [mysql中插入表数据中文乱码问题的解决方法](https://www.jb51.net/article/147131.htm)

# 性能

首先，程序性能和硬件存在一定的正相关关系。

另外，在番禺某客户现场有张表，保存了4784869条记录。对该表增加字段耗费了1087秒。平均每秒更新4393条记录，每条记录耗时0.00022秒。

## 为什么不要问我DB极限QPS/TPS

- [为什么不要问我DB极限QPS/TPS](https://www.cnblogs.com/zhiqian-ali/p/6336521.html)
