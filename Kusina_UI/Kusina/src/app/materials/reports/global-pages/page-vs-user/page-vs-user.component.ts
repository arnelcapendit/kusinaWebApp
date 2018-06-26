import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-page-vs-user',
    templateUrl: './page-vs-user.component.html',
    styleUrls: ['./page-vs-user.component.css']
})
export class PagesVsUserComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('pagesVsUser') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PAGESVSUSER;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_USER;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PAGES_BY_USER;
        this.reportType = this._appConfig.REPORT_TYPE.PAGES_BY_USER;
        this.filter = this._appConfig.FILTER.PAGES_BY_USER;
        this._tableService.init(this.tableFilter);
    }

}
