package com.geotmt.config;

import com.geotmt.common.config.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 引用common中的配置
 *
 * Created by chao.zhao on 2018/11/7. */
@Import(value = {DataSourceConfig.class,
        MongoConfig.class,
        MyBatisConfig.class,
        MyBatisMapperScannerConfig.class,
        RedisConfig.class,
        PageHelperConfig.class,
        RedisConfig.class,
        SpringAsyncConfig.class,
        SpringJdbcConfig.class,
        SpringJpaConfig.class,
        SwaggerConfig.class,
        WebMvcConfig.class})
@Configuration
public class BaseConfig {

}
