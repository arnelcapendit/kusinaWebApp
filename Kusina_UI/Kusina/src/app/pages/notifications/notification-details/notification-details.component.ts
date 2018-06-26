import { Component, OnInit, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-notification-details',
  templateUrl: './notification-details.component.html',
  styleUrls: ['./notification-details.component.css']
})
export class NotificationDetailsComponent implements OnInit {

    data: any;
    content: string;
    convertedContent: string;

    @Input()
    set setData(val) {
        this.data = val;
        this.content = val.CONTENT;
    }

    constructor( public dialogRef: MatDialogRef<NotificationDetailsComponent> ) { }

    ngOnInit() {
        this.convertToLink(this.content);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

    convertToLink(value) {
        if (value !== undefined && value !== null) {
            const result = value.toString().replace(/~%/g, '&#13;&#10;');
            this.convertedContent = result;
        } else {
            this.convertedContent = value;
        }
    }

}
