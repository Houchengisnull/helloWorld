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