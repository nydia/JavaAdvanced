package com.nydia.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nydia.modules.entity.Order;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("orderDao")
public interface OrderDao extends BaseMapper<Order>{

    @Select({ "select user_id as \"userId\", user_name \"userName\", password, nick_name \"nickName\", id_card \"idCard\" from  geek_user order by user_id desc" })
    List<Order> selectOrder(Order order);

}
