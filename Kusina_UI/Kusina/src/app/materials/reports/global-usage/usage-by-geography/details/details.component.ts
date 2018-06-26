import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { TableComponent } from '../../../../table/table.component';
import { DataSourceService } from '../../../../../services/datasource.service';
import { AppConfig } from '../../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-usage-by-geography-details',
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class UsageByGeographyDetailsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    filterMetrics: string;

    @ViewChild('detailsUsageByGeography') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }

    constructor(
        public dialogRef: MatDialogRef<UsageByGeographyDetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.DETAILSUSAGEBYSGEOGRAPHY;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_GEOGRAPHY_DETAILS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_GEOGRAPHY;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_GEOGRAPHY;
        this.filter = this._appConfig.FILTER.USAGE_BY_GEOGRAPHY;
        this.filterMetrics = this._appConfig.FILTER_METRICS.USAGE_BY_GEOGRAPHY;
        this._tableService.init(this.table);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

}
