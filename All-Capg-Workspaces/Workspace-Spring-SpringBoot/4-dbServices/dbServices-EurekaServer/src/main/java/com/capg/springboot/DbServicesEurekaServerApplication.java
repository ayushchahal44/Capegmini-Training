package com.capg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer   // Enables Eureka Registry
@SpringBootApplication
public class DbServicesEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbServicesEurekaServerApplication.class, args);
	    System.out.println("EurekaServer Start");
	}

}
