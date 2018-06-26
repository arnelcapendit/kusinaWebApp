
import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-hits-by-geography',
    templateUrl: './hits-by-geography.component.html',
    styleUrls: ['./hits-by-geography.component.css']
})
export class AibspHitsByGeographyComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('hitsByGeography') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.HITSBYGEOGRAPHY;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.HITS_BY_GEOGRAPHY;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.HITS_BY_GEOGRAPHY;
        this.reportType = this._appConfig.REPORT_TYPE.HITS_BY_GEOGRAPHY;
        this.filter = this._appConfig.FILTER.HITS_BY_GEOGRAPHY;
        this._tableService.init(this.tableFilter);
    }

}
