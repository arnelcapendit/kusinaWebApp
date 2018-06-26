import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpInterceptor, HttpErrorResponse, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AppConfig } from '../globals/app.config';
import { UserModel } from '../models/user.model';
import { MsgHandlerService } from '../services/msg-handler.service';
import { SidenavDisplayNameComponent } from '../materials/sidenav-display-name/sidenav-display-name.component';
import { AppProfileModel } from '../models/app-profile.model';
import { AppProfileService } from '../services/app-profile.service';
import { ChartService } from '../services/chart.service';
import { DateRangePickerService } from '../services/daterange.service';
import {
    MytePageVsCountry,
    MytePageVsCountryList,
    EventMetricsObject,
    EventMetricsList,
    EventMetricsWrapper,
    VisitorLogMetrics,
    VisitorLogList
} from '../models/api.model';
import { DateRangeModel } from '../models/daterange.model';
import { MytePageVsCountryDataSource } from '../datasources/myte-pageVsCountries.datasource';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import { VisitorLogDataSource } from '../datasources/visitorLog.datasource';
import { ProgressBarService } from './progressbar.service';
import { GlobalFilterService } from './global-filters/global-filters.service';

@Injectable()
export class CustomReportService {
    private selectedStartDateEpoc = 0;
    private selectedEndDateEpoc = 0;
    dataSource: MytePageVsCountryDataSource;
    dataChange: BehaviorSubject<MytePageVsCountry[]> = new BehaviorSubject<MytePageVsCountry[]>([]);

    eventActionDataChange: BehaviorSubject<EventMetricsObject[]> = new BehaviorSubject<EventMetricsObject[]>([]);
    visitorLogDataSource: VisitorLogDataSource;
    visitorLogDataChange: BehaviorSubject<VisitorLogMetrics[]> = new BehaviorSubject<VisitorLogMetrics[]>([]);
    dialogData: any;
    exportURL: string = '';
    URL_details: string;
    loadingData: boolean;

    rateSample: number;
    

    constructor(
        private _http: HttpClient,
        private _appConfig: AppConfig,
        private _msgHandler: MsgHandlerService,
        private _appProfileService: AppProfileService,
        private _daterangeService: DateRangePickerService,
        private _progressBarService: ProgressBarService,
        private _globalAppFilterService: GlobalFilterService
    ) {}

    get data(): MytePageVsCountry[] {
        return this.dataChange.value;
    }

    get visitorLogData(): VisitorLogMetrics[]{
        return this.visitorLogDataChange.value;
    }

    getDialogData() {
        return this.dialogData;
    }
 
    
    setDate(dateRange:DateRangeModel){
        if (dateRange.getSelectedPeriodModel() == this._appConfig.DATERANGEPICKER.PERIODS.CUSTOM){
            this.selectedStartDateEpoc  = dateRange.getCustomStartModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getCustomEndModel().getEpoc() + 86399000;
        } else {
            this.selectedStartDateEpoc = dateRange.getDayModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getDayModel().getEpoc() + 86399000;
        }
    }

    getMytePageVsCountry(): void {
         this.setDate(this._daterangeService.getDateRangeModel());
         const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
        //const requestUrl = "http://localhost:8080/kusina/rest/custom_reports/myTe?eid=nolan.rey.e.tagle&airid=2700&pageURL=myscheduling.accenture.com/escheduling/mycv/demographics.aspx&action=none&id=315&gte=1504195200000&lte=1516723199000&nonce=a00108d3-7bb3-4f82-a49a-f9a9dd93aa18&now=1516680407482";
        const _uriOnFilter = this._appConfig.URI.getCustomRptMyTe;
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._globalAppFilterService.getUser().getEID();
        const airid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
        const pageUrl = 'pageURL=*';
        const action = 'action=none';
        const appid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
        const gte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_GTE + this.selectedStartDateEpoc;
        const lte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_LTE + this.selectedEndDateEpoc;
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._globalAppFilterService.getUser().getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();

        const requestUrl =
            _uriOnFilter +
            eid + '&' +
            airid + '&' +
            pageUrl + '&' +
            action + '&' +
            appid + '&' +
            gte + '&' +
            lte + '&' +
            nonce + '&' +
            now;
        this.exportURL =
            this._appConfig.URI.exportMyTeToCsv +
            eid + '&' +
            airid + '&' +
            pageUrl + '&' +
            action + '&' +
            appid + '&' +
            gte + '&' +
            lte + '&' +
            nonce + '&' +
            now;
        //console.log(requestUrl);
        this._http.get<MytePageVsCountryList>(requestUrl).subscribe(data => {
            this.dataChange.next(data.MyTEReports);
          },
          (error: HttpErrorResponse) => {
            //console.log (error.name + ' ' + error.message);
          });
    }

    // event action
    // getEventAction(): void {
    //     this.loadingData = true;
    //     this.setDate(this._daterangeService.getDateRangeModel());
    //     const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
    //    const _uriOnFilter = this._appConfig.URI.getCustomEventAction;
    //    const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._sessionService.getUser().getEID();
    //    const airid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
    //    const id = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
    //    const gte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_GTE + this.selectedStartDateEpoc;
    //    const lte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_LTE + this.selectedEndDateEpoc;
    //    const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._sessionService.getUser().getNonceID();
    //    const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
    //    const exportType = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_CUSTRPTTYPE + this._appConfig.EXPORT_TYPE.EVENT_ACTION;
       
    //    let requestUrl = 
    //         _uriOnFilter + 
    //         eid + "&" + 
    //         airid + "&" + 
    //         id + "&" + 
    //         gte + "&" + 
    //         lte + "&" + 
    //         nonce + "&" + 
    //         now;

    //     this.exportURL = 
    //         this._appConfig.URI.exportCustomReportToCsv +
    //         eid  + '&' +
    //         airid + '&' +
    //         id + '&' +
    //         gte + '&' +
    //         lte + '&' +
    //         nonce + '&' +
    //         now + '&' +
    //         exportType;
       
    //    this._http.get<EventMetricsWrapper>(requestUrl).subscribe(data => {
    //        this.eventActionDataChange.next(data.EventMetrics.resultset);
    //        this.loadingData = false;
    //      },
    //      (error: HttpErrorResponse) => {
    //        console.log (error.name + ' ' + error.message);
    //      });
    //  }

    getVisitorLog(): void {
        this.loadingData = true;
        this.setDate(this._daterangeService.getDateRangeModel());
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
        const _uriOnFilter = this._appConfig.URI.getCustomVisitors;
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._globalAppFilterService.getUser().getEID();
        const airid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
        const id = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
        const gte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_GTE + this.selectedStartDateEpoc;
        const lte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_LTE + this.selectedEndDateEpoc;
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._globalAppFilterService.getUser().getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();

        const exportType = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_CUSTRPTTYPE + this._appConfig.EXPORT_TYPE.VISITOR_LOG;
        const requestUrl =
            _uriOnFilter +
            eid  + '&' +
            nonce + '&' +
            now + '&' +
            airid + '&' +
            id + '&' +
            gte + '&' +
            lte;

        this.exportURL = 
            this._appConfig.URI.exportCustomReportToCsv +
            airid + '&' +
            id + '&' +
            gte + '&' +
            lte + '&' +
            nonce + '&' +
            now + '&' +
            exportType;

        //console.log('Visitor Log: ', requestUrl);
        this._http.get<VisitorLogList>(requestUrl).subscribe(data => {
                this.visitorLogDataChange.next(data.VisitorLogMetrics);
                this.loadingData = false;
            },
            (error: HttpErrorResponse) => {
                //console.log (error.name + ' ' + error.message);
            });
    }
 }

