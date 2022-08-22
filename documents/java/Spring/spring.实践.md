[toc]

# Spring Bean自动执行

- **参考**
- [Spring Bean 方法自动触发的N种方式](https://www.codenong.com/j5dda22be51882573530/)

有时候我们需要在Bean初始化的之前、之后及销毁前做一些处理。

## 生命周期

首先，我们可以从Bean的生命周期的角度出发：

- 比如在Bean初始化之前，我们最简单的方式就是利用@Postconstruct注解。

- 如果需要在初始化之后，我们就需要利用Spring的`init-method`

  ``` java
  @Slf4j
  @Configuration
  //@PropertySource(value = "classpath:application-nacos.yml")
  public class NacosConfig  implements InitializingBean {
  
      /**
       *
       */
      @Value("${spring.application.name}")
      private String applicationName;
  
      @Value("${server.port}")
      private Integer port;
  
      @NacosInjected
      private NamingService namingService;
  
      /**
       * 注入后自动注册
       * @throws Exception
       */
      @Override
      public void afterPropertiesSet() throws Exception {
          String hostAddress = InetAddress.getLocalHost().getHostAddress();
          log.info("服务注册:[{}]{}:{}", applicationName, hostAddress, port);
          namingService.registerInstance(applicationName, hostAddress, port);
      }
  
  }
  ```

  让Bean实现`InitializingBean`，在初始化后将调用`afterPropertiesSet()`。

- 如果在销毁之前，则利用`DisposableBean`，在销毁前调用`destroy()`。

  这种方式有些不实用，因为需要显式销毁实例，比如：

  ``` java
  context.close();
  ```

  但实际开发场景，我们并不会这么做，而关闭进程也是利用`kill`命令。导致程序极少执行`destroy()`。

## ApplicationListener

- **参考**
- [ApplicationListener详解](https://www.cnblogs.com/javafucker/p/10276146.html)

ApplicationListener用于监听Spring事件。

- Spring内置事件

| 内置事件              | 描述                             |
| --------------------- | -------------------------------- |
| ContextRefreshedEvent | ApplicationContext初始化或刷新后 |
| ContextStartedEvent   | 调用Application接口的start()后   |
| ContextStoppedEvent   | 调用Application接口的stop()后    |
| ContextClosedEvent    | 调用Application接口的close()后   |
| RequestHandledEvent   | 在SpringMVC中处理用户请求结束后  |

我们可以监听`ContextRefreshedEvent`事件，在初始化后执行我们需要的操作。

