package com.nydia.modules.service;

import com.nydia.modules.entity.Order;
import com.nydia.modules.entity.OrderItem;

public interface IOrderService {
    int insert(Order order);
    int update(Order order);
    int delete(Order order);
    Order select(Order order);

    int insert(Order order, OrderItem orderItem);
}
