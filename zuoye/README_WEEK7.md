# Java进阶训练营实战 作业

## 第七周作业

### 作业答案目录
1. 作业2: https://github.com/nydia/JavaAdvanced/tree/main/mysqlInsert

2. 作业9: https://github.com/nydia/JavaAdvanced/tree/main/mysqlMasterSlaveV1

3. 作业10: https://github.com/nydia/JavaAdvanced/tree/main/mysqlMasterSlaveV2


### 作业简介

#### 第二题


#### 第九题


#### 第十题

1. 读写分离 - 框架：shardingsphere-jdbc

注意：sharding-jdbc-spring-boot-starter 的版本，高的版本比如：4.1.1会出现无法加载配置的情况，目前还不知道4.1.1做出了哪些大的变更

2. 测试读写分离和动态切换数据源

(1). 使用sql：

~~~sql
CREATE TABLE `geek_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `id_card` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户';
~~~

(2). 运行项目

(3). 执行方法： curl http://localhost:8080/user

(2). 观察结果：

~~~log
2021-06-20 18:03:34.443  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Rule Type: master-slave
2021-06-20 18:03:34.443  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQL: insert into geek_user(user_name, password, nick_name, id_card) values(?, ?, ?, ?) ::: DataSources: db0
2021-06-20 18:03:37.548  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Rule Type: master-slave
2021-06-20 18:03:37.548  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQL: insert into geek_user(user_name, password, nick_name, id_card) values(?, ?, ?, ?) ::: DataSources: db0
2021-06-20 18:03:38.233  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Rule Type: master-slave
2021-06-20 18:03:38.233  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQL: insert into geek_user(user_name, password, nick_name, id_card) values(?, ?, ?, ?) ::: DataSources: db0
2021-06-20 18:03:38.842  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Rule Type: master-slave
2021-06-20 18:03:38.842  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQL: insert into geek_user(user_name, password, nick_name, id_card) values(?, ?, ?, ?) ::: DataSources: db0
2021-06-20 18:03:39.101  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Rule Type: master-slave
2021-06-20 18:03:39.101  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQL: insert into geek_user(user_name, password, nick_name, id_card) values(?, ?, ?, ?) ::: DataSources: db0
2021-06-20 18:03:39.342  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Rule Type: master-slave
2021-06-20 18:03:39.342  INFO 36712 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQL: select user_id as "userId", user_name "userName", password, nick_name "nickName", id_card "idCard" from  geek_user order by user_id desc ::: DataSources: db1
~~~