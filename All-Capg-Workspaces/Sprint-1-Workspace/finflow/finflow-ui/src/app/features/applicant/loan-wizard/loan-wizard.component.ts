import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { LoanApplication } from '../../../core/models/application.model';
import { LucideAngularModule, User, Briefcase, Banknote, Check, ArrowLeft, ArrowRight, Loader, AlertTriangle, Info, Sparkles } from 'lucide-angular';

@Component({
  selector: 'app-loan-wizard',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  template: `
    <div class="wizard-container fade-in">
      <div class="wizard-header">
        <button (click)="goBack()" class="btn-icon">
          <lucide-icon [name]="ArrowLeftIcon" [size]="18"></lucide-icon>
        </button>
        <div class="header-text">
          <h1>Loan Application</h1>
          <p class="text-muted">Application #{{ applicationId }}</p>
        </div>
      </div>

      <!-- Step Indicator -->
      <div class="glass-card step-indicator">
        @for (step of steps; track step.index; let i = $index) {
          <div class="step-item" [class.active]="currentStep === i" [class.completed]="currentStep > i">
            <div class="step-box">
              @if (currentStep > i) {
                <lucide-icon [name]="CheckIcon" [size]="18" class="text-success"></lucide-icon>
              } @else {
                <span class="step-num">{{ i + 1 }}</span>
              }
            </div>
            <span class="step-name">{{ step.label }}</span>
          </div>
          @if (i < steps.length - 1) {
            <div class="step-bridge" [class.completed]="currentStep > i"></div>
          }
        }
      </div>

      <div class="glass-card wizard-form-card">
        <!-- Step 1: Personal Info -->
        @if (currentStep === 0) {
          <div class="step-content fade-in">
            <div class="step-title-row">
              <div class="step-icon-glow">
                <lucide-icon [name]="UserIcon" [size]="20"></lucide-icon>
              </div>
              <div class="title-meta">
                <h2>Personal Information</h2>
                <p>Provide your legal identity and contact details</p>
              </div>
            </div>
            
            <div class="form-layout">
              <div class="input-field">
                <label>Full Legal Name</label>
                <div class="input-control">
                  <lucide-icon [name]="UserIcon" [size]="16" class="field-icon"></lucide-icon>
                  <input type="text" [(ngModel)]="formData.fullName" required placeholder="John Doe">
                </div>
              </div>
              <div class="input-field">
                <label>Contact Number</label>
                <div class="input-control">
                  <span class="prefix">+91</span>
                  <input type="tel" [(ngModel)]="formData.phone" required placeholder="9876543210">
                </div>
              </div>
              <div class="input-field full">
                <label>Residential Address</label>
                <div class="input-control">
                  <textarea [(ngModel)]="formData.address" required placeholder="House No, Street, Landmark, City, State, PIN" rows="3"></textarea>
                </div>
              </div>
              <div class="input-field">
                <label>Date of Birth</label>
                <div class="input-control">
                  <input type="date" [(ngModel)]="formData.dateOfBirth" required>
                </div>
              </div>
            </div>
          </div>
        }

        <!-- Step 2: Employment -->
        @if (currentStep === 1) {
          <div class="step-content fade-in">
            <div class="step-title-row">
              <div class="step-icon-glow">
                <lucide-icon [name]="BriefcaseIcon" [size]="20"></lucide-icon>
              </div>
              <div class="title-meta">
                <h2>Employment Details</h2>
                <p>Tell us about your professional background</p>
              </div>
            </div>
            <div class="form-layout">
              <div class="input-field">
                <label>Employer / Organization</label>
                <div class="input-control">
                  <input type="text" [(ngModel)]="formData.employer" required placeholder="Google Inc.">
                </div>
              </div>
              <div class="input-field">
                <label>Employment Type</label>
                <div class="input-control">
                  <select [(ngModel)]="formData.employmentType" required>
                    <option value="">Select type</option>
                    <option value="SALARIED">Salaried Employee</option>
                    <option value="SELF_EMPLOYED">Self Employed</option>
                    <option value="BUSINESS">Business Owner</option>
                  </select>
                </div>
              </div>
              <div class="input-field">
                <label>Annual Gross Income (₹)</label>
                <div class="input-control">
                  <lucide-icon [name]="BanknoteIcon" [size]="16" class="field-icon"></lucide-icon>
                  <input type="number" [(ngModel)]="formData.annualIncome" required placeholder="e.g. 1200000" min="0">
                </div>
                <span class="field-hint">Include all sources of income</span>
              </div>
            </div>
          </div>
        }

        <!-- Step 3: Loan Details -->
        @if (currentStep === 2) {
          <div class="step-content fade-in">
            <div class="step-title-row">
              <div class="step-icon-glow">
                <lucide-icon [name]="BanknoteIcon" [size]="20"></lucide-icon>
              </div>
              <div class="title-meta">
                <h2>Loan Requirements</h2>
                <p>Define the financial support you need</p>
              </div>
            </div>
            <div class="form-layout">
              <div class="input-field">
                <label>Requested Loan Amount (₹)</label>
                <div class="input-control amount-mode">
                  <input type="number" [(ngModel)]="formData.loanAmount" required placeholder="5,00,000" min="10000">
                  <span class="currency-tag">INR</span>
                </div>
              </div>
              <div class="input-field">
                <label>Preferred Tenure</label>
                <div class="input-control">
                  <select [(ngModel)]="formData.tenureMonths" required>
                    <option [ngValue]="null">Select duration</option>
                    <option [ngValue]="12">1 Year (12 Months)</option>
                    <option [ngValue]="24">2 Years (24 Months)</option>
                    <option [ngValue]="36">3 Years (36 Months)</option>
                    <option [ngValue]="48">4 Years (48 Months)</option>
                    <option [ngValue]="60">5 Years (60 Months)</option>
                  </select>
                </div>
              </div>
              <div class="input-field full">
                <label>Purpose of Loan</label>
                <div class="input-control">
                  <textarea [(ngModel)]="formData.loanPurpose" required placeholder="Describe how you will use these funds..." rows="3"></textarea>
                </div>
              </div>
            </div>

            @if (formData.loanAmount && formData.tenureMonths) {
              <div class="emi-card">
                <div class="emi-info">
                  <span class="emi-label"><lucide-icon [name]="SparklesIcon" [size]="14"></lucide-icon> Estimated Monthly Payment</span>
                  <span class="emi-amount">{{ calculateEMI() | currency:'INR':'symbol':'1.0-0' }}</span>
                </div>
                <p class="emi-disclaimer">Calculation based on 10.5% p.a. standard rate</p>
              </div>
            }
          </div>
        }

        @if (errorMessage) {
          <div class="alert alert-danger fade-in">
            <lucide-icon [name]="AlertIcon" [size]="18"></lucide-icon>
            {{ errorMessage }}
          </div>
        }

        <!-- Navigation Footer -->
        <div class="wizard-footer">
          <div class="footer-left">
            @if (currentStep > 0) {
              <button (click)="previousStep()" class="btn btn-ghost" [disabled]="saving">
                <lucide-icon [name]="ArrowLeftIcon" [size]="18"></lucide-icon>
                Previous
              </button>
            }
          </div>

          <div class="footer-right">
            @if (currentStep < steps.length - 1) {
              <button (click)="nextStep()" class="btn btn-primary btn-wide" [disabled]="saving">
                @if (saving) {
                  <lucide-icon [name]="LoaderIcon" [size]="18" class="spin"></lucide-icon>
                  Saving Progress...
                } @else {
                  <span>Continue</span>
                  <lucide-icon [name]="ArrowRightIcon" [size]="18"></lucide-icon>
                }
              </button>
            } @else {
              <button (click)="submitApplication()" class="btn btn-primary btn-submit-hero" [disabled]="saving || submitting">
                @if (submitting) {
                  <lucide-icon [name]="LoaderIcon" [size]="18" class="spin"></lucide-icon>
                  Finalizing Application...
                } @else {
                  <lucide-icon [name]="CheckIcon" [size]="20"></lucide-icon>
                  Submit Application
                }
              </button>
            }
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .wizard-container { max-width: 900px; margin: 0 auto; padding: 3rem 1.5rem; }
    
    .wizard-header { display: flex; align-items: center; gap: 2rem; margin-bottom: 3rem; }
    
    .header-text h1 { font-size: 1.75rem; margin-bottom: 2px; }
    
    .step-indicator { display: flex; align-items: center; padding: 2.5rem 4rem; margin-bottom: 3rem; }
    
    .step-item { display: flex; flex-direction: column; align-items: center; gap: 12px; position: relative; z-index: 2; }
    .step-box { 
      width: 48px; height: 48px; border-radius: 16px; background: var(--bg-tertiary); 
      border: 2px solid var(--border); display: flex; align-items: center; justify-content: center;
      transition: var(--transition);
    }
    .step-num { font-size: 1.1rem; font-weight: 800; color: var(--text-muted); }
    .step-item.active .step-box { border-color: var(--accent); color: var(--accent); background: var(--accent-soft); box-shadow: 0 0 20px var(--accent-glow); }
    .step-item.completed .step-box { border-color: var(--success); background: var(--success-glow); }
    
    .step-name { font-size: 0.75rem; font-weight: 800; text-transform: uppercase; letter-spacing: 1px; color: var(--text-muted); }
    .step-item.active .step-name { color: var(--text-primary); }
    
    .step-bridge { flex: 1; height: 2px; background: var(--border); margin: 0 1rem; margin-bottom: 28px; opacity: 0.3; }
    .step-bridge.completed { background: var(--success); opacity: 0.8; }

    .wizard-form-card { padding: 3rem; }
    
    .step-title-row { display: flex; align-items: flex-start; gap: 1.5rem; margin-bottom: 3rem; }
    .step-icon-glow { 
      width: 54px; height: 54px; border-radius: 18px; background: var(--accent-soft); 
      color: var(--accent); display: flex; align-items: center; justify-content: center;
      box-shadow: 0 0 30px var(--accent-glow);
    }
    .title-meta h2 { font-size: 1.75rem; font-weight: 800; margin-bottom: 4px; }
    .title-meta p { font-size: 0.95rem; color: var(--text-muted); }

    .form-layout { display: grid; grid-template-columns: repeat(2, 1fr); gap: 2rem; }
    .input-field { display: flex; flex-direction: column; gap: 0.75rem; }
    .input-field.full { grid-column: 1 / -1; }
    .input-field label { font-size: 0.85rem; font-weight: 700; color: var(--text-secondary); }
    
    .input-control { 
      position: relative; display: flex; align-items: center; 
      background: var(--bg-deep); border: 1px solid var(--border); border-radius: var(--radius-md);
      transition: var(--transition);
    }
    .input-control:focus-within { border-color: var(--accent); box-shadow: 0 0 15px var(--accent-glow); }
    
    .input-control input, .input-control select, .input-control textarea {
      flex: 1; background: transparent; border: none; padding: 1rem 1.25rem; color: var(--text-primary);
      font-weight: 600; outline: none; font-family: inherit;
    }
    
    .input-control select {
      cursor: pointer;
      appearance: none;
      background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='%236366f1' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='m6 9 6 6 6-6'/%3E%3C/svg%3E");
      background-repeat: no-repeat;
      background-position: right 1rem center;
      background-size: 1.2rem;
    }

    .input-control select option {
      background-color: #1e293b; /* Slate 800 */
      color: #f8fafc;
      padding: 10px;
    }
    .field-icon { margin-left: 1.25rem; color: var(--accent); opacity: 0.7; }
    .prefix { padding-left: 1.25rem; font-weight: 800; color: var(--accent); font-size: 0.9rem; }
    .currency-tag { padding-right: 1.25rem; font-weight: 800; color: var(--text-muted); font-size: 0.8rem; }

    .form-layout { display: grid; grid-template-columns: repeat(2, 1fr); gap: 2rem; }
    .input-field { display: flex; flex-direction: column; gap: 0.75rem; }
    .input-field.full { grid-column: 1 / -1; }
    .input-field label { font-size: 0.85rem; font-weight: 700; color: var(--text-secondary); }
    
    .input-control { 
      position: relative; display: flex; align-items: center; 
      background: var(--bg-deep); border: 1px solid var(--border); border-radius: var(--radius-md);
      transition: var(--transition);
    }
    .input-control:focus-within { border-color: var(--accent); box-shadow: 0 0 15px var(--accent-glow); }
    
    .input-control input, .input-control select, .input-control textarea {
      flex: 1; background: transparent; border: none; padding: 1rem 1.25rem; color: var(--text-primary);
      font-weight: 600; outline: none; font-family: inherit;
    }
    .field-icon { margin-left: 1.25rem; color: var(--accent); opacity: 0.7; }
    .prefix { padding-left: 1.25rem; font-weight: 800; color: var(--accent); font-size: 0.9rem; }
    .currency-tag { padding-right: 1.25rem; font-weight: 800; color: var(--text-muted); font-size: 0.8rem; }
    
    .emi-card { 
      margin-top: 3rem; padding: 2.5rem; border-radius: var(--radius-lg);
      background: linear-gradient(135deg, rgba(99, 102, 241, 0.1), rgba(139, 92, 246, 0.1));
      border: 1px solid var(--accent-soft); display: flex; flex-direction: column; align-items: center; gap: 0.5rem;
    }
    .emi-amount { font-size: 3rem; font-weight: 800; color: var(--accent-bright); letter-spacing: -2px; }
    .emi-label { display: flex; align-items: center; gap: 8px; font-weight: 700; color: var(--text-secondary); }
    .emi-disclaimer { font-size: 0.75rem; color: var(--text-muted); }

    .wizard-footer { display: flex; justify-content: space-between; align-items: center; margin-top: 4rem; padding-top: 2rem; border-top: 1px solid var(--border); }
    .btn-wide { min-width: 200px; height: 56px; }
    .btn-submit-hero { min-width: 280px; height: 60px; font-size: 1.1rem; background: var(--success); }
    
    .alert { padding: 1rem 1.5rem; border-radius: var(--radius-md); display: flex; align-items: center; gap: 1rem; margin-top: 2rem; font-weight: 600; }
    .alert-danger { background: rgba(239, 68, 68, 0.1); border: 1px solid var(--danger); color: var(--danger); }
  `]

})
export class LoanWizardComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private appService = inject(ApplicationService);

  readonly UserIcon = User;
  readonly BriefcaseIcon = Briefcase;
  readonly BanknoteIcon = Banknote;
  readonly CheckIcon = Check;
  readonly ArrowLeftIcon = ArrowLeft;
  readonly ArrowRightIcon = ArrowRight;
  readonly LoaderIcon = Loader;
  readonly AlertIcon = AlertTriangle;
  readonly SparklesIcon = Sparkles;

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
