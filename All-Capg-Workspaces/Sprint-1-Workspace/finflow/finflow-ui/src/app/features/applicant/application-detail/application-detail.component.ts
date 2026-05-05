import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { LoanApplication } from '../../../core/models/application.model';
import { ApplicationStatusResponse, TimelineEntry, DocumentInfo } from '../../../core/models/admin.model';
import { LucideAngularModule, ArrowLeft, FileText, Upload, Clock, CircleCheck, CircleAlert, CircleX, Banknote, User, Briefcase, Download, Eye } from 'lucide-angular';

@Component({
  selector: 'app-application-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, LucideAngularModule],
  template: `
    <div class="detail-container fade-in">
      <button (click)="goBack()" class="btn-icon">
        <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
      </button>

      @if (application) {
        <div class="detail-header">
          <div class="header-main">
            <h1>Loan Application #{{ application.id }}</h1>
            <div class="badge-wrapper">
              <span class="status-badge" [class]="application.status.toLowerCase()">{{ application.status.replace('_', ' ') }}</span>
            </div>
          </div>
          @if (application.status === 'DRAFT') {
            <button [routerLink]="['/applicant/loan-wizard', application.id]" class="btn btn-primary">
              <lucide-icon [name]="FileText" [size]="18"></lucide-icon> Continue Editing
            </button>
          }
        </div>

        <div class="detail-grid">
          <!-- Application Summary -->
          <div class="glass-card detail-card card-hover">
            <h3><lucide-icon [name]="UserIcon" [size]="20"></lucide-icon> Personal Info</h3>
            <div class="info-grid">
              <div class="info-item"><span class="label">Full Name</span><span class="value">{{ application.fullName || '—' }}</span></div>
              <div class="info-item"><span class="label">Phone</span><span class="value">{{ application.phone || '—' }}</span></div>
              <div class="info-item"><span class="label">Date of Birth</span><span class="value">{{ application.dateOfBirth || '—' }}</span></div>
              <div class="info-item full"><span class="label">Address</span><span class="value">{{ application.address || '—' }}</span></div>
            </div>
          </div>

          <div class="glass-card detail-card card-hover">
            <h3><lucide-icon [name]="Briefcase" [size]="20"></lucide-icon> Employment</h3>
            <div class="info-grid">
              <div class="info-item"><span class="label">Employer</span><span class="value">{{ application.employer || '—' }}</span></div>
              <div class="info-item"><span class="label">Type</span><span class="value">{{ application.employmentType || '—' }}</span></div>
              <div class="info-item"><span class="label">Annual Income</span><span class="value amount">{{ application.annualIncome | currency:'INR':'symbol':'1.0-0' }}</span></div>
            </div>
          </div>

          <div class="glass-card detail-card card-hover">
            <h3><lucide-icon [name]="Banknote" [size]="20"></lucide-icon> Loan Details</h3>
            <div class="info-grid">
              <div class="info-item"><span class="label">Loan Amount</span><span class="value amount-hero">{{ application.loanAmount | currency:'INR':'symbol':'1.0-0' }}</span></div>
              <div class="info-item"><span class="label">Tenure</span><span class="value">{{ application.tenureMonths }} months</span></div>
              <div class="info-item full"><span class="label">Purpose</span><span class="value">{{ application.loanPurpose || '—' }}</span></div>
            </div>
          </div>

          <!-- Documents Section -->
          <div class="glass-card detail-card doc-card-full">
            <div class="card-header">
              <div class="title-with-subtitle">
                <h3><lucide-icon [name]="FileText" [size]="20"></lucide-icon> Documents</h3>
                <p class="subtitle">Verification status of your uploads</p>
              </div>
              @if (application.status === 'DOCS_PENDING' || application.status === 'REJECTED') {
                <button [routerLink]="['/applicant/documents', application.id]" class="btn btn-primary btn-sm">
                  <lucide-icon [name]="Upload" [size]="16"></lucide-icon> Resolve Issues
                </button>
              }
            </div>

            @if (documents.length === 0) {
              <div class="empty-state">
                <lucide-icon [name]="FileText" [size]="48" class="text-muted"></lucide-icon>
                <p class="empty-text">No documents have been uploaded yet.</p>
                <button [routerLink]="['/applicant/documents', application.id]" class="btn btn-ghost btn-sm">
                  Upload Now
                </button>
              </div>
            } @else {
              <div class="doc-list-enhanced">
                @for (doc of documents; track doc.id) {
                  <div class="enhanced-doc-item" [class.rejected]="doc.status === 'REJECTED'">
                    <div class="doc-main-content">
                      <div class="doc-type-icon">
                        <lucide-icon [name]="FileText" [size]="24"></lucide-icon>
                      </div>
                      <div class="doc-details-text">
                        <span class="doc-type-label">{{ doc.docType.replace('_', ' ') }}</span>
                        <span class="doc-filename">{{ doc.originalName }}</span>
                        <span class="doc-date">Uploaded {{ doc.createdAt | date:'MMM d, y' }}</span>
                      </div>
                      <div class="doc-status-col">
                        <span class="status-pill" [class]="doc.status.toLowerCase()">
                          <lucide-icon [name]="getTimelineIcon(doc.status)" [size]="12"></lucide-icon>
                          {{ doc.status }}
                        </span>
                      </div>
                    </div>

                    @if (doc.status === 'REJECTED') {
                      <div class="rejection-alert">
                        <div class="rejection-header">
                          <lucide-icon [name]="CircleX" [size]="16"></lucide-icon>
                          <span>Action Required: Re-upload Needed</span>
                        </div>
                        <p class="rejection-reason">{{ doc.rejectionReason || 'The document provided is unclear or invalid. Please re-upload a high-quality copy.' }}</p>
                        <button [routerLink]="['/applicant/documents', application.id]" class="btn btn-danger btn-sm">
                          <lucide-icon [name]="Upload" [size]="14"></lucide-icon> Re-upload Document
                        </button>
                      </div>
                    }

                    <div class="doc-footer">
                      <button (click)="downloadDoc(doc)" class="btn btn-ghost btn-xs">
                        <lucide-icon [name]="Download" [size]="14"></lucide-icon> Download
                      </button>
                      <button (click)="previewDoc(doc)" class="btn btn-ghost btn-xs">
                        <lucide-icon [name]="Eye" [size]="14"></lucide-icon> View
                      </button>
                    </div>
                  </div>
                }
              </div>
            }
          </div>

          <!-- Timeline -->
          <div class="glass-card detail-card timeline-card">
            <h3><lucide-icon [name]="Clock" [size]="20"></lucide-icon> Application Journey</h3>
            @if (timeline.length > 0) {
              <div class="timeline">
                @for (entry of timeline; track $index; let last = $last) {
                  <div class="timeline-item" [class.last]="last">
                    <div class="timeline-dot" [class]="entry.status.toLowerCase()">
                      <lucide-icon [name]="getTimelineIcon(entry.status)" [size]="14"></lucide-icon>
                    </div>
                    <div class="timeline-content">
                      <div class="timeline-header">
                        <span class="timeline-status">{{ entry.status.replace('_', ' ') }}</span>
                        <span class="timeline-date">{{ entry.occurredAt | date:'medium' }}</span>
                      </div>
                      <p class="timeline-note">{{ entry.note }}</p>
                    </div>
                  </div>
                }
              </div>
            } @else {
              <p class="empty-text">No status updates yet.</p>
            }
          </div>
        </div>
      } @else {
        <div class="loading-state">
          <div class="spinner"></div>
          <p>Analyzing your application...</p>
        </div>
      }
    </div>
  `,
  styles: [`
    .detail-container { max-width: 1100px; margin: 0 auto; padding: 2rem 1.5rem; }
    .back-btn { margin-bottom: 1.5rem; }
    .detail-header { margin-bottom: 2.5rem; display: flex; justify-content: space-between; align-items: flex-end; }
    .header-main h1 { font-size: 2.25rem; font-weight: 800; margin-bottom: 0.75rem; letter-spacing: -0.02em; }
    
    .status-badge {
      font-size: 0.7rem; font-weight: 800; text-transform: uppercase; padding: 0.4rem 1rem;
      border-radius: 100px; display: inline-flex; align-items: center; letter-spacing: 0.05em;
      border: 1px solid rgba(255,255,255,0.1);
    }
    .status-badge.draft { background: rgba(148,163,184,0.1); color: #94a3b8; }
    .status-badge.submitted { background: rgba(59,130,246,0.1); color: #3b82f6; border-color: rgba(59,130,246,0.2); }
    .status-badge.docs_pending { background: rgba(245,158,11,0.1); color: #f59e0b; border-color: rgba(245,158,11,0.2); }
    .status-badge.docs_verified { background: rgba(139,92,246,0.1); color: #8b5cf6; border-color: rgba(139,92,246,0.2); }
    .status-badge.under_review { background: rgba(168,85,247,0.1); color: #a855f7; border-color: rgba(168,85,247,0.2); }
    .status-badge.approved { background: rgba(16,185,129,0.1); color: #10b981; border-color: rgba(16,185,129,0.2); }
    .status-badge.rejected { background: rgba(239,68,68,0.1); color: #ef4444; border-color: rgba(239,68,68,0.2); }

    .detail-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 1.5rem; }
    .detail-card { padding: 2rem; border-radius: var(--radius-lg); }
    .detail-card h3 { display: flex; align-items: center; gap: 0.75rem; font-size: 1.1rem; margin-bottom: 1.5rem; color: var(--accent-bright); }
    .card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; }
    .card-header h3 { margin-bottom: 0; }
    .btn-sm { padding: 0.5rem 1rem; font-size: 0.8rem; }

    .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.25rem; }
    .info-item { display: flex; flex-direction: column; gap: 0.35rem; }
    .info-item.full { grid-column: 1 / -1; }
    .info-item .label { font-size: 0.7rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.08em; font-weight: 700; }
    .info-item .value { font-size: 1rem; font-weight: 600; color: var(--text-primary); }
    .info-item .value.amount { color: var(--accent-bright); font-size: 1.15rem; }
    .info-item .value.amount-hero { font-size: 1.75rem; font-weight: 800; color: var(--text-primary); letter-spacing: -0.02em; }

    .doc-card-full { grid-column: 1 / -1; }
    .title-with-subtitle h3 { margin-bottom: 4px !important; }
    .subtitle { font-size: 0.75rem; color: var(--text-muted); font-weight: 500; }

    .doc-list-enhanced { display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 1.5rem; }
    .enhanced-doc-item { 
      background: rgba(255,255,255,0.02); border: 1px solid var(--border); border-radius: var(--radius-md);
      padding: 1.5rem; display: flex; flex-direction: column; gap: 1.25rem; transition: var(--transition);
    }
    .enhanced-doc-item:hover { border-color: var(--accent-soft); background: rgba(255,255,255,0.04); }
    .enhanced-doc-item.rejected { border-color: rgba(239, 68, 68, 0.3); background: rgba(239, 68, 68, 0.03); }

    .doc-main-content { display: flex; align-items: flex-start; gap: 1rem; }
    .doc-type-icon { 
      width: 48px; height: 48px; border-radius: 12px; background: var(--bg-tertiary); 
      display: flex; align-items: center; justify-content: center; color: var(--accent);
    }
    .doc-details-text { flex: 1; display: flex; flex-direction: column; gap: 2px; }
    .doc-type-label { font-size: 0.7rem; font-weight: 800; text-transform: uppercase; color: var(--text-muted); letter-spacing: 0.5px; }
    .doc-filename { font-size: 0.95rem; font-weight: 700; color: var(--text-primary); }
    .doc-date { font-size: 0.75rem; color: var(--text-muted); }

    .status-pill { 
      font-size: 0.65rem; font-weight: 800; text-transform: uppercase; padding: 4px 10px; 
      border-radius: 100px; display: inline-flex; align-items: center; gap: 6px;
    }
    .status-pill.pending { background: rgba(245,158,11,0.1); color: var(--warning); }
    .status-pill.verified { background: rgba(16,185,129,0.1); color: var(--success); }
    .status-pill.rejected { background: rgba(239,68,68,0.1); color: var(--danger); }

    .rejection-alert { 
      background: rgba(239, 68, 68, 0.1); border-radius: 8px; padding: 1rem; 
      display: flex; flex-direction: column; gap: 0.75rem; border: 1px solid rgba(239, 68, 68, 0.2);
    }
    .rejection-header { display: flex; align-items: center; gap: 8px; color: var(--danger); font-weight: 700; font-size: 0.8rem; }
    .rejection-reason { font-size: 0.85rem; color: var(--text-secondary); line-height: 1.4; }

    .doc-footer { display: flex; gap: 0.75rem; margin-top: auto; padding-top: 1rem; border-top: 1px solid var(--border); }
    .btn-xs { padding: 0.4rem 0.75rem; font-size: 0.7rem; font-weight: 700; }

    .timeline-card { grid-column: 1 / -1; }
    .timeline { position: relative; padding-left: 1rem; margin-top: 1rem; }
    .timeline-item { position: relative; padding-bottom: 2rem; padding-left: 2.5rem; }
    .timeline-item.last { padding-bottom: 0; }
    .timeline-item::before {
      content: ''; position: absolute; left: 14px; top: 32px; bottom: 0; width: 2px;
      background: linear-gradient(to bottom, var(--border), transparent);
    }
    .timeline-item.last::before { display: none; }
    .timeline-dot {
      position: absolute; left: 0; top: 4px; width: 30px; height: 30px;
      border-radius: 50%; display: flex; align-items: center; justify-content: center;
      background: var(--bg-primary); border: 2px solid var(--border); color: var(--text-muted);
      box-shadow: 0 0 15px rgba(0,0,0,0.3); z-index: 1;
    }
    .timeline-dot.draft { border-color: #94a3b8; color: #94a3b8; }
    .timeline-dot.submitted { border-color: #3b82f6; color: #3b82f6; background: rgba(59,130,246,0.1); }
    .timeline-dot.docs_pending { border-color: #f59e0b; color: #f59e0b; background: rgba(245,158,11,0.1); }
    .timeline-dot.docs_verified { border-color: #8b5cf6; color: #8b5cf6; background: rgba(139,92,246,0.1); }
    .timeline-dot.under_review { border-color: #a855f7; color: #a855f7; background: rgba(168,85,247,0.1); }
    .timeline-dot.approved { border-color: #10b981; color: #10b981; background: rgba(16,185,129,0.2); box-shadow: 0 0 20px rgba(16,185,129,0.3); }
    .timeline-dot.rejected { border-color: #ef4444; color: #ef4444; background: rgba(239,68,68,0.2); box-shadow: 0 0 20px rgba(239,68,68,0.3); }
    
    .timeline-content { display: flex; flex-direction: column; gap: 0.5rem; }
    .timeline-header { display: flex; justify-content: space-between; align-items: center; }
    .timeline-status { font-weight: 800; font-size: 0.9rem; text-transform: uppercase; letter-spacing: 0.05em; color: var(--text-primary); }
    .timeline-date { font-size: 0.75rem; color: var(--text-muted); font-weight: 500; }
    .timeline-note { font-size: 0.9rem; color: var(--text-secondary); line-height: 1.5; padding: 0.75rem 1rem; background: rgba(255,255,255,0.03); border-radius: 8px; border-left: 3px solid var(--border); }
    .timeline-item.rejected .timeline-note { border-left-color: #ef4444; }
    .timeline-item.approved .timeline-note { border-left-color: #10b981; }

    .loading-state { display: flex; flex-direction: column; align-items: center; padding: 8rem 0; gap: 1.5rem; }
    .spinner { width: 50px; height: 50px; border: 3px solid rgba(99, 102, 241, 0.1); border-top-color: var(--accent); border-radius: 50%; animation: spin 1s linear infinite; }
    @keyframes spin { to { transform: rotate(360deg); } }
  `]
})
export class ApplicationDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appService = inject(ApplicationService);
  private docService = inject(DocumentService);

  readonly ArrowLeft = ArrowLeft; readonly FileText = FileText; readonly Upload = Upload;
  readonly Clock = Clock; readonly CircleCheck = CircleCheck; readonly CircleAlert = CircleAlert;
  readonly CircleX = CircleX; readonly Banknote = Banknote; readonly UserIcon = User; 
  readonly Briefcase = Briefcase; readonly Download = Download; readonly Eye = Eye;

  application: LoanApplication | null = null;
  documents: DocumentInfo[] = [];
  timeline: TimelineEntry[] = [];

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.appService.getApplicationById(id).subscribe({ next: (app) => this.application = app });
    this.appService.getStatus(id).subscribe({ next: (res) => this.timeline = res.timeline || [] });
    this.docService.getDocuments(id).subscribe({ next: (docs) => this.documents = docs });
  }

  getTimelineIcon(status: string) {
    switch (status) {
      case 'APPROVED': return CircleCheck;
      case 'REJECTED': return CircleX;
      case 'DOCS_PENDING': return CircleAlert;
      case 'SUBMITTED': return FileText;
      default: return Clock;
    }
  }

  downloadDoc(doc: DocumentInfo) {
    this.docService.downloadDocument(doc.id).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = doc.originalName;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
      }
    });
  }

  previewDoc(doc: DocumentInfo) {
    console.log('Previewing document:', doc.id);
    // Ideally this would open a preview modal or new tab with doc stream
  }

  goBack() { this.router.navigate(['/applicant/dashboard']); }
}
