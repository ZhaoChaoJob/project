package com.geotmt.rong360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Created by geo on 2018/9/28. */
@SpringBootApplication
@ImportResource({"classpath:config/spring-dubbo.xml"})
public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        System.in.read();
    }
}
