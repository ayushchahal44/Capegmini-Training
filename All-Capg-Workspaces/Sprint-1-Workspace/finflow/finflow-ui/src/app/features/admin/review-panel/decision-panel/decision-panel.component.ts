import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LucideAngularModule, MessageSquare, Banknote, Percent, Calendar, TriangleAlert, CircleCheck, CircleX, Wand2 } from 'lucide-angular';
import { LoanApplication } from '../../../../core/models/application.model';

export interface DecisionData {
  approvedAmount: number;
  interestRate: number;
  tenure: number;
  notes: string;
}

@Component({
  selector: 'app-decision-panel',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    LucideAngularModule
  ],
  templateUrl: './decision-panel.component.html',
  styleUrl: './decision-panel.component.css'
})
export class DecisionPanelComponent implements OnInit {
  @Input() application: LoanApplication | null = null;
  @Input() allDocumentsVerified: boolean = false;
  @Input() pendingDocuments: any[] = [];
  @Output() approve = new EventEmitter<DecisionData>();
  @Output() reject = new EventEmitter<DecisionData>();

  decisionForm: FormGroup;

  readonly MessageIcon = MessageSquare;
  readonly BanknoteIcon = Banknote;
  readonly PercentIcon = Percent;
  readonly CalendarIcon = Calendar;
  readonly AlertIcon = TriangleAlert;
  readonly CheckIcon = CircleCheck;
  readonly XIcon = CircleX;
  readonly MagicIcon = Wand2;

  constructor(private fb: FormBuilder) {
    this.decisionForm = this.fb.group({
      notes: ['', [Validators.required, Validators.minLength(10)]],
      approvedAmount: [0, [Validators.required, Validators.min(1)]],
      interestRate: [0, [Validators.required, Validators.min(0.1), Validators.max(30)]],
      tenure: [0, [Validators.required, Validators.min(1), Validators.max(360)]]
    });
  }

  ngOnInit() {
    if (this.application) {
      this.decisionForm.patchValue({
        approvedAmount: this.application.loanAmount,
        interestRate: 12.5,
        tenure: this.application.tenureMonths
      });
    }
  }

  get isActive(): boolean {
    return !!this.application && ['DOCS_VERIFIED', 'UNDER_REVIEW', 'PENDING'].includes(this.application.status);
  }

  get canApprove(): boolean {
    return this.isActive && 
           this.allDocumentsVerified && 
           (this.decisionForm.get('notes')?.valid ?? false) && 
           (this.decisionForm.get('approvedAmount')?.valid ?? false) &&
           (this.decisionForm.get('interestRate')?.valid ?? false) &&
           (this.decisionForm.get('tenure')?.valid ?? false);
  }

  onApprove() {
    if (this.decisionForm.valid) {
      this.approve.emit(this.decisionForm.value);
    }
  }

  onReject() {
    if (this.decisionForm.get('notes')?.valid) {
      this.reject.emit(this.decisionForm.value);
    }
  }

  setMaximumAmount() {
    if (this.application) {
      const maxAmount = Math.floor(this.application.annualIncome * 0.6);
      this.decisionForm.get('approvedAmount')?.setValue(maxAmount);
    }
  }

  setStandardRate() {
    this.decisionForm.get('interestRate')?.setValue(12.5);
  }

  setRequestedTenure() {
    if (this.application) {
      this.decisionForm.get('tenure')?.setValue(this.application.tenureMonths);
    }
  }

  resetForm() {
    if (this.application) {
      this.decisionForm.patchValue({
        notes: '',
        approvedAmount: this.application.loanAmount,
        interestRate: 12.5,
        tenure: this.application.tenureMonths
      });
    }
  }
}
