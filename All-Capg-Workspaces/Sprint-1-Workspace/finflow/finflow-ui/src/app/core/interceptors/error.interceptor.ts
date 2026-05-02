import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'An unexpected error occurred';

      if (error.error instanceof ErrorEvent) {
        // Client-side error
        errorMessage = error.error.message;
      } else {
        // Server-side error
        errorMessage = error.error?.message || `Error Code: ${error.status}\nMessage: ${error.message}`;
      }

      // Handle specific status codes
      if (error.status === 401) {
        // Auth handled by AuthInterceptor/Guard, but we can show a toast
        notificationService.error('Session expired or unauthorized');
      } else if (error.status === 403) {
        notificationService.error('You do not have permission to perform this action');
      } else if (error.status === 0) {
        notificationService.error('Could not connect to the server. Please check your internet connection.');
      } else {
        notificationService.error(errorMessage);
      }

      return throwError(() => error);
    })
  );
};
