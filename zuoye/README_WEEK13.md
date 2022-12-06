# Java进阶训练营实战 作业

# 第13周作业

## 作业题目
1.（必做）搭建一个 3 节点 Kafka 集群，测试功能和性能；实现 spring kafka 下对 kafka 集群的操作，将代码提交到 github。

6.（必做）思考和设计自定义 MQ 第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码，提交到 GitHub。

## 第1题  搭建一个 3 节点 Kafka 集群，测试功能和性能；实现 spring kafka 下对 kafka 集群的操作，


1. kafka 安装
- 下载地址：http://kafka.apache.org/downloads  
- 下载2.6.0或者2.7.0，解压。 前面是下载的包前面是scala版本，后面是kafka版本。
- 环境：window10

2. 启动安装

配置，新建三个日志目录：C:/tmp/kafka-logs1,C:/tmp/kafka-logs2,C:/tmp/kafka-logs3. 新建三个配置文件，server9001.properties， server9002.properties， server9003.properties

~~~
# 集群需要变更的配置
# broker.id分别为1，2，3
# log.dirs分别为C:/tmp/kafka-logs1， log.dirs=C:/tmp/kafka-logs2， log.dirs=C:/tmp/kafka-logs3
# listeners=PLAINTEXT://:9003 分别为 listeners=PLAINTEXT://:9001， listeners=PLAINTEXT://:9002， listeners=PLAINTEXT://:9003
broker.id=1
num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600
log.dirs=C:/tmp/kafka-logs1
num.partitions=1
num.recovery.threads.per.data.dir=1
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000
zookeeper.connection.timeout.ms=18000
delete.topic.enable=true
group.initial.rebalance.delay.ms=0
message.max.bytes=5000000
replica.fetch.max.bytes=5000000
listeners=PLAINTEXT://:9001
broker.list=localhost:9001,localhost:9002,localhost:9003
zookeeper.connect=localhost:2181
~~~

启动：

~~~
# 启动zookeeper
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

# 启动kafka集群
bin\windows\kafka-server-start.bat config\server9001.properties
bin\windows\kafka-server-start.bat config\server9002.properties
bin\windows\kafka-server-start.bat config\server9003.properties
~~~

集群启动结果：

```bat
[2021-07-31 23:13:20,678] INFO [SocketServer brokerId=1] Started socket server acceptors and processors (kafka.network.SocketServer)
[2021-07-31 23:13:20,695] INFO Kafka version: 2.7.1 (org.apache.kafka.common.utils.AppInfoParser)
[2021-07-31 23:13:20,695] INFO Kafka commitId: 61dbce85d0d41457 (org.apache.kafka.common.utils.AppInfoParser)
[2021-07-31 23:13:20,703] INFO Kafka startTimeMs: 1627744400681 (org.apache.kafka.common.utils.AppInfoParser)
[2021-07-31 23:13:20,713] INFO [KafkaServer id=1] started (kafka.server.KafkaServer)
log4j:ERROR Failed to rename [C:\soft\kafka_2.12-2.7.1/logs/state-change.log] to [C:\soft\kafka_2.12-2.7.1/logs/state-change.log.2021-07-31-17].
[2021-07-31 23:13:20,834] INFO [broker-1-to-controller-send-thread]: Recorded new controller, from now on will use broker 0 (kafka.server.BrokerToControllerRequestThread)
[2021-07-31 23:19:31,262] INFO [ReplicaFetcherManager on broker 1] Removed fetcher for partitions Set(test32-1) (kafka.server.ReplicaFetcherManager)
[2021-07-31 23:24:41,763] INFO [Log partition=test32-1, dir=C:\tmp\kafka-logs1] Loading producer state till offset 0 with message format version 2 (kafka.log.Log)
[2021-07-31 23:24:41,784] INFO Created log for partition test32-1 in C:\tmp\kafka-logs1\test32-1 with properties {compression.type -> producer, message.downconversion.enable -> true, min.insync.replicas -> 1, segment.jitter.ms -> 0, cleanup.policy -> [delete], flush.ms -> 9223372036854775807, segment.bytes -> 1073741824, retention.ms -> 604800000, flush.messages -> 9223372036854775807, message.format.version -> 2.7-IV2, file.delete.delay.ms -> 60000, max.compaction.lag.ms -> 9223372036854775807, max.message.bytes -> 5000000, min.compaction.lag.ms -> 0, message.timestamp.type -> CreateTime, preallocate -> false, min.cleanable.dirty.ratio -> 0.5, index.interval.bytes -> 4096, unclean.leader.election.enable -> false, retention.bytes -> -1, delete.retention.ms -> 86400000, segment.ms -> 604800000, message.timestamp.difference.max.ms -> 9223372036854775807, segment.index.bytes -> 10485760}. (kafka.log.LogManager)
[2021-07-31 23:24:41,786] INFO [Partition test32-1 broker=1] No checkpointed highwatermark is found for partition test32-1 (kafka.cluster.Partition)
[2021-07-31 23:24:41,788] INFO [Partition test32-1 broker=1] Log loaded for partition test32-1 with initial high watermark 0 (kafka.cluster.Partition)
```

3. 执行操作测试

~~~
#创建带有副本的 topic：
bin\windows\kafka-topics.bat --zookeeper localhost:2181 --create --topic test32 --partitions 3 --replication-factor 2
bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9003 --topic test32
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9001 --topic test32 --from-beginning

#执行生产性能测试（有可能下面的生产在window cmd里面执行不了，可以放到git bash里面执行）：
bin\windows\kafka-producer-perf-test.bat --topic test32 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9002
#执行消费性能测试：
bin\windows\kafka-consumer-perf-test.bat --bootstrap-server localhost:9002 --topic test32 --fetch-size 1048576 --messages 100000 --threads 1
~~~

测试结果：
~~~
# 创建topic结果：
C:\soft\kafka_2.12-2.7.1>bin\windows\kafka-topics.bat  --zookeeper localhost:2181 --create --topic test32 --partitions 3 --replication-factor 1
Created topic test32.

# 生产消息性能测试结果
C:\soft\kafka_2.12-2.7.1>bin\windows\kafka-producer-perf-test.bat --topic test32 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9002
10002 records sent, 1999.6 records/sec (1.91 MB/sec), 17.9 ms avg latency, 549.0 ms max latency.
10006 records sent, 2000.8 records/sec (1.91 MB/sec), 54.7 ms avg latency, 538.0 ms max latency.
10012 records sent, 2002.4 records/sec (1.91 MB/sec), 0.9 ms avg latency, 14.0 ms max latency.
10006 records sent, 2001.2 records/sec (1.91 MB/sec), 0.8 ms avg latency, 13.0 ms max latency.
10008 records sent, 2000.8 records/sec (1.91 MB/sec), 1.4 ms avg latency, 99.0 ms max latency.
10004 records sent, 2000.8 records/sec (1.91 MB/sec), 1.1 ms avg latency, 22.0 ms max latency.
9993 records sent, 1996.6 records/sec (1.90 MB/sec), 1.4 ms avg latency, 56.0 ms max latency.
10048 records sent, 2009.2 records/sec (1.92 MB/sec), 1.8 ms avg latency, 58.0 ms max latency.
10111 records sent, 2020.6 records/sec (1.93 MB/sec), 6.1 ms avg latency, 461.0 ms max latency.
100000 records sent, 1999.200320 records/sec (1.91 MB/sec), 9.32 ms avg latency, 549.00 ms max latency, 1 ms 50th, 28 ms 95th, 286 ms 99th, 384 ms 99.9th

# 消费消息性能测试结果
C:\soft\kafka_2.12-2.7.1>bin\windows\kafka-consumer-perf-test.bat --bootstrap-server localhost:9002 --topic test32 --fetch-size 1048576 --messages 100000 --threads 1
WARNING: option [threads] and [num-fetch-threads] have been deprecated and will be ignored by the test
start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec, rebalance.time.ms, fetch.time.ms, fetch.MB.sec, fetch.nMsg.sec
2021-07-31 23:57:55:789, 2021-07-31 23:57:59:181, 95.4772, 28.1478, 100123, 29517.3939, 1627747076351, -1627747072959, -0.0000, -0.0001
~~~

【生产消息输出解释】： 成功消费了 100000 条消息，吞吐量为 1999.200320  条/秒 (或 3.34 MB/秒)，平均时延为 9.32 ms，最大时延为 549.00 ms，50 % 的消息延时在 1 ms 内，95 % 的消息延时在 28 ms 内，99 % 的消息延时在 384 毫秒内。


## 第6题  思考和设计自定义 MQ 第二个版本或第三个版本，写代码实现其中至少一个功能点


https://github.com/nydia/JavaAdvanced/tree/main/09mq/kmq-core-v2