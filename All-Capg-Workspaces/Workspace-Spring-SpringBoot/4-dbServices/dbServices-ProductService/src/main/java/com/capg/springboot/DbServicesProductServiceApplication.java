package com.capg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class DbServicesProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbServicesProductServiceApplication.class, args);
	    System.out.println("Product Server Start..");
	}

}
