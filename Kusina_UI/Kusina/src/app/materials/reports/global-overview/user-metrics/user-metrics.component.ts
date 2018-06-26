import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-overview-usermetrics',
    templateUrl: './user-metrics.component.html',
    styleUrls: ['./user-metrics.component.css']
})
export class OverviewUserMetricsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    
    @ViewChild('overviewUserMetrics') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.OVERVIEWUSERMETRICS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.USER_METRICS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.OVERVIEW_USERMETRICS;
        this.reportType = this._appConfig.REPORT_TYPE.OVERVIEW_USERMETRICS;
        this.filter = this._appConfig.FILTER.OVERVIEW_USERMETRICS;
        this._tableService.init(this.tableFilter);
    }

}
