import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, FileText, Download, Eye, CheckCircle, XCircle, RefreshCcw, Info, Search, Clock, Save } from 'lucide-angular';
import { DocumentInfo } from '../../../../core/models/admin.model';
import { DocumentService } from '../../../../core/services/document.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-document-panel',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="glass-card document-panel-card">
      <div class="card-header">
        <h3 class="card-title">
          <lucide-icon [name]="FileIcon" [size]="20"></lucide-icon>
          Document Verification
        </h3>
      </div>
      
      <div class="card-content">
        @if (documents.length === 0) {
          <div class="empty-state">
            <lucide-icon [name]="FileIcon" [size]="48" class="empty-icon"></lucide-icon>
            <h3>No Documents Uploaded</h3>
            <p>Applicant has not uploaded any documents yet.</p>
          </div>
        } @else {
          <div class="doc-layout">
            <div class="doc-sidebar">
              @for (doc of documents; track doc.id; let i = $index) {
                <div class="doc-nav-item" [class.active]="selectedDocIndex === i" (click)="selectDocument(i)">
                  <div class="doc-nav-info">
                    <span class="doc-nav-type">{{getDocumentTypeLabel(doc.docType)}}</span>
                    <span class="doc-nav-name" [title]="doc.originalName">{{doc.originalName}}</span>
                  </div>
                  <lucide-icon [name]="getDocumentStatusIcon(doc.status)" [size]="14" [class]="doc.status.toLowerCase()"></lucide-icon>
                </div>
              }
            </div>

            <div class="doc-main">
              @if (documents[selectedDocIndex]; as doc) {
                <div class="document-details fade-in">
                  <div class="doc-header-main">
                    <div class="doc-meta-info">
                      <h2>{{doc.originalName}}</h2>
                      <span class="upload-info">Uploaded on {{doc.createdAt | date:'medium'}}</span>
                    </div>
                    <div class="doc-actions-top">
                      <button (click)="downloadDocument(doc)" class="btn-icon" title="Download">
                        <lucide-icon [name]="DownloadIcon" [size]="18"></lucide-icon>
                      </button>
                    </div>
                  </div>

                  <div class="document-preview-box">
                    @if (previewUrl) {
                      <iframe [src]="previewUrl" class="preview-iframe"></iframe>
                    } @else {
                      <div class="preview-overlay">
                        <lucide-icon [name]="FileIcon" [size]="64"></lucide-icon>
                        <p>Document Preview Available</p>
                        <button class="btn btn-secondary btn-sm" (click)="previewDocument(doc)">
                          <lucide-icon [name]="EyeIcon" [size]="16"></lucide-icon> Load Preview
                        </button>
                      </div>
                    }
                  </div>

                  <div class="verification-controls">
                    <div class="current-status-box" [class]="doc.status.toLowerCase()">
                      <lucide-icon [name]="getDocumentStatusIcon(doc.status)" [size]="18"></lucide-icon>
                      <span>Status: {{doc.status}}</span>
                    </div>

                    @if (doc.status === 'PENDING') {
                      <div class="action-buttons">
                        <button class="btn btn-success" (click)="verifyDocument(doc, true)">
                          <lucide-icon [name]="CheckIcon" [size]="18"></lucide-icon> Verify
                        </button>
                        <button class="btn btn-danger" (click)="rejectDocument(doc)">
                          <lucide-icon [name]="XIcon" [size]="18"></lucide-icon> Reject
                        </button>
                        <button class="btn btn-warning" (click)="requestReupload(doc)">
                          <lucide-icon [name]="RefreshIcon" [size]="18"></lucide-icon> Re-upload
                        </button>
                      </div>
                    }

                    @if (doc.status === 'REJECTED') {
                      <div class="rejection-note-form fade-in">
                        <label>Rejection Reason</label>
                        <textarea 
                          placeholder="Explain why this document was rejected..." 
                          #reasonText
                          [value]="doc.rejectionReason || ''"></textarea>
                        
                        <div class="note-actions">
                          <button 
                            class="btn btn-primary btn-sm" 
                            (click)="saveRejectionReason(doc, reasonText.value)"
                            [disabled]="reasonText.value === doc.rejectionReason">
                            <lucide-icon [name]="SaveIcon" [size]="14"></lucide-icon>
                            {{ doc.rejectionReason ? 'Update Note' : 'Save Note' }}
                          </button>
                          
                          @if (doc.rejectionReason) {
                            <span class="save-badge">
                              <lucide-icon [name]="CheckIcon" [size]="14"></lucide-icon>
                              Reason Saved
                            </span>
                          }
                        </div>
                      </div>
                    }
                  </div>
                </div>
              }
            </div>
          </div>

          <div class="verification-summary">
            <div class="summary-item verified">
              <span class="count">{{getVerifiedCount()}}</span>
              <span class="label">Verified</span>
            </div>
            <div class="summary-item pending">
              <span class="count">{{getPendingCount()}}</span>
              <span class="label">Pending</span>
            </div>
            <div class="summary-item rejected">
              <span class="count">{{getRejectedCount()}}</span>
              <span class="label">Rejected</span>
            </div>
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    .document-panel-card { padding: 2rem; border-radius: var(--radius-lg); height: 100%; }
    .card-title { display: flex; align-items: center; gap: 0.75rem; font-size: 1.25rem; font-weight: 800; color: var(--text-primary); }
    
    .doc-layout { display: flex; gap: 2rem; margin-bottom: 2.5rem; min-height: 550px; }
    
    .doc-sidebar { width: 280px; display: flex; flex-direction: column; gap: 0.75rem; border-right: 1px solid var(--border); padding-right: 1.5rem; }
    .doc-nav-item { 
      padding: 1rem 1.25rem; border-radius: var(--radius-md); border: 1px solid transparent; 
      cursor: pointer; display: flex; align-items: center; justify-content: space-between; transition: var(--transition);
      background: rgba(255,255,255,0.02);
    }
    .doc-nav-item:hover { background: rgba(255,255,255,0.05); border-color: var(--border); }
    .doc-nav-item.active { background: var(--accent-soft); border-color: var(--accent); }
    
    .doc-nav-info { display: flex; flex-direction: column; gap: 2px; }
    .doc-nav-type { font-size: 0.7rem; font-weight: 800; text-transform: uppercase; color: var(--text-muted); letter-spacing: 0.5px; }
    .doc-nav-name { font-size: 0.85rem; font-weight: 600; color: var(--text-primary); overflow: hidden; text-overflow: ellipsis; white-space: nowrap; max-width: 180px; }
    
    .doc-main { flex: 1; padding-left: 0.5rem; }
    .doc-header-main { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 2rem; }
    .doc-meta-info h2 { font-size: 1.5rem; font-weight: 800; color: var(--text-primary); margin-bottom: 4px; }
    .upload-info { font-size: 0.8rem; color: var(--text-muted); }
    
    .document-preview-box { 
      background: var(--bg-deep); border-radius: var(--radius-md); border: 1px solid var(--border);
      height: 450px; display: flex; align-items: center; justify-content: center; position: relative;
      overflow: hidden; margin-bottom: 2rem; box-shadow: inset 0 0 20px rgba(0,0,0,0.5);
    }
    .preview-iframe { width: 100%; height: 100%; border: none; background: white; }
    .preview-overlay { display: flex; flex-direction: column; align-items: center; gap: 1.5rem; color: var(--text-muted); }
    .preview-overlay p { font-weight: 600; }
    
    .verification-controls { display: flex; flex-direction: column; gap: 1.5rem; }
    .current-status-box { 
      display: flex; align-items: center; gap: 0.75rem; padding: 1rem 1.5rem; border-radius: var(--radius-md);
      font-weight: 700; text-transform: uppercase; font-size: 0.85rem; width: fit-content;
    }
    .current-status-box.pending { background: rgba(245,158,11,0.1); color: var(--warning); }
    .current-status-box.verified { background: rgba(16,185,129,0.1); color: var(--success); }
    .current-status-box.rejected { background: rgba(239, 68, 68, 0.1); color: var(--danger); }
    
    .action-buttons { display: flex; gap: 1rem; }
    .rejection-note-form { 
      display: flex; flex-direction: column; gap: 1rem; padding: 1.5rem; 
      background: rgba(239, 68, 68, 0.05); border-radius: var(--radius-md); border: 1px solid rgba(239, 68, 68, 0.1);
    }
    .rejection-note-form label { font-size: 0.75rem; font-weight: 800; text-transform: uppercase; color: var(--danger); }
    .rejection-note-form textarea { 
      background: var(--bg-deep); border: 1px solid var(--border); border-radius: var(--radius-sm);
      padding: 1rem; color: var(--text-primary); font-family: inherit; min-height: 100px; resize: none;
    }
    
    .verification-summary { 
      display: flex; justify-content: center; gap: 4rem; padding: 2rem; 
      background: rgba(0,0,0,0.2); border-radius: var(--radius-md); border: 1px solid var(--border);
    }
    .summary-item { display: flex; flex-direction: column; align-items: center; gap: 4px; }
    .summary-item .count { font-size: 1.75rem; font-weight: 800; }
    .summary-item .label { font-size: 0.75rem; font-weight: 700; text-transform: uppercase; color: var(--text-muted); }
    .summary-item.verified .count { color: var(--success); }
    .summary-item.pending .count { color: var(--warning); }
    .summary-item.rejected .count { color: var(--danger); }
    
    .btn-success { background: var(--success); color: white; }
    .btn-danger { background: var(--danger); color: white; }
    .btn-warning { background: var(--warning); color: white; }
    .btn-ghost { background: transparent; border: 1px solid var(--border); color: var(--text-secondary); }
    .btn-ghost:hover { background: rgba(255,255,255,0.05); color: var(--text-primary); }

    .pending { color: var(--warning); }
    .verified { color: var(--success); }
    .rejected { color: var(--danger); }

    .note-actions { display: flex; align-items: center; gap: 1.5rem; margin-top: 0.5rem; }
    .save-badge { display: flex; align-items: center; gap: 6px; font-size: 0.75rem; font-weight: 700; color: var(--success); background: rgba(16, 185, 129, 0.1); padding: 4px 10px; border-radius: 100px; }
  `]
})
export class DocumentPanelComponent {
  @Input() documents: DocumentInfo[] = [];
  @Output() documentVerified = new EventEmitter<{document: DocumentInfo, verified: boolean}>();
  @Output() documentRejected = new EventEmitter<DocumentInfo>();
  @Output() reuploadRequested = new EventEmitter<DocumentInfo>();

  private docService = inject(DocumentService);
  private sanitizer = inject(DomSanitizer);

  selectedDocIndex = 0;
  previewUrl: SafeResourceUrl | null = null;

  readonly FileIcon = FileText;
  readonly DownloadIcon = Download;
  readonly EyeIcon = Eye;
  readonly CheckIcon = CheckCircle;
  readonly XIcon = XCircle;
  readonly RefreshIcon = RefreshCcw;
  readonly SaveIcon = Save;

  selectDocument(index: number) {
    this.selectedDocIndex = index;
    this.previewUrl = null; // Reset preview when switching docs
  }

  getDocumentTypeLabel(docType: string): string {
    switch (docType) {
      case 'ID_PROOF': return 'ID Proof';
      case 'ADDRESS_PROOF': return 'Address Proof';
      case 'INCOME_PROOF': return 'Income Proof';
      default: return docType.replace('_', ' ');
    }
  }

  getDocumentStatusIcon(status: string) {
    switch (status) {
      case 'PENDING': return Clock;
      case 'VERIFIED': return CheckCircle;
      case 'REJECTED': return XCircle;
      default: return Info;
    }
  }

  getVerifiedCount(): number {
    return this.documents.filter(doc => doc.status === 'VERIFIED').length;
  }

  getPendingCount(): number {
    return this.documents.filter(doc => doc.status === 'PENDING').length;
  }

  getRejectedCount(): number {
    return this.documents.filter(doc => doc.status === 'REJECTED').length;
  }

  verifyDocument(document: DocumentInfo, verified: boolean) {
    this.documentVerified.emit({ document, verified });
  }

  rejectDocument(document: DocumentInfo) {
    this.documentRejected.emit(document);
  }

  requestReupload(document: DocumentInfo) {
    this.reuploadRequested.emit(document);
  }

  saveRejectionReason(doc: DocumentInfo, reason: string) {
    if (!reason.trim()) return;
    this.documentRejected.emit({ ...doc, rejectionReason: reason });
  }

  downloadDocument(docInfo: DocumentInfo) {
    this.docService.downloadDocument(docInfo.id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = docInfo.originalName;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  previewDocument(document: DocumentInfo) {
    this.docService.downloadDocument(document.id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      this.previewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    });
  }
}
