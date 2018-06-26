import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { AppConfig } from '../../../globals/app.config';

@Component({
  selector: 'app-announcement-settings',
  templateUrl: './announcement-settings.component.html',
  styleUrls: ['./announcement-settings.component.css']
})
export class AnnouncementSettingsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('announcementSettings') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.ANNOUNCEMENT;
        this.currentReport = this._appConfig.SETTINGS.SETTINGS_TAB.ANNOUNCEMENTS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.ANNOUNCEMENTS;
        this.reportType = this._appConfig.REPORT_TYPE.ANNOUNCEMENTS;
        this.filter = this._appConfig.FILTER.ANNOUNCEMENTS;
        this._tableService.init(this.table);
    }
}
