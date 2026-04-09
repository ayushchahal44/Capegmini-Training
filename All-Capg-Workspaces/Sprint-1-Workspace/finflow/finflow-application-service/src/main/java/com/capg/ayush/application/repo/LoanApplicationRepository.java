/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.ayush.application.domain.ApplicationStatus;
import com.capg.ayush.application.domain.LoanApplication;

/**
 * Repository interface for {@link LoanApplication} entities.
 */
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

	/**
	 * Finds all loan applications submitted by a specific user.
	 * @param userId The ID of the user
	 * @return A list of loan applications
	 */
	List<LoanApplication> findByUserIdOrderByCreatedAtDesc(Long userId);

	/**
	 * Counts loan applications by their current status.
	 * @param status The status to count
	 * @return The count of applications
	 */
	long countByStatus(ApplicationStatus status);

	/**
	 * Retrieves all loan applications ordered by their last update time.
	 * @return A list of all loan applications
	 */
	List<LoanApplication> findAllByOrderByUpdatedAtDesc();
}
