[toc]

# springboot集成nacos

- 引入依赖

``` xml
<!-- nacos 配置管理 -->
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>nacos-config-spring-boot-starter</artifactId>
    <version>0.2.7</version>
</dependency>

<!-- nacos 服务发现 -->
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>nacos-discovery-spring-boot-starter</artifactId>
    <version>0.2.7</version>
</dependency>
```

- 配置application-nacos.yml

```yml
nacos:
  config:
    server-addr: 127.0.0.1:8848
  discovery:
    server-addr: 127.0.0.1:8848
```

在单机启动的情况下，只要以上两个配置即可实现nacos两大核心功能:`配置管理`与`服务发现`。

在实际生产环境会更多，比如我目前并没有开启nacos的鉴权功能，所以不需要配置用户名与密码。这在实际生产环境上是不可能的。

## 服务注册

- 手动注册

  ```java
  namingService.registerInstance(applicationName, hostAddress, port);
  ```

- 自动注册

  只需要配置即可

  ``` yaml
  nacos:
    discovery:
    	# nacos地址
      server-addr: 127.0.0.1:8848
      # 是否自动注册
      auto-register: true
      # 进程信息
      register:
        ip: 192.168.192.2
        port: 8090
        service-name: ${spring.application.name}
  ```

在进程退出后，nacos服务列表将移除对应服务。

如果服务使用了nacos上的配置信息，比如${server.port}，那么容器启动时则选择使用该端口，这时如果本地配置中端口不同，则会以本地配置中的端口信息进行注册。

若此时服务中实现了自动注册，且读取了nacos上的配置信息，同样生效。

例如下图中两个实例实际是同一个服务：

![image-20220821213013767](https://raw.githubusercontent.com/Houchengisnull/helloworld/master/documents/images/nacos-discovery-auto_register-1.png)

## 配置管理

### 属性

| 属性名称     | 作用                                                         |
| ------------ | ------------------------------------------------------------ |
| auto-refresh | 自动刷新，当nacos配置信息发生变更时，服务将监听并拉取新的配置信息 |



## 注解

### @NacosPropertySource

``` java
@NacosPropertySource(dataId = "hc-web", autoRefreshed = true)
```

### @NacosValue

相比较于@Value，它可以设置是否自动刷新：

``` java
    @Value("${hc-web.username}")
    private String username;

    @NacosValue(value = "${hc-web.username}", autoRefreshed = false)
    private String nacosUsername;

    @NacosValue(value = "${hc-web.username}", autoRefreshed = true)
    private String autoRefreshedNacosUsername;
```

当`autoRefreshed=true`时，会跟随配置中心的内容改变而改变。

此处的autoRefreshed是存在优先级的，如果在配置类或者配置文件中`auto-refresh: false`，那么该字段同样不会更新

### @NacosInjected

``` java
    @NacosInjected
    private NamingService namingService;
```

使用`@Autowired`无法将`NamingService`注入成功。

## @NacosConfigurationProperties

类似@ConfigurationProperties，但是允许设置自动刷新、dataId等属性。好比@Value与@NacosValue。