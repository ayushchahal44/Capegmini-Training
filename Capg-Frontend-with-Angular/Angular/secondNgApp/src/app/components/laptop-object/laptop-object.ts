import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
// import { TruncatePipe } from '../../truncate-pipe';

@Component({
  selector: 'app-laptop-object',
  standalone: true,
  // imports: [CommonModule,TruncatePipe],
  imports: [CommonModule],
  templateUrl: './laptop-object.html',
  styleUrl: './laptop-object.css',
})
export class LaptopObject {
  @Input() laptop : any;
  @Input() truncValue : string = '';


  isExpand = false;

toggle() {
  this.isExpand = !this.isExpand;
}
}
