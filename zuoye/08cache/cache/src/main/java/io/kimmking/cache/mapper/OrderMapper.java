package io.kimmking.cache.mapper;

import io.kimmking.cache.entity.Order;
import io.kimmking.cache.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    Order find(int id);

}
