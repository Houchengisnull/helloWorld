[TOC]

# 系统

## 系统版本

- 查看Linux系统版本

``` shell
cat /etc/issue
```

- 查看Linux内核版本

``` shell
cat /proc/version
```

## 系统位数

``` shell
$ getconf LONG_BIT
```

## 查看磁盘IO信息

- iotop
- iostat

# 文件

## 创建文件夹 mkdir

``` shell
$ mkdir #{file}

// 需要时创建上级目录
$ mkdir -P #{file} 
```

## 设置用户或用户组 chown

将指定文件设置用户或用户组

``` shell
$ chown -R #{user}:#{usergroup} #{file}
```

| 参数 | 描述                           |
| ---- | ------------------------------ |
| -R   | 处理指定目录以及子目录下的文件 |
| -c | 显示更改的部份信息 |

## 设置文件权限 chowd

``` shell
chowd -R 777 #{directory}
```

| 权限代号 | 权限描述       |
| -------- | -------------- |
| 777      | 可读可写可执行 |

http://blog.sina.com.cn/s/blog_8610084f0102xx2k.html

# 网络

## host

### 域名解析

`# cat /etc/hosts` 记录hostname对应的ip地址 

`# cat /etc/resolv.conf` 设置DNS服务器的ip地址 

`# cat /etc/host.conf` 指定域名解析顺序

/etc/host.conf ：指定域名解析的顺序（是从本地的hosts文件解析还是从DNS解析） <https://www.cnblogs.com/sunfie/p/5138065.html>

## 查看ip地址

``` shell
# ip addr
```

## 网卡流量

`iptraf-ng`或`iptraf`一般系统默认不安装，需要通过`yum -y install iptraf`安装

``` shell
$ iptraf-ng eth0
```

# 内存

## 查看 free

``` shell
# free -h
```

## 查看 top

``` shell
# top
```

​	top命令是Linux下常用的性能分析工具，能够实时显示系统中各个进程的资源占用状况，类似于Windows的任务管理器

- 查看用户进程内存使用情况

``` shell
$ top -u root
```

- 查看端口内存占比

``` shell
$ top -p #{pid}
```

- 字段意义

| name | description |
| ---- | ----------- |
| PID | 进程的ID |
| USER | 进程所有者 |
| PR | 进程的优先级别，越小越优先被执行 |
| NInice | 值 |
| VIRT | 进程占用的虚拟内存 |
| RES | 进程占用的物理内存 |
| SHR | 进程使用的共享内存 |
| S | 进程的状态。S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值为负数 |
| %CPU | 进程占用CPU的使用率 |
| %MEM | 进程使用的物理内存和总内存的百分比 |
| TIME+ | 该进程启动后占用的总的CPU时间，即占用CPU使用时间的累加值。|
| COMMAND | 进程启动命令名称 |

- 常用命令
  - P 按照CPU占用百分百排序
  - M 按照内存占用百分比排序
  - T 按照MITE+排序

## 查看内存详细内容

``` shell 
$ cat /proc/#{pid}/status
```

## 释放内存

``` shell
# sync
# echo 1 > /proc/sys/vm/drop_caches
```

- sync

将buffer区数据写入磁盘，确保文件系统完整性

- echo #{num} > /proc/sys/vm/drop_caches

| num | 含义 |
| --- | --- |
| 0  | 不释放 |
| 1 |  释放页内存 |
| 2 | 释放dentries 和inodes |
| 3 |  释放所有缓存 |

# 进程

## 查看

```
# ps -ef | grep #{process_name}
```



## kill

`# kill #{port}`
`# kill -s 9 #{port}`
-s 9 即传递给进程信号为9, 9即强制,尽快结束进程

# 查看端口 lsof

查看FTP服务端口21

``` shell
# lsof -i:21 
```

# netstat

## 查看tcp端口

```
# netstat -tnlp
```

## 查看主机路由

``` shell
$ netstat -rn
```

# ssh

## 连接命令

- `# ssh #{address} -l root`
- `# ssh #{username}@#{address}`

# vi/vim

## 模式

### 命令模式

用户刚刚启动 vi/vim，便进入了命令模式。
此状态下敲击键盘动作会被Vim识别为命令，而非输入字符。**比如我们此时按下i，并不会输入一个字符，i被当作了一个命令。**

> 以下是常用的几个命令:

- i 切换到输入模式，以输入字符。
- x 删除当前光标所在处的字符。
- : 切换到底线命令模式，以在最底一行输入命令。

若想要编辑文本：启动Vim，进入了命令模式，按下i，切换到输入模式。

命令模式只有一些最基本的命令，因此仍要依靠底线命令模式输入更多命令。

### 输入模式

- ESC，退出输入模式，切换到命令模式

## 参考

<http://www.runoob.com/linux/linux-vim.html>

# Yum

`Yellow dog Updater , Modified`，为一款Shell前端软件包管理器。

# head 和 tail

分别查看文件头或文件尾

``` shell
# head #{filepath}
# tail #{filepath}
```



# 环境变量

## 变量种类

### 永久

需要修改配置文件

### 临时

使用export命令声明, 变量在关闭shell时失效

## 设置方法

### 对所有用户生效

修改 etc/profile 文件

```
# vi etc/profile
export CLASSPATH=./JAVA_HOME/lib;./JAVA_HOME/bin
```

> 修改后若要立即生效需要运行

```
# source /etc/profile
```

### 对单一用户生效

修改 用户目录下 .bash_profile (具体文件看linux版本,比如 ./bashrc)

```
# vi /etc/profile
export CLASSPATH=./JAVA_HOME/lib;$JAVA_HOME/jre/lib
```

### 临时变量

```
export hello=world
```

## 查看环境变量

- echo命令查看单个环境变量。例如:echo $PATH
- env查看所有环境变量。例如:env
- set查看所有本地定义环境变量