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
        filterChainDefinitionMap.put("/js/*.js", "anon"); // 放行
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/jsplug/**", "anon");
        filterChainDefinitionMap.put("/ajaxLogin", "anon");
        filterChainDefinitionMap.put("/listUser", "anon");
        filterChainDefinitionMap.put("/test", "anon");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "shiroFilter");

        // 从数据库获取
        List<SysPermission> list = systemService.getPermisAll();

        for (SysPermission sysPerm : list) {
            filterChainDefinitionMap.put(sysPerm.getUrl(), "authc"/*sysPerm.getPermissionStr()*/);
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
create table t_sys_user (
  uid bigint(20) not null auto_increment,
  create_name_id bigint(20) default null,
  create_time date default null,
  email varchar(50) collate utf8_bin default null,
  last_login_time datetime default null,
  last_update_name_id bigint(20) default null,
  last_update_time date default null,
  nickname varchar(120) collate utf8_bin default null,
  password varchar(120) collate utf8_bin default null,
  status tinyint(4) default null,
  createnameid bigint(20) default null,
  createtime date default null,
  lastlogintime datetime default null,
  lastupdatenameid bigint(20) default null,
  lastupdatetime date default null,
  primary key (uid)
) engine=innodb auto_increment=2 default charset=utf8 collate=utf8_bin;

create table t_sys_role (
  role_id bigint(20) not null auto_increment,
  description varchar(200) collate utf8_bin default null,
  role_name varchar(100) collate utf8_bin default null,
  roleid bigint(20) not null,
  rolename varchar(100) collate utf8_bin default null,
  primary key (role_id)
) engine=innodb default charset=utf8 collate=utf8_bin;

create table t_sys_permission (
  permission_id bigint(20) not null auto_increment,
  available bit(1) default null,
  name varchar(255) collate utf8_bin default null,
  parent_id bigint(20) default null,
  parent_ids varchar(255) collate utf8_bin default null,
  permission_str varchar(255) collate utf8_bin default null,
  resource_type enum('menu','button') collate utf8_bin default null,
  url varchar(255) collate utf8_bin default null,
  permissionid bigint(20) not null,
  parentid bigint(20) default null,
  parentids varchar(255) collate utf8_bin default null,
  permissionstr varchar(255) collate utf8_bin default null,
  resourcetype enum('menu','button') collate utf8_bin default null,
  primary key (permission_id)
) engine=innodb default charset=utf8 collate=utf8_bin;

create table t_sys_role_permission (
  role_id bigint(20) not null,
  permission_id bigint(20) not null,
  key fkomxrs8a388bknvhjokh440waq (permission_id),
  key fk9q28ewrhntqeipl1t04kh1be7 (role_id),
  constraint fk9q28ewrhntqeipl1t04kh1be7 foreign key (role_id) references t_sys_role (role_id),
  constraint fkomxrs8a388bknvhjokh440waq foreign key (permission_id) references t_sys_permission (permission_id)
) engine=innodb default charset=utf8 collate=utf8_bin;

create table t_sys_user_role (
  uid bigint(20) not null,
  role_id bigint(20) not null,
  key fkhh52n8vd4ny9ff4x9fb8v65qx (role_id),
  key fkput17v9wwg8wiukw8ykroaaag (uid),
  constraint fkhh52n8vd4ny9ff4x9fb8v65qx foreign key (role_id) references t_sys_role (role_id),
  constraint fkput17v9wwg8wiukw8ykroaaag foreign key (uid) references t_sys_user (uid)
) engine=innodb default charset=utf8 collate=utf8_bin;

create table t_sys_token (
  token_id bigint(20) not null,
  user_id bigint(20) default null,
  username varchar(20) collate utf8_bin default null,
  password varchar(32) collate utf8_bin default null,
  insert_time bigint(14) default null,
  invalid_time bigint(14) default null,
  status int(1) default null,
  primary key (token_id)
) engine=innodb default charset=utf8 collate=utf8_bin;
*/