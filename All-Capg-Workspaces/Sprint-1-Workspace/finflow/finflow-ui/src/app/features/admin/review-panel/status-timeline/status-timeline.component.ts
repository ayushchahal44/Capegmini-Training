import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, Circle, CircleCheck, Clock, TriangleAlert, CircleCheckBig, CircleX } from 'lucide-angular';
import { LoanApplication } from '../../../../core/models/application.model';

interface TimelineStep {
  status: string;
  label: string;
  icon: any;
  completed: boolean;
  active: boolean;
  failed?: boolean;
}

@Component({
  selector: 'app-status-timeline',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './status-timeline.component.html',
  styleUrl: './status-timeline.component.css'
})
export class StatusTimelineComponent {
  @Input() application: LoanApplication | null = null;

  get timelineSteps(): TimelineStep[] {
    const currentStatus = this.application?.status || 'DRAFT';
    
    const steps: TimelineStep[] = [
      { status: 'DRAFT', label: 'Draft', icon: Circle, completed: false, active: false },
      { status: 'SUBMITTED', label: 'Submitted', icon: CircleCheck, completed: false, active: false },
      { status: 'DOCS_PENDING', label: 'Docs Pending', icon: Clock, completed: false, active: false },
      { status: 'DOCS_VERIFIED', label: 'Docs Verified', icon: CircleCheckBig, completed: false, active: false },
      { status: 'UNDER_REVIEW', label: 'Under Review', icon: TriangleAlert, completed: false, active: false },
      { status: 'APPROVED', label: 'Approved', icon: CircleCheck, completed: false, active: false },
      { status: 'REJECTED', label: 'Rejected', icon: CircleX, completed: false, active: false, failed: false }
    ];

    const statusOrder = ['DRAFT', 'SUBMITTED', 'DOCS_PENDING', 'DOCS_VERIFIED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED'];
    const currentIndex = statusOrder.indexOf(currentStatus);
    
    steps.forEach((step, index) => {
      if (step.status === currentStatus) {
        step.active = true;
        if (currentStatus === 'APPROVED') {
          step.completed = true;
          step.active = false; // Priority to completed
        }
        if (currentStatus === 'REJECTED') {
          step.failed = true;
          step.active = false; // Priority to failed
        }
      } else if (index < currentIndex && step.status !== 'REJECTED' && step.status !== 'APPROVED') {
        step.completed = true;
      }
    });

    // Filter logic:
    // 1. Always keep initial steps.
    // 2. If status is APPROVED, remove REJECTED.
    // 3. If status is REJECTED, remove APPROVED.
    // 4. Otherwise show both potential end states.
    return steps.filter(step => {
      if (currentStatus === 'APPROVED' && step.status === 'REJECTED') return false;
      if (currentStatus === 'REJECTED' && step.status === 'APPROVED') return false;
      return true;
    });
  }

  getCurrentStatusText(): string {
    if (!this.application) return '';
    return this.application.status.replace('_', ' ');
  }

  getCurrentStatusClass(): string {
    if (!this.application) return '';
    return this.application.status.toLowerCase().replace('_', '-');
  }

  getProgressPercentage(): number {
    if (!this.application) return 0;
    
    const statusOrder = ['DRAFT', 'SUBMITTED', 'DOCS_PENDING', 'DOCS_VERIFIED', 'UNDER_REVIEW', 'APPROVED'];
    
    if (this.application.status === 'REJECTED' || this.application.status === 'APPROVED') {
      return 100;
    }
    
    const currentIndex = statusOrder.indexOf(this.application.status);
    if (currentIndex === -1) return 0;
    
    return Math.round(((currentIndex + 1) / statusOrder.length) * 100);
  }

  getProgressClass(): string {
    if (!this.application) return 'normal';
    
    if (this.application.status === 'REJECTED') {
      return 'danger';
    }
    
    if (this.application.status === 'APPROVED') {
      return 'success';
    }
    
    return 'normal';
  }
}
