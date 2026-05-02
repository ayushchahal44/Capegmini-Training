import { Injectable, inject } from '@angular/core';
import { ApiService } from './api.service';
import { ReportResponse, UserInfo } from '../models/admin.model';
import { LoanApplication } from '../models/application.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private api = inject(ApiService);

  getApplications(): Observable<LoanApplication[]> {
    return this.api.get<LoanApplication[]>('/admin/applications');
  }

  decide(applicationId: number, approved: boolean, terms?: string, rejectionReason?: string) {
    return this.api.put(`/admin/applications/${applicationId}/decision`, {
      approved, terms, rejectionReason
    });
  }

  getReports(): Observable<ReportResponse> {
    return this.api.get<ReportResponse>('/admin/reports');
  }

  getUsers(): Observable<UserInfo[]> {
    return this.api.get<UserInfo[]>('/admin/users');
  }

  updateUser(id: number, data: Partial<UserInfo>): Observable<UserInfo> {
    return this.api.put<UserInfo>(`/admin/users/${id}`, data);
  }
}
