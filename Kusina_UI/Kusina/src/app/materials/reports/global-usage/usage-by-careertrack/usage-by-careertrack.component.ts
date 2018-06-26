import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
    selector: 'app-usage-by-careertrack',
    templateUrl: './usage-by-careertrack.component.html',
    styleUrls: ['./usage-by-careertrack.component.css']
})
export class UsageByCareertrackComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('usageByCareertracks') public table: TableComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.USAGEBYSCAREERTRACKS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USAGE_BY_CAREERTRACKS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_CAREERTRACKS;
        this.reportType = this._appConfig.REPORT_TYPE.USAGE_BY_CAREERTRACKS;
        this.filter = this._appConfig.FILTER.USAGE_BY_CAREERTRACKS;
        this._tableService.init(this.table);
    }

}
