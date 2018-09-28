package com.geotmt.config;

import com.geotmt.admin.model.jpa.SysPermission;
import com.geotmt.admin.service.SysPermissionService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro配置项
 * https://download.csdn.net/download/lzbcitl/10537303
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private SysPermissionService systemService;


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题
     *
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的,因为在
     * 		 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明：
     *  1、一个URL可以配置多个Filter，使用逗号分隔
     *  2、当设置多个过滤器时，全部验证通过，才视为通过
     *  3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.html"页面
        shiroFilterFactoryBean.setLoginUrl("/login2");

        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("auth3",new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 权限控制map
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        /*
            权限拦截
            /app/o/*    -- 手机开放路径
            /app/u/*    -- 手机普通用户路径
            /app/m/*    -- 手机管理员路径

            /api/o/*    -- API开放路径
            /api/u/*    -- API普通用户路径
            /api/m/*    -- API管理员路径

            /web/o/*    -- 网站开放路径
            /web/u/*    -- 网站普通用户路径
            /web/m/*    -- 网站管理员路径

            /com/o/*    -- 通用开放接口
            /com/u/*    -- 通用普通用户接口
            /com/m/*    -- 通用管理员接口
            /
         */
        filterChainDefinitionMap.put("/js/*.js", "anon"); // 放行
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/jsplug/**", "anon");
        filterChainDefinitionMap.put("/ajaxLogin", "anon");
        filterChainDefinitionMap.put("/listUser", "anon");
        filterChainDefinitionMap.put("/app/o/*", "anon");
        filterChainDefinitionMap.put("/api/o/*", "anon");
        filterChainDefinitionMap.put("/web/o/*", "anon");
        filterChainDefinitionMap.put("/com/o/*", "anon");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "shiroFilter");

        // 从数据库获取
        List<SysPermission> list = systemService.getPermisAll();

        for (SysPermission sysPerm : list) {
//            filterChainDefinitionMap.put(sysPerm.getUrl(), "authc"/*sysPerm.getPermissionStr()*/);
        }
        filterChainDefinitionMap.put("/jpa", "auth3"); // 搜身检查
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public ShiroRealm myShiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    @Bean
    @DependsOn("shiroRedisSessionDAO") // 控制启动顺序
    public SessionManager sessionManager(ShiroRedisSessionDAO shiroRedisSessionDAO){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        ShiroRedisSessionDAO shiroRedisSessionDAO = new ShiroRedisSessionDAO();
        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setSessionIdUrlRewritingEnabled(false);

        //如果开启redis缓存且geo.shiro.redis=true，则shiro session存到redis里
        sessionManager.setSessionDAO(shiroRedisSessionDAO);
        return sessionManager;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

// =========== 建表语句 ==========
/*
-- 用户信息表
-- drop table if exists t_sys_user;
create table t_sys_user (
  `uid` 					numeric(20) 	not null 	comment '用户id' 		,
  `create_name_id` 			numeric(20) 				comment '创建人id' 		,
  `create_time` 			numeric(14) 				comment '创建时间' 		,
  `email` 					varchar(50) 				comment '邮箱|登录帐号' ,
  `last_login_time` 		numeric(14) 				comment '最后登录时间' 	,
  `last_update_name_id` 	numeric(20) 				comment '最后修改人id' 	,
  `last_update_time`		numeric(14) 				comment '最后修改时间' 	,
  `nickname` 				varchar(120) 				comment '用户昵称' 		,
  `password` 				varchar(120) 				comment '密码' 			,
  `status` 					numeric(4) 					comment '用户状态,0:创建未认证, 1:正常状态,2：用户被锁定'
) engine=innodb comment '用户信息表' ;
alter table t_sys_user add primary key (uid) ;
create index ix_sys_user_email on t_sys_user (email) ;

-- 用户角色表
-- drop table if exists t_sys_role;
create table t_sys_role (
  `role_id` 		numeric(20) 	not null 	comment '角色id' 					,
  `description` 	varchar(200)  				comment '角色描述,UI界面显示使用' 	,
  `role_name` 		varchar(100)  				comment '角色名称'
) engine=innodb comment '用户角色表' ;
alter table t_sys_role add primary key (role_id) ;

-- 用户权限表
-- drop table if exists t_sys_permission;
create table t_sys_permission (
  `permission_id` 	numeric(20) not null 	comment '权限id' 		,
  `available` 		numeric(1) 				comment '' 				,
  `name` 			varchar(255)  			comment '名称' 			,
  `parent_id` 		numeric(20) 			comment '父编号' 		,
  `parent_ids` 		varchar(255)  			comment '父编号列表' 	,
  `permission_str` 	varchar(255)  			comment '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view' ,
  `resource_type` 	enum('menu','button')  	comment '资源类型，[menu|button]' ,
  `url` 				varchar(255)   			comment '资源路径'
) engine=innodb comment '用户权限表' ;
alter table t_sys_permission add primary key (permission_id) ;

-- 角色&权限关联表
-- drop table if exists t_sys_role_permission;
create table t_sys_role_permission (
  `role_id` 			numeric(20) not null 	comment '角色ID' ,
  `permission_id` 		numeric(20) not null	comment '权限ID'
) engine=innodb comment '角色&权限关联表' ;
alter table t_sys_role_permission add foreign key(role_id) references t_sys_role(role_id);
alter table t_sys_role_permission add foreign key(permission_id) references t_sys_permission(permission_id);


-- 用户&角色关联表
-- drop table if exists t_sys_user_role;
create table t_sys_user_role (
  `uid` 				numeric(20) not null	comment '用户ID' ,
  `role_id` 			numeric(20) not null	comment '角色ID'
) engine=innodb comment '用户&角色关联表' ;
alter table t_sys_user_role add foreign key(role_id) references t_sys_user(uid);
alter table t_sys_user_role add foreign key(role_id) references t_sys_role(role_id);

-- 用户Token表，主要用户Api调用
-- drop table if exists t_sys_token;
create table t_sys_token (
  `token_id` 			numeric(20) not null	comment '令牌ID' 	,
  `user_id` 			numeric(20) 			comment '用户ID' 	,
  `user_code`			varchar(20)  			comment '账号' 		,
  `password` 			varchar(32)  			comment '密码' 		,
  `insert_time` 		numeric(14) 			comment '插入时间' 	,
  `invalid_time` 		numeric(14) 			comment '失效时间' 	,
  `status` 				numeric(1) 				comment '令牌状态'
) engine=innodb comment '用户Token表，主要用户Api调用' ;
alter table t_sys_token add primary key (token_id) ;


*/