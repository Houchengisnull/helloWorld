[toc]

# 技术栈

- Distributed Computing Environment,DCE	分布式运算环境

- Network Computing Architecture,NCA	网络运算架构，远程服务调用的雏形
  - RPC
- Andrew File System	AFS文件系统
  - DFS
- Kerberos协议	服务认证和访问控制的基础性协议，应用于Windows、MacOS等操作系统登录认证
- Cloud Native	后微服务时代
  - Software-Defined Networking，SDN	[软件定义网络](https://en.wikipedia.org/wiki/Software-defined_networking)
  - Software-Defined Storage，SDS	[软件定义存储](https://en.wikipedia.org/wiki/Software-defined_storage)
  - Service Mesh	服务网格
    - Sidecar Proxy	边车代理模式

# 演进史

- Monolithic Application	单体架构/巨石系统

> - 《凤凰架构》
>
> 同样，由于所有代码都共享着同一个进程空间，不能隔离，也就无法（其实还是有办法的，譬如使用 OSGi 这种运行时模块化框架，但是很别扭、很复杂）做到单独停止、更新、升级某一部分代码，因为不可能有“停掉半个进程，重启 1/4 个程序”这样不合逻辑的操作，所以从可维护性来说，单体系统也是不占优势的。程序升级、修改缺陷往往需要制定专门的停机更新计划，做灰度发布、A/B 测试也相对更复杂。
>
> - 《凤凰架构》
>
> 如本书的前言开篇《[什么是"凤凰架构"](https://icyfenix.cn/introduction/about-the-fenix-project.html)》所说，正是随着软件架构演进，构筑可靠系统从“追求尽量不出错”，到正视“出错是必然”的观念转变，才是微服务架构得以挑战并逐步开始取代运作了数十年的单体架构的底气所在。

- Service-Oriented Architecture,SOA
  - Information Silo Architecture	烟囱式架构
  - Microkernel Architecture/Plug-in Architecture	微内核架构/插件式架构
  - Event-Driven Architecture	事件驱动架构
  - Service-Oriented Architecture,SOA
    - SOAP
    - ESB
    - Business Process Management，BPM	业务流程编排
    - Service Data Object, SDO	服务数据对象
    - Service Component Architecture，SCA	服务组件架构
  
- Microservices	微服务架构

- Cloud Native	后微服务时代/云原生
  - Kubernetes
  - Spring Cloud
  
  |          | Kubernetes              | Spring Cloud          |
  | -------- | ----------------------- | --------------------- |
  | 弹性伸缩 | Autoscaling             | N/A                   |
  | 服务发现 | KubeDNS / CoreDNS       | Spring Cloud Eureka   |
  | 配置中心 | ConfigMap / Secret      | Spring Cloud Config   |
  | 服务网关 | Ingress Controller      | Spring Cloud Zuul     |
  | 负载均衡 | Load Balancer           | Spring Cloud Ribbon   |
  | 服务安全 | RBAC API                | Spring Cloud Security |
  | 跟踪监控 | Metrics API / Dashboard | Spring Cloud Turbine  |
  | 降级熔断 | N/A                     | Spring Cloud Hystrix  |
  
- Serverless	无服务架构

# 通信

# 事务

- Atomic	原子性
- Isolation	隔离性
- Durability	持久性

## 本地事务

- ARIES理论

- 场景

  单服务单数据源

### 实现原子性与持久性

- Commit Logging

- Write-Ahead Logging

  - Analysis	分析阶段

    找出commit却未持久化的日志记录

  - Redo	重做阶段

    重新持久化

  - Undo	回滚阶段

    回滚对写入但未commit的记录

- Shadow Paging

- 策略

  - FORCE

    在事务提交后，同时完成写入

  - STEAL

    在事务提交前，允许提前写入

  - NO-FORCE

    在事务提交后，不要求立即写入

  |                     | FORCE | STEAL | NO-FORCE |
  | ------------------- | ----- | ----- | -------- |
  | Commit Logging      | √     | ×     | √        |
  | Write-Ahead Logging | √     | √     | √        |

### 实现隔离性

- 原理

  加锁

- 锁类型

  - X-Lock

  - S-Lock

  - Range Lock

    ``` sql
    # 范围锁示例
    SELECT * FROM books WHERE price < 100 FOR UPDATE;
    ```

## 全局事务

- 场景

  单服务多数据源

- X/Open XA	处理事务架构

  - 全局事务管理器，用于协调全局事务
  - 局部事务管理器，用于处理本地事务
  - JTA
    - XADataSource
    - XAResource

- 2PC	两段式提交

  - 准备阶段
  - 提交阶段

- 3PC	三段式提交

  - CanCommit
  - PreCommit
  - DoCommit

## 共享事务

- 场景

  多服务单数据源
  
  ``` mermaid
  graph LR
  	用户服务 --> 交易服务器
  	商家服务 --> 交易服务器
  	仓库服务 --> 交易服务器
  	交易服务器 --> 数据库
  ```
  
- 变体

  使用消息队列代替代理服务器(上图的交易服务器)，通过消息队列传递对数据库的改动。

## 分布式事务

- CAP理论

  - Consistency	一致性
  - Availabilty	可用性
  - Partition Tolerance	分区容忍性

- 一致性

  - 强一致性

    - CAP
    - ACID

  - 弱一致性

    即不保证一致性

  - 最终一致性

    - BASE

### 可靠事件队列

- 最终一致性
- 最大努力交付
- 问题
  - 缺乏隔离性

### TCC事务

Try-Confirm-Cancel

- 过程
  - Try	尝试执行阶段
  - Confirm	确认执行阶段
  - Cancel	取消执行阶段
- 问题
  - 业务侵入性强
- 类库
  - Seata

### SAGA事务

- 思想

  通过补偿代替回滚

### AT事务
