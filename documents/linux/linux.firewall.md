[toc]

# Overview

在Linux系统中，最常用的防火墙工具是iptables和firewalld。

## Firewalld

### Concept

#### Rank

- public

  public通常用于连接互联网。

- internal

  internal[^1]适用于连接局域网。

- external

  external[^2]适用于连接受信任的网络。例如：合作公司。

[^1]: 内部
[^2]: 外部

### Usag

``` shell
systemctl status firewalld.service
systemctl start firewalld.service
systemctl stop firewalld.service

# 查看开放端口
firewall-cmd --list-ports
# 开放指定端口
# --zone=public 	指定防火墙级别为public
# --permanent 		永久性操作
firewall-cmd --zone=public --add-port=<port>/tcp --permanent
# 重载防火墙
firewall-cmd --reload

# 设置开机不启动
systemctl disable firewalld
```

## iptables

### Usag

``` shell
service iptables start
service iptables stop
```