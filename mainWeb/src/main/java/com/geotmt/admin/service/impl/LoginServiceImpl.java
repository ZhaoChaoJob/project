package com.geotmt.admin.service.impl;

import com.geotmt.admin.dao.SysTokenRepository;
import com.geotmt.admin.service.LoginService;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录Service
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysTokenRepository sysTokenDao;
    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @param accessToken token
     */
    @Override
    public String login(String username,String password,String accessToken){
        UsernamePasswordExtToken token = new UsernamePasswordExtToken(username, password,accessToken);
        SecurityUtils.getSubject().login(token);
        return token.getAccessToken();
    }


}
