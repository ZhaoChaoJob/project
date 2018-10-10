package com.geotmt.eureka.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by geo on 2018/10/9.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/serviceinfo/{api}", method= RequestMethod.GET)
    public Object getInfo(@PathVariable("api")String str) {
        List<ServiceInstance> list = discoveryClient.getInstances(str);
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri();
        }
        return null;
    }

//    @HystrixCommand(fallbackMethod = "findByIdFallback")
    public Object findByIdFallback(String str) {

        return "findByIdFallback";
    }

    @RequestMapping(value="/getuser/{name}", method= RequestMethod.GET)
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")},fallbackMethod = "findByIdFallback")
    public Object getuser(@PathVariable("name")String str) {
       return this.userService.getUser(str) ;
    }

    @RequestMapping(value="/serviceinfo2/{web}/{api}", method= RequestMethod.GET)
    public Object getInfo2(@PathVariable("web")String str,@PathVariable("api")String str2) {
        List<ServiceInstance> list = discoveryClient.getInstances(str);
        ServiceInstance serviceInstance = null ;
        if (list != null && list.size() > 0 ) {
            serviceInstance = list.get(0);
        }
        System.out.println(serviceInstance.getUri() +"/"+str2);
        return restTemplate.getForObject("http://BING-FIRST-PRODUCTION" +"/getUser",String.class);
    }
}
