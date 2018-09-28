package com.geotmt.demo;

public interface UserService {
    /**
     * 模拟用户登录
     * @param username
     * @return
     */
    String login(String username);

    /**
     * 模拟用户注册
     * @param username
     * @return
     */
    String regist(String username);
}
