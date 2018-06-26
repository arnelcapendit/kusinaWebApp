import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-page-vs-careertrack',
    templateUrl: './page-vs-careertrack.component.html',
    styleUrls: ['./page-vs-careertrack.component.css']
})
export class PagesVsCareertrackComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('pagesVsCareertracks') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PAGESVSCAREERTRACKS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_CAREERTRACKS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PAGES_BY_CAREERTRACKS;
        this.reportType = this._appConfig.REPORT_TYPE.PAGES_BY_CAREERTRACKS;
        this.filter = this._appConfig.FILTER.PAGES_BY_CAREERTRACKS;
        this._tableService.init(this.table);
    }

}
