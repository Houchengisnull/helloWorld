[TOC]

- **官网**
- <a href='https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestmapping'>Spring Web MVC</a>

# Spring MVC

## 原理

# ViewResolver

`ViewResolver`是`Spring MVC`视图渲染核心机制；

`Spring MVC`里有一个接口叫`ViewResolver`，实现这个接口重写方法`resiveViewName()`，这个接口的返回值是接口`View`，而`View`的职责就是使用`model`、`request`、`response`对象，并将渲染的视图返回给浏览器。

``` java
@Configuration
@EnableWebMvc
@ComponentScan(“”)
Public class MyMvcConfig {
    
@Bean
Public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix(“/WEB-INF/classes/views/”);
		viewResolver.setSuffix(“.jsp”);
		viewResolver.setViewClass(JstlView.class);
		Return viewResolver;
	}
}
```

# WebApplicationInitializer接口

  是`Spring`提供用来配置`Servlet3.0 +`配置的接口，从而实现了替代`web.xml`的作用

  ``` java
public class WebInitializer implements WebApplicationInitializer {

    @Override
    Public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        Ctx.register(MyMvcConfig.class);
        Ctx.setServletContext(servletContext);
        Dynamic servlet = servletContext.addServlet(“dispatcherServlet”, new DispatcherServlet(ctx));
        Servlet.addMapping(“/”);
        Servlet.setLoadOnStartup(1);
    }

}
  ```

## @RestController

``` java
@RestController = @ResponseBody + @Controller
@Request(value = “/getjson”, produces=”application/json;charset=UTF-8”)
@Request(value = “/getxml”, produces=”application/xml;charset=UTF-8”)
```

## 静态资源映射

``` java
@Configuration
@EnableWebMvc
@ComponentScan(“”)
Public class MyMvcConfig extends WebMvcConfigurerAdapter{

@Bean
Public InternalResourceViewResolver viewResolver() {
	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	viewResolver.setPrefix(“/WEB-INF/classes/views/”);
	viewResolver.setSuffix(“.jsp”);
	viewResolver.setViewClass(JstlView.class);
	Return viewResolver;
}
```

``` java
@Override
Public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Registry.addResourceHandler("/static/**").addResourceLocations("classpath");
}
```

addResourceHandler 对外暴露路径

addResourceLocations 文件放置位置

EnableWebMvc开启SpringMVC支持 否则重写WebMvcConfigurerAdapter方法无效 

# Interceptor拦截器

# Advice

## ControllerAdvice

控制器建言。

将控制器全局配置放置于一处，例如对异常的处理@ControllerAdvice + @ExceptionHandler

## RequestBodyAdvice

在请求内容到达Controller之前，对请求内容进行处理。

## ResposeBodyAdvice

在响应内容返回前，对响应内容进行处理。

> 只要是通过Spring MVC实现的程序，如果需要对请求体或者响应体进行处理，比如加解密的场景，个人更建议使用建言而非通过过滤器结合RequestWrapper/ResponseWrapper。
>
> 因为后者的实现更加原生，往往会带来难以预料的情况。

# HttpMessageConverter

`HttpMessageConverter`用来处理`request`和`response`里的数据。

`Spring`为我们内置了大量的`HttpMessageConverte`：

`MappingJackson2HttpMessageConverter`

`StringHttpMessageConverter`

 

``` java
public class MyMessageConverter extends AbstractHttpMessageConverter<DemoObj>{

	public MyMessageConverter() {
		super(new MediaType("application","x-wisely",Charset.forName("UTF-8")));
	}

	// 该Converter支持类型
	@Override
	protected boolean supports(Class<?> clazz) {
		return DemoObj.class.isAssignableFrom(clazz);
	}

	// 处理请求数据
	@Override
	protected DemoObj readInternal(Class<? extends DemoObj> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	// 处理如何输出数据到response
	@Override
	protected void writeInternal(DemoObj t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
	}
}
```

# 服务器推送

## ajax轮询 

## 持有请求 

客户端发送请求给服务器，服务抓住请求不放当有数据方返回，返回后客户端再向服务器发送请求；

方式1基于sse(Server Send Event 服务端发送事件)

方式2基于Sevlet3.0+异步方法特性

## Websocket

# 注解

## Request Mapping

`MVC`中最基本的注解，`spring mvc`将根据`Http Request`中的`url`将其映射到匹配的`Controller`的方法上。

同时，它可以设置`method`、`request parameters`、`headers`和`media types`等属性进行匹配。

它包括以下几个扩展：

- **@GetMapping**
- **@PostMapping**
- **@PutMapping**
- **@DeleteMapping**
- **@PatchMapping**

以对应`RESTful`风格。

``` java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
* 实际就是设置了method为Get的RequestMapping注解
*/
@RequestMapping(
    method = {RequestMethod.GET}
)
public @interface GetMapping {
    @AliasFor(
        annotation = RequestMapping.class
    )
    String name() default "";

    @AliasFor(
        annotation = RequestMapping.class
    )
    String[] value() default {};

    @AliasFor(
        annotation = RequestMapping.class
    )
    String[] path() default {};

    @AliasFor(
        annotation = RequestMapping.class
    )
    String[] params() default {};

    @AliasFor(
        annotation = RequestMapping.class
    )
    String[] headers() default {};

    @AliasFor(
        annotation = RequestMapping.class
    )
    String[] consumes() default {};

    @AliasFor(
        annotation = RequestMapping.class
    )
    String[] produces() default {};
}
```

## Handler方法

### 修饰方法形参

#### @RequestParam

绑定`Request parameters`到`Controller`的方法参数上。

- 声明该注解的参数是默认情况下是必须的
- 如果参数类型不是`String`，`spring mvc`将自动映射
- 如果参数类型是`Map<String, String>`或者`MultiValueMap<String, String>`，且在注解中填写参数名称，那么这个对象将根据`Request parameter`中的参数名一一填充。

#### @RequestBody

绑定`Request Body`。

默认情况下，`Validation error`会导致`MethodArgumentNoValidException`，并返回`400`错误给客户端。

#### @HttpEntity

`HttpEntity`比起`@RequestBody`应用得更少，其包括请求头和请求体。

#### @RequestHeader

绑定`Request Header`到`Controller`的方法参数上。

``` java
@GetMapping("/demo")
public void handle(
	@RequestHeader("Accept-Encoding") String encoding
    , @RequestHeader("Keep-Alive") long keepAlive
) {
    // ...
}
```

### 方法返回值

#### ResponseBody

将方法的返回值放入Http响应的响应体(Response Body)中。

#### ResponseEntity

类似`@ResponseBody`，但是包含响应状态与响应头。

``` java
@GetMapping("/something")
public ResponseEntity<String> handle() {
    String body = "...";
    String etag = "...";
    return ResponseEntity.ok().eTag(etag).build(body);
}

/**
* 返回文件
*/
@PostMapping("/download")
public ResponseEntity<byte[]> download() {
    byte[] bytes = service.getFileByte();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentDispositionFormData("attachment", filename);
    headers.setContentLength(bytes.length);
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	return new ResponseEntity(bytes, headers, HttpStatus.OK);
}

/**
* 返回json
*/
@GetMapping("/getMessage")
public ResponseEntity<Map<String, Object>> query() {
    Map<String, Object> result = new HashMap();
    result.put("message", "查询成功");
    result.put("code", 0)
    return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
}

```

## @ExceptionHandler

## @ModelAttribute

@ModelAttribute 绑定键值对到Model，此处让全局的RequestMapping都能获得此处设置的键值

## @InitBinder

- **参考**

- [SpringMVC中利用@InitBinder来对页面数据进行解析绑定](https://www.cnblogs.com/heyonggang/p/6186633.html)

@InitBinder 用来设置WebDataBinder，WebDataBinder用来自动绑定前台请求参数到Model

在使用SpingMVC框架的项目中，经常会遇到页面某些数据类型是Date、Integer、Double等的数据要绑定到控制器的实体，或者控制器需要接受这些数据，如果这类数据类型不做处理的话将无法绑定。

这里我们可以使用注解@InitBinder来解决这些问题，这样SpingMVC在绑定表单之前，都会先注册这些编辑器。一般会将这些方法写在**BaseController**中，需要进行这类转换的控制器只需继承BaseController即可。其实Spring提供了很多的实现类，如CustomDateEditor、CustomBooleanEditor、CustomNumberEditor等，基本上是够用的。

# Model 接口

实例 `ExtendedModelMap`

# FAQ

## spring mvc限制list传递大小

- 缺省值 256

``` java
@InitBinder
public void initBinder() {
    // 将list限制大小修改为1000
    binder.setAutoGrowCollectionLimit(1000);
}
```

或通过转换成json字符串发送到后台，再通过jsonArray反序列化成集合对象以解决类似问题。

## Spring Boot集成jsp出现404

- [springboot整合jsp，页面全报404](https://blog.51cto.com/u_15964717/6057819)
