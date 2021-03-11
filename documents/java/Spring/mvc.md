[TOC]

- <a href='https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestmapping'>Spring Web MVC</a>

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

# Model 接口

实例 `ExtendedModelMap`

# spring mvc限制list传递大小

- 缺省值 256

``` java
@InitBinder
public void initBinder() {
    // 将list限制大小修改为1000
    binder.setAutoGrowCollectionLimit(1000);
}
```

或通过转换成json字符串发送到后台，再通过jsonArray反序列化成集合对象以解决类似问题。

