package com.peiwan.service;

import com.peiwan.dto.LoginParam;
import com.peiwan.dto.RegisterParam;
import com.peiwan.entity.User;

public interface UserService {
    String login(LoginParam param);
    boolean register(RegisterParam param);
    User getUserById(Long id);
    boolean updateUser(User user);
}