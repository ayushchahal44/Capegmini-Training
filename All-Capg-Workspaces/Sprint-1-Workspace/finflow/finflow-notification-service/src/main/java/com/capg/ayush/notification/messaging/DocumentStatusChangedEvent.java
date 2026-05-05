/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.messaging;

/**
 * Event received when a document status changes.
 */
public class DocumentStatusChangedEvent {
    private Long documentId;
    private Long applicationId;
    private Long userId;
    private String docType;
    private String oldStatus;
    private String newStatus;
    private String reason;

    public DocumentStatusChangedEvent() {}

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }

    public String getOldStatus() { return oldStatus; }
    public void setOldStatus(String oldStatus) { this.oldStatus = oldStatus; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
