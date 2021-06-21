package com.nydia.modules.service.impl;

import com.nydia.modules.common.Slave1DataSource;
import com.nydia.modules.entity.User;
import com.nydia.modules.mapper.UserDao;
import com.nydia.modules.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService{

     @Autowired
     private UserDao userDao;

     @Override
     public int insertUser(User user){
          return userDao.insertUser(user);
     }

     @Slave1DataSource
     @Override
     public User findUser(User user){
          return userDao.findUser(user).get(0);
     }

}
