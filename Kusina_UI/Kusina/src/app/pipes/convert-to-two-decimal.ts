import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'convertToTwoDecimal'
})
export class ConvertToTwoDecimalPipe implements PipeTransform {

  transform(value: string): string {
    // const val = parseFloat(value);
    // console.log('convertToTwoDecimal: ', val);

    if (value !== undefined && value !== null) {
      const val1 = value.split(',')
      const val = value.split('.');
      if (val.length >= 3) {
        return value;
      } else if (val1) {
        return value;
      } else {
        const floatValue = parseFloat(value);
        const indexValue = value.indexOf(':');
        if (floatValue.toString() === 'NaN') {
            return value;
        } else if (indexValue !== -1) {
            return value;
        } else {
            const intValue = floatValue.toFixed(2);
            const splitValue = intValue.split('.');
            if (splitValue[1].toString() === '00') {
                return floatValue.toString();
            } else {
                return intValue.toString();
            }
        }
      }
    } else {
      return ' ';
    }
  }

}
