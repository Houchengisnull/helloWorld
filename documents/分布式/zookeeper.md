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

## 2PC

二阶段提交算法，用于解决分布式事务问题。

- 阶段一	(对各个资源)写入undo和redo事务日志
- 阶段二	(在每个资源反馈后)commit/rollback

``` mermaid
graph LR
事务管理器 --> 资源1
事务管理器 --> 资源2
```

### 优点

- 原理简单
- 实现方便

### 缺点

- 同步阻塞
- 单点故障
- 数据不一致
- 容错机制不完善

## 3PC

三阶段提交。

``` sequence
participant Coordinator
participant `Participant
Coordinator - `Participant : canCommit?
`Participant -- Coordinator : yes
Coordinator - `Participant : preCommit?
`Participant -- Coordinator : yes
Coordinator - `Participant : doCommit?
`Participant -- Coordinator : yes
```

- **与2PC的区别**

  在第二阶段才写入undo和redo事务日志；

  在第三阶段协调者发生异常或网络超时时，参与者仍会commit；

### 优点

- 改善同步阻塞
- 改善单点故障

### 缺点

- 同步阻塞
- 单点故障
- 数据不一致
- 容错机制不完善

> 不论是二阶段还是三阶段提交，本质都是分阶段处理。其中每一步的均依赖于上一步的“正确完成”，并且理所当然的认为：我确认之后，故障变不会发生，但是事实上是：故障时随时发生的。

## Paxos

- 二阶段提交
- 少数服从多数

## ZAB

Zookeeper集群一致性协议——ZAB，是ZooKeeper专门设计的支持崩溃恢复的原子广播协议。在ZooKeeper中，主要依赖ZAB协议实现分布式数据一致性。

ZAB协议包含两种模式：

- 消息广播
  1. Leader接收消息后，对消息赋予全局唯一的64位自增id（zxid），通过zxid的大小比较即可实现**有序性**。
  2. Leader通过先进先出队列将带有zxid的消息作为一个提案（proposal）分发所有的follower。
  3. 当Follower接收到proposal，将proposal写入硬盘，写入成功后向Leader返回ACK。
  4. 当Leader收到合法数量的ACK后，Leader就向所有Follower发送COMMIT，会在本地执行该消息。
  5. 当Follower收到消息的COMMIT命令后，执行该消息。
- 崩溃恢复(用于选举领导)
  1. 每个Server会发出一个投票，第一次都是投自己（投票信息：zxid+myid）;
  2. 收集各个服务器的投票；
  3. 处理投票并重新投票，处理逻辑：优先比较zxid，然后比较myid；
  4. 统计投票：只要超过半数的机器接收同样的投票信息，就可以确定Leader；
  5. 改变服务器状态；

<hr>

ZAB协议中相关角色：

- Leader

  1. 事务请求的唯一调度和处理者，保证集群事务处理的顺序性；

  2. 集群内部各个服务器的调度者（管理Follower、数据同步等）；

- Follower

  1. 处理非事务请求，转发事务请求给Leader；

  2. 参与事务请求proposal投票；

  3. 参与leader选举投票；

- Observer

  3.30版本以上和Follower功能相同，但不参与投票；

  ``` shell
  # observer配置
  peerType=observer
  server.3=192.**.**.**:2888:3888:observer
  ```

Zookeeper集群实际上是一种读写分离的架构，Leader负责写入数据，Follower仅能读取数据。

- **特点**
- 顺序一致性
- 原子性
- 单一视图
- 可靠性
- 实时性
- 角色轮换避免单点故障

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

# 配置

## 端口说明

| 端口 | 说明                      |
| ---- | ------------------------- |
| 2181 | Zookeeper默认端口         |
| 2888 | Zookeeper集群数据同步端口 |
| 3888 | Zookeeper集群选举端口     |

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

# 日志可视化

zookeeper有两个重要配置：dataDir与dataLogDir，分别存放快照数据和事务日志文件。

## 事务日志可视化

``` shell
java -cp
/soft/zookeeper-3.4.12/zookeeper-3.4.12.jar:/soft/zookeeper-3.4.12/lib/slf4j-api-1.7.25.jar
org.apache.zookeeper.server.LogFormatter log.1
```

## 快照数据可视化

``` java
java -cp
/soft/zookeeper-3.4.12/zookeeper-3.4.12.jar:/soft/zookeeper-3.4.12/lib/slf4j-api-1.7.25.jar
org.apache.zookeeper.server.LogFormatter log.1
```

# Java客户端

- Zookeeper官方原生客户端
- ZkClient
- Curator

## Zookeeper原生客户端

### Watcher机制

用于监听各个节点的变化。

### 弊端

- 会话连接是异步的
- Watch需要重复注册
- Session重连机制
- 开发复杂性高

# 源码

## 客户端源码

- SendThread

- EventThread

