import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-geography',
    templateUrl: './geography.component.html',
    styleUrls: ['./geography.component.css']
})
export class AibspGeographyComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('geography') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.GEOGRAPHYBYSEGMENTNAME;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.GEOGRAPHY_BY_SEGMENT_NAME;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.GEOGRAPHY_BY_SEGMENT_NAME;
        this.reportType = this._appConfig.REPORT_TYPE.GEOGRAPHY_BY_SEGMENT_NAME;
        this.filter = this._appConfig.FILTER.GEOGRAPHY_BY_SEGMENT_NAME;
        this._tableService.init(this.tableFilter);
    }


}
