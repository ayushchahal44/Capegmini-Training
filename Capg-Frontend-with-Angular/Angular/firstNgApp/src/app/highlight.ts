import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class Highlight {

  @Input('appHighlight') color: string = 'yellow'; 

  constructor(private element: ElementRef) {}

  @HostListener('mouseenter')
  onmouseenter() {
    this.element.nativeElement.style.backgroundColor = this.color;
  }

  @HostListener('mouseleave')
  onmouseleave() {
    this.element.nativeElement.style.backgroundColor = '';
  }
}