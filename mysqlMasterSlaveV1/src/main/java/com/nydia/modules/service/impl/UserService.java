package com.nydia.modules.service.impl;

import com.nydia.modules.entity.User;
import com.nydia.modules.repository.UserRepository;
import com.nydia.modules.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService{

     @Autowired
     private UserRepository userRepository;

     @Override
     public User insertUser(User user){
          return userRepository.save(user);
     }

     @Override
     public User selectUser(User user){
          return userRepository.selectUser(user).get(0);
     }

}
