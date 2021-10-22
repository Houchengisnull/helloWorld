package org.hc.learning.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RabbitMQApplication /*extends SpringBootServletInitializer */{

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RabbitMQApplication.class);
        RabbitMQProducer producer = context.getBean(RabbitMQProducer.class);
        // to topic_queue_1
        producer.sendMsg("topic_exchange", "topic.exchange.hello.1", "from topic.exchange.hello.1:你好");
        producer.sendMsg("topic_exchange", "topic.exchange.goodbye.1", "from topic.exchange.goodbye.1:再见");
    }

}
