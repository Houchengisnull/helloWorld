[toc]

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

  https://blog.csdn.net/chachapaofan/article/details/88697452