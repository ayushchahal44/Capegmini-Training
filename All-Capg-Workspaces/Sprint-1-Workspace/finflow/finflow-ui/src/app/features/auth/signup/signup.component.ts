import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { LucideAngularModule, User, Mail, Lock, Loader, CircleCheck } from 'lucide-angular';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, LucideAngularModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  
  readonly icons = {
    user: User,
    mail: Mail,
    lock: Lock,
    loader: Loader,
    circleCheck: CircleCheck
  };

  data = { email: '', password: '', firstName: '', lastName: '' };
  loading = false;
  success = false;
  error = '';

  onSubmit() {
    this.loading = true;
    this.error = '';

    this.authService.signup(this.data).subscribe({
      next: (res) => {
        this.success = true;
        this.loading = false;
        const returnUrl = res.user.role === 'ADMIN' ? '/admin/dashboard' : '/applicant/dashboard';
        this.router.navigate([returnUrl]);
      },
      error: (err) => {
        if (err.status === 409) {
          this.error = 'Email already registered. Please use a different email or login.';
        } else if (err.status === 400) {
          this.error = 'Invalid input. Please check all fields and try again.';
        } else if (err.status === 0) {
          this.error = 'Unable to connect to server. Please check your connection.';
        } else {
          this.error = err.error?.message || 'Registration failed. Please try again.';
        }
        this.loading = false;
      }
    });
  }
}
