import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { TableComponent } from '../../../../table/table.component';
import { DataSourceService } from '../../../../../services/datasource.service';
import { AppConfig } from '../../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-usage-by-level-details',
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class UsageByLevelDetailsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    filterMetrics: string;

    @ViewChild('detailsUsageByLevel') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }

    constructor(
        public dialogRef: MatDialogRef<UsageByLevelDetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.DETAILSUSAGEBYSLEVEL;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_LEVEL_DETAILS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_LEVEL;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_LEVEL;
        this.filter = this._appConfig.FILTER.USAGE_BY_LEVEL;
        this.filterMetrics = this._appConfig.FILTER_METRICS.USAGE_BY_LEVEL;
        this._tableService.init(this.table);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

}
