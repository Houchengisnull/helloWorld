[TOC]
# 安装

# 管理

## profile

### 目录

`profile`下的文件目录表

| 文件夹名称 | 文件夹描述                                                 |
| ---------- | ---------------------------------------------------------- |
| temp       | 应用运行过程中生成的一些临时文件，例如JSP编译后的class文件 |
| wstemp       | `websphere`运行时生成的临时文件，比如进行配置时保存的配置缓存文件 |
| translog | 事务管理的临时文件，在事务运行时保存日志等 |

http://www.talkwithtrend.com/Question/382627?order=desc

### 安全性管理

即 wasphere控制台

- 默认端口
  - 管理控制端口 9060
  - 管理控制安全端口 9043
  - HTTP传输端口 9080
  - HTTPS传输端口 9443
  - 引导程序端口2809
  - SOAP连接端口 8880

### 通过控制台查看

- Server
  - WebSpere Application Server
    - 端口
      - WC_adminihost 控制台默认端口 (9060)
      - WC_default 应用程序默认访问端口(9080)
    - 详细信息
      - 配置
        - 主机
          - '*' 对所有主机进行开放
        - 端口

> 重启应用服务器生效

### app_install_root 应用程序默认部署路径

> $WAS_HOME/profiles/#{app_server}/installedApps/#{xxxNode01Cell}

### 删除profile文件

- `# manageprofiles -delete -profileName #{profileName}`
- 删除 **$WAS_HOME/profiles**下对应文件夹

# 更新应用程序

## 更改应用程序上下文

- was控制台
- 应用程序
- 应用程序类型
- WebSphere企业应用程序(点击应用)
- Web模块上下文

> 等价修改 Tomcat的 $config/server.xml 中的Host内容

## 手动更新

- 除web.xml文件以外直接替换即可
- web.xml文件
  - 替换安装目录中文件
  - 替换缓存文件
  - was8.5版本同时需要更新web_merge.xml
- 缓存文件目录
  `$WAS_HOME/profiles/#{app_server}/config/cells/#{xxxNode01Cell}/applications`
- web_merge.xml 自动排序 合并

## 使用was自带更新

### 替换, 添加或删除多个文件

- 在META-INF目录下添加属性文件:META-INF/ibm-parialapp-delete.props, 且文件必须是ASCII文件
- 文件路径相对于 META-INF/ibm-parialapp-delete.props

# 类装入器策略

> 在部署WebSphere应用的过程中，经常会发生诸如：`ClassCastException`、`ClassNotFoundException`、`NoClassDefFoundException`、`UnsatisfiedLinkError`的错误。这种有关“类”（Class）的错误，往往来无影——开发环境好的，怎么在生产环境就有问题；而且去无踪——单独建立一个Profile部署一下就没问题了，把Jar包换个目录就OK了。其实要解决这些怪异的问题，首先要了解WebSphere的类加载（Class loader）机制。

## 使用的类装入器以及使用顺序

- Java 虚拟机创建的引导程序、扩展和 CLASSPATH 类装入器引导程序类装入器使用引导类路径（通常是 `jre/lib` 中的类）找到并装入类。扩展类装入器使用系统属性 java.ext.dirs（通常是`jre/lib/ext`）找到并装入类。CLASSPATH 类装入器使用 CLASSPATH 环境变量查找和装入类。CLASSPATH 类装入器装入 WebSphere Application Server 产品在 `j2ee.jar` 文件中提供的 Java 2 Platform, Enterprise Edition（J2EE）应用程序编程接口（API）。由于这个类装入器装入 J2EE API，所以，可以将依赖于 J2EE API 的库添加到类路径系统属性中以扩展服务器类路径。但是，扩展服务器的类路径的首选方法是[添加共享库](http://publib.boulder.ibm.com/tcws_sharedlib_create.html)。
- WebSphere 扩展类装入器WebSphere 扩展类装入器装入在运行时需要的 WebSphere Application Server 类。扩展类装入器使用 ws.ext.dirs 系统属性来确定装入类时所使用的路径。ws.ext.dirs 类路径中的每个目录和这些目录中的每个 Java 归档（JAR）文件或 ZIP 文件都添加到此类装入器使用的类路径中。如果安装在服务器上的应用程序模块引用了与资源提供程序相关联的资源，并且该提供程序指定了资源驱动程序的目录名称，那么 WebSphere 扩展类装入器还将资源提供程序类装入到服务器中。
- 一个或多个应用程序模块类装入器，它们负责装入在服务器中运行的企业应用程序的元素应用程序元素可以是 Web 模块、企业 bean（EJB）模块、资源适配器归档（RAR 文件）和依赖项 JAR 文件。应用程序类装入器按照 J2EE 类装入规则从企业应用程序装入类和 JAR 文件。WebSphere Application Server 允许使共享库与应用程序相关联。
- 零个或多个 Web 模块类装入器缺省情况下，Web 模块类装入器装入 WEB-INF/classes 和 WEB-INF/lib 目录的内容。Web 模块类装入器是应用程序类装入器的子代。可以指定使用应用程序类装入器来装入 Web 模块的内容，而不是使用 Web 模块类装入器来装入这些内容。

> 关于WebSphere的类加载器的层次结构，以下的几点说明可能更有助于进一步的理解类的查找和加载过程：
>
> - 每个类加载器负责在自身定义的类路径上进行查找和加载类。
> - 一个子类加载器能够委托它的父类加载器查找和加载类，一个加载类的请求会从子类加载器发送到父类加载器，但是从来不会从父类加载器发送到子类加载器。
> - 一旦一个类被成功加载，JVM 会缓存这个类直至其生命周期结束，并把它和相应的类加载器关联在一起，这意味着不同的类加载器可以加载相同名字的类。
> - 如果一个加载的类依赖于另一个或一些类，那么这些被依赖的类必须存在于这个类的类加载器查找路径上，或者父类加载器查找路径上。
> - 如果一个类加载器以及它所有的父类加载器都无法找到所需的类，系统就会抛出ClassNotFoundExecption异常或者NoClassDefFoundError的错误。

## 类装入器隔离策略 class loader policy

### 应用服务器配置

- Single

  整个应用服务器上的所有`Application`使用同一个类加载器。

- Multiple

  应用服务器上的每个`Application`使用自己的类加载器。

### 应用程序设置：Web 模块类加载器策略

- Application

  整个`Application`内所有的`jar`和`Web 模块`使用同一个类加载器

- Module

  `Application`内的每个`Web 模块`使用自己的类加载器。`Application`类加载器依然存在，负责加载`Web 模块`以外其他类。

## 查看实际类加载顺序

- Troubleshooting -> Class Loader Viewer

### 查看类加载日志

1、于进程定义中勾选详细类装入以在`native_stderr.log`查看日志。

2、可通过设置控制台`Troubleshooting` -> `Log and trace`日志级别为

`*=info: com.ibm.ws.classloader.*=all`

以在对应目录下查看更详细日志（`trace.log`）

## 类加载顺序与`Tomcat`对比

web容器类优先于web应用类，`Tomcat`则相反，故有时在`Tomcat`不会出现问题却会在`websphere`出现问题。

## 参考

http://blog.sina.com.cn/s/blog_62bcc50c0100uciw.html

https://blog.csdn.net/zhouyong0/article/details/7968902

http://www.blogjava.net/jjshcc/archive/2014/05/13/413618.html

# 数据源

## customer properties

### 设置current Schema

# 修改端口

*$WAS_HOME/profiles/#{serverName}/config/cells/#{cellName}/nodes/#{nodeName}/serverindex.xml*

中定义各端口。

# 升级JDK

# was永久使用

- 修改系统时间
- 删除$WAS_HOME/properties/was.license

# 参考

<https://edu.51cto.com/center/course/lesson/index?id=153550>

http://blog.chinaunix.net/uid-20802110-id-4121432.html



