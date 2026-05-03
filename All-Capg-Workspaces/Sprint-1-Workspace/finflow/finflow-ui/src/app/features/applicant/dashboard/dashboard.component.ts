import { Component, inject, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { CommonModule, ViewportScroller } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { SearchService } from '../../../core/services/search.service';
import { ApplicationService } from '../../../core/services/application.service';
import { AuthService } from '../../../core/services/auth.service';
import { LoanApplication } from '../../../core/models/application.model';
import { LucideAngularModule, Plus, FileText, ChevronRight, Clock, CircleCheck, CircleAlert, Search } from 'lucide-angular';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="dashboard-container fade-in">
      <header class="dashboard-header">
        <div class="header-text">
          <h1>Welcome back, {{ authService.currentUser()?.firstName }}</h1>
          <p class="text-muted">Your financial journey at a glance</p>
        </div>
        <button (click)="createNewApplication()" class="btn btn-primary" [disabled]="creating">
          <lucide-icon [name]="Plus" [size]="18"></lucide-icon>
          New Application
        </button>
      </header>

      @if (loading) {
        <div class="loading-state">
          <div class="spinner"></div>
          <p class="text-muted">Fetching your applications...</p>
        </div>
      } @else if (applications.length === 0) {
        <div class="glass-card empty-state">
          <div class="empty-icon-wrapper">
            <lucide-icon [name]="FileText" [size]="48" class="empty-icon"></lucide-icon>
          </div>
          <h3>Ready to start?</h3>
          <p class="text-muted">Apply for a new loan in just a few minutes.</p>
          <button (click)="createNewApplication()" class="btn btn-primary">Start Application</button>
        </div>
      } @else {
        <div class="stats-grid">
          <div class="glass-card stat-card mesh-blue">
            <div class="stat-info">
              <span class="stat-label">Total Applications</span>
              <span class="stat-value">{{ applications.length }}</span>
            </div>
          </div>
          <div class="glass-card stat-card mesh-purple">
            <div class="stat-info">
              <span class="stat-label">In Progress</span>
              <span class="stat-value text-warning">{{ getInProgressCount() }}</span>
            </div>
          </div>
          <div class="glass-card stat-card mesh-green">
            <div class="stat-info">
              <span class="stat-label">Approved</span>
              <span class="stat-value text-success">{{ getApprovedCount() }}</span>
            </div>
          </div>
        </div>

        <div id="applications-list" class="applications-section">
          <div class="section-header">
            <h2>Recent Applications</h2>
            <div class="search-bar">
              <lucide-icon [name]="Search" [size]="18"></lucide-icon>
              <input type="text" placeholder="Search by ID or status..." (input)="onSearch($event)">
            </div>
          </div>
          
          <div class="applications-grid">
            @for (app of filteredApplications; track app.id) {
              <div class="glass-card app-card card-hover" (click)="openApplication(app)">
                <div class="app-status-indicator" [class]="app.status.toLowerCase()"></div>
                <div class="app-card-content">
                  <div class="app-top">
                    <div class="app-icon-box" [class]="app.status.toLowerCase()">
                      <lucide-icon [name]="getStatusIcon(app.status)" [size]="20"></lucide-icon>
                    </div>
                    <div class="app-meta">
                      <h3>Loan #{{ app.id }}</h3>
                      <span class="app-date">{{ app.updatedAt | date:'MMM d, y' }}</span>
                    </div>
                  </div>
                  
                  <div class="app-bottom">
                    <div class="app-amount-box">
                      <span class="label">Amount</span>
                      <span class="value">{{ app.loanAmount | currency:'INR':'symbol':'1.0-0' }}</span>
                    </div>
                    <span class="badge" [class]="'badge-' + getStatusClass(app.status)">
                      {{ app.status.replace('_', ' ') }}
                    </span>
                  </div>
                </div>
                <div class="app-card-overlay">
                  <span>View Details</span>
                  <lucide-icon [name]="ChevronRight" [size]="16"></lucide-icon>
                </div>
              </div>
            } @empty {
              <div class="glass-card empty-search">
                <p>No applications found matching "{{ searchTerm }}"</p>
              </div>
            }
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .dashboard-container { 
      max-width: 1200px; 
      margin: 0 auto; 
      padding: var(--gap-md);
    }
    
    .dashboard-header { 
      display: flex; 
      justify-content: space-between; 
      align-items: center; 
      margin-bottom: var(--gap-lg);
    }
    
    .header-text h1 { margin-bottom: 4px; }
    
    .stats-grid { 
      display: grid; 
      grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); 
      gap: var(--gap-md); 
      margin-bottom: var(--gap-lg); 
    }
    
    .stat-card {
      padding: 1.75rem;
      position: relative;
      overflow: hidden;
    }
    
    .stat-label { 
      display: block;
      font-size: 0.875rem; 
      color: var(--text-secondary); 
      font-weight: 600;
      margin-bottom: 8px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    
    .stat-value { 
      font-size: 2.25rem; 
      font-weight: 800; 
      letter-spacing: -1px;
    }

    /* Mesh Gradients */
    .mesh-blue { background: radial-gradient(at 0% 0%, rgba(59, 130, 246, 0.15) 0%, transparent 50%), var(--glass-bg); }
    .mesh-purple { background: radial-gradient(at 0% 0%, rgba(139, 92, 246, 0.15) 0%, transparent 50%), var(--glass-bg); }
    .mesh-green { background: radial-gradient(at 0% 0%, rgba(16, 185, 129, 0.15) 0%, transparent 50%), var(--glass-bg); }
    
    .applications-section {
      display: flex;
      flex-direction: column;
      gap: var(--gap-md);
    }
    
    .section-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 8px;
    }
    
    .search-bar {
      display: flex;
      align-items: center;
      gap: 12px;
      background: var(--bg-tertiary);
      padding: 0.75rem 1.25rem;
      border-radius: var(--radius-md);
      border: 1px solid var(--border);
      width: 320px;
      transition: var(--transition);
    }
    
    .search-bar:focus-within {
      border-color: var(--accent);
      box-shadow: 0 0 0 4px var(--accent-soft);
    }
    
    .search-bar input {
      background: none;
      border: none;
      color: var(--text-primary);
      outline: none;
      font-size: 0.935rem;
      width: 100%;
    }
    
    .applications-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(340px, 1fr));
      gap: var(--gap-md);
    }
    
    .app-card {
      padding: 1.5rem;
      cursor: pointer;
      position: relative;
    }
    
    .app-status-indicator {
      position: absolute;
      left: 0;
      top: 20%;
      bottom: 20%;
      width: 4px;
      border-radius: 0 4px 4px 0;
      opacity: 0.6;
    }
    
    .app-status-indicator.approved { background: var(--success); }
    .app-status-indicator.rejected { background: var(--danger); }
    .app-status-indicator.under_review { background: var(--violet); }
    .app-status-indicator.draft { background: var(--text-muted); }
    
    .app-top {
      display: flex;
      align-items: center;
      gap: 1rem;
      margin-bottom: 1.5rem;
    }
    
    .app-icon-box {
      width: 48px;
      height: 48px;
      border-radius: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: var(--bg-tertiary);
      border: 1px solid var(--border);
      color: var(--text-secondary);
      transition: var(--transition);
    }
    
    .app-icon-box.approved { background: rgba(16, 185, 129, 0.1); color: var(--success); }
    .app-icon-box.rejected { background: rgba(239, 68, 68, 0.1); color: var(--danger); }
    .app-icon-box.under_review { background: rgba(139, 92, 246, 0.1); color: var(--violet); }
    
    .app-meta h3 { font-size: 1.125rem; margin-bottom: 2px; }
    .app-date { font-size: 0.815rem; color: var(--text-muted); }
    
    .app-bottom {
      display: flex;
      justify-content: space-between;
      align-items: flex-end;
    }
    
    .app-amount-box .label {
      display: block;
      font-size: 0.75rem;
      color: var(--text-muted);
      text-transform: uppercase;
      font-weight: 700;
      margin-bottom: 4px;
    }
    
    .app-amount-box .value {
      font-size: 1.25rem;
      font-weight: 800;
      color: var(--text-primary);
    }
    
    .app-card-overlay {
      position: absolute;
      inset: 0;
      background: rgba(99, 102, 241, 0.9);
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      opacity: 0;
      transition: var(--transition);
      font-weight: 700;
      color: white;
      border-radius: inherit;
    }
    
    .app-card:hover .app-card-overlay { opacity: 1; }
    
    .empty-state {
      padding: 5rem 2rem;
      text-align: center;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 1.5rem;
    }
    
    .empty-icon-wrapper {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      background: var(--bg-tertiary);
      display: flex;
      align-items: center;
      justify-content: center;
      color: var(--text-muted);
      margin-bottom: 0.5rem;
    }
    
    .loading-state {
      padding: 8rem 0;
      text-align: center;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 1.5rem;
    }
    
    .spinner {
      width: 48px;
      height: 48px;
      border: 4px solid var(--bg-tertiary);
      border-top-color: var(--accent);
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }
  `]

})
export class DashboardComponent implements OnInit, OnDestroy {
  readonly authService = inject(AuthService);
  private readonly appService = inject(ApplicationService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly scroller = inject(ViewportScroller);
  private readonly searchService = inject(SearchService);
  private searchSub?: Subscription;

  readonly Plus = Plus;
  readonly FileText = FileText;
  readonly ChevronRight = ChevronRight;
  readonly Search = Search;

  applications: LoanApplication[] = [];
  filteredApplications: LoanApplication[] = [];
  loading = true;
  creating = false;
  searchTerm = '';

  ngOnInit() {
    this.loadApplications();
    this.route.fragment.subscribe(frag => {
      if (frag) {
        setTimeout(() => this.scroller.scrollToAnchor(frag), 100);
      }
    });

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
        app.status.toLowerCase().includes(this.searchTerm);
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

  getStatusClass(status: string) {
    switch (status) {
      case 'APPROVED': return 'success';
      case 'REJECTED': return 'danger';
      case 'UNDER_REVIEW': return 'info';
      case 'DOCS_PENDING': return 'warning';
      default: return 'muted';
    }
  }
}
