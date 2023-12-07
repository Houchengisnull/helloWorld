[toc]

# 分布式系统

分布式部署具有以下特点：

- 分布性
- 对等性
- 并发性
- 缺乏全局时钟
- 故障随时发生

## CAP理论

- **Consistency**
- **Available**
- **Partition Tolerance**

架构师的职责之一就是在一致性与可用性之间寻求平衡。

## BASE理论

- **Basically Available**

  基本可用，当分布式系统出现不可预见的故障时，允许损失部分可用性，保障系统的基本可用；

- **Soft state**

  软状态，系统同步数据时，允许系统中的数据存在中间状态；

- **Eventually consistent**	

  最终一致性，所有的数据经过一段时间的数据同步后，最终能够达到一个一直的状态；

# 分布式一致性算法

常见的分布式一致性算法：

- 2p

  两阶段提交，是分布式数据库常用的一种分布式算法，简单但是会出现阻塞或者数据不一致的情况。

- 3p

  对2p进行完善，解决阻塞并提高可用性。

- paxos

  分布式一致性算法，同样分为两个阶段，遵循少数服从多数的原则，并不需要所有参与者都同意。

- zab

  借助paxos的思路，是zookeeper解决分布式一致性使用的算法。

# 分布式环境协调和通信场景

- 数据发布订阅
- 负载均衡
- 命名服务
- Master选举
- 集群管理
- 配置管理
- 分布式队列
- 分布式锁

# Zookeeper

ZooKeeper致力于提供一个高性能、高可用，且具备严格的顺序访问控制能力的分布式协调服务。由雅虎公司创建，是 Google 的 Chubby 一个开源的实现，也是 Hadoop 和 Hbase 的重要组件。

其设计目标为：

- 简单的数据结构

  共享树形结构，类似文件系统，存储于内存。

- 集群

- 顺序访问

  对于每个读请求，zookeeper会分配一个全局唯一的递增编号。

- 高性能

  基于内存操作，服务于非事务请求。适用于读操作为主的业务场景。

## 集群部署

 1. 下载zookeeper

    ``` shell
    wget https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/zookeeper-3.4.10/zookeeper-3.4.10.tar.gz
    ```

 2. 解压zookeeper

    ``` shell
    tar -zxvf zookeeper-3.4.10.tar.gz
    ```

 3. 重命名

    ``` shell
    mv zookeeper-3.4.10 zookeeper
    ```

 4. 修改配置

    ``` shell
    cd /usr/local/zookeeper/conf
    mv zoo_sample.cfg zoo.cfg
    vi zoo.cfg
    
    # 1. 修改dataDir
    # 2. 最后添加集群节点
    ```

# Session

指客户端与服务端的一次会话连接，本质是TCP长连接。通过会话进行心跳检测和数据传输。

# Znode

## 类型

- 持久节点
- 持久有序节点
- 临时节点
- 临时有序节点

持久节点与临时节点的区别：

1. 当客户端断开连接后，持久节点依然存在，但是临时节点将不再存在；
2. 临时节点不允许创造子节点；

# ACL

zookeeper的权限控制机制，表示为`schema:id:permissions`。

## schema

- world	默认方式
- auth	已经认证通过的用户
- digest	密码认证
- ip	ip地址认证

## id

- auth	username:password
- digeset	username:BASE64(SHA1(password))
- world	anyone
- ip

## permission

- CREATE
- READ
- WRITE
- DELETE
- ADMIN	管理员权限，指该用户可以授予其他用户权限

## 注册会话授权信息

``` shell
addauth digest user1:123456 # 需要先添加一个用户
setAcl /testDir/testAcl auth:user1:123456:crwa # 然后才可以拿着这个用户去设置权限
getAcl /testDir/testAcl # 密码是以密文的形式存储的
create /testDir/testAcl/testa aaa
delete /testDir/testAcl/testa # 由于没有 d 权限，所以提示无法删除
```

# Java客户端

- Zookeeper官方提高客户端
- ZkClient
- Curator
