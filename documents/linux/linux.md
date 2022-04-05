[TOC]

# 为什么中linux中各种服务都带一个d呢？

> d 代表了daemon (守护进程)，例如`vsFTPd`、`mysqld`。通常为某系统的服务端程序。

- [为什么中linux中各种服务都带一个d呢？](https://blog.csdn.net/comeoncomputer/article/details/78681193)

# 目录结构

## etc

`Editable Text Configuration`或`Extended Tool Chest`。

用于存放系统配置文件，例如系统启动脚本、用户登录配置文件、网络配置文件、httpd配置文件等等。

### group 用户组配置文件

> 用户组的配置文件，内容包括用户和用户组，并且能显示出用户是归属哪个用户组或哪几个用户组，因为一个用户可以归属一个或多个不同的用户组；同一用 户组的用户之间具有相似的特征。

### gshadow

用户组影子口令文件

### passwd

用户配置文件

### shadow

用户影子口令文件

# 文件

## 权限

``` shell
drwx-rwx-x ...
```

其中以`-`分隔。

- d 文件夹
- 所有者权限
- 用户组权限
- 其他用户权限
- r 读权限
- w 写权限
- x 执行权限

> 可通过`ls -l`显示当前目录详细信息

### 参考

[linux下查看所有用户及所有用户组](https://www.cnblogs.com/pengyunjing/p/8543026.html)

## shell

`linux`引用环境变量中其他脚本或文件时，

``` shell
#!/usr/bin/ksh
```

该语句将引用根目录下路径的`ksh`

# 进程通信

Inter-Process Communication，IPC

## 管道 Pipe

即两个进程间的通信桥梁，在进程之间传递少量的字符流或者字节流。管道符`|`就是利用管道通信的典型应用。

## 信号 Signal

通知进程某种事件发生。例如`kill`就是由`Shell`进程发送信号`SIGKILL`给指定`PID`的进程。

## 信号量 Semaphore

两个进程间同步协作手段，相当于操作系统提供的特殊变量，进程可以在上面进行`wait()`和`notify()`操作。

## 消息队列

适用于数据量较多的通信。

## 共享内存

## 套接字接口

> - 《凤凰架构》
>
>   消息队列和共享内存只适合单机多进程间的通信，套接字接口是更为普适的进程间通信机制，可用于不同机器之间的进程通信。套接字（Socket）起初是由 UNIX 系统的 BSD 分支开发出来的，现在已经移植到所有主流的操作系统上。出于效率考虑，当仅限于本机进程间通信时，套接字接口是被优化过的，不会经过网络协议栈，不需要打包拆包、计算校验和、维护序号和应答等操作，只是简单地将应用层数据从一个进程拷贝到另一个进程，这种进程间通信方式有个专名的名称：UNIX Domain Socket，又叫做 IPC Socket。

# 发行版

![img](.\images\linux\version.jpeg)

## Ubuntu

- [官网](http://old-releases.ubuntu.com/)

有操作界面的`Linux`系统。

### 修改密码

- [ubuntu 设置root用户密码并实现root用户登录](https://segmentfault.com/a/1190000018164314)

``` shell
# 修改root用户密码
sudo passwd
```

