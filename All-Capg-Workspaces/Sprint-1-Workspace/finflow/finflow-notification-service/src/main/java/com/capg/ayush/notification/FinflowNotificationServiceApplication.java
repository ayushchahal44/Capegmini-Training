/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FinFlow Notification Service.
 * Consumes RabbitMQ events and logs/sends mock email notifications.
 */
@SpringBootApplication(scanBasePackages = { "com.capg.ayush.notification", "com.capg.ayush.finflow" })
public class FinflowNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinflowNotificationServiceApplication.class, args);
	}
}
