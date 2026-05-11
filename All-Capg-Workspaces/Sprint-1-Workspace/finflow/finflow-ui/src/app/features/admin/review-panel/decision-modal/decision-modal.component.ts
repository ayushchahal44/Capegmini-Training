import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LucideAngularModule, User, Hash, Banknote, MessageSquare, TriangleAlert, CircleCheck, CircleX } from 'lucide-angular';
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
  templateUrl: './decision-modal.component.html',
  styleUrl: './decision-modal.component.css'
})
export class DecisionModalComponent {
  readonly UserIcon = User;
  readonly HashIcon = Hash;
  readonly BanknoteIcon = Banknote;
  readonly MessageIcon = MessageSquare;
  readonly AlertIcon = TriangleAlert;
  readonly CheckIcon = CircleCheck;
  readonly XIcon = CircleX;

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
