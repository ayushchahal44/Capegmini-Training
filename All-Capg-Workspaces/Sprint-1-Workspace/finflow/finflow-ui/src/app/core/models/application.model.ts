export interface LoanApplication {
  id: number;
  applicantId: string;
  fullName: string;
  phone: string;
  address: string;
  dateOfBirth: string;
  employer: string;
  annualIncome: number;
  employmentType: 'SALARIED' | 'SELF_EMPLOYED' | 'BUSINESS';
  loanAmount: number;
  tenureMonths: number;
  loanPurpose: string;
  status: 'DRAFT' | 'SUBMITTED' | 'DOCS_PENDING' | 'DOCS_VERIFIED' | 'UNDER_REVIEW' | 'APPROVED' | 'REJECTED';
  createdAt: string;
  updatedAt: string;
}
