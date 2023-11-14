[toc]

# 跨域问题

当前域下的ajax请求，仅可请求当前域下的站点。但是实际情况下，当前分布式域集群纵横。为了解决这个问题，我们有以下解决之道：

## JSONP

浏览器不允许ajax跨域请求，却允许script标签发起跨域请求。因此，有人扩展script标签，借助它来发送ajax请求。

但这种方式存在以下问题：

- 仅支持GET请求
- 使用jsonp方式，对应服务代码必须进行改造，将返回值做一个函数式包装，对业务开发侵入性大。

## document.domain + iframe

这种方式同样侵入性较大。

## CORS

Cross-Origin resource sharing, 跨域资源共享。它允许浏览器向跨源服务器，发出ajax请求，从而克服同源使用限制。

- **简单请求**

  浏览器在跨源ajax请求的头信息之中，自动添加一个Origin字段。

  服务器根据这个值，在许可范围内，则在响应头信息中包含Access-Control-Allow-Origin。

- **复杂请求**

  在正式通信之前，增加一次Http查询请求，称为预检请求（OPTIONS）询问。那么我们需要在nginx上进行如下配置：

  ``` nginx
  if ($request_method = 'OPTIONS') {##OPTIONS类的请求，是跨域先验请求
  	return 204;##204代表ok
  }
  ```

### nginx配置

``` shell
#是否允许请求带有验证信息
add_header Access-Control-Allow-Credentials true;
#允许跨域访问的域名,可以是一个域的列表，也可以是通配符*
add_header Access-Control-Allow-Origin  http://static.enjoy.com;
#允许脚本访问的返回头
add_header Access-Control-Allow-Headers 'x-requested-with,content-type,Cache-Control,Pragma,Date,x-timestamp';
#允许使用的请求方法，以逗号隔开
add_header Access-Control-Allow-Methods 'POST,GET,OPTIONS,PUT,DELETE';
#允许自定义的头部，以逗号隔开,大小写不敏感
add_header Access-Control-Expose-Headers 'WWW-Authenticate,Server-Authorization';
#P3P支持跨域cookie操作
add_header P3P 'policyref="/w3c/p3p.xml", CP="NOI DSP PSAa OUR BUS IND ONL UNI COM NAV INT LOC"';
if ($request_method = 'OPTIONS') {##OPTIONS类的请求，是跨域先验请求
	return 204;##204代表ok
}
```

# 防盗链

有时候我们的网站上有一些静态资源（好看的图片），聪明的网友们会顺着src来下载。这就不可避免的为我们的服务器带来负担。

为了不允许未知的客户端随意访问我们的静态资源，我们通过防盗链来规避这个问题。

- **nginx配置**

  ``` nginx
  location ^~ /mall {
      valid_referers *.enjoy.com;##对referer进行校验
      if ($invalid_referer) {##校验不过，拒绝访问 
          return 404;
      }
      root /etc/nginx/html/gzip;
  }
  ```

# 压缩

顾名思义，节省宽带资源。

- **nginx配置**

  ``` nginx 
  location ~ /(.*)\.(html|js|css|png)$ {
      gzip on; # 启用gzip压缩，默认是off，不启用
  
      # 对js、css、jpg、png、gif格式的文件启用gzip压缩功能
      gzip_types application/javascript text/css image/jpeg image/png image/gif;
      gzip_min_length 1024; # 所压缩文件的最小值，小于这个的不会压缩
      gzip_buffers 4 1k; # 设置压缩响应的缓冲块的大小和个数，默认是内存一个页的大小
      gzip_comp_level 1; # 压缩水平，默认1。取值范围1-9，取值越大压缩比率越大，但越耗cpu时间
  
      root /etc/nginx/html/gzip;
  }
  ```

  

# Https

步骤：

1. 确认安装https模块

   通过`nginx -V`检查

2. 准备浏览器证书与私钥

3. Nginx配置

   ``` nginx
   server {
       listen 443 ssl;
       server_name enjoy.com;
       
       ssl_certificate /etc/nginx/server.crt;
       ssl_certificate_key /etc/nginx/server.key;
       
       location / {
           root /etc/nginx.html;
           index index.html index.htm;
       }
   }
   ```

# 高可用

## 传统思路

在tomcat集群前面加一层负载服务nginx。

## lvs思想解决高可用问题

翻译一下——nginx集群。

### 什么是LVS

[lvs详解 - 胡杨的文章 - 知乎](https://zhuanlan.zhihu.com/p/445202915)

### keepalived配置LVS

Keepalived是管理LVS的一款软件，看名字用于集群节点之间的心跳检测。

- 关闭selinux

  ``` shell
  vi /etc/sysconfig/selinux
  
  SELINUX=disabled
  :wq
  ```

- 安装依赖

  ``` shell
  yum -y install libnl libnl-devel libnfnetlink-devel
  ```

- 安装keepalived

  ``` shell
  # 禁止用yum安装
  wget https://www.keepalived.org/software/keepalived-1.3.4.tar.gz
  ./configure --prefix=/usr/local/keepalived --sysconf=/etc
  make && make install
  ```

- keepalived主机配置

  ``` shell
  vi /etc/keepalived/keepalived.conf
  
  ! Configuration File for keepalived
  
  global_defs {
     router_id LVS_DEVEL		####keepalived的唯一标识
  }
  
  vrrp_instance VI_1 {
      state MASTER
      interface ens33 ##系统网上名,可以使用ip addr命令查看
      virtual_router_id 51	##组名，参与此虚拟ip的机器配置一样的值
      priority 200	##优先级，数值大的优先级高，组内最高的胜出
      advert_int 1	##心跳检测1s一次
      authentication {	##心跳检测1s一次
          auth_type PASS
          auth_pass 1111
      }
  
      virtual_ipaddress {
          192.168.244.200	##虚拟的ip
      }
  }
  ```

- 启动 keepalived

  通过`ip addr`命令发现网卡增加了**192.168.244.200**虚拟网卡。

- keepalived从机配置

  需要注意的是：从机的配置router_id与priority必须不太，priority的优先级必须低于主机。

  ``` shell
  vi /etc/keepalived/keepalived.conf
  
  ! Configuration File for keepalived
  
  global_defs {
     router_id LVS_DEVEL		####keepalived的唯一标识
  }
  
  vrrp_instance VI_1 {
      state MASTER
      interface ens33 ##系统网上名,可以使用ip addr命令查看
      virtual_router_id 51	##组名，参与此虚拟ip的机器配置一样的值
      priority 200	##优先级，数值大的优先级高，组内最高的胜出
      advert_int 1	##心跳检测1s一次
      authentication {	##心跳检测1s一次
          auth_type PASS
          auth_pass 1111
      }
      
      virtual_ipaddress {
          192.168.244.200	##虚拟的ip
      }
  }
  ```

- 主从效果验证

  1、此时，杀掉主机上的 keepalived，244.200 的 ip 将从主机上消失。而出现的从机的 ip 中；

  2、再次启动主机的 keepalived，244.200 的 ip 将被主机重新夺回；

  3、此效果是单主单备方式。备机资源有一定的浪费。可以重复前面的动作，虚拟出第二个ip，将主从机优先级颠倒，从而利用起备机服务；

## keepalived监控服务软件

以上操作中，keepalived 很好的实现了 LVS 功能，即集群机器共同虚拟一个 vip，并实现在集群中自动漂移。但假如物理机状况良好，并不能保障其上运行的服务软件 ok，因此，需要借助 keepalived 来监控服务软件。

``` shell
#!/bin/bash

A=`ps -C nginx --no-header |wc -l`	##统计nginx进程数，若为0，表明nginx被杀
if [ $A -eq 0 ];then
        /usr/local/openresty/nginx/sbin/nginx                #重启nginx
        #nginx重启失败，则停掉keepalived服务，进行VIP转移
        if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then
              killall keepalived    #杀掉，vip就漫游到另一台机器
        fi
fi

```

下一步需要修改keepalived配置:

1. 增加vrrp_script部分；
2. 增加track_script部分；

``` shell
! Configuration File for keepalived

global_defs {
   router_id LVS_DEVEL		####keepalived的唯一标识
}
vrrp_script chk_http_port {
    script "/etc/nginx/chk_nginx.sh" #心跳执行的脚本
    interval 2                          #（检测脚本执行的间隔，单位是秒）
    weight 2
}

vrrp_instance VI_1 {
    state MASTER
    interface ens33 ##系统网上名,可以使用ip addr命令查看
    virtual_router_id 51	##组名，参与此虚拟ip的机器配置一样的值
    priority 200	##优先级，数值大的优先级高，组内最高的胜出
    advert_int 1	##心跳检测1s一次
    authentication {	##心跳检测1s一次
        auth_type PASS
        auth_pass 1111
    }
    track_script {
	chk_http_port	#（调用检测脚本）
    }
    virtual_ipaddress {
        192.168.244.200	##虚拟的ip
    }
}
```

最后，重启keepalived，并关闭nginx测试效果。