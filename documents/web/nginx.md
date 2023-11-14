[toc]

# 简介

Nginx是俄罗斯工程师实现的轻量级HTTP服务器，它的发音是"engine X"。

- 支持五万并发连接
- 内存消耗少
- 成本低

# 作用

Nginx在架构体系中的位置与作用

- 网关

  面向客户的总入口

- 虚拟主机

  为不同域名/ip/端口提供服务

- 路由

  使用反向代理

- 静态服务器

  在`MVVM`模式中，用来发布html/css/js/img

- 负载集群

  使用upstream，负载多个web服务器(tomcat)

# Nginx架构设计

## 模块化设计

高度模块化的设计是Nginx的架构基础，Nginx服务器被分为多个模块，每个模块是一个功能模块，只负责自身的功能，严格遵循高内聚，低耦合的原则。

- core

- standard http

- optional http

- email

- third

  第三方模块是为了扩展Nginx服务器应用的。

## 多进程模型

Nginx采用多进程模型

- master进程

  接收来自外界的信号，向各个worker进程发生信号，监控各个worker进程的运行状态，当worker进程异常退出将重新拉起。
  
- work进程

  子进程，负责与客户端建立连接进行交互。

## Epoll模式

采用epoll[^epoll]模型。

- **select与epoll的区别**

  select采用忙轮询，收集所有的TCP连接，把套接字交给操作系统梳理数据；epoll则是内核将时间写入Map，发生IO事件时，内核到Map中查找**my_event**；

[^epoll]:event + polls

# 安装

- **windows**	[nginx:download](http://nginx.org/en/download.html)
- **linux**	[nginx:Linux packages](http://nginx.org/en/linux_packages.html#instructions)
- **仓库**	https://nginx.org/download/

>  除从`nginx`的官网获取程序外，猜想还能从华为、阿里云等仓库获取。

- **参考**
- [linux安装nginx 步骤](https://blog.csdn.net/qq_14926283/article/details/109838952)

## 源码安装

- C/C++编译环境

  ``` shell
  # 安装make
  yum -y install autoconf automake make
  # 安装g++
  yum -y install gcc gcc-c++
  yum -y install pcre pcre-devel
  yum -y install zlib zlib-devel
  yum -y install openssl openssl-devel
  ```

- 源码安装

  ``` shell
  # 源码安装
  wget http://nginx.org/download/nginx-1.15.8.tar.gz
  tar -zxcvf
  cd nginx-1.9.0
  # 启用命令 --with: 针对Nignx内部的模块化设计，部分模块默认不启动
  ./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module 
  make && make install
  export PATH=$PATH:/usr/local/nginx/sbin
  
  # yum安装
  yum install yum-utils
  yum-config-manager --add-repo https://openresty.org/package/centos/openresty.repo
  yum install openresty
  ```

# 目录结构

- conf	配置文件
- html	静态文件
- logs	日志
- sbin	二进制程序

# 配置

nginx.conf是nginx的配置文件，其结构如下：

- main	全局配置
- events	设置nginx的工作模式及连接数上限
- http		服务器相关属性
- server	虚拟主机设置
- upstream	上游服务器设置，主要为反向代理、负载均衡相关配置
- location	URL匹配特定位置后的设置

## 常用语法

### Location

路由。

匹配优先级：

- 精准匹配	location= /
- 普通匹配	location ^~ /static/
- 正则匹配	location~*.(gif|png|css|js)$

`~`符号代表了正则表达式。

- Location与Path

  当Path匹配了Location后，Path会丢弃Location的命中的部分，将未命中的部分重新嫁接到proxy_pass部分。

### Rewrite

- 语法

  rewrite regex replacement [flag]

当正则表达式regex匹配path成功后，把路径替换成replacement字符串。

flag指重定向的方式：

- 内部重定向

  - break	path值被更新，rewrite命令中断，原控制流程逻辑不变往下走
  - last	path值被跟新，rewrite命令中断，控制流程刷新整个location层的逻辑流程

- 外部重定向

  发生页面重定向，nginx流程结束，返回http响应，页面url刷新

  - permanent 返回301永久重定向
  - redirect	返回302临时重定向

- 空

  发生内部重定向，path值更新，rewrite层面的命令继续，最后一个rewrite刷新控制流程，重新进行location匹配。

> CDN:
>
> Content Delivery Network, 内容分发网络。

# Nginx处理请求的过程

1. **post-read**

   接收到完整的 http 头部后处理的阶段，在 uri 重写之前。

2. **server-rewrite**

   location 匹配前，修改 uri 的阶段，用于重定向，location 块外的重写指令（**多次执行**）

3. **find-config**

   uri 寻找匹配的 location 块配置项（**多次执行**）

4. **rewrite** 

   找到 location 块后再修改 uri，location 级别的 uri 重写阶段（**多次执行**）

5. **post-rewrite**

   防死循环，跳转到对应阶段

6. **preaccess** 

   权限预处理

7. **access**

   判断是否允许这个请求进入

8. **post-access**

   向用户发送拒绝服务的错误码，用来响应上一阶段的拒绝

9. **try-files**

   访问静态文件资源

10. **content**

    内容生成阶段，该阶段产生响应，并发送到客户端

11. **log** 

    记录访问日志

# 启动与停止

执行`nginx.exe`即可。启动成功如下图：

![image-20211103105728525](../images/web/nginx.start.png)

> `nginx`启动成功后没有任何提示，可能这就是俄罗斯工程师的浪漫。

``` shell
# 安全退出
kill -quit $pid
# 立即退出
kill -term $pid 
```

# 日志分割脚本

- 分割脚本

  ``` shell
  #!/bin/bash
  #设置日志文件存放目录
  LOG_HOME="/usr/local/nginx/logs/"
  #备分文件名称
  LOG_PATH_BAK="$(date -d yesterday +%Y%m%d%H%M)"
  #重命名日志文件
  mv ${LOG_HOME}/access.log ${LOG_HOME}/access.${LOG_PATH_BAK}.log
  mv ${LOG_HOME}/error.log ${LOG_HOME}/error.${LOG_PATH_BAK}.log
  #向 nginx 主进程发信号重新打开日志
  kill -USR1 `cat ${LOG_HOME}/nginx.pid
  ```

- 定时脚本

  ``` shell
  */1 * * * * /usr/local/nginx/sbin/logcut.sh
  ```

# 负载均衡

常规使用：

- 轮询
- 权重
- ip_hash

# Plugins

## echo-nginx-module

# FAQ

## Nginx 错误10013: An attempt was made to access a socket in a way forbidden

- [Nginx 错误10013: An attempt was made to access a socket in a way forbidden](https://blog.csdn.net/qq_40646143/article/details/79593958)

  莫怀疑，就是端口被占用。

