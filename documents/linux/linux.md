[TOC]

# 为什么中linux中各种服务都带一个d呢？

> d 代表了daemon (守护进程)，例如`vsFTPd`、`mysqld`。通常为某系统的服务端程序。

- [为什么中linux中各种服务都带一个d呢？](https://blog.csdn.net/comeoncomputer/article/details/78681193)

# 目录结构

## /etc

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

## /boot

存放启动Linux时使用的内核文件，包括连接文件以及镜像文件

## /lib

存放基本代码块，比如C++库

## /sys

linux2.6内核变化。该目录在2.6内核中新出现文件系统sysfs，该文件系统集成了3种系统信息：

- 针对进程信息的proc文件系统
- 针对设备的devfs文件系统
- 针对伪终端的devpts文件系统

## 指令集合

### /bin

存放常用的程序和指令

### /sbin

只有系统管理员能使用的程序和指令

## 外部文件管理

### /dev

存放Linux外部设备。**注意：在Linux中访问设备和访问文件的方式时相同的。**

### /media

类似windows的其他设备，例如u盘、光驱等等。识别后，linux会把设备放在这个目录下。

## /mnt

临时挂载别的文件系统后，我们可以将光驱挂载在/mnt/上，然后进入该目录查看光驱内容。

## 临时文件

### /run

临时文件系统，存放系统启动依赖的信息。

当系统重启后，这个目录下的文件应该被删掉或清除。

如果系统上由/var/run，应该让它志向run。

### /lost+found

一般情况下为空，系统非法关机后，这里存放一些文件。

### /tmp

保存临时文件。

## 账户

### /root

系统管理员的主目录。

### /home

用户的主目录，各个用户以账号命名。

### /usr

用户的很多应用程序和文件放在这个目录下，类似windows的program files目录。

### /usr/bin

系统用户使用的应用程序和指令。

### /usr/sbin

超级用户使用的比较高级的管理程序和系统守护程序。

### /usr/src

内核源代码放置目录。

## 运行过程中使用

### /var

经常修改的数据，例如程序运行的日志文件。

## /proc

管理内存空间的虚拟目录，是系统内存的映射。

我们可以直接访问该目录获取系统信息。

这个目录的内容不在硬盘上而是在内存李，我们可以直接修改这里的某些文件来修改。

## 扩展

### /opt

默认是空，安装额外软件。

### /srv

存放服务启动后需要提取的数据。

# 内核

负责控制硬件、管理文件系统、程序进程等。

``` shell
# 查看内核版本
cat /proc/version
# 查看发行版本
cat /etc/redhat-release
```

> 操作系统内核一定是免费的，然而操作系统发行版不一定是免费的。
>
> 发行版本除内核提供功能之外，还提供了C/C++编译器、C/C++库、系统管理工具、网络工具、办公软件、多媒体软件、绘图软件等。

# 启动过程



![linux-kernel-bootstarp](../images/linux-kernel-bootstarp.png)

> Docker镜像仅打包第四步及第五步涵盖的内容。

# 用户

## 用户类型

- 超级用户

  root，id=0

- 普通用户

- 伪用户

  满足文件或者程序运行的需要而创建，不能登录不能使用。

## shell

- **查看用户组**

``` shell
cat /etc/group
```

- **新增用户组**

``` shell
# 创建组group1
groupadd group1

# 创建group2,该组的id是1003
group -g 1003 group2
```

- **删除用户组**

``` shell
groupdel group1
```

- **修改用户组**

``` shell 
# 将group2的id修改为10000，组名修改为group3
groupmod -g 10000 -n group3 group2
```

- **查看用户**

``` shell
cat /etc/passwd
```

- **创建用户**

创建用户时，会在home文件夹下创建一个以用户名命名的目录及使用的shell脚本（/bin/bash）。

``` shell
useradd peter

# -d 指定用户目录
# -m 目录不存在时创建
useradd -d /usr/peter -m peter
```

- **删除**

``` shell
# -r同时删除home下的peter目录
userdel -r peter
```

- **修改密码**

``` shell
passwd peter

# 把用户peter密码置空
passwd -d peter
# 把用户peter密码锁定
passwd -l peter
```

# 文件

## 权限

``` shell
drwxrwx--x ...
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

### shell

`linux`引用环境变量中其他脚本或文件时，

``` shell
#!/usr/bin/ksh
```

该语句将引用根目录下路径的`ksh`。

- **修改权限**

``` shell
# 每一组添加可执行权限
chmod +x hello.sh
# 每一组移除可执行权限
chmod -x hello.sh

# 设置权限777
chmod 777 hello.sh
```

## 硬链接和软链接

### 硬链接

通过索引节点进行链接。在Linux文件系统中，操作系统会分配保存在磁盘分区的文件一个编号（node Index）。

硬链接的作用时允许一个文件拥有多个有效路径名。

只删除一个链接不影响索引节点本身和其他链接。即文件的真正删除时与之相关所有硬链接均被删除。

``` shell
ln hello.sh hello

# -i参数显示文件的inode节点信息
ln -li
```

### 软链接

软链接是符号链接（Symbolic Link），类似快捷方式。

``` shell
ln -s hello.sh hello
```

# 环境变量

在/etc/profile文件下，可以通过vi profile编辑该文件。

- **添加环境变量**

  1. vim /etc/profile

  2. 在该文件追加

     `export HELLO=.$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar`

  3. 环境变量生效

     ``` shell
     source /etc/profile
     ```

# Shell脚本

- 常见
- Bourne Shell(/usr/bin/sh或/bin/sh)
- Bourne Again Shell(/bin/bash)

格式：

``` shell
#!/bin/bash

name="Peter"
echo $name

# 字符串替换
echo "I am ${name}'s friend."

# 声明数组
names={"Peter" "James" "Deer"}
# 打印所有
echo ${name[@]}
echo ${name[*]}

# 循环
for var in "Peter" "James"
do
	echo $var
done

for var in ${name[*]}
do
	if test $var = 'Peter'
	then
		echo $var
	else
		echo "not"
    fi
done

if [ $(ps -ef|grep -c ssh) -gt 1 ];
	then echo "ssh is start"
fi 



```

> 首行（`#!/bin/bash`）均指定解释器版本。

## 输入输出重定向

一个命令通常从也给标准输入读取输入，默认情况下是终端。同样的，从终端读取标准输出。

``` shell
# 输入输出重定向
# 命令输出到out文件，报错信息并不会进入out
# 1. test 'aa' -eq "bb" > out 
# 将stderr合并到stdout
# 2. test 'aa' -eq "bb" > out 2>&1
```

| 命令            | 说明                                         |
| --------------- | -------------------------------------------- |
| command > file  | 将输出重定向到file                           |
| command < file  | 将输入重定向到file                           |
| command >> file | 将输入以追加方式重定向到file                 |
| n > file        | 将文件描述符为n的文件重定向到file            |
| n >> file       | 将文件描述符为n的文件以追加方式重定向到file  |
| n >& m          | 将输出文件m和n合并                           |
| n <& m          | 将输入文件m和n合并                           |
| << tag          | 将开始标记tag和结束标记tag之间的内容作为输入 |

一般情况下，每个Unix/Linux命令允许会打开三个文件：

- 标准输入文件

  stdin，文件描述符为0，Unix程序默认从stdin读取数据

- 标准输出文件

  stdout，文件描述符为1，Unix程序默认向stdout输出数据

- 标准错误文件

  stderr，文件描述符为2，Unix程序向stderr流中写入错误信息

## 传递参数

``` shell
# 传递参数
echo "sh arg: $0 $1"
```

# 常用命令

## 查看文件

- cat
- less
- more
- tail

less和more的区别是：less可以前后翻页，more只能向前翻页。

## 查看文件大小

``` shell
# 显示当前目录下所有文件的大小
du -sh * 

# 显示该文件大小
du -sh <filename>

# 显示当前目录所占空间大小
du -sh

# 显示磁盘占用信息
df

# 显示本地系统的占用信息
df -lh
```



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

![img](..\images\linux\version.jpeg)

## Ubuntu

- [官网](http://old-releases.ubuntu.com/)

有操作界面的`Linux`系统。

### 修改密码

- [ubuntu 设置root用户密码并实现root用户登录](https://segmentfault.com/a/1190000018164314)

``` shell
# 修改root用户密码
sudo passwd
```

