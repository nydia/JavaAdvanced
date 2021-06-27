package com.nydia.modules.test;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.Order;
import com.nydia.modules.entity.OrderItem;
import com.nydia.modules.service.IOrderItemService;
import com.nydia.modules.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

//Xa事务测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class XaTest {
    @Autowired
    private IOrderItemService orderItemService;
    //增
    @Test
    public void testXaInsert() {
        for(int i = 0; i < 1; i ++){
//            OrderItem orderItem = new OrderItem();
//            orderItem.setGoodName("111ce");
//            orderItem.setGoodId(1L);
//            orderItem.setOrderId(new Date().getTime());
//            orderItem.setUserId(1L);
//            orderItem.setPrice();
            try {
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
//            orderItemService.insert(orderItem);
            orderItemService.insert(OrderItem.builder().goodName("商品").goodId(1L).orderId(new Date().getTime()).price(new BigDecimal(3.21)).userId(1L).build());
        }
    }
}
