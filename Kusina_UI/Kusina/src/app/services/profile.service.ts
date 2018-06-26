import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { AppConfig } from '../globals/app.config';
import { AppProfileService } from './app-profile.service';
import { DateRangeModel } from '../models/daterange.model';
import { ProfileData} from '../models/api.model';
import { GlobalFilterService } from './global-filters/global-filters.service';
import { AnnouncementIconComponent } from '../materials/announcement/announcement-icon/announcement-icon.component';
import { UserModel } from '../models/user.model';
// tslint:disable-next-line:import-blacklist
import { Observable } from 'rxjs';

@Injectable()
export class ProfileService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    isValid: boolean;

    constructor(
        private _http: HttpClient,
        private _appConfig: AppConfig,
        private _appProfileService: AppProfileService,
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

    // Getting all records for announcements
    getProfileData( filteredData = '*', pageNumber = 0, pageSize = 10, reportType) {
        // this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getAppProfile;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);

        return this._http.get<ProfileData>(URI, {
            params: Params
            }).map(data => {
                // console.log('Data: ', data);
                let result;
                this.totalData = data.total;
                result = data.profile;
                return result;
        });
    }

    addProfile(formData) {
        console.log(formData);
        const URI = this._appConfig.URI.addAppProfile;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, formData.airid.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SITE_ID, formData.site_id.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_APP_NAME, formData.app_name.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());

        return this._http.post(URI, Params).map(data => {
                return 'App Profile has been created!';
        });
    }

    editProfile(formData, id, created_date) {
        const URI = this._appConfig.URI.editAppProfile;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, id);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, formData.airid.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SITE_ID, formData.site_id.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_APP_NAME, formData.app_name.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());

        return this._http.post(URI, Params).map(data => {
                return 'App Profile has been updated!';
        });
    }

    deleteProfile(data) {
        const URI = this._appConfig.URI.deleteAppProfile;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, data.ID);

        return this._http.delete<ProfileData>(URI, {
            params: Params
            }).map(res => {
                return 'App Profile has been successfully deleted!';
        });
    }

    getAllAirid() {

        const URI = this._appConfig.URI.getAllId;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());

        return this._http.get<ProfileData>(URI, {
            params: Params
            }).map(data => {
                // console.log('Data: ', data);
                let result;
                this.totalData = data.total;
                result = data.profile;
                return result;
        });
    }

    getSiteId(search) {

        const URI = this._appConfig.URI.getSiteId;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, search);

        return this._http.get<ProfileData>(URI, {
            params: Params
            }).map(data => {
                // console.log('Data: ', data);
                let result;
                result = data.site_id;
                return result;
        });
    }
}
