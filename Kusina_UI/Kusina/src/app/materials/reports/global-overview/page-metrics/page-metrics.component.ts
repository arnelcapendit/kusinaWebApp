import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-overview-pagemetrics',
    templateUrl: './page-metrics.component.html',
    styleUrls: ['./page-metrics.component.css']
})
export class OverviewPageMetricsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    
    @ViewChild('overviewPageMetrics') public tableFilter: TableFilterComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.OVERVIEWPAGEMETRICS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.PAGE_METRICS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.OVERVIEW_PAGEMETRICS;
        this.reportType = this._appConfig.REPORT_TYPE.OVERVIEW_PAGEMETRICS;
        this.filter = this._appConfig.FILTER.OVERVIEW_PAGEMETRICS;
        this._tableService.init(this.tableFilter);;
    }
 


}
