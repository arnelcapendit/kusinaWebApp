import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { AppConfig } from '../globals/app.config';
import { AppProfileService } from './app-profile.service';
import { SessionDataService } from './session.service';
import { DateRangeModel } from '../models/daterange.model';
import { AnnouncementsData, NotifCount } from '../models/api.model';
import { GlobalFilterService } from './global-filters/global-filters.service';

@Injectable()
export class NotificationService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    isValid: boolean;

    constructor(
        private _http: HttpClient,
        private _appConfig: AppConfig,
        private _appProfileService: AppProfileService,
        private _globalFilterService: GlobalFilterService
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

    // Getting all records for announcements
    getAnnouncementsData( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, reportType) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getAllAnnounceLive;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TIMEZONE, encodeURIComponent(this._globalFilterService.getDateRangeService().getTimezone()));

        return this._http.get<AnnouncementsData>(URI, {
            params: Params
            }).map(data => {
                //console.log('Data: ', data);
                let result;
                this.totalData = data.total;
                result = data.announcements;
                return result;
        });
    }
}
