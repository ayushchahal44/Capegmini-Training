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
  templateUrl: './decision-page.component.html',
  styleUrl: './decision-page.component.css'
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
          .subscribe({
            next: () => this.goBack(),
            error: (err) => {
              if (err.status === 409) {
                alert('This application has already been processed and cannot be modified.');
              } else {
                alert(err.error?.message || 'An error occurred while processing the decision.');
              }
            }
          });
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
          .subscribe({
            next: () => this.navigateBack(),
            error: (err) => {
              if (err.status === 409) {
                alert('This application has already been processed and cannot be modified.');
              } else {
                alert(err.error?.message || 'An error occurred while processing the decision.');
              }
            }
          });
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
