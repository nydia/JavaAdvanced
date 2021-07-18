package io.kimmking.cache.Service;

import com.alibaba.fastjson.JSONArray;
import io.kimmking.cache.entity.Order;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: hqlv
 * @Date: 2021/7/17 18:50
 * @Description: 订单处理
 */
public class OrderService {

    private Jedis jedis;

    private String[] channels = new String[]{"order"};

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        // 1. 订阅服务
//        orderService.sub();

        // 2. 发布消息
        AtomicInteger i = new AtomicInteger(100);
        List<Order> orders = Arrays.asList(Order.builder().orderNo("order" + i.getAndIncrement()).amt(new BigDecimal(100))
                .status("1").userId(1L).createDate("2021-07-12 20:44:12").build());
        orderService.publish(JSONArray.toJSONString(orders));
    }

    //redis 发布服务
    public void publish(String msg){
        jedis().publish(channels[0], msg);
    }

    //redis 订阅服务
    public void sub(){
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("订阅消息：" + message);
                saveOrder(message);
            }
        };
        while (true){
            jedis().subscribe(jedisPubSub, channels);
        }
    }

    public Jedis jedis(){
        if(this.jedis != null){
            return this.jedis;
        }
        Jedis jedis = new Jedis("192.168.99.100", 6379);
        return jedis;
    }

    //保存订单
    public void  saveOrder(String msg){
        List<Order> orderList = JSONArray.parseArray(msg,Order.class);

        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3307/geek?rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai";
        final String USER = "root";
        final String PASS = "123456";
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            conn.setAutoCommit(false);
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            for(Order order : orderList){
                //String sql1 = "INSERT INTO `geek_order` ( `order_no`, `amt`, `status`, `user_id`, `create_date`) VALUES('20210717',1,'1','1','2021-07-17 20:34:12')";
                String sql = "INSERT INTO `geek_order` ( `order_no`, `amt`, `status`, `user_id`, `create_date`) VALUES('%s',%f,'%s',%d,'%s')";
                stmt.addBatch(String.format(sql,order.getOrderNo(), order.getAmt(), order.getStatus(),order.getUserId(),order.getCreateDate()));
            }
            stmt.executeBatch();
            // 执行
            conn.commit();//执行完后，手动提交事务
            stmt.clearBatch();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
                conn.setAutoCommit(true);
                if(conn!=null) conn.close();
            }catch(SQLException se2){
            }
        }
        System.out.println("Goodbye!");
    }


}
