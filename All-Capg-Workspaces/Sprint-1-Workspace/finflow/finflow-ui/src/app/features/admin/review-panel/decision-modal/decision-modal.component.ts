import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LucideAngularModule, User, Hash, Banknote, MessageSquare, AlertTriangle, CheckCircle, XCircle } from 'lucide-angular';
import { LoanApplication } from '../../../../core/models/application.model';

export interface DecisionModalData {
  application: LoanApplication;
  decisionType: 'approve' | 'reject';
  decisionData: {
    approvedAmount?: number;
    interestRate?: number;
    tenure?: number;
    notes: string;
  };
}

@Component({
  selector: 'app-decision-modal',
  standalone: true,
  imports: [CommonModule, MatDialogModule, LucideAngularModule],
  template: `
    <div class="glass-modal decision-modal fade-in">
      <div class="modal-header">
        <div class="header-icon-container" [class.approve]="data.decisionType === 'approve'" [class.reject]="data.decisionType === 'reject'">
          <lucide-icon [name]="data.decisionType === 'approve' ? CheckIcon : XIcon" [size]="32"></lucide-icon>
        </div>
        <h2 class="modal-title">
          {{data.decisionType === 'approve' ? 'Approve Application' : 'Reject Application'}}
        </h2>
        <p class="modal-subtitle">
          Please review the details before confirming your decision
        </p>
      </div>

      <div class="modal-content">
        <div class="info-section">
          <h3 class="section-title">Applicant Information</h3>
          <div class="info-grid">
            <div class="info-card">
              <lucide-icon [name]="UserIcon" [size]="16" class="card-icon"></lucide-icon>
              <div class="card-body">
                <span class="card-label">Name</span>
                <span class="card-value">{{data.application.fullName}}</span>
              </div>
            </div>
            <div class="info-card">
              <lucide-icon [name]="HashIcon" [size]="16" class="card-icon"></lucide-icon>
              <div class="card-body">
                <span class="card-label">Application ID</span>
                <span class="card-value">#{{data.application.id}}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="info-section">
          <h3 class="section-title">Loan Details</h3>
          <div class="info-grid" [class.single-col]="data.decisionType === 'reject'">
            <div class="info-card">
              <lucide-icon [name]="BanknoteIcon" [size]="16" class="card-icon"></lucide-icon>
              <div class="card-body">
                <span class="card-label">Requested Amount</span>
                <span class="card-value">{{data.application.loanAmount | currency:'INR':'symbol':'1.0-0'}}</span>
              </div>
            </div>
            @if (data.decisionType === 'approve' && data.decisionData.approvedAmount) {
              <div class="info-card highlight-success">
                <lucide-icon [name]="BanknoteIcon" [size]="16" class="card-icon"></lucide-icon>
                <div class="card-body">
                  <span class="card-label">Approved Amount</span>
                  <span class="card-value">{{data.decisionData.approvedAmount | currency:'INR':'symbol':'1.0-0'}}</span>
                </div>
              </div>
            }
          </div>
        </div>

        <div class="info-section">
          <h3 class="section-title">
            <lucide-icon [name]="MessageIcon" [size]="16"></lucide-icon>
            Reviewer Notes
          </h3>
          <div class="notes-card">
            <p>{{data.decisionData.notes}}</p>
          </div>
        </div>

        <div class="warning-banner" [class.reject]="data.decisionType === 'reject'">
          <lucide-icon [name]="AlertIcon" [size]="18"></lucide-icon>
          <div class="warning-text">
            @if (data.decisionType === 'approve') {
              <p>This action will approve the loan application and notify the applicant.</p>
              <p>Once approved, this decision cannot be easily reversed.</p>
            } @else {
              <p>This action will reject the loan application permanently.</p>
              <p>The applicant will need to submit a new application if they wish to reapply.</p>
            }
          </div>
        </div>
      </div>

      <div class="modal-actions">
        <button (click)="onCancel()" class="btn btn-ghost">Cancel</button>
        <button (click)="onConfirm()" class="btn" [class.btn-primary]="data.decisionType === 'approve'" [class.btn-danger]="data.decisionType === 'reject'">
          {{data.decisionType === 'approve' ? 'Confirm Approval' : 'Confirm Rejection'}}
        </button>
      </div>
    </div>
  `,
  styles: [`
    .glass-modal {
      background: #0f172a; /* Solid dark background to ensure visibility */
      backdrop-filter: blur(20px);
      border: 1px solid rgba(255,255,255,0.1);
      border-radius: 24px;
      padding: 2rem;
      max-width: 550px;
      width: 90vw;
      max-height: 90vh;
      overflow-y: auto;
      color: #f8fafc;
      box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
      scrollbar-width: thin;
      scrollbar-color: rgba(255,255,255,0.1) transparent;
    }
    
    .modal-header { text-align: center; margin-bottom: 2rem; }
    
    .header-icon-container {
      width: 64px; height: 64px; border-radius: 50%; display: flex; align-items: center; justify-content: center;
      margin: 0 auto 1rem;
    }
    .header-icon-container.approve { background: rgba(16, 185, 129, 0.15); color: #10b981; }
    .header-icon-container.reject { background: rgba(239, 68, 68, 0.15); color: #ef4444; }
    
    .modal-title { font-size: 1.75rem; font-weight: 800; letter-spacing: -0.02em; margin-bottom: 0.5rem; color: #ffffff; }
    .modal-subtitle { color: #94a3b8; font-size: 0.9rem; font-weight: 500; }
    
    .modal-content { display: flex; flex-direction: column; gap: 1.5rem; margin-bottom: 2rem; }
    
    .section-title { 
      font-size: 0.7rem; font-weight: 800; text-transform: uppercase; color: #64748b; 
      letter-spacing: 1px; margin-bottom: 0.75rem; display: flex; align-items: center; gap: 8px;
    }
    
    .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
    .info-grid.single-col { grid-template-columns: 1fr; }
    
    .info-card {
      background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.1); border-radius: 12px;
      padding: 0.85rem 1rem; display: flex; align-items: center; gap: 0.75rem;
    }
    .info-card.highlight-success { border-color: #10b981; background: rgba(16, 185, 129, 0.05); }
    
    .card-icon { color: #64748b; }
    .card-body { display: flex; flex-direction: column; gap: 1px; }
    .card-label { font-size: 0.65rem; font-weight: 700; color: #64748b; text-transform: uppercase; }
    .card-value { font-size: 0.95rem; font-weight: 700; color: #f1f5f9; }
    
    .notes-card {
      background: rgba(255,255,255,0.02); border: 1px solid rgba(255,255,255,0.05); border-radius: 12px;
      padding: 1rem;
    }
    .notes-card p { font-size: 0.9rem; line-height: 1.5; color: #cbd5e1; margin: 0; white-space: pre-wrap; }
    
    .warning-banner {
      background: rgba(245, 158, 11, 0.1); border: 1px solid rgba(245, 158, 11, 0.2); border-radius: 12px;
      padding: 1rem; display: flex; gap: 0.75rem; color: #f59e0b;
    }
    .warning-banner.reject { background: rgba(239, 68, 68, 0.1); border-color: rgba(239, 68, 68, 0.2); color: #ef4444; }
    .warning-text { font-size: 0.8rem; font-weight: 600; line-height: 1.4; }
    .warning-text p { margin: 0; }
    
    .modal-actions { display: flex; gap: 1rem; justify-content: flex-end; position: sticky; bottom: 0; background: #0f172a; padding-top: 1rem; }
    .modal-actions .btn { min-width: 120px; height: 45px; font-weight: 700; border-radius: 10px; font-size: 0.9rem; }
    
    .btn-ghost { background: transparent; border: 1px solid rgba(255,255,255,0.1); color: #94a3b8; }
    .btn-ghost:hover { background: rgba(255,255,255,0.05); color: #ffffff; }
    .btn-primary { background: #6366f1; color: white; border: none; }
    .btn-danger { background: #ef4444; color: white; border: none; }

    @media (max-width: 480px) {
      .glass-modal { padding: 1.25rem; border-radius: 16px; }
      .info-grid { grid-template-columns: 1fr; }
      .modal-actions { flex-direction: column-reverse; gap: 0.75rem; }
      .modal-actions .btn { width: 100%; min-width: none; }
      .modal-title { font-size: 1.5rem; }
    }
  `]
})
export class DecisionModalComponent {
  readonly UserIcon = User;
  readonly HashIcon = Hash;
  readonly BanknoteIcon = Banknote;
  readonly MessageIcon = MessageSquare;
  readonly AlertIcon = AlertTriangle;
  readonly CheckIcon = CheckCircle;
  readonly XIcon = XCircle;

  constructor(
    public dialogRef: MatDialogRef<DecisionModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DecisionModalData
  ) {}

  onCancel(): void {
    this.dialogRef.close(false);
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }
}
