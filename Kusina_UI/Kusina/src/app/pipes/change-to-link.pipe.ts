import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'changeToLink'
})
export class ChangeToLinkPipe implements PipeTransform {

  transform(value: string): string {
    //console.log('ChangeToLinkPipe', value);
    if (value !== undefined && value !== null) {
        const start = value.split('[');
        if (start[1]) {
            const end = start[1].split(']');
            const link = end[0];
            const result = link.link('google.com');
            //console.log(link);
            const withLink = value.toString().replace('[',  result);
            const newValue = withLink.toString().replace(']', '</a>');
            return result;
        } else {
            return value;
        }
    } else {
      return value;
    }
  }

}
