/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.messaging;

import java.io.Serializable;

/**
 * DTO representing an application status change event received from RabbitMQ.
 */
public class ApplicationStatusChangedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long applicationId;
    private Long userId;
    private String previousStatus;
    private String newStatus;
    private String note;

    public ApplicationStatusChangedEvent() {}

    public ApplicationStatusChangedEvent(Long applicationId, Long userId, String previousStatus, String newStatus, String note) {
        this.applicationId = applicationId;
        this.userId = userId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.note = note;
    }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPreviousStatus() { return previousStatus; }
    public void setPreviousStatus(String previousStatus) { this.previousStatus = previousStatus; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return "ApplicationStatusChangedEvent{" +
                "applicationId=" + applicationId +
                ", userId=" + userId +
                ", previousStatus='" + previousStatus + '\'' +
                ", newStatus='" + newStatus + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
