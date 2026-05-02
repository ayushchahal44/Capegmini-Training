import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { AuthService } from '../../../core/services/auth.service';
import { LoanApplication } from '../../../core/models/application.model';
import { LucideAngularModule, Plus, FileText, ChevronRight, Clock, CircleCheck, CircleAlert } from 'lucide-angular';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="dashboard-container fade-in">
      <header class="dashboard-header">
        <div>
          <h1>Welcome, {{ authService.currentUser()?.firstName }}</h1>
          <p>Track and manage your loan applications</p>
        </div>
        <button (click)="createNewApplication()" class="btn btn-primary" [disabled]="creating">
          <lucide-icon [name]="Plus" [size]="18"></lucide-icon>
          New Application
        </button>
      </header>

      @if (loading) {
        <div class="loading-state">
          <div class="spinner"></div>
          <p>Loading applications...</p>
        </div>
      } @else if (applications.length === 0) {
        <div class="glass empty-state">
          <lucide-icon [name]="FileText" [size]="48" class="empty-icon"></lucide-icon>
          <h3>No applications found</h3>
          <p>You haven't started any loan applications yet.</p>
          <button (click)="createNewApplication()" class="btn btn-primary">Start Your First Application</button>
        </div>
      } @else {
        <div class="stats-grid">
          <div class="glass stat-card">
            <span class="stat-label">Total Applications</span>
            <span class="stat-value">{{ applications.length }}</span>
          </div>
          <div class="glass stat-card">
            <span class="stat-label">In Progress</span>
            <span class="stat-value">{{ getInProgressCount() }}</span>
          </div>
          <div class="glass stat-card">
            <span class="stat-label">Approved</span>
            <span class="stat-value text-success">{{ getApprovedCount() }}</span>
          </div>
        </div>

        <div class="applications-list">
          <h2>Your Applications</h2>
          <div class="list-container">
            @for (app of applications; track app.id) {
              <div class="glass app-item" (click)="openApplication(app)">
                <div class="app-icon" [class]="app.status.toLowerCase()">
                  <lucide-icon [name]="getStatusIcon(app.status)" [size]="20"></lucide-icon>
                </div>
                <div class="app-info">
                  <div class="app-main">
                    <h3>Loan #{{ app.id }}</h3>
                    <span class="app-date">Updated {{ app.updatedAt | date:'mediumDate' }}</span>
                  </div>
                  <div class="app-details">
                    <span class="app-amount">{{ app.loanAmount | currency:'INR':'symbol':'1.0-0' }}</span>
                    <span class="app-status-badge" [class]="app.status.toLowerCase()">
                      {{ app.status.replace('_', ' ') }}
                    </span>
                  </div>
                </div>
                <lucide-icon [name]="ChevronRight" [size]="20" class="arrow-icon"></lucide-icon>
              </div>
            }
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .dashboard-container { max-width: 1000px; margin: 0 auto; padding: 2rem 1.5rem; }
    .dashboard-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 3rem; }
    .dashboard-header h1 { font-size: 2rem; margin-bottom: 0.25rem; }
    .dashboard-header p { color: var(--text-muted); }
    .loading-state, .empty-state { display: flex; flex-direction: column; align-items: center; justify-content: center; padding: 5rem 2rem; text-align: center; }
    .empty-icon { color: var(--text-muted); margin-bottom: 1.5rem; opacity: 0.5; }
    .empty-state h3 { margin-bottom: 0.5rem; }
    .empty-state p { color: var(--text-muted); margin-bottom: 2rem; }
    .stats-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1.5rem; margin-bottom: 3rem; }
    .stat-card { padding: 1.5rem; display: flex; flex-direction: column; gap: 0.5rem; }
    .stat-label { font-size: 0.875rem; color: var(--text-muted); font-weight: 500; }
    .stat-value { font-size: 1.5rem; font-weight: 700; }
    .text-success { color: var(--primary-color); }
    .applications-list h2 { font-size: 1.25rem; margin-bottom: 1.5rem; }
    .list-container { display: flex; flex-direction: column; gap: 1rem; }
    .app-item { display: flex; align-items: center; gap: 1.25rem; padding: 1.25rem; cursor: pointer; transition: all 0.2s; }
    .app-item:hover { transform: translateX(4px); border-color: var(--primary-color); }
    .app-icon { width: 44px; height: 44px; border-radius: 12px; display: flex; align-items: center; justify-content: center; }
    .app-icon.draft { background: rgba(148,163,184,0.1); color: #94a3b8; }
    .app-icon.submitted { background: rgba(59,130,246,0.1); color: #3b82f6; }
    .app-icon.docs_pending { background: rgba(245,158,11,0.1); color: #f59e0b; }
    .app-icon.docs_verified { background: rgba(139,92,246,0.1); color: #8b5cf6; }
    .app-icon.under_review { background: rgba(168,85,247,0.1); color: #a855f7; }
    .app-icon.approved { background: rgba(16,185,129,0.1); color: #10b981; }
    .app-icon.rejected { background: rgba(239,68,68,0.1); color: #ef4444; }
    .app-info { flex: 1; display: flex; align-items: center; justify-content: space-between; }
    .app-main h3 { font-size: 1rem; margin-bottom: 0.125rem; }
    .app-date { font-size: 0.75rem; color: var(--text-muted); }
    .app-details { display: flex; flex-direction: column; align-items: flex-end; gap: 0.5rem; }
    .app-amount { font-weight: 700; font-size: 1rem; }
    .app-status-badge { font-size: 0.7rem; font-weight: 700; text-transform: uppercase; padding: 0.2rem 0.6rem; border-radius: 100px; background: rgba(255,255,255,0.05); }
    .app-status-badge.draft { color: #94a3b8; } .app-status-badge.submitted { color: #3b82f6; }
    .app-status-badge.docs_pending { color: #f59e0b; } .app-status-badge.docs_verified { color: #8b5cf6; }
    .app-status-badge.approved { color: #10b981; } .app-status-badge.rejected { color: #ef4444; }
    .arrow-icon { color: var(--text-muted); opacity: 0.5; }
    .spinner { width: 40px; height: 40px; border: 3px solid rgba(255,255,255,0.1); border-top-color: var(--primary-color); border-radius: 50%; animation: spin 1s linear infinite; margin-bottom: 1rem; }
    @keyframes spin { to { transform: rotate(360deg); } }
  `]
})
export class DashboardComponent implements OnInit {
  readonly authService = inject(AuthService);
  private readonly appService = inject(ApplicationService);
  private readonly router = inject(Router);

  readonly Plus = Plus;
  readonly FileText = FileText;
  readonly ChevronRight = ChevronRight;

  applications: LoanApplication[] = [];
  loading = true;
  creating = false;

  ngOnInit() {
    this.loadApplications();
  }

  loadApplications() {
    this.appService.getApplications().subscribe({
      next: (apps) => { this.applications = apps; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  createNewApplication() {
    this.creating = true;
    this.appService.createDraft().subscribe({
      next: (app) => {
        this.creating = false;
        this.router.navigate(['/applicant/loan-wizard', app.id]);
      },
      error: () => { this.creating = false; }
    });
  }

  openApplication(app: LoanApplication) {
    if (app.status === 'DRAFT') {
      this.router.navigate(['/applicant/loan-wizard', app.id]);
    } else {
      this.router.navigate(['/applicant/application', app.id]);
    }
  }

  getInProgressCount() {
    return this.applications.filter(a => !['APPROVED', 'REJECTED'].includes(a.status)).length;
  }

  getApprovedCount() {
    return this.applications.filter(a => a.status === 'APPROVED').length;
  }

  getStatusIcon(status: string) {
    switch (status) {
      case 'DRAFT': return FileText;
      case 'SUBMITTED': case 'UNDER_REVIEW': return Clock;
      case 'DOCS_PENDING': return CircleAlert;
      case 'APPROVED': return CircleCheck;
      case 'REJECTED': return CircleAlert;
      default: return FileText;
    }
  }
}
