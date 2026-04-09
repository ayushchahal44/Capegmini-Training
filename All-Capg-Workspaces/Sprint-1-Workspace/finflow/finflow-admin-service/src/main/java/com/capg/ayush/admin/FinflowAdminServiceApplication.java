/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FinFlow Admin Service.
 * This service aggregates administrative operations across the microservice stack.
 */
@SpringBootApplication(scanBasePackages = { "com.capg.ayush.admin", "com.capg.ayush.finflow" })
public class FinflowAdminServiceApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinflowAdminServiceApplication.class, args);
	}
}
