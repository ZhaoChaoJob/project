package com.geotmt.eureka.server;

import org.springframework.stereotype.Service;

/**
 * Created by geo on 2018/10/9.
 */
@Service
public class UserService {
    public String fallback(Long id) {
        return "服务端返回："+id;
    }
}
