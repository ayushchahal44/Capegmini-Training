import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../../core/services/document.service';
import { DocumentInfo } from '../../../core/models/admin.model';
import { LucideAngularModule } from 'lucide-angular';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-document-upload',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule],
  templateUrl: './document-upload.component.html',
  styleUrl: './document-upload.component.css'
})
export class DocumentUploadComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private docService = inject(DocumentService);

  applicationId = 0;
  documents: DocumentInfo[] = [];
  uploadingType: string | null = null;
  error = '';

  docTypes = [
    { value: 'ID_PROOF', label: 'ID Proof' },
    { value: 'ADDRESS_PROOF', label: 'Address Proof' },
    { value: 'INCOME_PROOF', label: 'Income Proof' }
  ];

  ngOnInit() {
    this.applicationId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDocuments();
  }

  loadDocuments() {
    this.docService.getDocuments(this.applicationId).subscribe({
      next: (docs) => this.documents = docs
    });
  }

  getDoc(type: string): DocumentInfo | undefined {
    return this.documents.find(d => d.docType === type);
  }

  onFileSelected(event: any, docType: string) {
    const file: File = event.target.files[0];
    if (!file) return;
    
    // Validate file type
    const allowedTypes = ['application/pdf', 'image/jpeg', 'image/png', 'image/jpg'];
    if (!allowedTypes.includes(file.type)) {
      this.error = 'Invalid file type. Please upload PDF, JPG, or PNG files only.';
      return;
    }
    
    // Validate file size (max 15MB to match backend)
    const maxSize = 15 * 1024 * 1024; 
    if (file.size > maxSize) {
      this.error = 'File is too large! Maximum allowed size is 15MB.';
      return;
    }
    
    this.error = '';
    this.uploadingType = docType;
    this.docService.upload(this.applicationId, docType, file).subscribe({
      next: (response) => { 
        console.log('Upload successful:', response);
        this.uploadingType = null; 
        this.loadDocuments(); 
        // Clear the file input
        event.target.value = '';
      },
      error: (err) => { 
        console.error('Upload error:', err);
        this.uploadingType = null; 
        this.error = this.extractErrorMessage(err) || 'Upload failed. Please try again.'; 
        // Clear the file input
        event.target.value = '';
      }
    });
  }

  private extractErrorMessage(err: any): string {
    if (err.status === 413) return 'File is too large for the server to process (Max 15MB).';
    if (err.error?.message) return err.error.message;
    return err.message || 'An unexpected error occurred';
  }

  goBack() { this.router.navigate(['/applicant/application', this.applicationId]); }
}
