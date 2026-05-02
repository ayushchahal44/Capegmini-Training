import { Component, Input } from '@angular/core';
import { Customer } from '../../customer';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer-details.html',
  styleUrl: './customer-details.css',
})
export class CustomerDetails {
  @Input() customer!: Customer;
}