package com.geotmt.admin.controller;

import com.geotmt.admin.dao.TTableMyBatisDao;
import com.geotmt.admin.model.mongodb.Persion;
import com.geotmt.admin.service.TTableService;
import com.geotmt.commons.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    @Autowired
    private TTableMyBatisDao myBatisDao ;
    @Autowired
    private MongoTemplate mongoTemplate;

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

    /**
     * 测试myBatis
     *
     * @return 对象信息
     */
    @RequestMapping("/myBatis")
    @ResponseBody
    Object testMyBatis() {
        return this.myBatisDao.find() ;
    }

    /**
     * 测试mongodb
     *
     * @return 对象信息
     */
    @RequestMapping("/mongo")
    @ResponseBody
    Object testMongo() {
        Persion persion = new Persion() ;
        persion.set_id("123");
        persion.setName("大老张");
        this.mongoTemplate.save(persion);
        return this.mongoTemplate.findById("123",Persion.class);
    }
}
