package com.nydia.modules;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author ips
 */
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan("com.nydia.modules")
@MapperScan(basePackages = {"com.nydia.modules.mapper"}) //扫描DAO
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}
