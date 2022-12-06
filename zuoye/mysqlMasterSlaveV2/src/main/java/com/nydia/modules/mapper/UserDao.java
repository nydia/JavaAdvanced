package com.nydia.modules.mapper;

import com.nydia.modules.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志Dao
 */
@Component("userDao")
@Mapper
public interface UserDao {

    @Insert({ "insert into geek_user(user_name, password, nick_name, id_card) values(#{userName}, #{password}, #{nickName}, #{idCard})" })
    int insertUser(User user);

    @Select({ "select user_id as \"userId\", user_name \"userName\", password, nick_name \"nickName\", id_card \"idCard\" from  geek_user order by user_id desc" })
    List<User> selectUser(User user);

}
