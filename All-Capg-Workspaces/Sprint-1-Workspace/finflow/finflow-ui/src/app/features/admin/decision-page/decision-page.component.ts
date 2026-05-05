import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { AdminService } from '../../../core/services/admin.service';
import { LoanApplication } from '../../../core/models/application.model';
import { DocumentInfo } from '../../../core/models/admin.model';
import { SharedModule } from '../../../shared/shared.module';
import { DecisionPanelComponent, DecisionData } from '../review-panel/decision-panel/decision-panel.component';
import { DecisionModalComponent, DecisionModalData } from '../review-panel/decision-modal/decision-modal.component';
import { LucideAngularModule, ArrowLeft, ArrowRight } from 'lucide-angular';
import { Observable, combineLatest, map } from 'rxjs';

@Component({
  selector: 'app-decision-page',
  standalone: true,
  imports: [
    CommonModule, 
    SharedModule,
    MatDialogModule,
    LucideAngularModule,
    DecisionPanelComponent
  ],
  template: `
    <div class="decision-container fade-in">
      <div class="page-header">
        <button (click)="navigateBack()" class="btn-icon-large">
          <lucide-icon [name]="ArrowLeft" [size]="20"></lucide-icon>
        </button>
        <div class="header-content">
          <h1>Decision Panel #{{application?.id}}</h1>
          <p class="header-subtitle">Page 3 of 3 - Final Decision</p>
        </div>
        <div class="page-navigation">
          <button (click)="goToDocuments()" class="btn btn-ghost nav-btn">
            <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
            Previous: Docs
          </button>
        </div>
      </div>

      <div class="content-grid" *ngIf="application">
        <div class="main-section">
          <app-decision-panel 
            [application]="application"
            [allDocumentsVerified]="allDocumentsVerified"
            [pendingDocuments]="pendingDocuments"
            (approve)="onApprove($event)"
            (reject)="onReject($event)">
          </app-decision-panel>
        </div>
      </div>

      <div class="loading-state" *ngIf="!application">
        <div class="spinner"></div>
        <p>Loading application details...</p>
      </div>
    </div>
  `,
  styles: [`
    .decision-container { max-width: 1200px; margin: 0 auto; padding: 2.5rem; }
    
    .page-header { 
      display: flex; 
      align-items: center; 
      gap: 2rem; 
      margin-bottom: 3rem;
      position: relative;
    }
    
    .header-content h1 { font-size: 2.25rem; font-weight: 800; letter-spacing: -0.02em; margin-bottom: 4px; }
    .header-subtitle { color: var(--text-muted); font-weight: 600; font-size: 0.9rem; }
    
    .btn-icon-large {
      width: 48px; height: 48px; border-radius: 12px; display: flex; align-items: center; justify-content: center;
      background: rgba(255,255,255,0.03); border: 1px solid var(--border); color: var(--text-secondary);
      cursor: pointer; transition: var(--transition);
    }
    .btn-icon-large:hover { background: rgba(255,255,255,0.08); color: var(--text-primary); border-color: rgba(255,255,255,0.2); }
    
    .page-navigation { margin-left: auto; display: flex; gap: 1rem; }
    .nav-btn { min-width: 160px; display: flex; align-items: center; gap: 10px; justify-content: center; }

    .content-grid { display: flex; gap: 2.5rem; }
    .main-section { flex: 1; }

    .loading-state {
      display: flex; flex-direction: column; align-items: center; justify-content: center;
      padding: 6rem; gap: 1.5rem;
    }
    .spinner { width: 50px; height: 50px; border: 3px solid rgba(99, 102, 241, 0.1); border-top-color: var(--accent); border-radius: 50%; animation: spin 1s linear infinite; }
    @keyframes spin { to { transform: rotate(360deg); } }
  `]
})
export class DecisionPageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appService = inject(ApplicationService);
  private docService = inject(DocumentService);
  private adminService = inject(AdminService);
  private dialog = inject(MatDialog);

  application: LoanApplication | null = null;
  documents: DocumentInfo[] = [];
  pendingDocuments: DocumentInfo[] = [];
  allDocumentsVerified = false;

  public ArrowLeft = ArrowLeft;
  public ArrowRight = ArrowRight;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.loadApplicationData(id);
      }
    });
  }

  private loadApplicationData(applicationId: number) {
    combineLatest([
      this.appService.getApplicationById(applicationId),
      this.docService.getDocuments(applicationId)
    ]).pipe(
      map(([app, docs]) => ({ application: app, documents: docs || [] }))
    ).subscribe(({ application, documents }) => {
      this.application = application;
      this.documents = documents;
      this.updateDocumentVerificationStatus();
    });
  }

  private updateDocumentVerificationStatus() {
    // Only consider the latest document of each type for verification
    const latestDocs = new Map<string, DocumentInfo>();
    this.documents.forEach(doc => {
      if (!latestDocs.has(doc.docType) || new Date(doc.createdAt) > new Date(latestDocs.get(doc.docType)!.createdAt)) {
        latestDocs.set(doc.docType, doc);
      }
    });

    const activeDocs = Array.from(latestDocs.values());
    this.pendingDocuments = activeDocs.filter(doc => doc.status !== 'VERIFIED');
    this.allDocumentsVerified = activeDocs.length >= 3 && this.pendingDocuments.length === 0;
  }

  navigateBack() {
    this.router.navigate(['/admin/dashboard']);
  }

  goToDocuments() {
    if (this.application) {
      this.router.navigate(['/admin/review', this.application.id, 'documents']);
    }
  }

  onApprove(decisionData: DecisionData) {
    if (!this.application) return;

    const dialogRef = this.dialog.open(DecisionModalComponent, {
      width: '600px',
      data: {
        application: this.application,
        decisionType: 'approve',
        decisionData
      } as DecisionModalData
    });

    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed && this.application) {
        this.adminService.decide(this.application.id, true)
          .subscribe(() => this.goBack());
      }
    });
  }

  onReject(decisionData: DecisionData) {
    if (!this.application) return;

    const dialogRef = this.dialog.open(DecisionModalComponent, {
      width: '600px',
      data: {
        application: this.application,
        decisionType: 'reject',
        decisionData
      } as DecisionModalData
    });

    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed && this.application) {
        this.adminService.decide(this.application.id, false)
          .subscribe(() => this.navigateBack());
      }
    });
  }

  canDecide() {
    return this.application && ['DOCS_VERIFIED', 'UNDER_REVIEW'].includes(this.application.status);
  }

  goBack() { 
    this.router.navigate(['/admin/dashboard']); 
  }
}
