import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { TableComponent } from '../../../../table/table.component';
import { DataSourceService } from '../../../../../services/datasource.service';
import { AppConfig } from '../../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-download-details',
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class DownloadDetailsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    filterMetrics: string;
    reportType: string;
    filter: string;

    @ViewChild('detailsdownload') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }
    constructor(
        public dialogRef: MatDialogRef<DownloadDetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.OVERVIEWDOWNLOADMETRICSDETAILS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.DETAILS_DOWNLOAD_METRICS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.OVERVIEW_DOWNLOADMETRICS_DETAILS;
        this.reportType = this._appConfig.REPORT_TYPE.OVERVIEW_DOWNLOADMETRICS_DETAILS;
        this.filter = this._appConfig.FILTER.OVERVIEW_DOWNLOADMETRICS_DETAILS;
        this._tableService.init(this.table);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

}
