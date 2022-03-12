[TOC]

- **参考**
- [官网](https://redis.io/documentation)

# 启动

`redis-server`程序在`src`目录下：

- 默认

``` shell
redis-server 
```

- 指定配置文件

``` shell
redis-server ../redis.conf
```

# 安全

## 修改密码

- 方法1

  1. 修改配置文件`redis.conf`；
  2. 修改其中的`requirepass`属性；
  3. 重启`redis`；

- 方法2

  ``` shell
  config set requirepass [password]
  ```

## 鉴权

``` shell
redis > AUTH passowrd
"OK"
```

# 配置

## 配置地址绑定

- **参考**

  [redis设置允许远程连接](https://www.cnblogs.com/lishidefengchen/p/10668989.html)

- **步骤**

  打开`redis.conf`

  1. 地址绑定

     ``` text
     bind 127.0.0.1 172.16.8.218
     ```

  2. 保护模式

     ``` text
     protected-mode no
     ```

  3. 密码

     ``` text
     requirepass redis_password
     ```

  > 参数前不可有空格

## 配置最大连接数

``` shell
redis > config get maxclients

1) "maxclients"
2) "10000"
```

# 连接

## Ping

连接正常返回`PONG`，否则返回连接错误。

## 查看连接

- **CLIENT LIST**
- **CLIENT SETNAME**
- **CLIENT GETNAME**
- **CLIENT PAUSE**
- **CLIENT KILL**

# 删除

``` shell
redis > set key1 "Hello"
"OK"
redis > set key2 "World"
"OK"
redis > DEL key1 key2 key3
(integer) 2
redis >
```

> 该命令不支持通配符方式：
>
> ``` shell
> DEL key*
> ```

# 清理

## FLUSHALL

清空所有数据库。

在默认情况下，同步清空所有数据库。

``` shell
flushall [ASYNC|SYNC]
```

## FLUSHDB

清理当前数据库

``` shell
flushdb [ASYNC|SYNC]
```

# 发布订阅

``` shell
# 发布
publish chat.queue "hello world"

# 订阅
subscribe chat.queue
```

- 缺点
  - 消息无法持久化，存在丢失风险
  - 没有ACK机制，发布方不确保订阅方成功接收
  - 广播机制，下游消费能力取决于消费者本身

# 架构

## 单线程

### 原因

## 客户端连接过程

### Socket

- **TCP**
  1. 非阻塞模式
  2. TCP_NODELAY

## 非阻塞多路复用模型

# 分布式锁

- **参考**
- [分布式锁](http://www.redis.cn/topics/distlock.html)
- [Redis分布式锁使用不当，酿成一个重大事故，超卖了100瓶飞天茅台！！！ - 老刘的文章 - 知乎 ](https://zhuanlan.zhihu.com/p/362755550)
- [为什么redis可以做分布式锁，是因为redis是单线程的吗? - Leon的回答 - 知乎 ](https://www.zhihu.com/question/317687988/answer/2223528054)

## 锁设计

### setnx

## RedLock

# 缓存穿透

查询不存在的数据，每次都将到持久层查询，导致缓存失效。

## 解决方案1:布隆过滤器

主要用于快速检索一个元素是否在集合中。

空间效率极高的概率型算法

![1557320189757](../images/redis/boolm.png)

### 优势

- 空间效率极高
- 算法复杂度：O(n)
- 信息安全高

### 不足

- 存在`Hash碰撞`
- 数据删除困难

### 实现 Google Guava

### 存在问题

#### 数据量过大，布隆过滤器初始化过长

##### 解决方案

Java多线程分而治之——多线程 Fork/Join

#### 数据不一致

