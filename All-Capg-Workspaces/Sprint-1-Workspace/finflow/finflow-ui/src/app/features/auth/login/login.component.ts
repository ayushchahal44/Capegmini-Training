import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { LucideAngularModule, Lock, Mail, Loader, ArrowRight } from 'lucide-angular';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, LucideAngularModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly icons = {
    mail: Mail,
    lock: Lock,
    loader: Loader,
    arrowRight: ArrowRight
  };

  credentials = { email: '', password: '' };
  loading = false;
  error = '';

  onSubmit() {
    this.loading = true;
    this.error = '';

    this.authService.login(this.credentials).subscribe({
      next: (res) => {
        const returnUrl = res.user.role === 'ADMIN' ? '/admin/dashboard' : '/applicant/dashboard';
        this.router.navigate([returnUrl]);
      },
      error: (err) => {
        this.error = 'Invalid email or password. Please try again.';
        this.loading = false;
      }
    });
  }
}
