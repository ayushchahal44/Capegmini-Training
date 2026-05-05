import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DocumentInfo } from '../models/admin.model';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  private http = inject(HttpClient);
  private baseUrl = '/gateway/documents';

  upload(applicationId: number, docType: string, file: File): Observable<DocumentInfo> {
    const formData = new FormData();
    formData.append('applicationId', applicationId.toString());
    formData.append('docType', docType);
    formData.append('file', file);
    return this.http.post<DocumentInfo>(`${this.baseUrl}/upload`, formData);
  }

  getDocuments(applicationId: number): Observable<DocumentInfo[]> {
    return this.http.get<DocumentInfo[]>(`${this.baseUrl}/application/${applicationId}`);
  }

  verifyDocument(docId: number, verified: boolean, notes?: string): Observable<DocumentInfo> {
    return this.http.put<DocumentInfo>(`${this.baseUrl}/${docId}/verify`, { verified, notes });
  }

  downloadDocument(docId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/${docId}/download`, { responseType: 'blob' });
  }
}
