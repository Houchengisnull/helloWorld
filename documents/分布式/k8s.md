[toc]

- [官网](https://kubernetes.io/zh-cn/docs)



# 集群搭建

## 安装

- 关闭selinux

  ``` shell
  # 查看
  getenforce 
  # 临时关闭
  setenforce 0
  # 永久关闭
  vi /etc/sysconfig/selinux
  
  SELINUX=disabled
  ```

- 关闭swap

  ``` shell
  # 临时
  swapoff -a
  # 永久
  sed -i.bak '/swap/s/^/#/' /etc/fstab
  ```

- 允许ip_forward

  ``` shell
  # 0 表示禁止
  echo "1" > /proc/sys/net/ipv4/ip_forward
  ```

- 配置centos7、docker源

  ``` shell
  cd /etc/yum.repos.d/
  rm -rf *
  
  # 下载centos7源和docker源
  wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
  wget -P /etc/yum.repos.d/ http://mirrors.aliyun.com/repo/epel-7.repo
  wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
  ```

- 配置k8s源

  ``` shell
  cat <<EOF > /etc/yum.repos.d/kubernetes.repo
  [kubernetes]
  name=Kubernetes
  baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
  enabled=1
  gpgcheck=0
  EOF
  ```
  
- 安装docker

  ``` shell
  yum install docker-ce-18.09.9 docker-ce-cli-18.09.9 containerd.io -y
  ```
  
- 配置docker的`--cgroup-driver=systemd`

  
  
  ``` shell
  vi /etc/docker/daemon.json
  
  {
  	"registry-mirrors":["https://v16stybc.mirror.aliyuncs.com"]
  	, "exec-opts":["native.cgroupdriver=systemd"]
  }
  
  ## 执行完这一步之后需要重启docker与kubenet令配置生效
  systemctl daemon-reload
  systemctl restart docker
  systemctl restart kubelet
  ```
  
  [^cgroup driver]: control group driver是Linux系统内核提供的特性，主要用于限制和隔离一组进程对系统资源的使用。
  
- 启动docker并配置开机启动

  ``` shell
  systemctl enable docker && systemctl start docker
  ```
  
- 安装k8s

  ``` shell
  yum install -y kubelet-1.16.4 kubeadm-1.16.4 kubectl-1.16.4
  # 卸载
  # yum remove -y kubelet-1.16.4 kubeadm-1.16.4 kubectl-1.16.4
  ```
  
- 设置开机启动

  ``` shell
  systemctl enable kubelet && systemctl start kubelet
  ```

- 配置kubectl上下文到环境中

  ``` shell 
  echo "source <(kubectl completion bash)" >> ~/.bash_profile
  cd ~
  source .bash_profile
  ```

- 内核参数修改

  k8s网络一般使用flannel，该网络需要设置内核参数bridge-nf-call-iptables=1

  ``` shell
  vi /etc/sysctl.d/k8s.conf
  net.bridge.bridge-nf-call-ip6tables = 1
  net.bridge.bridge-nf-call-iptables = 1
  ```

  执行`sysctl -p /etc/sysctl.d/k8s.conf`

  ``` shell
  sysctl -p /etc/sysctl.d/k8s.conf
  net.bridge.bridge-nf-call-ip6tables = 1
  net.bridge.bridge-nf-call-iptables = 1
  ```

## Master节点配置

- 参考

  [使用 Kubeadm 部署](http://icyfenix.cn/appendix/deployment-env-setup/setup-kubernetes/setup-kubeadm.html)

  [如何解决kubeadm init初始化时dial tcp 127.0.0.1:10248: connect: connection refused](https://www.myfreax.com/how-to-solve-dial-tcp-127-0-0-1-10248-connect-connection-refused-during-kubeadm-init-initialization/)

- **初始化**

  初始化`kubernet`的`control-pane`。

  在执行这一步时总是报错，通过`journalctl -xeu kubelet`发现：存在大量的拒绝连接错误。

  搜索一番后，了解到这是因为cgroup驱动问题造成的。kubernet的驱动是systemd，而docker的驱动则是cgroupfs。根据官方建议，使用systemd更加稳定，因此将docker的配置修改为cgroupfs。

  重启docker和kubernet生效。

  ``` shell
  # sudo kubeadm reset
  # sudo kubeadm init
  # 这一步非常耗时, 需要拉去镜像
  sudo kubeadm init --image-repository registry.aliyuncs.com/google_containers --kubernetes-version v1.16.4 --pod-network-cidr=10.244.0.0/16
  
  # 在执行完成后, 根据master的提示执行
  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config
  ```

  执行结果如下:

  ``` shell
  To start using your cluster, you need to run the following as a regular user:
  
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config
  
  You should now deploy a pod network to the cluster.
  Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
    https://kubernetes.io/docs/concepts/cluster-administration/addons/
  
  Then you can join any number of worker nodes by running the following on each as root:
  
  kubeadm join 192.168.192.15:6443 --token aqf3ii.kn1luk9odk5ix0dq \
      --discovery-token-ca-cert-hash sha256:e98fd2e33da8cb07ded532e1d0fd453c8f5e1c2e823962307f4f0589b14b6135
  ```

  ![image-kubernet-master-node-install](../images/kubernet-master-node-install-success.png)

- **添加flannel网络**

  配置pod network

  ``` shell
  kubectl apply -f peter-flannel.yml
  ```

- **查看集群**

  ``` shell
  kubectl get nodes
  ```

  这一步报错TLS握手异常，问题出现在kubenet的api-sever服务，看了日志之后也确实如此。尽管如此，还是按照网上的说法重新设置了一下虚拟机的内存大小，从1g设置为3g。

  重启虚拟机后，问题解决。

  > 20240123
  >
  > 这让我的学习体验十分不好，因为从握手异常真的很难联想到内存不足。
  >
  > 只能说Google开源的产品是具有其独特文化和环境的，国内这种伸手即来、开箱即用的风气真的是不太好。甚至国内各行各业的IT公司将其作为招聘的硬性条件，变相要求所有从业者去学习kubernet，进而导致国内唯一的解决方案就是k8s，实在是一种缺乏创新精神的表现。

## Work节点配置

- **设置机器名**

  ``` shell 
  hostnamectl set-hostname work1
  ```

- **配置ip**

  ``` shell
  vi /etc/hosts
  
  192.168.192.10 work1
  ```

- **加入集群**

  ``` shell
  kubeadm join 192.168.192.15:6443 --token aqf3ii.kn1luk9odk5ix0dq \
      --discovery-token-ca-cert-hash sha256:e98fd2e33da8cb07ded532e1d0fd453c8f5e1c2e823962307f4f0589b14b6135
  ```

# 架构

![Kubernetes 架构](../images/kubernetes-cluster-architecture.svg)
