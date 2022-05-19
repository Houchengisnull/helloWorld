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

- 参考
- [Jersey是一个什么框架,价值在哪里？](https://www.jianshu.com/p/a9b0ebd4c3fe)

<hr>
`Jersey`是对`JAX-RS(JSR-311)`协议的实现，用于构建`RESTful Web Service`，也可以将`Jersey`理解为一个`RESTful`或者是`Web Service`框架。

> 需要注意：Jersey并不是实现RPC中`Web Service`的框架，而是实现Web服务的工具，不要弄混淆，与之更相似的是`Spring MVC`。

作为`Java开发者`，可能会觉得`Jersey`与我们常见的`MVC`框架很相似，比如`Spring mvc`。但它与`MVC`是有区别的，以`Spring mvc`为例：

- **Jersey与Spring MVC的区别**
- `Jersey`提供`DI`，由`glassfish hk2`实现，即想单独使用`jersey`需要另外学习`Bean容器`
- `Jersey`出发点是`RESTFull`，而`MVC`的出发点是`Web`，体现在接口设计方面
- `Jersey`提倡一种子资源的概念，即`RESTFull`提倡所有`url`都是资源
- `Jersey`直接提供`application.wadl`资源描绘`url`
- `Jersey`没有提供`Session`等状态管理，源于`RESTFull`设计无状态化
- `Response`方法支持更好返回结果，更加便利地返回`Status`
- 更加便利地访问`RESTFull`

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

# RestEasy

`RestEasy`同样是一个以`jaxrs-api.jar`为基础、实现`JAX-RS`标准的`RESTFull`框架，是`JBoss`的开源项目之一。

## 文件上传

最近实现一个文件上传功能，项目使用`RestEasy`实现。

一开始没注意用的是`RestEasy`，以`Spring mvc`的方式实现总是出错，还以为是没有配置合适的`Resolver`。

最后发现项目使用了`RestEasy`。

``` java
/**
* @timestamp 时间戳
* @input 
*/
@POST
@Path("/upload")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public ResultBean upload(@QueryParam("timestamp") String timestamp, MultipartFormDataInput input) {
    Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
	for(String key : uploadForm) {
        List<InputPart> inputPart = uploadForm.get(key);
        for (InputPart input : inputParts) {
            String body = inputPart.getBodyAsString();
            System.out.println(key + ":" + bodyAsString);
        }
    }
	String code = service.addByTxt(uploadForm);
    return new ResultBean(code);
}
```

可能大家会觉得使用`MultipartFormDataInput`来接收请求不是很方便——无法将`request`与`bean`一一映射。本着精益求精地想法，研究一会儿后搞定。

``` java
/**
* @timestamp 时间戳
* @input 
*/
@POST
@Path("/upload")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public ResultBean upload(@QueryParam("timestamp") String timestamp, @MultipartForm Form form) {
	String code = service.addByTxt(form);
    return new ResultBean(code);
}
```

- **Form**

``` java
@Iombok
public class Form {
    
    @FormParam("file")
    private byte[] file;
    
    /**
    * 文件名称
    */
    @FormParam("name")
    private String name;
    
    /**
    * 公司名称
    */
    @FormParam("company")
    private String company;
    
    /**
    * 公司规模
    */
    private Integer amount;
}
```

需要注意的是，使用`Integer`时，`RestEasy`无法很好地处理空字符串或者`Null`的情况，这里会出现一个异常。就不上源码了，领会精神。

总体而言，个人觉得`Spring mvc`用起来更加得心应手。