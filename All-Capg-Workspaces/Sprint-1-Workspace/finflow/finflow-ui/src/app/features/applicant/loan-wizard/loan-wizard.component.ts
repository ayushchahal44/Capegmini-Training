import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { LoanApplication } from '../../../core/models/application.model';
import { LucideAngularModule, User, Briefcase, Banknote, Check, ArrowLeft, ArrowRight, Loader, CircleAlert } from 'lucide-angular';

@Component({
  selector: 'app-loan-wizard',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  template: `
    <div class="wizard-container fade-in">
      <div class="wizard-header">
        <button (click)="goBack()" class="btn btn-ghost">
          <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
          Back
        </button>
        <h1>Loan Application</h1>
        <p>Application #{{ applicationId }}</p>
      </div>

      <!-- Step Indicator -->
      <div class="glass step-indicator">
        @for (step of steps; track step.index; let i = $index) {
          <div class="step" [class.active]="currentStep === i" [class.completed]="currentStep > i">
            <div class="step-circle">
              @if (currentStep > i) {
                <lucide-icon [name]="Check" [size]="16"></lucide-icon>
              } @else {
                {{ i + 1 }}
              }
            </div>
            <span class="step-label">{{ step.label }}</span>
          </div>
          @if (i < steps.length - 1) {
            <div class="step-line" [class.completed]="currentStep > i"></div>
          }
        }
      </div>

      <div class="glass wizard-form">
        <!-- Step 1: Personal Info -->
        @if (currentStep === 0) {
          <div class="step-content fade-in">
            <div class="step-title">
              <lucide-icon [name]="UserIcon" [size]="24"></lucide-icon>
              <h2>Personal Information</h2>
            </div>
            <div class="form-grid">
              <div class="form-group">
                <label for="fullName">Full Name *</label>
                <input type="text" id="fullName" [(ngModel)]="formData.fullName" required placeholder="Enter your full legal name">
              </div>
              <div class="form-group">
                <label for="phone">Phone Number *</label>
                <input type="tel" id="phone" [(ngModel)]="formData.phone" required placeholder="+91 XXXXX XXXXX">
              </div>
              <div class="form-group full-width">
                <label for="address">Address *</label>
                <textarea id="address" [(ngModel)]="formData.address" required placeholder="Enter your full address" rows="3"></textarea>
              </div>
              <div class="form-group">
                <label for="dob">Date of Birth *</label>
                <input type="date" id="dob" [(ngModel)]="formData.dateOfBirth" required>
              </div>
            </div>
          </div>
        }

        <!-- Step 2: Employment -->
        @if (currentStep === 1) {
          <div class="step-content fade-in">
            <div class="step-title">
              <lucide-icon [name]="Briefcase" [size]="24"></lucide-icon>
              <h2>Employment Details</h2>
            </div>
            <div class="form-grid">
              <div class="form-group">
                <label for="employer">Employer / Business Name *</label>
                <input type="text" id="employer" [(ngModel)]="formData.employer" required placeholder="Company name">
              </div>
              <div class="form-group">
                <label for="employmentType">Employment Type *</label>
                <select id="employmentType" [(ngModel)]="formData.employmentType" required>
                  <option value="">Select type</option>
                  <option value="SALARIED">Salaried</option>
                  <option value="SELF_EMPLOYED">Self Employed</option>
                  <option value="BUSINESS">Business</option>
                </select>
              </div>
              <div class="form-group">
                <label for="annualIncome">Annual Income (₹) *</label>
                <input type="number" id="annualIncome" [(ngModel)]="formData.annualIncome" required placeholder="e.g. 600000" min="0">
              </div>
            </div>
          </div>
        }

        <!-- Step 3: Loan Details -->
        @if (currentStep === 2) {
          <div class="step-content fade-in">
            <div class="step-title">
              <lucide-icon [name]="Banknote" [size]="24"></lucide-icon>
              <h2>Loan Details</h2>
            </div>
            <div class="form-grid">
              <div class="form-group">
                <label for="loanAmount">Loan Amount (₹) *</label>
                <input type="number" id="loanAmount" [(ngModel)]="formData.loanAmount" required placeholder="e.g. 500000" min="10000">
              </div>
              <div class="form-group">
                <label for="tenureMonths">Tenure (Months) *</label>
                <select id="tenureMonths" [(ngModel)]="formData.tenureMonths" required>
                  <option [ngValue]="null">Select tenure</option>
                  <option [ngValue]="12">12 Months</option>
                  <option [ngValue]="24">24 Months</option>
                  <option [ngValue]="36">36 Months</option>
                  <option [ngValue]="48">48 Months</option>
                  <option [ngValue]="60">60 Months</option>
                </select>
              </div>
              <div class="form-group full-width">
                <label for="loanPurpose">Purpose of Loan *</label>
                <textarea id="loanPurpose" [(ngModel)]="formData.loanPurpose" required placeholder="Describe the purpose..." rows="3"></textarea>
              </div>
            </div>

            @if (formData.loanAmount && formData.tenureMonths) {
              <div class="emi-preview">
                <span class="emi-label">Estimated Monthly EMI</span>
                <span class="emi-value">{{ calculateEMI() | currency:'INR':'symbol':'1.0-0' }}</span>
                <span class="emi-note">&#64; 10.5% p.a. (indicative)</span>
              </div>
            }
          </div>
        }

        <!-- Error Message Display -->
        @if (errorMessage) {
          <div class="error-alert fade-in">
            <lucide-icon [name]="CircleAlert" [size]="18"></lucide-icon>
            {{ errorMessage }}
          </div>
        }

        <!-- Navigation -->
        <div class="wizard-nav">
          @if (currentStep > 0) {
            <button (click)="previousStep()" class="btn btn-ghost">
              <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
              Previous
            </button>
          } @else {
            <div></div>
          }

          @if (currentStep < steps.length - 1) {
            <button (click)="nextStep()" class="btn btn-primary" [disabled]="saving">
              @if (saving) {
                <lucide-icon [name]="LoaderIcon" [size]="18" class="spin"></lucide-icon>
                Saving...
              } @else {
                Next
                <lucide-icon [name]="ArrowRight" [size]="18"></lucide-icon>
              }
            </button>
          } @else {
            <button (click)="submitApplication()" class="btn btn-primary btn-submit" [disabled]="saving || submitting">
              @if (submitting) {
                <lucide-icon [name]="LoaderIcon" [size]="18" class="spin"></lucide-icon>
                Submitting...
              } @else {
                Submit Application
                <lucide-icon [name]="Check" [size]="18"></lucide-icon>
              }
            </button>
          }
        </div>
      </div>
    </div>
  `,
  styles: [`
    .wizard-container { max-width: 800px; margin: 0 auto; padding: 2rem 1.5rem; }
    .wizard-header { margin-bottom: 2rem; }
    .wizard-header h1 { font-size: 1.75rem; margin-bottom: 0.25rem; }
    .wizard-header p { color: var(--text-muted); font-size: 0.875rem; }

    .step-indicator {
      display: flex; align-items: center; justify-content: center;
      padding: 1.5rem 2rem; margin-bottom: 2rem; border-radius: var(--radius-xl); gap: 0;
    }
    .step {
      display: flex; flex-direction: column; align-items: center; gap: 0.5rem; z-index: 1;
    }
    .step-circle {
      width: 36px; height: 36px; border-radius: 50%; display: flex; align-items: center;
      justify-content: center; font-weight: 700; font-size: 0.875rem;
      background: var(--surface-color); border: 2px solid var(--border-color); color: var(--text-muted);
      transition: all 0.3s;
    }
    .step.active .step-circle { border-color: var(--primary-color); color: var(--primary-color); background: rgba(16,185,129,0.1); }
    .step.completed .step-circle { background: var(--primary-color); color: white; border-color: var(--primary-color); }
    .step-label { font-size: 0.75rem; color: var(--text-muted); font-weight: 500; white-space: nowrap; }
    .step.active .step-label { color: var(--primary-color); }
    .step.completed .step-label { color: var(--text-main); }
    .step-line {
      flex: 1; height: 2px; background: var(--border-color); min-width: 40px; margin: 0 0.5rem;
      margin-bottom: 1.5rem; transition: background 0.3s;
    }
    .step-line.completed { background: var(--primary-color); }

    .wizard-form { padding: 2rem; border-radius: var(--radius-xl); }
    .step-title { display: flex; align-items: center; gap: 0.75rem; margin-bottom: 2rem; color: var(--primary-color); }
    .step-title h2 { font-size: 1.25rem; color: var(--text-main); }

    .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.25rem; }
    .full-width { grid-column: 1 / -1; }

    textarea, select {
      width: 100%; padding: 0.75rem 1rem; background: var(--surface-color);
      border: 1px solid var(--border-color); border-radius: var(--radius-md);
      color: var(--text-main); font-family: inherit; transition: border-color 0.2s, box-shadow 0.2s;
      resize: vertical;
    }
    textarea:focus, select:focus {
      outline: none; border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
    }
    select option { background: var(--surface-color); color: var(--text-main); }

    .emi-preview {
      margin-top: 2rem; padding: 1.5rem; border-radius: var(--radius-lg);
      background: rgba(16,185,129,0.05); border: 1px solid rgba(16,185,129,0.15);
      display: flex; flex-direction: column; align-items: center; gap: 0.25rem;
    }
    .emi-label { font-size: 0.875rem; color: var(--text-muted); }
    .emi-value { font-size: 1.75rem; font-weight: 700; color: var(--primary-color); }
    .emi-note { font-size: 0.75rem; color: var(--text-muted); }

    .wizard-nav {
      display: flex; justify-content: space-between; align-items: center;
      margin-top: 2.5rem; padding-top: 1.5rem; border-top: 1px solid var(--border-color);
    }
    .btn-submit { background: linear-gradient(135deg, var(--primary-color), var(--secondary-color)); }
    .spin { animation: spin 1s linear infinite; }
    @keyframes spin { to { transform: rotate(360deg); } }
    .error-alert {
      margin-top: 1.5rem; padding: 1rem; border-radius: var(--radius-md);
      background: rgba(239, 68, 68, 0.1); border: 1px solid rgba(239, 68, 68, 0.2);
      color: #ef4444; display: flex; align-items: center; gap: 0.5rem; font-size: 0.875rem; font-weight: 500;
    }
  `]
})
export class LoanWizardComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appService = inject(ApplicationService);

  readonly UserIcon = User;
  readonly Briefcase = Briefcase;
  readonly Banknote = Banknote;
  readonly Check = Check;
  readonly ArrowLeft = ArrowLeft;
  readonly ArrowRight = ArrowRight;
  readonly LoaderIcon = Loader;
  readonly CircleAlert = CircleAlert;

  applicationId = 0;
  currentStep = 0;
  saving = false;
  submitting = false;
  errorMessage = '';

  steps = [
    { index: 0, label: 'Personal Info' },
    { index: 1, label: 'Employment' },
    { index: 2, label: 'Loan Details' }
  ];

  formData: any = {
    fullName: '', phone: '', address: '', dateOfBirth: '',
    employer: '', employmentType: '', annualIncome: null,
    loanAmount: null, tenureMonths: null, loanPurpose: ''
  };

  ngOnInit() {
    this.applicationId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.applicationId) {
      this.appService.getApplicationById(this.applicationId).subscribe({
        next: (app) => {
          this.formData = {
            fullName: app.fullName || '', phone: app.phone || '', address: app.address || '',
            dateOfBirth: app.dateOfBirth || '', employer: app.employer || '',
            employmentType: app.employmentType || '', annualIncome: app.annualIncome || null,
            loanAmount: app.loanAmount || null, tenureMonths: app.tenureMonths || null,
            loanPurpose: app.loanPurpose || ''
          };
        }
      });
    }
  }

  nextStep() {
    this.saveDraft(() => {
      if (this.currentStep < this.steps.length - 1) this.currentStep++;
    });
  }

  previousStep() {
    if (this.currentStep > 0) this.currentStep--;
  }

  saveDraft(callback?: () => void) {
    this.saving = true;
    this.errorMessage = '';
    this.appService.updateDraft(this.applicationId, this.formData).subscribe({
      next: () => { this.saving = false; if (callback) callback(); },
      error: (err) => { 
        this.saving = false; 
        this.errorMessage = this.extractErrorMessage(err) || 'Failed to save draft. Please check your inputs.';
      }
    });
  }

  submitApplication() {
    this.saving = true;
    this.errorMessage = '';
    this.appService.updateDraft(this.applicationId, this.formData).subscribe({
      next: () => {
        this.saving = false;
        this.submitting = true;
        this.appService.submitApplication(this.applicationId).subscribe({
          next: () => { this.router.navigate(['/applicant/application', this.applicationId]); },
          error: (err) => { 
            this.submitting = false; 
            this.errorMessage = this.extractErrorMessage(err) || 'Failed to submit application. Please try again.';
          }
        });
      },
      error: (err) => { 
        this.saving = false; 
        this.errorMessage = this.extractErrorMessage(err) || 'Failed to save application before submission.';
      }
    });
  }

  private extractErrorMessage(err: any): string {
    if (err.error?.details) {
      // Extract the first validation error
      const keys = Object.keys(err.error.details);
      if (keys.length > 0) {
        return `${keys[0]}: ${err.error.details[keys[0]]}`;
      }
    }
    return err.error?.message || err.message || '';
  }

  calculateEMI(): number {
    const p = this.formData.loanAmount;
    const r = 10.5 / 12 / 100;
    const n = this.formData.tenureMonths;
    if (!p || !n) return 0;
    return (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
  }

  goBack() {
    this.router.navigate(['/applicant/dashboard']);
  }
}
