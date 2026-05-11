package com.capg.ayush.notification.messaging;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capg.ayush.notification.service.NotificationService;

/**
 * Unit tests for {@link StatusEventListener}.
 * Validates RabbitMQ listener delegates to NotificationService.
 */
@ExtendWith(MockitoExtension.class)
class StatusEventListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private StatusEventListener listener;

    @Test
    void handleStatusEventDelegatesToService() {
        ApplicationStatusChangedEvent event = new ApplicationStatusChangedEvent(1L, 100L, "DRAFT", "SUBMITTED", null);

        listener.handleStatusEvent(event);

        verify(notificationService).processEvent(event);
    }

    @Test
    void handleStatusEventServiceThrowsNoRethrow() {
        ApplicationStatusChangedEvent event = new ApplicationStatusChangedEvent(1L, 100L, "DRAFT", "SUBMITTED", null);
        doThrow(new RuntimeException("DB error")).when(notificationService).processEvent(event);

        // Should not throw — listener catches exceptions
        listener.handleStatusEvent(event);

        verify(notificationService).processEvent(event);
    }

    @Test
    void handleDocumentEventDelegatesToService() {
        DocumentStatusChangedEvent event = new DocumentStatusChangedEvent();
        event.setDocumentId(1L);
        event.setApplicationId(10L);
        event.setNewStatus("REJECTED");

        listener.handleDocumentEvent(event);

        verify(notificationService).processDocumentEvent(event);
    }

    @Test
    void handleDocumentEventServiceThrowsNoRethrow() {
        DocumentStatusChangedEvent event = new DocumentStatusChangedEvent();
        event.setDocumentId(1L);
        doThrow(new RuntimeException("DB error")).when(notificationService).processDocumentEvent(event);

        // Should not throw — listener catches exceptions
        listener.handleDocumentEvent(event);

        verify(notificationService).processDocumentEvent(event);
    }
}
