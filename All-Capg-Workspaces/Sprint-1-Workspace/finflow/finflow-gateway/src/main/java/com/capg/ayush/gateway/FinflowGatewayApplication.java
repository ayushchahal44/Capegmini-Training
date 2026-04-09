/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FinFlow API Gateway.
 * This service acts as the central entry point for all client requests,
 * providing routing, security, and load balancing.
 */
@SpringBootApplication(scanBasePackages = { "com.capg.ayush.gateway", "com.capg.ayush.finflow" })
public class FinflowGatewayApplication {

	/**
	 * Main method to start the Spring Boot application (Gateway).
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinflowGatewayApplication.class, args);
	}

}
