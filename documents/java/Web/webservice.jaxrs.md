[toc]

- 参考

  SpringBoot2.x系列教程38--整合JAX-RS之利用Jersey框架实现RESTful - 千锋java学院的文章 - 知乎 https://zhuanlan.zhihu.com/p/121645580
  
  https://blog.csdn.net/lhf2009913/article/details/20641179
  
  https://www.cnblogs.com/kitor/p/11101533.html

# JAX-RS

在`Java EE 6`中引入对`JSR-311`支持。`JSR-311`目的在于引入接口开发`REST应用`，避免依赖第三方框架。

同时，`JAX-RS`使用`POJO`编程模型和基于注解的配置，并集成了`JAXB`，从而有效缩短`REST应用`的开发周期。

`JAX-RS`定义`API`位于`java.ws.rs`包中。

# Jersey

`Jersey`是对`JAX-RS(JSR-311)`协议的实现，用于构建`RESTful Web Service`，也可以将`Jersey`理解为一个`RESTful`框架。

> 2020.09.01
>
> `Jersey`对请求数据限制较多，而`Spring MVC`便宽松很多。

## 接收参数

除了使用注解指定参数外，我们还可以**通过Bean接收Json字符串**

## 常用注解

### @Path

类似于`Spring MVC`中的`@RequestMapping`。

### @Produce

指定返回给客户端的`MIME媒体类型`。

并非一定需要该注解修饰接口方法。

它可以指定多个值，比如：

``` java
@Produce({"application/xml; qs=0.9", "application/json"})
```

当我们使用`@Produce("application/json")`时，其效果等价于`Spring MVC`中的`@ResponseBody`。

### @Consumes

该注解用于指定可以接受的客户端请求的`MIME媒体类型`，我们`Http请求`的`Content-Type`即其中一种。

并非一定需要该注解修饰接口方法。

``` java
@Consumes({"text/plain", "application/json", "text/html"})
```

- **表示任意类型：** `*/*`

### @QueryParam

从请求中提取参数。类似于`@RequestParam`，但只能获取基本数据类型。

实际上`@RequestParam`也可以处理表单中的数据。

### @FormParam

处理类型为`application/x-www-form-urlencoded`数据，即表单中的数据。

### @PathParam

提取路径中参数，类似于`@PathVariable`。

### @BeanParam

这个注解没有使用过，它将从请求的各部分中提取参数并注入到对应的`Bean`。似乎类似于`@RequestParam`可以帮助我们将数据映射到`Bean`中。

``` java
	@GET
	public String add(@BeanParam MyParam param){
		int c = param.a+param.b;
		return "add successful";
	}
```

### @HeaderParam

从请求的头部中提取`Header`。

### @CookieParam

提取`cookie`。

### @Context

获取`request`和`response`上下文。

也可以通过`@Context`获取`QueryParam`

``` java
public String getInfo(@Context UriInfo info) {
    info.getQueryParameters().getFirst("start");
    return "success";
}
```

## 引入依赖

``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jersey</artifactId>
</dependency>
```

## 配置Jersey

`spring boot`官方不建议在使用`配置类`是，通过扫描注解的方式加载`Bean`，而是使用建议手动添加。

> Jersey’s support for scanning executable archives is rather limited. For example,  it cannot scan for endpoints in a package found in WEB-INF/classes when running an executable war file.  To avoid this limitation, the packages method should not be used and endpoints should be registered individually using the register method.

- `JerseyConfig`

  ``` java
  @Configuration
  @ApplicationPath("rest")
  public class JerseyConfig extends ResourceConfig {
      public JerseryConfig() {
          register(HelloResource.class); // 即HelloController
      }
  }
  ```

- `HelloResource`

  ``` java
  @Controller
  @Path("hello")
  public class HelloResource {
  
      @Path("say")
      @Get
      public String sayHi(){
          return "success";
      }
  }
  ```

### 设置项目根目录

- `@ApplicationPath`

- `application.properties`

  在配置文件中加入

  ``` properties
  spring.jersey.application-path=rest
  ```

## 注册方式

### web

``` xml
<servlet>
    <servlet-name>Jersey Web Application</servlet-name>
    <servlet-class>
        com.sun.jersey.spi.spring.container.servlet.SpringServlet
    </servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>Jersey Web Application</servlet-name>
    <!-- 相当于设置根目录 -->
    <url-pattern>/rest/*</url-pattern>
</servlet-mapping>
```

### CXF

- `web.xml`

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>

    <!--CXF核心控制器-->
    <servlet>
        <servlet-name>cxfServlet</servlet-name>
        <servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>cxfServlet</servlet-name>
        <url-pattern>/rest/*</url-pattern>
    </servlet-mapping>
</web-app>
```

并在`spring`的`xml配置`中添加

``` xml
 <jaxrs:server address="/userService" serviceClass="com.alibaba.controller.UserController"/>
```

### spring boot

与`spring-boot`集成，需要在`spring-boot`配置文件下添加

``` properties
spring.jersey.type=servlet
# spring.jersey.filter=filter
```

## 单元测试（CXF）

``` java
public class TestDemo {
    @Test
    public void test() {
    //获取服务器数据
    User user = WebClient.create("http://localhost:8081/jaxrs_server_war_exploded/ws/userService/user/1").type(MediaType.APPLICATION_XML).get(User.class);
    System.out.println(user);

    //向服务器发送带参数的数据获取请求,参数类型和参数user
    WebClient.create("http://localhost:8081/jaxrs_server_war_exploded/ws/userService/user").type(MediaType.APPLICATION_XML).post(user);
    } 
}
```

个人认为项目实际环境会更加复杂，该单元测试可用于创建接口并测试接口是否能接收数据时。