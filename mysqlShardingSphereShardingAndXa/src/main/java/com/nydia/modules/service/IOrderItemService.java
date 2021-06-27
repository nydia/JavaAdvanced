package com.nydia.modules.service;

import com.nydia.modules.entity.OrderItem;

public interface IOrderItemService {
    int insert(OrderItem orderItem);
    int update(OrderItem orderItem);
    int delete(OrderItem orderItem);
    OrderItem select(OrderItem orderItem);
}
