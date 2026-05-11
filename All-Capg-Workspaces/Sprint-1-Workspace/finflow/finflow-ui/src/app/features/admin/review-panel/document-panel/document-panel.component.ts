import { Component, Input, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LucideAngularModule, FileText, Download, Eye, CircleCheck, CircleX, RefreshCcw, Info, Search, Clock, Save } from 'lucide-angular';
import { DocumentInfo } from '../../../../core/models/admin.model';
import { DocumentService } from '../../../../core/services/document.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-document-panel',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './document-panel.component.html',
  styleUrl: './document-panel.component.css'
})
export class DocumentPanelComponent {
  @Input() documents: DocumentInfo[] = [];
  @Output() documentVerified = new EventEmitter<{document: DocumentInfo, verified: boolean}>();
  @Output() documentRejected = new EventEmitter<DocumentInfo>();
  @Output() reuploadRequested = new EventEmitter<DocumentInfo>();

  private docService = inject(DocumentService);
  private sanitizer = inject(DomSanitizer);

  selectedDocIndex = 0;
  previewUrl: SafeResourceUrl | null = null;

  readonly FileIcon = FileText;
  readonly DownloadIcon = Download;
  readonly EyeIcon = Eye;
  readonly CheckIcon = CircleCheck;
  readonly XIcon = CircleX;
  readonly RefreshIcon = RefreshCcw;
  readonly SaveIcon = Save;

  selectDocument(index: number) {
    this.selectedDocIndex = index;
    this.previewUrl = null; // Reset preview when switching docs
  }

  getDocumentTypeLabel(docType: string): string {
    switch (docType) {
      case 'ID_PROOF': return 'ID Proof';
      case 'ADDRESS_PROOF': return 'Address Proof';
      case 'INCOME_PROOF': return 'Income Proof';
      default: return docType.replace('_', ' ');
    }
  }

  getDocumentStatusIcon(status: string) {
    switch (status) {
      case 'PENDING': return Clock;
      case 'VERIFIED': return CircleCheck;
      case 'REJECTED': return CircleX;
      default: return Info;
    }
  }

  getVerifiedCount(): number {
    return this.documents.filter(doc => doc.status === 'VERIFIED').length;
  }

  getPendingCount(): number {
    return this.documents.filter(doc => doc.status === 'PENDING').length;
  }

  getRejectedCount(): number {
    return this.documents.filter(doc => doc.status === 'REJECTED').length;
  }

  verifyDocument(document: DocumentInfo, verified: boolean) {
    this.documentVerified.emit({ document, verified });
  }

  rejectDocument(document: DocumentInfo) {
    this.documentRejected.emit(document);
  }

  requestReupload(document: DocumentInfo) {
    this.reuploadRequested.emit(document);
  }

  saveRejectionReason(doc: DocumentInfo, reason: string) {
    if (!reason.trim()) return;
    this.documentRejected.emit({ ...doc, rejectionReason: reason });
  }

  downloadDocument(docInfo: DocumentInfo) {
    this.docService.downloadDocument(docInfo.id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = docInfo.originalName;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  previewDocument(document: DocumentInfo) {
    this.docService.downloadDocument(document.id).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      this.previewUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    });
  }
}
