import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-user-details',
  standalone: true,
  imports: [],
  templateUrl: './user-details.html',
  styleUrl: './user-details.css',
})
export class UserDetails {
  editMode = false;
  @Input() name = 'Ayush Chahal';
  @Input() age = 21;
  @Input() address = 'Bijnor';
  @Input() gender = 'Male';
  @Input() pic = '';
  @Output() onupdate = new EventEmitter<any>();

  newName =""

  informParents(){
    this.onupdate.emit({
      name: this.name,
      age: this.age,
      address: this.address,  
  })
}


  
  @Input() viewType!: string; 

  constructor() {
    console.log('In Constructor of userDetails');
    console.log('name:' + this.name);
    console.log('age:' + this.age);
    console.log('address:' + this.address);
    console.log('gender:' + this.gender);
  }

  toggleEditMode(){
    console.log('Toggling edit mode');
    this.editMode = !this.editMode;
  }

}
