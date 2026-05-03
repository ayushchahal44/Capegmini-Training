import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule, ViewportScroller } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { SearchService } from '../../../core/services/search.service';
import { AdminService } from '../../../core/services/admin.service';
import { LoanApplication } from '../../../core/models/application.model';
import { LucideAngularModule, Search, Filter, Eye, CircleCheck, CircleX, FileText, Clock, Inbox, Loader2 } from 'lucide-angular';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit, OnDestroy {
  private readonly adminService = inject(AdminService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly scroller = inject(ViewportScroller);
  private readonly searchService = inject(SearchService);
  private searchSub?: Subscription;

  readonly icons = {
    search: Search,
    filter: Filter,
    eye: Eye,
    circleCheck: CircleCheck,
    circleX: CircleX,
    fileText: FileText,
    clock: Clock,
    inbox: Inbox,
    loader: Loader2
  };

  applications: LoanApplication[] = [];
  filteredApplications: LoanApplication[] = [];
  isLoading = true;
  searchTerm = '';
  statusFilter = 'ALL';
  decidingId: number | null = null;

  ngOnInit() {
    this.loadApplications();

    // Handle status filtering from query params
    this.route.queryParams.subscribe(params => {
      this.statusFilter = params['status'] || 'ALL';
      this.applyFilters();
    });

    // Handle scroll and initial fragment filter
    const initialFrag = this.route.snapshot.fragment;
    if (initialFrag === 'applications-list') {
      this.statusFilter = 'APPROVED';
      this.applyFilters();
    }

    this.route.fragment.subscribe(frag => {
      if (frag === 'applications-list') {
        this.statusFilter = 'APPROVED';
        this.applyFilters();
      }
      if (frag) {
        setTimeout(() => {
          const element = document.getElementById(frag);
          if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }
        }, 500);
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
    this.isLoading = true;
    this.adminService.getApplications().subscribe({
      next: (apps) => {
        this.applications = apps;
        this.applyFilters();
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  onSearch(event: any) {
    this.searchTerm = event.target.value.toLowerCase();
    this.applyFilters();
  }

  onFilter(event: any) {
    this.statusFilter = event.target.value;
    this.applyFilters();
  }

  applyFilters() {
    this.filteredApplications = this.applications.filter(app => {
      const matchSearch = !this.searchTerm ||
        (app.fullName?.toLowerCase().includes(this.searchTerm) ||
         app.id.toString().includes(this.searchTerm));
      const matchStatus = this.statusFilter === 'ALL' || app.status === this.statusFilter;
      return matchSearch && matchStatus;
    });
  }

  getCountByStatus(status: string): number {
    return this.applications.filter(a => a.status === status).length;
  }

  getPendingCount(): number {
    return this.applications.filter(a =>
      ['SUBMITTED', 'DOCS_PENDING', 'DOCS_VERIFIED', 'UNDER_REVIEW'].includes(a.status)
    ).length;
  }

  getInitials(name: string | undefined): string {
    if (!name) return 'DU';
    const parts = name.split(' ');
    return parts.length >= 2
      ? (parts[0][0] + parts[1][0]).toUpperCase()
      : parts[0].substring(0, 2).toUpperCase();
  }

  getStatusBadgeClass(status: string): string {
    const map: Record<string, string> = {
      'APPROVED': 'badge-success', 'REJECTED': 'badge-danger',
      'SUBMITTED': 'badge-info', 'UNDER_REVIEW': 'badge-purple',
      'DOCS_VERIFIED': 'badge-purple', 'DOCS_PENDING': 'badge-warning',
      'DRAFT': 'badge-neutral'
    };
    return map[status] || 'badge-neutral';
  }

  formatStatus(status: string): string {
    return status.replace(/_/g, ' ');
  }

  approveApp(app: LoanApplication) {
    this.decidingId = app.id;
    this.adminService.decide(app.id, true).subscribe({
      next: () => {
        app.status = 'APPROVED';
        this.decidingId = null;
        this.applyFilters();
      },
      error: () => {
        this.decidingId = null;
      }
    });
  }

  rejectApp(app: LoanApplication) {
    this.decidingId = app.id;
    this.adminService.decide(app.id, false).subscribe({
      next: () => {
        app.status = 'REJECTED';
        this.decidingId = null;
        this.applyFilters();
      },
      error: () => {
        this.decidingId = null;
      }
    });
  }

  viewDetails(id: number) {
    this.router.navigate(['/admin/review', id]);
  }
}
