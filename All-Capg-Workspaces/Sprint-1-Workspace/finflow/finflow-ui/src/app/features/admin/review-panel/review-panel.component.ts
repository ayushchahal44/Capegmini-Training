import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AdminService } from '../../../core/services/admin.service';
import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { LoanApplication } from '../../../core/models/application.model';
import { DocumentInfo } from '../../../core/models/admin.model';
import { SharedModule } from '../../../shared/shared.module';
import { ApplicantSummaryComponent } from './applicant-summary/applicant-summary.component';
import { DocumentPanelComponent } from './document-panel/document-panel.component';
import { DecisionPanelComponent, DecisionData } from './decision-panel/decision-panel.component';
import { StatusTimelineComponent } from './status-timeline/status-timeline.component';
import { DecisionModalComponent, DecisionModalData } from './decision-modal/decision-modal.component';
import { Observable, combineLatest, map } from 'rxjs';

@Component({
  selector: 'app-review-panel',
  standalone: true,
  imports: [
    CommonModule, 
    SharedModule,
    ApplicantSummaryComponent,
    DocumentPanelComponent,
    DecisionPanelComponent,
    StatusTimelineComponent
  ],
  template: `
    <div class="review-container">
      <div class="page-header">
        <button mat-icon-button (click)="goBack()" class="back-btn">
          <mat-icon>arrow_back</mat-icon>
        </button>
        <div class="header-content">
          <h1>Review Application #{{application?.id}}</h1>
          <p class="header-subtitle">Loan Underwriting System</p>
        </div>
      </div>

      <div class="status-timeline-section" *ngIf="application">
        <app-status-timeline [application]="application"></app-status-timeline>
      </div>

      <div class="main-content" *ngIf="application">
        <div class="content-grid">
          <!-- Left Section: Applicant Summary (30%) -->
          <div class="left-section">
            <app-applicant-summary [application]="application"></app-applicant-summary>
          </div>

          <!-- Center Section: Document Verification (40%) -->
          <div class="center-section">
            <app-document-panel 
              [documents]="documents"
              (documentVerified)="onDocumentVerified($event)"
              (documentRejected)="onDocumentRejected($event)"
              (reuploadRequested)="onReuploadRequested($event)">
            </app-document-panel>
          </div>

          <!-- Right Section: Decision Panel (30%) -->
          <div class="right-section">
            <app-decision-panel 
              [application]="application"
              [allDocumentsVerified]="allDocumentsVerified"
              (approve)="onApprove($event)"
              (reject)="onReject($event)">
            </app-decision-panel>
          </div>
        </div>
      </div>

      <div class="loading-state" *ngIf="!application">
        <mat-spinner></mat-spinner>
        <p>Loading application details...</p>
      </div>
    </div>
  `,
  styles: [`
    .review-container {
      padding: 24px;
      background: #f5f7fa;
      min-height: 100vh;
    }

    .page-header {
      display: flex;
      align-items: center;
      gap: 16px;
      margin-bottom: 24px;
      padding: 16px 24px;
      background: white;
      border-radius: 12px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .back-btn {
      background: #e3f2fd;
      color: #1976d2;
    }

    .header-content h1 {
      margin: 0 0 4px 0;
      font-size: 28px;
      font-weight: 700;
      color: #1a237e;
    }

    .header-subtitle {
      margin: 0;
      color: #666;
      font-size: 14px;
      font-weight: 500;
    }

    .status-timeline-section {
      margin-bottom: 24px;
    }

    .main-content {
      margin-bottom: 24px;
    }

    .content-grid {
      display: grid;
      grid-template-columns: 30% 40% 30%;
      gap: 24px;
      align-items: start;
    }

    .left-section,
    .center-section,
    .right-section {
      min-height: 600px;
    }

    .loading-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 64px;
      text-align: center;
    }

    .loading-state mat-spinner {
      margin-bottom: 16px;
    }

    .loading-state p {
      margin: 0;
      color: #666;
      font-size: 16px;
    }

    @media (max-width: 1200px) {
      .content-grid {
        grid-template-columns: 1fr;
        gap: 16px;
      }

      .left-section,
      .center-section,
      .right-section {
        min-height: auto;
      }
    }

    @media (max-width: 768px) {
      .review-container {
        padding: 16px;
      }

      .page-header {
        padding: 12px 16px;
      }

      .header-content h1 {
        font-size: 24px;
      }

      .content-grid {
        gap: 12px;
      }
    }
  `]
})
export class ReviewPanelComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private adminService = inject(AdminService);
  private appService = inject(ApplicationService);
  private documentService = inject(DocumentService);
  private dialog = inject(MatDialog);

  application: LoanApplication | null = null;
  documents: DocumentInfo[] = [];
  allDocumentsVerified = false;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadApplicationData(id);
    }
  }

  private loadApplicationData(applicationId: number) {
    combineLatest([
      this.appService.getApplicationById(applicationId),
      this.documentService.getDocuments(applicationId)
    ]).pipe(
      map(([app, docs]) => ({ application: app, documents: docs }))
    ).subscribe(({ application, documents }) => {
      this.application = application;
      this.documents = documents || [];
      this.updateDocumentVerificationStatus();
    });
  }

  private updateDocumentVerificationStatus() {
    this.allDocumentsVerified = this.documents.length > 0 && 
      this.documents.every(doc => doc.status === 'VERIFIED');
  }

  onDocumentVerified(event: { document: DocumentInfo, verified: boolean }) {
    this.documentService.verifyDocument(event.document.id, event.verified)
      .subscribe(updatedDoc => {
        const index = this.documents.findIndex(doc => doc.id === updatedDoc.id);
        if (index !== -1) {
          this.documents[index] = updatedDoc;
          this.updateDocumentVerificationStatus();
        }
      });
  }

  onDocumentRejected(document: DocumentInfo) {
    // Handle document rejection logic
    console.log('Document rejected:', document);
  }

  onReuploadRequested(document: DocumentInfo) {
    // Handle reupload request logic
    console.log('Reupload requested for:', document);
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
          .subscribe(() => this.goBack());
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
