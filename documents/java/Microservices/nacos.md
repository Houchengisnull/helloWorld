[toc]

- **参考**
- [服务注册和配置中心Nacos](https://www.cnblogs.com/xuweiweiwoaini/p/13858963.html)

# 基础

Nacos，Dynamic Naming and Configuration Service。

个人看来，nacos的两个核心作用是:

- 服务发现
- 配置管理

以上是句大废话。

- 各种注册中心对比

| 服务注册和发现 | CAP模型 | 控制台管理 | 社区活跃度        |
| -------------- | ------- | ---------- | ----------------- |
| Eureka         | AP      | 支持       | 低（2.x版本闭源） |
| Zookeeper      | CP      | 不支持     | 中                |
| Consul         | CP      | 支持       | 高                |
| Nacos          | AP      | 支持       | 高                |

## 配置管理

### 基本概念

- **data-id**

  通常理解为配置文件的名称。

- **group**

  不同应用可能使用相同的配置文件名称，通过group来区分不同的应用。

- **namespace**

  用于区分开发或者生产环境。

# 启动

``` bat
# 单机启动
startup.cmd -m standalone
```

