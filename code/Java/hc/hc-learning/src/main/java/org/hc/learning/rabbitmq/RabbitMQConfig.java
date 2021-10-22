package org.hc.learning.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {
    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private Queue rabbitQueueHello;

    @Autowired
    private Queue rabbitQueueGoodbye;

    @Autowired
    private Exchange exchange;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(Integer.valueOf("5672"));
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPublisherConfirms(true); // 消息确认, 见《rabbitmq.md》Publisher Confirms 消息确认
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(factory);
    }

    @Bean
    public Exchange rabbitExchange() {
        return new TopicExchange("topic_exchange", true, false);
    }

    @Bean
    public Queue rabbitQueueHello() {
        return new Queue("topic_queue_hello", true, false, false);
    }

    @Bean
    public Queue rabbitQueueGoodbye() {
        return new Queue("topic_queue_goodbye", true, false, false);
    }

    /**
     * 通过Bind可以自动绑定exchange与队列，在队列不存在时将创建队列
     * @return
     */
    @Bean
    public Binding bindingHello() {
        return BindingBuilder.bind(rabbitQueueHello).to(exchange).with("topic.exchange.hello.1").noargs();
    }

    @Bean
    public Binding bindingGoodbye() {
        return BindingBuilder.bind(rabbitQueueGoodbye).to(exchange).with("topic.exchange.goodbye.1").noargs();
    }

}
