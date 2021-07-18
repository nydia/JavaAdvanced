package io.kimmking.cache.service;

import io.kimmking.cache.entity.Order;
import io.kimmking.cache.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper; //DAO  // Repository

    public Order find(int id) {
        System.out.println(" ==> find " + id);
        return orderMapper.find(id);
    }

}
