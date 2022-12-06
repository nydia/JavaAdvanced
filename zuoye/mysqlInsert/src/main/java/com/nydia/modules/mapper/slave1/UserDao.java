package com.nydia.modules.mapper.slave1;

import com.nydia.modules.entity.slave1.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志Dao
 */
@Component("slave1UserDao")
@Mapper
public interface UserDao {

    @Select({ "select user_id as \"userId\", user_name \"userName\", password, nick_name \"nickName\", id_card \"idCard\" from  geek_user order by user_id desc" })
    List<User> selectUser(User user);

}
