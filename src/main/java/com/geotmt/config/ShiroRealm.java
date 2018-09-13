package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysPermission;
import com.geotmt.admin.model.jpa.SysRole;
import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.commons.Entity.UsernamePasswordExtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户的角色和权限信息
 */
public class ShiroRealm  extends AuthorizingRealm {

    @Autowired
    private SysUserService systemService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordExtToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordExtToken token = (UsernamePasswordExtToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());

        // 从数据库获取对应用户名密码的用户
        SysUser user = systemService.getSysUserByName(name, password);

        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        }else if("0".equals(user.getStatus())){
            /**用户状态,0:创建未认证, 1:正常状态,2：用户被锁定*/
            throw new DisabledAccountException("此帐号已经设置为禁止登录！");
        }else{
            //登录成功
            //更新登录时间 last login time
        }
//        Logger.getLogger(getClass()).info("身份认证成功，登录用户："+name);
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

        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRoleName());
            for(SysPermission p:role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermissionStr());
            }
        }

        return authorizationInfo;
    }
}
