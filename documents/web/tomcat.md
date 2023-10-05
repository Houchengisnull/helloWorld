[TOC]

# 部署方式

- 隐式部署
- 显式部署
  - 添加context
  - 创建xml

# 目录结构

## bin

- catalina脚本

- Version

  查看当前tomcat版本号；

- Configtest

  检查Tomcat配置文件server.xml格式、内容是否合法；

## conf

- web.xml

  Tomcat中所有应用默认部署描述文件，定义了基础的Servlet和MIME映射。若应用中不包含web.xml，那么Tomcat将使用此文件初始化部署。

- server.xml

  该文件中，每个Server代表了一个Tomcat实例，包含一个或多个Services，其中每个Service都有自己的Engines和Connectors。

- context.xml

  自定义所有Web应用均需要加载的Context配置，优先使用应用自身的context.xml。

> context.xml与server.xml中配置的context的区别：
>
> server.xml是不可动态重加载的资源，而Tomcat会定时扫描context.xml，一旦发现文件被修改，则重新加载。

- catalina.policy

  相关权限

- tomcat-users.xml

- logging.properties

  设置日志

## webapps

存放 web 项目的目录，其中每个文件夹都是一个项目；如果这个目录下已经存在了目录，那么都是 tomcat 自带的项目。

其中 ROOT 是一个特殊的 项目，在地址栏中没有给出项目目录时，对应的就是 ROOT 项目。http://localhost:8080/examples ，进入示例项目。其中 examples 就是项目名，即文件夹 的名字。

## lib

Tomcat类库，所有的项目都可以共享。

## work

运行时生成的文件，例如:访问JSP时生成的Java文件与class文件。

## logs

- localhost-*.log	Web应用内部的程序日志；
- catalina-*.log	控制台日志；
- host-manager.*.log	Tomcat管理页面host-manager的操作日志，建议关闭；
- localhost_access_log_*.log	用户请求Tomcat的访问日志；

## temp

Tomcat的临时目录。

# server.xml

## 解压war unpackWARs

``` xml
<Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true">
```

# 异常

## Invalid character

> Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986.

`Tomcat`在7.0.73版本以后，添加了对`http head`的验证。

当前端申请的`url`包含一些字符时将出错，比如：`{`、`}`、`<`、`>`等待。

但我们依然有解决方案：

修改`tomcat`的`catalina.properties`文件

找到这一行：

``` properties
tomcat.util.http.parser.HttpParser.requestTargetAllow=|{}?&
```

> spring-boot 2.0.0内置tomcat版本在8.0以上