[toc]

- [官网](https://kubernetes.io/zh-cn/docs)

# Overview

Kubernetes（简称k8s）是一款容器编排[^容器编排]系统。

可以帮助我们：

- 自动化部署
- 自动化扩展
- 服务发现和负载均衡
- 存储管理
- 自动故障恢复
- 自动化配置管理

[^容器编排]: 容器编排是指对容器化应用程序的部署、管理和调度。

# Concept

- **心得**

  我在学习kubernetes的过程中，最痛苦的莫过于遇到问题无从下手。特别是看网上各种教程的时候：同样的步骤带来不一样的结果，作为初学者是非常懵逼，非常不友好的。

  复盘一下，归根到底是：

  - 对k8s的组件、基本概念和原理不熟悉；

  - k8s是一个繁杂的工具，体现在：安装与使用过程中会遇到很多**网络通信**的问题，而不是k8s本身需要怎么运用；

  - 官网非常全面，但是我并不想成为kubernetes专家；
  - 博客通常是一片文字+图片，至少对我来说很难建立概念之间的联系；


## Object

在使用kubernetes时，我们就是在与其中各个Object打交道。

- **什么是Object**

  如果类比Rabbit MQ，那对象是用户、交换机、队列、路由；

  如果类比MySQL，那对象就是表空间、表、行、值、索引；

  对象也就是我们编排容器的各个数据结构。

> 在kubernetes中，每个Object都有Spec和Status两个属性。

### Node

Node代表k8s集群中的每一台服务器。

``` shell
[root@master /]# kubectl get nodes
NAME     STATUS   ROLES    AGE   VERSION
master   Ready    master   71d   v1.16.4
work1    Ready    <none>   71d   v1.16.4
```

注意这里的角色：

- **master**
- **<none>**

这里的<none>就意味着这是一个worker。

`kubectl get nodes`的结果表明：当前集群中有一个master和worker

### Namespace

命名空间是类似Rabbit MQ的虚拟主机[^vhost]。

> 在 Kubernetes 中，**名字空间（Namespace）** 提供一种机制，将同一集群中的资源划分为相互隔离的组。 
>
> ~~同一名字空间内的资源名称要唯一，但跨名字空间时没有这个要求。 名字空间作用域仅针对带有名字空间的[对象](https://kubernetes.io/zh-cn/docs/concepts/overview/working-with-objects/#kubernetes-objects)， （例如 Deployment、Service 等），这种作用域对集群范围的对象 （例如 StorageClass、Node、PersistentVolume 等）不适用。~~

以上是来自官网的描述，但我们初学者只想关注Namespace是什么，对其他的说明、StorageClass、PersistentVolume只是感觉到头疼。

可以说Namespace是kubernetes对象的共同的家，常见的Pod、Service、Deployment都住在这里。

``` shell
[root@master /]# kubectl get namespace
NAME                   STATUS   AGE
default                Active   71d
kafka                  Active   2d4h
kube-node-lease        Active   71d
kube-public            Active   71d
kube-system            Active   71d
kubernetes-dashboard   Active   24h
```

[^vhost]: 在不同vhost中，各个vhost下的用户、交换机、队列无法互相通信，相当于一种隔离。

#### Namespace下的Object

> 初学者可以先跳过

并不是所有的Object都属于Namespace，我们可以通过下方command查看哪些Object在Namespace下。

``` shell
kubectl api-resources --namespaced=true
kubectl api-resources --namespaced=false
```

### Pod

在Kubernetes中，Pod是容器们居住的场所。

如果把容器比作犯人，那么Pod就是牢房。

``` shell
# 在kafka命名空间下里有两个pod:kafka-6dd8d74db-6nxrj和zookeeper-69c999f9d-2f9lv
[root@master ~]# kubectl get pods -n kafka
NAME                        READY   STATUS    RESTARTS   AGE
kafka-6dd8d74db-6nxrj       1/1     Running   8          149m
zookeeper-69c999f9d-2f9lv   1/1     Running   3          2d5h

# 通过describe命令可以查看pod的详情
[root@master ~]# kubectl describe pods kafka-6dd8d74db-6nxrj  -n kafka
```

### Deployment



### Service

Service在Kubernetes中承担了服务发现的重任。

Service有四种类型：

- ClusterIP
- NodePort
- LoadBalancer
- ExternalName

其中ClusterType和NodeType是最基础的。

ClusterType是Service的默认值，仅用于集群的内部访问。

NodeType则通过一个静态端口，在每个节点上暴露该服务。

``` shell
[root@master ~]# kubectl get services -n kafka
NAME        TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)                      AGE
kafka       NodePort    10.100.178.196   <none>        9092:31694/TCP               25h
zookeeper   ClusterIP   10.103.118.140   <none>        2181/TCP,2888/TCP,3888/TCP   2d1h

```

在上述例子中，我通过NodePort建立了9092到31694之间的映射，另外我有`192.168.192.11`与`192.168.192.12`两台Node，那么我可以通过任意Node的31694端口访问到`10.100.178.196:9092`。

``` shell
[root@master ~]# kubectl describe svc kafka -n kafka 
Name:                     kafka
Namespace:                kafka
Labels:                   <none>
Annotations:              kubectl.kubernetes.io/last-applied-configuration:
                            {"apiVersion":"v1","kind":"Service","metadata":{"annotations":{},"name":"kafka","namespace":"kafka"},"spec":{"ports":[{"port":9092,"target...
Selector:                 app=kafka
Type:                     NodePort
IP:                       10.100.178.196
Port:                     <unset>  9092/TCP
TargetPort:               9092/TCP
NodePort:                 <unset>  31694/TCP
Endpoints:                10.244.1.31:9092
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>

```

## Deployment

## Component

## Kubernetes 网络

## Kubernetes DNS 解析

# Usag

## Namespace

``` shell
kubectl get namespaces 

kubectl delete namespaces <namespaces>
```

## Service

``` shell
# 查看service
kubeclt get svc
```

### Kubernetes 对外暴露服务

## Pod

Pod是Kubernet的最小工作单元，包含一个或者多个容器。

``` shell
# 创建pod
# replicas 副本
kubectl run nginx-dep --image=nginx:1.7.9 --port=80 --replicas=2 --dry-run
kubectl run nginx-dep --image=nginx:1.7.9 --port=80 --replicas=2

# 
kubectl get deployment

# 查看服务信息
kubectl get pods -o wide

# 查看服务详情
kubectl describe pod nginx-dep-5779c9d6c9-jw5gc

# 删除pod(模拟pod出现故障)
kubeclt delete nginx-dep-5779c9d6c9-745b2
```

# Sample

## kubectl run

``` shell
# 创建一个应用程序
kubectl run nginx-dep --image=nginx:1.7.9 --port=80 --replicas=2
# 查看服务信息
kubectl get pods -o wide
# 查看pod详情
kubectl describe pod nginx-dep-5779c9d6c9-745b2

# 进入容器查看
kubectl exec -it nginx-dep-5779c9d6c9-745b2 -c nginx-dep /bin/bash

# 退出容器
exit

# 查看日志
kubectl logs kafka-6b969957f8-878gm -n kafka
```


## Firewall

kubernetes集群访问需要放行相关端口：

``` shell
# 允许API服务器端口
firewall-cmd --zone=public --add-port=6443/tcp --permanent

# 允许Flannel VXLAN端口（如果您的网络使用了VXLAN）
firewall-cmd --zone=public --add-port=4789/udp --permanent

# 允许kubelet端口
firewall-cmd --zone=public --add-port=10250/tcp --permanent
firewall-cmd --zone=public --add-port=10255/tcp --permanent

# 允许kube-proxy端口
# 这里假设kube-proxy使用了动态分配的端口范围，您可能需要根据实际情况调整
firewall-cmd --zone=public --add-port=30000-32767/tcp --permanent

# 允许Pod网络范围（PodCIDR）中的通信端口
# 这里假设PodCIDR范围是10.244.0.0/16，您可能需要根据实际情况调整
firewall-cmd --zone=public --add-source=10.244.0.0/16 --permanent

# 重新加载防火墙规则
firewall-cmd --reload
```

注意，需要放行CIDR，CIDR的查询方式如下：

``` shell
kubectl describe node master | grep -i podcidr
kubectl describe node work1 | grep -i podcidr
```

## 暴露服务

``` shell
# 创建service
kubectl expose deployment nginx-dep --name=nginx-service --port=8080 --target-port=80

# 查看service
kubectl get svc
# 查看service的label配置
kubectl describe svc nginx-service

# 设置windows访问
# 通过以下命令编辑，并将ClusterIP类型修改为NodePort
kubectl edit svc nginx-service

# 查看service绑定端口
kubectl get svc
```

## Scale

``` shell 
kubectl scale --replicas=4 deployment nginx-dep
```

## Update and rollback

``` shell 
# update
kubectl set image deployment nginx-dep nginx-dep=nginx:1.9.1
# roll back
kubectl rollout undo deployment nginx-dep
```

