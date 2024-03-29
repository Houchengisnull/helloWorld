[toc]

# Spring Boot自动装配

- **装配过程**
  1. 在@EnableAutoConfiguration中，用@Import注解导入了一个AutoConfigurationImportSelector.class。
  2. 这个Selector将收集所有的META-INF/spring.factories文件。
  3. 收集完成的自动装配信息将返回给ConfigurationClassPostProcessor。
  4. 由ConfigurationClassPostProcessor注入Ioc容器。

- 时序图

  ``` sequence
  participant SpringApplication
  participant Context
  participant Delegate
  participant Processor
  participant Parser
  participant Selector
  participant spring.factories
  participant Reader
  
  SpringApplication - Context :applicationContext.refresh()
  Context - Delegate: ...
  Delegate - Processor: ...
  Processor - Processor:postProcessBeanDefinitionRegistry(registry)
  Processor - Parser:parser.parse(candidates)
  Parser - Selector : getCandidateConfigurations()
  Selector - spring.factories : 收集配置信息
  Selector -- Parser : 配置信息
  Parser -- Processor : 配置信息
  Processor - Reader : reader.loadBeanDefinitions(configClasses)
  Reader - Reader : 加载Bean Definitions
  
  ```

  以上就是大概的时序图，简化了类名。
  
  如果客官感兴趣想要自己debug的话，我建议只要关注两个地方：
  
  1. ConfigurationClassPostProcessor[^1]的postProcessBeanDefinitionRegistry()
  2. AutoConfigurationImportSelector的getCandidateConfigurations()
  
  > 可能会有人回答得更全面，但我花一天时间读完之后确实觉得没有意义（除了面试）。
  >
  > 那些花时间去看的人大概是真的没有女朋友吧(好吧，我也没有)。
  >
  > 至于里面用了哪些子类、哪些设计模式，在一个小时之后我又忘了。

[^1]: 后置处理器
