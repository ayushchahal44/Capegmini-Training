package com.mycom.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@ComponentScan(basePackages = { "com.mycom.springboot.controller" })

public class SpringbootSecurityHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityHelloApplication.class, args);
	System.out.println("Spring Boot Server Start..");
	}

}
