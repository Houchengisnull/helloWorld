[toc]

# DDL

## 表

- 创建

``` sql
create table tablename (
	"id" NUMBER(11) primary key ,
    "name" VARCHAR2(11 CHAR),
    "is_enable" NUMBER(1) DEFAULT(1)
);
```

## 序列

- 创建序列

``` sql
CREATE SEQUENCE seq_name
INCREMENT by 1 
START WITH 1
NOMAXVALUE
NOCYCLE
CACHE 10;
```

- 删除序列

``` sql
DROP SEQUENCE seq_name;
```

- 查看序列

``` sql
SELECT seq_name.NEXTVAL from dual;
```

- 查看所有序列

``` sql
SELECT SEQUENCE_OWNER, SEQUENCE_NAME
FROM DBA_SEQUENCES
```

# DML

## 查询

### ROWNUM

- 参考

  https://www.cnblogs.com/elikun/p/10101288.html

  https://blog.csdn.net/yuyecsdn/article/details/91410802

`ROWNUM`是`Oracle`的引入的虚列。

- 该语句将报**ORA-01747**错误

``` sql
SELECT t.*, t.rownum
FROM dual t;
```

这是因为`dual`是一个虚表。在`dual`中不存在这个字段，所以我们无法使用`t.rownum`。

- 正确的写法

``` sql 
SELECT t.*, rownum
FROM dual t;
```

- 分页

``` sql
SELECT *
FROM (
	SELECT outter_.*, ROWNUM AS NUM
    FROM (
    	SELECT *
        FROM tablename inner_
        -- WHERE
        -- ORDER BY
    ) outter_
) page_
WHERE page_.NUM >= #{start}
AND page_.NUM <= #{end}
```

`ROWNUM`是在已经产生的数据上生成编号，所以在使用`ROWNUM`时需要保证已有数据的基础上。

## 计算时间

- https://www.cnblogs.com/yanghj010/p/5109714.html

``` sql
// 天
ROUND(TO_NUMBER(END_DATE - START_DATE))
// 小时
ROUND(TO_NUMBER(END_DATE - START_DATE) * 24)
// 分钟
ROUND(TO_NUMBER(END_DATE - START_DATE) * 24 * 60)
// 秒
ROUND(TO_NUMBER(END_DATE - START_DATE) * 24 * 60 * 60)
// 毫秒
ROUND(TO_NUMBER(END_DATE - START_DATE) * 24 * 60 * 60 * 60)
```

## 查询oracle版本

``` sql
SELECT * FROM PRODUCT_COMPONENT_VERSION;
```

## 查询oracle标准数据块大小 

``` sql
-- 可通过以下语句查询oracle标准数据块大小 
SELECT name, value FROM V$PARAMETER WHERE name = 'db_block_size'
```

## 查询数据文件

``` sql
SELECT FILE_NAME, TABLESPACE_NAME FROM DBA_DATA_FILES;
```

## 查询控制文件

``` sql
-- 查询控制文件
SELECT NAME FROM v$controlfile;
```

# 数据类型

## DATE

### 调用系统日期

``` sql
insert into tablename (date_col) value (sysdate);
insert into tablename (date_col) value (sysdate + 1);
```

在`mySql`数据库中，与`sysdate`对应的是`now()`函数。

### 日期处理函数

- `to_date(string, format)`

  ``` sql
  insert into tablename (date_col) value ('2020-08-15 17:00:00', 'yyyy-mm-dd hh24:mi"ss');
  ```

# 清理归档日志

当连接`oracle`出现`ORA-00257`错误时，说明`oracle`服务的归档日志已满。

- 如何清理归档日志

  ``` shell
  # 登录ssh
  # 切换oracle用户
  su - oracle
  sqlplus /nolog
  SQL> connect /as sysdba
  # 查看使用情况
  SQL> select * from V$FLASH_RECOVERY_AREA_USAGE;
  # 计算flash recovery area已经占用的空间
  SQL> select sum(percent_space_used)*3/100 from v$flash_recovery_area_usage;
  # 查找日志目录位置
  SQL> show parameter recover;
  
  # 删除归档日志
  
  # 查找rman工具所在目录
  find / -name rman
  # 找到oracle的bin目录下的rman工具
  
  # 连接数据库
  rman target /
  # RMAN
  # RMAN 检查归档日志
  RMAN> crosscheck archivelog all; 
  RMAN> list expire archivelog all
  ```
- 清理全部

  ```` bash
  RMAN> delete expired archivelog all;
  ````

- 删除7天前的日志

  ``` shell
  RMAN> delete archivelog until time "sysdate-7";
  ```

- RMAN 检查归档日志是否已经删除

  ``` shell
  RMAN> crosscheck archivelog all;   
  ```

-