[toc]

# 常用

## debug

在`application.properties`设置`debug`属性，即可打印堆栈日志。

``` properties
debug=true
```

# 热部署

- 配置依赖

``` xml
 <!--devtools热部署-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
    <scope>runtime
    </scope>
</dependency>
```

- 配置`application.properties`

``` properties
spring.devtools.restart.enabled=true
```

检查热部署是否成功。

若不成功，可能需要设置`IDE`的自动构建。以下以`IDEA`为例子：

1. 首先打开`setting`的`Compiler`选项，勾选`Build project automatically`；
2. 通过快捷键`ctrl + shift + alt + /`，进入`Registry`菜单，勾选`compiler.automake.allow.wher.app.running`

>  在使用`IDEA`时，可以直接创建`springboot project`，设置为`update class and resources`

- 参考

  [springboot实现热部署](https://blog.csdn.net/chachapaofan/article/details/88697452)

# 集成Spring-web

- **maven添加依赖**

  ``` xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  ```

## 设置Server启动端口

- **参考**

  [如何为Spring Boot应用程序配置端口](https://www.cnblogs.com/exmyth/p/11345520.html)

- 常规方法

``` properties
# 在application*.properties中添加<key-value>
server.port=8080
```

- 在`main`方法中将属性设置为系统属性

``` java
System.setProperty("server.port", "8090")
```

# FAQ

## 深入Spring Boot：怎样排查 Cannot determine embedded database driver class for database type NONE

