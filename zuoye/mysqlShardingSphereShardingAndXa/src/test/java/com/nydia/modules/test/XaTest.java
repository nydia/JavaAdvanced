package com.nydia.modules.test;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.Order;
import com.nydia.modules.entity.OrderItem;
import com.nydia.modules.service.IOrderItemService;
import com.nydia.modules.service.IOrderService;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

//Xa事务单元测试
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
         4. service类里面的方法加上事务注解（@Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ)），模拟订单插入成功，订单项插入失败
        */
         Order order = Order.builder().userId(1L).amount(new BigDecimal(100)).orderNo(UUID.randomUUID().toString()).build();
        OrderItem orderItem = OrderItem.builder().userId(2L).orderId(new Date().getTime()).goodName("商品").goodId(1L).price(new BigDecimal(3.21)).build();
        orderService.insert(order, orderItem);
    }
}
