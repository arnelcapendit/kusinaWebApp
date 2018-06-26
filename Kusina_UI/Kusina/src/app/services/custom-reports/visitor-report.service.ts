import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { AppConfig } from '../../globals/app.config';
import { MsgHandlerService } from '../msg-handler.service';
import { AppProfileService } from '../app-profile.service';
import { DateRangePickerService } from '../daterange.service';
import { ProgressBarService } from '../progressbar.service';
import { DateRangeModel } from '../../models/daterange.model';
import { UsageData, VisitorLogList } from '../../models/api.model';
import { GlobalFilterService } from '../global-filters/global-filters.service';

@Injectable()
export class VisitorReportService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    stcParams = new HttpParams();
    DynamicParams = new HttpParams();

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

    getUsageData( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, reportType) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getCustomVisitors3;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, this._appProfileService.getSelectedApp().getAirId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, this._appProfileService.getSelectedApp().getId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_GTE, this.selectedStartDateEpoc.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_LTE, this.selectedEndDateEpoc.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTER, reportType);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);

            return this._http.get<VisitorLogList>(URI, {
                params: Params
                }).map(data => {
                    this.totalData = data.total;
                    return data.VisitorLogMetrics;
            });
    }

    getUsageDataByChild( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, uniqueParam) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getCustomVisitorsChild;
        let Params = new HttpParams();
            // Params = this.stcParams;
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, this._appProfileService.getSelectedApp().getAirId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, this._appProfileService.getSelectedApp().getId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_GTE, this.selectedStartDateEpoc.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_LTE, this.selectedEndDateEpoc.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTERFOR, uniqueParam);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);

        return this._http.get<VisitorLogList>(URI, {
            params: Params
            }).map(data => {
                this.totalData = data.total;
                return data.VisitorLogMetrics;
        });
    }
}

