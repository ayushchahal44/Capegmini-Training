import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { ApiService } from './api.service';
import { Router, provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { AuthResponse, User } from '../models/auth.model';

describe('AuthService', () => {
  let service: AuthService;
  let apiServiceSpy: jasmine.SpyObj<ApiService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const apiSpy = jasmine.createSpyObj('ApiService', ['post']);
    const rSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        AuthService,
        provideRouter([]),
        { provide: ApiService, useValue: apiSpy },
        { provide: Router, useValue: rSpy }
      ]
    });

    service = TestBed.inject(AuthService);
    apiServiceSpy = TestBed.inject(ApiService) as jasmine.SpyObj<ApiService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    
    // Clear localStorage before each test
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and store user/token', (done) => {
    const mockUser: User = { id: 1, email: 'test@example.com', role: 'APPLICANT', firstName: 'Test', lastName: 'User' };
    const mockRes: AuthResponse = { token: 'mock-token', user: mockUser };
    
    apiServiceSpy.post.and.returnValue(of(mockRes));

    service.login({ email: 'test@example.com', password: 'password' }).subscribe(() => {
      expect(service.token()).toBe('mock-token');
      expect(service.currentUser()).toEqual(mockUser);
      expect(localStorage.getItem('finflow_token')).toBe('mock-token');
      done();
    });
  });

  it('should logout and clear storage', () => {
    service.logout();
    expect(service.token()).toBeNull();
    expect(service.currentUser()).toBeNull();
    expect(localStorage.getItem('finflow_token')).toBeNull();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/auth/login']);
  });
});
