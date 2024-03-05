[toc]

# 支持事务的存储引擎

``` mysql
# 查看数据库下面是否支持事务: InnoDB
SHOW ENGINES;

# 查看mysql当前默认存储引擎
SHOW VARIABLES LIKE '%storage_engine%';

# 查看某张表的存储引擎
SHOW CREATE TABLE '表名';
```

# 原子性

# 一致性

# 持久性

# 隔离性

## 事务并发问题

- 脏读

  事务A读取了事务B更新的记录，然后B执行回滚操作，那么A读取的数据即是脏数据。

- 不可重复读

  事务A多次读取同一数据，事务B在事务A多次读取的过程中，**修改**了其中一条记录并且`commit`，导致事务A多次读取数据结果不一致。

- 幻读

  事务A处理数据过程中，事务B插入了一条记录，事务A结束后发现仍然存在一条未处理的记录。

> 不可重复读和幻读很容易混淆，不可重复度侧重于修改，幻读侧重于新增或者删除。
>
> 解决不可重复度问题只需要**锁住满足条件的行**，解决幻读需要**锁表**。

## 隔离级别

| 隔离级别        | Dirty Read | Non-Repeatable Read | Phantom Read |
| --------------- | ---------- | ------------------- | ------------ |
| READ UNCOMMITED | ×          | ×                   | ×            |
| READ COMMITED   | √          | ×                   | ×            |
| REPEATABLE READ | √          | √                   | ×            |
| SERIALIZABLE    | √          | √                   | √            |

## 语法

``` mysql
##### 开启事务
BEGIN;
# (推荐)
START TRANSACTION;
BEGIN WORK;

##### 回滚
ROLLBACK;

##### 提交
COMMIT;

##### 还原点
INSERT STATEMENT1;
SAVEPOINT S1;
INSERT STATEMENT2;
SAVEPOINT S1;

ROLLBACK TO SAVEPOINT S2;
```

以`read uncommited`为例：

- **read uncommited**

``` mysql
# 当前事务隔离级别
SHOW VARIABLES LIKE '%tx_isolation%';
# 当前会话设置隔离级别为read uncommited
SET SESSION TRANSACTION ISOLATION LEVEL read UNCOMMITTED;

START TRANSACTION;
UPDATE account SET balance = balance -50 WHERE id = 1;
# 此时其他会话若隔离级别为read uncommited将查询到未提交的结果

ROLLBACK;
```

