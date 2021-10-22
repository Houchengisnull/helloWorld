package org.hc.learning.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate template;

    public void sendMsg(String exchange, String routingKey, String content) {
        template.convertAndSend(exchange, routingKey, content);
    }

}
