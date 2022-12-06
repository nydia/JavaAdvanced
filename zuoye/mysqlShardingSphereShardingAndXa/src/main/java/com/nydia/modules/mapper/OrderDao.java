package com.nydia.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.modules.entity.Order;
import org.springframework.stereotype.Component;

@Component("orderDao")
public interface OrderDao extends BaseMapper<Order>{

}
