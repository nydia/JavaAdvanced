package com.nydia.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nydia.modules.entity.OrderItem;
import com.nydia.modules.mapper.OrderItemDao;
import com.nydia.modules.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service("orderItemService")
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT)
    @Override
    public int insert(OrderItem orderItem) {
        return orderItemDao.insert(orderItem);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT)
    @Override
    public int update(OrderItem orderItem) {
        return orderItemDao.updateById(orderItem);
    }

    @Override
    public int delete(OrderItem orderItem) {
        return orderItemDao.deleteById(orderItem);
    }
    @Override
    public OrderItem select(OrderItem orderItem) {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(orderItem);
        return orderItemDao.selectList(queryWrapper).get(0);
    }
}
