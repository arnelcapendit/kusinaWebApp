import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-overview-downloadmetrics',
    templateUrl: './download-metrics.component.html',
    styleUrls: ['./download-metrics.component.css']
})
export class OverviewDownloadMetricsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    
    @ViewChild('overviewDownloadMetrics') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.OVERVIEWDOWNLOADMETRICS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.DOWNLOAD_METRICS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.OVERVIEW_DOWNLOADMETRICS;
        this.reportType = this._appConfig.REPORT_TYPE.OVERVIEW_DOWNLOADMETRICS;
        this.filter = this._appConfig.FILTER.OVERVIEW_DOWNLOADMETRICS;
        this._tableService.init(this.tableFilter);;
    }

}
