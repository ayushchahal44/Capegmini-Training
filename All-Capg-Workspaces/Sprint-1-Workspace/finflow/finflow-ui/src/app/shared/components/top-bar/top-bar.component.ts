import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, Menu, Bell, User, Search } from 'lucide-angular';
import { AuthService } from '../../../core/services/auth.service';
import { SearchService } from '../../../core/services/search.service';

@Component({
  selector: 'app-top-bar',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.css'
})
export class TopBarComponent {
  @Input() sidebarCollapsed = false;
  @Output() toggleSidebar = new EventEmitter<void>();

  readonly authService = inject(AuthService);
  private readonly searchService = inject(SearchService);

  readonly icons = {
    menu: Menu,
    bell: Bell,
    user: User,
    search: Search
  };

  onSearch(event: any) {
    this.searchService.setSearchTerm(event.target.value);
  }
}
