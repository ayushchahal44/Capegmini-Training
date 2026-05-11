import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../../core/services/document.service';
import { ApplicationService } from '../../../core/services/application.service';
import { LoanApplication } from '../../../core/models/application.model';
import { DocumentInfo } from '../../../core/models/admin.model';
import { SharedModule } from '../../../shared/shared.module';
import { DocumentPanelComponent } from '../review-panel/document-panel/document-panel.component';
import { LucideAngularModule, ArrowLeft, ArrowRight } from 'lucide-angular';
import { Observable, combineLatest, map } from 'rxjs';

@Component({
  selector: 'app-document-verification-page',
  standalone: true,
  imports: [
    CommonModule, 
    SharedModule,
    LucideAngularModule,
    DocumentPanelComponent
  ],
  templateUrl: './document-verification-page.component.html',
  styleUrl: './document-verification-page.component.css'
})
export class DocumentVerificationPageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appService = inject(ApplicationService);
  private docService = inject(DocumentService);

  application: LoanApplication | null = null;
  documents: DocumentInfo[] = [];

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
      
      // Filter to only show the latest document of each type
      const latestDocsMap = new Map<string, DocumentInfo>();
      documents.forEach(doc => {
        if (!latestDocsMap.has(doc.docType) || new Date(doc.createdAt) > new Date(latestDocsMap.get(doc.docType)!.createdAt)) {
          latestDocsMap.set(doc.docType, doc);
        }
      });
      this.documents = Array.from(latestDocsMap.values());
    });
  }

  goBack() {
    this.router.navigate(['/admin/dashboard']);
  }

  goToSummary() {
    if (this.application) {
      this.router.navigate(['/admin/review', this.application.id, 'summary']);
    }
  }

  goToDecision() {
    if (this.application) {
      this.router.navigate(['/admin/review', this.application.id, 'decision']);
    }
  }

  onDocumentVerified(event: { document: DocumentInfo, verified: boolean }) {
    this.docService.verifyDocument(event.document.id, event.verified)
      .subscribe(updatedDoc => {
        const index = this.documents.findIndex(doc => doc.id === updatedDoc.id);
        if (index !== -1) {
          this.documents[index] = updatedDoc;
        }
      });
  }

  onDocumentRejected(document: DocumentInfo) {
    this.docService.verifyDocument(document.id, false, document.rejectionReason)
      .subscribe(updatedDoc => {
        const index = this.documents.findIndex(doc => doc.id === updatedDoc.id);
        if (index !== -1) {
          this.documents[index] = updatedDoc;
        }
      });
  }

  onReuploadRequested(document: DocumentInfo) {
    // Trigger reupload request - this would typically send notification to applicant
    console.log('Reupload requested for:', document);
    // You could also update document status to REJECTED to trigger reupload flow
    this.docService.verifyDocument(document.id, false)
      .subscribe(updatedDoc => {
        const index = this.documents.findIndex(doc => doc.id === updatedDoc.id);
        if (index !== -1) {
          this.documents[index] = updatedDoc;
        }
      });
  }
}
