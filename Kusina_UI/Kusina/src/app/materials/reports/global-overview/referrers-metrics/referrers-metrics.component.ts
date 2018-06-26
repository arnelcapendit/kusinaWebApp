import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-overview-referrersmetrics',
    templateUrl: './referrers-metrics.component.html',
    styleUrls: ['./referrers-metrics.component.css']
})
export class OverviewReferrersMetricsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    
    @ViewChild('overviewReferrersMetrics') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.OVERVIEWREFERRERSMETRICS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.REFERRERS_METRICS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.OVERVIEW_REFERRERSMETRICS;
        this.reportType = this._appConfig.REPORT_TYPE.OVERVIEW_REFERRERSMETRICS;
        this.filter = this._appConfig.FILTER.OVERVIEW_REFERRERSMETRICS;
        this._tableService.init(this.tableFilter);;
    }


}
