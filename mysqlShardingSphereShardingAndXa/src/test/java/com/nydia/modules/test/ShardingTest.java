package com.nydia.modules.test;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.Order;
import com.nydia.modules.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

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
