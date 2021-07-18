package io.kimmking.cache.controller;

import io.kimmking.cache.entity.Order;
import io.kimmking.cache.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class OrderController {

    @Autowired
    OrderService orderService;
    
    @RequestMapping("/order/find")
    Order find(Integer id) {
        return orderService.find(id);
        //return new User(1,"KK", 28);
    }

}