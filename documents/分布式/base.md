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
- Serverless	无服务架构