import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { LucideAngularModule, LayoutDashboard, FileText, Users, BarChart3, Settings, LogOut, ChevronLeft, ChevronRight } from 'lucide-angular';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, LucideAngularModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  @Input() collapsed = false;
  @Output() collapsedChange = new EventEmitter<boolean>();
  
  private authService = inject(AuthService);

  readonly icons = {
    dashboard: LayoutDashboard,
    loans: FileText,
    customers: Users,
    reports: BarChart3,
    settings: Settings,
    logout: LogOut,
    collapse: ChevronLeft,
    expand: ChevronRight
  };

  toggleCollapse() {
    this.collapsed = !this.collapsed;
    this.collapsedChange.emit(this.collapsed);
  }

  logout() {
    this.authService.logout();
  }
}
