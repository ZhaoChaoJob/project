package com.geotmt.admin.controller;

import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.common.beans.ResultBean;
import com.geotmt.common.beans.factory.ResultBeanFactory;
import com.geotmt.common.exception.SimpleException;
import com.geotmt.common.exception.StatusCode;
import com.geotmt.common.utils.http.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geo on 2018/10/11. */
public abstract class BaseController {
    private final Logger logger = LogManager.getLogger(getClass());

    public SysUser getSysUser(){
        return  (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    public Long getSysUserId(){
        SysUser sysUser =  getSysUser();
        if(sysUser != null){
            return sysUser.getUserId() ;
        }else {
            return null;
        }
    }

    @ExceptionHandler({SimpleException.class,
            AuthenticationException.class,
            AuthorizationException.class,
            NullPointerException.class,
            RuntimeException.class,
            Exception.class})
    @ResponseBody
    public ResultBean responseException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String uri = request.getRequestURI();
        String path = request.getContextPath();

        uri = HttpUtil.uriFormat(uri, path) ;

        boolean ajax = HttpUtil.checkAjaxReq(request) ;
        // ajax调用或者api、app调用就返回json，API路径需要满足规范：/api
        logger.warn("controller统一异常处理",e);
        if (e instanceof SimpleException) {
            return ResultBeanFactory.create(StatusCode.R_ACC_NO_LOGIN);
        }else if(e instanceof AuthenticationException){
            return ResultBeanFactory.create(StatusCode.E_ACC_ERR_USERORPASS);
        }else if(e instanceof AuthorizationException){
            return ResultBeanFactory.create(StatusCode.R_ACC_AUTH_NO_ENOUGH);
        }
        return ResultBeanFactory.create(null,e.getMessage());
    }
}
