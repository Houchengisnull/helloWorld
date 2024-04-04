[toc]

# 消费延迟

查看Kafka的Topic在各个Partition的分配情况

``` shell
./kafka-consumer-groups --bootstrap-server localhost:9092 --group ******** --describe
```

观察返回结果的LAG[^1]。



[^1]:滞后