import { Component, Input } from '@angular/core';
import { Customer } from '../../customer';

@Component({
  selector: 'app-customer-details',
  imports: [],
  templateUrl: './customer-details.html',
  styleUrl: './customer-details.css',
})
export class CustomerDetails {
  @Input() customer!: Customer;
}
