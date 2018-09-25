package com.geotmt.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试
 *
 * Created by choa.zhao on 2018/9/12. */
@Controller
@RequestMapping("/a")
public class HelloworldController {

    @RequestMapping("/b")
    @ResponseBody
    String home() {

        return "Hello World!";

    }
}
