import { Injectable, signal } from '@angular/core';

export interface Toast {
  message: string;
  type: 'success' | 'error' | 'info';
  id: number;
}


@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private readonly toastsSignal = signal<Toast[]>([]);
  readonly toasts = this.toastsSignal.asReadonly();
  
  constructor() {}


  show(message: string, type: 'success' | 'error' | 'info' = 'info') {
    const id = Date.now();
    const newToast: Toast = { id, message, type };
    
    this.toastsSignal.update(toasts => [...toasts, newToast]);

    // Auto-remove after 5 seconds
    setTimeout(() => {
      this.remove(id);
    }, 5000);
  }

  success(message: string) {
    this.show(message, 'success');
  }

  error(message: string) {
    this.show(message, 'error');
  }

  remove(id: number) {
    this.toastsSignal.update(toasts => toasts.filter(t => t.id !== id));
  }
}
