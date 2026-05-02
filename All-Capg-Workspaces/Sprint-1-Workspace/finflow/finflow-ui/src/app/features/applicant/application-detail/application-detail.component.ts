import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { LoanApplication } from '../../../core/models/application.model';
import { ApplicationStatusResponse, TimelineEntry, DocumentInfo } from '../../../core/models/admin.model';
import { LucideAngularModule, ArrowLeft, FileText, Upload, Clock, CircleCheck, CircleAlert, CircleX, Banknote, User, Briefcase } from 'lucide-angular';

@Component({
  selector: 'app-application-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, LucideAngularModule],
  template: `
    <div class="detail-container fade-in">
      <button (click)="goBack()" class="btn btn-ghost back-btn">
        <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon> Back to Dashboard
      </button>

      @if (application) {
        <div class="detail-header">
          <div>
            <h1>Loan Application #{{ application.id }}</h1>
            <span class="status-badge" [class]="application.status.toLowerCase()">{{ application.status.replace('_', ' ') }}</span>
          </div>
          @if (application.status === 'DRAFT') {
            <button [routerLink]="['/applicant/loan-wizard', application.id]" class="btn btn-primary">
              <lucide-icon [name]="FileText" [size]="18"></lucide-icon> Continue Editing
            </button>
          }
        </div>

        <div class="detail-grid">
          <!-- Application Summary -->
          <div class="glass detail-card">
            <h3><lucide-icon [name]="UserIcon" [size]="20"></lucide-icon> Personal Info</h3>
            <div class="info-grid">
              <div class="info-item"><span class="label">Full Name</span><span class="value">{{ application.fullName || '—' }}</span></div>
              <div class="info-item"><span class="label">Phone</span><span class="value">{{ application.phone || '—' }}</span></div>
              <div class="info-item"><span class="label">Date of Birth</span><span class="value">{{ application.dateOfBirth || '—' }}</span></div>
              <div class="info-item full"><span class="label">Address</span><span class="value">{{ application.address || '—' }}</span></div>
            </div>
          </div>

          <div class="glass detail-card">
            <h3><lucide-icon [name]="Briefcase" [size]="20"></lucide-icon> Employment</h3>
            <div class="info-grid">
              <div class="info-item"><span class="label">Employer</span><span class="value">{{ application.employer || '—' }}</span></div>
              <div class="info-item"><span class="label">Type</span><span class="value">{{ application.employmentType || '—' }}</span></div>
              <div class="info-item"><span class="label">Annual Income</span><span class="value">{{ application.annualIncome | currency:'INR':'symbol':'1.0-0' }}</span></div>
            </div>
          </div>

          <div class="glass detail-card">
            <h3><lucide-icon [name]="Banknote" [size]="20"></lucide-icon> Loan Details</h3>
            <div class="info-grid">
              <div class="info-item"><span class="label">Loan Amount</span><span class="value amount">{{ application.loanAmount | currency:'INR':'symbol':'1.0-0' }}</span></div>
              <div class="info-item"><span class="label">Tenure</span><span class="value">{{ application.tenureMonths }} months</span></div>
              <div class="info-item full"><span class="label">Purpose</span><span class="value">{{ application.loanPurpose || '—' }}</span></div>
            </div>
          </div>

          <!-- Documents Section -->
          <div class="glass detail-card">
            <div class="card-header">
              <h3><lucide-icon [name]="FileText" [size]="20"></lucide-icon> Documents</h3>
              @if (application.status !== 'DRAFT') {
                <button [routerLink]="['/applicant/documents', application.id]" class="btn btn-ghost btn-sm">
                  <lucide-icon [name]="Upload" [size]="16"></lucide-icon> Upload
                </button>
              }
            </div>
            @if (documents.length === 0) {
              <p class="empty-text">No documents uploaded yet.</p>
            } @else {
              <div class="doc-list">
                @for (doc of documents; track doc.id) {
                  <div class="doc-item">
                    <div class="doc-info">
                      <span class="doc-type">{{ doc.docType.replace('_', ' ') }}</span>
                      <span class="doc-name">{{ doc.originalName }}</span>
                    </div>
                    <span class="doc-status" [class]="doc.status.toLowerCase()">{{ doc.status }}</span>
                  </div>
                }
              </div>
            }
          </div>

          <!-- Timeline -->
          <div class="glass detail-card timeline-card">
            <h3><lucide-icon [name]="Clock" [size]="20"></lucide-icon> Status Timeline</h3>
            @if (timeline.length > 0) {
              <div class="timeline">
                @for (entry of timeline; track $index; let last = $last) {
                  <div class="timeline-item" [class.last]="last">
                    <div class="timeline-dot" [class]="entry.status.toLowerCase()">
                      <lucide-icon [name]="getTimelineIcon(entry.status)" [size]="14"></lucide-icon>
                    </div>
                    <div class="timeline-content">
                      <span class="timeline-status">{{ entry.status.replace('_', ' ') }}</span>
                      <span class="timeline-note">{{ entry.note }}</span>
                      <span class="timeline-date">{{ entry.occurredAt | date:'medium' }}</span>
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
          <p>Loading application...</p>
        </div>
      }
    </div>
  `,
  styles: [`
    .detail-container { max-width: 1000px; margin: 0 auto; padding: 2rem 1.5rem; }
    .back-btn { margin-bottom: 1.5rem; }
    .detail-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; }
    .detail-header h1 { font-size: 1.75rem; margin-bottom: 0.5rem; }
    .status-badge {
      font-size: 0.75rem; font-weight: 700; text-transform: uppercase; padding: 0.3rem 0.8rem;
      border-radius: 100px; display: inline-block;
    }
    .status-badge.draft { background: rgba(148,163,184,0.1); color: #94a3b8; }
    .status-badge.submitted { background: rgba(59,130,246,0.1); color: #3b82f6; }
    .status-badge.docs_pending { background: rgba(245,158,11,0.1); color: #f59e0b; }
    .status-badge.docs_verified { background: rgba(139,92,246,0.1); color: #8b5cf6; }
    .status-badge.under_review { background: rgba(168,85,247,0.1); color: #a855f7; }
    .status-badge.approved { background: rgba(16,185,129,0.1); color: #10b981; }
    .status-badge.rejected { background: rgba(239,68,68,0.1); color: #ef4444; }

    .detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; }
    .detail-card { padding: 1.5rem; border-radius: var(--radius-xl); }
    .detail-card h3 { display: flex; align-items: center; gap: 0.5rem; font-size: 1rem; margin-bottom: 1.25rem; color: var(--primary-color); }
    .card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.25rem; }
    .card-header h3 { margin-bottom: 0; }
    .btn-sm { padding: 0.4rem 0.75rem; font-size: 0.8rem; }

    .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
    .info-item { display: flex; flex-direction: column; gap: 0.25rem; }
    .info-item.full { grid-column: 1 / -1; }
    .info-item .label { font-size: 0.75rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.05em; }
    .info-item .value { font-size: 0.95rem; font-weight: 500; }
    .info-item .value.amount { font-size: 1.25rem; font-weight: 700; color: var(--primary-color); }
    .empty-text { color: var(--text-muted); font-size: 0.875rem; }

    .doc-list { display: flex; flex-direction: column; gap: 0.75rem; }
    .doc-item {
      display: flex; justify-content: space-between; align-items: center;
      padding: 0.75rem 1rem; background: rgba(255,255,255,0.03); border-radius: var(--radius-md);
      border: 1px solid var(--border-color);
    }
    .doc-type { font-weight: 600; font-size: 0.8rem; text-transform: uppercase; color: var(--text-muted); }
    .doc-name { font-size: 0.875rem; display: block; }
    .doc-status { font-size: 0.7rem; font-weight: 700; text-transform: uppercase; padding: 0.2rem 0.5rem; border-radius: 100px; }
    .doc-status.pending { color: #f59e0b; }
    .doc-status.verified { color: #10b981; }
    .doc-status.rejected { color: #ef4444; }

    .timeline-card { grid-column: 1 / -1; }
    .timeline { position: relative; padding-left: 2rem; }
    .timeline-item { position: relative; padding-bottom: 1.5rem; padding-left: 1.5rem; }
    .timeline-item.last { padding-bottom: 0; }
    .timeline-item::before {
      content: ''; position: absolute; left: -2rem; top: 24px; bottom: 0; width: 2px;
      background: var(--border-color);
    }
    .timeline-item.last::before { display: none; }
    .timeline-dot {
      position: absolute; left: -2.55rem; top: 2px; width: 28px; height: 28px;
      border-radius: 50%; display: flex; align-items: center; justify-content: center;
      background: var(--surface-color); border: 2px solid var(--border-color); color: var(--text-muted);
    }
    .timeline-dot.draft { border-color: #94a3b8; color: #94a3b8; }
    .timeline-dot.submitted { border-color: #3b82f6; color: #3b82f6; }
    .timeline-dot.docs_pending { border-color: #f59e0b; color: #f59e0b; }
    .timeline-dot.docs_verified { border-color: #8b5cf6; color: #8b5cf6; }
    .timeline-dot.under_review { border-color: #a855f7; color: #a855f7; }
    .timeline-dot.approved { border-color: #10b981; color: #10b981; background: rgba(16,185,129,0.1); }
    .timeline-dot.rejected { border-color: #ef4444; color: #ef4444; background: rgba(239,68,68,0.1); }
    .timeline-content { display: flex; flex-direction: column; gap: 0.25rem; }
    .timeline-status { font-weight: 700; font-size: 0.875rem; text-transform: uppercase; }
    .timeline-note { font-size: 0.875rem; color: var(--text-muted); }
    .timeline-date { font-size: 0.75rem; color: var(--text-muted); opacity: 0.7; }

    .loading-state { display: flex; flex-direction: column; align-items: center; padding: 5rem; }
    .spinner { width: 40px; height: 40px; border: 3px solid rgba(255,255,255,0.1); border-top-color: var(--primary-color); border-radius: 50%; animation: spin 1s linear infinite; margin-bottom: 1rem; }
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
  readonly CircleX = CircleX; readonly Banknote = Banknote; readonly UserIcon = User; readonly Briefcase = Briefcase;

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
      default: return Clock;
    }
  }

  goBack() { this.router.navigate(['/applicant/dashboard']); }
}
