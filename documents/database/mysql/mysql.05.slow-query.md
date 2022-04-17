[toc]

# 慢查询

指执行时间超过`long_query_time`参数设定的时间阈值的SQL语句的日志。

默认情况下，日志是关闭的。

``` mysql
-- 慢查询是否启动
SHOW VARIABLES LIKE '%slow_query_log%';
-- 慢查询日志存储路径 默认与数据文件存放在一起
SHOW VARIABLES LIKE '%slow_query_log_file%';
-- 慢查询时间阈值
SHOW VARIABLES LIKE '%long_query_time%';
-- 是否记录未使用的索引
SHOW VARIABLES LIKE '%log_queries_not_using_indexes%';
-- 日志存放的地方[TABLE|FILE|FILE,TABLE]
SHOW VARIABLES LIKE '%log_output%';
```

配置慢查询后，它会记录符合条件的SQL:

- 查询语句
- 数据修改语句
- 回滚的语句

## 慢查询日志

``` shell
# 查看慢查询日志
cat /usr/local/mysql/data/mysql-slow.log
```

![image-20220417221814387](..\..\images\mysql\slow-query-log.png)

| **行号** | **内容**                        |
| -------- | ------------------------------- |
| 1        | 用户名 、用户的IP信息、线程ID号 |
| 2        | 执行花费的时间【单位：毫秒】    |
| 3        | 执行获得锁的时间                |
| 4        | 获得的结果行数                  |
| 5        | 扫描的数据行数                  |
| 6        | 这SQL执行的具体时间             |
| 7        | 具体的SQL语句                   |

## 分析

### 慢查询分析工具

- mysqldumpslow
- pt_query_digest

#### mysqldumpslow

``` shell
mysqldumpslow -s r -t 10 slow-mysql.log

# -s [c|t|l|r|at|al|ar]
# 排序
# c: 总次数
# t: 总时间
# l: 锁的时间
# r: 总数据行
# at,al,ar :[t|l|r]/c 即平均数

# -t top 指定取前几天作为结果输出
```

#### pt_query_digest

``` shell
# 安装
yum -y install 'perl(Data::Dumper)'
yum -y install perl-Digest-MD5 
yum -y install perl-DBI 
yum -y install perl-DBD-MySQL

# 分析
pt-query-digest  --explain h=127.0.0.1, u=root,p=password slow-mysql.log
```

