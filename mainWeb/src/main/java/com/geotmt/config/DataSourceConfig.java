package com.geotmt.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;

/**
 * 数据源配置
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;


    /**
     * 本地开发环境
     * 说明：生产环境写死
     *
     * @return 数据源
     */
    @Bean
    @Profile(value = {"dev"})
    public DataSource DevDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    /**
     * 测试开发环境
     * 说明：生产环境因常年不动，故写死
     *
     * @return 数据源
     */
    @Bean
    @Profile(value = {"test"})
    public DataSource TestDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("写死测试环境");
        dataSource.setUsername("写死测试环境");
        dataSource.setPassword("写死测试环境");
        return dataSource;
    }

    /**
     * 生产开发环境
     * 说明：生产环境因常年不动，故写死
     *
     * @return 数据源
     */
    @Bean
    @Profile(value = {"pro"})
    public DataSource ProDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

}
