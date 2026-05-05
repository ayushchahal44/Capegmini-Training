import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatStepperModule } from '@angular/material/stepper';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LucideAngularModule, LayoutDashboard, FileText, Users, BarChart3, Settings, LogOut, ChevronLeft, ChevronRight, Menu, Bell, User, Search, Mail, Lock, Loader, ArrowRight, CircleCheck, CircleX, Clock, Inbox, Eye, Filter, TrendingUp, Shield, Download, Plus, CircleAlert, DollarSign, Briefcase, TrendingUpIcon, AlertTriangle, CheckCircle, XCircle, RefreshCw, MessageSquare, Percent, Calendar, Upload } from 'lucide-angular';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatStepperModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,
    MatDialogModule,
    MatProgressBarModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    LucideAngularModule.pick({
      LayoutDashboard, FileText, Users, BarChart3, Settings, LogOut, ChevronLeft, ChevronRight,
      Menu, Bell, User, Search, Mail, Lock, Loader, ArrowRight, CircleCheck, CircleX,
      Clock, Inbox, Eye, Filter, TrendingUp, Shield, Download, Plus, CircleAlert,
      DollarSign, Briefcase, TrendingUpIcon, AlertTriangle, CheckCircle, XCircle, RefreshCw,
      MessageSquare, Percent, Calendar, Upload
    })
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatStepperModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,
    MatDialogModule,
    MatProgressBarModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    LucideAngularModule
  ]
})
export class SharedModule {}
