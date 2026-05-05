/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.ayush.notification.dto.NotificationDto;
import com.capg.ayush.notification.entity.NotificationLog;
import com.capg.ayush.notification.repository.NotificationLogRepository;
import com.capg.ayush.notification.messaging.ApplicationStatusChangedEvent;
import com.capg.ayush.notification.messaging.DocumentStatusChangedEvent;
import com.capg.ayush.notification.client.AuthServiceClient;

/**
 * Service for processing notification events and managing notification logs.
 * Simulates sending notifications (email/SMS) by logging them.
 */
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationLogRepository notificationLogRepository;
    private final JavaMailSender mailSender;
    private final AuthServiceClient authServiceClient;

    public NotificationService(NotificationLogRepository notificationLogRepository,
                               JavaMailSender mailSender,
                               AuthServiceClient authServiceClient) {
        this.notificationLogRepository = notificationLogRepository;
        this.mailSender = mailSender;
        this.authServiceClient = authServiceClient;
    }

    /**
     * Processes a status change event by logging it and simulating notification dispatch.
     * @param event The status change event received from RabbitMQ
     */
    @Transactional
    public void processEvent(ApplicationStatusChangedEvent event) {
        String message = buildNotificationMessage(event);

        // Simulate EMAIL notification
        sendMockEmail(event.getUserId(), event.getApplicationId(), message);

        // Log the notification
        NotificationLog emailLog = new NotificationLog();
        emailLog.setApplicationId(event.getApplicationId());
        emailLog.setUserId(event.getUserId() != null ? event.getUserId() : 0L);
        emailLog.setEventType(event.getNewStatus() != null ? event.getNewStatus() : "UNKNOWN");
        emailLog.setChannel("EMAIL");
        emailLog.setMessage(message);
        emailLog.setSent(true);
        notificationLogRepository.save(emailLog);

        // Simulate SMS notification for critical events
        if (isCriticalEvent(event.getNewStatus())) {
            sendMockSms(event.getUserId(), message);

            NotificationLog smsLog = new NotificationLog();
            smsLog.setApplicationId(event.getApplicationId());
            smsLog.setUserId(event.getUserId() != null ? event.getUserId() : 0L);
            smsLog.setEventType(event.getNewStatus() != null ? event.getNewStatus() : "UNKNOWN");
            smsLog.setChannel("SMS");
            smsLog.setMessage("SMS: " + message);
            smsLog.setSent(true);
            notificationLogRepository.save(smsLog);
        }
    }

    @Transactional
    public void processDocumentEvent(DocumentStatusChangedEvent event) {
        if (!"REJECTED".equals(event.getNewStatus())) {
            return; // Only notify rejections for now
        }

        String message = String.format("Action Required: Your %s for application #%d was rejected. Reason: %s. Please re-upload.", 
            event.getDocType().replace("_", " "), event.getApplicationId(), event.getReason() != null ? event.getReason() : "Incomplete document");

        // Simulate EMAIL notification
        sendMockEmail(event.getUserId(), event.getApplicationId(), message);

        // Log the notification
        NotificationLog emailLog = new NotificationLog();
        emailLog.setApplicationId(event.getApplicationId());
        emailLog.setUserId(event.getUserId() != null ? event.getUserId() : 0L);
        emailLog.setEventType("DOC_REJECTED");
        emailLog.setChannel("EMAIL");
        emailLog.setMessage(message);
        emailLog.setSent(true);
        notificationLogRepository.save(emailLog);
    }

    /**
     * Retrieves all notification logs ordered by most recent.
     * @return List of notification DTOs
     */
    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotifications() {
        return notificationLogRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Retrieves notifications for a specific application.
     * @param applicationId The application ID
     * @return List of notification DTOs
     */
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsForApplication(Long applicationId) {
        return notificationLogRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private String buildNotificationMessage(ApplicationStatusChangedEvent event) {
        String status = event.getNewStatus() != null ? event.getNewStatus() : "UNKNOWN";
        switch (status) {
            case "SUBMITTED":
                return String.format("Your loan application #%d has been submitted successfully. We will review it shortly.", event.getApplicationId());
            case "DOCS_PENDING":
                return String.format("Documents are required for your loan application #%d. Please upload your KYC documents.", event.getApplicationId());
            case "DOCS_VERIFIED":
                return String.format("All documents for application #%d have been verified. Your application is now under review.", event.getApplicationId());
            case "UNDER_REVIEW":
                return String.format("Your loan application #%d is currently under manual review by our team.", event.getApplicationId());
            case "APPROVED":
                return String.format("🎉 Congratulations! Your loan application #%d has been APPROVED. %s", event.getApplicationId(), event.getNote() != null ? event.getNote() : "");
            case "REJECTED":
                return String.format("We regret to inform you that your loan application #%d has been rejected. %s", event.getApplicationId(), event.getNote() != null ? event.getNote() : "");
            default:
                return String.format("Status update for your loan application #%d: %s", event.getApplicationId(), status);
        }
    }

    private boolean isCriticalEvent(String status) {
        return "APPROVED".equals(status) || "REJECTED".equals(status);
    }

    private void sendMockEmail(Long userId, Long applicationId, String message) {
        String emailAddress = authServiceClient.getUserEmail(userId);
        if (emailAddress == null || emailAddress.isEmpty()) {
            log.warn("Could not find email address for userId: {}. Falling back to mock email.", userId);
            log.info("📧 [MOCK EMAIL] To: user#{} | Subject: Loan Application #{} Update | Body: {}",
                    userId, applicationId, message);
            return;
        }

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailAddress);
            mailMessage.setSubject("FinFlow Loan Application #" + applicationId + " Update");
            mailMessage.setText(message);
            mailSender.send(mailMessage);
            log.info("📧 Successfully sent email to: {} for application #{}", emailAddress, applicationId);
        } catch (Exception e) {
            log.error("❌ Failed to send SMTP email to {}: {}", emailAddress, e.getMessage());
            log.info("📧 [FALLBACK MOCK EMAIL] To: {} | Subject: Loan Application #{} Update | Body: {}",
                    emailAddress, applicationId, message);
        }
    }

    private void sendMockSms(Long userId, String message) {
        log.info("📱 [MOCK SMS] To: user#{} | Message: {}", userId, message);
    }

    private NotificationDto toDto(NotificationLog n) {
        NotificationDto dto = new NotificationDto();
        dto.setId(n.getId());
        dto.setApplicationId(n.getApplicationId());
        dto.setUserId(n.getUserId());
        dto.setEventType(n.getEventType());
        dto.setChannel(n.getChannel());
        dto.setMessage(n.getMessage());
        dto.setSent(n.isSent());
        dto.setCreatedAt(n.getCreatedAt());
        return dto;
    }
}
