import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-career-level',
    templateUrl: './career-level.component.html',
    styleUrls: ['./career-level.component.css']
})
export class AibspCareerLevelComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('careerLevel') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.CAREERLEVELBYSEGMENTNAME;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.CAREERLEVEL_BY_SEGMENT_NAME;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.CAREERLEVEL_BY_SEGMENT_NAME;
        this.reportType = this._appConfig.REPORT_TYPE.CAREERLEVEL_BY_SEGMENT_NAME;
        this.filter = this._appConfig.FILTER.CAREERLEVEL_BY_SEGMENT_NAME;
        this._tableService.init(this.tableFilter);
    }

}
