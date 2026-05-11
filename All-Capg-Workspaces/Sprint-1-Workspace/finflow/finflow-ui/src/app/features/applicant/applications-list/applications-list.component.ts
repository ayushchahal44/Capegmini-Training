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
  templateUrl: './applications-list.component.html',
  styleUrl: './applications-list.component.css'
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
