[toc]

- 官方文档
- https://docs.vmware.com/cn/VMware-Workstation-Pro/index.html

# 安装VMWare

- **密钥**	`YF390-0HF8P-M81RQ-2DXQE-M2UT6`

# 安装虚拟机

## 操作步骤

1. 新建虚拟机
2. 典型安装

# 网络连接配置

- 参考
- [VMWare虚拟机网络配置](https://www.cnblogs.com/aeolian/p/8882790.html)

## 常见网络配置

| 模式     | 网络适配器 |
| -------- | ---------- |
| 桥接模式 | VMnet0     |
| NAT      | VMnet8     |

- 桥接模式

  虚拟机和主机再同一个真实网段，VMWare相当于[^集线器]功能。

- NAT

  网络地址转换模式。

  虚拟机在外部网络中不必具有自己的IP地址。主机系统上会建立专用的网络。相当于虚拟机虚拟出一个内网，主机和虚拟机都在这个虚拟的局域网中。

  NAT中VMWare相当于[^交换机]

- Host-Only

  主机模式和NAT模式相似，但是不能上网，相当于VMWare虚拟了一个局域网，但是这个局域网没有连接互联网。	

[^集线器]: 一根网线连接到主机相连得路由器上。
[^交换机]: 产生一个局域网，在这个局域网中分别给主机和虚拟机分配网络地址。

## NAT模式配置网络

- **环境**

  - **主机**	win10

  - **虚拟机**	CentOS7

<hr>

- **操作布置**

  1. 设置VMWare默认网关(相当于设置路由器):

     `编辑 > 虚拟网络编辑器 > 更改设置 > 选择VMnet8 > NAT设置`

     ![image-20220405163843820](..\images\VMware\网络配置-NAT-step1.png)

     将默认网关设置为`192.168.192.2`

     ![image-20220405164028022](..\images\VMware\网络配置-NAT-step2.png)

  2. 设置主机IP:

     设置`VMnet8`的IP地址为`192.168.192.1`

     ![image-20220405164119537](..\images\VMware\网络配置-NAT-step3.png)![image-20220405164335357](..\images\VMware\网络配置-NAT-step4.png)

  3. 修改虚拟机静态IP:

     ``` shell
     # 进入到网络适配器文件夹中
     cd  /etc/sysconfig/network-scripts/
     # 名字改为ifcfg-eth0
     mv ifcfg-ens33 ifcfg-eth0
     # 编辑文件
     vi  ifcfg-eth0
     
     TYPE=Ethernet 
     DEFROUTE=yes 
     PEERDNS=yes 
     PEERROUTES=yes 
     IPV4_FAILURE_FATAL=no 
     IPV6INIT=yes 
     IPV6_AUTOCONF=yes
     IPV6_DEFROUTE=yes 
     IPV6_PEERDNS=yes 
     IPV6_PEERROUTES=yes 
     IPV6_FAILURE_FATAL=no 
     # 将名称etn33修改为eth0
     NAME=eth0
     #UUID（Universally Unique Identifier）是系统层面的全局唯一标识符号，Mac地址以及IP地址是网络层面的标识号；
     #两台不同的Linux系统拥有相同的UUID并不影响系统的使用以及系统之间的通信；
     #可以通过命令uuidgen ens33生成新的uuid
     #和DEVICE一样也可以不写,DEVICE="ens33"可以不写，但一定不要写DEVICE="eth0"
     UUID=ae0965e7-22b9-45aa-8ec9-3f0a20a85d11 
     
     ONBOOT=yes  #开启自动启用网络连接,这个一定要改
     IPADDR=192.168.192.10  #设置IP地址 
     NETMASK=255.255.255.0  #设置子网掩码 
     GATEWAY=192.168.192.2  #设置网关 
     #设置主DNS 
     DNS1=61.147.37.1
     #设置备DNS 
     DNS2=8.8.8.8
     #启用静态IP地址 ,默认为dhcp
     BOOTPROTO=static  
     :wq!  #保存退出 
     
     service network restart  #重启网络，本文环境为centos7
     
     ping www.baidu.com  #测试网络是否正常
     ```

     