import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, User, Banknote, TrendingUp, Briefcase, Info, TriangleAlert, CircleCheck, Clock, CircleX } from 'lucide-angular';
import { LoanApplication } from '../../../../core/models/application.model';

@Component({
  selector: 'app-applicant-summary',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './applicant-summary.component.html',
  styleUrl: './applicant-summary.component.css'
})
export class ApplicantSummaryComponent {
  @Input() application: LoanApplication | null = null;

  readonly UserIcon = User;
  readonly BanknoteIcon = Banknote;
  readonly TrendingUpIcon = TrendingUp;
  readonly BriefcaseIcon = Briefcase;
  readonly AlertTriangleIcon = TriangleAlert;

  getStatusClass() {
    if (!this.application) return '';
    const status = this.application.status.toLowerCase();
    return status.replace('_', '-');
  }

  getStatusIcon() {
    if (!this.application) return Clock;
    
    switch (this.application.status) {
      case 'DOCS_PENDING': return Clock;
      case 'UNDER_REVIEW': return TriangleAlert;
      case 'DOCS_VERIFIED': return CircleCheck;
      case 'APPROVED': return CircleCheck;
      case 'REJECTED': return CircleX;
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
