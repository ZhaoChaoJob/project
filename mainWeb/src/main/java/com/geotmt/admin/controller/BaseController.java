package com.geotmt.admin.controller;

import com.geotmt.common.exception.SimpleException;
import com.geotmt.common.utils.http.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geo on 2018/10/11. */
public abstract class BaseController {
    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler({SimpleException.class,
            NullPointerException.class,
            RuntimeException.class,
            Exception.class})
    public void responseException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String uri = request.getRequestURI();
        String path = request.getContextPath();

        uri = HttpUtil.uriFormat(uri, path) ;

        boolean ajax = HttpUtil.checkAjaxReq(request) ;
        // ajax调用或者api、app调用就返回json，API路径需要满足规范：/api

            if (e instanceof SimpleException) {
            }

    }
}