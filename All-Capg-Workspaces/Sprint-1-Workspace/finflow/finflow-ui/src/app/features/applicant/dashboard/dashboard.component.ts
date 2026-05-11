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
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
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
