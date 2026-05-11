/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.ayush.notification.entity.NotificationLog;

/**
 * Repository for NotificationLog entities.
 */
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByApplicationIdOrderByCreatedAtDesc(Long applicationId);


    List<NotificationLog> findAllByOrderByCreatedAtDesc();

    long countByEventType(String eventType);
}
