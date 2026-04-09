/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.capg.ayush.auth.repo.UserRepository;
import com.capg.ayush.auth.domain.Role;
import com.capg.ayush.auth.domain.User;

/**
 * Configuration class to seed initial administrative user data.
 */
@Configuration
public class AdminUserDataLoader {

	/**
	 * Seeds a default admin user if no user with the admin email exists.
	 * @param users The user repository
	 * @param encoder The password encoder
	 * @return A CommandLineRunner that executes the seeding logic
	 */
	@Bean
	CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder encoder) {
		return args -> {
			String email = "admin@finflow.com";
			if (users.findByEmail(email).isEmpty()) {
				User admin = new User();
				admin.setEmail(email);
				admin.setPasswordHash(encoder.encode("admin123"));
				admin.setFirstName("System");
				admin.setLastName("Admin");
				admin.setRole(Role.ADMIN);
				admin.setEnabled(true);
				users.save(admin);
			}
		};
	}
}
