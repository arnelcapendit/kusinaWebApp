import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-usage-by-level',
    templateUrl: './usage-by-level.component.html',
    styleUrls: ['./usage-by-level.component.css']
})
export class UsageByLevelComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('usageByLevel') public table: TableComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.USAGEBYSLEVEL;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_LEVEL;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_LEVEL;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_LEVEL;
        this.filter = this._appConfig.FILTER.USAGE_BY_LEVEL;
        this._tableService.init(this.table);
    }

}
