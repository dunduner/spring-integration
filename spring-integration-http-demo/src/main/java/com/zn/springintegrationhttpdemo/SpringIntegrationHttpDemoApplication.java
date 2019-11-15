package com.zn.springintegrationhttpdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//引入配置文件
@ImportResource(locations = "classpath:http-inbound-config.xml")
public class SpringIntegrationHttpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationHttpDemoApplication.class, args);
	}

}
