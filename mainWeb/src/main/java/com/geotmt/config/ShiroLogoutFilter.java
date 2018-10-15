package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import org.apache.shiro.SecurityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Shiro退出登录过滤器
 */
public class ShiroLogoutFilter extends LogoutFilter {
    private final Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private SysUserService systemService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        String accessToken = ShiroTokenFilter.getRequestToken((HttpServletRequest) request);
        SysUser userInfo = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            subject.logout();
            if (StringUtils.isNotBlank(accessToken)) {
                // 删除token对象
                this.systemService.delTokenById(accessToken);
            }
            logger.info("Shiro退出登录过滤器,非accessToken退出accessToken:[{}],username:[{}],password:[{}]",
                    accessToken,userInfo.getNickname(),userInfo.getPassword());
        } catch (SessionException ise) {
            logger.info("Shiro退出登录过滤器,非accessToken退出accessToken:[{}],username:[{}],password:[{}]",
                    accessToken,userInfo.getNickname(),userInfo.getPassword(),ise);
        }
        issueRedirect(request, response, redirectUrl);
        return false;
    }
}
