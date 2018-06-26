import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { AppConfig } from '../../../globals/app.config';
import { TableFilterComponent } from '../../../materials/table-filter/table-filter.component';

@Component({
    selector: 'app-user-access',
    templateUrl: './user-access.component.html',
    styleUrls: ['./user-access.component.css']
})
export class UserAccessComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    
    @ViewChild('allusers') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        //this._expressionChangeService.detectChanges();
        this.displayedColumns = this._appConfig.COLUMNS.USERACCESS;
        this.currentReport = this._appConfig.SETTINGS.SETTINGS_TAB.USER_ACCESS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.USER_ACCESS;
        this.reportType = this._appConfig.REPORT_TYPE.USER_ACCESS;
        this.filter = this._appConfig.FILTER.USER_ACCESS;
        this._tableService.init(this.table);
    }

}
