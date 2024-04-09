[toc]

# Overview

- 取代虚拟机

  以往**互联网**行业需要部署多个应用，往往需要安装多个虚拟机，在资源以及便捷方面不如Docker安装简单。

> 但是一旦出现问题，就要求运维人员及开发人员熟悉docker，这就增加了处理的成本。

# Install

``` shell
yum install -y yum-utils

# 设置Docker镜像仓库
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum install docker-ce

# 检查docker是否安装成功
docker version
docker info

# 启动
service docker start
# 随系统启动
chkconfig docker on

# docker run hello-world

# 卸载
# 找到所有已安装的docker组件
yum list installed | grep docker
```

# Image

**常用命令:**

``` shell
# 查看镜像
docker images

# 移除
docker rm [id|name]

# 构建
docker build -t [name:version] [path]

# tag
tag [name:version]|[repository/name:version]

# 查看指定版本历史
docker history [name:version]

# 保存镜像
docker save -o ***.tar [name:v7]
docker save [name:v7] > ***.tar

# 加载镜像
docker load --input ***.tar
docker load < ***.tar
```

## Commit

由容器创建镜像

``` shell
# -a 作者
# -m 镜像名
docker commit -a "peter" -m "mvc" tomcat tomcat:mvc
```

一般用于在已有容器的基础上，简单调整一下，以便快速复用。

## Dockerfile

| 要素名     | 作用                                                         |
| ---------- | ------------------------------------------------------------ |
| FROM       | 基础镜像                                                     |
| MAINTAINER | 维护者                                                       |
| RUN        |                                                              |
| ADD/COPY   | 新增文件(会自动解压)                                         |
| USER       | 运行run命令的用户                                            |
| WORKDIR    | 设置工作目录                                                 |
| VOLUME     | 挂载主机目录                                                 |
| EXPOSE     | 声明运行时容器提供服务端口：1、帮助镜像使用者理解服务守护端口，方便配置映射；2、在运行时使用随机端口映射；（在docker run -p会自动随机EXPOSE的端口） |
| CMD        | 容器启动后需要处理的事项                                     |
| ENV        | 设置环境变量                                                 |
| ENTRYPOINT | 把整个容器变成可执行文件，且不能通过替换CMD的方法来改变创建container的方式。当定义ENTRYPOINT后，CMD只能作为参数传递 |



### Java镜像

- **创建Dockerfile**

  ``` shell
  # syntax=docker/dockerfile:1
  FROM openjdk:8
  # 指定工作目录
  WORKDIR /hc-docker
  # 复制文件
  COPY ["hc-docker-0.0.1-SNAPSHOT.jar", "hc-docker.jar"]
  # 主业
  ENTRYPOINT ["java", "-jar", "hc-docker.jar"]
  EXPOSE 8080
  ```

- 构建镜像

  ``` shell
  # docker中镜像格式为[name:version]	
  docker built -t hc-docker:v1 .
  ```

- 运行容器

  ``` shell
  docker run -d -p 8080:8080 --name  hc-docker hc-docker:v1
  ```

**注意事项**:

- Dockerfile的名称并不重要

## MAVEN插件

docker-maven-plugin，打通环境。

``` shell
mvn clean package docker:build
```

# Container

``` shell
# 创建并启动
docker run [name]

docker start/stop/restart [name]
docker kill [name]
docker rm [name]

# 暂停或恢复
docker pause/unpause [name]

# 查看容器列表
docker ps

docker cp [local path] [name]:[container path]

# 
docker exec -it hc-docker /bin/bash
# 退出并关闭容器
exit
# 退出不关闭容器
Ctrl + P +Q

# 导入导出
docker export -o ***.tar hc-docker
docker export hc-docker > ***.tar
docker import -o ***.tar hc-docker:v7

docker logs hc-docker
```

> -it：交互
>
> -d: 后台

# Repository

- [官方仓库](https://hub.docker.com)

``` shell
docker pull [image:version]
docker push [repository]/[image:version]
docker search [image]
# 标记镜像到某一仓库
docker tag [image:version] [repository]/[image:verison]
docker login
```

- 私有仓库

``` shell
docker pull registry
docker run -d --name reg -p 5000:5000 registry

curl http://localhost:5000/v2/catalog

# 开放https
vi /etc/docker/daemon.json
systemctl daemon-reload
systemctl restart docker
```

# 挂载

## volume

``` shell
# 将容器的/opt/data挂载出去
docker run --name hc-docker -v /opt/data -it centos /bin/bash
# 查看挂载信息
docker inspect hc-docker

# 容器中新增data目录, 主机同时增加
docker run --name hc-docker -v /opt/data:/data -it centos /bin/bash
```

## volumes-from

引用数据卷。

``` shell
# 新启动一容器，自动得到一目录，内容与data容器相同
docker run -it --rm --volumes-from data --name app centos /bin/bash
```

## 删除数据卷

``` shell
docker rm -v data
```



# Other

## Docker-Compose

当项目设计容器较多时，需要一个管理容器的工具——docker-compose。

## Swarm

Docker官方提供的集群管理工具。