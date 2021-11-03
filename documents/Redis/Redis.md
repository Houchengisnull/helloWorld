[TOC]

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



# redis配置地址绑定

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

