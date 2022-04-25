[toc]

# 索引

- MySQL中的索引

  MySQL默认存储引擎InnoDB的索引——**B+ Tree**。

``` mysql
# 查看索引
SHOW INDEX FROM TABLENAME
```



## B+ Tree

- **特点**
  - 数据只存在叶子节点上
  - 非叶子节点只保存索引信息
  - 叶子节点按照升序排序串联起来
  - 叶子节点**在物理存储上无序的**，但**在逻辑上是有序的**（通过指针串在一起）

## 分类

- 普通索引
- 唯一索引
- 复合索引
- 聚簇索引
- 非聚簇索引

## 覆盖索引

指`SELECT`的列都是索引字段，即直接查询索引即可，不需要回表操作。

``` mysql
CREATE TABLE T1 (
    COL1 INT PRIMARY KEY
    , COL2 INT
    , KEY(COL2)
);

SELECT COL2 FROM T1;
```

这个时候，`SELECT`所有的字段就是索引字段，那么只查询该索引对应的B+树即可，不需要再进行回表操作，查询数据对应的B+树。

