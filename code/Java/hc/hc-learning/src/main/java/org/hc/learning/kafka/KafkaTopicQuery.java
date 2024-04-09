package org.hc.learning.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaTopicQuery {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Kafka 服务器地址和端口
        String bootstrapServers = "192.168.192.11:31694";

        // 配置 AdminClient
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // 创建 AdminClient
        try (AdminClient adminClient = AdminClient.create(props)) {
            // 使用 AdminClient 查询主题列表
            ListTopicsResult topicsResult = adminClient.listTopics(new ListTopicsOptions().listInternal(true));

            Set<String> topicNames = topicsResult.names().get();

            // 输出查询到的主题列表
            System.out.println("Kafka 主题列表：");
            for (String topicName : topicNames) {
                System.out.println(topicName);
            }
        }
    }
}
