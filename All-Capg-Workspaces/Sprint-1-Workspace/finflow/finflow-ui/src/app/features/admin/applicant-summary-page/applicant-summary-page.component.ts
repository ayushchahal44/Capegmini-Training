import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { LoanApplication } from '../../../core/models/application.model';
import { SharedModule } from '../../../shared/shared.module';
import { ApplicantSummaryComponent } from '../review-panel/applicant-summary/applicant-summary.component';
import { StatusTimelineComponent } from '../review-panel/status-timeline/status-timeline.component';
import { LucideAngularModule, ArrowLeft, ArrowRight, FileText } from 'lucide-angular';
import { Observable, combineLatest, map } from 'rxjs';

@Component({
  selector: 'app-applicant-summary-page',
  standalone: true,
  imports: [
    CommonModule, 
    SharedModule,
    LucideAngularModule,
    StatusTimelineComponent,
    ApplicantSummaryComponent
  ],
  template: `
    <div class="summary-container fade-in">
      <div class="page-header">
        <button (click)="goBack()" class="btn-icon-large">
          <lucide-icon [name]="ArrowLeft" [size]="20"></lucide-icon>
        </button>
        <div class="header-content">
          <h1>Application Summary #{{application?.id}}</h1>
          <p class="header-subtitle">Page 1 of 3 - Applicant Information</p>
        </div>
        <div class="page-navigation">
          <button (click)="goToDocuments()" class="btn btn-primary nav-btn">
            <span>Next: Documents</span>
            <lucide-icon [name]="ArrowRight" [size]="18"></lucide-icon>
          </button>
        </div>
      </div>

      <div class="status-timeline-section" *ngIf="application">
        <app-status-timeline [application]="application"></app-status-timeline>
      </div>

      <div class="content-grid" *ngIf="application">
        <div class="main-section">
          <app-applicant-summary [application]="application"></app-applicant-summary>
        </div>
      </div>

      <div class="loading-state" *ngIf="!application">
        <div class="spinner"></div>
        <p>Loading application details...</p>
      </div>
    </div>
  `,
  styles: [`
    .summary-container { max-width: 1200px; margin: 0 auto; padding: 2.5rem; }
    
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
    
    .page-navigation { margin-left: auto; }
    .nav-btn { min-width: 180px; display: flex; align-items: center; gap: 8px; }

    .status-timeline-section { margin-bottom: 3rem; }
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
export class ApplicantSummaryPageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appService = inject(ApplicationService);
  private docService = inject(DocumentService);

  readonly ArrowLeft = ArrowLeft;
  readonly ArrowRight = ArrowRight;
  readonly FileText = FileText;

  application: LoanApplication | null = null;
  documents: any[] = [];

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
    });
  }

  goBack() {
    this.router.navigate(['/admin/dashboard']);
  }

  goToDocuments() {
    if (this.application) {
      this.router.navigate(['/admin/review', this.application.id, 'documents']);
    }
  }
}
