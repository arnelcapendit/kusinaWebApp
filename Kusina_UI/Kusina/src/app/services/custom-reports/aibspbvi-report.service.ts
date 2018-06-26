import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AppConfig } from '../../globals/app.config';
import { MsgHandlerService } from '../msg-handler.service';
import { AppProfileService } from '../app-profile.service';
import { DateRangePickerService } from '../daterange.service';
import { ProgressBarService } from '../progressbar.service';
import { DateRangeModel } from '../../models/daterange.model';
import { AibspBviData, AibspBviReport } from '../../models/api.model';
import { ArrayObservable } from 'rxjs/observable/ArrayObservable';
import { GlobalFilterService } from '../global-filters/global-filters.service';


@Injectable()
export class AibspBviReportService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    isValid:boolean;

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

    getAibspBviData( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, reportType) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getAibspBvi;
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
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            // Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTER, filter);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);

        return this._http.get<AibspBviData>(URI, {
            params: Params
            }).map(data => {
                let result;
                this.totalData = data.total;
                // console.log('Pages Total Data: ', data.total);
                switch (reportType) {
                    case 'pageCustomInfo':
                        result = data.pageCustomInfo;
                        break;
                    case 'careerTracksBySegmentName':
                        result = data.careerTracksBySegmentName;
                        break;
                    case 'careerLevelDescriptionBySegmentName':
                        result = data.careerLevelDescriptionBySegmentName;
                        break;
                    case 'geographyBySegmentName':
                        result = data.geographyBySegmentName;
                        break;
                    case 'hitsByGeography':
                        result = data.hitsByGeography;
                        break;
                    case 'hitsByAsset':
                        result = data.hitsByAsset;
                        break;
                }
                return result;
        });
    }

    // tslint:disable-next-line:max-line-length
    getAibspBviDataByChild( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, reportType, uniqueParam, filter) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getPagesChild;
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
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTER, filter);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTERMETRICS, filter);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FILTERFOR, uniqueParam);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);

        return this._http.get<AibspBviData>(URI, {
            params: Params
            }).map(data => {
                let result;
                this.totalData = data.total;
                switch (reportType) {
                    case 'pageCustomInfo':
                        result = data.pageCustomInfo;
                        break;
                    case 'careerTracksBySegmentName':
                        result = data.careerTracksBySegmentName;
                        break;
                    case 'careerLevelDescriptionBySegmentName':
                        result = data.careerLevelDescriptionBySegmentName;
                        break;
                    case 'geographyBySegmentName':
                        result = data.geographyBySegmentName;
                        break;
                    case 'hitsByGeography':
                        result = data.hitsByGeography;
                        break;
                    case 'hitsByAsset':
                        result = data.hitsByAsset;
                        break;
                }
                return result;
        });
    }

    isValidAibspBviReportType(currentReport):boolean{
        const aibspArray:any = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB;
        const aibspArrayList = [];
        let isValid=null;
        //Adding key value pairs of aibsp tab names to new array
        for(const key in aibspArray){
            aibspArrayList.push({'name' : key , 'value' : aibspArray[key] })
        }
        //Checking array values if matches to currentReport
        aibspArrayList.forEach(element => {
            if(element.value = currentReport){
                isValid = currentReport;
            }else{
                return isValid;
            }
        });
        return isValid;
    }
}
