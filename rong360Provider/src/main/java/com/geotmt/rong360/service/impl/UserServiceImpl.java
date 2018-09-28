package com.geotmt.rong360.service.impl;

import com.geotmt.demo.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public String login(String username) {
        System.out.println("登录人："+username);
        return username + " 登录成功 ! 欢迎回来。。。。";
    }

    @Override
    public String regist(String username) {
        return username + " 注册成功！　欢迎加入这个大家园。。。。";
    }
}