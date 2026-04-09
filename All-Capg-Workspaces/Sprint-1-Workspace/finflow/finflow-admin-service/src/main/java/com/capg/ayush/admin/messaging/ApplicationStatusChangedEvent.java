/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.messaging;

import com.capg.ayush.admin.domain.ApplicationStatus;


/**
 * Event representing a change in the status of a loan application.
 * Used for messaging between services.
 */
public class ApplicationStatusChangedEvent {

    private Long applicationId;
    private Long userId;
    private ApplicationStatus previousStatus;
    private ApplicationStatus newStatus;
    private String note;

    /**
     * Default constructor for Jackson deserialization.
     */
    public ApplicationStatusChangedEvent() {}

    /**
     * Constructs a new ApplicationStatusChangedEvent with full details.
     * @param applicationId The ID of the application
     * @param userId The ID of the user associated with the change
     * @param previousStatus The status before the change
     * @param newStatus The status after the change
     * @param note An optional note describing the change
     */
    public ApplicationStatusChangedEvent(Long applicationId, Long userId,
                                          ApplicationStatus previousStatus,
                                          ApplicationStatus newStatus,
                                          String note) {
        this.applicationId  = applicationId;
        this.userId         = userId;
        this.previousStatus = previousStatus;
        this.newStatus      = newStatus;
        this.note           = note;
    }

    public Long getApplicationId()  { return applicationId; }
    public Long getUserId()          { return userId; }
    public ApplicationStatus getPreviousStatus() { return previousStatus; }
    public ApplicationStatus getNewStatus()       { return newStatus; }
    public String getNote()          { return note; }

    public void setApplicationId(Long applicationId)   { this.applicationId = applicationId; }
    public void setUserId(Long userId)                  { this.userId = userId; }
    public void setPreviousStatus(ApplicationStatus s)  { this.previousStatus = s; }
    public void setNewStatus(ApplicationStatus s)       { this.newStatus = s; }
    public void setNote(String note)                    { this.note = note; }
}
