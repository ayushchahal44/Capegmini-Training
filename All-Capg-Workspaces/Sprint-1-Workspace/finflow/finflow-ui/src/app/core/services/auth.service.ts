import { Injectable, inject, signal } from '@angular/core';
import { ApiService } from './api.service';
import { AuthResponse, LoginRequest, SignupRequest, User } from '../models/auth.model';
import { tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api = inject(ApiService);
  private router = inject(Router);
  
  currentUser = signal<User | null>(this.getStoredUser());
  token = signal<string | null>(localStorage.getItem('finflow_token'));

  isAuthenticated() {
    return !!this.token();
  }

  isAdmin() {
    return this.currentUser()?.role === 'ADMIN';
  }

  login(credentials: LoginRequest) {
    return this.api.post<AuthResponse>('/auth/login', credentials).pipe(
      tap(res => this.handleAuthResponse(res))
    );
  }

  signup(data: SignupRequest) {
    return this.api.post<AuthResponse>('/auth/signup', data).pipe(
      tap(res => this.handleAuthResponse(res))
    );
  }

  logout() {
    this.currentUser.set(null);
    this.token.set(null);
    localStorage.removeItem('finflow_token');
    localStorage.removeItem('finflow_user');
    this.router.navigate(['/auth/login']);
  }

  private handleAuthResponse(res: AuthResponse) {
    this.currentUser.set(res.user);
    this.token.set(res.token);
    localStorage.setItem('finflow_token', res.token);
    localStorage.setItem('finflow_user', JSON.stringify(res.user));
  }

  private getStoredUser(): User | null {
    const userJson = localStorage.getItem('finflow_user');
    return userJson ? JSON.parse(userJson) : null;
  }
}
