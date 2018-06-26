import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { AppConfig } from '../../../globals/app.config';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.css']
})
export class ProfileSettingsComponent implements OnInit {


    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('profileSettings') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService,
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PROFILE;
        this.currentReport = this._appConfig.SETTINGS.SETTINGS_TAB.PROFILE;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PROFILE;
        this.reportType = this._appConfig.REPORT_TYPE.PROFILE;
        this.filter = this._appConfig.FILTER.PROFILE;
        this._tableService.init(this.table);
    }

}
