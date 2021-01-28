[toc]

`Java9`是一个过渡版本，`oracle`的下一个长期维护版本是`java11`。所以有人说：“也许以后Java世界分裂为两种：Java8和Java11以后。”

# 新特性

- **模块系统**

  `Java9`最大变化。

- **REPL(JShell)**

  交互式编程环境，类似`python command`。

- **HTTP2 客户端**

- **改进Javadoc**

  支持在`Javadoc`中进行搜索。

- **多版本兼容JAR**

  允许创建特定版本`JRE`中运行时选择使用`class`版本。

- **集合工厂方法**

  在`List`、`Set`和`Map`接口中，新的静态工厂方法可以创建这些集合的不可变实例。

- **进程 API**

  通过`Process`、`ProcessHandle`及其嵌套接口`Info`来控制管理操作系统进程。

- **改进Stream API**

- **改进try-with-resources**

- **改进@Deprecated**

- **改进Diamond Operator**

- **改进Optional**

- **多分辨率图形API**

- **改进CompletableFuture API**

- **轻量级JSON API**

- **响应式流API**

<hr>

- **参考**
- [Java 9 新特性](https://www.runoob.com/java/java9-new-features.html)

- [Java 14都快出来了，为什么还有那么多人执着于Java 8？ - blindpirate的回答 - 知乎](https://www.zhihu.com/question/360985479/answer/956242314)
- [也许以后Java世界分裂为两种：Java8和Java11以后 - 忠实的码农的文章 - 知乎](https://zhuanlan.zhihu.com/p/59585738)

> - **2021-1-28**
>
>   一方面，个人觉得自`Oracle`维护`JDK`以来，有许多鸡肋或者无足轻重的改动，甚至无视`Java`特性过于追求完美的引进一些其他语言的特性，让`Java`在往一个奇怪的方向靠拢。
>
>   另一方面，也许这就是程序员精益求精的体现。
>
>   也许是`oracle`没有瞄准好`Java`的未来在哪里，以致于`JDK8`仍旧是`Java`中最活跃的版本。
>
>   另外大家能看看这个升级JDK的血泪史——[Java 14都快出来了，为什么还有那么多人执着于Java 8？ - blindpirate的回答 - 知乎](https://www.zhihu.com/question/360985479/answer/956242314)

# 模块化 Modular

> - **2021-1-28**
>
>   由于使用`JavaFX`的需要，简单看一下`Java9`的模块化。

## 为什么要模块化



<hr>

- **refer**
- [为什么要模块化](https://www.cnblogs.com/tigerhsu/p/9877733.html)
- [关于java9模块化的意义](https://www.v2ex.com/amp/t/459610)
- [Java 9的模块化--壮士断"腕"的涅槃 - 欧阳辰的文章 - 知乎](https://zhuanlan.zhihu.com/p/24800180)