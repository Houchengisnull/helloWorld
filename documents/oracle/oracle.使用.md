[toc]

# DDL

## 表空间与用户

``` sql
/* 创建用户myoracle*/
CREATE USER myorcl identified by "password";
/* 授权 */
GRANT CREATE SESSION TO myorcl;
GRANT CREATE TABLE TO myorcl;
GRANT CREATE TABLESPACE TO myorcl;
GRANT CREATE VIEW TO myorcl;
/* 授权用户创建序列 */
GRANT CREATE SEQUENCE,SELECT ANY SEQUENCE TO myorcl;

/* 创建表空间 */
CREATE TABLESPACE my_orcl_space;
DROP TABLESPACE my_orcl_space;

/* 用户授权 */
ALTER USER myorcl quota unlimited on my_orcl_space;
```

- **删除用户**

``` sql
DROP USER my_orcl cascade;
```

## 表

- **创建**

``` sql
CREATE TABLE STUDENT (
	"ID" NUMBER(11) PRIMARY KEY ,
    "NAME" VARCHAR2(11 CHAR),
    "IS_ENABLE" NUMBER(1) DEFAULT(1)
);
```

- **修改表结构**

``` sql
# 增加主键
ALTER TABLE "MYORACLE"."STUDENT" ADD PRIMARY KEY ("ID"); 

# 增加队列
ALTER TABLE STUDENT ADD (STATUS NUMBER(1), GRADE NUMBER(3));

# 增加注释
COMMENT ON COLUMN STUDENT.STATUS is "学习状态";
COMMENT ON COLUMN STUDENT.GRADE is "学生年级";

# 删除字段
ALTER TABLE STUDENT DROP COLUMN GRADE;

# 修改字段
# 修改字段长度
ALTER TABLE STUDENT MODIFY (STATUS VARCHAR2(10));
ALTER TABLE STUDENT RENAME COLUMN STATUS TO STUDY_STATUS;
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

  [Oracle分页](https://blog.csdn.net/yuyecsdn/article/details/91410802)

  [Oracle中rownum机制原理&用法详解](https://www.cnblogs.com/elikun/p/10101288.html)

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

#### 分页

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

> - **2021-4-6**
>
>   `ROWNUM`的作用应该是`oracle`对`select`语句的查询结果进行编号，也就是说先执行`select`语句，得到查询结果后，`oracle`将对这个查询结果一一编号。
>
>   那么`ROWNUM`的生效在`WHERE`之后。
>
>   经过验证，确实如此。

#### 错误的写法

``` sql
SELECT *
FROM STUDENT
WHERE ROWNUM > 1;
```

这样的查询语句是没有结果的。

**这是因为`ROWNUM`是动态的！根据结果集实时变化的！**

执行完`SELECT * FROM STUDENT`后，`oracle`对结果集进行筛选|更新。

第一条记录`00001`的`ROWNUM`是0，那么`oracle`会把它去掉。

接着`oracle`提携下一条记录`00002`后，由于去掉了上一条记录`00001`，记录`00002`的`ROWNUM`依然是0。循环往复，这条`SQL Statement`查不到结果。

## 函数

### 截取时间

``` sql
// 获取时间的时
SELECT TO_CHAR(SYSDATE, 'hh24') D from dual;
// 获取时间的日
SELECT TO_CHAR(SYSDATE, 'dd') D from dual;
```

### 计算时间

- [Oracle 计算两个时间的差值](https://www.cnblogs.com/yanghj010/p/5109714.html)

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
  insert into tablename (date_col) value (to_date('2020-08-15 17:00:00', 'yyyy-mm-dd hh24:mi"ss'));
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
  RMAN> list expired archivelog all
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

# FAQ

## 无法删除当前连接用户

``` sql
/* 查看用户连接情况 */
SELECT username,sid,serial# from v$session;

/* 找到退出用户sid与serial */
ALTER SYSTEM KILL SESSION '532,4562';
```

## ORA-00942 表或视图不存在

- 使用`navicat`创建表会导致表名有双引号，需要在查询时增加双引号

  建议直接使用`SQL语句`建表
