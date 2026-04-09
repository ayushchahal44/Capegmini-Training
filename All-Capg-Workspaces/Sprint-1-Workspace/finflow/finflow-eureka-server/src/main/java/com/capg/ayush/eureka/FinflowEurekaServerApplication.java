/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main entry point for the FinFlow Eureka Server.
 * This service provides service discovery capabilities for the microservices architecture.
 */
@SpringBootApplication
@EnableEurekaServer
public class FinflowEurekaServerApplication {

	/**
	 * Main method to start the Spring Boot application (Eureka Server).
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinflowEurekaServerApplication.class, args);
	}
}
