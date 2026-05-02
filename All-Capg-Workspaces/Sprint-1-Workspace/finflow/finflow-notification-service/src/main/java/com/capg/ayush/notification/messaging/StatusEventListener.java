/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.capg.ayush.notification.service.NotificationService;

/**
 * RabbitMQ listener that consumes application status change events.
 * Delegates to the NotificationService for processing and logging.
 */
@Component
public class StatusEventListener {

    private static final Logger log = LoggerFactory.getLogger(StatusEventListener.class);

    private final NotificationService notificationService;

    public StatusEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Consumes application status events from the notification queue.
     * @param event The deserialized status change event
     */
    @RabbitListener(queues = "finflow.notifications")
    public void handleStatusEvent(ApplicationStatusChangedEvent event) {
        log.info("📩 Received status event: {}", event);
        try {
            notificationService.processEvent(event);
            log.info("✅ Notification processed for application {} -> {}", event.getApplicationId(), event.getNewStatus());
        } catch (Exception e) {
            log.error("❌ Failed to process notification for application {}: {}", event.getApplicationId(), e.getMessage(), e);
        }
    }
}
