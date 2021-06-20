package com.nydia.modules.service.slave1.impl;

import com.nydia.modules.entity.slave1.User;
import com.nydia.modules.mapper.slave1.UserDao;
import com.nydia.modules.service.slave1.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("slave1UserService")
public class UserService implements IUserService {

     @Autowired
     private UserDao slave1UserDao;

     public User selectUser(User user){
          return slave1UserDao.selectUser(user).get(0);
     }
}
