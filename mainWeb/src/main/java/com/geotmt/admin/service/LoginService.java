package com.geotmt.admin.service;

import com.geotmt.admin.model.jpa.SysToken;

/**
 * 登录Service
 */
public interface LoginService {

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @param accessToken accessToken
     */
    public String login(String username,String password,String accessToken);


}
