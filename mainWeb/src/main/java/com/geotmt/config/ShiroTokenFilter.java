package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysToken;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Shiro 过滤器
 */
public class ShiroTokenFilter extends AuthenticatingFilter {
    private final Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private SysUserService systemService;

    /**
     * 创建token
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @return UsernamePasswordExtToken（自定义的）
     * @throws Exception 异常
     */
    @Override
    protected UsernamePasswordExtToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String accessToken = getRequestToken((HttpServletRequest)servletRequest);
        String openId = servletRequest.getParameter("openId");
        UsernamePasswordExtToken usernamePasswordExtToken = new UsernamePasswordExtToken();
        usernamePasswordExtToken.setAccessToken(accessToken);
        if(StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(openId)){
            SysToken sysToken = this.systemService.getTokenById(accessToken) ; // 获取账号密码及token状态
            // 这里应当根据token去获取用户名和密码
            if(sysToken != null && openId.equals(sysToken.getOpenId())){
                usernamePasswordExtToken.setUsername(sysToken.getUserName());
                usernamePasswordExtToken.setPassword(sysToken.getPassword().toCharArray());
                logger.info("创建token:[{}],username:[{}],password:[{}]",accessToken,usernamePasswordExtToken.getUsername(),usernamePasswordExtToken.getPassword());
                return usernamePasswordExtToken;
            }else {
                return null;
            }
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
        String accessToken = httpRequest.getHeader("accessToken");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isEmpty(accessToken)){
            accessToken = httpRequest.getParameter("accessToken");
        }
        logger.info("获取请求的token:[{}]",accessToken);
        return accessToken;
    }}
