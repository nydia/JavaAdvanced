package com.nydia.modules.service;

import com.nydia.modules.entity.User;

/**
 * 接口
 */
public interface IUserService {

    User insertUser(User user);

    User selectUser(User user);

}
