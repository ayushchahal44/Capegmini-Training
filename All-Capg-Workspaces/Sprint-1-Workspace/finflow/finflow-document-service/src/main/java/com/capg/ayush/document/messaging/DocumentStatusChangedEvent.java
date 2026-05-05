/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.messaging;

import com.capg.ayush.document.entity.DocStatus;
import com.capg.ayush.document.entity.DocType;

/**
 * Event published when a document status changes (Verified/Rejected).
 */
public class DocumentStatusChangedEvent {
    private Long documentId;
    private Long applicationId;
    private Long userId;
    private DocType docType;
    private DocStatus oldStatus;
    private DocStatus newStatus;
    private String reason;

    public DocumentStatusChangedEvent() {}

    public DocumentStatusChangedEvent(Long documentId, Long applicationId, Long userId, DocType docType, 
                                     DocStatus oldStatus, DocStatus newStatus, String reason) {
        this.documentId = documentId;
        this.applicationId = applicationId;
        this.userId = userId;
        this.docType = docType;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
    }

    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public DocType getDocType() { return docType; }
    public void setDocType(DocType docType) { this.docType = docType; }

    public DocStatus getOldStatus() { return oldStatus; }
    public void setOldStatus(DocStatus oldStatus) { this.oldStatus = oldStatus; }

    public DocStatus getNewStatus() { return newStatus; }
    public void setNewStatus(DocStatus newStatus) { this.newStatus = newStatus; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
