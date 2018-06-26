import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'countSpanTime'
})
export class CountSpanTime implements PipeTransform {
  newValue=null;
  transform(value: string): string {
    if (value !== undefined && value !== null) {
        const today = new Date();
        const current_day = today.getDate();
        const current_month = today.getMonth() + 1;
        const current_year = today.getFullYear();
        const current_hour = today.getHours();
        const current_minute = today.getMinutes();
        const current_second = today.getSeconds();

        const newDAte = (current_day + '/' + current_month + '/' + current_year + ' ' + current_hour + ':' + current_minute + ':'+  current_second);
        //console.log("Today's Date: ", newDAte);

        const createdDate = new Date(value);
        const created_day = createdDate.getDate();
        const created_month = createdDate.getMonth() + 1;
        const created_year = createdDate.getFullYear();
        const created_hour = createdDate.getHours();
        const created_minute = createdDate.getMinutes();
        const created_second = createdDate.getSeconds();
        //console.log("Created Date: ", createdDate);
        //console.log("Value Date: ", value);
        const newCreatedDAte = (created_day + '/' + created_month + '/' + created_year + ' ' + created_hour + ':' + created_minute + ':'+  created_second);

        const diff = Math.abs(today.getTime() - createdDate.getTime());
        const diffDays = Math.round(diff / (1000 * 3600 * 24));
        const diffHours = Math.round(diff / (1000 * 3600));
        const diffMinutes = Math.round(diff / (1000 * 60));


        if (diffDays <= 0 && diffHours < 24){
            if(diffMinutes == 1){
                this.newValue = (diffMinutes + ' minute' + ' ago');
            }else if(diffMinutes > 1 && diffMinutes < 60){
                this.newValue = (diffMinutes + ' minutes' + ' ago');
            }else if(diffHours == 1){
                this.newValue = (diffHours + ' hour' + ' ago');
            }else if(diffHours > 1 && diffHours < 24){
                this.newValue = (diffHours + ' hours' + ' ago');
            }else{
                this.newValue = newCreatedDAte;
            }
        }else{
            if(diffDays == 1){
                // tslint:disable-next-line:max-line-length
                const minuteTime = (diffMinutes % 60);
                this.newValue = ('Yesterday' + ' ' + 'at ' + diffHours + ':' + minuteTime + ' ' + ((diffHours > 12) ? 'PM' : 'AM'));
            }else if (diffDays > 1 && diffDays < 7){
                this.newValue = ( diffDays + ' ' + 'days' + ' ago' );
            }else if (diffDays >= 7 && diffDays < 28){
                if(diffDays == 7){
                    this.newValue = ( Math.floor(diffDays / 7) + ' ' + 'week' + ' ago' );
                }else{
                    this.newValue = ( Math.floor(diffDays / 7) + ' ' + 'weeks' + ' ago' );
                }
            }else if (diffDays >= 28 && diffDays < 364){
                if(diffDays >= 28 && diffDays <= 31){
                    this.newValue = ( Math.floor(diffDays / 28) + ' ' + 'month' + ' ago' );
                }else{
                    this.newValue = ( Math.floor(diffDays / 30) + ' ' + 'months' + ' ago' );
                }
            }else if(diffDays > 364){
                this.newValue = ( Math.floor(diffDays / 364) + ' ' + 'year' + ' ago' );
            }else{
                this.newValue = newCreatedDAte;
            } 
        }
      return this.newValue;
    } else {
      return ' ';
    }
  }

}
