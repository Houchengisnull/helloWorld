[toc]

# Suggestion

如果是Kubernetes的初学者，我建议从官网的教程开始看起：[教程](https://kubernetes.io/zh-cn/docs/tutorials/)。

官网其他的东西先不要看，就像天龙八部里灵鹫宫的图画，虚竹看了受益匪浅，梅兰竹菊却会受内伤。

# Install flannel

不论是Master还是Worker，都要安装CNI网络插件[^CNI]。

[^CNI]: CNI网络用于让Kubernetes给容器分配集群内部的IP地址。

Flannel就是一种CNI网络插件。

- 安装

``` shell
yum install flannel -y
```

- 启动

``` shell
systemctl daemon-reload
systemctl enable flanneld
systemctl start flanneld
systemctl status flanneld
```

- 安装插件

``` shell
mkdir /opt/cni/bin
tar zxvf cni-plugins-linux-amd64-v0.8.6.tgz -C /opt/cni/bin
```

# Install kubernetes

## 配置环境

1. 关闭selinux

   Linux安全相关。

   ``` shell
   # 查看
   getenforce 
   # 临时关闭
   setenforce 0
   ```

   修改配置(重启不生效)

   ``` shell
   # 永久关闭
   vi /etc/sysconfig/selinux
   
   SELINUX=disabled
   ```

2. 关闭swap

   ``` shell
   # 临时
   swapoff -a
   # 永久
   sed -i.bak '/swap/s/^/#/' /etc/fstab
   ```

3. 允许ip_forward

   ``` shell
   # 0 表示禁止
   echo "1" > /proc/sys/net/ipv4/ip_forward
   ```

## 配置源

1. 配置centos7、docker源

   在这个过程中，我发现我的CentOS7系统默认的wget有些问题，需要重新安装：

   ``` shell
   yum remove wget
   yum install wget
   ```

   这里需要注意备份：

   ``` shell
   cd /etc/yum.repos.d/
   
   # backup
   mv CentOS-Base.repo CentOS-Base.repo.bak
   
   # 下载centos7源和docker源
   wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
   wget -P /etc/yum.repos.d/ http://mirrors.aliyun.com/repo/epel-7.repo
   wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
   
   yum clean all && yum makecache fast
   ```

2. 配置k8s源

   ``` shell
   cat <<EOF > /etc/yum.repos.d/kubernetes.repo
   [kubernetes]
   name=Kubernetes
   baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
   enabled=1
   gpgcheck=0
   EOF
   ```

## 安装Docker

1. 安装Docker

   ``` shell
   yum install docker-ce-18.09.9 docker-ce-cli-18.09.9 containerd.io -y
   ```

2. 配置docker的`--cgroup-driver=systemd`

   ``` shell
   mkdir /etc/docker
   tee /etc/docker/daemon.json <<EOF
   {
       "registry-mirrors":["https://v16stybc.mirror.aliyuncs.com"]
       , "exec-opts":["native.cgroupdriver=systemd"]
   }
   EOF
   cat /etc/docker/daemon.json
   
   
   #vi /etc/docker/daemon.json
   #
   #{
   #	"registry-mirrors":["https://v16stybc.mirror.aliyuncs.com"]
   #    , "exec-opts":["native.cgroupdriver=systemd"]
   #}
   
   ## 执行完这一步之后需要重启docker与kubenet令配置生效
   systemctl daemon-reload
   systemctl restart docker
   ```

   [^cgroup driver]: control group driver是Linux系统内核提供的特性，主要用于限制和隔离一组进程对系统资源的使用。

3. 启动docker并配置开机启动

   ``` shell
   systemctl enable docker && systemctl start docker
   ```

## 安装k8s

1. 安装k8s

   ``` shell
   yum install -y kubelet-1.16.4 kubeadm-1.16.4 kubectl-1.16.4
   # 卸载
   # yum remove -y kubelet-1.16.4 kubeadm-1.16.4 kubectl-1.16.4
   ```

2. 设置开机启动

   ``` shell
   systemctl enable kubelet && systemctl start kubelet
   ```

3. 配置kubectl上下文到环境中

   ``` shell
   echo "source <(kubectl completion bash)" >> ~/.bash_profile
   cd ~
   source .bash_profile
   ```

4. 内核参数修改

   k8s网络一般使用flannel，该网络需要设置内核参数bridge-nf-call-iptables=1

   ``` shell
   vi /etc/sysctl.d/k8s.conf
   net.bridge.bridge-nf-call-ip6tables = 1
   net.bridge.bridge-nf-call-iptables = 1
   ```

   执行sysctl -p /etc/sysctl.d/k8s.conf使之生效

   ``` shell
   sysctl -p /etc/sysctl.d/k8s.conf
   ```

# Master Node

## Init

1. 设置节点名称：

   ``` shell
   hostnamectl set-hostname master
   ```

   这部需要优先做，再初始化之后kubernetes会使用hostname作为节点名称

2. 初始化

   ``` shell
   # sudo kubeadm init
   # 这一步非常耗时, 需要拉去镜像
   sudo kubeadm init --image-repository registry.aliyuncs.com/google_containers --kubernetes-version v1.16.4 --pod-network-cidr=10.244.0.0/16
   ```

   执行结果如下:

   ![image-kubernet-master-node-install](../images/kubernet-master-node-install-success.png)

   在执行完成后, 根据提示执行：

   ``` shell
   mkdir -p $HOME/.kube
   sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
   sudo chown $(id -u):$(id -g) $HOME/.kube/config
   ```

3. 配置pod network

   ``` shell
   # 执行成功后，会出现/etc/cni/net.d/
   kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/2140ac876ef134e0ed5af15c65e414cf26827915/Documentation/kube-flannel.yml
   ```

## Reset

当你安装失败或者其他什么原因需要重置Node(包括Master和Worker)时：

``` shell
sudo kubeadm reset
```

## Generate token

token主要用于子节点加入集群，我们可以使用方式生成命令：

``` shell
# 生成join command
kubeadm token create --print-join-command
```

- 查看Token

``` shell
# 查询token list
kubeadm token list
```

# Work Node

- **设置机器名**

  ``` shell 
  hostnamectl set-hostname work1
  ```

- **配置ip**

  ``` shell
  vi /etc/hosts
  
  192.168.192.11 work1
  ```

- **加入集群**

  ``` shell
  kubeadm join 192.168.192.10:6443 --token znygl1.rdkprye9ibg5l27y \
      --discovery-token-ca-cert-hash sha256:ae4b04bbe7aee468b410e5f20cbbbd02b80d9761dd915ecf17f88f42fa57debb 
  ```

## Reset

当Master节点Reset后，需要重新加入集群时，我们同样需要先执行：

``` shell
sudo kubeadm reset
```
