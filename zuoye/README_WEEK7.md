# Java进阶训练营实战 作业

## 第七周作业

### 作业答案目录
1. 作业2: https://github.com/nydia/JavaAdvanced/tree/main/mysqlInsert

2. 作业9: https://github.com/nydia/JavaAdvanced/tree/main/mysqlMasterSlaveV1

3. 作业10: https://github.com/nydia/JavaAdvanced/tree/main/mysqlMasterSlaveV2


### 作业简介

#### 第二题

##### MySQL 插入100w数据性能

|量级     | 方式  | 耗时  |
|  ----  | ----  | ----  |
| 100w  | Spring框架+Druid+单线程 | 时间太久不具有参考性 |
| 100w  | Spring框架+Druid+100个线程| 4754663(毫秒),4755(秒),79(分) |
| 100w  | Jdbc+Statement + (url带rewriteBatchedStatements=true) | 125091(毫秒),125(秒),2(分) |
| 100w  | Jdbc+Statement + (url带rewriteBatchedStatements=true) + Druid | 106345(毫秒),106(秒),1.5(分) |
| 100w  | Jdbc+Statement + (url带rewriteBatchedStatements=true) + Druid(10) + 10线程 | 57889(毫秒),58(秒),1(分) |
| 100w  | Jdbc+Statement| 221600(毫秒),221(秒),3.6(分) |
| 100w  | Jdbc+PreparedStatement(url + rewriteBatchedStatements=true)| 210226(毫秒),210(秒),3.5(分) |
| 100w  | Jdbc+PreparedStatement(url + rewriteBatchedStatements=true) + + Druid(20) + 20线程| 7301(毫秒),7.3(秒) |

##### 单元测试类： com.nydia.modules.test.InsertDataTest

地址：https://github.com/nydia/JavaAdvanced/blob/main/mysqlInsert/src/test/java/com/nydia/modules/test/InsertDataTest.java

主要代码

```java
    //4.1. data:100w,方式： Jdbc+PreparedStatement(url + rewriteBatchedStatements=true) + Druid(20) + 20线程 ====> 7s
    @SuppressWarnings("Duplicates")
    public void insertByJdbcInPreparedStatementV2(){
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<Object>> futureList = new ArrayList<>();
        DruidDataSource dataSource = getDataSource();
        long startTime = new Date().getTime();
        for(int i = 1; i<= 20; i ++){
            Future<Object> future = executorService.submit(new Callable<Object>() {
                public Object call(){
                    Connection conn = null;
                    PreparedStatement pstm = null;
                    try{
                        conn = dataSource.getConnection();//使用连接池
                        conn.setAutoCommit(false);
                        System.out.println("实例化PreparedStatement对象...");
                        pstm = conn.prepareStatement("insert into `db` ( `username`) values('1')");
                        for(int i = 1; i <= 50000; i ++){
                            // 将一组参数添加到此 PreparedStatement 对象的批处理命令中。
                            pstm.addBatch();
                            if(i % 10000 == 0){
                                pstm.executeBatch();
                                conn.commit();//执行完后，手动提交事务
                                pstm.clearBatch();
                                System.out.println("执行到" + i);
                            }
                        }
                    }catch(SQLException se){
                        se.printStackTrace();// 处理 JDBC 错误
                    }catch(Exception e){
                        e.printStackTrace();// 处理 Class.forName 错误
                    }finally{
                        try{
                            conn.setAutoCommit(true);
                            if(pstm!=null) pstm.close();// 关闭资源
                            if(conn!=null) conn.close();// 关闭资源
                        }catch(SQLException se2){
                        }
                    }
                    return null;
                }
            });
            futureList.add(future);
        }
        for(Future<Object> f: futureList){
            try {
                f.get();
            }catch (InterruptedException e){
            }catch (ExecutionException e){}
        }
        long endTime = new Date().getTime();
        dataSource.close();
        executorService.shutdown();
        System.out.println("执行时间：" + (endTime - startTime) + "(毫秒)");
        System.out.println("Goodbye!");
    }
    
    public DruidDataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3316/db?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        dataSource.setInitialSize(20);//初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        dataSource.setMinIdle(5);//最小连接池数量
        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒
        dataSource.setMaxActive(20);//最大连接池数量

        try {
            dataSource.init();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dataSource;
    }
```



#### 第九题

##### 主要思路

AbstractRoutingDataSource抽象类知识，实现AOP动态切换的关键

AbstractRoutingDataSource中determineTargetDataSource()方法中获取数据源 

setDefaultTargetDataSource 设置默认数据源

setTargetDataSources 设置数据源列表 



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
(2). 主要配置

```yaml
server:
  port: 8080

spring:
  main: #允许相同bean名称覆盖
    allow-bean-definition-overriding: true
  shardingsphere:
    datasource:
      names: db0,db1
      db0:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3316/db?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
        username: root
        password:
        type: com.alibaba.druid.pool.DruidDataSource

      db1:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3326/db?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
        username: root
        password:
        type: com.alibaba.druid.pool.DruidDataSource


    sharding:
      ##配置默认数据源 主要用于写
      default-data-source-name: db0
    masterslave:
      name: ms
      #配置主库 主要用于写 只可以配置一个
      master-data-source-name: db0
      #配置主库 主要用于读取 可以配置多个
      slave-data-source-names: db1
      load-balance-algorithm-type: round_robin
    props:
      sql:
        show: true

# mybatis-plus配置
mybatis-plus:
  mapper-locations: classpath*:/mappers/*.xml
  configuration:
    call-setters-on-nulls: true
  type-aliases-package: com.nydia.modules.entity
```

(3). 运行项目

(4). 执行方法： curl http://localhost:8080/user

(5). 观察结果：

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
