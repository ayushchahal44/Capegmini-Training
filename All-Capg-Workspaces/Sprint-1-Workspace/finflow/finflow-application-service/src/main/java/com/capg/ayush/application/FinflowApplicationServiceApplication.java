/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FinFlow Application Service.
 * This service manages the lifecycle of loan applications.
 */
@SpringBootApplication(scanBasePackages = { "com.capg.ayush.application", "com.capg.ayush.finflow" })
public class FinflowApplicationServiceApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinflowApplicationServiceApplication.class, args);
	}
}
