# Java进阶训练营实战 作业

## 第八周作业


### 作业答案目录
1. 作业2（水平分库分表下面的增删改查）单元测试文件目录: https://github.com/nydia/JavaAdvanced/blob/main/mysqlShardingSphereShardingAndXa/src/test/java/com/nydia/modules/test/ShardingTest.java
2. 作业6（shardingsphere下面的xa事务）单元测试文件目录: https://github.com/nydia/JavaAdvanced/blob/main/mysqlShardingSphereShardingAndXa/src/test/java/com/nydia/modules/test/XaTest.java

### 前置

1. 注意：因为本地3307的端口已经有另外的mysq占用，所以启动proxy的端口为3308

2. shardingsphere-proxy配置说明


config-sharding.yaml:
```yaml
######################################################################################################
#
# If you want to connect to MySQL, you should manually copy MySQL driver to lib directory.
#
######################################################################################################

schemaName: sharding_db

dataSources:
  ds_0:
    url: jdbc:mysql://127.0.0.1:3307/demo_ds_0?serverTimezone=UTC&useSSL=false&characterEncoding=utf8
    username: root
    password: 123456
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1
    maintenanceIntervalMilliseconds: 30000
  ds_1:
    url: jdbc:mysql://127.0.0.1:3307/demo_ds_1?serverTimezone=UTC&useSSL=false&characterEncoding=utf8
    username: root
    password: 123456
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1
    maintenanceIntervalMilliseconds: 30000

rules:
- !SHARDING
  tables:
    t_order:
      actualDataNodes: ds_${0..1}.t_order_${0..15}
      tableStrategy:
        standard:
          shardingColumn: order_id
          shardingAlgorithmName: t_order_inline
      keyGenerateStrategy:
        column: order_id
        keyGeneratorName: snowflake
    t_order_item:
      actualDataNodes: ds_${0..1}.t_order_item_${0..15}
      tableStrategy:
        standard:
          shardingColumn: order_id
          shardingAlgorithmName: t_order_item_inline
      keyGenerateStrategy:
        column: order_item_id
        keyGeneratorName: snowflake
  bindingTables:
    - t_order,t_order_item
  defaultDatabaseStrategy:
    standard:
      shardingColumn: user_id
      shardingAlgorithmName: database_inline
  defaultTableStrategy:
    none:
  
  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${user_id % 2}
    t_order_inline:
      type: INLINE
      props:
        algorithm-expression: t_order_${order_id % 16}
    t_order_item_inline:
      type: INLINE
      props:
        algorithm-expression: t_order_item_${order_id % 16}
  
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
      props:
        worker-id: 123
```

server.yaml:
```yaml
rules:
  - !AUTHORITY
    users:
      - root@%:root
      - sharding@:sharding
    provider:
      type: NATIVE

props:
  max-connections-size-per-query: 1
  executor-size: 16  # Infinite by default.
  proxy-frontend-flush-threshold: 128  # The default value is 128.
    # LOCAL: Proxy will run with LOCAL transaction.
    # XA: Proxy will run with XA transaction.
    # BASE: Proxy will run with B.A.S.E transaction.
  proxy-transaction-type: LOCAL
  xa-transaction-manager-type: Atomikos
  proxy-opentracing-enabled: false
  proxy-hint-enabled: false
  sql-show: true
  check-table-metadata-enabled: false
  lock-wait-timeout-milliseconds: 50000 # The maximum time to wait for a lock
```


### 作业具体说明

#### 第2题  ShardingSphere-proxy 实现的水平分库分表

主要流程：
1. 配置shardingsphere-proxy，参见上面的前置配置
2. 在mysql客户端执行创建表的脚本，自动会生成十六个订单表
3. 在SpringBoot项目中配置代理地址来操作数据库

配置：
```yaml
server:
  port: 8080
spring:
  datasource:
     driver-class-name: com.mysql.jdbc.Driver
     # proxy 地址和密码
     url: jdbc:mysql://localhost:3308/sharding_db?rewriteBatchedStatements=true&useServerPrepStmts=true&cachePrepStmts=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
     username: root
     password: root
     type: com.zaxxer.hikari.HikariDataSource
# mybatis-plus配置
mybatis-plus:
  mapper-locations: classpath*:/mappers/*.xml
  configuration:
    call-setters-on-nulls: true # mybatis空字段返回null
  type-aliases-package: com.nydia.modules.entity
```

分库分表单元测试ShardingTest：
```java
//测试水平分库分表,增删查改
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingTest {
    @Autowired
    private IOrderService orderService;
    //增
    @Test
    public void testShrdingInsert() {
        for(int i = 0; i < 1; i ++){
            //order_id和user_id不能为空，user_id用于库路由,order_id用于表路由，具体参见配置
            orderService.insert(Order.builder().userId(1L).amount(new BigDecimal(100)).orderNo(UUID.randomUUID().toString()).build());
        }
    }
    //查
    @Test
    public void testShrdingSelect() {
        Order order = orderService.select(Order.builder().orderId(615870968317325312L).build());
        System.out.println(JSON.toJSON(order));
    }
    //删
    @Test
    public void testShrdingDelete() {
        orderService.delete(Order.builder().orderId(615870968317325312L).build());
    }
    //改
    @Test
    public void testShrdingUpdate() {
        orderService.update(Order.builder().orderId(615870968573177856L).amount(new BigDecimal(800.01)).build());
    }
}

```

Service:
```java
@Service("orderService")
public class OrderService implements IOrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT)
    public int insert(Order order) {
        return orderDao.insert(order);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT)
    public int update(Order order) {
        return orderDao.updateById(order);
    }

    @Override
    public int delete(Order order) {
        return orderDao.deleteById(order);
    }
    @Override
    public Order select(Order order) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(order);
        return orderDao.selectList(queryWrapper).get(0);
    }
}

```


#### 第6题  ShardingSphere 的 Atomikos XA 实现的分布式事务

主要测试流程：


1. 水平分库采用模2的算法，分表采用模16的算法
2. user_id用于分库， order_id和order_item_id用于分表，order_id和order_item_id采用雪花算法生成
3. user_id=1订单order插入库demo_ds_1， user_id=2订单项插入库demo_ds_0
4. service类里面的方法加上事务注解（@Transactional(readOnly = false, isolation=Isolation.REPEATABLE_READ)），模拟订单插入成功，订单项插入失败


下面仅列举单元测试的说明和主要的Service方法以及链接数据库配置，DAO和其他的配置省略（具体可以参看代码）

配置：
```yaml
server:
  port: 8080
spring:
  datasource:
     driver-class-name: com.mysql.jdbc.Driver
     # proxy 地址和密码
     url: jdbc:mysql://localhost:3308/sharding_db?rewriteBatchedStatements=true&useServerPrepStmts=true&cachePrepStmts=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
     username: root
     password: root
     type: com.zaxxer.hikari.HikariDataSource
# mybatis-plus配置
mybatis-plus:
  mapper-locations: classpath*:/mappers/*.xml
  configuration:
    call-setters-on-nulls: true # mybatis空字段返回null
  type-aliases-package: com.nydia.modules.entity
```

事务Xa单元测试类：
```java
//Xa事务测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class XaTest {
    @Autowired
    private IOrderService orderService;
    // ShardingSphere Atomikos XA
    @Test
    public void testAtomikosXA(){
        /**
         1. 水平分库采用模2的算法，分表采用模16的算法
         2. user_id用于分库， order_id和order_item_id用于分表，order_id和order_item_id采用雪花算法生成
         3. user_id=1订单order插入库demo_ds_1， user_id=2订单项插入库demo_ds_0
         4. service类里面的方法加上事务注解，模拟订单插入成功，订单项插入失败
        */
         Order order = Order.builder().userId(1L).amount(new BigDecimal(100)).orderNo(UUID.randomUUID().toString()).build();
        OrderItem orderItem = OrderItem.builder().userId(2L).orderId(new Date().getTime()).goodName("商品").goodId(1L).price(new BigDecimal(3.21)).build();
        orderService.insert(order, orderItem);
    }
}

```

Service:
```java
@Service("orderService")
public class OrderService implements IOrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ)
    public int insert(Order order, OrderItem orderItem) {
        int r1 = orderDao.insert(order);
        //模拟失败
		System.out.println(1/0);
        int r2 = orderItemDao.insert(orderItem);
        return r1 + r2;
    }
}
```