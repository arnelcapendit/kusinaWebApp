import { Component, OnInit, Output, ViewChild, ElementRef, Input, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { TableModel } from '../../models/table.model';
import { EventEmitter } from 'events';
import { MatPaginator, MatSort, MatSnackBar, MatDialog } from '@angular/material';
import { AppConfig } from '../../globals/app.config';
import { UserAccessService } from '../../services/user-access.service';
import { DateRangePickerService } from '../../services/daterange.service';
import { DataSourceService } from '../../services/datasource.service';
import { AnnouncementsService } from '../../services/announcement.service';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { debounceTime, tap } from 'rxjs/operators';
import { merge } from 'rxjs/observable/merge';
import { AnnouncementsDataSource } from '../../datasources/announcement.datasource';
import { DialogComponent } from '../dialog/dialog.component';
import { NotificationsDataSource } from '../../datasources/notification.datasource';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent extends TableModel implements OnInit, AfterViewInit {

    displayedColumns = [];
    dataSource: any;
    currentReport: string | null;
    isUserAccess = false;
    isUserAccessAnnouncement = false;
    isExportTable = true;
    currentParam: string | null;
    searchPlaceholder: string;
    reporttype: string;
    filterData: string;


    @Output() dateR = new EventEmitter();
    @Output() columnsChange = new EventEmitter();
    @Output() reportChange = new EventEmitter();

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    @ViewChild('filter') filter: ElementRef;

    @Input()
    get columns(){
        return this.displayedColumns;
    }

    set columns(val) {
        this.displayedColumns = val;
    }

    @Input()
    get report(){
        return this.currentReport;
    }
    set report(val) {
        this.currentReport = val;
        this.reportChange.emit(this.currentReport);
    }

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }

    @Input()
    set placeholder(val) {
        this.searchPlaceholder = val;
    }

    @Input()
    set reportType(val) {
        this.reporttype = val;
    }

    @Input()
    set filtered(val) {
        this.filterData = val;
    }


    constructor(
        private _appConfig: AppConfig,
        private _snackbar: MatSnackBar,
        private _userService: UserAccessService,
        public dialog: MatDialog,
        private _daterangeService: DateRangePickerService,
        private _dataSourceService: DataSourceService,
        private changeDetectorRefs: ChangeDetectorRef,
        private notificationService: NotificationService,
        private _announceService: AnnouncementsService
    ) {
        super();
    }

    ngOnInit() {
        this.refresh();
    }

    ngAfterViewInit() {
        // server-side search
        fromEvent(this.filter.nativeElement, 'keyup')
            .pipe(
                debounceTime(500),
                // distinctUntilChanged(),
                tap(() => {
                    this.paginator.pageIndex = 0;
                    this.refresh();
                })
            )
            .subscribe();
        // reset the paginator after sorting
        // this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
        // on sort or paginate events, load a new page
        merge(this.sort.sortChange, this.paginator.page)
            .pipe(
                tap(() => this.refresh())
            )
            .subscribe();

    }



    openSnackBar(message: string, action: string) {
        this._snackbar.open(message, action, {
          duration: 2000,
        });
    }

    refresh() {
        //console.log('currentReport: ', this.currentReport);
        switch (this.currentReport) {
            case this._appConfig.NOTIFICATIONS.NOTIFICATION:
                this.dataSource = new NotificationsDataSource(this.notificationService);
                this.dataSource.loadAnnouncementReport(
                    this.filter.nativeElement.value,
                    this.sort.direction,
                    this.sort.active,
                    this.paginator.pageIndex,
                    this.paginator.pageSize,
                    this._daterangeService.getDateRangeModel(),
                    this.reporttype,
                    this.filterData,
                    this.currentParam);
                this.isUserAccessAnnouncement = true;
                break;
        }

    }

    // Dialog Showing for Insert User Form
    viewContent(data): void {
        console.log("this is datako: " + data);
        if (data.READ_STATUS === '0') {
            this.postReadAction(data);
        }
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '1000px',
            panelClass: 'view-dialog',
            data: { dialogName: 'notification-details', data: data }
        });

        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }

    postReadAction(announceData) {
        this._announceService.postReadAction(announceData).subscribe(data => {
            this._dataSourceService.getTable().refresh();
        });
    }
}
