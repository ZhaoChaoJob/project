package com.geotmt.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * https://blog.csdn.net/ysk_xh_521/article/details/80633141
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    @Profile(value = {"dev","test"})
    public Docket devCreateRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.geotmt.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // 生产就关掉吧
    @Bean
    @Profile(value = {"pro"})
    public Docket proCreateRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.geotmt.admin.controller"))
                .paths(PathSelectors.none())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("赵超新架构系统")
                .description("牛逼，双击666！")
                .termsOfServiceUrl("http:/zhaochao.com/")
                //.contact(contact)
                .version("1.1")
                .build();
    }
}
