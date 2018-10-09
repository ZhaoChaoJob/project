package com.geotmt.eureka.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by geo on 2018/10/9. */
@FeignClient("BING-FIRST-PRODUCTION")
public interface  UserService {

    @RequestMapping(value="/getUser/{name}", method= RequestMethod.GET)
    public String getUser(@PathVariable("name")String name);
}
