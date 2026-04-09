/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.messaging;

import com.capg.ayush.document.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Message listener for application status change events.
 * Listens for events on the RabbitMQ queue and performs document-related actions as needed.
 */
@Component
public class ApplicationStatusListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStatusListener.class);

    /**
     * Handles application status changed events.
     * Logs the event and prepares for document uploads if the status is DOCS_PENDING.
     * @param event The event data received from RabbitMQ
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_APP_STATUS)
    public void onApplicationStatusChanged(Map<String, Object> event) {
        if (event == null) return;

        Object appIdObj  = event.get("applicationId");
        Object statusObj = event.get("newStatus");
        Object noteObj   = event.get("note");

        String newStatus  = statusObj  != null ? statusObj.toString()  : "UNKNOWN";
        String note       = noteObj    != null ? noteObj.toString()    : "";
        Object appId      = appIdObj   != null ? appIdObj              : "?";

        log.info("[DocumentService][RabbitMQ] Received event - appId={}, newStatus={}, note={}", appId, newStatus, note);

        if ("DOCS_PENDING".equals(newStatus)) {
            log.info("[DocumentService][RabbitMQ] Application {} is awaiting KYC documents. Ready for document upload.", appId);
        }
    }
}
