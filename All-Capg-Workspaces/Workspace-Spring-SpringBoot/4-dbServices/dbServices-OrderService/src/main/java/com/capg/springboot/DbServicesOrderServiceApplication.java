package com.capg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.capg.springboot.client")
public class DbServicesOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbServicesOrderServiceApplication.class, args);
	System.out.println("Order Service Server Start..");
	}

}
