package com.nydia.modules.web;

import com.alibaba.fastjson.JSON;
import com.nydia.modules.entity.User;
import com.nydia.modules.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查Controller
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity handleUser(){
        User userInsert = new User();
        userInsert.setUserName("王五");
        userInsert.setNickName("小五");
        userInsert.setPassword("123456");
        userInsert.setIdCard("34112");
        userService.insertUser(userInsert);

        User user = userService.selectUser(null);

        return ResponseEntity.ok(JSON.toJSON(user));
    }

}
