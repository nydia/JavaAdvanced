package com.nydia.modules.test;


import com.nydia.modules.entity.master.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertDataTest {

    @Autowired
    private com.nydia.modules.service.master.IUserService masterUserService;

    ThreadLocal<Long> runTime = new ThreadLocal<>();

    @Test
    public void insertData() {
        insertByJdbc();
    }

    //1. 普通的单线程，利用框架加上Druid连接池
    public void insertDataByDruidInSingleThread() {
        runTime.set(new Date().getTime());
        for (int i = 0; i < 1000000; i++) {
            com.nydia.modules.entity.master.User userInsert = new com.nydia.modules.entity.master.User();
            userInsert.setUserName("王五");
            userInsert.setNickName("小五-" + i);
            userInsert.setPassword("123456");
            userInsert.setIdCard("34112");
            masterUserService.insertUser(userInsert);
        }
        System.out.println("执行时间1000000次插入的时间：" + (new Date().getTime() - runTime.get()) + "（毫秒）");
    }

    //2. 100个线程，利用框架加上Druid连接池
    public void insertDataByDruidIn100Thread() {
        runTime.set(new Date().getTime());
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<Object>> futures = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            Future<Object> future = executorService.submit(new Callable<Object>() {
                public Object call(){
                    User user = User.builder().userName("王五").nickName("小五-").password("123456").idCard("2344556666").build();
                    masterUserService.insertUser(user);
                    return null;
                }
            });
            futures.add(future);
        }
        for (Future<Object> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        System.out.println("执行时间1000000次插入的时间：" + (new Date().getTime() - runTime.get()) + "（毫秒）");
    }

    //3. Jdbc插入
    public void insertByJdbc(){
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3316/db";
        final String USER = "root";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "insert into `geek_user` ( `user_name`, `password`, `nick_name`, `id_card`) values('王五','123456','小五','2344556666')";
            stmt.executeUpdate(sql);
            // 完成后关闭
            stmt.close();
            conn.close();
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
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }


}
