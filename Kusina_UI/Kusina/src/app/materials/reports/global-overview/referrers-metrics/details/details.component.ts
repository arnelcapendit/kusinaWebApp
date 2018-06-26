import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { TableComponent } from '../../../../table/table.component';
import { DataSourceService } from '../../../../../services/datasource.service';
import { AppConfig } from '../../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-referrers-details',
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class ReferrersDetailsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    filterMetrics: string;
    reportType: string;
    filter: string;

    @ViewChild('detailsreferrersName') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }
    constructor(
        public dialogRef: MatDialogRef<ReferrersDetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.OVERVIEWREFERRERSMETRICSDETAILS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.DETAILS_REFERRERS_METRICS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.OVERVIEW_REFERRERSMETRICS_DETAILS;
        this.reportType = this._appConfig.REPORT_TYPE.OVERVIEW_REFERRERSMETRICS_DETAILS;
        this.filter = this._appConfig.FILTER.OVERVIEW_REFERRERSMETRICS_DETAILS;
        this._tableService.init(this.table);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

}
