[TOC]

# 环境配置

要开始`C/C++`编程，我们常常要安装以下几个组件：

- **依赖类库**	比如`GNU`、`MinGW`、`MinGW-w64`，相当于`Java`中的`JDK`。
- **编译器**	比如`g++`或者`clang`。`clang`仅是一个编译前端，并未实现`c标准库`。所以`c标准库`由`MinGW`提供。
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

`GNU`是`Richard Stallman`在1983年发起的自由软件集体协作**计划**。其目标是创建一套完全**Free**的操作系统。

> `GNU Project`中有自己的内核——`Hurd`，但是其至今无法稳定使用。

- [1985] 成立“自由软件基金会”（Free Software Foundation)，为`GNU Project`提供技术、法律、财政支持。
- [1990] `GNU项目组`使用`Lisp`编写`Emacs`、`GCC`
- [1991] `Linus`发布`Linux`内核
- [1992] `Linux内核` + `GNU软件`=`GNU/Linux`

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

### LLVM

- **全名**	`Low Level Virtual Machine`
- **作者**	`Chris Latter`
- **背景**	`Chris Latter`本想实现一个底层虚拟机，类似`JVM`，但是后来`LLVM`从未作为虚拟机使用。但人们仍然叫它`LLVM`，即使它与虚拟机毫无关系。

#### 编译器结构

- **传统编译器**

  ![img](../images/c++/传统编译器结构.webp)
  
- **LLVM架构**

  ![img](../images/c++/LLVM架构.webp)

> - **Frontend**	前端：词法分析、语法分析、语义分析、生成中间代码
> - **Optimizer**	优化器：优化中间代码
> - **Backend**	生成机器码

<hr>

#### Feature 特点

- 不同前后端使用统一中间代码`LLVM Intermediate Representation(LLVM IR)`
- 如果需要支持一种新的编程语言，只需要实现一个新的前端
- 如果需要支持一种新的硬件设备，只需要实现一个新的后端
- 优化阶段是一个通用阶段，针对的是同一的`LLVM IR`
- 相比之下，GCC的前端和后端没有分离，耦合在一起，所以`GCC`为了支持新的语言或者目标平台变得特别困难
- `LLVM`现在被作为实现各种静态和运行时编译语言的通用基础结构（`GCC家族`、`Java`、`.NET`、`Python`、`Ruby`等

> - license
> - clang
> - 良好定义与模块化
> - 足够年轻、健壮，未被各方修改成一个难以被外人理解的奇形怪状的怪物。
>
> ——知乎用户 狗肉 花与果


<hr>

- 参考
- <a href='https://www.jianshu.com/p/1367dad95445'>深入浅出让你理解什么是LLVM</a>

- <a href='https://www.zhihu.com/question/20235742'>Clang 比 GCC 好在哪里？</a>

### Clang

`Clang`是`LLVM`的子项目，**基于`LLVM`架构的`C/C++/Objective-C`编译器前端**。

![img](../images/C++/Clang)

- 相比于GCC，Clang具有如下优点：

- 编译效率高
- 占用内存小，`Clang`生成`AST`（语法分析树）所占用的内存是`GCC`的1/5
- 模块化设计
- 诊断信息可读性强
- 设计清晰简单，易于扩展增强

### GPL协议

个人理解为**免费代码协议**。

需要注意的是它与**开源代码协议**，比如`Apache Licene 2.0`的异同。

### MFC

`Microsoft Foundation Class`，微软基础类库。微软提供`C++`类库。

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

## IDE

目前支持`IDE`有很多。

- **[Microsoft]** `Visual Studio`、`Visual Code`
- **[JetBrains]**	`Clion`
- **[Ecplise]**	`CDT插件`

## 如何选择合适的IDE

结合网上的一些评论，大概是这个样子的。

- **大型项目**

  `visual studio`是不二选择，**稳定、速度快、bug少**。

- **懒人**

  **大型工程bug多，速度慢**。

  > 刚安装，速度确实慢。

- **vscode**

  最适合前端开发，轻量无敌。但对于`C++大型工程`，**折腾、bug多**。

  > 最初，研究vscode的时间比学`C/C++`的时间还多。

### Visual Code



<a href='https://www.cnblogs.com/esllovesn/p/10012653.html'>Visual Studio Code 配置C/C++环境</a>

- **[下载]**	`LLVM`、`MinGw-w64`、`CMake`

- **[合并]**	将`MinGw-w64`中的所有文件及文件夹放在`$LLVM\目录下

- **[配置]**	配置`Visual Studio Code`的`json`配置文件

  1. launch.json
  2. tasks.json
  3. settings.json
  4. c_cpp_properties.json

  > - 如果没有合并`Clang`与`MinGW-W64`，需要将`c_cpp_properties.json`中的`compilerPath`填写为`MinGW`的完整路径，精确到`gcc.exe`。
  >- 如果自己编写了头文件且不再`workspaceFolder`下，路径也要填写到`includePath`和`browse`中。
  
  > 即便按照官网的方式配置`vscode`的`c/c++`环境，构建体验还是很糟糕。

#### vscode include问题

首先需要将依赖项写在`cpp_propertiest.json`中，

其次如果依然有问题，禁用一些插件，具体是哪些需要一步步测试。

``` json
{
    "configurations": [
        {
            "name": "Win32",
            "includePath": [
                "D:/Program Files/LLVM/**",
                "${workspaceFolder}/**"
            ],
            "defines": [
                "_DEBUG",
                "UNICODE",
                "_UNICODE"
            ],
            "compilerPath": "D:/Program Files/LLVM/bin/clang++.exe",
            "cStandard": "gnu17",
            "cppStandard": "gnu++14",
            "intelliSenseMode": "clang-x64"
        }
    ],
    "version": 4
}
```

我引入了之后发现依然报错，最后发现是一款插件的问题——`Jupyter`。

如果是`c`的话还要禁用`python`插件。

- **参考**
- <a href='https://blog.csdn.net/weixin_40694527/article/details/84251461'>关于VScode在Windows环境下c_cpp_properties.json文件配置问题</a>
- <a href='https://blog.csdn.net/caoshiying/article/details/80870298'>Visual Studio Code报错：找不到iostream</a>

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