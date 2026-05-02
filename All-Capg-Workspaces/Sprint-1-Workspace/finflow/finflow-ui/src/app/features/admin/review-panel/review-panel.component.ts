import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../../core/services/admin.service';
import { ApplicationService } from '../../../core/services/application.service';
import { LoanApplication } from '../../../core/models/application.model';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'app-review-panel',
  standalone: true,
  imports: [CommonModule, SharedModule],
  template: `
    <div class="review-container">
      <button mat-icon-button (click)="goBack()"><mat-icon>arrow_back</mat-icon></button>
      <h2>Review Application #{{application?.id}}</h2>

      <mat-card *ngIf="application">
        <mat-card-content>
          <p><strong>Applicant:</strong> {{application.fullName}}</p>
          <p><strong>Amount:</strong> {{application.loanAmount | currency:'INR'}}</p>
          <p><strong>Status:</strong> {{application.status}}</p>
          
          <div class="actions" *ngIf="canDecide()">
            <button mat-raised-button color="primary" (click)="approve()">Approve</button>
            <button mat-raised-button color="warn" (click)="reject()">Reject</button>
          </div>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .review-container { padding: 20px; }
    .actions { margin-top: 20px; display: flex; gap: 10px; }
  `]
})
export class ReviewPanelComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private adminService = inject(AdminService);
  private appService = inject(ApplicationService);

  application: LoanApplication | null = null;

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.appService.getApplicationById(id).subscribe(app => this.application = app);
  }

  canDecide() {
    return this.application && ['DOCS_VERIFIED', 'UNDER_REVIEW'].includes(this.application.status);
  }

  approve() {
    if (!this.application) return;
    this.adminService.decide(this.application.id, true).subscribe(() => this.goBack());
  }

  reject() {
    if (!this.application) return;
    this.adminService.decide(this.application.id, false).subscribe(() => this.goBack());
  }

  goBack() { this.router.navigate(['/admin/dashboard']); }
}
