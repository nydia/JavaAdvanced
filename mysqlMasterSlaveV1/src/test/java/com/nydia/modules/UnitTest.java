package com.nydia.modules;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.User;
import com.nydia.modules.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: hqlv
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testUser() {
        User userInsert = new User();
        userInsert.setUserName("王五");
        userInsert.setNickName("小五");
        userInsert.setPassword("123456");
        userInsert.setIdCard("34112");
        userService.insertUser(userInsert);

        User user = userService.findUser(null);

        System.out.println(JSON.toJSON(user));
    }

}
