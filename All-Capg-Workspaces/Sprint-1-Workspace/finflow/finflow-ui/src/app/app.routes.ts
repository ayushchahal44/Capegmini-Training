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
        children: [
          {
            path: 'summary',
            loadComponent: () => import('./features/admin/applicant-summary-page/applicant-summary-page.component').then(m => m.ApplicantSummaryPageComponent)
          },
          {
            path: 'documents',
            loadComponent: () => import('./features/admin/document-verification-page/document-verification-page.component').then(m => m.DocumentVerificationPageComponent)
          },
          {
            path: 'decision',
            loadComponent: () => import('./features/admin/decision-page/decision-page.component').then(m => m.DecisionPageComponent)
          },
          {
            path: '',
            redirectTo: 'summary',
            pathMatch: 'full'
          }
        ]
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
