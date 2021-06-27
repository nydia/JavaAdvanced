package com.nydia.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.modules.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component("orderItemDao")
public interface OrderItemDao extends BaseMapper<OrderItem>{

}
