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
  templateUrl: './review-panel.component.html',
  styleUrl: './review-panel.component.css'
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
