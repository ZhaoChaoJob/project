package com.geotmt.admin.controller;

import com.geotmt.admin.dao.TTableMyBatisDao;
import com.geotmt.admin.model.mongodb.Persion;
import com.geotmt.admin.service.TTableService;
import com.geotmt.commons.RedisService;
import com.geotmt.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
    @Autowired(required = false)
    private UserService userService; // 在找不到匹配 Bean 时也不报错

    /**
     * 测试mvc
     * @return 字符串
     */
    @RequestMapping("/test")
    @ResponseBody
//    aa:test
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

    /**
     * 测试mongodb
     *
     * @return 对象信息
     */
    @RequestMapping("/dubbo")
    @ResponseBody
    String login(String username){

        try{
            return userService.login(username);
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(int pageNo, int pageSize){
        return tTableService.listHelpTopics(pageNo, pageSize);
    }
}
