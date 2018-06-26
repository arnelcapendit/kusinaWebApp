import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { AppConfig } from '../globals/app.config';
import { MsgHandlerService } from './msg-handler.service';
import { AppProfileService } from './app-profile.service';
import { DateRangePickerService } from './daterange.service';
import { ProgressBarService } from './progressbar.service';
import { DateRangeModel } from '../models/daterange.model';
import { GlobalFilterService } from './global-filters/global-filters.service';

@Injectable()
export class ExportService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    stcParams = new HttpParams();
    DynamicParams = new HttpParams();
    exportURL: string;

    // dataUsageByUser: BehaviorSubject<UsageByUser[]> = new BehaviorSubject<UsageByUser[]>([]);

    constructor(
        private _http: HttpClient,
        private _appConfig: AppConfig,
        private _msgHandler: MsgHandlerService,
        private _appProfileService: AppProfileService,
        private _daterangeService: DateRangePickerService,
        private _progressBarService: ProgressBarService,
        private _globalAppFilterService: GlobalFilterService
    ) { }

    setDate(dateRange: DateRangeModel) {
        if (dateRange.getSelectedPeriodModel() === this._appConfig.DATERANGEPICKER.PERIODS.CUSTOM) {
            this.selectedStartDateEpoc  = dateRange.getCustomStartModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getCustomEndModel().getEpoc() + 86399000;
        } else {
            this.selectedStartDateEpoc = dateRange.getDayModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getDayModel().getEpoc() + 86399000;
        }
    }

    setFilter(filteredData) {
        if (filteredData === '') {
            this.keywords = '*';
        } else {
            this.keywords = filteredData;
        }
    }

    getExportData( reportType, filterData) {

        this.setDate(this._daterangeService.getDateRangeModel());
        const URI = this._appConfig.URI.exportToCsv;
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._globalAppFilterService.getUser().getEID();
        const airid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
        const id = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
        const gte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_GTE + this.selectedStartDateEpoc;
        const lte = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_LTE + this.selectedEndDateEpoc;
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._globalAppFilterService.getUser().getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        const report_Type =  this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_CUSTRPTTYPE + reportType;
        const filter = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_FILTER + filterData;
        const search = 'from=10&size=10&search=*';

         this.exportURL =
            URI + eid  + '&' + airid + '&' + id + '&' + gte + '&' + lte + '&' + nonce + '&' + now + '&' +
            report_Type + '&' +
            filter + '&' +
            search;
    }
}

