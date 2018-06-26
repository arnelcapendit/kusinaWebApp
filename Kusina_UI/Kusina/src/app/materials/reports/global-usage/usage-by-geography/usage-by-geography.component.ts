import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-usage-by-geography',
    templateUrl: './usage-by-geography.component.html',
    styleUrls: ['./usage-by-geography.component.css']
})
export class UsageByGeographyComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('usageByGeography') public table: TableComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.USAGEBYSGEOGRAPHY;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_GEOGRAPHY;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_GEOGRAPHY;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_GEOGRAPHY;
        this.filter = this._appConfig.FILTER.USAGE_BY_GEOGRAPHY;
        this._tableService.init(this.table);
    }

}
