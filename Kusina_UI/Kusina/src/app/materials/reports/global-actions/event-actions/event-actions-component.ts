import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-event-actionmetrics',
    templateUrl: './event-actions-component.html',
    styleUrls: ['./event-actions-component.css']
})
export class EventActionsMetricsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    
    @ViewChild('eventActionMetrics') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.EVENTACTIONS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.EVENTS_TAB.EVENT_ACTIONS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.EVENT_ACTIONS;
        this.reportType = this._appConfig.REPORT_TYPE.EVENT_ACTIONS;
        this.filter = this._appConfig.FILTER.EVENT_ACTIONS;
        this._tableService.init(this.tableFilter);
    }

}
