[TOC]

# 起子

2019-04-29

IBM Websphere上有处关于**类加载机制**的设置，于UAT环境上设置前两项可部署war，于DEV却要设置后两项否则无法加载JSP。由于想弄清楚这个问题，于是想深入研究类加载机制。但是观遍百度，并不能满足我内心好奇，我对这些答案不满意。而且一遍遍的百度"各种加载器的应用场景"，"Jar包如何查找class并装载"，"如何配置manifest.mf"让我感到厌烦。我渴望知道JVM，\*.java，\*.class是如何工作的。

***我眼前仿佛有一卷幽帘，挡住了所有秘密。***

- 配置应用程序使用类装入器的方式

https://www.ibm.com/support/knowledgecenter/zh/SSAW57_9.0.0/com.ibm.websphere.nd.multiplatform.doc/ae/trun_app_classload.html

# Java体系

## Sun官方定义Java体系

- Java 程序设计语言
- 各种硬件平台上的JVM
- Class
- Java API
- 来自商业机构和开源社区的第三方Java类库

>  我们将Java程序设计语言、JVM、Java API统称JDK(Java Development Kit)

## 发展历史

- 1991.4 James Gosling为开发一种能够在各种消费性电子产品（如冰箱，机顶盒）上运行的程序架构`Oak(橡树)`

- 1995.5.23 `Oak`更名为`Java`，提出"`write once, run anywhere`"口号

- 1996.1.23 `JDK1.0`发布

- 1997.2.19 `JDK1.1`，Java技术一些最基础的支撑点在此版本发布，比如`JDBC`,`jar`,`JavaBeans`,`RMI`;Java语法也有了一定发展，比如`Inner Class(内部类)`,`Reflection(反射)`

- 1998.12.4 `JDK1.2`发布，工程代号——PlayGround。Sun在这个版本中将Java技术体系拆分为3个方向J2SE,J2EE与J2ME。

- 1999.4.27 `HotSpot`由Longview Technologise一间小公司发布。`HopSpot`在`JDK1.2`中作为附加程序发布，`JDK1.3`作为Sun默认虚拟机。

  >  该公司在1997被Sun收购。

- 2000.5.18 `JDK1.3`发布，代号Kestrel。此次改进表现在一些类库上，比如数学运算和Timer API。`JNDI`作为一项平台级服务提供，使用`CORBA IIOP`来实现`RMI`通信协议。

  > 自JDK1.3后，Sun维持约两年发布一个JDK版本的习惯，以动物命名，修正版本以昆虫命名。

- 2002.2.13 `JDK1.4`，代号Merlin。Compaq、Fujitsu、SAS、Symbian、IBM等公司实现自己独立JDK1.4。JDK1.4发布许多新特性，比如正则表达式、异常链、NIO、日志类、XML解析器及XSTL转换器等。

  > JDK1.4是Java走向成熟的一个标志版本。

  > 同年微软.Net Framework发布

- 2004.9.30 `JDK1.5`发布，代号Tiger。

  在**语法层面**增加`自动装箱`、`泛型`、`动态注解`、`枚举`、`可变长参数`、`遍历循环`等语法特性。

  在虚拟机与API层面，改进Java内存模型、提供`java.util.concurrent`并发包等

- 2006.12.11 `JDK1.6`发布，工程代号Mustang，启用`Java SE6`，`Java EE6`，`Java ME6`的命名方式。

  改进包括:提供动态语言支持（通过内置`Mozilla JavaScript Rhino`引擎实现），提供编译API和微型HTTP服务器等。

  同版本对Java虚拟机内部做出大量改进包括`锁与同步`，`垃圾回收`，`类加载`等方面的算法都有相当多改动。

- 2006.11.13 Sun宣布最终会将Java开源。并建立OpenJDK组织对源码进行管理。（除极少量Encumbered Code版权代码，Sun本身也无权限进行开源处理）

  > OpenJDK质量主管层表示，在JDK1.7中除了代码文件头版权注释外，代码基本完全一样。

- 2009.2.19 `JDK1.7`，工程代号Dolphin。

### JDK8 日落见神谕

发布于2014.3.18。

> 距离JDK7发布已经过去近5年。之所以如此之久不仅因为代码复杂性增加，还因为一系列事故导致Sun在JDK发展以外事情上耗费太多，导致力不从心。
>
> 在2009.4.20日被Oracle以74亿美元收购。
>
> 在2011年JavaOne大会上，Oracle公司提到JDK1.9长远规划，希望未来Java虚拟机能管理以GB计Java堆，更高效与本地代码集成。

#### 新特性

- **Lambda 表达式** − Lambda允许把函数作为一个方法的参数（函数作为参数传递进方法中。
- **方法引用** − 方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
- **默认方法** − 默认方法就是一个在接口里面有了一个实现的方法。
- **新工具** − 新的编译工具，如：Nashorn引擎 jjs、 类依赖分析器jdeps。
- **Stream API** −新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。
- **Date Time API** − 加强对日期与时间的处理。
- **Optional 类** − Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。
- **Nashorn, JavaScript 引擎** − Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。
- **接口默认方法Default**
- **使用 :: 关键字来传递方法或者构造函数引用 **

### JDK9

- **模块系统**：模块是一个包的容器，Java 9 最大的变化之一是引入了模块系统（Jigsaw 项目）。
- **REPL (JShell)**：交互式编程环境。
- **HTTP 2 客户端**：HTTP/2标准是HTTP协议的最新版本，新的 HTTPClient API 支持 WebSocket 和 HTTP2 流以及服务器推送特性。
- **改进的 Javadoc**：Javadoc 现在支持在 API 文档中的进行搜索。另外，Javadoc 的输出现在符合兼容 HTML5 标准。
- **多版本兼容 JAR 包**：多版本兼容 JAR 功能能让你创建仅在特定版本的 Java 环境中运行库程序时选择使用的 class 版本。
- **集合工厂方法**：List，Set 和 Map 接口中，新的静态工厂方法可以创建这些集合的不可变实例。
- **私有接口方法**：在接口中使用private私有方法。我们可以使用 private 访问修饰符在接口中编写私有方法。
- **进程 API**: 改进的 API 来控制和管理操作系统进程。引进 java.lang.ProcessHandle 及其嵌套接口 Info 来让开发者逃离时常因为要获取一个本地进程的 PID 而不得不使用本地代码的窘境。
- **改进的 Stream API**：改进的 Stream API 添加了一些便利的方法，使流处理更容易，并使用收集器编写复杂的查询。
- **改进 try-with-resources**：如果你已经有一个资源是 final 或等效于 final 变量,您可以在 try-with-resources 语句中使用该变量，而无需在 try-with-resources 语句中声明一个新变量。
- **改进的弃用注解 @Deprecated**：注解 @Deprecated 可以标记 Java API 状态，可以表示被标记的 API 将会被移除，或者已经破坏。
- **改进钻石操作符(Diamond Operator)** ：匿名类可以使用钻石操作符(Diamond Operator)。
- **改进 Optional 类**：java.util.Optional 添加了很多新的有用方法，Optional 可以直接转为 stream。
- **多分辨率图像 API**：定义多分辨率图像API，开发者可以很容易的操作和展示不同分辨率的图像了。
- **改进的 CompletableFuture API** ： CompletableFuture 类的异步机制可以在 ProcessHandle.onExit 方法退出时执行操作。
- **轻量级的 JSON API**：内置了一个轻量级的JSON API
- **响应式流（Reactive Streams) API**: Java 9中引入了新的响应式流 API 来支持 Java 9 中的响应式编程。

### JDK10

- JEP286 var 局部变量类型推断。
- JEP296 将原来用 Mercurial 管理的众多 JDK 仓库代码，合并到一个仓库中，简化开发和管理过程
- JEP304 统一的垃圾回收接口。
- JEP307 G1 垃圾回收器的并行完整垃圾回收，实现并行性来改善最坏情况下的延迟。
- JEP310 应用程序类数据 (AppCDS) 共享，通过跨进程共享通用类元数据来减少内存占用空间，和减少启动时间。
- JEP312 ThreadLocal 握手交互。在不进入到全局 JVM 安全点 (Safepoint) 的情况下，对线程执行回调。优化可以只停止单个线程，而不是停全部线程或一个都不停。
- JEP313 移除 JDK 中附带的 javah 工具。可以使用 javac -h 代替。
- JEP314 使用附加的 Unicode 语言标记扩展。
- JEP317 能将堆内存占用分配给用户指定的备用内存设备。
- JEP317 使用 Graal 基于 Java 的编译器，可以预先把 Java 代码编译成本地代码来提升效能。

### JDK11

- JEP181 Nest-Based访问控制
- JEP309 动态类文件常量
- JEP315 改善Aarch64 intrinsic
- JEP318 无操作垃圾收集器
- JEP320 消除Java EE和CORBA模块
- JEP321 HTTP客户端(标准)
- JEP323 局部变量的语法λ参数
- JEP324 Curve25519和Curve448关键协议
- JEP327 Unicode 10
- JEP328 飞行记录器
- JEP329 ChaCha20和Poly1305加密算法
- JEP330 发射一列纵队源代码程序
- JEP331 低开销堆分析
- JEP332 传输层安全性(Transport Layer Security,TLS)1.3
- JEP333 动作:一个可伸缩的低延迟垃圾收集器 (实验)
- JEP335 反对Nashorn JavaScript引擎

- JEP336 反对Pack200工具和API

### JDK12

- JEP189 Shenandoah: A Low-Pause-Time Garbage Collector (Experimental) ：新增一个名为 Shenandoah 的垃圾回收器，它通过在 Java 线程运行的同时进行疏散 (evacuation) 工作来减少停顿时间。
- 230  Microbenchmark Suite：新增一套微基准测试，使开发者能够基于现有的 Java Microbenchmark Harness（JMH）轻松测试 JDK 的性能，并创建新的基准测试。
- 325 Switch Expressions (Preview) ：对 switch 语句进行扩展，使其可以用作语句或表达式，简化日常代码。
- 334 JVM Constants API ：引入一个 API 来对关键类文件 (key class-file) 和运行时工件的名义描述（nominal descriptions）进行建模，特别是那些可从常量池加载的常量。
- 340 One AArch64 Port, Not Two ：删除与 arm64 端口相关的所有源码，保留 32 位 ARM 移植和 64 位 aarch64 移植。
- 341 Default CDS Archives ：默认生成类数据共享（CDS）存档。
- 344  Abortable Mixed Collections for G1 ：当 G1 垃圾回收器的回收超过暂停目标，则能中止垃圾回收过程。

- JEP346  Promptly Return Unused Committed Memory from G1 ：改进 G1 垃圾回收器，以便在空闲时自动将 Java 堆内存返回给操作系统。

# 参考

## 书籍

- 《深入理解Java虚拟机》

## 网站

https://www.runoob.com/java/java8-new-features.html

https://blog.csdn.net/qq934235475/article/details/82220076

http://www.runoob.com/java/java9-new-features.html