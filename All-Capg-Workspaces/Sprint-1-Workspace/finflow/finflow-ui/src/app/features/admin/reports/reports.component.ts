import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../core/services/admin.service';
import { LucideAngularModule, BarChart3, TrendingUp, TrendingDown, Users, FileText } from 'lucide-angular';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
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
