/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FinFlow Document Service.
 * This service handles storage, retrieval, and verification of KYC documents.
 */
@SpringBootApplication(scanBasePackages = { "com.capg.ayush.document", "com.capg.ayush.finflow" })
public class FinflowDocumentServiceApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinflowDocumentServiceApplication.class, args);
	}
}
