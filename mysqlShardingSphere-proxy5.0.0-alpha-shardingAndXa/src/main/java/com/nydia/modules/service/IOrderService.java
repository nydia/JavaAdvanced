package com.nydia.modules.service;

import com.nydia.modules.entity.Order;

public interface IOrderService {
    int insert(Order order);
    int update(Order order);
    int delete(Order order);
    Order select(Order order);
}
