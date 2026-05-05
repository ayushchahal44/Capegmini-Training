import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, User, Banknote, TrendingUp, Briefcase, Info, AlertTriangle, CheckCircle, Clock, XCircle } from 'lucide-angular';
import { LoanApplication } from '../../../../core/models/application.model';

@Component({
  selector: 'app-applicant-summary',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="glass-card applicant-summary-card">
      <div class="card-header">
        <h3 class="card-title">
          <lucide-icon [name]="UserIcon" [size]="20"></lucide-icon>
          Applicant Summary
        </h3>
      </div>
      
      <div class="card-content">
        <div class="applicant-info">
          <div class="info-row">
            <span class="label">Full Name</span>
            <span class="value">{{application?.fullName}}</span>
          </div>
          <div class="info-row">
            <span class="label">Phone</span>
            <span class="value">{{application?.phone}}</span>
          </div>
          <div class="info-row">
            <span class="label">Email</span>
            <span class="value">{{application?.applicantId}}&#64;finflow.com</span>
          </div>
        </div>

        <div class="divider"></div>

        <div class="loan-details">
          <h4 class="section-subtitle">Loan Details</h4>
          <div class="info-row">
            <div class="label-with-icon">
              <lucide-icon [name]="BanknoteIcon" [size]="16" class="text-accent"></lucide-icon>
              <span class="label">Amount</span>
            </div>
            <span class="value amount">{{application?.loanAmount | currency:'INR':'symbol':'1.0-0'}}</span>
          </div>
          <div class="info-row">
            <div class="label-with-icon">
              <lucide-icon [name]="TrendingUpIcon" [size]="16" class="text-accent"></lucide-icon>
              <span class="label">Monthly Income</span>
            </div>
            <span class="value">{{(application?.annualIncome || 0) / 12 | currency:'INR':'symbol':'1.0-0'}}</span>
          </div>
          <div class="info-row">
            <div class="label-with-icon">
              <lucide-icon [name]="BriefcaseIcon" [size]="16" class="text-accent"></lucide-icon>
              <span class="label">Employment</span>
            </div>
            <span class="value">{{application?.employmentType}}</span>
          </div>
          <div class="info-row">
            <span class="label">Employer</span>
            <span class="value">{{application?.employer}}</span>
          </div>
        </div>

        <div class="status-section">
          <div class="status-badge" [ngClass]="getStatusClass()">
            <lucide-icon [name]="getStatusIcon()" [size]="16"></lucide-icon>
            {{getStatusText()}}
          </div>
          
          <div class="risk-info">
            <span class="risk-label">Risk Level:</span>
            <div class="risk-badge" [ngClass]="getRiskClass()">
              <lucide-icon [name]="AlertTriangleIcon" [size]="14"></lucide-icon>
              {{getRiskLevel()}}
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .applicant-summary-card { padding: 2rem; border-radius: var(--radius-lg); height: 100%; }
    .card-header { margin-bottom: 2rem; }
    .card-title { display: flex; align-items: center; gap: 0.75rem; font-size: 1.25rem; font-weight: 800; color: var(--text-primary); }
    
    .applicant-info { display: flex; flex-direction: column; gap: 1rem; margin-bottom: 2rem; }
    .loan-details { margin-bottom: 2.5rem; }
    .section-subtitle { font-size: 0.9rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 1.5rem; }
    
    .info-row { display: flex; align-items: center; justify-content: space-between; padding: 0.75rem 0; border-bottom: 1px solid var(--border); }
    .info-row:last-child { border-bottom: none; }
    
    .label-with-icon { display: flex; align-items: center; gap: 0.75rem; }
    .label { font-size: 0.875rem; color: var(--text-secondary); font-weight: 500; }
    .value { font-weight: 700; color: var(--text-primary); font-size: 0.95rem; }
    .amount { color: var(--success); font-size: 1.1rem; }
    
    .divider { height: 1px; background: var(--border); margin: 2rem 0; opacity: 0.5; }
    
    .status-section { display: flex; flex-direction: column; gap: 1.5rem; padding: 1.5rem; background: rgba(255,255,255,0.03); border-radius: var(--radius-md); border: 1px solid var(--border); }
    
    .status-badge { 
      display: flex; align-items: center; justify-content: center; gap: 0.75rem; padding: 0.75rem 1.5rem; border-radius: 100px;
      font-weight: 800; font-size: 0.8rem; text-transform: uppercase; letter-spacing: 1px;
    }
    .status-badge.docs-pending { background: rgba(245,158,11,0.1); color: var(--warning); }
    .status-badge.under-review { background: rgba(59,130,246,0.1); color: var(--info); }
    .status-badge.docs-verified { background: rgba(139,92,246,0.1); color: var(--violet); }
    .status-badge.approved { background: rgba(16,185,129,0.1); color: var(--success); }
    .status-badge.rejected { background: rgba(239,68,68,0.1); color: var(--danger); }
    
    .risk-info { display: flex; align-items: center; justify-content: space-between; }
    .risk-label { font-size: 0.8rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase; }
    .risk-badge { display: flex; align-items: center; gap: 0.5rem; padding: 0.5rem 1rem; border-radius: 8px; font-weight: 700; font-size: 0.75rem; }
    .risk-badge.low { background: rgba(16,185,129,0.1); color: var(--success); }
    .risk-badge.medium { background: rgba(245,158,11,0.1); color: var(--warning); }
    .risk-badge.high { background: rgba(239,68,68,0.1); color: var(--danger); }
    
    .text-accent { color: var(--accent); }
  `]
})
export class ApplicantSummaryComponent {
  @Input() application: LoanApplication | null = null;

  readonly UserIcon = User;
  readonly BanknoteIcon = Banknote;
  readonly TrendingUpIcon = TrendingUp;
  readonly BriefcaseIcon = Briefcase;
  readonly AlertTriangleIcon = AlertTriangle;

  getStatusClass() {
    if (!this.application) return '';
    const status = this.application.status.toLowerCase();
    return status.replace('_', '-');
  }

  getStatusIcon() {
    if (!this.application) return Clock;
    
    switch (this.application.status) {
      case 'DOCS_PENDING': return Clock;
      case 'UNDER_REVIEW': return AlertTriangle;
      case 'DOCS_VERIFIED': return CheckCircle;
      case 'APPROVED': return CheckCircle;
      case 'REJECTED': return XCircle;
      default: return Clock;
    }
  }

  getStatusText() {
    if (!this.application) return '';
    return this.application.status.replace('_', ' ');
  }

  getRiskLevel() {
    if (!this.application) return 'Medium';
    const income = this.application.annualIncome || 1;
    const loanAmount = this.application.loanAmount || 0;
    const ratio = loanAmount / income;
    if (ratio < 0.3) return 'Low';
    if (ratio < 0.6) return 'Medium';
    return 'High';
  }

  getRiskClass() { return this.getRiskLevel().toLowerCase(); }
}
