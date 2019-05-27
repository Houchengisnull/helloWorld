[TOC]

# 概念

## ProcessInstance 与EXECUTION

- 存储于`ACT_RU_EXECUTION`
- ProcessInstance与业务是一对一的关系

- `EXECUTION`于`ProcessInstance`是多对一的关系，提醒在表`ACT_RU_EXECUTION`中字段`ID`_与`PROC_INST_ID_`可能存在多对一的情况，表示执行子流程。

  > 例如一个购物流程中除了下单、出库节点之外可能还有一个**付款子流程**，在实际企业应用中付款流程通常是作为公用的，所以使用子流程作为主流程（购物流程）的一部分。

## Task

### Assigned与Candidate

Assigned 已签收

Candidate （被某人委派该任务后，该流程实例处于）待签收

## 参考

https://blog.csdn.net/bigtree_3721/article/details/82889806

# API

`ProcessEnginee`和`Service`都是线程安全的

## ProcessEngine

## Service

### RepositoryService

涉及流程定义相关业务。即`静态信息`/`模版`，类似`Java`中的`class`与`static`声明。

`Spring`中可通过`@Repository`表示注入DAO层实例（持久层实例），Repository在此处同样应该以**“持久层”**中的**持久**来理解。

### RuntimeService

启动一个流程定义的新实例。类似：

``` java
Repository instance = new Repository();
```

`RuntimeService`中的“Runtime”同样可借鉴`JVM`中`instance`的概念。

### TaskService

一系列任务构成流程。

# Database命名

- ACT_RE_*

  Repository，涉及流程定义与流程静态资源。

- ACT_RU_*

  Runtime，涉及流程实例，任务，变量，异步任务等运行中的数据。**流程结束时将删除这些记录。**

- ACT_ID_*

  Identity，涉及身份信息，比如用户，组。

- ACT_HI_*

  History，涉及历史数据。

- ACT_GE_*

  通用数据，用于不同场景。