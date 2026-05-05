import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../../core/services/document.service';
import { DocumentInfo } from '../../../core/models/admin.model';
import { LucideAngularModule } from 'lucide-angular';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-document-upload',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule],
  template: `
    <div class="upload-container fade-in">
      <div class="upload-header">
        <button mat-icon-button (click)="goBack()" class="btn-icon">
          <mat-icon>arrow_back</mat-icon>
        </button>
        <div class="header-text">
          <h1>Identity & Verification</h1>
          <p class="text-muted">Upload required KYC documents for Application #{{ applicationId }}</p>
        </div>
      </div>

      <div class="upload-grid">
        @for (docType of docTypes; track docType.value) {
          <div class="glass-card upload-card card-hover" 
               [class.uploaded]="getDoc(docType.value)"
               [class.verified]="getDoc(docType.value)?.status === 'VERIFIED'"
               [class.rejected]="getDoc(docType.value)?.status === 'REJECTED'">
            
            <div class="card-status-dot" [class]="getDoc(docType.value)?.status?.toLowerCase() || 'pending'"></div>
            
            <div class="card-top">
              <div class="icon-box" [class.active]="getDoc(docType.value)">
                <mat-icon>description</mat-icon>
              </div>
              <div class="card-meta">
                <h3>{{ docType.label }}</h3>
                <span class="status-text">{{ getDoc(docType.value)?.status || 'Awaiting Upload' }}</span>
              </div>
            </div>

            @if (getDoc(docType.value)) {
              <div class="doc-preview fade-in">
                <div class="file-info">
                  <span class="filename">{{ getDoc(docType.value)!.originalName }}</span>
                  <span class="filesize">Uploaded {{ getDoc(docType.value)!.createdAt | date:'MMM d' }}</span>
                </div>
                
                <div class="doc-footer">
                  @if (getDoc(docType.value)!.status === 'REJECTED') {
                    <div class="rejection-info fade-in" *ngIf="getDoc(docType.value)?.rejectionReason">
                      <mat-icon>error_outline</mat-icon>
                      <span>{{ getDoc(docType.value)!.rejectionReason }}</span>
                    </div>
                    <label class="btn btn-primary btn-sm full-width">
                      <input type="file" (change)="onFileSelected($event, docType.value)" hidden>
                      <mat-icon>upload</mat-icon>
                      Re-upload Document
                    </label>
                  } @else {
                    <div class="verified-badge" *ngIf="getDoc(docType.value)!.status === 'VERIFIED'">
                      <mat-icon>check_circle</mat-icon>
                      Verified
                    </div>
                  }
                </div>
              </div>
            } @else {
              <label class="dropzone-area" [class.uploading]="uploadingType === docType.value">
                <input type="file" (change)="onFileSelected($event, docType.value)" hidden [disabled]="uploadingType === docType.value">
                @if (uploadingType === docType.value) {
                  <mat-progress-spinner diameter="24"></mat-progress-spinner>
                  <p>Uploading...</p>
                } @else {
                  <mat-icon class="upload-icon">upload</mat-icon>
                  <p>Click to upload</p>
                  <span class="hint">PDF, JPG or PNG</span>
                }
              </label>
            }
          </div>
        }
      </div>

      <div class="glass-card help-banner fade-in">
        <mat-icon class="text-success">check_circle</mat-icon>
        <div class="banner-text">
          <h4>Verification Process</h4>
          <p class="text-muted">Our compliance team will review these documents within 24 hours. You'll be notified once verified.</p>
        </div>
      </div>

      @if (error) {
        <div class="error-alert fade-in">
          <mat-icon>error</mat-icon>
          {{ error }}
        </div>
      }
    </div>
  `,
  styles: [`
    .upload-container { max-width: 1100px; margin: 0 auto; padding: var(--gap-md); }
    
    .upload-header { display: flex; align-items: center; gap: 1.5rem; margin-bottom: var(--gap-lg); }
    .header-text h1 { font-size: 1.75rem; margin-bottom: 2px; }
    .btn-icon { background: transparent; border: none; padding: 8px; border-radius: 50%; }
    .btn-icon:hover { background: rgba(0,0,0,0.04); }

    .upload-grid { 
      display: grid; 
      grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); 
      gap: var(--gap-md); 
      margin-bottom: var(--gap-lg);
    }

    .upload-card {
      padding: 1.5rem;
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
      min-height: 240px;
    }

    .card-status-dot {
      position: absolute;
      top: 1.5rem;
      right: 1.5rem;
      width: 8px;
      height: 8px;
      border-radius: 50%;
      background: var(--text-muted);
    }
    .card-status-dot.verified { background: var(--success); box-shadow: 0 0 10px var(--success-glow); }
    .card-status-dot.rejected { background: var(--danger); box-shadow: 0 0 10px rgba(239, 68, 68, 0.4); }
    .card-status-dot.pending { background: var(--warning); box-shadow: 0 0 10px rgba(245, 158, 11, 0.4); }

    .card-top { display: flex; align-items: center; gap: 1rem; }
    
    .icon-box {
      width: 48px;
      height: 48px;
      border-radius: 14px;
      background: var(--bg-tertiary);
      border: 1px solid var(--border);
      display: flex;
      align-items: center;
      justify-content: center;
      color: var(--text-muted);
      transition: var(--transition);
    }
    .icon-box.active { background: var(--accent-soft); color: var(--accent); border-color: var(--accent); }

    .card-meta h3 { font-size: 1.125rem; margin-bottom: 2px; }
    .status-text { font-size: 0.75rem; font-weight: 700; text-transform: uppercase; color: var(--text-muted); letter-spacing: 0.5px; }

    .dropzone-area {
      flex: 1;
      border: 2px dashed var(--border);
      border-radius: var(--radius-lg);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: 8px;
      cursor: pointer;
      transition: var(--transition);
      color: var(--text-muted);
    }
    .dropzone-area:hover { border-color: var(--accent); color: var(--text-primary); background: rgba(99, 102, 241, 0.03); }
    .dropzone-area p { font-size: 0.875rem; font-weight: 600; }
    .hint { font-size: 0.7rem; opacity: 0.6; }
    .upload-icon { opacity: 0.5; }

    .doc-preview {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      gap: 1rem;
    }

    .file-info {
      background: var(--bg-primary);
      padding: 1rem;
      border-radius: var(--radius-md);
      border: 1px solid var(--border);
    }
    .filename { display: block; font-weight: 600; font-size: 0.875rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    .filesize { font-size: 0.75rem; color: var(--text-muted); }

    .verified-badge {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 6px;
      color: var(--success);
      font-size: 0.875rem;
      font-weight: 700;
    }

    .help-banner {
      padding: 1.5rem 2rem;
      display: flex;
      align-items: center;
      gap: 1.5rem;
      background: linear-gradient(to right, var(--accent-soft), transparent);
    }
    .banner-text h4 { margin-bottom: 4px; }
    .banner-text p { font-size: 0.875rem; }

    .error-alert {
      margin-top: 1.5rem;
      padding: 1rem 1.5rem;
      border-radius: var(--radius-md);
      background: rgba(239, 68, 68, 0.1);
      border: 1px solid rgba(239, 68, 68, 0.2);
      color: var(--danger);
      display: flex;
      align-items: center;
      gap: 12px;
      font-weight: 600;
    }

    .spin { animation: spin 1s linear infinite; }
    @keyframes spin { to { transform: rotate(360deg); } }
    
    .rejection-info {
      padding: 0.75rem;
      background: rgba(239, 68, 68, 0.1);
      border-radius: 8px;
      color: #ef4444;
      font-size: 0.8rem;
      display: flex;
      align-items: flex-start;
      gap: 8px;
      margin-bottom: 0.5rem;
    }
    .rejection-info mat-icon { font-size: 16px; width: 16px; height: 16px; margin-top: 2px; }

    .full-width { width: 100%; }
    .btn-sm { padding: 0.6rem 1rem; font-size: 0.815rem; }
  `]
})
export class DocumentUploadComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private docService = inject(DocumentService);

  applicationId = 0;
  documents: DocumentInfo[] = [];
  uploadingType: string | null = null;
  error = '';

  docTypes = [
    { value: 'ID_PROOF', label: 'ID Proof' },
    { value: 'ADDRESS_PROOF', label: 'Address Proof' },
    { value: 'INCOME_PROOF', label: 'Income Proof' }
  ];

  ngOnInit() {
    this.applicationId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDocuments();
  }

  loadDocuments() {
    this.docService.getDocuments(this.applicationId).subscribe({
      next: (docs) => this.documents = docs
    });
  }

  getDoc(type: string): DocumentInfo | undefined {
    return this.documents.find(d => d.docType === type);
  }

  onFileSelected(event: any, docType: string) {
    const file: File = event.target.files[0];
    if (!file) return;
    
    // Validate file type
    const allowedTypes = ['application/pdf', 'image/jpeg', 'image/png', 'image/jpg'];
    if (!allowedTypes.includes(file.type)) {
      this.error = 'Invalid file type. Please upload PDF, JPG, or PNG files only.';
      return;
    }
    
    // Validate file size (max 5MB)
    const maxSize = 5 * 1024 * 1024; // 5MB in bytes
    if (file.size > maxSize) {
      this.error = 'File size too large. Please upload files smaller than 5MB.';
      return;
    }
    
    this.error = '';
    this.uploadingType = docType;
    this.docService.upload(this.applicationId, docType, file).subscribe({
      next: (response) => { 
        console.log('Upload successful:', response);
        this.uploadingType = null; 
        this.loadDocuments(); 
        // Clear the file input
        event.target.value = '';
      },
      error: (err) => { 
        console.error('Upload error:', err);
        this.uploadingType = null; 
        this.error = 'Upload failed. Please try again.'; 
        // Clear the file input
        event.target.value = '';
      }
    });
  }

  goBack() { this.router.navigate(['/applicant/application', this.applicationId]); }
}
