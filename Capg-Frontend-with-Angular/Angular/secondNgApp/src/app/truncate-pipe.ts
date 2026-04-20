import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate',
  standalone: true
})
export class TruncatePipe implements PipeTransform {
  transform(str: string, max = 20, trail = '...'): string {
    if(!str) return '';
    if(str.length <= max) return str;
    return str.substring(0, max) + trail;
  }
}
