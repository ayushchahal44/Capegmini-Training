import { Component } from '@angular/core';
import { Customer } from '../../customer';
import { CustomerDetails } from '../customer-details/customer-details';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-customer-list',
  imports: [CommonModule,CustomerDetails,FormsModule],
  templateUrl: './customer-list.html',
  styleUrl: './customer-list.css',
})
export class CustomerList {
  customers: Customer[] = [
  { id: 1, name: 'Ayush Chahal', address: 'Bijnor, Uttar Pradesh, India', email: 'ayush1@gmail.com', phone: '9000000001', dateOfBirth: '1990-01-01', gender: 'Male' },
  { id: 2, name: 'Aman Verma', address: 'Delhi, India', email: 'aman2@gmail.com', phone: '9000000002', dateOfBirth: '1991-02-02', gender: 'Male' },
  { id: 3, name: 'Rohit Sharma', address: 'Mumbai, Maharashtra, India', email: 'rohit3@gmail.com', phone: '9000000003', dateOfBirth: '1989-03-03', gender: 'Male' },
  { id: 4, name: 'Neha Singh', address: 'Lucknow, Uttar Pradesh, India', email: 'neha4@gmail.com', phone: '9000000004', dateOfBirth: '1992-04-04', gender: 'Female' },
  { id: 5, name: 'Priya Mehta', address: 'Ahmedabad, Gujarat, India', email: 'priya5@gmail.com', phone: '9000000005', dateOfBirth: '1993-05-05', gender: 'Female' },
  { id: 6, name: 'Karan Malhotra', address: 'Delhi, India', email: 'karan6@gmail.com', phone: '9000000006', dateOfBirth: '1990-06-06', gender: 'Male' },
  { id: 7, name: 'Sneha Kapoor', address: 'Chandigarh, India', email: 'sneha7@gmail.com', phone: '9000000007', dateOfBirth: '1994-07-07', gender: 'Female' },
  { id: 8, name: 'Vikas Yadav', address: 'Noida, Uttar Pradesh, India', email: 'vikas8@gmail.com', phone: '9000000008', dateOfBirth: '1991-08-08', gender: 'Male' },
  { id: 9, name: 'Anjali Gupta', address: 'Kanpur, Uttar Pradesh, India', email: 'anjali9@gmail.com', phone: '9000000009', dateOfBirth: '1995-09-09', gender: 'Female' },
  { id: 10, name: 'Rahul Jain', address: 'Jaipur, Rajasthan, India', email: 'rahul10@gmail.com', phone: '9000000010', dateOfBirth: '1988-10-10', gender: 'Male' },
  { id: 11, name: 'Pooja Sharma', address: 'Delhi, India', email: 'pooja11@gmail.com', phone: '9000000011', dateOfBirth: '1996-11-11', gender: 'Female' },
  { id: 12, name: 'Suresh Patel', address: 'Surat, Gujarat, India', email: 'suresh12@gmail.com', phone: '9000000012', dateOfBirth: '1987-12-12', gender: 'Male' },
  { id: 13, name: 'Meena Iyer', address: 'Chennai, Tamil Nadu, India', email: 'meena13@gmail.com', phone: '9000000013', dateOfBirth: '1993-01-13', gender: 'Female' },
  { id: 14, name: 'Arjun Reddy', address: 'Hyderabad, Telangana, India', email: 'arjun14@gmail.com', phone: '9000000014', dateOfBirth: '1992-02-14', gender: 'Male' },
  { id: 15, name: 'Kavita Nair', address: 'Kochi, Kerala, India', email: 'kavita15@gmail.com', phone: '9000000015', dateOfBirth: '1994-03-15', gender: 'Female' },
  { id: 16, name: 'Deepak Kumar', address: 'Patna, Bihar, India', email: 'deepak16@gmail.com', phone: '9000000016', dateOfBirth: '1990-04-16', gender: 'Male' },
  { id: 17, name: 'Ritu Saxena', address: 'Agra, Uttar Pradesh, India', email: 'ritu17@gmail.com', phone: '9000000017', dateOfBirth: '1995-05-17', gender: 'Female' },
  { id: 18, name: 'Manish Tiwari', address: 'Bhopal, Madhya Pradesh, India', email: 'manish18@gmail.com', phone: '9000000018', dateOfBirth: '1989-06-18', gender: 'Male' },
  { id: 19, name: 'Swati Agarwal', address: 'Kolkata, West Bengal, India', email: 'swati19@gmail.com', phone: '9000000019', dateOfBirth: '1996-07-19', gender: 'Female' },
  { id: 20, name: 'Nikhil Bansal', address: 'Pune, Maharashtra, India', email: 'nikhil20@gmail.com', phone: '9000000020', dateOfBirth: '1991-08-20', gender: 'Male' }
  ]

searchText = '';
page = 0;
size = 5;

get filteredCustomers() {
  return this.customers.filter(c =>
    c.name.toLowerCase().includes(this.searchText.toLowerCase())
  );
}

get paginatedCustomers() {
  const start = this.page * this.size;
  return this.filteredCustomers.slice(start, start + this.size);
}

next() {
  if ((this.page + 1) * this.size < this.filteredCustomers.length) this.page++;
}

prev() {
  if (this.page > 0) this.page--;
}

isFirstPage() {
  return this.page === 0;
}

isLastPage() {
  return (this.page + 1) * this.size >= this.filteredCustomers.length;
}

onSearchChange() {
  this.page = 0;
}
}
