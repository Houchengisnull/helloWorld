package org.hc.learning.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer {

    @RabbitHandler
    @RabbitListener(queues = "topic_queue_1")
    public void handleTopicQueueAll(String message) {
        log.info("topic_queue_1收到消息: {}" , message);
    }

    /**
     * 增加一个监听测试
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = "topic_queue_1")
    public void handleTopicQueueAllOther(String message) {
        log.info("topic_queue_1(Other)收到消息: {}" , message);
    }

    @RabbitHandler
    @RabbitListener(queues = "topic_queue_hello")
    public void handleTopicQueueHello(String message) {
        log.info("topic_queue_hello收到消息: {}" , message);
    }

    @RabbitHandler
    @RabbitListener(queues = "topic_queue_goodbye")
    public void handleTopicQueueWorld(String message) {
        log.info("topic_queue_world收到消息: {}" , message);
    }

}
