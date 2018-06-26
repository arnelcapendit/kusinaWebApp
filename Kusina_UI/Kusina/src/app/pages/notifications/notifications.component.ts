import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../materials/table/table.component';
import { DataSourceService } from '../../services/datasource.service';
import { AppConfig } from '../../globals/app.config';
import { UserModel } from '../../models/user.model';
import { Router } from '@angular/router';
import { AnnouncementsService } from '../../services/announcement.service';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { DialogComponent } from '../../materials/dialog/dialog.component';
import { MatDialog } from '@angular/material';
import { ToolBarService } from '../../services/toolbar.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;
    acceptedDataPrivacy:boolean=false;
    private user:UserModel;

    @ViewChild('notificationArchive') public table: TableComponent;

    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig,
        private _router: Router,
        private _announcementService: AnnouncementsService,
        private _globalFilterService: GlobalFilterService,
        private _toolbarService: ToolBarService,
        public dialog: MatDialog
    ) { }

    ngOnInit() {
        this.user = this._globalFilterService.getUser();
        this._announcementService.getNotifCount(this.user);
        this.displayedColumns = this._appConfig.COLUMNS.NOTIFICATIONS;
        this.currentReport = this._appConfig.NOTIFICATIONS.NOTIFICATION;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.NOTIFICATIONS;
        this.reportType = this._appConfig.REPORT_TYPE.NOTIFICATIONS;
        this.filter = this._appConfig.FILTER.NOTIFICATIONS;
        this._tableService.init(this.table);
       
    }

}
