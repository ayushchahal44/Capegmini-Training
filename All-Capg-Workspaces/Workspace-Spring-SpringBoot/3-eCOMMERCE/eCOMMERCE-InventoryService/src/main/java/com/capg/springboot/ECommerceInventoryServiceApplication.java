package com.capg.springboot;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Main class for Inventory Service
 */

//@EnableFeignClients 
@SpringBootApplication
public class ECommerceInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceInventoryServiceApplication.class, args);
	   System.out.println("SpringBoot Inventory Server Start");
	}

}
