import { Injectable, inject } from '@angular/core';
import { ApiService } from './api.service';
import { LoanApplication } from '../models/application.model';
import { ApplicationStatusResponse } from '../models/admin.model';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private api = inject(ApiService);

  getApplications() {
    return this.api.get<LoanApplication[]>('/applications/my');
  }

  getApplicationById(id: number) {
    return this.api.get<LoanApplication>(`/applications/${id}`);
  }

  createDraft() {
    return this.api.post<LoanApplication>('/applications/draft', {});
  }

  updateDraft(id: number, data: Partial<LoanApplication>) {
    return this.api.put<LoanApplication>(`/applications/${id}/draft`, data);
  }

  submitApplication(id: number) {
    return this.api.post<LoanApplication>(`/applications/${id}/submit`, {});
  }

  getStatus(id: number) {
    return this.api.get<ApplicationStatusResponse>(`/applications/${id}/status`);
  }

  // Admin methods
  getAllApplications() {
    return this.api.get<LoanApplication[]>('/admin/applications');
  }
}

