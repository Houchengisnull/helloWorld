[toc]

# MySQL中的锁

- 表级锁

  开销小，加锁快，不会出现死锁。锁定粒度大，发生锁冲突概率高，并发度最低。

- 行级锁

  开销大，加锁慢，会出现死锁。锁定粒度最小，发生锁冲突概率最低，并发度最高。

- 页面锁（gap锁，间隙锁）

  开销和加锁时间介于表锁和行锁之间，会出现死锁。锁定粒度介于表锁和行锁之间，并发度一般。

# 表锁与行锁的使用场景

- 表锁

  适合以查询为主，只有少量按索引条件更新数据的应用。

- 行锁

  行级锁适合有大量按索引条件并发更新少量不太数据，同时又有并发查询的应用。

# MyISAM

| 锁模式 | None | 读锁 | 写锁 |
| ------ | ---- | ---- | ---- |
| READ   | YES  | YES  | NO   |
| WRITE  | YES  | NO   | NO   |

## 表锁

### 语法

``` mysql
LOCK TABLE testmyisam READ;
# 报错
insert/update
# 另一个session, 则等待
insert
# 同一个session查询其他表，报错
select
# 另一个session插入其他表，成功
insert
# 同一个session查询表别名，报错
select * from testmyisan s;
lock table testmyisam as s read;
# 解锁
unlock tabels;

LOCK TABLE testmyisam WRITE;
# 对不同表操作insert,select,则会报错
# 另一个session中select操作，等待
```

### 总结

- **读锁**

  - 对MyISAM表的读操作，不会阻塞其他用户对同一表的读请求。

  - 对MyISAM表的读操作，不会阻塞当前session对表读，对表进行修改会报错。

  - 一个session使用`LOCK TABLE`命令给表加读锁，这个session可以查询锁定表的记录，但更新或访问其他表都会提示报错。

- **写锁**

  - 对MyISAM表的写操作，会阻塞其他用户对同一表的读写操作。
  - 对MyISAM表的写操作，当前session可以对本表做CRUD，但对其他表进行操作会报错。

# InnoDB

在InnoDB中支持行锁

- **基本概念**

  - 共享锁

    当一个事务对某几行上读锁时，允许其他事务进行读操作，但不允许写操作

  - 排他锁

    当一个事务对某几行上写锁时，不允许其他事务写及上锁（包括读锁和写锁），但允许读。

## 行锁

行锁必须有索引才能实现，否则将锁全表

### 语法

``` mysql
BEGIN;

# 对id为1的记录上锁
SELECT * FROM testdemo WHERE id=1 for update;

# insert\update\delete语句在事务中默认加锁
UPDATE testdemo 
SET c1='1'
WHERE id=1;

# 行锁需要ROLLBACK/COMMIT才能释放锁
-- ROLLBACK;
COMMIT;
```

#### 手动释放锁

``` mysql
# MYSQL5.6
SELECT
	r.trx_id waiting_trx_id
	, r.trx_mysql_thread_id waiting_thread
	, r.trx_query waiting_query
	, b.trx_id blocking_trx_id
	, b.trx_mysql_thread_id blocking_thread
FROM
	information_schema.innodb_lock_waits w
	INNER JOIN
	information_schema.innodb_trx b ON b.trx_id = w.blocking_trx_id
	INNER JOIN
	information_schema.innodb_trx r ON r.trx_id = w.requesting_trx_id;
# MYSQL5.7
# 执行该查询的sql_kill_blocking_connection
SELECT * 	FROM information_schema.INNODB_LOCKS;
SELECT * FROM sys.innodb_lock_waits;

kill thread_id;
```

# 间隙锁

在MySQL中，将隔离级别设置为可重复读，即可解决幻读问题，借助的就是间隙锁。