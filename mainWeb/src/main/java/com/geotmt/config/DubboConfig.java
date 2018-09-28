package com.geotmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;


/**
 * dubbo 配置
 *
 * Created by geo on 2018/9/28. */
@Configuration
public class DubboConfig {

    @Profile(value = {"dev"})
    @ImportResource({"classpath:config/spring-dubbo.xml"})
    public class DevDubboConfig{
    }

    @Profile(value = {"test"})
    @ImportResource({"classpath:config/spring-dubbo.xml"})
    public class TestDubboConfig{
    }

    @Profile(value = {"pro"})
    @ImportResource({"classpath:config/spring-dubbo.xml"})
    public class ProDubboConfig{
    }
}
