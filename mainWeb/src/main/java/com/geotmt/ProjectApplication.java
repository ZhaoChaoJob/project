package com.geotmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 项目启动入口，使用spring-boot的maven插件
 */
@SpringBootApplication
public class ProjectApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	// ServletInitializer
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProjectApplication.class);
	}
}
