import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserDetails } from './components/user-details/user-details';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, UserDetails,FormsModule],
  templateUrl: './app.html',
  // template: `
  // <h3> I can also work. </h3>
  // `,
  styleUrl: './app.css',
})
export class App {
  protected title = 'firstNgApp';
  user = {
    name: 'Ayush Chahal',
    email: 'ayushchahal44@gmail.com',
    age: 22,
    address: 'Bijnor,Uttar-Pradesh',
    phoneNo: [9760203187, 7017971564],
  };
  
  users = [
    {
      name: 'Ayush Chahal',
      email: 'ayushchahal44@gmail.com',
      age: 22,
      address: 'Bijnor,Uttar-Pradesh',
      phoneNo: [9760203187, 7017971564],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Aryan Singh',
      email: 'aryansingh@gmail.com',
      age: 25,
      address: 'Bijnor,Uttar-Pradesh',
      phoneNo: [8765432109, 9876543210],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Navdeep Rana',
      email: 'navdeeprana@gmail.com',
      age: 28,
      address: 'Bijnor,Uttar-Pradesh',
      phoneNo: [7654321098, 8765432109],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: false,
    },
    {
      name: 'Rohit Sharma',
      email: 'rohit@gmail.com',
      age: 30,
      address: 'Delhi, India',
      phoneNo: [9876543210, 9123456780],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Aman Verma',
      email: 'aman@gmail.com',
      age: 26,
      address: 'Noida, India',
      phoneNo: [8765432109, 9012345678],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: false,
    },
    {
      name: 'Priya Singh',
      email: 'priya@gmail.com',
      age: 24,
      address: 'Lucknow, India',
      phoneNo: [7654321098, 8901234567],
      gender: 'Female',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Neha Gupta',
      email: 'neha@gmail.com',
      age: 27,
      address: 'Ghaziabad, India',
      phoneNo: [6543210987, 7890123456],
      gender: 'Female',
      pic: '/images/pngtree.jpg',
      edit: false,
    },
    {
      name: 'Karan Mehta',
      email: 'karan@gmail.com',
      age: 29,
      address: 'Mumbai, India',
      phoneNo: [5432109876, 6789012345],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Simran Kaur',
      email: 'simran@gmail.com',
      age: 25,
      address: 'Chandigarh, India',
      phoneNo: [4321098765, 5678901234],
      gender: 'Female',
      pic: '/images/pngtree.jpg',
      edit: false,
    },
    {
      name: 'Arjun Kapoor',
      email: 'arjun@gmail.com',
      age: 31,
      address: 'Pune, India',
      phoneNo: [3210987654, 4567890123],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Sneha Reddy',
      email: 'sneha@gmail.com',
      age: 23,
      address: 'Hyderabad, India',
      phoneNo: [2109876543, 3456789012],
      gender: 'Female',
      pic: '/images/pngtree.jpg',
      edit: false,
    },
    {
      name: 'Vikas Yadav',
      email: 'vikas@gmail.com',
      age: 28,
      address: 'Jaipur, India',
      phoneNo: [1098765432, 2345678901],
      gender: 'Male',
      pic: '/images/pngtree.jpg',
      edit: true,
    },
    {
      name: 'Pooja Sharma',
      email: 'pooja@gmail.com',
      age: 26,
      address: 'Bhopal, India',
      phoneNo: [9988776655, 2233445566],
      gender: 'Female',
      pic: '/images/pngtree.jpg',
      edit: false,
    },
  ];
  change = true;

  changeTitle() {
    if (this.change === true) {
      this.title = 'Title Changed';
      this.change = false;
    } else {
      this.title = 'firstNgApp';
      this.change = true;
    }
  }
  viewType = 'card';

  card() {
    this.viewType = 'card';
  }

  list() {
    this.viewType = 'list';
  }

  parentAction(data: any){
    console.log('In parent component');
    console.log(data);
    for(let i =0;i<this.users.length;i++){
      if(this.users[i].name === data.name){
        this.users.splice(i,1);
        break;
      }
    }
  }

start = 0;
jump = 4;

next() {
  if (this.start + this.jump < this.users.length) {
    this.start += this.jump;
  }
}

previous() {
  if (this.start - this.jump >= 0) {
    this.start -= this.jump;
  }
}

}
