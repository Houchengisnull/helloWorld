[toc]

# SELECT

## 尽量全值匹配

``` mysql
SELECT *
FROM staff
WHERE name = 'Hugo'
```

## 最佳左前缀原则

存在组合索引情况下，查询从索引的最左前列开始，且不跳过索引中间列

## 不在索引列上做任何操作

``` mysql
# 反例
SELECT * FROM staff WHERE LEFT(name,4) = 'Hugo'
```

## 范围条件放在最后

中间有范围查询会导致后面的索引列全部失效

``` mysql
# 反例
SELECT * 
FROM staff 
WHERE 
	name = 'Hugo'
	AND age > 22
	AND pos = 'master'
```

## 尽量使用覆盖索引

## 不等于要慎用

在使用不等于(!=, <>)时将导致无法使用索引，而引起全表扫描

## Null/Not的影响

### 字段为NOT NULL

在字段为`NOT NULL`的情况下，使用`IS NULL`或`IS NOT NULL`会导致索引失效。

### 字段为NULL

在字段可为`NULL`的情况下，使用`IS NOT NULL`会导致索引失效。

## LIKE

``` mysql
# 反例
SELECT * 
FROM staff 
WHERE 
	name LIKE '%go';
	
# 使用覆盖索引规避
create index idx_allstaff_id_name_psw on allstaff(staffId, staffName, psw);

explain select staffId, staffName, psw from allstaff where staffName like '%一%';
+----+-------------+----------+------------+-------+---------------+--------------------------+---------+------+------+----------+--------------------------+
| id | select_type | table    | partitions | type  | possible_keys | key                      | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+----------+------------+-------+---------------+--------------------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | allstaff | NULL       | index | NULL          | idx_allstaff_id_name_psw | 368     | NULL |    2 |    50.00 | Using where; Using index |
+----+-------------+----------+------------+-------+---------------+--------------------------+---------+------+------+----------+--------------------------+
```

## 字符类型不加上单引号

将导致索引失效

``` mysql
# 反例
SELECT * 
FROM staff 
WHERE 
	name = Hugo
```

# UNION > OR

OR改UNION效率高

# 批量导入

## INSERT

- 尽量使用批量导入
- 提交前关闭自动提交
- 使用MyISAM

## LOAD DATA INFILE

使用`LOAD DATA INFILE`比一般的insert快20倍。

- 语法

``` mysql
select * into outfile 'D:\\product.txt' from product_info;

load data INFILE 'D:\\product.txt' into table product_info;
```

- 参数

``` mysql
# 是否允许导入
SHOW VARIABLES LIKE 'secure_file_priv';
```

- secure_file_priv为`null`时，不允许mysqld导入导出
- secure_file_priv为`/tmp`时，仅允许mysqld在`/tmp`导入导出
- secure_file_priv为`''`时，不限制mysqld在任意目录的导入导出