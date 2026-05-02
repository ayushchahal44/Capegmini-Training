import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../core/services/admin.service';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [CommonModule, SharedModule],
  template: `
    <div class="reports-container">
      <h2>System Reports</h2>
      <mat-card *ngIf="report">
        <mat-card-content>
          <p>Total Applications: {{report.totalApplications}}</p>
          <p>Approved: {{report.approvedDecisions}}</p>
          <p>Rejected: {{report.rejectedDecisions}}</p>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .reports-container { padding: 20px; }
  `]
})
export class ReportsComponent implements OnInit {
  private adminService = inject(AdminService);
  report: any = null;

  ngOnInit() {
    this.adminService.getReports().subscribe(r => this.report = r);
  }
}
