# Java进阶训练营实战 作业

# 第12周作业

## 作业题目
1.（必做）配置 redis 的主从复制，sentinel 高可用，Cluster 集群。

6.（必做）搭建 ActiveMQ 服务，基于 JMS，写代码分别实现对于 queue 和 topic 的消息生产和消费，代码提交到 github。

## 第1题  配置 redis 的主从复制，sentinel 高可用，Cluster 集群

### env : docker, redis6.2.5

1. redis tar -zxvf
2. make prefix=/opt/softs/redis install
3. redis建立的日志根目录logs, 存储stores, 进程根目录pid（每个redis启动之后会生成一个pid文件，用于锁住进程）

### redis主从

1. 配置
- 主要配置参数：dir, pidfile, port, replicaof
-	stores根目录下面建立：redis1 redis2

2. 启动：
- redis-server /opt/softs/redis/conf/redis-6379.conf
- redis-server /opt/softs/redis/conf/redis-6380.conf


### sentinel哨兵

1. 配置：
- stores根目录下面建立：sentinel0 sentinel1

2. sentinel cluster start:
	1. start master and slaveof:
	- redis-server /opt/softs/redis/conf/redis-6379.conf
	- redis-server /opt/softs/redis/conf/redis-6380.conf
	2. start sentinel:
	- redis-server /opt/softs/redis/conf/sentinel0.conf --sentinel
	- redis-server /opt/softs/redis/conf/sentinel1.conf --sentinel

### cluster分片集群
1. 配置
- stores根目录下面建立：cluster7000 cluster7001  cluster7002  cluster7100 cluster7101 cluster7102

2. 搭建Redis Cluster主要步骤：
	1.配置开启节点
		- 一个docker环境里面6个节点，6个端口：7000，7001，7002，7100，7101，7102。其中7000，7001，7002为主节点，7100，7101，7102为从节点。7000通过meet分别与7001，7002建立通信。7100通过meet方式分别与7101，7102建立通信。 7100为7000的从节点，7101为7001的从节点，7102为7002的从节点。
	2.meet
	3.指派槽
	4.主从关系分配

3. 配置 cluster-master7000.conf, 其他类似
~~~
port 7000
daemonize yes, 其他类似
dir '/opt/softs/redis/stores/cluster7000/'
logfile '/opt/softs/redis/logs/redis_7000.log'
dbfilename 'redis_7000.data'
cluster-enabled yes
cluster-config-file nodes-7000.conf
cluster-require-full-coverage no
~~~
3. 启动：
```sh
redis-server /opt/softs/redis/conf/cluster-master7000.conf
redis-server /opt/softs/redis/conf/cluster-master7001.conf
redis-server /opt/softs/redis/conf/cluster-master7002.conf
redis-server /opt/softs/redis/conf/cluster-salveof7100.conf
redis-server /opt/softs/redis/conf/cluster-salveof7101.conf
redis-server /opt/softs/redis/conf/cluster-salveof7102.conf
```
4. 用meet把节点加入到集群
```sh
redis-cli -p 7000 culster meet 127.0.0.1 7001
redis-cli -p 7000 culster meet 127.0.0.1 7002
redis-cli -p 7000 culster meet 127.0.0.1 7100
redis-cli -p 7000 culster meet 127.0.0.1 7101
redis-cli -p 7000 culster meet 127.0.0.1 7102
```
5. 设置槽点，下面的槽位可以同时设置
- 用脚本设置槽点，脚本如下：
```sh
#!/bin/bash
start=$1
end=$2
port=$3
for slot in `seq ${start} ${end}`
do
	echo "slot:${slot}"
	redis-cli -p ${port} cluster addslots ${slot}
done
```
- 服务器上执行下面脚本：
```sh
sh add_slots.sh 0 5461 7000        # 运行add_slots.sh脚本，把0到5461号槽分配给127.0.0.1:7000的redis server节点
sh add_slots.sh 5462 10922 7001    # 运行add_slots.sh脚本，把5462号到10922号槽分配给127.0.0.1:7001端口运行的redis server
sh add_slots.sh 10923 16383 7002   # 运行add_slots.sh脚本，把10923号到16383号槽分配给127.0.0.1:7002端口运行的redis server			
```
6. 设置主从
```sh
redis-cli -p 7100 cluster replicate f8536ac3337bd12971629f42699294ef44043845 # 7100作为7000的从节点
redis-cli -p 7101 cluster replicate bc58cc1e08330bea2d7f66d96031c505437aa3d2 # 7101作为7001的从节点
redis-cli -p 7102 cluster replicate c51042f43bc75cee64de2d875aef0df708414c1a # 7102作为7002的从节点
```
7. 向集群写入数据

```sh
[root@e90dec803b8c conf]# redis-cli -c -p 7000
127.0.0.1:7000> set s 1
OK
127.0.0.1:7000> get s
"1"
127.0.0.1:7000>
```
8. 集群扩容：增加主节点
```sh
[root@e90dec803b8c conf]# sed 's/7000/7003/g' cluster-master7000.conf > cluster-master7003.conf  #  复制节点配置
[root@e90dec803b8c conf]# sed 's/7100/7103/g' cluster-salveof7100.conf > cluster-salveof7103.conf # 复制节点配置
[root@e90dec803b8c conf]# mkdir ../stores/cluster7003 ../stores/cluster7103
[root@e90dec803b8c conf]# redis-server /opt/softs/redis/conf/cluster-master7003.conf
[root@e90dec803b8c conf]# redis-server /opt/softs/redis/conf/cluster-salveof7103.conf
[root@e90dec803b8c conf]# redis-cli -p 7000 cluster meet 127.0.0.1 7003
OK
[root@e90dec803b8c conf]# redis-cli -p 7100 cluster meet 127.0.0.1 7103
OK
[root@e90dec803b8c conf]# redis-cli -p 7000 cluster nodes
f8536ac3337bd12971629f42699294ef44043845 127.0.0.1:7000@17000 myself,master - 0 1627209427000 0 connected 0-5461
d18f179bffdde5ab45ae6e0b1df8b02f4d40b3fa 127.0.0.1:7101@17101 slave bc58cc1e08330bea2d7f66d96031c505437aa3d2 0 1627209428563 1 connected
f53b4805df7fcda6c4a6e0788b6544cce3ad7485 127.0.0.1:7100@17100 slave f8536ac3337bd12971629f42699294ef44043845 0 1627209428000 0 connected
8b6d7887f9ef004dd17f9b12b41310d3c8ef8d5f 127.0.0.1:7103@17103 master - 0 1627209426000 7 connected
c51042f43bc75cee64de2d875aef0df708414c1a 127.0.0.1:7002@17002 master - 0 1627209429590 2 connected 10923-16383
bc58cc1e08330bea2d7f66d96031c505437aa3d2 127.0.0.1:7001@17001 master - 0 1627209427000 1 connected 5462-10922
977cd2b865e9292496d6bf75fd15300762849853 127.0.0.1:7003@17003 master - 0 1627209428000 6 connected
c743425da73f319ac8a5868ce6fbbe2b2a822a6d 127.0.0.1:7102@17102 slave c51042f43bc75cee64de2d875aef0df708414c1a 0 1627209425500 2 connected
[root@e90dec803b8c conf]# redis-cli -p 7000 cluster info
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:8
cluster_size:3
cluster_current_epoch:7
cluster_my_epoch:0
cluster_stats_messages_ping_sent:21125
cluster_stats_messages_pong_sent:21134
cluster_stats_messages_meet_sent:6
cluster_stats_messages_sent:42265
cluster_stats_messages_ping_received:21134
cluster_stats_messages_pong_received:21131
cluster_stats_messages_received:42265
[root@e90dec803b8c conf]# redis-cli -p 7103 cluster relicate 977cd2b865e9292496d6bf75fd15300762849853
(error) ERR Unknown subcommand or wrong number of arguments for 'relicate'. Try CLUSTER HELP.
[root@e90dec803b8c conf]# redis-cli -p 7103 cluster replicate 977cd2b865e9292496d6bf75fd15300762849853
OK
```
## 第6题  搭建 ActiveMQ 服务，基于 JMS，写代码分别实现对于 queue 和 topic 的消息生产和消费

### 创建queue和topic
- activemq version: 5.16.2 单机
- 创建queue: test.queue, 创建topic: test.topic

### 利用jms 分别在queue和topic

```java

public class ActivemqApplication {

    public static void main(String[] args) {

        // 定义Destination
        // Destination destination = new ActiveMQTopic("test.topic");
        Destination destination = new ActiveMQQueue("test.queue");

        testDestination(destination);

        //SpringApplication.run(ActivemqApplication.class, args);
    }

    public static void testDestination(Destination destination) {
        try {
            // 创建连接和会话
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
            ActiveMQConnection conn = (ActiveMQConnection) factory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建消费者
            MessageConsumer consumer = session.createConsumer( destination );
            final AtomicInteger count = new AtomicInteger(0);
            MessageListener listener = new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        // 打印所有的消息内容
                        // Thread.sleep();
                        System.out.println(count.incrementAndGet() + " => receive from " + destination.toString() + ": " + message);
                        // message.acknowledge(); // 前面所有未被确认的消息全部都确认。

                    } catch (Exception e) {
                        e.printStackTrace(); // 不要吞任何这里的异常，
                    }
                }
            };
            // 绑定消息监听器
            consumer.setMessageListener(listener);

            //consumer.receive()

            // 创建生产者，生产100个消息
            MessageProducer producer = session.createProducer(destination);
            int index = 0;
            while (index++ < 100) {
                TextMessage message = session.createTextMessage(index + " message.");
                producer.send(message);
            }

            Thread.sleep(20000);
            session.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```
