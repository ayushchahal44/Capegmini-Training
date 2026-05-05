export interface DocumentInfo {
  id: number;
  applicationId: number;
  userId: number;
  docType: 'ID_PROOF' | 'ADDRESS_PROOF' | 'INCOME_PROOF';
  originalName: string;
  status: 'PENDING' | 'VERIFIED' | 'REJECTED';
  createdAt: string;
  rejectionReason?: string;
}

export interface TimelineEntry {
  status: string;
  note: string;
  occurredAt: string;
}

export interface ApplicationStatusResponse {
  applicationId: number;
  currentStatus: string;
  timeline: TimelineEntry[];
}

export interface AdminStats {
  applicationsByStatus: { [key: string]: number };
  totalApplications: number;
}

export interface ReportResponse {
  applicationsByStatus: { [key: string]: number };
  totalApplications: number;
  totalRecordedDecisions: number;
  approvedDecisions: number;
  rejectedDecisions: number;
}

export interface UserInfo {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: 'APPLICANT' | 'ADMIN';
  enabled: boolean;
}
