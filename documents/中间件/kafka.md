[toc]

# Kafka

## 特点

- 持久化
- 高伸缩性
- 高性能

## 性能影响的因素

- 磁盘吞吐量
- 内存
- 网络

# 消费者

## 再分配

当group里的消费者发生变化时，或者分区发生变化时，会发生**再分配**现象。

**在此期间，消费者无法消费消息，会造成整个群组一小段时间不可用。**

消费者会向群组协调器的Broker发送心跳确保连接存活。

## 偏移量

消费者根据**偏移量**来决定从什么位置开始消费。偏移量的值保存在一个`_consumer_offset`的特殊主题中。

### 提交

- **自动提交**

  在分区再分配之后，可能导致数据重复消费。

- **手动提交**

  使用`commitSync()`提交最新poll的偏移量。

- **手动提交(Async)**

  使用`commitAsync()`异步提交最新poll的偏移量。

<hr>

以上情况或多或少都有一些缺陷，无法保证发生再平衡时数据不会重新消费，所以Kafka提供ConsumerRebalancelistener[^1]。

[1]: 再平衡监听器

``` java 
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import java.util.Collection;

public class MyConsumerRebalanceListener implements ConsumerRebalanceListener {

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        // 在重新分配分区之前，消费者将要丧失这些分区的所有权
        System.out.println("Lost partitions in rebalance. Committing current offsets: " + partitions);
        // 在这里添加提交当前偏移量的逻辑
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        // 消费者重新分配到了这些分区的所有权
        System.out.println("Assigned partitions in rebalance: " + partitions);
        // 在这里添加消费者重新分配到分区后的处理逻辑
    }
}

// 在消费者代码中使用 ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Collections;
import java.util.Properties;

public class MyConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("my-topic"), new MyConsumerRebalanceListener());

        try {
            while (true) {
                // 消费消息的逻辑
            }
        } finally {
            consumer.close();
        }
    }
}

```

# Kafka集群

## 成员关系

Kafka使用zookeeper来维护成员关系。

每个Broker都有一个唯一标识符。在启动时，Broker通过创建临时节点将唯一标识符注册到zookeeper上。

Kafka组件订阅Zookeeper的/brokers/ids路径，当有broker加入或退出集群时，这些组件就可以获得通知。

## 控制器

集群之中第一个在Zookeeper中注册/controller成功的Broker，就是控制器。控制器负责分区首领的选举。

其他的Broker在控制器节点上创建Zookeeperwatch对象，监听控制器的变更通知。

## 复制

复制架构是Kafka的核心。复制保证在个别节点失效时，Kafka仍然高可用和持久性。

- **什么是复制**

  Kafka使用主题来组织数据，每个主题被分为若干个分区，每个分区有多个副本。那些副本被保存在Broker上，每个Broker可以保存成百上千个各个主题和分区的副本。

### 副本类型

- **首领副本**

  所有生产者和消费者的请求都会经过这个副本，保证了一致性。

- **跟随者副本**

  除了首领都是跟随者，跟随者只是从首领那里复制消息，与首领保持一致。只是为了接替首领。

- **优先副本**

  除了首领副本外，每个分区都有一个优先副本。创建主题时，选定的首领分区就是分区的优先副本。

  

