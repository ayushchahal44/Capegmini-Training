import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { LucideAngularModule, LayoutDashboard, FileText, Users, BarChart3, Settings, LogOut, ChevronLeft, ChevronRight } from 'lucide-angular';
import { AuthService } from '../../../core/services/auth.service';
import { SearchService } from '../../../core/services/search.service';

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
  
  public authService = inject(AuthService);
  private searchService = inject(SearchService);
  private router = inject(Router);
  
  isAdmin = this.authService.isAdmin;
  currentUser = this.authService.currentUser;

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

  clearSearch() {
    this.searchService.setSearchTerm('');
  }

  onActiveLoansClick() {
    this.clearSearch();
    this.router.navigate(['/admin/dashboard'], {
      queryParams: { status: 'APPROVED' },
      fragment: 'applications-list'
    });
  }
}
