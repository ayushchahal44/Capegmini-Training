import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LucideAngularModule, MessageSquare, Banknote, Percent, Calendar, AlertTriangle, CheckCircle, XCircle, Wand2 } from 'lucide-angular';
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
  template: `
    <div class="glass-card decision-panel-card">
      <div class="card-header">
        <h3 class="card-title">
          <lucide-icon [name]="MessageIcon" [size]="20"></lucide-icon>
          Decision Panel
        </h3>
      </div>
      
      <div class="card-content">
        <form [formGroup]="decisionForm" class="decision-form">
          <div class="form-section">
            <label class="section-label">Reviewer Notes *</label>
            <div class="textarea-wrapper">
              <textarea 
                placeholder="Enter detailed notes about your decision..."
                formControlName="notes"
                rows="4">
              </textarea>
            </div>
            @if (decisionForm.get('notes')?.invalid && decisionForm.get('notes')?.touched) {
              <span class="error-text">Notes are required for decision (min 10 chars)</span>
            }
          </div>

          <div class="divider"></div>

          <div class="form-section">
            <h4 class="section-subtitle">Proposed Loan Terms</h4>
            
            <div class="input-grid">
              <div class="input-group">
                <label><lucide-icon [name]="BanknoteIcon" [size]="14"></lucide-icon> Approved Amount</label>
                <input type="number" formControlName="approvedAmount" placeholder="0">
                <span class="hint">{{application?.loanAmount | currency:'INR':'symbol':'1.0-0'}} requested</span>
              </div>

              <div class="input-group">
                <label><lucide-icon [name]="PercentIcon" [size]="14"></lucide-icon> Interest Rate (%)</label>
                <input type="number" formControlName="interestRate" placeholder="0" step="0.1">
                <span class="hint">Annual interest rate</span>
              </div>

              <div class="input-group">
                <label><lucide-icon [name]="CalendarIcon" [size]="14"></lucide-icon> Tenure (Months)</label>
                <input type="number" formControlName="tenure" placeholder="0">
                <span class="hint">{{application?.tenureMonths}} months requested</span>
              </div>
            </div>
          </div>

          <div class="validation-summary" *ngIf="!canApprove">
            <div class="validation-item" *ngIf="decisionForm.get('notes')?.invalid">
              <lucide-icon [name]="AlertIcon" [size]="14"></lucide-icon>
              <span>Reviewer notes are required</span>
            </div>
            <div class="validation-item" *ngIf="!allDocumentsVerified">
              <lucide-icon [name]="AlertIcon" [size]="14"></lucide-icon>
              <div class="validation-details">
                <span>The following documents require verification:</span>
                <ul class="pending-list">
                  @for (doc of pendingDocuments; track doc.id) {
                    <li>{{doc.docType.replace('_', ' ')}} ({{doc.originalName}})</li>
                  }
                </ul>
              </div>
            </div>
          </div>

          <div class="action-buttons">
            <button 
              class="btn btn-primary approve-btn"
              (click)="onApprove()"
              [disabled]="!canApprove || decisionForm.invalid">
              <lucide-icon [name]="CheckIcon" [size]="18"></lucide-icon>
              Approve Application
            </button>
            
            <button 
              class="btn btn-danger reject-btn"
              (click)="onReject()"
              [disabled]="!decisionForm.get('notes')?.valid">
              <lucide-icon [name]="XIcon" [size]="18"></lucide-icon>
              Reject Application
            </button>
          </div>
        </form>

        <div class="quick-actions" *ngIf="application">
          <h5><lucide-icon [name]="MagicIcon" [size]="14"></lucide-icon> Quick Tools</h5>
          <div class="action-grid">
            <button class="btn btn-ghost btn-sm" (click)="setMaximumAmount()">
              Max Amount (60% Income)
            </button>
            <button class="btn btn-ghost btn-sm" (click)="setStandardRate()">
              Standard Rate (12.5%)
            </button>
            <button class="btn btn-ghost btn-sm" (click)="setRequestedTenure()">
              Requested Tenure
            </button>
            <button class="btn btn-ghost btn-sm" (click)="resetForm()">
              Reset Form
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .decision-panel-card { padding: 2rem; border-radius: var(--radius-lg); height: 100%; }
    .card-title { display: flex; align-items: center; gap: 0.75rem; font-size: 1.25rem; font-weight: 800; color: var(--text-primary); }
    
    .decision-form { display: flex; flex-direction: column; gap: 2rem; margin-top: 1rem; }
    
    .section-label { font-size: 0.75rem; font-weight: 800; text-transform: uppercase; color: var(--text-muted); letter-spacing: 1px; margin-bottom: 0.75rem; display: block; }
    .section-subtitle { font-size: 0.9rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.05em; margin-bottom: 1.5rem; }
    
    .textarea-wrapper textarea { 
      width: 100%; background: var(--bg-deep); border: 1px solid var(--border); border-radius: var(--radius-md);
      padding: 1rem; color: var(--text-primary); font-family: inherit; resize: none; transition: var(--transition);
    }
    .textarea-wrapper textarea:focus { border-color: var(--accent); box-shadow: 0 0 15px var(--accent-glow); outline: none; }
    
    .input-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1.5rem; }
    .input-group { display: flex; flex-direction: column; gap: 0.5rem; }
    .input-group label { font-size: 0.75rem; font-weight: 700; color: var(--text-secondary); display: flex; align-items: center; gap: 6px; }
    .input-group input { 
      background: var(--bg-deep); border: 1px solid var(--border); border-radius: var(--radius-sm);
      padding: 0.75rem 1rem; color: var(--text-primary); font-weight: 600; transition: var(--transition);
    }
    .input-group input:focus { border-color: var(--accent); outline: none; }
    .hint { font-size: 0.7rem; color: var(--text-muted); }
    
    .divider { height: 1px; background: var(--border); margin: 0.5rem 0; opacity: 0.5; }
    
    .validation-summary { 
      background: rgba(245, 158, 11, 0.05); border: 1px solid rgba(245, 158, 11, 0.2); 
      border-radius: var(--radius-md); padding: 1rem; 
    }
    .validation-item { display: flex; align-items: flex-start; gap: 0.75rem; color: var(--warning); font-size: 0.85rem; font-weight: 600; margin-bottom: 0.5rem; }
    .validation-details { display: flex; flex-direction: column; gap: 4px; }
    .pending-list { margin: 4px 0 0 1.25rem; padding: 0; list-style: disc; }
    .pending-list li { font-size: 0.8rem; opacity: 0.9; }
    .validation-item:last-child { margin-bottom: 0; }
    
    .action-buttons { display: flex; gap: 1rem; margin-top: 1rem; }
    .approve-btn, .reject-btn { flex: 1; height: 54px; font-size: 1rem; font-weight: 800; border-radius: var(--radius-md); }
    .approve-btn { background: var(--success); color: white; }
    .reject-btn { background: var(--danger); color: white; }
    
    .quick-actions { margin-top: 2rem; padding-top: 2rem; border-top: 1px solid var(--border); }
    .quick-actions h5 { font-size: 0.75rem; font-weight: 800; color: var(--text-muted); text-transform: uppercase; letter-spacing: 1px; margin-bottom: 1rem; display: flex; align-items: center; gap: 8px; }
    .action-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 0.75rem; }
    
    .error-text { color: var(--danger); font-size: 0.7rem; font-weight: 600; margin-top: 4px; }
    .btn-ghost { background: rgba(255,255,255,0.02); border: 1px solid var(--border); color: var(--text-secondary); font-size: 0.75rem; font-weight: 700; }
    .btn-ghost:hover { background: rgba(255,255,255,0.05); color: var(--text-primary); border-color: var(--accent-soft); }
  `]
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
  readonly AlertIcon = AlertTriangle;
  readonly CheckIcon = CheckCircle;
  readonly XIcon = XCircle;
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

  get canApprove(): boolean {
    return this.allDocumentsVerified && 
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
