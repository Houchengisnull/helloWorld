[toc]

- 参考

  https://www.cnblogs.com/948046hs/p/9032831.html

> 2020.8.27
>
> 之前做了一个需求，使用了`oracle`数据库。由于两三年没有使用过`oracle`数据库了，而且本身也不是很了解，开发过程中遇到重重困难。
>
> 其中有一个问题，我在一个`schema`创建了`序列`，在`sql statement`没有指定`schema`，连接数据库时又使用了与`schema`不对应的用户。导致插入时一直找不到`序列`。

`oracle`的存储结构分为`逻辑存储结构`与`物理存储结构`

# 逻辑存储结构

描述`oracle`内部组成和管理数据的方式。

[![image](../images/oracle/oracle.逻辑结构.png)](逻辑结构)

## 数据块

`oracle`逻辑存储结构中的最小的逻辑单位，也是**执行/输出操作**的最小存储单位

``` sql
-- 可通过以下语句查询oracle标准数据块大小 
SELECT name, value FROM V$PARAMETER WHERE name = 'db_block_size'
```

## 数据区

由一组`数据块`组成的`Oracle`存储结构，`数据区`是`oracle`**存储分配**的最小单位

## 数据段

由一组`数据区`组成的`Oracle`存储结构

## 表空间

数据块最大的逻辑分区，通常用于存放表、索引等数据对象，每个数据块至少有一个表空间(即`System`表空间)

- **system**：系统表空间
- **sysaux**：`system`辅助表空间，降低`system`表空间负荷，主要存储数据字典以外的其他数据对象
- **undo**：撤销表空间，当用户对数据表进行修改操作时，`oracle`系统自动使用撤销表空间临时存放修改前数据
- **users**：用户表空间，是`oracle`建议用户使用的表空间，可以在表空间上创造各种数据对象

在使用Navicat查询数据库时，仅体现了`schema`，并没有体现`表空间`这种逻辑结构。但不用怀疑表空间是的存在。

``` sql
-- 查询所以表空间
SELECT * 
FROM dba_tablespaces;
```



## 用户[user]与模式[schema]

- 参考

  [Oracle 用户（user)和模式(schema)的区别](https://www.cnblogs.com/dunjidan/p/4033573.html)

### 数据库对象

数据库对象指的是在`Oracle`中所具有特殊功能的组件，统称为`数据库对象`。比如：`表`、`视图`、`约束`、`索引`、`存储过程`等等。

### 用户

`oracle用户`是用连接`数据库`和访问`数据库对象`的

### 模式

`数据库对象`的集合。`模式对象`是数据库数据的***逻辑结构***。

> For all intents and purposes you can consider a user to be a schema and a schema to be a user.
> 在任何情况下你都可以把用户和schema当作同一个东西。

### 用户和模式的区别

用户是用来连接数据库对象的；模式用来管理对象的。

通常情况下，一个用户仅有一个默认`schema`。

**在`oracle`中，不能创建新的`schema`，只能通过创建一个用户的方法解决。尽管`oracle`有`create schema`的语句。**

所以，`user`和`schema`往往一一对应。

# 物理存储结构

## 文件结构

描述`oracle`在操作系统中物理文件组成。

- **数据文件( *.DBF)**

  保存用户应用数据和`oracle`系统内部数据的文件。在创建表空间的同时创建数据文件。

  ``` sql
  SELECT FILE_NAME, TABLESPACE_NAME FROM DBA_DATA_FILES;
  ```

- **控制文件(*.CTL)**

  记录数据库的物理结构，其中主要包含数据库名，数据文件和日志文件的名字和位置，数据库建立日期等信息。

  ``` sql
  -- 查询控制文件
  SELECT NAME FROM v$controlfile;
  ```

- **日志**

  记录对数据所做的修改，包括`重做日志文件`和`归档日志文件`。

  - **重做日志**

    记录数据所有发生过得更改信息及`oracle`内部行为，比如创建表或者索引。 

    可以通过对表或整个表空间设定`NOLOGGING`属性，以便基于表的所有DML操作不会生成日志信息。

  - **归档日志**

    `非归档模式`

    是在系统运行期间，所产生的日志信息不断记录到日志文件组中，当所有重做日志被写满后，又重新从第一个日志组开始覆写。

    `归档模式`

    各个日志文件被写满而即将覆盖之前，先由归档进场将即将覆盖的日志文件中的日志信息读出，并将读出的日志信息写入归档文件中，这个过程被称为`归档操作`。

- **服务器参数文件**

  记录`oracle`数据库的基本参数信息，控制文件基本路径，日志缓冲大小等。

## 数据高速缓存

- [oracle运行速度与效率高的秘密](https://blog.csdn.net/dream361/article/details/53447132)

  `数据高速缓存`跟操作系统缓存类似，它保存最近从`数据文件`中读取的`数据块`。

  例如当我们使用`select statement`查询员工信息时，首先不是从`数据文件`中查询数据，而是从`数据高速缓存`中查询数据。

### 空闲缓存块

重启数据库后，操作系统会为数据库分配空闲缓存块。当`oracle`读取数据后，数据库就会寻找是否有空闲的缓存块，以便将写入数据。

所以当数据库启动后，便会占用比较多的内存，但依然可以正常运行。同时，在启动时申请，可以免去在实际需要时向内存申请的时间。

实际上，在调用`select statement`读取数据时，数据库首先寻找是否有空闲的缓存块。

### 命中缓存块

当`select statement`读取数据后，会将取得的数据放入这个命中缓存块中，直到高速缓存消耗完毕。

当下次读取数据时，就直接从`数据高速缓存`中读取数据。

### 脏缓存块

`脏缓存块`存储已修改过但还没写入数据文件的信息。当通过`update statement`对某个缓存块中数据修改后，数据库将这个缓存块标记为脏缓存块。

当满足一定条件时，将脏缓存块数据写入数据库文件中。

# 常见概念

## Instance

- [oracle 数据库、实例、服务名、SID](https://www.cnblogs.com/ahudyan-forever/p/6016784.html)
- [Oracle数据库查看SID和service_name](https://www.cnblogs.com/rusking/p/7762343.html)

`Instance`这个单词我们常常翻译成`实例`。但实际上在计算机科学中，`Instance`指操作系统中一系列的**`进程`**以及为这些进程所分配的**`内存块`**。

而我们常说的`数据库实例(instance)`就是数据库的后台进程/线程以及共享内存区。而`数据库(database)`则指物理操作系统文件或磁盘的集合。

作为一名严谨的程序员，我们不应该把其弄混淆。

``` sql
-- 查询实例名称
SELECT *
FROM v$instance;
```

## Service

指对外公开的服务名。

> 打个比方，你的名字叫小明，但是你有很多外号。你父母叫你小明，但是朋友都叫你的外号。
>
> 这里你的父母就是oracle实例，小明就是sid，service name就是你的外号。

我们打开`SQL/Plus`，执行`show parameter service`即可查看。

> 此处概念里的`SID`，即数据库实例名称，注意与下文中`SID`区分。

## session、sid、serial

- **参考**
- [Oracle session的SID和Serial#的概念](http://blog.sina.com.cn/s/blog_c33497610102v54f.html)

### sid

`sid`用于标识一个`session`，与`process`对应。

一个`process`一般对应一个`session`，在`session`结束后，新的`session`建立时，`sid`被复用。

> 此处`SID`为会话进程/线程的`SID`，`Service`中`SID`指数据库实例的`SID`。
>
> 讲实话我也不清楚这里开辟的是进程or线程。

### Serial#

`oracle`通过它来识别具有相同`sid`的不同`session`。

可通过`conn user/passwd`命令发起新的`session`。

``` sql
SELECT username,sid,serial# 
FROM v$session;
```

### 注意

- `oracle`数据库内存配置要大
- 不要在数据库服务器上运行其他服务。除硬件资源争夺外，还会导致数据库高速缓存块不连续。