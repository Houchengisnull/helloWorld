[toc]

# Debug

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

# Http远程调用

- 参考
- [springboot项目间接口调用实现：RestTemplate](https://blog.csdn.net/zhanglf02/article/details/89842372)
- [Spring Boot 进阶 | 01 使用Feign作为HTTP客户端调用远程HTTP服务](https://blog.csdn.net/u010541670/article/details/80068575)

> - **2021-8-11**
>
>   旁边来了一个实习生妹妹，导师要求实现一个远程调用`HTTP`接口的`Demo`。她在网上搜到了几种方式来实现，除了最常见的`Apache`提供的`HttpClient`，还发现了两种`Spring boot`本身集成的方式——一种全新玩法。

## RestTemplate

- **参考**
- [springboot项目间接口调用实现：RestTemplate](https://blog.csdn.net/zhanglf02/article/details/89842372)
- [如何使用RestTemplate访问restful服务](https://www.jianshu.com/p/c9644755dd5e)

`RestTemplate`是`Spring`中自带的`Rest客户端工具`。一般情况下，访问`Restful服务`需要使用`Apache`的`HttpClient`。但是`HttpClient`通常比较繁琐。

`Spring`则提供了一个`RestTemplate`来简化开发。

实现起来可以十分简单：

``` java
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public String test(){
        return "测试成功";
    }

    @GetMapping("/call")
    public String callTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.getForEntity("http://127.0.0.1:8090/hc-web/test/", String.class);
        return res.getBody();
    }

}
```

### 设置底层连接方式

我们可以使用`RestTemplate`的无参构造方法快速实现一个`Restful`接口调用。同样可以结合`HttpClient`与`HttpComponentsClientHttpRequestFactory`设置一些请求属性，并将通过一个请求工厂快速达到我们的目的。

``` java
//生成一个设置了连接超时时间、请求超时时间、异常最大重试次数的httpClient
RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(30000).build();
HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config).setRetryHandler(new DefaultHttpRequestRetryHandler(5, false));
HttpClient httpClient = builder.build();
//使用httpClient创建一个ClientHttpRequestFactory的实现
ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//ClientHttpRequestFactory作为参数构造一个使用作为底层的RestTemplate
RestTemplate restTemplate = new RestTemplate(requestFactory);
```

注意，以上的`HttpClient`是`Apache`的实现的Http调用工具，而`HttpComponentsClientHttpRequestFactory`是`Spring`对这个`HttpClient`的集成与封装。

## Feign

### Spring Boot 2下导入依赖后使用Feign找不到@EnableFeignClients的解决办法

- **参考**
- [Spring Boot 2下导入依赖后使用Feign找不到@EnableFeignClients的解决办法](https://blog.csdn.net/u010728594/article/details/103295796)

需要在`pom.xml`中增加如下配置:

``` xml
<!--导入-->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Greenwich.SR3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

其实这是因为在`spring boot`项目中引入了`spring cloud`依赖，但是没有显式的声明版本号导致依赖没有正确引入。只要增加一个<version>标签及版本号，`Feign`的依赖即可正常引入。

``` xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
        <!-- <version>2.1.3.RELEASE</version> -->
    </dependency>

    <dependency>
        <groupId>com.netflix.feign</groupId>
        <artifactId>feign-httpclient</artifactId>
        <!--<version>${feign-httpclient}</version>-->
    </dependency>
```



# FAQ

## 深入Spring Boot：怎样排查 Cannot determine embedded database driver class for database type NONE

## Spring启动报错org.springframework.boot.builder.SpringApplicationBuilder

- **参考**
- [Spring启动报错org.springframework.boot.builder.SpringApplicationBuilder](https://blog.csdn.net/hanjun0612/article/details/106568877)

在通过`SpringApplicationBuilder`启动项目时，由于`spring-boot`项目中依赖中引入了`spring cloud`的依赖，两者版本不兼容导致。

目前已注释：

``` xml
	<!-- <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-feign</artifactId>
      </dependency>

      <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-openfeign</artifactId>
          <version>2.1.3.RELEASE</version>
      </dependency>-->
```

