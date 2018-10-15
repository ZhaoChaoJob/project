package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysToken;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.common.exception.StatusCode;
import com.geotmt.common.utils.context.ServletUtil;
import com.geotmt.common.utils.http.ServletResponseUtils;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Shiro 过滤器
 * 允许accessToken+openId进行登录
 *
 * 参考 https://gitee.com/zhang.w/boot-backend/blob/master/src/main/java/com/zw/admin/server/filter/RestfulFilter.java
 * 说明 http://shiro.apache.org/static/1.2.3/apidocs/org/apache/shiro/web/filter/authc/package-summary.html
 */
public class ShiroTokenFilter extends UserFilter {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private SysUserService systemService;

    /**
     * ShiroTokenFilter自定义拦截，允许accessToken+openId进行登录
     *
     * isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * @param request ServletRequest
     * @param response ServletResponse
     * @param mappedValue the filter-specific config value mapped to this filter in the URL rules mappings.
     * @return true if the request is a loginRequest or if the current subject is not null, false otherwise.
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 获取accessToken 和 openId
        String accessToken = getRequestToken((HttpServletRequest)request);
        String openId = request.getParameter("openId");

        // 当且仅当accessToken 和 openId 都不为空时，才进行登录
        // 否则继续执行，进行未登录处理
        if(StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(openId)){

            // 从数据库中获取SysToken对象、得到账号密码及token状态
            // 考虑效率问题，TODO 先从redis获取，再从数据库中获取，目前先从数据库中提取
            // 建议使用MongoDB，当然目前还是MySQL操作，再慢慢迁移
            SysToken sysToken = this.systemService.getTokenById(accessToken) ;

            // 判断accessToken 状态及合法性
            // 1、accessToken 要对应到SysToken对象；
            // 2、openId 合法；
            // 3、accessToken 状态处于有效期间
            if(sysToken != null && openId.equals(sysToken.getOpenId()) && sysToken.getStatus() == 1){
                // 创建一个shiro的自定义token对象
                UsernamePasswordExtToken usernamePasswordExtToken = new UsernamePasswordExtToken();
                usernamePasswordExtToken.setUsername(sysToken.getUserName());
                usernamePasswordExtToken.setPassword(sysToken.getPassword().toCharArray());
                usernamePasswordExtToken.setAccessToken(accessToken);

                try {
                    Subject subject = getSubject(request, response);
                    if (subject.getPrincipal() == null) {
                        subject.login(usernamePasswordExtToken);
                    }
                    logger.info(" ShiroTokenFilter自定义拦截，允许accessToken+openId进行登录accessToken:[{}],username:[{}],password:[{}],openId:[{}]",
                            accessToken,usernamePasswordExtToken.getUsername(),usernamePasswordExtToken.getPassword(),openId);
                    return true;
                } catch (Exception e) {
                    logger.info(" ShiroTokenFilter自定义拦截，允许accessToken+openId进行登录accessToken:[{}],username:[{}],password:[{}]",
                            accessToken,usernamePasswordExtToken.getUsername(),usernamePasswordExtToken.getPassword(),e);
                    return false;
                }
            }else {
                logger.info(" ShiroTokenFilter自定义拦截，允许accessToken+openId进行登录，token非法accessToken:[{}],openId:[{}],sysToken:[{}]",
                        accessToken,openId,sysToken);
                return false;
            }
        }else {
            logger.info(" ShiroTokenFilter自定义拦截，允许accessToken+openId进行登录,参数不全accessToken:[{}],openId:[{}]",
                    accessToken,openId);
            return super.isAccessAllowed(request, response, mappedValue);
        }
    }

    /**
     * 访问被拒绝后处理
     * 1、当是accessToken登录时，返回json信息
     * 2、非accessToken登录时，直接按照原计划执行
     *
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param request ServletRequest
     * @param response ServletResponse
     * @return 状态
     * @throws Exception 异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 判断是否是accessToken登录
        String accessToken = getRequestToken((HttpServletRequest)request);
        // 非accessToken登录
        if (StringUtils.isBlank(accessToken)) {
            return super.onAccessDenied(request, response);
        }

        // 是accessToken登录
        ServletResponseUtils.outputChineseByOutputStream((HttpServletResponse) response, StatusCode.R_ACC_NO_LOGIN.toString());
        return false;
    }

    /**
     * 获取请求的 accessToken
     * accessToken 可能放在head里面，也可能是参数传递
     *
     * @param httpRequest HttpServletRequest
     * @return accessToken
     */
    static String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String accessToken = httpRequest.getHeader("accessToken");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isEmpty(accessToken)){
            accessToken = httpRequest.getParameter("accessToken");
        }

        if(StringUtils.isEmpty(accessToken)){
            accessToken = ServletUtil.getCookie(httpRequest,"accessToken");
        }
        return accessToken;
    }
}
