package com.peiwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peiwan.dto.LoginParam;
import com.peiwan.dto.RegisterParam;
import com.peiwan.entity.Master;
import com.peiwan.entity.User;
import com.peiwan.mapper.MasterMapper;
import com.peiwan.mapper.UserMapper;
import com.peiwan.service.UserService;
import com.peiwan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MasterMapper masterMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String login(LoginParam param) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, param.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user != null && encoder.matches(param.getPassword(), user.getPassword())) {
            if (user.getStatus() == 0) {
                throw new RuntimeException("账号已被禁用");
            }
            return JwtUtil.generateToken(user.getId().toString(), user.getRole());
        }
        return null;
    }

    @Override
    @Transactional
    public boolean register(RegisterParam param) {
        // 检查用户名是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, param.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            return false;
        }
        User user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(encoder.encode(param.getPassword()));
        user.setRealName(param.getRealName());
        user.setPhone(param.getPhone());
        user.setRole(param.getRole());
        user.setStatus(1);
        userMapper.insert(user);
        // 如果是陪玩师，插入master表
        if ("master".equals(param.getRole())) {
            Master master = new Master();
            master.setUserId(user.getId());
            master.setGameLevel(param.getGameLevel());
            master.setPricePerHour(param.getPricePerHour() != null ? 
                    java.math.BigDecimal.valueOf(param.getPricePerHour()) : null);
            master.setTags(param.getTags());
            master.setAuthStatus("pending");
            masterMapper.insert(master);
        }
        return true;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }
}