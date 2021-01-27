

[toc]

# 引言

> - **2021-1-27**
>
>   之前用`swing`做了一款`ReentrantReadWriteLock`的演示工具。
>
>   同时想将做把更多的并发工具做成演示程序，帮助自己与大家学习理解。
>
>   但发现自己原来写的代码太烂了。由于当时想以后慢慢再迭代，为求方便只考虑了`读写锁`的演示程序，再看代码觉得难以入手。其次自己对`swing`理解真的不深入，也觉得没有必要去细细研究。
>
>   所以打算学习一下`javafx`，看看是否能更优雅地完成这个项目。

-  **参考**
-  [JavaFX 15](https://openjfx.cn/index.html)
-  [JavaFX中文资料](http://www.javafxchina.net/blog)
- [JavaFX 教程 （中文）](https://code.makery.ch/zh-cn/library/javafx-tutorial/)

# 概述

- **什么是`javafx`**

  我觉得`javafx`就是一个`java`实现的ui类库。

  但是这个网站说了一大堆[DOC-01-01 JavaFX概览](http://www.javafxchina.net/blog/2015/06/doc01_overview/)。

  有兴趣的读者可以看看。

# FAQ

## <font color='red'>错误: 缺少 JavaFX 运行时组件, 需要使用该组件来运行此应用程序</font>

在`debug`时发生这个错误，但是从`javafx-archetype-fxml`这个模板直接拉下来的项目并没有发生这个问题。同样的，在直接运行时也没有问题。



## maven clean compile package javafx:run 报错: -release无法解析

将`maven`所使用的`jdk`换成`jdk15`。