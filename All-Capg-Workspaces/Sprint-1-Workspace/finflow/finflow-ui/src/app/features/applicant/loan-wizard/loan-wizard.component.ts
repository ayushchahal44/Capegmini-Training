import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { LucideAngularModule, User, Briefcase, Banknote, Check, ArrowLeft, ArrowRight, Loader, TriangleAlert, Sparkles } from 'lucide-angular';

@Component({
  selector: 'app-loan-wizard',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  templateUrl: './loan-wizard.component.html',
  styleUrl: './loan-wizard.component.css'
})
export class LoanWizardComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly appService = inject(ApplicationService);

  readonly UserIcon = User;
  readonly BriefcaseIcon = Briefcase;
  readonly BanknoteIcon = Banknote;
  readonly CheckIcon = Check;
  readonly ArrowLeftIcon = ArrowLeft;
  readonly ArrowRightIcon = ArrowRight;
  readonly LoaderIcon = Loader;
  readonly AlertIcon = TriangleAlert;
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
    if (!this.isStepValid()) {
      this.errorMessage = 'Please fix the errors in the current step before proceeding.';
      return;
    }
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
    if (!this.isStepValid()) {
      this.errorMessage = 'Please complete all required fields correctly before submitting.';
      return;
    }
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

  isMinor(): boolean {
    if (!this.formData.dateOfBirth) return false;
    const dob = new Date(this.formData.dateOfBirth);
    const today = new Date();
    let age = today.getFullYear() - dob.getFullYear();
    const m = today.getMonth() - dob.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
      age--;
    }
    return age < 18;
  }

  isStepValid(): boolean {
    switch (this.currentStep) {
      case 0:
        return this.isFieldValid('fullName') && 
               this.isFieldValid('phone') && 
               this.isFieldValid('address') && 
               this.isFieldValid('dateOfBirth');
      case 1:
        return this.isFieldValid('employer') && 
               this.isFieldValid('employmentType') && 
               this.isFieldValid('annualIncome');
      case 2:
        return this.isFieldValid('loanAmount') && 
               this.isFieldValid('tenureMonths') && 
               this.isFieldValid('loanPurpose');
      default:
        return true;
    }
  }

  isFieldValid(fieldName: string): boolean {
    const val = this.formData[fieldName];
    switch (fieldName) {
      case 'fullName': return !!val?.match(/^[a-zA-Z\s]{3,50}$/);
      case 'phone': return !!val?.match(/^[6-9]\d{9}$/);
      case 'address': return !!(val && val.length >= 10);
      case 'dateOfBirth': return !!(val && !this.isMinor());
      case 'employer': return !!val;
      case 'employmentType': return !!val;
      case 'annualIncome': return val >= 10000;
      case 'loanAmount': return val >= 10000;
      case 'tenureMonths': return !!val;
      case 'loanPurpose': return !!(val && val.length >= 10);
      default: return true;
    }
  }

  getFieldError(fieldName: string): string {
    const val = this.formData[fieldName];
    if (!val && fieldName !== 'annualIncome' && fieldName !== 'loanAmount') return 'This field is required';
    
    switch (fieldName) {
      case 'fullName':
        if (val.length < 3) return 'Name must be at least 3 characters';
        if (val.length > 50) return 'Name cannot exceed 50 characters';
        return 'Only letters and spaces allowed';
      case 'phone':
        return 'Enter a valid 10-digit Indian phone number';
      case 'address':
        return 'Address must be at least 10 characters long';
      case 'dateOfBirth':
        if (!val) return 'Date of Birth is required';
        if (this.isMinor()) return 'You must be at least 18 years old';
        return '';
      case 'annualIncome':
        if (val === null || val === undefined) return 'Annual income is required';
        return 'Minimum income should be 10,000';
      case 'loanAmount':
        if (val === null || val === undefined) return 'Loan amount is required';
        return 'Minimum loan amount should be 10,000';
      case 'loanPurpose':
        return 'Purpose must be at least 10 characters long';
      default:
        return '';
    }
  }

  goBack() {
    this.router.navigate(['/applicant/dashboard']);
  }
}
