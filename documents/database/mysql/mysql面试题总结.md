[toc]

# 基础

## MySQL Engine

- MyISAM
- InnoDB
- CSV
- Archive
- Memory
- Ferderated

可以通过`show engines`命令查看MySQL支持的引擎。

## MyISAM与InnoDB的区别

- 行级锁

  MyISAM不支持

- 事务

- 外键

  MyISAM不支持

- 恢复

  MyISAM不支持

- MVVC

  MyISAM不支持

- 索引实现

  InnoDB数据文件本身是索引文件；MyISAM的索引和数据文件是分开的；

- 性能

  MyISAM由于读写不支持并发，所以随CPU核心数增加，InnoDB的读写能力呈线性增强

## MyISAM适用场景

- 非事务性场景
- 只读场景

# 事务

## 什么是事务

- 崩溃场景
- 并发场景

在崩溃场景或并发场景下，为了保证数据的完整性，要求数据的一组操作要么完全执行，要么完全不执行。

## 事务的特点

- 原子性
- 持久性
- 隔离性
- 一致性

## MySQL事务有哪些隔离级别

- read uncommited
- read commited
- repeatable read
- serializable

## MySQL隔离由什么实现

`serializable`的隔离由锁实现，`repeatable read`与`read commited`由锁与MVVC实现。

## MVVC是什么

`Multi-Version Concurrency Control`，多版本并发控制。

## MVVC实现原理

MVVC由`undo log`与`read view`共同实现。

`undo_log`中有一个`trx_id`字段，记录修改该记录的事务id;

`read_view`中则有：

- **m_ids**	当前活跃事务id
- **min_id**	m_ids中的最小事务id
- **max_id**	m_ids的下一事务id
- **creator_id**	当前事务id

| 场景                                        | 可用 | 备注 |
| ------------------------------------------- | ---- | ---- |
| trx_id ∈ (-∞，min_id)                       | √    |      |
| trx_id = min_id                             | ×    |      |
| trx_id ∈ {m_ids} ∩ [min_id, max_id]         | ×    |      |
| trx_id ∈ [min_id, max_id]且trx_id ∉ {m_ids} | √    |      |
| trx_id ∈[max_id, +∞)                        | ×    |      |
| trx_id = creator_id                         | √    | 优先 |



# 锁

## InnoDB由哪些锁

- 行锁
- 表锁
- 间隙锁

# 索引

# 优化

# 分表分库

