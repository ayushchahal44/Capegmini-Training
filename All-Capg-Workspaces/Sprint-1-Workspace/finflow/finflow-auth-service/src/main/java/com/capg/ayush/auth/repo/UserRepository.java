/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.ayush.auth.domain.User;

/**
 * Repository interface for {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Finds a user by their email address.
	 * @param email The email to search for
	 * @return An Optional containing the user if found
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Checks if a user already exists with the given email.
	 * @param email The email to check
	 * @return true if a user exists, false otherwise
	 */
	boolean existsByEmail(String email);
}
