package com.capg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Main class for Product Service
 */
@SpringBootApplication
public class ECommerceProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceProductServiceApplication.class, args);
	    System.out.println("Product Service Start");
	}

}
