[toc]

# Kafka

Apache Kafka 是一个分布式流处理平台和消息队列系统，最初由 LinkedIn 公司开发，并于2011年开源。Kafka 旨在处理实时数据流，具有高吞吐量、低延迟、可扩展性和容错性等特点，已被广泛应用于各种场景，如日志收集、实时数据分析、事件驱动架构等。

## 特点

- **持久化：** Kafka 可以持久化大量数据，并确保数据不会丢失。
  
- **高伸缩性：** Kafka 集群可以轻松地水平扩展，以适应不断增长的数据量和流量。
  
- **高性能：** Kafka 具有优秀的性能表现，可以处理大规模的消息传输和处理任务。

## 性能影响的因素

Kafka 的性能受多种因素影响，包括磁盘吞吐量、内存和网络等。合理配置和优化这些因素可以提升 Kafka 的性能表现。

# 消费者

## 再分配

当group里的消费者发生变化时，或者分区发生变化时，会发生**再分配**现象。

**在此期间，消费者无法消费消息，会造成整个群组一小段时间不可用。**

消费者会向群组协调器的Broker发送心跳确保连接存活。

## 偏移量

消费者根据偏移量来决定从什么位置开始消费消息。偏移量的值保存在一个特殊的主题 `_consumer_offset` 中。消费者可以选择自动提交或手动提交偏移量。

### 提交

- **自动提交：** 在分区再分配之后可能导致数据重复消费。
  
- **手动提交：** 可以使用同步或异步方式手动提交偏移量，以确保偏移量的准确性。

## 再平衡监听器

为了更好地处理再分配现象，Kafka 提供了 ConsumerRebalanceListener 接口，可以在再分配前后执行一些逻辑操作，如提交偏移量等。

```java
// 示例代码
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import java.util.Collection;

public class MyConsumerRebalanceListener implements ConsumerRebalanceListener {

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        // 在重新分配分区之前执行的逻辑操作
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        // 在重新分配分区之后执行的逻辑操作
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