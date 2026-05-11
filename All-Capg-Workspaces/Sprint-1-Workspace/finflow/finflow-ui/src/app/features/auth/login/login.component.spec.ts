import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { LucideAngularModule, Mail, Lock, Loader, ArrowRight } from 'lucide-angular';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let router: Router;

  beforeEach(async () => {
    const aSpy = jasmine.createSpyObj('AuthService', ['login']);

    await TestBed.configureTestingModule({
      imports: [
        LoginComponent, 
        FormsModule, 
        RouterTestingModule,
        LucideAngularModule.pick({ Mail, Lock, Loader, ArrowRight })
      ],
      providers: [
        { provide: AuthService, useValue: aSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authServiceSpy = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not call login if form is invalid', () => {
    component.credentials = { email: '', password: '' };
    component.onSubmit();
    expect(authServiceSpy.login).not.toHaveBeenCalled();
  });

  it('should call login and navigate to applicant dashboard on success', () => {
    const mockRes = { user: { role: 'APPLICANT' }, token: 'tk' } as any;
    authServiceSpy.login.and.returnValue(of(mockRes));
    
    component.credentials = { email: 'test@test.com', password: 'password' };
    component.onSubmit();
    
    expect(authServiceSpy.login).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/applicant/dashboard']);
  });

  it('should show error message on login failure', () => {
    authServiceSpy.login.and.returnValue(throwError(() => ({ status: 401 })));
    
    component.credentials = { email: 'test@test.com', password: 'wrong' };
    component.onSubmit();
    
    expect(component.error).toContain('Invalid email or password');
    expect(component.loading).toBeFalse();
  });
});
