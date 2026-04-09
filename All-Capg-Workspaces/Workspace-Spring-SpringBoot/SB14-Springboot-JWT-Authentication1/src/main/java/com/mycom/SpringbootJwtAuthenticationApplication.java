package com.mycom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class SpringbootJwtAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJwtAuthenticationApplication.class, args);
		System.out.println("Spring Boot Server Started...!");
	}

}
