[TOC]

# 为什么中linux中各种服务都带一个d呢？

> d 代表了daemon (守护进程)，例如`vsFTPd`、`mysqld`。通常为某系统的服务端程序。

https://blog.csdn.net/comeoncomputer/article/details/78681193

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

# 文件权限

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

# 参考

https://www.cnblogs.com/pengyunjing/p/8543026.html