[toc]

# 简介

RabbitMQ是一个流行的开源消息代理和队列服务器，用于在分布式系统中协调和管理消息传递。它实现了高级消息队列协议（AMQP），并且支持多种消息协议和插件扩展，如MQTT, STOMP等。RabbitMQ是用Erlang语言编写的，因此继承了Erlang的高并发、高可用性和容错特性。

# Channel

Channel是生产者/消费者与RabbitMQ通信的渠道。

信道是建立在TCP连接上虚拟连接，即RabbitMQ可以在一条TCP连接上建立多个Channel交给多线程来处理。

> 从技术上将，这被称为·**多路复用**，有效减少操作系统的性能开销。

# Exchange

服务器不会把消息直接塞进队列(`Queue`)，而是发送给交换机(`Exchange`)。再根据消息附带的规则，`Exchange`将会决定消息投递到哪个队列中。

- **routing key**

  消息附带的规则称为`路由键(routing key)`。

- **binding key**

  队列和交换机之间的绑定。

## Type

- **Direct Exchange**

  将消息中的`Routing key`与该`Exchange`关联的所有`Binding`中的`Routing key`匹配，如果**相等**则发送到该`Binding`对应的`Queue`。

- **Topic Exchange**

  将消息中的`Routing key`与该`Exchange`关联的所有`Binding`中的`Routing key`匹配，如果**匹配**则发送到该`Binding`对应的`Queue`。

- **Fanout Exchange**

  忽略`Routing key`， 直接将消息转发给所有`binding`的队列中。

- **Headers Exchange**

  将消息中的`headers`与该`Exchange`相关联的所有`Binding`中的**参数**进行匹配，如果**匹配**则发送到绑定的队列中。

## Binding Key

### Match Rule

- **\***	匹配一个单词
- **#**	匹配任意字符
- **\*,#**	只能写在`.`左右，且不能挨着字符

# Virtual Host

虚拟主机(virtual host)是一个逻辑分组，t同一台RabbitMQ服务器上可以有不同的vhost。

而各个vhost下的user、exchange、topic、queue互不干扰。

vhost之间的**资源隔离**可以满足以下场景：

- 权限管理
- 环境分离[^1]
- 多租户环境

[^1]: 可以使用虚拟主机区分不同的环境：开发环境、测试环境、生产环境。

# Publish

## Transaction

在发生消息时，需要设置channel支持事务。

- channel.txSelect()		开启事务
- channel.txCommit()	提交事务
- channel.txRollback()	回滚事务

在开启事务的channel与RabbitMQ交互时，任意环节发生问题则会抛出IOException，这样用户可以捕获异常进行事务回滚。

# Consume

## ack

在RabbitMQ中，每条消息需要消费者确认。

消费者声明队列时，设置autoAck参数：

当autoAck=false，需要消费者显式确认，RabbitMQ在收到消费者返回的basicAck后，才会移除消息。

否则，该消息在消费者断开连接后，重新返回队列。

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
  

## reject

消费者在应答时，可以将消息设置为reject，表明拒绝该消息。可能时非法的消息，或者是该消息自己暂时无法处理，于是reject。

在reject的同时，结合requeue参数，确定这条消息是否需要重回队列，否则丢弃。

- nack

  nack与reject类似，但是它可以一次性拒绝多条消息。

## Qos预取模式

Qos预取模式是消费者先拉取一定数量的消息，然后批量确认。

Qos模式需要autoAck=false，然后设置baseQos的值。

# Queue

## 死信队列

RabbitMQ提出了**死信队列**的概念。

死信队列主要保存三种消息：

- reject & requeue=false的消息
- 过期消息[^2]
- 队列达到最大长度

[^2]: 默认情况下消息不会过期，但是可以给队列设置过期时间和消息的过期时间

死信队列的exchange同样是个普通的exchange，在创建队列的时候，声明该队列是死信队列即可。

### 和备用exchange的区别

- 备用exchange是在原exchange不可用时作用
- 备用exchange是在原exchange创建时声明，死信exchange是在创建队列时声明

## 临时队列

临时队列即没有持久化的队列，在RabbitMQ重启后，队列不存在。

## 自动删除队列

在声明队列时，设置auto-delete=true，那么在最后一个消费者断开连接后，该队列自动删除。

## exclusive

在声明队列时，设置exclusive=true，那么只有一个消费者可以消费该队列的消息。

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

  