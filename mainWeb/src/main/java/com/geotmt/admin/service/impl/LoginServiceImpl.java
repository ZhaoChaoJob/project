package com.geotmt.admin.service.impl;

import com.geotmt.admin.service.LoginService;
import com.geotmt.common.beans.ResultBean;
import com.geotmt.common.beans.factory.ResultBeanFactory;
import com.geotmt.common.exception.StatusCode;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * 登录Service
 */
@Service
public class LoginServiceImpl implements LoginService {

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

    /**
     * 注销登录
     * @param accessToken token
     * @param openId openId
     * @return ResultBean
     */
    @Override
    public ResultBean logout(String accessToken, String openId){
        Subject subject = SecurityUtils.getSubject();//取出当前验证主体
        if (subject != null) {
            subject.logout();//不为空，执行一次logout的操作，将session全部清空
        }
        return ResultBeanFactory.create(StatusCode.OK) ;
    }

}
