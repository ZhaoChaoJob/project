package com.geotmt.admin.controller;

import com.geotmt.admin.service.TTableService;
import com.geotmt.commons.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试
 *
 * Created by choa.zhao on 2018/9/12. */
@Controller
public class HelloController {

    @Autowired
    private TTableService tTableService;
    @Autowired
    private RedisService redisUtil;

    /**
     * 测试mvc
     * @return 字符串
     */
    @RequestMapping("/test")
    @ResponseBody
    String test() {
        return "欢迎进入权限模块，请浏览 com.geotmt.admin.controller 包。";
    }

    /**
     * 测试jpa
     *
     * @return 对象信息
     */
    @RequestMapping("/jpa")
    @ResponseBody
    Object testJpa() {
        return this.tTableService.findTTable();
    }

    /**
     * 测试redis
     *
     * @return 对象信息
     */
    @RequestMapping("/redis")
    @ResponseBody
    Object testRedis() {
        return this.redisUtil.exists("key");
    }
}
