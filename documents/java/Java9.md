[toc]

# New Feature

- **Module System**
- **Modular JDK**

<hr>

- **REPL(JShell)**

  交互式编程环境，类似`python command`。

- **进程 API**

  通过`Process`、`ProcessHandle`及其嵌套接口`Info`来控制管理操作系统进程。

<hr>

不一一枚举了，直接看官网吧。

- **refer**
- [JDK 9](http://openjdk.java.net/projects/jdk9/)
- [JDK 9 Release Notes](https://www.oracle.com/java/technologies/javase/v9-issues-relnotes.html)
- [Java Platform, Standard Edition What’s New in Oracle JDK 9](https://docs.oracle.com/javase/9/whatsnew/toc.htm#JSNEW-GUID-C23AFD78-C777-460B-8ACE-58BE5EA681F6)

# Module

- **refer**
- [jdk9模块快速入门](https://segmentfault.com/a/1190000015420994)

## 说在前面

`Modular`是JDK9最大的变化。

但如非必要，我不建议特地去学习。

- **refer**

- [Java 14都快出来了，为什么还有那么多人执着于Java 8？ - blindpirate的回答 - 知乎 ](https://www.zhihu.com/question/360985479/answer/956242314)

- [Java 14都快出来了，为什么还有那么多人执着于Java 8？ - 北南的回答 - 知乎](https://www.zhihu.com/question/360985479/answer/1451078193)
- [JDK 9 发布仅数月，为何在生产环境中却频遭嫌弃？](https://blog.csdn.net/csdnnews/article/details/78722304)

因为在学习`javafx13`的过程中在运行时有一个`bug`，是由于`modular`导致的。为了研究与弄清问题本质，所以才特地了解一下`Modular`。

在了解`Modular`的过程中，觉得这个功能比较鸡肋。`Java`真的要舍去自己的优势去和`python`与`nodejs`抢市场吗？也许编程语言真的有它的生命周期，`Java`正在渐渐老去。

`Oracle`真的要瞄准好`Java`的未来才是。

> - **Glavo**
>
>   个人感觉挺失望的。
>
>   <u>`Java 9`库上的改动很小，`API`上的变动更小，而语法新特性貌似一样都没有，所以对我来说，`Java 9`没有太大的吸引力。</u>

## 模块化的历史

> - **2021-6-5**
>
>   今天在读《深入理解Java虚拟机》时，读`<1.5 展望Java技术的未来>`，发现对模块化的一些叙述。
>
>   觉得有点意思，便记录下来。
>   
> - ***模块化是解决应用系统与技术平台越来越复杂、越来越庞大问题的重要途经。***
>
>   作为一名`Java开发人员`，我理解的`应用系统`便是`Java程序`，而`技术平台`指的是`JDK`或`JRE`。大部分情况下，我们在生产环境安装的都是一整套`JRE`，而`JDK8`的`JRE`就有180MB。
>
>   如果我只是开发一个简单的程序，根本不会用到`JRE`所有的功能。要是我们能把`JRE`拆开那将节省很多空间。
>
> - ***随着OSGi技术的发展，各个厂商在JCP中对模块化规范的激烈斗争***
>
>   要拆分不容易（要解耦不容易），我们的模块之间依赖必须要低（`高内聚低耦合`）,而`OSGi(Open Service Gateway Initiative)`就是做这样一件事情，不单单是逻辑上解耦，甚至是物理上的解耦。
>
>   [【OSGI】1.初识OSGI-到底什么是OSGI](https://blog.csdn.net/acmman/article/details/50848595)

### 模块化之争

>在2007年，`Sun`提出`JSR-277: Java Module System`，但受挫于`IBM`提交的`JSR-291：Dynamic Component Support for Java SE(实际就是OSGi R4.1)`。但`Sun`作为`Java创始者`，不能接受一个无法由它控制的规范，在整个`JDK 6`期间拒绝把任何`模块化技术`内置到`JDK`中。
>
>在`JDK7`期间，`SUN`提交了`JSR-294：Improved Modularity Support in the Java Programming Language`，尽管该提案未被通过，但`Sun`独立于`JCP`在`Open JDK`中建立了一个`Jigsaw`（拼图）。

总之在模块化之争中，`Sun`处于劣势，实在是有够好笑的，整个过程是像小孩子打架一样。

不过`Java`的主战场是`Web`领域，模块化这把手术刀是否有用武之地？在这点上我无法和`《深入理解Java虚拟机》`的作者持相同意见。

## Define

模块是一个被命名的代码与数据的自描述集合。

- **代码**

- **数据**	`resource`和一些静态信息。

## Module JDK

- [JEP 200 : The Modular JDK](http://openjdk.java.net/jeps/200)

![img](../images/java9/jdk8_jdk9_compare.png)

`JDK9`可以在<u>编译、构建、运行期间</u>进行组合。`oracle`将原本复杂的`JDK`拆分成各个精简的模块。

- **查看JDK模块**

  ``` shell
  java --list-modules
  ```

此外，在我们使用模块化功能时，通过反编译`module-info.class`可见默认添加了`java.base`模块。

## Module System

- [JEP 261 : Module System](http://openjdk.java.net/jeps/261)

### Phases 阶段

新增`link time`在编译阶段与运行阶段期间，用于装配和优化一个自定义`run-time image`——运行镜像，并提供连接工具`jlink`。

### Motivation 动机

基于`Jigsaw`实现，令`Java`程序更容易扩展到小型设备上。

## 声明模块

`模块`的自描述表现其声明中。

``` java
module com.foo.bar{
    requires org.baz.qux;
    exports com.foo.bar.alpha;
    exports com.foo.bar.beta;
}
```

- **requires**	表明`com.foo.bar`模块依赖其他模块。

- **exports**	指定`package`中的`public `可以被其他模块使用。

- **opens**	指定开放包

- **open**	指定开放模块

- **uses**	使用接口的名字，实现可由其他模块提供

- **provides .. with...**	指定一个或多个接口的实现类

  ``` java
  module java.computer{
      provieds com.computer.Icomputer with com.computter.impl.Dell;
  }
  ```

按照约定，模块声明文件`module-info.java`在`root目录`下。

# jlink

`Modular`是为了更容易地扩展至小型设备上。如果我们的程序依然使用原来巨大的`jdk/jre`那么毫无意义。**所以`java 9`新增了`jlink`工具以简化`jre`，它将按需生成`jre`。**

> 裁剪后的`jre`应该被称为`run-time image`。

- **查看项目模块**

  ``` shell
  jdepts --list-deps Helloworld.jar
  ```

- **裁剪**

  ``` java
  // `java.base`是最基础的模块，所有模块依赖它。
  jlink --module-path jmods --add-modules java.base --output minijre
  ```

# jmod

自`jdk 9`后，其目录结构发生改变。多了一个`jmods`文件夹，里面有很多`*.jmod`。

`*.jmod`本质是压缩包，里面包含了`*.class`和其他资源文件。

- **jar >> jmod**

  ``` shell
  jmod create --class-path Helloworld.jar Helloworld.jmod
  ```

- **运行**

  ``` shell
  java --module-path hello.jar --module hello.world
  ```

# FAQ

## IntelliJ Idea 2018无法正确运行Java 9

更新至`IntelliJ Idea 2020`

## lombok无法编译通过

在更新到`IntelliJ Idea 2020`后，报错信息也不一样了。

根据新的报错信息，发现还需要引入两个依赖。

``` xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.3.0.Final</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
```

<hr>

- **lombok version**

  至少使用`1.18.6`版本，否则将会导致找不到任一模块的错误而无法编译。

<hr>

如果需要在控制台打印日志信息，我们还要添加具体的日志实现。比如`logback`

``` xml
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.1.2</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-core</artifactId>
    <version>1.1.2</version>
</dependency>
```

我直接添加了这个能正常打印了。

<hr>

- **refer**
- [Error: java.lang.module.ResolutionException: Module lombok does not read a module that exports org.mapstruct.ap.spi #1806](https://github.com/rzwitserloot/lombok/issues/1806)
- [IDEA 2020.3 lombok失效问题](https://blog.csdn.net/weixin_46927507/article/details/110947964)
- [mapstruct使用详解](https://www.cnblogs.com/mmzs/p/12735212.html)