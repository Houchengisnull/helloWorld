[toc]

# 查询oracle版本

``` sql
SELECT * FROM PRODUCT_COMPONENT_VERSION;
```

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