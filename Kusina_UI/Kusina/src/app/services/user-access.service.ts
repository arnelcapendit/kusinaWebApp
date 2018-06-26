import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppConfig } from '../globals/app.config';
import {UserModel} from '../models/user.model';
import { HttpClient, HttpHeaders, HttpResponse, HttpInterceptor, HttpErrorResponse, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { MsgHandlerService } from  '../services/msg-handler.service';
import { AppProfileService } from '../services/app-profile.service';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {
    Users,
    UsersList,
    ProjectPreferences,
    ProjectPreferencesList
} from '../models/api.model';
import { FormGroup } from '@angular/forms/src/model';
import { DateRangePickerService } from './daterange.service';
import { ProgressBarService } from './progressbar.service';
import { DateRangeModel } from '../models/daterange.model';
import { GlobalFilterService } from './global-filters/global-filters.service';

@Injectable()
export class UserAccessService {
   private userModel:UserModel;
   private dataResult:string;
   isAdminVal:boolean=false;
   exportURL:string='';
   data0:Array<any>=[];
   users:Users[];
   addResult:any;
   editResult:any;
   removeResult:any;
   status: string;

   dataAllUsers: BehaviorSubject<Users[]> = new BehaviorSubject<Users[]>([]);
   dataProjectPreferences: BehaviorSubject<ProjectPreferences[]> = new BehaviorSubject<ProjectPreferences[]>([]);
    
   selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    isValid:boolean;
    userTeam: string;
    userAirID: string;
    userSiteID: string;

    constructor(
        private _appConfig: AppConfig,
        private _http: HttpClient,
        private _msgHandler: MsgHandlerService,
        private _daterangeService: DateRangePickerService,
        private _appProfileService: AppProfileService,
        private _progressBarService: ProgressBarService,
        private _globalAppFilterService: GlobalFilterService
    ) {    
    }
    init(){

    }

    public setUser(userModel: UserModel){
        this.userModel = userModel;
    }
    public getUser(){
        return this.userModel;
    }

    get dataUsers(): Users[] {
        return this.dataAllUsers.value;
    }

    get dataProjPreferences(): ProjectPreferences[]{
        return this.dataProjectPreferences.value;
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

    getUserAccessData( filteredData = '*', sortOrder, pageNumber = 0, pageSize = 10, dateRange, reportType) {
        this.setDate(dateRange);
        this.setFilter(filteredData);
        const pageIndex = pageNumber * pageSize;

        const URI = this._appConfig.URI.getUsers;
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

        return this._http.get<UsersList>(URI, {
            params: Params
            }).map(data => {
                let result;
                this.totalData = data.total;
                result = data.allusers;
                // //console.log('Pages Total Data: ', data.total); 
                return result;
        });
    }

       //Getting Single User from ELK
       getSingleUser(eidName:string) {
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);

        const _uriOnFilter = this._appConfig.URI.getUser;
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._globalAppFilterService.getUser().getEID();
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._globalAppFilterService.getUser().getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        const eidSearch = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EIDSEARCH + eidName;
        
        const requestUrl =
            _uriOnFilter +
            eid  + '&' +
            nonce + '&' +
            now + '&' +
            eidSearch;

       
        this._http.get(requestUrl).subscribe(data => {
                this.addResult = data
            },
            (error: HttpErrorResponse) => {
                //console.log (error.name + ' ' + error.message);
            });
    }




    //   Getting All Users from ELK
    getAllUser(): void {
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);

        const _uriOnFilter = this._appConfig.URI.getAllUsers;
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._globalAppFilterService.getUser().getEID();
        const airid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
        const id = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._globalAppFilterService.getUser().getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        const exportType = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_CUSTRPTTYPE + this._appConfig.EXPORT_TYPE.VISITOR_LOG;

        const requestUrl =
            _uriOnFilter +
            eid  + '&' +
            nonce + '&' +
            now;

        this.exportURL = 
            this._appConfig.URI.exportCustomReportToCsv+
            eid  + '&' +
            airid + '&' +
            id + '&' +
            nonce + '&' +
            now + '&' +
            exportType;
        this._http.get<UsersList>(requestUrl).subscribe(data => {
            this.dataAllUsers.next(data.allusers);
            // //console.log("This is AllUsersList: ", this.dataAllUsers.value);
            },
            (error: HttpErrorResponse) => {
            //console.log (error.name + ' ' + error.message);
            });
    }


    // Getting all project preferences data
    getProjectPreferences(): void {
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);

        const _uriOnFilter = this._appConfig.URI.getAppPreferences;
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this._globalAppFilterService.getUser().getEID();
        const airid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
        const id = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this._globalAppFilterService.getUser().getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        // const exportType = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_CUSTRPTTYPE + this._appConfig.EXPORT_TYPE.VISITOR_LOG;

        const requestUrl =
            _uriOnFilter +
            eid  + '&' +
            nonce + '&' +
            now;

        this._http.get<ProjectPreferences>(requestUrl).subscribe(data => {
                this.data0.push(data);
                this.dataProjectPreferences.next(this.data0);
            },
            (error: HttpErrorResponse) => {
                //console.log (error.name + ' ' + error.message);
            });
    }


    checkedNull(user) {
        if (user.team === null) {
            this.userTeam = '-';
        } else {
            this.userTeam = user.team;
        }

        if (user.airid === null) {
            this.userAirID = 'All';
        } else {
            this.userAirID = user.airid;
        }

        if (user.site_id === null) {
            this.userSiteID = 'All';
        } else {
            this.userSiteID = user.site_id;
        }
    }

    // Inserting New User Data to ELK DB
    insertUserToELK(user) {
        this.checkedNull(user);

        const URI = this._appConfig.URI.insertUserDetails;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());

            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EIDINSERT, user.eid.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, this.userSiteID.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, this.userAirID.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ACCESS, user.access.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EXPDATE,  new Date(user.expiredDate).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ACCESSSTATUS, user.status.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TEAM, this.userTeam.toString());

            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());

        return this._http.post(URI, Params).map(data => {
                return 'User has been created!';
        });

    }

    // Updating User Data from ELK DB
    editUserToELK(formData, createdDate) {
        this.checkedNull(formData);

        const URI = this._appConfig.URI.editUserDetails;
        let Params = new HttpParams();

            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());

            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EIDINSERT, formData.eid.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, this.userSiteID.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_AIRID, this.userAirID.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ACCESS, formData.access.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EXPDATE,  new Date(formData.expiredDate).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ACCESSSTATUS, formData.status.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TEAM, this.userTeam.toString());

            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date(createdDate).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());

        return this._http.post(URI, Params).map(data => {
            return 'User has been updated!';
        });

    }

    // Deleting User Data from ELK DB
    deleteUserToELK(data) {
        const URI = this._appConfig.URI.deleteUserDetails;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            // tslint:disable-next-line:max-line-length
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EIDDELETE, data.EID);

        return this._http.delete(URI, {
            params: Params
            }).map(res => {
                return 'User has been successfully deleted!';
        });
    }


}
