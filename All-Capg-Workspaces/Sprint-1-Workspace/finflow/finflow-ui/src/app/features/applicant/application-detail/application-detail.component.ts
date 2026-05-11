import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ApplicationService } from '../../../core/services/application.service';
import { DocumentService } from '../../../core/services/document.service';
import { LoanApplication } from '../../../core/models/application.model';
import { TimelineEntry, DocumentInfo } from '../../../core/models/admin.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { LucideAngularModule, ArrowLeft, FileText, Upload, Clock, CircleCheck, CircleAlert, CircleX, Banknote, User, Briefcase, Download, Eye, X } from 'lucide-angular';

@Component({
  selector: 'app-application-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, LucideAngularModule],
  templateUrl: './application-detail.component.html',
  styleUrl: './application-detail.component.css'
})
export class ApplicationDetailComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly appService = inject(ApplicationService);
  private readonly docService = inject(DocumentService);

  readonly ArrowLeft = ArrowLeft; readonly FileText = FileText; readonly Upload = Upload;
  readonly Clock = Clock; readonly CircleCheck = CircleCheck; readonly CircleAlert = CircleAlert;
  readonly CircleX = CircleX; readonly Banknote = Banknote; readonly UserIcon = User; 
  readonly Briefcase = Briefcase; readonly Download = Download; readonly Eye = Eye; readonly X = X;

  application: LoanApplication | null = null;
  documents: DocumentInfo[] = [];
  timeline: TimelineEntry[] = [];

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.appService.getApplicationById(id).subscribe({ next: (app) => this.application = app });
    this.appService.getStatus(id).subscribe({ next: (res) => this.timeline = res.timeline || [] });
    this.docService.getDocuments(id).subscribe({ next: (docs) => this.documents = docs });
  }

  getTimelineIcon(status: string) {
    switch (status) {
      case 'APPROVED': return CircleCheck;
      case 'REJECTED': return CircleX;
      case 'DOCS_PENDING': return CircleAlert;
      case 'SUBMITTED': return FileText;
      default: return Clock;
    }
  }

  downloadDoc(doc: DocumentInfo) {
    this.docService.downloadDocument(doc.id).subscribe({
      next: (blob) => {
        const url = globalThis.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = doc.originalName;
        document.body.appendChild(a);
        a.click();
        globalThis.URL.revokeObjectURL(url);
        a.remove();
      }
    });
  }

  previewUrl: SafeResourceUrl | null = null;
  isPreviewing = false;
  selectedPreviewDoc: DocumentInfo | null = null;

  private readonly sanitizer = inject(DomSanitizer);

  previewDoc(doc: DocumentInfo) {
    this.selectedPreviewDoc = doc;
    this.docService.downloadDocument(doc.id).subscribe({
      next: (blob) => {
        const url = globalThis.URL.createObjectURL(blob);
        this.previewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
        this.isPreviewing = true;
      }
    });
  }

  closePreview() {
    this.isPreviewing = false;
    this.previewUrl = null;
    this.selectedPreviewDoc = null;
  }

  get hasRejectedDocs(): boolean {
    return this.documents.some(doc => doc.status === 'REJECTED');
  }

  get allDocsVerified(): boolean {
    return this.documents.length > 0 && this.documents.every(doc => doc.status === 'VERIFIED');
  }

  goBack() { this.router.navigate(['/applicant/dashboard']); }
}
