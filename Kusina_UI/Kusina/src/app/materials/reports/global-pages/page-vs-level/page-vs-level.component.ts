import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-page-vs-level',
    templateUrl: './page-vs-level.component.html',
    styleUrls: ['./page-vs-level.component.css']
})
export class PagesVsLevelComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('pagesVsLevel') public table: TableComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PAGESVSLEVEL;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_LEVEL;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PAGES_BY_LEVEL;
        this.reportType = this._appConfig.REPORT_TYPE.PAGES_BY_LEVEL;
        this.filter = this._appConfig.FILTER.PAGES_BY_LEVEL;
        this._tableService.init(this.table);
    }

}
