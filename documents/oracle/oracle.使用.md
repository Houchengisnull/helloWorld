[toc]

# 连接

``` shell
SQL > conn /as sysdba
Connected.
SQL > grant create view to scott;
SQL > conn scott/tiger
Connected.
```

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

## 视图

- **参考**

  [Oracle视图详解](https://www.cnblogs.com/cxdanger/p/9914307.html)

  [Materialized View](https://www.cnblogs.com/benio/archive/2011/06/10/2077414.html)

- **基表概念**

  `视图(View)`通常是一种逻辑结构(不占用任何物理空间)，也叫`虚表`。

- **只读视图**

  其次，对视图中数据的修改，也将改动`基表`中的数据。但是如果我们在创建视图时，增加`with read only`，即可设置这个视图是`只读视图`。

  > 防止用户通过视图间接修改表。

- **物化视图(Materialized View)**

  占用`存储空间`的视图。

### 作用

- 提供各种数据表现形式

- 隐藏数据的逻辑复杂性并简化查询语句

- 提供安全性

  屏蔽基表中某些敏感信息，譬如用户证件号、手机号。

- 简化用户权限管理

  将视图权限授予用户，而不必将基表中某些列的权限授予用户。

### 使用

#### 创建视图

- **语法**

``` sql
CREATE [OR REPLACE] [FORCE] VIEW [schema.]view_name
	[(column1, column2,...)]
	AS
	SELECT ...
	[WITH CHECK OPTION] [constraint constraint_name]
	[WITH READ ONLY];
```

- **OR REPLACE**	如果存在同名视图，则使用新视图替代。

- **FORCE**	强制创建视图，无视基表是否存在、是否有权限

- **WITH CHECK OPTION**	

  例如：

  ``` sql
  -- 创建一个视图: 查询部门号为10的人员
  CREATE VIEW vw_emp_check
  AS SELECT emp_no
  	, emp_name
  	, job
  	, dept_no
  	FROM EMP
  	WHERE dept_no = 10
  WITH CHECK OPTION;
  
  -- 插入一条记录
  INSERT INTO vw_emp_check VALUES('3', '张三', '工程师', '20');
  -- 由于20号部门不在视图查询范围内, 将报错'违反检查约束'
  ```

  对视图的增删改查操作，必须是`select`可以查询到的数据。

##### 只读视图

``` sql
CREATE VIEW vw_emp_readonly AS
SELECT emp_no
	, emp_name
	, job
FROM emp
WITH READONLY;
```

##### 授予权限

- 授予创建视图的权限

``` sql 
GRANT CREATE VIEW TO scott;
```

#### 删除视图

``` sql
DROP VIEW vw_test_drop;
```

#### 实体化视图(Materialized View)

- 意义

  预先计算并保存表连接、聚集等耗时较多的操作，从而快速得到结果。



> - [Materialized View](https://www.cnblogs.com/benio/archive/2011/06/10/2077414.html)
>
> 这两天帮用户重写一个package. 原来的package含有三层loop,每层loop包含一个显式cursor. 运行需要2-3天。
>
> 我用materialized view重写底下两层的显式cursor.结果相同的参数，10分钟就跑出来了。
>
> Mv真的是效率很高啊.这两天要好好研究一下，现在先转一个介绍文档.

## 同义词

`同义词(Synonym)`是数据库对象的一个别名。

``` sql
CREATE [OR REPLACE] SYSNONYM [schema.]synonym_name FOR [schema.]object_name
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

### 拼接字符串

``` sql
# concat
SELECT CONCAT('当前时间:', TO_CHAR(sysdate, 'yyyy-MM-dd')) current_time
FROM daul;

# ||
SELECT *
FROM students
WHERE name like '%' || '子' || '%'
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

## 查询表空间

- **参考**

  [oracle 如何查看当前用户的表空间名称](https://www.cnblogs.com/xielong/p/9001595.html)

``` sql
-- 查询用户所在表空间
SELECT DEFAULT_TABLESPACE
FROM dba_users
WHERE USERNAME='用户名'

-- 查看所有表空间
SELECT * FROM dba_tablespaces;
SELECT * FROM v$tablespace;

-- 查询用户下所有表
SELECT * FROM user_tables;
SELECt * FROM dba_tables WHERE owner='用户名';

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
