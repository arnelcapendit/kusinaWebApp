import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'changeToSpace'
})
export class ChangeToSpacePipe implements PipeTransform {

  transform(value: string): string {
    if (value !== undefined && value !== null) {
      return value.toString().replace(/_/g, ' ');
    } else {
      return ' ';
    }
  }

}
