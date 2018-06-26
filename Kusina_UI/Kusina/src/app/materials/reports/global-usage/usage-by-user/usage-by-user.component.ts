import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-usage-by-user',
    templateUrl: './usage-by-user.component.html',
    styleUrls: ['./usage-by-user.component.css']
})
export class UsageByUserComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('usageByUser') public table: TableComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.USAGEBYSUSER;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_USER;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_USER;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_USER;
        this.filter = this._appConfig.FILTER.USAGE_BY_USER;
        this._tableService.init(this.table);
    }

}
