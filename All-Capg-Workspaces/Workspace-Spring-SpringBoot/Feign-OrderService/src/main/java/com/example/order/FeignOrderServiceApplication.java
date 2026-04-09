package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients(basePackages = "com.example.order.client")
@SpringBootApplication
public class FeignOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignOrderServiceApplication.class, args);
		System.out.println("Spring  Order Server..");
	}

}
