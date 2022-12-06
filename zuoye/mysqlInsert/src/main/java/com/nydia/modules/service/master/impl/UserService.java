package com.nydia.modules.service.master.impl;

import com.nydia.modules.entity.master.User;
import com.nydia.modules.mapper.master.UserDao;
import com.nydia.modules.service.master.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("masterUserService")
public class UserService implements IUserService{

     @Autowired
     private UserDao masterUserDao;

     @Override
     public int insertUser(User user){
          return masterUserDao.insertUser(user);
     }
}
