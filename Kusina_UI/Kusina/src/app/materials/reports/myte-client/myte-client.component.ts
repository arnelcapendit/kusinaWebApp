import { Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { MatTabChangeEvent, MatTabsModule, MatTabGroup } from '@angular/material';
import { AppConfig } from '../../../globals/app.config';

@Component({
  selector: 'app-myte-client',
  templateUrl: './myte-client.component.html',
  styleUrls: ['./myte-client.component.css']
})
export class MyteClientComponent implements OnInit {
    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('myteTable1') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig,
        private _expressionChangeService: ChangeDetectorRef
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.MYTE_PAGEVSCOUNTRIES;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.REPORTS.PAGE_VS_COUNTRIES;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.MYTE_PAGEVSCOUNTRIES;
        this.reportType = this._appConfig.REPORT_TYPE.MYTE_PAGEVSCOUNTRIES;
        this._tableService.init(this.table);
    }

}
