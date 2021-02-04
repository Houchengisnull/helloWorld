

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
  
- **架构图**

  ![img](../images/javafx/2_1-jfxar_dt_001_arch-diag.png)

# Application

# 场景图

## Stage 舞台

`Stage`是在`JavaFX`中最为常见的顶级容器，继承自`Window`类。

- **Window声明**

  `javafx`顶级容器的实现类，通常我们调用它的`setScene(Scene)`方法，把`场景`交给它托管。`Window objects`将被`JavaFX Application Thread`构造与创建。

-  **Stage声明**

  ``` java
  public class Stage extends Window {}
  ```

## Scene 场景

`Scene Graph`，是构建`JavaFX`应用的入口。它是一个树状结构，包含一个根结点。

## Node

- **声明**

  ``` java
  @IDProperty("id")
  public abstract class Node implements EventTarget, Styleable {}
  ```

  每个节点都具有一个`id`、`样式类`(见`Styleable`)与`包围盒`。除了`Window`与`Scene`外，所有元素本质都是`Node`，即使是`Pane`。

# 布局

## 内置布局

面板是一种布局容器。

<hr>

- **BorderPane**

  从以`Border`命名来看，大概就是一个中心四条边的经典布局。

  通常，上方作为菜单来和工具栏，下方是状态栏，左边是导航栏，右边是附加信息面板，中间是核心工作区域。

  当`BorderPane`所在窗口比各区域内容所需空间大时，将多出的空间默认设置给中间区域。

  当窗口大小比各区域所需空间小时，各个区域就会重叠。重叠的顺序取决于各个区域设置的顺序。例如，如果各个区域设置的顺序是左、下、右，则当窗口变小时，下方区域会覆盖左边区域，而右边区域会覆盖下方区域。如果区域设置顺序是左、右、下，当窗口变小时，下方区域则会在覆盖到左边和右边区域之上。

- **水平盒子(HBox)**

- **垂直盒子(VBox)**

- **栈面板(StackPane)**

  将所有的节点放在一个堆栈中进行布局管理，后添加的节点会显示在前一个节点之上。

- **网格面板(GridPane)**

- **流面板(FlowPane)**

- **磁贴面板(TilePane)**

- **锚面板(AnchorPane)**

  `AnchorPane`布局面板可以让你将节点锚定到面板的顶部、底部、左边、右边或者中间位置。当窗体的大小变化时，节点会保持与其锚点之间的相对位置。一个节点可以锚定到一个或者多个位置，并且多个节点可以被锚定到同一个位置。

## CSS

# FAQ

## 错误: 缺少 JavaFX 运行时组件, 需要使用该组件来运行此应用程序

在`debug`时发生这个错误，但是从`javafx-archetype-fxml`这个模板直接拉下来的项目并没有发生这个问题。同样的，在直接运行时也没有问题。

这是`Java 9`新特性模块化导致的。

- **参考**

  [在 Intellij IDEA 里使用 OpenJFX (JavaFX)](https://my.oschina.net/tridays/blog/2222909)

  [jdk11 + maven 打包JavaFX11](https://blog.csdn.net/xizi1103/article/details/104015406)

## maven clean compile package javafx:run 报错: -release无法解析

将`maven`所使用的`jdk`换成`jdk15`。