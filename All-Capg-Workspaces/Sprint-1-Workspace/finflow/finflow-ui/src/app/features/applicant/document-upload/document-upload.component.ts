import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../../core/services/document.service';
import { DocumentInfo } from '../../../core/models/admin.model';
import { LucideAngularModule, Upload, FileText, Check, X, ArrowLeft, Loader } from 'lucide-angular';

@Component({
  selector: 'app-document-upload',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  template: `
    <div class="upload-container fade-in">
      <button (click)="goBack()" class="btn btn-ghost">
        <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon> Back to Application
      </button>

      <h1>Upload Documents</h1>
      <p class="subtitle">Application #{{ applicationId }} — Upload required KYC documents</p>

      <div class="upload-grid">
        @for (docType of docTypes; track docType.value) {
          <div class="glass upload-card" [class.uploaded]="getDoc(docType.value)"
               [class.verified]="getDoc(docType.value)?.status === 'VERIFIED'"
               [class.rejected]="getDoc(docType.value)?.status === 'REJECTED'">
            <div class="card-icon">
              <lucide-icon [name]="FileText" [size]="24"></lucide-icon>
            </div>
            <h3>{{ docType.label }}</h3>

            @if (getDoc(docType.value)) {
              <div class="doc-uploaded">
                <span class="doc-filename">{{ getDoc(docType.value)!.originalName }}</span>
                <span class="doc-badge" [class]="getDoc(docType.value)!.status.toLowerCase()">
                  @if (getDoc(docType.value)!.status === 'VERIFIED') {
                    <lucide-icon [name]="Check" [size]="12"></lucide-icon>
                  } @else if (getDoc(docType.value)!.status === 'REJECTED') {
                    <lucide-icon [name]="XIcon" [size]="12"></lucide-icon>
                  }
                  {{ getDoc(docType.value)!.status }}
                </span>
              </div>
              @if (getDoc(docType.value)!.status === 'REJECTED') {
                <label class="upload-dropzone small">
                  <input type="file" (change)="onFileSelected($event, docType.value)" hidden>
                  <lucide-icon [name]="Upload" [size]="16"></lucide-icon>
                  <span>Re-upload</span>
                </label>
              }
            } @else {
              <label class="upload-dropzone" [class.uploading]="uploadingType === docType.value">
                <input type="file" (change)="onFileSelected($event, docType.value)" hidden [disabled]="uploadingType === docType.value">
                @if (uploadingType === docType.value) {
                  <lucide-icon [name]="LoaderIcon" [size]="24" class="spin"></lucide-icon>
                  <span>Uploading...</span>
                } @else {
                  <lucide-icon [name]="Upload" [size]="24"></lucide-icon>
                  <span>Click to upload</span>
                  <span class="hint">PDF, JPG, PNG (max 15MB)</span>
                }
              </label>
            }
          </div>
        }
      </div>

      @if (error) {
        <div class="error-alert">{{ error }}</div>
      }
    </div>
  `,
  styles: [`
    .upload-container { max-width: 900px; margin: 0 auto; padding: 2rem 1.5rem; }
    h1 { font-size: 1.75rem; margin-top: 1.5rem; }
    .subtitle { color: var(--text-muted); margin-bottom: 2rem; }
    .upload-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1.5rem; }

    .upload-card {
      padding: 1.5rem; border-radius: var(--radius-xl); text-align: center;
      transition: all 0.3s; border: 2px solid transparent;
    }
    .upload-card.uploaded { border-color: rgba(59,130,246,0.2); }
    .upload-card.verified { border-color: rgba(16,185,129,0.3); }
    .upload-card.rejected { border-color: rgba(239,68,68,0.3); }
    .card-icon { margin-bottom: 0.75rem; color: var(--text-muted); }
    .upload-card h3 { font-size: 0.95rem; margin-bottom: 1rem; }

    .upload-dropzone {
      display: flex; flex-direction: column; align-items: center; gap: 0.5rem;
      padding: 2rem 1rem; border: 2px dashed var(--border-color); border-radius: var(--radius-lg);
      cursor: pointer; transition: all 0.2s; color: var(--text-muted);
    }
    .upload-dropzone:hover { border-color: var(--primary-color); color: var(--primary-color); background: rgba(16,185,129,0.03); }
    .upload-dropzone.small { padding: 0.75rem; margin-top: 0.75rem; }
    .upload-dropzone.uploading { cursor: default; border-color: var(--primary-color); }
    .hint { font-size: 0.7rem; opacity: 0.6; }

    .doc-uploaded { display: flex; flex-direction: column; align-items: center; gap: 0.5rem; }
    .doc-filename { font-size: 0.8rem; color: var(--text-muted); word-break: break-all; }
    .doc-badge {
      display: inline-flex; align-items: center; gap: 0.25rem; font-size: 0.7rem;
      font-weight: 700; padding: 0.2rem 0.6rem; border-radius: 100px; text-transform: uppercase;
    }
    .doc-badge.pending { color: #f59e0b; background: rgba(245,158,11,0.1); }
    .doc-badge.verified { color: #10b981; background: rgba(16,185,129,0.1); }
    .doc-badge.rejected { color: #ef4444; background: rgba(239,68,68,0.1); }

    .error-alert {
      margin-top: 1.5rem; background: rgba(239,68,68,0.1); border: 1px solid rgba(239,68,68,0.2);
      color: #ef4444; padding: 0.75rem; border-radius: var(--radius-md); text-align: center; font-size: 0.875rem;
    }
    .spin { animation: spin 1s linear infinite; }
    @keyframes spin { to { transform: rotate(360deg); } }
  `]
})
export class DocumentUploadComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private docService = inject(DocumentService);

  readonly Upload = Upload; readonly FileText = FileText; readonly Check = Check;
  readonly XIcon = X; readonly ArrowLeft = ArrowLeft; readonly LoaderIcon = Loader;

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
    this.error = '';
    this.uploadingType = docType;
    this.docService.upload(this.applicationId, docType, file).subscribe({
      next: () => { this.uploadingType = null; this.loadDocuments(); },
      error: (err) => { this.uploadingType = null; this.error = 'Upload failed. Please try again.'; }
    });
  }

  goBack() { this.router.navigate(['/applicant/application', this.applicationId]); }
}
