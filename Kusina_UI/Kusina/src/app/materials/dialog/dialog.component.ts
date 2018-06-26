import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { CustomReportService } from '../../services/custom-report.service';
import { AppConfig } from '../../globals/app.config';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-dialog',
    templateUrl: './dialog.component.html',
    styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
    selectedCategory: string;
    uniqueParam: string;
    getData: any;

    constructor(
        public dialogRef: MatDialogRef<DialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        public _customReportService: CustomReportService,
        private _appConfig: AppConfig,
    ) {}

    ngOnInit() {
        this.uniqueParam = this.data.url;
        this._customReportService.URL_details = this.data.url;
        this.selectedCategory = this.data.dialogName;
        this.getData = this.data.data;
    }
    closeDialogue(t:any){
        this.dialogRef.close();
    }

}
