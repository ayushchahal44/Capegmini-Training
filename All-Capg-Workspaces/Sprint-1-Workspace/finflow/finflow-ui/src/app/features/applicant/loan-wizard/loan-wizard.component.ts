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
        <button (click)="goBack()" class="btn-icon">
          <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
        </button>
        <div class="header-text">
          <h1>Loan Application</h1>
          <p class="text-muted">Application #{{ applicationId }}</p>
        </div>
      </div>

      <!-- Step Indicator -->
      <div class="glass-card step-indicator">
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

      <div class="glass-card wizard-form">
        <!-- Step 1: Personal Info -->
        @if (currentStep === 0) {
          <div class="step-content fade-in">
            <div class="step-title">
              <div class="icon-box">
                <lucide-icon [name]="UserIcon" [size]="20"></lucide-icon>
              </div>
              <h2>Personal Information</h2>
            </div>
            <div class="form-grid">
              <div class="form-group">
                <label>Full Name</label>
                <div class="input-wrapper">
                  <input type="text" [(ngModel)]="formData.fullName" required placeholder="Enter your full legal name">
                </div>
              </div>
              <div class="form-group">
                <label>Phone Number</label>
                <div class="input-wrapper">
                  <input type="tel" [(ngModel)]="formData.phone" required placeholder="+91 XXXXX XXXXX">
                </div>
              </div>
              <div class="form-group full-width">
                <label>Permanent Address</label>
                <div class="input-wrapper">
                  <textarea [(ngModel)]="formData.address" required placeholder="Enter your full residential address" rows="3"></textarea>
                </div>
              </div>
              <div class="form-group">
                <label>Date of Birth</label>
                <div class="input-wrapper">
                  <input type="date" [(ngModel)]="formData.dateOfBirth" required>
                </div>
              </div>
            </div>
          </div>
        }

        <!-- Step 2: Employment -->
        @if (currentStep === 1) {
          <div class="step-content fade-in">
            <div class="step-title">
              <div class="icon-box">
                <lucide-icon [name]="Briefcase" [size]="20"></lucide-icon>
              </div>
              <h2>Employment Details</h2>
            </div>
            <div class="form-grid">
              <div class="form-group">
                <label>Employer / Business Name</label>
                <div class="input-wrapper">
                  <input type="text" [(ngModel)]="formData.employer" required placeholder="Company name">
                </div>
              </div>
              <div class="form-group">
                <label>Employment Type</label>
                <div class="input-wrapper">
                  <select [(ngModel)]="formData.employmentType" required>
                    <option value="">Select type</option>
                    <option value="SALARIED">Salaried</option>
                    <option value="SELF_EMPLOYED">Self Employed</option>
                    <option value="BUSINESS">Business</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label>Annual Income (₹)</label>
                <div class="input-wrapper">
                  <input type="number" [(ngModel)]="formData.annualIncome" required placeholder="e.g. 600000" min="0">
                </div>
              </div>
            </div>
          </div>
        }

        <!-- Step 3: Loan Details -->
        @if (currentStep === 2) {
          <div class="step-content fade-in">
            <div class="step-title">
              <div class="icon-box">
                <lucide-icon [name]="Banknote" [size]="20"></lucide-icon>
              </div>
              <h2>Loan Details</h2>
            </div>
            <div class="form-grid">
              <div class="form-group">
                <label>Loan Amount (₹)</label>
                <div class="input-wrapper">
                  <input type="number" [(ngModel)]="formData.loanAmount" required placeholder="e.g. 500000" min="10000">
                </div>
              </div>
              <div class="form-group">
                <label>Tenure (Months)</label>
                <div class="input-wrapper">
                  <select [(ngModel)]="formData.tenureMonths" required>
                    <option [ngValue]="null">Select tenure</option>
                    <option [ngValue]="12">12 Months</option>
                    <option [ngValue]="24">24 Months</option>
                    <option [ngValue]="36">36 Months</option>
                    <option [ngValue]="48">48 Months</option>
                    <option [ngValue]="60">60 Months</option>
                  </select>
                </div>
              </div>
              <div class="form-group full-width">
                <label>Purpose of Loan</label>
                <div class="input-wrapper">
                  <textarea [(ngModel)]="formData.loanPurpose" required placeholder="Describe the purpose..." rows="3"></textarea>
                </div>
              </div>
            </div>

            @if (formData.loanAmount && formData.tenureMonths) {
              <div class="emi-preview mesh-blue">
                <span class="emi-label">Estimated Monthly EMI</span>
                <span class="emi-value">{{ calculateEMI() | currency:'INR':'symbol':'1.0-0' }}</span>
                <p class="emi-note">&#64; 10.5% p.a. (indicative rate)</p>
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
          <button (click)="previousStep()" class="btn btn-icon" *ngIf="currentStep > 0">
            <lucide-icon [name]="ArrowLeft" [size]="18"></lucide-icon>
          </button>
          <div class="spacer" *ngIf="currentStep === 0"></div>

          @if (currentStep < steps.length - 1) {
            <button (click)="nextStep()" class="btn btn-primary" [disabled]="saving">
              @if (saving) {
                <lucide-icon [name]="LoaderIcon" [size]="18" class="spin"></lucide-icon>
                Saving...
              } @else {
                Next Step
                <lucide-icon [name]="ArrowRight" [size]="18"></lucide-icon>
              }
            </button>
          } @else {
            <button (click)="submitApplication()" class="btn btn-primary btn-submit" [disabled]="saving || submitting">
              @if (submitting) {
                <lucide-icon [name]="LoaderIcon" [size]="18" class="spin"></lucide-icon>
                Finalizing...
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
    .wizard-container { 
      max-width: 800px; 
      margin: 0 auto; 
      padding: var(--gap-md); 
    }
    
    .wizard-header { 
      display: flex; 
      align-items: center; 
      gap: 1.5rem; 
      margin-bottom: var(--gap-lg); 
    }
    
    .header-text h1 { font-size: 1.75rem; margin-bottom: 2px; }
    
    .step-indicator {
      display: flex; 
      align-items: center; 
      justify-content: center;
      padding: 1.5rem 2rem; 
      margin-bottom: var(--gap-lg); 
      gap: 0;
    }
    
    .step {
      display: flex; 
      flex-direction: column; 
      align-items: center; 
      gap: 8px; 
      z-index: 1;
    }
    
    .step-circle {
      width: 40px; 
      height: 40px; 
      border-radius: 50%; 
      display: flex; 
      align-items: center;
      justify-content: center; 
      font-weight: 800; 
      font-size: 0.935rem;
      background: var(--bg-tertiary); 
      border: 2px solid var(--border); 
      color: var(--text-muted);
      transition: var(--transition);
    }
    
    .step.active .step-circle { 
      border-color: var(--accent); 
      color: var(--accent); 
      background: var(--accent-soft);
      box-shadow: 0 0 15px var(--accent-glow);
    }
    
    .step.completed .step-circle { 
      background: var(--accent); 
      color: white; 
      border-color: var(--accent); 
    }
    
    .step-label { 
      font-size: 0.75rem; 
      color: var(--text-muted); 
      font-weight: 700; 
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    
    .step.active .step-label { color: var(--accent); }
    .step.completed .step-label { color: var(--text-primary); }
    
    .step-line {
      flex: 1; 
      height: 2px; 
      background: var(--border); 
      min-width: 40px; 
      margin: 0 10px;
      margin-bottom: 24px; 
      transition: var(--transition);
    }
    
    .step-line.completed { background: var(--accent); }

    .wizard-form { padding: 2.5rem; }
    
    .step-title { 
      display: flex; 
      align-items: center; 
      gap: 1rem; 
      margin-bottom: 2.5rem; 
    }
    
    .icon-box {
      width: 40px;
      height: 40px;
      border-radius: 12px;
      background: var(--accent-soft);
      color: var(--accent);
      display: flex;
      align-items: center;
      justify-content: center;
    }
    
    .step-title h2 { font-size: 1.35rem; }

    .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; }
    .full-width { grid-column: 1 / -1; }

    .form-group label {
      display: block;
      font-size: 0.875rem;
      font-weight: 600;
      color: var(--text-secondary);
      margin-bottom: 8px;
    }

    .input-wrapper input, .input-wrapper textarea, .input-wrapper select {
      width: 100%; 
      padding: 0.875rem 1.25rem; 
      background: var(--bg-tertiary);
      border: 1px solid var(--border); 
      border-radius: var(--radius-md);
      color: var(--text-primary); 
      font-family: inherit; 
      transition: var(--transition);
      outline: none;
    }
    
    .input-wrapper input:focus, .input-wrapper textarea:focus, .input-wrapper select:focus {
      border-color: var(--accent);
      box-shadow: 0 0 0 4px var(--accent-soft);
      background: var(--bg-secondary);
    }

    .emi-preview {
      margin-top: 2.5rem; 
      padding: 2rem; 
      border-radius: var(--radius-lg);
      border: 1px solid var(--glass-border);
      display: flex; 
      flex-direction: column; 
      align-items: center; 
      gap: 4px;
      text-align: center;
    }
    
    .mesh-blue { background: radial-gradient(at 0% 0%, rgba(59, 130, 246, 0.1), transparent 70%), var(--glass-bg); }
    
    .emi-label { font-size: 0.935rem; color: var(--text-secondary); font-weight: 600; }
    .emi-value { font-size: 2.5rem; font-weight: 800; color: var(--accent-bright); letter-spacing: -1px; }
    .emi-note { font-size: 0.815rem; color: var(--text-muted); margin-top: 8px; }

    .wizard-nav {
      display: flex; 
      justify-content: space-between; 
      align-items: center;
      margin-top: 3rem; 
      padding-top: 2rem; 
      border-top: 1px solid var(--border);
    }
    
    .spacer { flex: 1; }
    
    .error-alert {
      margin-top: 2rem; 
      padding: 1rem 1.5rem; 
      border-radius: var(--radius-md);
      background: rgba(239, 68, 68, 0.1); 
      border: 1px solid rgba(239, 68, 68, 0.2);
      color: var(--danger); 
      display: flex; 
      align-items: center; 
      gap: 12px; 
      font-size: 0.935rem; 
      font-weight: 600;
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
