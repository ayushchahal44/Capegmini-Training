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
      next: () => {
        this.success = true;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Registration failed. The email might already be in use.';
        this.loading = false;
      }
    });
  }
}
