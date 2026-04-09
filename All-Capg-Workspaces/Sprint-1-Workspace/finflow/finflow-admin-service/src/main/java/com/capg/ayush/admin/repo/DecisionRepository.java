/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.ayush.admin.domain.Decision;

/**
 * Repository interface for {@link Decision} entities.
 */
public interface DecisionRepository extends JpaRepository<Decision, Long> {

	/**
	 * Counts the number of decisions based on their approval status.
	 * @param approved Whether to count approved or rejected decisions
	 * @return The count of decisions
	 */
	long countByApproved(boolean approved);
}
