import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-page-vs-geography',
    templateUrl: './page-vs-geography.component.html',
    styleUrls: ['./page-vs-geography.component.css']
})
export class PagesVsGeographyComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('pagesVsGeography') public table: TableComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PAGESVSGEOGRAPHY;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_GEOGRAPHY;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PAGES_BY_GEOGRAPHY;
        this.reportType = this._appConfig.REPORT_TYPE.PAGES_BY_GEOGRAPHY;
        this.filter = this._appConfig.FILTER.PAGES_BY_GEOGRAPHY;
        this._tableService.init(this.table);
    }

}
