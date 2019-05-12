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

# Java虚拟机发展史

> 也许还有一些程序员会注意到BEAJRockit和**IBM J9**，但对JVM的认识不仅仅这些。

2019-05-07

在使用WebSphere过程中认识到了OpenJ9,下面是OpenJ9的源码地址：

https://github.com/eclipse/openj9.git

## Sun Classic/Exact VM

- Sun Classic

  以今天角度来看技术比较原始，但其为`世界第一款商用Java虚拟机`。

  `JDK1.0`中自带虚拟机即`Classic VM`。该虚拟器只能使用`純解释器方式`执行Java代码。如果要使用`JIT编译器`就要使用外挂，例如`sunwjit`。由于解释器与编译器无法配合工作，就意味着使用编译器时，不得不对每一个方法，每一行代码进行编译，不论它们执行的频率是否有价值。基于程序响应时间压力，这些编译器不敢应用编译耗时较高的`优化技术`。即使使用了`JIT编译器`，执行效率也无法与`C/C++`媲美。

  “Java语言很慢”的形象就是在这个时候在用户中心树立起来的。

  * 优化技术比如指对生成字节码的优化

- Exact VM

  Sun虚拟机团队为解决`Classic VM`面临各种问题，曾发布过`Exact VM`。其初具现代高性能虚拟机雏形：`两级即时编译器`，`编译器与解释器混合工作模式`。它采用`准确式内存管理（Exact Memory Management）`而得名，即其准确知道内存中某位置数据的具体类型。

  譬如能分辨内存中一个32位整数123456，它到底是一个reference类型指向123456的内存地址，还是数值为123456的整数。如此才能在GC时判断`堆`上数据是否还可能被使用。

## HotSpot

由`LongView Technologies`小公司设计。最初并非是为Java设计，其源于`Strongtalk VM`，而这款虚拟机中相当多技术又是来源于一款支持Self语言实现“达到C语言50%以上执行效率”为目标而设计的虚拟机。

> `HotSpot`指热点代码探测技术

### 上帝规则：局部性原理

局部性原理：在一段时间内，整个程序的执行仅限于程序的某一部分，相应地，程序访问的存储空间也局限于某个内存区域。

- 时间局部性：如果程序中某条指令一旦执行，不久之后该指令可能再次被执行。如果某条数据被访问，不久后该数据可能再次被访问。
- 空间局部性：一旦程序访问了某个存储单元，不久之后其附件的存储单元也将被访问。

涉及知识：`缓存`、`热点代码`等。

## BEA JRockit

`BEA公司`已经被`Oracle`收购

为服务器硬件和服务器端应用场景高度优化虚拟机，不关注程序启动速度。

## IBM J9 VM

开发目的，作为IBM公司各种Java产品的执行平台，例如：`websphere`

## Apache Harmony/Dalvik VM

两者不止是“Java虚拟机”。

### Apache

#### 号外

`Apache` 曾要求Sun提供`TCK(Technology Compatibility Kit)`的使用授权，但一直遭到拒绝，直至两者关系愈发僵化，以`Apache`退出`JCP（Java Community Process）`收场。

## Dalivik VM

`Android`平台核心核心组成之一。

并非Java虚拟机，也未遵从Java虚拟机规范，不能直接执行`Class`文件。

但其执行`dex(Dalvik Executable)`由`Class`文件转化而来，使用Java语法编写程序，可以使用大部分Java API。

## TCK

Java兼容性测试

# Java技术未来发展方向

- 模块化
- 混合语言
- 多核并行
- 64 bit 虚拟机

## OSGi (Open Service Gateway Initiative)

- 背景

  > ​    我们来回到我们以前的某些开发场景中去，假设我们使用SSH(struts+spring+hibernate)框架来开发我们的Web项目，我们做产品设计和开发的时候都是分模块的，我们分模块的目的就是实现模块之间的“解耦”，更进一步的目的是方便对一个项目的控制和管理。
  > 我们对一个项目进行模块化分解之后，我们就可以把不同模块交给不同的开发人员来完成开发，然后项目经理把大家完成的模块集中在一起，然后拼装成一个最终的产品。一般我们开发都是这样的基本情况。
  >
  > ​    那么我们开发的时候预计的是系统的功能，根据系统的功能来进行模块的划分，也就是说，这个产品的功能或客户的需求是划分的重要依据。
  >
  > ​    但是我们在开发过程中，我们模块之间还要彼此保持联系，比如A模块要从B模块拿到一些数据，而B模块可能要调用C模块中的一些方法(除了公共底层的工具类之外)。所以这些模块只是一种逻辑意义上的划分。
  >
  > ​    最重要的一点是，我们把最终的项目要去部署到tomcat或者jBoss的服务器中去部署。那么我们启动服务器的时候，能不能关闭项目的某个模块或功能呢？很明显是做不到的，一旦服务器启动，所有模块就要一起启动，都要占用服务器资源，所以关闭不了模块，假设能强制拿掉，就会影响其它的功能。  

- 目的

  `模块解耦`

### 参考

- 初始OSGi

  https://www.cnblogs.com/zhaoxinshanwei/p/8376696.html

  https://blog.51cto.com/9291927/2125230

# 卷2 Java内存区域与内存溢出异常

## 程序计数器

`Program Counter Register`

是一块较小内存空间，可以看作是当前`线程`执行`字节码`的行号指示器。

在`JVM`的概念模型里，字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令，分支，循环，跳转，异常处理，线程恢复等基础功能都需要依赖这个计数器。

JVM多线程通过线程轮流切换并分配处理器执行时间方式来执行。

在任何一个确定的时刻，一个处理器（对于多核处理器来说是一个内核）都只会执行一条线程中的`指令`。因此，为了线程切换后能恢复到正确的执行位置，每条线程都需要有一个独立的`程序计数器`，各条线程之间计数器互不影响，独立存储。

我们称这类内存区域为`线程私有`。

> 程序计数器 存放CPU下一条工作指令地址。 ——《码农翻身》
>
> 该工作指令指向内存中某个地址，例如OxFFFFFFF0。

要谨记一条概念：`线程`是`CPU`调度最小单元。

CPU`与`线程`相互"作用"（使用作用这个词语是因为笔者不清楚两者之间具体发生了什么不可描述的事情，也不清楚两者对于`内存`、`操作系统`是什么样一个概念，但两者一定联系紧密），`线程`通过`程序计数器`保存下一条执行命令地址。



如果线程正在执行一个Java方法，这个计数器记录的是正在执行虚拟机字节码指令地址。

如果正在执行的是Native方法，这个计数器值为空（Undefined）。此内存区域是唯一一个在JVM规范中没有规定任何`OutOfMemoryError`的区域。

# 参考

## 书籍

- 《深入理解Java虚拟机》
- 《码农翻身》

## 网站

https://www.runoob.com/java/java8-new-features.html

https://blog.csdn.net/qq934235475/article/details/82220076

http://www.runoob.com/java/java9-new-features.html