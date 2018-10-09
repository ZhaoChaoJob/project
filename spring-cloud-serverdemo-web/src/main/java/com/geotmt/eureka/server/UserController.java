package com.geotmt.eureka.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by geo on 2018/10/9.
 */
@RestController
public class UserController {
    private UserService userService;

    @RequestMapping(value="/getUser/{name}", method= RequestMethod.GET)
    public String getUser(@PathVariable("name")String name){
        return "来自服务端的调用:" + name ;
    }
}
