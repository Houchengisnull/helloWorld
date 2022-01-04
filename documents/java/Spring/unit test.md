[TOC]

# Standard Template

## Spring Boot

- 参考
- [Testing](https://docs.spring.io/spring-framework/docs/5.3.14/reference/html/testing.html#testing)

``` java
/**
 * 标准单元测试模板
 */

/**
 * 需要注册的Bean的路径
 */
@ComponentScan(basePackages = {}, basePackageClasses = {})
/**
 * 启动profile
 */
@ActiveProfiles({"dev"})
/**
 * 填写配置类
 */
@ContextConfiguration(classes = {})
@RunWith(SpringRunner.class) // <==> @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootStandardTemplateTest {



}
```

> `SpringRunner`是`SpringJunit4ClassRunner`的子类，但并没有重写任何方法，仅是为了更简洁的名称。

# MockMultipartFile

> Mock 仿制的，模拟的

小开发通过Class MockMultipartFile构造一个MultipartFile实例以作单元测试



springmvc中常用MultipartFile实现类为 `CommonsMultipartFile`

## 构造方法

- public MockMultipartFile(String, byte[])
- public MockMultipartFile(String, InputStream)
- public MockMultipartFile(String, String, String, byte[])
- public MockMultipartFile(String, String, String, InputStream)

## 参考

https://blog.csdn.net/Altoin/article/details/78275164