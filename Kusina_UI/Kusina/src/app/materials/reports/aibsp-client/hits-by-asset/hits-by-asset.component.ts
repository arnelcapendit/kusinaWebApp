import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-hits-by-asset',
    templateUrl: './hits-by-asset.component.html',
    styleUrls: ['./hits-by-asset.component.css']
})
export class AibspHitsByAssetComponent implements OnInit {

    
    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('hitsByAsset') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.HITSBYASSET;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.HITS_BY_ASSET;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.HITS_BY_ASSET;
        this.reportType = this._appConfig.REPORT_TYPE.HITS_BY_ASSET;
        this.filter = this._appConfig.FILTER.HITS_BY_ASSET;
        this._tableService.init(this.tableFilter);
    }
}
