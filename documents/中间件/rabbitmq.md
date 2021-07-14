[toc]

# 基本

- **参考**
- [RabbitMQ博客列表](https://www.jianshu.com/p/cd81afa8ade1)
- [RabbitMQ笔记一：消息队列和RabbitMQ及AMQP协议介绍](https://www.jianshu.com/p/6e6821604efc)
- **实现语言**	`Erlang`

## 用途

- 异步处理
- 系统解耦
- 流量削峰
- 广播
- 最终一致性

## 常用信息 

- **默认端口**	5672
- **管理端端口**	15672
- **管理端默认用户** guest
- **管理端默认密码** guest

# AMQP

应用层标准高级消息队列协议，`Advanced Message Queuing Protocol`。

## 背景

各个厂家使用的`MQ`使用不同的`api`、协议，如果应用需要使用不同的厂家的队列，那么将增加系统复杂性。为了解决这个问题，Java曾经试图使用`JMS(Java Message Service)`来屏蔽这些问题，类似`JDBC`。

但是消息中间件接口的复杂程度不得不引入新的消息通信标准化方案——`AMQP`。

## 基本概念

| 概念          | 意义                                                         |
| ------------- | ------------------------------------------------------------ |
| Server/Broker | 实现`AMQP`的服务器实体                                       |
| Connection    | 应用程序与Broker的网络连接                                   |
| Channel       | 信道，进行消息读写的通道，客户端可以建立多个Channel，每个Channel代表一个会话任务 |
| Message       | 消息                                                         |
| Exchange      | 交换机，用于接收消息。                                       |
| Binding       | Exchange与Queue之间的虚拟连接                                |
| Routing key   | 一个虚拟地址，个人理解为转发时所使用的映射关系中的key        |
| Queue         | 消息队列，用于缓存消息并转发给消费者                         |
| Virtual Host  | 类似权限控制组，一个Virtual Host中里面可以有若干个Exchange和Queue |

![img](../images/messagequeue/amqp)

> - 如何理解“路由”？
> - 2021-7-14 暂时理解为转发就好了。

# Publisher Confirms 消息确认

- **参考**
- [RabbitMQ笔记十五：消息确认之一（Publisher Confirms）](https://www.jianshu.com/p/0db95a3e972c)

在使用消息中间件时，存在丢失消息的情况，发送者不能确保是否发送成功，接收者接收失败也无法反馈。

``` mermaid
graph LR
	p(生产者)  -- publish --> broker(broker)
	broker -- consume --> c(消费者)

```

为了保证消息中间件的可靠性：

1. 事务

   利用`AMQP`的一部分，发送消息前将`channel`设置为`tx`模式，与数据库事务类似。但同样非常耗费消息中间件的性能。

2. 消息确认，`Publish confirms`

   ``` mermaid
   graph LR
   	p(生产者)  -- publish --> broker(broker)
   	broker -- consume --> c(消费者)
   	broker -- confirm --> p
   	c -- confirm --> broker
   	broker -- 数据落地 --> broker
   ```

   发送消息前将`channel`设置为`confirmSelect`模式。

- **Java**

  ``` java
  import com.rabbitmq.client.*;
  
  import java.io.IOException;
  import java.util.TreeSet;
  import java.util.concurrent.TimeUnit;
  
  /**
  * 以下代码纯属copy 未经过测试
  */
  public class RabbitMq {
      
      public static void main(String[] args) {
          ConnectionFactory connectionFactory = new ConnectionFactory();
          connectionFactory.setUri("amqp://guest:guest@127.0.0.1:5672");
          Connection connection = connectionFactory.newConnection();
          Channel channel = connection.createChannel();
  
          //是当前的channel处于确认模式
          channel.confirmSelect();
  
          //使当前的channel处于事务模式，与上面的使channel处于确认模式使互斥的
          //channel.txSelect();
          
          ...
      }
  		
  }
  ```

  

- **参考**

- [RabbitMQ笔记十五：消息确认之一（Publisher Confirms）](https://www.jianshu.com/p/0db95a3e972c)

# Java集成

## SpringBoot集成

- 通过`maven`集成

  需要引入相关依赖，例如：

  ``` xml
  <dependency>
  	<groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-amqp</artifactId>
      <version>1.5.2.RELEASE</version>
  </dependency>
  ```

- 配置类`RabbitConfig`

  ``` java
  @Configuration
  public class RabbitConfig {
      
      @Autowired
      private ConnectionFactory factory;
      
      @Autowired
      private Queue queue;
      
      private String routingKey = "routingKey";
      
      @Bean
      public ConnectionFactory rabbitFactory(){
          CachingConnectionFactory factory = new CachingConnectionFactory();
          factory.setHost("127.0.0.1");
          factory.setPort(5672);
          factory.setUsername("guest");
          factory.setPassword("guest");
          factory.setPublishConfirms(true); // 设置channel为confirmSelect模式
      }
      
      @Bean
      public RabbitTemplate rabbitTemplate() {
  		return new RabbitTemplate(factory);
      }
      
      @Bean 
      public Exchange rabbitExchange() {
          return TopicExchange("my_exchange");
      }
      
      @Bean
      public Queue rabbitQueue() {
          return new Queue("my_queue",true, false, false);
      }
      
      /**
      * 需要绑定后才能自动创建exchange、queue
      */
      @Bean
      public Binding binding() {
          return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
      }
      
  }
  ```

- **消费者**

  ``` java
  @Component
  @RabbitListener(queue = "queue")
  public class RabbitMQConsumer {
      
      @RabbitHandler
      public void receiveMessage(String message) {
          System.out.println("receive message:" + message);
      }
      
  }
  ```

- **生产者**

  ``` java
  @Component
  public class RabbitMQProducer {
      
      @Autowired
      RabbitTemplate template;
      
  	public void sendMessage(String content) {
          template.convertAndSent("my_exchange", "routingKey", content);
      }
      
  }
  ```

  