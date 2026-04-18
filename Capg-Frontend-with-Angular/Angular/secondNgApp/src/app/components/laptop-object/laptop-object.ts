import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-laptop-object',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './laptop-object.html',
  styleUrl: './laptop-object.css',
})
export class LaptopObject {
  @Input() laptop : any;
}
