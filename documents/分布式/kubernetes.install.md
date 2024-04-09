[toc]

# Setting up a cluster

## install flannel

- [network plugin is not ready & cni config uninitialized](https://github.com/kubernetes/kubernetes/issues/103324)

- [failed to find plugin “flannel” in path [/opt/cni/bin]](https://blog.csdn.net/qq_29385297/article/details/127682552)
- [解决k8s安装flannel无法拉取镜像(ImagePullBackOff)](https://blog.csdn.net/sinat_23225111/article/details/125111063)

通过`journalctl -u kubelet -f`查看kubelet的日志。

发现一直在报错`failed to find plugin "flannel" in path [/opt/cni/bin]`。

## Install

- **关闭selinux**

  Linux安全相关。

    ``` shell
    # 查看
    getenforce 
    # 临时关闭
    setenforce 0
    # 永久关闭
    vi /etc/sysconfig/selinux
  
    SELINUX=disabled
    ```

- **关闭swap**

  ``` shell
  # 临时
  swapoff -a
  # 永久
  sed -i.bak '/swap/s/^/#/' /etc/fstab
  ```

- **允许ip_forward**

  ``` shell
  # 0 表示禁止
  echo "1" > /proc/sys/net/ipv4/ip_forward
  ```

- **配置centos7、docker源**

  ``` shell
  cd /etc/yum.repos.d/
  rm -rf *
  
  # 下载centos7源和docker源
  wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
  wget -P /etc/yum.repos.d/ http://mirrors.aliyun.com/repo/epel-7.repo
  wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
  
  yum clean all && yum makecache fast
  ```

- **配置k8s源**

  ``` shell
  cat <<EOF > /etc/yum.repos.d/kubernetes.repo
  [kubernetes]
  name=Kubernetes
  baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
  enabled=1
  gpgcheck=0
  EOF
  ```

- **安装docker**

  ``` shell
  yum install docker-ce-18.09.9 docker-ce-cli-18.09.9 containerd.io -y
  ```

- **配置docker的`--cgroup-driver=systemd`**

  ``` shell
  tee /etc/docker/daemon.json <<EOF
  {
      "registry-mirrors":["https://v16stybc.mirror.aliyuncs.com"]
      , "exec-opts":["native.cgroupdriver=systemd"]
  }
  EOF
  cat /etc/docker/daemon.json
  
  
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

- **启动docker并配置开机启动**

  ``` shell
  systemctl enable docker && systemctl start docker
  ```

- **安装k8s**

  ``` shell
  yum install -y kubelet-1.16.4 kubeadm-1.16.4 kubectl-1.16.4
  # 卸载
  # yum remove -y kubelet-1.16.4 kubeadm-1.16.4 kubectl-1.16.4
  ```

- **设置开机启动**

  ``` shell
  systemctl enable kubelet && systemctl start kubelet
  ```

- **配置kubectl上下文到环境中**

  ``` shell 
  echo "source <(kubectl completion bash)" >> ~/.bash_profile
  cd ~
  source .bash_profile
  ```

- **内核参数修改**

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


## Master Node

- 参考

  [使用 Kubeadm 部署](http://icyfenix.cn/appendix/deployment-env-setup/setup-kubernetes/setup-kubeadm.html)

  [如何解决kubeadm init初始化时dial tcp 127.0.0.1:10248: connect: connection refused](https://www.myfreax.com/how-to-solve-dial-tcp-127-0-0-1-10248-connect-connection-refused-during-kubeadm-init-initialization/)

### Init

初始化`kubernet`的`control-pane`。

在执行这一步时总是报错，通过`journalctl -xeu kubelet`发现：存在大量的拒绝连接错误。

搜索一番后，了解到这是因为cgroup驱动问题造成的。kubernet的驱动是systemd，而docker的驱动则是cgroupfs。

根据官方建议，使用systemd更加稳定，因此将docker的配置修改为cgroupfs。

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

![image-kubernet-master-node-install](../images/kubernet-master-node-install-success.png)

### Add flannel

配置pod network

``` shell
# 执行成功后，会出现/etc/cni/net.d/
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/2140ac876ef134e0ed5af15c65e414cf26827915/Documentation/kube-flannel.yml
```

### Generate token

``` shell
# 查询token list
kubeadm token list

# 生成join command
kubeadm token create --print-join-command
```

## Work Node

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

