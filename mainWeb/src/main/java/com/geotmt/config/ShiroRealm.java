package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysPermission;
import com.geotmt.admin.model.jpa.SysRole;
import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.model.jpa.SysUserGroup;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * 获取用户的角色和权限信息
 */
public class ShiroRealm  extends AuthorizingRealm {
    private final Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private SysUserService systemService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordExtToken;
    }

    /**
     * 认证信息.(身份验证) :
     * @param authcToken AuthenticationToken
     * @return  Authentication 是用来验证用户身份
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordExtToken token = (UsernamePasswordExtToken) authcToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        String accessToken = UUID.randomUUID().toString().replaceAll("-","") ;
        token.setAccessToken("accessToken:"+accessToken); // 设定一个token，用来做用户登录的唯一标识

        // 从数据库获取对应用户名密码的用户
        SysUser user = systemService.getSysUserByName(username, password);
        systemService.saveToken(user.getUserId(),username,password,accessToken);

        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        }else if("0".equals(user.getStatus())){
            /*用户状态,0:创建未认证, 1:正常状态,2：用户被锁定*/
            throw new DisabledAccountException("此帐号已经设置为禁止登录！");
        }else{
            //登录成功
            //更新登录时间 last login time
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
