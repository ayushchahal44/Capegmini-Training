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
  template: `
    <div class="documents-container fade-in">
      <div class="page-header">
        <button (click)="goBack()" class="btn-icon-large">
          <lucide-icon [name]="ArrowLeft" [size]="20"></lucide-icon>
        </button>
        <div class="header-content">
          <h1>Document Verification #{{application?.id}}</h1>
          <p class="header-subtitle">Page 2 of 3 - Document Review</p>
        </div>
        <div class="page-navigation">
          <button (click)="goToSummary()" class="btn btn-ghost nav-btn">
            <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
            Previous: Info
          </button>
          <button (click)="goToDecision()" class="btn btn-primary nav-btn">
            Next: Decision
            <lucide-icon [name]="ArrowRight" [size]="18"></lucide-icon>
          </button>
        </div>
      </div>

      <div class="content-grid" *ngIf="application">
        <div class="main-section">
          <app-document-panel 
            [documents]="documents"
            (documentVerified)="onDocumentVerified($event)"
            (documentRejected)="onDocumentRejected($event)"
            (reuploadRequested)="onReuploadRequested($event)">
          </app-document-panel>
        </div>
      </div>

      <div class="loading-state" *ngIf="!application">
        <div class="spinner"></div>
        <p>Loading application details...</p>
      </div>
    </div>
  `,
  styles: [`
    .documents-container { max-width: 1200px; margin: 0 auto; padding: 2.5rem; }
    
    .page-header { 
      display: flex; 
      align-items: center; 
      gap: 2rem; 
      margin-bottom: 3rem;
      position: relative;
    }
    
    @media (max-width: 768px) {
      .page-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 1rem;
      }
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
