package com.capg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * Main class for Eureka Server
 * This server acts as a Service Registry
 */
@EnableEurekaServer // Enables Eureka Server functionality
@SpringBootApplication
public class ECommerceEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceEurekaServerApplication.class, args);
	    System.out.println("eCommerce Eureka Server Start");
	}

}
