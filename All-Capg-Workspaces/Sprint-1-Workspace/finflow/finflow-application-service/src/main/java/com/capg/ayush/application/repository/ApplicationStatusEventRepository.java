/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.ayush.application.entity.ApplicationStatusEvent;

/**
 * Repository interface for {@link ApplicationStatusEvent} entities.
 */
public interface ApplicationStatusEventRepository extends JpaRepository<ApplicationStatusEvent, Long> {

	/**
	 * Retrieves all status history for a given application, ordered by occurrence.
	 * @param applicationId The ID of the application
	 * @return A list of status events
	 */
	List<ApplicationStatusEvent> findByApplication_IdOrderByOccurredAtAsc(Long applicationId);
}
