import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../../core/services/admin.service';
import { UserInfo } from '../../../core/models/admin.model';
import { LucideAngularModule, Users, Shield, ShieldOff, Loader } from 'lucide-angular';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.css'
})
export class UserManagementComponent implements OnInit {
  private adminService = inject(AdminService);

  readonly UsersIcon = Users; readonly Shield = Shield; readonly ShieldOff = ShieldOff; readonly LoaderIcon = Loader;

  users: UserInfo[] = [];
  updatingId: number | null = null;

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.adminService.getUsers().subscribe({ next: (users) => this.users = users });
  }

  updateRole(user: UserInfo) {
    this.updatingId = user.id;
    this.adminService.updateUser(user.id, { role: user.role }).subscribe({
      next: () => { this.updatingId = null; },
      error: () => { this.updatingId = null; }
    });
  }

  toggleEnabled(user: UserInfo) {
    this.updatingId = user.id;
    this.adminService.updateUser(user.id, { enabled: !user.enabled }).subscribe({
      next: (updated) => { user.enabled = updated.enabled; this.updatingId = null; },
      error: () => { this.updatingId = null; }
    });
  }
}
