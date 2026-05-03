import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../core/services/admin.service';
import { LucideAngularModule, BarChart3, TrendingUp, TrendingDown, Users, FileText } from 'lucide-angular';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  template: `
    <div class="reports-container fade-in">
      <header class="section-header">
        <div>
          <h1>Analytical Reports</h1>
          <p class="text-muted">System-wide performance and application metrics.</p>
        </div>
      </header>

      @if (report) {
        <div class="stats-grid">
          <div class="glass report-card">
            <div class="card-header">
              <lucide-icon [name]="FileText" [size]="24" class="icon-primary"></lucide-icon>
              <h3>Volume</h3>
            </div>
            <div class="card-body">
              <span class="value">{{report.totalApplications}}</span>
              <span class="label">Total Applications</span>
            </div>
          </div>

          <div class="glass report-card">
            <div class="card-header">
              <lucide-icon [name]="TrendingUp" [size]="24" class="icon-success"></lucide-icon>
              <h3>Approvals</h3>
            </div>
            <div class="card-body">
              <span class="value success">{{report.approvedDecisions}}</span>
              <span class="label">Approved Decisions</span>
            </div>
          </div>

          <div class="glass report-card">
            <div class="card-header">
              <lucide-icon [name]="TrendingDown" [size]="24" class="icon-danger"></lucide-icon>
              <h3>Rejections</h3>
            </div>
            <div class="card-body">
              <span class="value danger">{{report.rejectedDecisions}}</span>
              <span class="label">Rejected Decisions</span>
            </div>
          </div>
        </div>

        <div class="glass charts-section">
          <h3>Approval Rate Visualization</h3>
          <div class="simple-chart">
            <div class="bar-container">
              <div class="bar approved" [style.width.%]="(report.approvedDecisions / report.totalApplications) * 100 || 0"></div>
              <div class="bar rejected" [style.width.%]="(report.rejectedDecisions / report.totalApplications) * 100 || 0"></div>
            </div>
            <div class="legend">
              <span class="legend-item"><span class="dot approved"></span> Approved</span>
              <span class="legend-item"><span class="dot rejected"></span> Rejected</span>
            </div>
          </div>
        </div>
      } @else {
        <div class="loading-state">
          <div class="spinner"></div>
          <p>Generating reports...</p>
        </div>
      }
    </div>
  `,
  styles: [`
    .reports-container { display: flex; flex-direction: column; gap: 2.5rem; }
    .section-header h1 { font-size: 2rem; margin-bottom: 0.5rem; background: linear-gradient(to right, #fff, #94a3b8); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }
    .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 1.5rem; }
    .report-card { padding: 2rem; display: flex; flex-direction: column; gap: 1.5rem; }
    .card-header { display: flex; align-items: center; gap: 1rem; }
    .card-header h3 { font-size: 1.125rem; font-weight: 600; }
    .card-body { display: flex; flex-direction: column; }
    .value { font-size: 2.5rem; font-weight: 800; }
    .value.success { color: #10b981; }
    .value.danger { color: #ef4444; }
    .label { font-size: 0.875rem; color: var(--text-muted); font-weight: 500; }
    .charts-section { padding: 2rem; margin-top: 1rem; }
    .charts-section h3 { margin-bottom: 2rem; }
    .simple-chart { display: flex; flex-direction: column; gap: 1.5rem; }
    .bar-container { height: 24px; background: rgba(255,255,255,0.05); border-radius: 12px; display: flex; overflow: hidden; }
    .bar.approved { background: linear-gradient(to right, #10b981, #34d399); }
    .bar.rejected { background: linear-gradient(to right, #ef4444, #f87171); }
    .legend { display: flex; gap: 2rem; }
    .legend-item { display: flex; align-items: center; gap: 0.5rem; font-size: 0.875rem; color: var(--text-muted); }
    .dot { width: 10px; height: 10px; border-radius: 50%; }
    .dot.approved { background: #10b981; }
    .dot.rejected { background: #ef4444; }
    .icon-primary { color: #6366f1; }
    .icon-success { color: #10b981; }
    .icon-danger { color: #ef4444; }
    .spinner { width: 40px; height: 40px; border: 3px solid rgba(255,255,255,0.1); border-top-color: #6366f1; border-radius: 50%; animation: spin 1s linear infinite; margin: 2rem auto; }
    @keyframes spin { to { transform: rotate(360deg); } }
  `]
})
export class ReportsComponent implements OnInit {
  private adminService = inject(AdminService);

  readonly BarChart3 = BarChart3;
  readonly TrendingUp = TrendingUp;
  readonly TrendingDown = TrendingDown;
  readonly Users = Users;
  readonly FileText = FileText;

  report: any = null;

  ngOnInit() {
    this.adminService.getReports().subscribe(r => this.report = r);
  }
}
