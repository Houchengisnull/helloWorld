[toc]

# 执行计划

## ID

用于分析执行顺序。

- 若ID不同，从大到小执行
- 若ID相同，从上往下执行

## SELECT_TYPE

| SELECT_TYPE  | 作用                |
| ------------ | ------------------- |
| SIMPLE       | 不包含子查询与UNION |
| PRIMARY      | 最外层的查询        |
| SUBQUERY     |                     |
| DERIVED      | 衍生表              |
| UNION        | UNION跟随的表       |
| UNION RESULT | 合并结果            |

- **UNION**

  ``` mysql
  EXPLAIN
  SELECT * FROM T1
  UNION
  SELECT * FROM T2
  ```

  以上查询中，UNION所使用的表是T2。

## TABLE

相关的表。

## TYPE

访问类型。

- 常用

``` text
system > const > eq_ref> range > index > all
```

- 详细

| TYPE            | 作用                                                         |
| --------------- | ------------------------------------------------------------ |
| system          | 表中只有一行记录                                             |
| const           | 常数，例：`SELECT * FROM T1 WHERE ID=1;`                     |
| eq_ref          | 使用主键或者唯一索引，例：`SELECT * FROM T1, T2 WHERE T1.ID = T2.ID` |
| ref             | 非唯一索引                                                   |
| fulltext        |                                                              |
| ref_or_null     |                                                              |
| index_merge     |                                                              |
| unique_subquery |                                                              |
| index_subquery  |                                                              |
| range           | 范围查询                                                     |
| index           | 扫描全部索引列，例：`SELECT ID FROM T1`                      |
| all             | 全表扫描                                                     |

一般来说，需要保证查询至少达到range，最好达到ref。

## POSSIBLE_KEYS与KEY

- **POSSIBLE_KEYS**	可能使用的索引
- **KEY**	实际使用的索引

## KEY_LEN

KEY_LEN表示索引中使用的最大字节数。

- 在不损失精确性的情况下，长度越短越好
- 根据该值，判断组合索引是否充分利用

### 字符类型

是否可变、字符集、长度、是否为空都将影响该字段的值。

- **char**

  比如字段类型是`char(10)`，字符集是UTF8，那么KEY_LEN就是3*10=30。如果该字段可为null，那么结果再加1。

  > UTF8每个字符占用3个字节。

- **varchar**

  如果该字段是`varchar(10)`，那么结果是3*30+2=33。

  > 变长类型需要两个字段来存储该变长列的实际长度。

### 整形/浮点数/时间

字段本身长度与是否为空将影响该字段的值。

## REF

显示索引哪一列被使用了。

## ROWS

大致估算执行完成需要读取的行数。

## EXTRA

| 类型              | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| using filesort    | 使用文件排序                                                 |
| using temporary   | 使用临时表保存结果，常见于排序与分组                         |
| using index       | 使用了覆盖索引                                               |
| using where       | 使用where过滤                                                |
| using join buffer | 使用了连接缓存，使用join时缓存记录较少的表。效率略高于笛卡尔积。 |
| impossible where  | where子句总为false                                           |

