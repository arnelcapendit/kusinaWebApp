import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-page-custom-info',
    templateUrl: './page-custom-info.component.html',
    styleUrls: ['./page-custom-info.component.css']
})
export class AibspPageCustomInfoComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('pageCustomInfo') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PAGESCUSTOMINFO;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.PAGES_CUSTOM_INFO;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PAGES_CUSTOM_INFO;
        this.reportType = this._appConfig.REPORT_TYPE.PAGES_CUSTOM_INFO;
        this.filter = this._appConfig.FILTER.PAGES_CUSTOM_INFO;
        this._tableService.init(this.tableFilter);
    }

}
