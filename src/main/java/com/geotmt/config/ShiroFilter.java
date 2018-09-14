package com.geotmt.config;

import com.geotmt.commons.Entity.UsernamePasswordExtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Shiro 过滤器
 */
public class ShiroFilter  extends AuthenticatingFilter {

    @Override
    protected UsernamePasswordExtToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest)servletRequest);
        UsernamePasswordExtToken usernamePasswordExtToken = new UsernamePasswordExtToken();
        usernamePasswordExtToken.setApiToken(token);
        // 这里应当根据token去获取用户名和密码
        if("222".equals(token)){
            usernamePasswordExtToken.setUsername("admin");
            usernamePasswordExtToken.setPassword("123".toCharArray());
            return usernamePasswordExtToken;
        }else {
            return null;
        }


    }

    // onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return executeLogin(servletRequest, servletResponse);
    }


    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");


        return false;
    }

    // 重点在这里
    // 根据token，进行登录
    //
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordExtToken token = createToken(request, response);
        if(token == null) {
            String e1 = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(e1);
        } else {
            try {
                Subject e = this.getSubject(request, response);
                e.login(token);
                return this.onLoginSuccess(token, e, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return true;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(token != null && !"".equals(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }}
