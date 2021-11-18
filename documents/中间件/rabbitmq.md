[toc]

# 基本

- **参考**
- [RabbitMQ博客列表](https://www.jianshu.com/p/cd81afa8ade1)
- [RabbitMQ笔记一：消息队列和RabbitMQ及AMQP协议介绍](https://www.jianshu.com/p/6e6821604efc)
- **实现语言**	`Erlang`

![img](../images/messagequeue/rabbitmq.arch.jpg)

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

# Exchange

- **参考**
- [RabbitMQ笔记三：四种类型Exchange](https://www.jianshu.com/p/04f443dcd8bd)

## 作用

服务器不会把消息直接塞进队列(`Queue`)，而是发送给交换机(`Exchange`)。再根据消息附带的规则，`Exchange`将会决定消息投递到哪个队列中。

- **routing key**

  消息附带的规则称为`路由键(routing key)`。

- **binding key**

  队列和交换机之间的绑定。

## `Exchange`类型有四种：

- **Direct Exchange**

  将消息中的`Routing key`与该`Exchange`关联的所有`Binding`中的`Routing key`匹配，如果**相等**则发送到该`Binding`对应的`Queue`。

- **Topic Exchange**

  将消息中的`Routing key`与该`Exchange`关联的所有`Binding`中的`Routing key`匹配，如果**匹配**则发送到该`Binding`对应的`Queue`。

- **Fanout Exchange**

  忽略`Routing key`， 直接将消息转发给所有`binding`的队列中。

- **Headers Exchange**

  将消息中的`headers`与该`Exchange`相关联的所有`Binding`中的**参数**进行匹配，如果**匹配**则发送到绑定的队列中。

## Routing Key匹配规则

- **参考**

  [通配符模式](https://www.cnblogs.com/laosunlaiye/p/11671519.html)

- **\***	匹配一个单词
- **#**	匹配任意字符
- **\*,#**	只能写在`.`左右，且不能挨着字符

尽管没有阅读过官网文档，但是直觉告诉我以下描述为`Binding Key`更为准确。

首先因为消费者消费消息只需要指定队列即可，其次`Binding Key`用于指明消息从`Exchanger`到`Queue`之间的链路，所以在很多文章中都会说明——通配符只针对消费者。反正队列里的数据都是消费者消费的，那么笼统地将队列看成整个消费者也没什么关系。

> 需要注意的是: 通配符只针对~~消费者~~**Binding Key**。

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

  