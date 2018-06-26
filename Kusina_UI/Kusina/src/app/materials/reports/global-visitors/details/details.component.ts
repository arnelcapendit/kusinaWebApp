import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { MatDialogRef } from '@angular/material';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
  selector: 'app-details-visitor',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {


    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('detailsVisitorLogs') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }

    constructor(
        public dialogRef: MatDialogRef<DetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.VISITORSDETAILS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.VISITORLOGS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.REPORTS.VISITORSDETAILS;
        this._tableService.init(this.table);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }


}
