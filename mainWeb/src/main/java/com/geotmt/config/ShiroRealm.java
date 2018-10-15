package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysPermission;
import com.geotmt.admin.model.jpa.SysRole;
import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.model.jpa.SysUserGroup;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.common.exception.SimpleException;
import com.geotmt.common.exception.StatusCode;
import com.geotmt.common.utils.context.ServletUtil;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 获取用户的角色和权限信息
 */
public class ShiroRealm  extends AuthorizingRealm {
    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private SysUserService systemService;

    /**
     * 判断Token是否支持
     * @param token AuthenticationToken
     * @return 状态
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordExtToken;
    }

    /**
     * 认证信息.(身份验证) :
     *
     * @param authcToken AuthenticationToken
     * @return  Authentication 是用来验证用户身份
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordExtToken token = (UsernamePasswordExtToken) authcToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        // 校验用户名及密码 TODO 这个地方跑出的异常，controller可能是接收不到的，后面记得处理
        if(StringUtils.isEmpty(username)){
            throw new SimpleException(StatusCode.E_ACC_NULL_USERNAME) ; // 账号为空
        }else if(StringUtils.isEmpty(password)){
            throw new SimpleException(StatusCode.E_ACC_NULL_PASSWORD) ; // 密码为空
        }

        // 从数据库获取对应用户名密码的用户
        SysUser user = systemService.getSysUserByName(username, password);
        if (null == user) {
            throw new AuthenticationException(StatusCode.E_ACC_ERR_USERORPASS.toString());

            // 账户状态判断，TODO 接下来考虑自定义异常的处理
        }else if(user.getStatus() == 0 ){
            /*用户状态,0:创建未认证, 1:正常状态,2：用户被锁定*/
            throw new DisabledAccountException("此帐号已经设置为禁止登录！");
        }else{
            // 登录成功
            // 1、更新登录时间 last login time
            // 2、如果不是accessToken登录的话，则进行token的持久化，TODO 先进行redis的存储，再MongoDB。目前先用MySQL
            if(token.getAccessToken() == null ){
                String accessToken = UUID.randomUUID().toString().replaceAll("-","") ;
                token.setAccessToken("accessToken:"+accessToken); // 设定一个token，用来做用户登录的唯一标识
                systemService.saveToken(user.getUserId(),username,password,"accessToken:"+accessToken,"在user扩展表里抓取openId");
            }
            // 登录成功放在token放在head里面
//            ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();
            ServletResponse response = ((WebSubject)SecurityUtils.getSubject()).getServletResponse();
            ((HttpServletResponse)response).setHeader("accessToken",token.getAccessToken() );
            ServletUtil.writeCookie((HttpServletResponse)response,"accessToken",token.getAccessToken() );
        }
        logger.info("身份认证成功，登录用户："+username);
        return new SimpleAuthenticationInfo(user, password, getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
        SysUser userInfo = (SysUser) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        //将用户对应的角色（role）及权限（permission）放入到Authorization里。

        // 进行授权处理
        for(SysUserGroup sysUserGroup:userInfo.getSysUserGroups()){
            for(SysRole role:sysUserGroup.getSysRoles()){
                authorizationInfo.addRole(role.getRoleName());
                for(SysPermission p:role.getPermissions()){
                    authorizationInfo.addStringPermission(p.getPermissionStr());
                }
            }
        }
        return authorizationInfo;
    }
}
