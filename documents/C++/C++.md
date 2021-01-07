[TOC]

# 环境配置

要开始`C/C++`编程，我们常常要安装以下几个组件：

- **依赖类库**	比如`GNU`、`MinGW`、`MinGW-w64`，相当于`Java`中的`JDK`。
- **编译器**	比如或者`clang`。`clang`仅是一个编译前端，并未实现`c标准库`。所以`c标准库`由`MinGW`提供。
- **构建工具**	比如`CMake`非必须，类似`Maven`、`Gradle`等工具。

## 基本概念

开始之前先补充一些计算机科学的历史。

### UNIX

很神奇吧，居然还有`UNIX`。是应该补补计算机历史的课程了。

`UNIX`是一个`分时系统`，替代了`批处理系统`。它由`C语言之父`——Dennis Ritchie开发。

`UNIX`在结构上主要分为：

- **kernel**	内核

  处理机、进程管理、存储管理、设备管理和文件系统

- **shell**	外壳

  用户界面、系统程序以及应用程序

此外`UNIX`包括其它特点：

- 良好的用户界面
- 文件系统是树形结构
- 大部分由C语言编写，5%由汇编语言编写
- 提供进程间简单通信

### GNU

意义为**GNU's Not Unix!**。

`GNU`是`RichardStallman`在1983年发起的自由软件集体协作**计划**。其目标是创建一套完全**Free**的操作系统。

> `GNU Project`中有自己的内核——`Hurd`，但是其至今无法稳定使用。

### Linux

- 作者`Linux Torvalds`。

- 全称`GNU/Linux`。

  `Linux内核`与其他`GNU软件`结合。

- 基于`POSIX`和`Unix`的多用户、多任务、支持多线程与多CPU的操作系统。

### GCC

`GNU Compiler Collection`，`GNU`编译机集合。

### MinGW

`MinGW`( Minimalist GNU for Windows ），**是将`GCC编译器`和`GNU Binutils`移植Windows 32下**的产物，包括一系列头文件（Win32API）、库和可执行文件。

我之所以选择MinGW，是因为我暂时没有深入C++开发的需求，不需要安装庞大的`visual studio`。

- MinGW下载地址

<https://sourceforge.net/projects/mingw/files/latest/download?source=files>

- MinGW安装步骤
  - 选择需要安装的Categories(条目)，以下为本人选择Categoies
    - mingw-developer-toolkit
    - mingw32-base
    - mingw32-gcc-c++
    - msys-base
  - 选择“Installation-Apply Change”

- 配置Path

  将%filepath%\MinGW\bin配置在`Path`（环境变量下）

- 参考

  https://www.cnblogs.com/qcssmd/p/5302052.html

### MinGW-W64

一款开源免费的编译器。

- 与`MinGW`的区别

  `MinGW`只能生成`32位可执行程序`；`MinGW-w64`则可以生成`64位或32位可执行程序`。

  此外，`MinGW`项目已经停止维护。

## Python依赖

在安装python类库`wordcloud`时出现以下错误：

``` shell
  error: Microsoft Visual C++ 14.0 is required. Get it with "Microsoft Visual C++ Build Tools": https://visualstudio.microsoft.com/downloads/
```

本想通过使用轻量的Visual Code绕过这个问题，但是Visual Code仅提供插件而不安装编译器。所以我们需要安装`C/C++`运行环境。

### Visual Cpp Build Tools

百度云地址

>  链接：https://pan.baidu.com/s/1uRAPZXu9uyzezCc-r2ORUA 
>  提取码：qu7o 

> 如果条件允许，还是直接安装 visual studio比较好，在安装与选择版本上浪费太多时间实在是太boring了。

# 编译

## 编译过程

### 编译预处理

读取源代码，对其中伪指令和特殊符号处理

> 伪指令 例如以*#*开头指令

#### 伪指令

- 宏定义

  ``` 
  #define Name tokenName
  ```

- 条件编译

  通过`条件编译`决定`编译器`对不同代码进行不同处理

  - #ifdef

  - #ifndef
  - #else
  - #elif
  - #endif

- 头文件

  - #include #{filename}
  - #include <>

- 特殊符号

  - 例如

    - LINE标识被解释为当前行号

    - FILE 被编译源程序名称

### 编译

将代码翻译成中间代码或汇编代码

### 优化

### 汇编过程

将汇编语言翻译成机器语言

### 链接程序

由汇编语言生成的`可执行文件` 并不能立即执行，其中可能还有许多没有解决的问题。

#### 原因

例如，某个源文件中的函数可能引用了另一个源文件中定义的某个符号；在程序中调用某个库文件的函数.......

#### 链接处理

##### 动态链接

在`可执行程序`中记录下“`共享对象`”信息。在运行时，`动态链接库`相关内容映射到相应进程虚拟地址空间

###### advantage

- 节省空间
- 程序升级容易
- 不同数据间的数据和指令访问都集中在了同一个共享模块，可以减少物理页面的换入换出，增加CPU的缓存命中率
- 插件的引入，程序运行时可以动态选择加载的各种模块，即选择插件
- 加强程序的兼容性  动态链接库相当于在程序和操作系统间增加一个中间层，消除程序对不同平台之间的依赖性

##### 静态链接

将`静态链接库`代码拷贝至`可执行程序`中。

###### advantage

- 不同程序模块可以独立开发和测试，最终链接供用户使用，提高开发效率；

###### shortcoming

- 浪费空间

  多进程情况下，每个进程都要保存静态链接库副本

- 更新困难

  链接众多目标其中一个更新后，需要重新编译链接



## 参考

https://blog.csdn.net/wyb19890515/article/details/7211006

https://www.cnblogs.com/improvement/p/4719325.html

# 宏定义

## ifndef

## define