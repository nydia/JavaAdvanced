package com.nydia.modules.mapper.master;

import com.nydia.modules.entity.master.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 日志Dao
 */
@Component("masterUserDao")
@Mapper
public interface UserDao {

    @Insert({ "insert into geek_user(user_name, password, nick_name, id_card) values(#{userName}, #{password}, #{nickName}, #{idCard})" })
    int insertUser(User user);

}
