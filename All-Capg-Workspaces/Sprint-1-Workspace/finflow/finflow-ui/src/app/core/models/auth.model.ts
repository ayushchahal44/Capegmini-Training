export interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  role: 'APPLICANT' | 'ADMIN';
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}
