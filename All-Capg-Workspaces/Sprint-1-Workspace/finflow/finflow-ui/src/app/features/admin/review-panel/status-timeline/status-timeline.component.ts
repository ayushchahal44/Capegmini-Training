import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, Circle, CheckCircle, Clock, AlertTriangle, CheckCircle2, XCircle } from 'lucide-angular';
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
  template: `
    <div class="status-timeline">
      <div class="timeline-container">
        @for (step of timelineSteps; track step.status; let i = $index) {
          <div 
            class="timeline-step"
            [class.completed]="step.completed"
            [class.active]="step.active"
            [class.failed]="step.failed">
            
            <div class="step-icon">
              <lucide-icon [name]="step.icon" [size]="20"></lucide-icon>
            </div>
            
            <div class="step-content">
              <div class="step-label">{{step.label}}</div>
              @if (step.completed || step.active || step.failed) {
                <div class="step-status">
                  @if (step.completed) { <span class="status-text completed">Completed</span> }
                  @if (step.active) { <span class="status-text active">In Progress</span> }
                  @if (step.failed) { <span class="status-text failed">Failed</span> }
                </div>
              }
            </div>
            
            @if (i < timelineSteps.length - 1) {
              <div class="step-connector">
                <div class="connector-line" [class.completed]="step.completed && !step.failed"></div>
              </div>
            }
          </div>
        }
      </div>
      
      <div class="timeline-summary">
        <div class="current-status">
          <span class="status-label">Current Status:</span>
          <span class="status-value" [ngClass]="getCurrentStatusClass()">
            {{getCurrentStatusText()}}
          </span>
        </div>
        
        <div class="progress-info">
          <div class="progress-bar">
            <div 
              class="progress-fill" 
              [style.width.%]="getProgressPercentage()"
              [ngClass]="getProgressClass()">
            </div>
          </div>
          <span class="progress-text">{{getProgressPercentage()}}% Complete</span>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .status-timeline {
      background: var(--bg-secondary);
      border: 1px solid var(--border);
      border-radius: var(--radius-lg);
      padding: 2.5rem;
      margin-bottom: 2.5rem;
      box-shadow: var(--glass-shadow);
    }

    .timeline-container {
      display: flex;
      align-items: center;
      justify-content: space-between;
      position: relative;
      margin-bottom: 3rem;
      padding: 0 1rem;
    }

    .timeline-step {
      display: flex;
      flex-direction: column;
      align-items: center;
      position: relative;
      flex: 1;
      z-index: 2;
    }

    .step-icon {
      width: 54px;
      height: 54px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      background: var(--bg-deep);
      border: 2px solid var(--border);
      margin-bottom: 1rem;
      color: var(--text-muted);
      transition: var(--transition);
      box-shadow: inset 0 0 10px rgba(0,0,0,0.5);
    }

    .timeline-step.completed .step-icon {
      background: var(--success-glow);
      border-color: var(--success);
      color: var(--success);
    }

    .timeline-step.active .step-icon {
      background: var(--accent-soft);
      border-color: var(--accent);
      color: var(--accent);
      box-shadow: 0 0 20px var(--accent-glow);
    }

    .timeline-step.failed .step-icon {
      background: rgba(239, 68, 68, 0.1);
      border-color: var(--danger);
      color: var(--danger);
    }

    .step-content { text-align: center; }
    .step-label { font-weight: 700; font-size: 0.9rem; color: var(--text-secondary); margin-bottom: 0.25rem; }
    .timeline-step.active .step-label { color: var(--text-primary); }

    .status-text { font-size: 0.75rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.05em; }
    .status-text.completed { color: var(--success); }
    .status-text.active { color: var(--accent); }
    .status-text.failed { color: var(--danger); }

    .step-connector {
      position: absolute;
      top: 27px;
      left: 50%;
      width: 100%;
      height: 2px;
      z-index: 1;
    }

    .connector-line {
      height: 100%;
      background: var(--border);
      opacity: 0.3;
      transition: var(--transition);
    }

    .connector-line.completed {
      background: var(--success);
      opacity: 1;
    }

    .timeline-summary {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1.5rem 2rem;
      background: rgba(0,0,0,0.2);
      border-radius: var(--radius-md);
      border: 1px solid var(--border);
    }

    .current-status {
      display: flex;
      align-items: center;
      gap: 1rem;
    }

    .status-label {
      font-weight: 700;
      color: var(--text-muted);
      font-size: 0.8rem;
      text-transform: uppercase;
      letter-spacing: 0.05em;
    }

    .status-value {
      font-weight: 800;
      font-size: 0.85rem;
      padding: 0.5rem 1.25rem;
      border-radius: var(--radius-sm);
      text-transform: uppercase;
      letter-spacing: 1px;
    }

    .status-value.docs-pending { background: rgba(245,158,11,0.1); color: var(--warning); }
    .status-value.under-review { background: rgba(59,130,246,0.1); color: var(--info); }
    .status-value.docs-verified { background: rgba(139,92,246,0.1); color: var(--violet); }
    .status-value.approved { background: rgba(16,185,129,0.1); color: var(--success); }
    .status-value.rejected { background: rgba(239,68,68,0.1); color: var(--danger); }

    .progress-info {
      display: flex;
      align-items: center;
      gap: 1.5rem;
    }

    .progress-bar {
      width: 200px;
      height: 8px;
      background: var(--bg-tertiary);
      border-radius: 4px;
      overflow: hidden;
    }

    .progress-fill {
      height: 100%;
      transition: width 1s ease-out;
      border-radius: 4px;
    }

    .progress-fill.normal { background: linear-gradient(90deg, var(--accent), var(--cyan)); }
    .progress-fill.success { background: linear-gradient(90deg, var(--success), #66bb6a); }
    .progress-fill.danger { background: linear-gradient(90deg, var(--danger), #ef5350); }

    .progress-text {
      font-size: 0.8rem;
      font-weight: 700;
      color: var(--text-secondary);
      min-width: 80px;
    }

    @media (max-width: 768px) {
      .timeline-container {
        justify-content: flex-start;
        gap: 40px;
      }
      
      .step-connector {
        display: none;
      }
      
      .timeline-summary {
        flex-direction: column;
        gap: 12px;
        align-items: flex-start;
      }
      
      .progress-info {
        width: 100%;
      }
      
      .progress-bar {
        flex: 1;
      }
    }
  `]
})
export class StatusTimelineComponent {
  @Input() application: LoanApplication | null = null;

  get timelineSteps(): TimelineStep[] {
    const currentStatus = this.application?.status || 'DRAFT';
    
    const steps: TimelineStep[] = [
      { status: 'DRAFT', label: 'Draft', icon: Circle, completed: false, active: false },
      { status: 'SUBMITTED', label: 'Submitted', icon: CheckCircle, completed: false, active: false },
      { status: 'DOCS_PENDING', label: 'Docs Pending', icon: Clock, completed: false, active: false },
      { status: 'DOCS_VERIFIED', label: 'Docs Verified', icon: CheckCircle2, completed: false, active: false },
      { status: 'UNDER_REVIEW', label: 'Under Review', icon: AlertTriangle, completed: false, active: false },
      { status: 'APPROVED', label: 'Approved', icon: CheckCircle, completed: false, active: false, failed: false },
      { status: 'REJECTED', label: 'Rejected', icon: XCircle, completed: false, active: false, failed: true }
    ];

    const statusOrder = ['DRAFT', 'SUBMITTED', 'DOCS_PENDING', 'DOCS_VERIFIED', 'UNDER_REVIEW', 'APPROVED'];
    const currentIndex = statusOrder.indexOf(currentStatus);
    
    steps.forEach((step, index) => {
      if (currentStatus === 'REJECTED' && step.status === 'REJECTED') {
        step.active = true;
        step.failed = true;
      } else if (currentStatus === 'APPROVED' && step.status === 'APPROVED') {
        step.active = true;
        step.completed = true;
      } else if (step.status === currentStatus) {
        step.active = true;
      } else if (index < currentIndex) {
        step.completed = true;
      }
    });

    return steps.filter(step => !step.failed || step.status === 'REJECTED');
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
    const currentIndex = statusOrder.indexOf(this.application.status);
    
    if (this.application.status === 'REJECTED') {
      return Math.round((currentIndex / statusOrder.length) * 100);
    }
    
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
