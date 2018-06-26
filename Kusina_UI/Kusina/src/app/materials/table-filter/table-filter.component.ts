import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, Input, Output, ChangeDetectorRef } from '@angular/core';
import { TableModel } from '../../models/table.model';
import { MatPaginator, MatSort, MatSnackBar, MatDialog } from '@angular/material';
import { UsageReportService } from '../../services/custom-reports/usage-report.service';
import { VisitorReportService } from '../../services/custom-reports/visitor-report.service';
import { MyteReportService } from '../../services/custom-reports/myte-report.service';
import { PageReportService } from '../../services/custom-reports/page-report.service';
import { AppConfig } from '../../globals/app.config';
import { UserAccessService } from '../../services/user-access.service';
import { DateRangePickerService } from '../../services/daterange.service';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { debounceTime, tap } from 'rxjs/operators';
import { merge } from 'rxjs/observable/merge';
import { UsageDataSource } from '../../datasources/usage.datasource';
import { VisitorLogDataSource } from '../../datasources/visitorLog.datasource';
import { VisitorLogsDetailsDataSource } from '../../datasources/visitorlogs-details.datasource';
import { MytePageVsCountryDataSource } from '../../datasources/myte-pageVsCountries.datasource';
import { PagesDataSource } from '../../datasources/pages.datasource';
import { DialogComponent } from '../dialog/dialog.component';
import { DataSourceService } from '../../services/datasource.service';
import { TableFilterModel } from '../../models/table-filter.model';
import { EventEmitter } from 'events';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { AibspBviDataSource } from '../../datasources/aibspBvi.datasource';
import { OverviewDataSource } from '../../datasources/overview.datasource';
import { AibspBviReportService } from '../../services/custom-reports/aibspbvi-report.service';
import { OverviewReportService } from '../../services/custom-reports/overview-report.service';
import { ActionDataSource } from '../../datasources/action.datasource';
import { ActionReportService } from '../../services/custom-reports/action-report.service';
import { UserAccessDataSource } from '../../datasources/user-access.datasource';
import { FeedbacksDataSource } from '../../datasources/feedbacks.datasource';
import { FeedbackService } from '../../services/feedback.service';
import { AnnouncementsDataSource } from '../../datasources/announcement.datasource';
import { AnnouncementsService } from '../../services/announcement.service';
import { ProfileDataSource } from '../../datasources/profile.datasource';
import { ProfileService } from '../../services/profile.service';
import { ItfReportService } from '../../services/custom-reports/itf-report.service';
import { ITFDataSource } from '../../datasources/itf.datasource';


@Component({
  selector: 'app-table-filter',
  templateUrl: './table-filter.component.html',
  styleUrls: ['./table-filter.component.css']
})
export class TableFilterComponent extends TableModel implements OnInit, AfterViewInit  {

    displayedColumns = [];
    // dataSource1: MytePageVsCountryDataSource | null;
    dataSource: any;
    currentReport: string | null;
    exportLink = '';
    isUserAccess = false;
    isUserAccessAnnouncement = false;
    isUserAccessProfile = false;
    isExportTable = true;
    currentParam: string | null;
    searchPlaceholder: string;
    reporttype: string;
    filterData: string;
    filterMetrics: string;
    dateRange: string;

    // User Access Params
    newUser: any= {};
    add = 'add';
    edit = 'edit';
    remove = 'remove';
    getEditData: any;

    @Output() dateR = new EventEmitter();
    @Output() columnsChange = new EventEmitter();
    @Output() reportChange = new EventEmitter();

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;
    @ViewChild('filter') filter: ElementRef;

    @Input()
    setDateRange(date1) {
        // console.log(date1);
        this.dateRange = date1;
        this.dateR.emit(this.dateRange);
    }

    @Input()
    get columns(){
        return this.displayedColumns;
    }

    set columns(val) {
        // console.log(val);
        this.displayedColumns = val;
        // this.columnsChange.emit(this.displayedColumns);
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

    @Input()
    set filteredMetrics(val) {
        this.filterMetrics = val;
    }

    constructor(
        private _customReportService: UsageReportService,
        private _visitorLogsReportService: VisitorReportService,
        private _myteReportService: MyteReportService,
        private _pageReportService: PageReportService,
        private _appConfig: AppConfig,
        private _snackbar: MatSnackBar,
        private _userService: UserAccessService,
        public dialog: MatDialog,
        private _daterangeService: DateRangePickerService,
        private _dataSourceService: DataSourceService,
        private changeDetectorRefs: ChangeDetectorRef,
        private _aibspbviReportService: AibspBviReportService,
        private _overviewReportService: OverviewReportService,
        private _actionReportService: ActionReportService,
        private _feedbackService: FeedbackService,
        private _announcementsService: AnnouncementsService,
        private _profileService: ProfileService,
        private _expressionChangeService: ChangeDetectorRef,
        private _itfService: ItfReportService
    ) {
        super();
    }

    ngOnInit() {
        // this._expressionChangeService.detectChanges();
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
        // console.log('currentReport: ', this.currentReport);
        switch (this.currentReport) {
            case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_USER:
                case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_LEVEL:
                    case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_CAREERTRACKS:
                        case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_GEOGRAPHY:
                            case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_LEVEL:
                                case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_CAREERTRACKS:
                                    case this._appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_GEOGRAPHY:
                                            this.dataSource = new UsageDataSource(this._customReportService);
                                            this.dataSource.loadUsageReport(
                                                this.filter.nativeElement.value,
                                                this.sort.direction,
                                                this.sort.active,
                                                this.paginator.pageIndex,
                                                this.paginator.pageSize,
                                                this._daterangeService.getDateRangeModel(),
                                                this.reporttype,
                                                this.filterData,
                                                this.currentParam,
                                                this.filterMetrics);
                                                if (this.currentParam !== undefined) {
                                                    this.isExportTable = false;
                                                }
                                                break;
            case this._appConfig.CUSTOM_REPORTS.REPORTS.VISITORS:
                this.dataSource = new VisitorLogDataSource(this._visitorLogsReportService);
                // tslint:disable-next-line:max-line-length
                this.dataSource.loadVisitorLogMetrics( this.filter.nativeElement.value, this.sort.direction, this.sort.active, this.paginator.pageIndex, this.paginator.pageSize, this._daterangeService.getDateRangeModel());
                break;
            case this._appConfig.CUSTOM_REPORTS.REPORTS.VISITORSDETAILS:
                this.dataSource = new VisitorLogsDetailsDataSource(this._visitorLogsReportService);
                // tslint:disable-next-line:max-line-length
                this.dataSource.loadVisitorLogMetricsDetails( this.filter.nativeElement.value, this.sort.direction, this.sort.active, this.paginator.pageIndex, this.paginator.pageSize, this._daterangeService.getDateRangeModel(), this.currentParam);
                this.isExportTable = false;
                break;
            case this._appConfig.CUSTOM_REPORTS.REPORTS.PAGE_VS_COUNTRIES:
                this.dataSource = new MytePageVsCountryDataSource(this._myteReportService);
                // tslint:disable-next-line:max-line-length
                this.dataSource.loadMytePageVsCountry( this.filter.nativeElement.value, this.sort.direction, this.sort.active, this.paginator.pageIndex, this.paginator.pageSize, this._daterangeService.getDateRangeModel());
                break;
            case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_USER:
                case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_LEVEL:
                    case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_CAREERTRACKS:
                        case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.PAGES_VS_GEOGRAPHY:
                            case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.DETAILS_PAGES_VS_USER:
                                case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.DETAILS_PAGES_VS_LEVEL:
                                    case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.DETAILS_PAGES_VS_GEOGRAPHY:
                                        case this._appConfig.CUSTOM_REPORTS.PAGES_TAB.DETAILS_PAGES_VS_CAREERTRACKS:
                                                this.dataSource = new PagesDataSource(this._pageReportService);
                                                this.dataSource.loadPagesReport(
                                                    this.filter.nativeElement.value,
                                                    this.sort.direction,
                                                    this.sort.active,
                                                    this.paginator.pageIndex,
                                                    this.paginator.pageSize,
                                                    this._daterangeService.getDateRangeModel(),
                                                    this.reporttype,
                                                    this.filterData,
                                                    this.currentParam);
                                                if (this.currentParam !== undefined) {
                                                    this.isExportTable = false;
                                                }
                                                break;
            case this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.CAREERLEVEL_BY_SEGMENT_NAME:
                case this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.CAREERTRACKS_BY_SEGMENTNAME:
                    case this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.GEOGRAPHY_BY_SEGMENT_NAME:
                        case this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.HITS_BY_ASSET:
                            case this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.HITS_BY_GEOGRAPHY:
                                case this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.PAGES_CUSTOM_INFO:
                                                this.dataSource = new AibspBviDataSource(this._aibspbviReportService);
                                                this.dataSource.loadAibspBviReport(
                                                    this.filter.nativeElement.value,
                                                    this.sort.direction,
                                                    this.sort.active,
                                                    this.paginator.pageIndex,
                                                    this.paginator.pageSize,
                                                    this._daterangeService.getDateRangeModel(),
                                                    this.reporttype,
                                                    this.filterData,
                                                    this.currentParam);
                                                    console.log("Current Report Type: "+ this.reporttype);
                                                if (this.currentParam !== undefined) {
                                                    this.isExportTable = false;
                                                }
                                                break;
            case this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.USER_METRICS:
                case this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.REFERRERS_METRICS:
                    case this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.PAGE_METRICS:
                        case this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.DOWNLOAD_METRICS:
                            case this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.DETAILS_DOWNLOAD_METRICS:
                                case this._appConfig.CUSTOM_REPORTS.OVERVIEW_TAB.DETAILS_REFERRERS_METRICS:
                                                this.dataSource = new OverviewDataSource(this._overviewReportService);
                                                this.dataSource.loadOverviewReport(
                                                    this.filter.nativeElement.value,
                                                    this.sort.direction,
                                                    this.sort.active,
                                                    this.paginator.pageIndex,
                                                    this.paginator.pageSize,
                                                    this._daterangeService.getDateRangeModel(),
                                                    this.reporttype,
                                                    this.filterData,
                                                    this.currentParam);
                                                if (this.currentParam !== undefined) {
                                                    this.isExportTable = false;
                                                }
                                                break;
            case this._appConfig.CUSTOM_REPORTS.EVENTS_TAB.EVENT_CATEGORIES:
                case this._appConfig.CUSTOM_REPORTS.EVENTS_TAB.EVENT_NAMES:
                    case this._appConfig.CUSTOM_REPORTS.EVENTS_TAB.EVENT_ACTIONS:
                                                this.dataSource = new ActionDataSource(this._actionReportService);
                                                this.dataSource.loadActionReport(
                                                    this.filter.nativeElement.value,
                                                    this.sort.direction,
                                                    this.sort.active,
                                                    this.paginator.pageIndex,
                                                    this.paginator.pageSize,
                                                    this._daterangeService.getDateRangeModel(),
                                                    this.reporttype,
                                                    this.filterData,
                                                    this.currentParam);
                                                if (this.currentParam !== undefined) {
                                                    this.isExportTable = false;
                                                }
                                                break;

            case this._appConfig.SETTINGS.SETTINGS_TAB.USER_ACCESS:
                this.dataSource = new UserAccessDataSource(this._userService);
                // tslint:disable-next-line:max-line-length
                this.dataSource.loadUserAccessReport(
                    this.filter.nativeElement.value,
                    this.sort.direction,
                    this.sort.active,
                    this.paginator.pageIndex,
                    this.paginator.pageSize,
                    this._daterangeService.getDateRangeModel(),
                    this.reporttype,
                    this.filterData,
                    this.currentParam);
                this.isUserAccess = true;
                break;

            case this._appConfig.FEEDBACKS.FEEDBACKS:
                this.dataSource = new FeedbacksDataSource(this._feedbackService);
                // tslint:disable-next-line:max-line-length
                this.dataSource.loadFeedbacksReport(
                    this.filter.nativeElement.value,
                    this.sort.direction,
                    this.sort.active,
                    this.paginator.pageIndex,
                    this.paginator.pageSize,
                    this._daterangeService.getDateRangeModel(),
                    this.reporttype,
                    this.filterData,
                    this.currentParam);
                // this.isUserAccess = true;
                break;
            case this._appConfig.SETTINGS.SETTINGS_TAB.ANNOUNCEMENTS:
                this.dataSource = new AnnouncementsDataSource(this._announcementsService);
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
                this.isExportTable = false;
                break;
            case this._appConfig.SETTINGS.SETTINGS_TAB.PROFILE:
                this.dataSource = new ProfileDataSource(this._profileService);
                this.dataSource.loadProfileReport(
                    this.filter.nativeElement.value,
                    this.sort.direction,
                    this.sort.active,
                    this.paginator.pageIndex,
                    this.paginator.pageSize,
                    this._daterangeService.getDateRangeModel(),
                    this.reporttype,
                    this.filterData,
                    this.currentParam);
                this.isUserAccessProfile = true;
                break;

            case this._appConfig.CUSTOM_REPORTS.ITF_TAB.FULFILLMENT_CONNECT_USAGE:
                case this._appConfig.CUSTOM_REPORTS.ITF_TAB.NOTIFICATION_COUNT:
                    case this._appConfig.CUSTOM_REPORTS.ITF_TAB.COMMENTS_CLICK_COUNT:
                    this.dataSource = new ITFDataSource(this._itfService);
                    this.dataSource.loadITFReport(
                        this.filter.nativeElement.value,
                        this.sort.direction,
                        this.sort.active,
                        this.paginator.pageIndex,
                        this.paginator.pageSize,
                        this._daterangeService.getDateRangeModel(),
                        this.reporttype,
                        this.filterData,
                        this.currentParam);
                    break;
        }

    }

    viewDialog(url): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '1024px',
            panelClass: 'view-dialog',
            data: { url: url, dialogName: this.currentReport }
        });

        dialogRef.afterClosed().subscribe(result => {
            // Return Data
        });
    }

    // Dialog Showing for Insert User Form
    showInsertUserForm(): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '600px',
            // height: '600px',
            data: { dialogName: this.add }
        });

        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }

    // Dialog Showing for Delete User Form
    showRemoveUserConfirmation(eid): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '600px',
            data: { dialogName: this.remove, userDelete: eid}
        });
        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }

    showEditUserForm(user): void {
        const userArry = {
            'EID': user.EID,
            'ID': user.ID,
            'AIRID': user.AIRID,
            'ACCESS': user.ACCESS,
            'EXPIRY_DATE': new Date(user.EXPIRY_DATE).toISOString(),
            'STATUS': user.STATUS,
            'CREATED_DATE': new Date(user.CREATED_DATE).toISOString(),
            'LAST_UPDATED_DATE': new Date(user.LAST_UPDATED_DATE).toISOString(),
            'LAST_UPDATED_BY': user.LAST_UPDATED_BY
        };
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '600px',
            data: { dialogName: this.edit, userdata: userArry }
        });
        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }

    // Dialog Showing for Announcement Form
    announcementForm(action, data): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '600px',
            panelClass: 'view-dialog',
            data: { dialogName: action, data: data }
        });

        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }

    // Dialog Showing for Announcement Form
    profileForm(action, data): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '600px',
            panelClass: 'view-dialog',
            data: { dialogName: action, data: data }
        });

        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }

    userForm(action, data): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '600px',
            panelClass: 'view-dialog',
            data: { dialogName: action, data: data }
        });

        dialogRef.afterClosed().subscribe(result => {
            this._dataSourceService.getTable().refresh();
        });
    }
}
