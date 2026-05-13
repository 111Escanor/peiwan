package com.peiwan.controller;

import com.peiwan.dto.LoginParam;
import com.peiwan.dto.RegisterParam;
import com.peiwan.dto.Result;
import com.peiwan.entity.User;
import com.peiwan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginParam param) {
        String token = userService.login(param);
        if (token != null) {
            return Result.success(token);
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody RegisterParam param) {
        boolean success = userService.register(param);
        if (success) {
            return Result.success("注册成功");
        } else {
            return Result.error(400, "用户名已存在");
        }
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute Long userId) {
        User user = userService.getUserById(userId);
        user.setPassword(null); // 脱敏
        return Result.success(user);
    }
}