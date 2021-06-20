package com.nydia.modules.web;

import com.alibaba.fastjson.JSON;
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
    private com.nydia.modules.service.master.IUserService masterUserService;

    @Autowired
    private com.nydia.modules.service.slave1.IUserService slave1UserService;

    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity handleUser(){
        com.nydia.modules.entity.master.User userInsert = new com.nydia.modules.entity.master.User();
        userInsert.setUserName("王五");
        userInsert.setNickName("小五");
        userInsert.setPassword("123456");
        userInsert.setIdCard("34112");
        masterUserService.insertUser(userInsert);

        com.nydia.modules.entity.slave1.User user = slave1UserService.selectUser(null);

        return ResponseEntity.ok(JSON.toJSON(user));
    }

}
