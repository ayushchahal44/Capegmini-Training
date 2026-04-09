/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FinFlow Auth Service.
 * This service handles user authentication, registration, and management.
 */
@SpringBootApplication(scanBasePackages = { "com.capg.ayush.auth", "com.capg.ayush.finflow" })
public class FinflowAuthServiceApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinflowAuthServiceApplication.class, args);
	}
}
