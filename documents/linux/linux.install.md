[toc]

# Overview

# apt-get

Advanced Package Tool，用于自动从互联网搜索、安装、升级或卸载软件。

另外，apt-get通常需要root权限，所以一般在sudo命令后。

## 设置源

源文件目录在：`/etc/apt/source.list`。

在对源文件修改后，执行sudo apt-get update进行更新。

- 网易

  ``` shel
  deb http://mirrors.163.com/ubuntu/ trusty main restricted universe multiverse
  deb http://mirrors.163.com/ubuntu/ trusty-security main restricted universe multiverse
  deb http://mirrors.163.com/ubuntu/ trusty-updates main restricted universe multiverse
  deb http://mirrors.163.com/ubuntu/ trusty-proposed main restricted universe multiverse
  deb http://mirrors.163.com/ubuntu/ trusty-backports main restricted universe multiverse
  deb-src http://mirrors.163.com/ubuntu/ trusty main restricted universe multiverse
  deb-src http://mirrors.163.com/ubuntu/ trusty-security main restricted universe multiverse
  deb-src http://mirrors.163.com/ubuntu/ trusty-updates main restricted universe multiverse
  deb-src http://mirrors.163.com/ubuntu/ trusty-proposed main restricted universe multiverse
  deb-src http://mirrors.163.com/ubuntu/ trusty-backports main restricted universe multiverse	
  ```

# yum

yum(Yellow dog Updater,Modified)是Fedora和RedHat以及SUSE中的Shell前端软件包管理，基于RPM包管理，能够从指定服务器自动下载RPM包并安装，可以自动处理依赖关系，无须一次次下载、安装。

## Usage

### 使用说明

``` shell
# 查询rz命令安装地址
yum search rz

# 下载及安装rz
yum install -y rz*

# 列出已安装程序
yun list installed

# 移除
yum remove <package_name>

# 可更新程序
yum check-update
# 更新所有软件
yum update

# 仅安装指定包
yum install <package_name>

# 仅更新指定包
yum update <package_name>

# 清除缓存
## 清除缓存目录下的包
yum clean pacakges
## 清除缓存目录下的headers
yum clean headers
## 清除缓存目录下的旧headers
yum clean oldheaders
## 清除所有
yum clean | yum clean all
```

### 设置源

源文件目录在：/etc/yum/repos.d。

### 安装常用软件

``` shell
# 常用软件
yum install wget
yum install lrzsz
yum install net-tools
```

# rpm

## Usage

``` shell
# 查询jdk
rpm -qa|grep jdk

# 卸载jdk
rpm -e jdk*
```

