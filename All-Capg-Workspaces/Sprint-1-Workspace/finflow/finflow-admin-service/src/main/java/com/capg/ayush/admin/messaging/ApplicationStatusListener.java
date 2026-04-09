/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.capg.ayush.admin.config.RabbitMQConfig;

/**
 * Listener for application status change events from RabbitMQ.
 * Audits status changes for administrative oversight.
 */
@Component
public class ApplicationStatusListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStatusListener.class);

    /**
     * Handles incoming application status change events.
     * @param event The event details received from the queue
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_APP_STATUS)
    public void handleStatusChange(ApplicationStatusChangedEvent event) {
        log.info("[Admin-Audit] Application {} status changed: {} -> {} (Note: {})",
                event.getApplicationId(),
                event.getPreviousStatus(),
                event.getNewStatus(),
                event.getNote());
        
        
        
    }
}
