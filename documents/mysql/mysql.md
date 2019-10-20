[TOC]

# 数据类型

## text数据类型最大长度

| type     | size      |      |
| -------- | --------- | ---- |
| TINYTEXT | 256 bytes |      |
| TEXT | 65,535 bytes | ~64kb |
| MEDIUMTEXT | 16,777,215 bytes | ~16MB  |
| LONGTEXT | 4,294,967,295 bytes | ~4GB   |

# character与collation

`字符编码`与`比对方法`

``` mysq
show collation;
```

https://www.cnblogs.com/EasonJim/p/8128196.html

# MySQL乱码

## 注意

- `mysql`启动时的字符集,在`my.ini`文件中配置 `default-character-set=字符集` ,放在,[WinMySQLadmin]之上; 

- 数据库建表的过程中设置字符集,在`CREATE语句最后的 CHARSET=字符集` 语句中设置; 

- 在驱动url中的选项中设置字符集,`jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=字符集; `

- 在web显示页中设置显示的字符集, 
- `<%@ page contentType="text/html; charset=字符集" language="java" import="java.sql.*" errorPage="" %>`在servelt中则是`response.setContentType("text/html;charset=字符集");`

> 问题描述
>
> `Server`中显示正常，在将数据插入数据库后中文显示为??

## 解决

使用`注意`中第三点

https://www.jb51.net/article/147131.htm