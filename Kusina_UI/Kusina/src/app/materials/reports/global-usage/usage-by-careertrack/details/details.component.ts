import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { TableComponent } from '../../../../table/table.component';
import { DataSourceService } from '../../../../../services/datasource.service';
import { AppConfig } from '../../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-usage-by-careertrack-details',
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class UsageByCareertrackDetailsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    filterMetrics: string;

    @ViewChild('detailsUsageByCareertracks') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }

    constructor(
        public dialogRef: MatDialogRef<UsageByCareertrackDetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.DETAILSUSAGEBYSCAREERTRACKS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_CAREERTRACKS_DETAILS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_CAREERTRACKS;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_CAREERTRACKS;
        this.filter = this._appConfig.FILTER.USAGE_BY_CAREERTRACKS;
        this.filterMetrics = this._appConfig.FILTER_METRICS.USAGE_BY_CAREERTRACKS;
        this._tableService.init(this.table);

    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

}
