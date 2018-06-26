import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { AppConfig } from '../globals/app.config';
import { AppProfileService } from './app-profile.service';
import { DateRangeModel } from '../models/daterange.model';
import { FeedbacksData } from '../models/api.model';
import { GlobalFilterService } from './global-filters/global-filters.service';

@Injectable()
export class FeedbackService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    isValid:boolean;

    constructor(
        private _http: HttpClient,
        private _appConfig: AppConfig,
        private _appProfileService: AppProfileService,
        private _globalAppFilterService: GlobalFilterService
    ) { }

    addFeedback(formData, rateValue = 0) {

        const URI = this._appConfig.URI.addFeedbacks;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, this._appProfileService.getSelectedApp().getAirId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, this._appProfileService.getSelectedApp().getId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_RATING_MODULE, formData.module.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_RATING_SCORE, rateValue.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_RATING_COMMENTS, formData.comment.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_STATUS, 'New');
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());

        return this._http.post(URI, Params).map(data => {
                return 'Thank you for your feedback.';
        });
    }

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

    // Getting all records for feedbacks
    getFeedbacksData( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, reportType) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getFeedbacks;
        let Params = new HttpParams();
            // Params = this.stcParams;
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            // tslint:disable-next-line:max-line-length
           // Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, this._appProfileService.getSelectedApp().getAirId());
           // Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, this._appProfileService.getSelectedApp().getId());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
           // Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_GTE, this.selectedStartDateEpoc.toString());
           // Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_LTE, this.selectedEndDateEpoc.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            // Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTER, filter);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);

        return this._http.get<FeedbacksData>(URI, {
            params: Params
            }).map(data => {
                let result;
                this.totalData = data.total;
                result = data.feedbacks;
                // console.log('Pages Total Data: ', data.total); 
                return result;
        });
    }
}
