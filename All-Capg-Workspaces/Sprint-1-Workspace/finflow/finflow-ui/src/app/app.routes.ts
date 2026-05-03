import { Routes } from '@angular/router';
import { authGuard, adminGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
      },
      {
        path: 'signup',
        loadComponent: () => import('./features/auth/signup/signup.component').then(m => m.SignupComponent)
      }
    ]
  },
  {
    path: 'applicant',
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/applicant/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'applications',
        loadComponent: () => import('./features/applicant/applications-list/applications-list.component').then(m => m.ApplicationsListComponent)
      },
      {
        path: 'loan-wizard/:id',
        loadComponent: () => import('./features/applicant/loan-wizard/loan-wizard.component').then(m => m.LoanWizardComponent)
      },
      {
        path: 'application/:id',
        loadComponent: () => import('./features/applicant/application-detail/application-detail.component').then(m => m.ApplicationDetailComponent)
      },
      {
        path: 'documents/:id',
        loadComponent: () => import('./features/applicant/document-upload/document-upload.component').then(m => m.DocumentUploadComponent)
      }
    ]
  },
  {
    path: 'admin',
    canActivate: [authGuard, adminGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/admin/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'loans',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'review/:id',
        loadComponent: () => import('./features/admin/review-panel/review-panel.component').then(m => m.ReviewPanelComponent)
      },
      {
        path: 'reports',
        loadComponent: () => import('./features/admin/reports/reports.component').then(m => m.ReportsComponent)
      },
      {
        path: 'users',
        loadComponent: () => import('./features/admin/user-management/user-management.component').then(m => m.UserManagementComponent)
      }
    ]
  },
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: 'auth/login'
  }
];
