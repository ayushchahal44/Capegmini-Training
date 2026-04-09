/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.ayush.document.domain.DocStatus;
import com.capg.ayush.document.domain.DocType;
import com.capg.ayush.document.domain.DocumentEntity;

/**
 * Repository interface for {@link DocumentEntity} entities.
 */
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

	/**
	 * Finds all documents associated with a specific loan application, ordered by creation date (newest first).
	 * @param applicationId The ID of the loan application
	 * @return A list of matching DocumentEntity objects
	 */
	List<DocumentEntity> findByApplicationIdOrderByCreatedAtDesc(Long applicationId);

	/**
	 * Checks if a verified document of a specific type exists for an application.
	 * @param applicationId The ID of the loan application
	 * @param docType The type of document
	 * @param status The status (usually VERIFIED)
	 * @return true if a matching document exists, false otherwise
	 */
	boolean existsByApplicationIdAndDocTypeAndStatus(Long applicationId, DocType docType, DocStatus status);
}
