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
  templateUrl: './applicant-summary-page.component.html',
  styleUrl: './applicant-summary-page.component.css'
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
