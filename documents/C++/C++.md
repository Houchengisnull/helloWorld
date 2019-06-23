[TOC]

# 安装编译器

在安装python类库`wordcloud`时出现以下错误：

``` shell
  error: Microsoft Visual C++ 14.0 is required. Get it with "Microsoft Visual C++ Build Tools": https://visualstudio.microsoft.com/downloads/
```

本想通过使用轻量的Visual Code绕过这个问题，但是Visual Code仅提供插件而不安装编译器，故我们安装自己的编译器`MinGW`。

## MinGW

`MinGW`( Minimalist GNU for Windows ），是将`GCC编译器`和`GNU Binutils`移植Windows 32下的产物，包括一系列头文件（Win32API）、库和可执行文件。

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

## Visual Cpp Build Tools

百度云地址

>  链接：https://pan.baidu.com/s/1uRAPZXu9uyzezCc-r2ORUA 
> 提取码：qu7o 

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