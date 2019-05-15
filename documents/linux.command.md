[TOC]



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

# 进程

## 查看

```
# ps -ef | grep #{process_name}
```

## kill

`# kill #{port}`
`# kill -s 9 #{port}`
-s 9 即传递给进程信号为9, 9即强制,尽快结束进程

# netstat

## 查看tcp端口

```
# netstat -tnlp
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