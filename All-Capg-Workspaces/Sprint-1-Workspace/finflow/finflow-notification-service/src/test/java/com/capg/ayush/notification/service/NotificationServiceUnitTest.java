/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.capg.ayush.notification.client.AuthServiceClient;
import com.capg.ayush.notification.dto.NotificationDto;
import com.capg.ayush.notification.entity.NotificationLog;
import com.capg.ayush.notification.messaging.ApplicationStatusChangedEvent;
import com.capg.ayush.notification.messaging.DocumentStatusChangedEvent;
import com.capg.ayush.notification.repository.NotificationLogRepository;

/**
 * Unit tests for {@link NotificationService}.
 * Covers event processing, notification logging, and message building logic.
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class NotificationServiceUnitTest {

    @Mock
    private NotificationLogRepository notificationLogRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private AuthServiceClient authServiceClient;

    @InjectMocks
    private NotificationService notificationService;

    private ApplicationStatusChangedEvent submittedEvent;
    private ApplicationStatusChangedEvent approvedEvent;
    private ApplicationStatusChangedEvent rejectedEvent;

    @BeforeEach
    void setUp() {
        submittedEvent = new ApplicationStatusChangedEvent(1L, 100L, "DRAFT", "SUBMITTED", null);
        approvedEvent = new ApplicationStatusChangedEvent(2L, 100L, "UNDER_REVIEW", "APPROVED", "Approved with terms");
        rejectedEvent = new ApplicationStatusChangedEvent(3L, 100L, "UNDER_REVIEW", "REJECTED", "Low credit score");
    }

    // ==================== processEvent ====================

    @Test
    void processEventSubmittedLogsEmail() {
        when(authServiceClient.getUserEmail(100L)).thenReturn("test@example.com");

        notificationService.processEvent(submittedEvent);

        // Should save 1 email log (SUBMITTED is not critical, no SMS)
        verify(notificationLogRepository, times(1)).save(any(NotificationLog.class));
    }

    @Test
    void processEventApprovedLogsEmailAndSms() {
        when(authServiceClient.getUserEmail(100L)).thenReturn("test@example.com");

        notificationService.processEvent(approvedEvent);

        // APPROVED is critical → should save 2 logs (EMAIL + SMS)
        verify(notificationLogRepository, times(2)).save(any(NotificationLog.class));
    }

    @Test
    void processEventRejectedLogsEmailAndSms() {
        when(authServiceClient.getUserEmail(100L)).thenReturn("test@example.com");

        notificationService.processEvent(rejectedEvent);

        // REJECTED is critical → should save 2 logs (EMAIL + SMS)
        verify(notificationLogRepository, times(2)).save(any(NotificationLog.class));
    }

    @Test
    void processEventDocsPendingLogsEmailOnly() {
        ApplicationStatusChangedEvent docsPending = new ApplicationStatusChangedEvent(4L, 100L, "SUBMITTED", "DOCS_PENDING", null);
        when(authServiceClient.getUserEmail(100L)).thenReturn("test@example.com");

        notificationService.processEvent(docsPending);

        verify(notificationLogRepository, times(1)).save(any(NotificationLog.class));
    }

    @Test
    void processEventNullEmailFallsBackToMock() {
        when(authServiceClient.getUserEmail(100L)).thenReturn(null);

        notificationService.processEvent(submittedEvent);

        // Should still save the log even with null email
        verify(notificationLogRepository, times(1)).save(any(NotificationLog.class));
    }

    @Test
    void processEventEmptyEmailFallsBackToMock() {
        when(authServiceClient.getUserEmail(100L)).thenReturn("");

        notificationService.processEvent(submittedEvent);

        verify(notificationLogRepository, times(1)).save(any(NotificationLog.class));
    }

    @Test
    void processEventMailSenderThrowsFallsBack() {
        when(authServiceClient.getUserEmail(100L)).thenReturn("test@example.com");
        doThrow(new RuntimeException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Should not throw — falls back to mock email
        assertDoesNotThrow(() -> notificationService.processEvent(submittedEvent));
        verify(notificationLogRepository, times(1)).save(any(NotificationLog.class));
    }

    @Test
    void processEventNullUserIdDefaultsToZero() {
        ApplicationStatusChangedEvent event = new ApplicationStatusChangedEvent(5L, null, "DRAFT", "SUBMITTED", null);
        when(authServiceClient.getUserEmail(null)).thenReturn(null);

        notificationService.processEvent(event);

        ArgumentCaptor<NotificationLog> captor = ArgumentCaptor.forClass(NotificationLog.class);
        verify(notificationLogRepository).save(captor.capture());
        assertEquals(0L, captor.getValue().getUserId());
    }

    @Test
    void processEventNullNewStatusHandlesGracefully() {
        ApplicationStatusChangedEvent event = new ApplicationStatusChangedEvent(6L, 100L, "DRAFT", null, null);
        when(authServiceClient.getUserEmail(100L)).thenReturn(null);

        notificationService.processEvent(event);

        ArgumentCaptor<NotificationLog> captor = ArgumentCaptor.forClass(NotificationLog.class);
        verify(notificationLogRepository).save(captor.capture());
        assertEquals("UNKNOWN", captor.getValue().getEventType());
    }

    // ==================== processDocumentEvent ====================

    @Test
    void processDocumentEventRejectedLogsEmail() {
        DocumentStatusChangedEvent event = new DocumentStatusChangedEvent();
        event.setDocumentId(1L);
        event.setApplicationId(10L);
        event.setUserId(100L);
        event.setDocType("ID_PROOF");
        event.setOldStatus("PENDING");
        event.setNewStatus("REJECTED");
        event.setReason("Blurry image");

        when(authServiceClient.getUserEmail(100L)).thenReturn("test@example.com");

        notificationService.processDocumentEvent(event);

        verify(notificationLogRepository, times(1)).save(any(NotificationLog.class));
    }

    @Test
    void processDocumentEventNonRejectedSkipped() {
        DocumentStatusChangedEvent event = new DocumentStatusChangedEvent();
        event.setNewStatus("VERIFIED");

        notificationService.processDocumentEvent(event);

        verify(notificationLogRepository, never()).save(any());
    }

    @Test
    void processDocumentEventNullReasonDefaultMessage() {
        DocumentStatusChangedEvent event = new DocumentStatusChangedEvent();
        event.setDocumentId(1L);
        event.setApplicationId(10L);
        event.setUserId(100L);
        event.setDocType("INCOME_PROOF");
        event.setNewStatus("REJECTED");
        event.setReason(null);

        when(authServiceClient.getUserEmail(100L)).thenReturn(null);

        notificationService.processDocumentEvent(event);

        ArgumentCaptor<NotificationLog> captor = ArgumentCaptor.forClass(NotificationLog.class);
        verify(notificationLogRepository).save(captor.capture());
        assertTrue(captor.getValue().getMessage().contains("Incomplete document"));
    }

    // ==================== getAllNotifications ====================

    @Test
    void getAllNotificationsSuccess() {
        NotificationLog log = new NotificationLog();
        log.setId(1L);
        log.setApplicationId(10L);
        log.setUserId(100L);
        log.setEventType("SUBMITTED");
        log.setChannel("EMAIL");
        log.setMessage("Test");
        log.setSent(true);
        log.setCreatedAt(Instant.now());

        when(notificationLogRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(log));

        List<NotificationDto> result = notificationService.getAllNotifications();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SUBMITTED", result.get(0).getEventType());
    }

    @Test
    void getAllNotificationsEmpty() {
        when(notificationLogRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of());

        List<NotificationDto> result = notificationService.getAllNotifications();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== getNotificationsForApplication ====================

    @Test
    void getNotificationsForApplicationSuccess() {
        NotificationLog log = new NotificationLog();
        log.setId(1L);
        log.setApplicationId(10L);
        log.setUserId(100L);
        log.setEventType("APPROVED");
        log.setChannel("SMS");
        log.setMessage("Congrats");
        log.setSent(true);
        log.setCreatedAt(Instant.now());

        when(notificationLogRepository.findByApplicationIdOrderByCreatedAtDesc(10L)).thenReturn(List.of(log));

        List<NotificationDto> result = notificationService.getNotificationsForApplication(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getApplicationId());
    }
}
