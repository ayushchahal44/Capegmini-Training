import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { AuthService } from '../../../core/services/auth.service';
import { LoanApplication } from '../../../core/models/application.model';
import { LucideAngularModule, Plus, FileText, ChevronRight, Clock, CircleCheck, CircleAlert, Search, ArrowLeft } from 'lucide-angular';
import { Subscription } from 'rxjs';
import { SearchService } from '../../../core/services/search.service';

@Component({
  selector: 'app-applications-list',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="list-container fade-in">
      <header class="list-header">
        <div class="header-left">
          <button class="btn-back" (click)="goBack()">
            <lucide-icon [name]="ArrowLeft" [size]="20"></lucide-icon>
          </button>
          <div class="header-text">
            <h1>My Applications</h1>
            <p class="text-muted">Manage and track your loan requests</p>
          </div>
        </div>
        <button (click)="createNew()" class="btn btn-primary" [disabled]="creating">
          <lucide-icon [name]="Plus" [size]="18"></lucide-icon>
          New Application
        </button>
      </header>

      <div class="search-filter-bar card">
        <div class="search-box">
          <lucide-icon [name]="Search" [size]="18"></lucide-icon>
          <input type="text" placeholder="Search by ID, amount or status..." (input)="onSearch($event)">
        </div>
      </div>

      @if (loading) {
        <div class="loading-state">
          <div class="spinner"></div>
          <p>Loading applications...</p>
        </div>
      } @else if (applications.length === 0) {
        <div class="glass-card empty-state">
          <lucide-icon [name]="FileText" [size]="64" class="empty-icon"></lucide-icon>
          <h2>No applications yet</h2>
          <p class="text-muted">You haven't started any loan applications.</p>
          <button (click)="createNew()" class="btn btn-primary">Start Your First Application</button>
        </div>
      } @else {
        <div class="applications-grid">
          @for (app of filteredApplications; track app.id) {
            <div class="glass-card app-card card-hover" (click)="openApp(app)">
              <div class="app-card-header">
                <div class="status-chip" [class]="app.status.toLowerCase()">
                   <lucide-icon [name]="getStatusIcon(app.status)" [size]="14"></lucide-icon>
                   {{ app.status.replace('_', ' ') }}
                </div>
                <span class="app-id">#{{ app.id }}</span>
              </div>
              
              <div class="app-card-body">
                <div class="amount-group">
                  <span class="label">Requested Amount</span>
                  <span class="value">{{ app.loanAmount | currency:'INR' }}</span>
                </div>
                <div class="date-group">
                  <span class="label">Last Updated</span>
                  <span class="value">{{ app.updatedAt | date:'mediumDate' }}</span>
                </div>
              </div>

              <div class="app-card-footer">
                <span class="view-label">View Details</span>
                <lucide-icon [name]="ChevronRight" [size]="16"></lucide-icon>
              </div>
            </div>
          } @empty {
            <div class="no-results card">
              <p>No applications found matching your search.</p>
            </div>
          }
        </div>
      }
    </div>
  `,
  styles: [`
    .list-container { max-width: 1200px; margin: 0 auto; padding: 2rem; }
    .list-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; }
    .header-left { display: flex; align-items: center; gap: 1.5rem; }
    
    .btn-back { 
      width: 40px; height: 40px; border-radius: 12px; background: var(--bg-tertiary); 
      border: 1px solid var(--border); color: var(--text-primary); cursor: pointer;
      display: flex; align-items: center; justify-content: center; transition: var(--transition);
    }
    .btn-back:hover { background: var(--accent-soft); color: var(--accent); }

    .search-filter-bar { padding: 1rem; margin-bottom: 2rem; }
    .search-box { display: flex; align-items: center; gap: 1rem; background: var(--bg-secondary); padding: 0.75rem 1.25rem; border-radius: 12px; }
    .search-box input { background: none; border: none; color: var(--text-primary); width: 100%; outline: none; }

    .applications-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 1.5rem; }
    
    .app-card { padding: 1.5rem; display: flex; flex-direction: column; gap: 1.5rem; cursor: pointer; }
    .app-card-header { display: flex; justify-content: space-between; align-items: center; }
    
    .status-chip { 
      padding: 4px 12px; border-radius: 100px; font-size: 0.75rem; font-weight: 700; 
      display: flex; align-items: center; gap: 6px; text-transform: uppercase;
      background: var(--bg-tertiary); color: var(--text-secondary);
    }
    .status-chip.approved { background: rgba(16, 185, 129, 0.1); color: var(--success); }
    .status-chip.rejected { background: rgba(239, 68, 68, 0.1); color: var(--danger); }
    .status-chip.under_review { background: rgba(139, 92, 246, 0.1); color: var(--violet); }
    
    .app-id { font-family: monospace; color: var(--text-muted); font-weight: 600; }
    
    .app-card-body { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
    .label { display: block; font-size: 0.7rem; color: var(--text-muted); text-transform: uppercase; font-weight: 700; margin-bottom: 4px; }
    .value { font-weight: 700; color: var(--text-primary); }

    .app-card-footer { 
      margin-top: auto; padding-top: 1rem; border-top: 1px solid var(--border);
      display: flex; justify-content: space-between; align-items: center;
      color: var(--accent); font-weight: 700; font-size: 0.875rem;
    }

    .loading-state, .empty-state { text-align: center; padding: 5rem 0; display: flex; flex-direction: column; align-items: center; gap: 1.5rem; }
    .spinner { width: 40px; height: 40px; border: 3px solid var(--border); border-top-color: var(--accent); border-radius: 50%; animation: spin 1s linear infinite; }
  `]
})
export class ApplicationsListComponent implements OnInit, OnDestroy {
  private readonly appService = inject(ApplicationService);
  private readonly router = inject(Router);
  private readonly searchService = inject(SearchService);
  private searchSub?: Subscription;
  
  readonly Plus = Plus;
  readonly FileText = FileText;
  readonly ChevronRight = ChevronRight;
  readonly Search = Search;
  readonly ArrowLeft = ArrowLeft;
  readonly Clock = Clock;
  readonly CircleCheck = CircleCheck;
  readonly CircleAlert = CircleAlert;

  applications: LoanApplication[] = [];
  filteredApplications: LoanApplication[] = [];
  loading = true;
  creating = false;
  searchTerm = '';

  ngOnInit() {
    this.loadApplications();
    this.searchSub = this.searchService.searchTerm$.subscribe(term => {
      this.searchTerm = term.toLowerCase();
      this.applyFilters();
    });
  }

  ngOnDestroy() {
    this.searchSub?.unsubscribe();
  }


  loadApplications() {
    this.appService.getApplications().subscribe({
      next: (apps) => {
        this.applications = apps;
        this.applyFilters();
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  onSearch(event: any) {
    this.searchTerm = event.target.value.toLowerCase();
    this.applyFilters();
  }

  applyFilters() {
    this.filteredApplications = this.applications.filter(app => {
      return !this.searchTerm || 
        app.id.toString().includes(this.searchTerm) || 
        app.status.toLowerCase().includes(this.searchTerm) ||
        app.loanAmount.toString().includes(this.searchTerm);
    });
  }

  createNew() {
    this.creating = true;
    this.appService.createDraft().subscribe({
      next: (app) => {
        this.creating = false;
        this.router.navigate(['/applicant/loan-wizard', app.id]);
      },
      error: () => { this.creating = false; }
    });
  }

  openApp(app: LoanApplication) {
    if (app.status === 'DRAFT') {
      this.router.navigate(['/applicant/loan-wizard', app.id]);
    } else {
      this.router.navigate(['/applicant/application', app.id]);
    }
  }

  goBack() {
    this.router.navigate(['/applicant/dashboard']);
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
