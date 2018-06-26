import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { AppConfig } from '../globals/app.config';
import { AppProfileService } from './app-profile.service';
import { DateRangeModel } from '../models/daterange.model';
import { AnnouncementsData, NotifCount, AnnouncementsBeforeDueDateData, AnnouncementsLiveData, HistoryId } from '../models/api.model';
import { GlobalFilterService } from './global-filters/global-filters.service';
import { AnnouncementIconComponent } from '../materials/announcement/announcement-icon/announcement-icon.component';
import { UserModel } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable()
export class AnnouncementsService {

    selectedStartDateEpoc = 0;
    selectedEndDateEpoc = 0;
    keywords;
    totalData;
    isValid: boolean;
    notifCount:number;
    historyId: string;
    user_timezone: string;

    private component:AnnouncementIconComponent;

    constructor(
        private _http: HttpClient,
        private _appConfig: AppConfig,
        private _appProfileService: AppProfileService,
        private _globalAppFilterService: GlobalFilterService
    ) { }

    initComponent(component:AnnouncementIconComponent){
        console.log("this is announce init");
        this.component = component;
    }
    getComponent(){
        return this.component;
    }
    setCount(count:number){
        this.component.notificationCount=count;
    }

    setHistoryId(id: string){
        this.historyId = id;
    }

    getHistoryId(){
        return this.historyId;
    }

    setDate(dateRange: DateRangeModel) {
        if (dateRange.getSelectedPeriodModel() === this._appConfig.DATERANGEPICKER.PERIODS.CUSTOM) {
            this.selectedStartDateEpoc  = dateRange.getCustomStartModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getCustomEndModel().getEpoc() + 86399000;
        } else {
            this.selectedStartDateEpoc = dateRange.getDayModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getDayModel().getEpoc() + 86399000;
        }
        this.user_timezone = dateRange.getTimezone();
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

        const URI = this._appConfig.URI.getAnnouncements;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, reportType);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, pageIndex.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, pageSize.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, this.keywords);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TIMEZONE, encodeURIComponent(this.user_timezone));

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

    addAnnouncement(formData) {
        const URI = this._appConfig.URI.addAnnouncements;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TYPE, formData.type.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_DUE_DATE, new Date(formData.dueDate).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TITLE, formData.title.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CONTENT, formData.content.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_STATUS, formData.status.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());
            
            console.log("ADD ANNOUNCEMENTS:",URI,Params);
            
        return this._http.post(URI, Params).map(data => {
                return 'Announcement has been created!';
        });
    }

    editAnnouncement(formData, id, created_date) {
        const URI = this._appConfig.URI.editAnnouncements;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, id);
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TYPE, formData.type.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_DUE_DATE, new Date(formData.dueDate).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TITLE, formData.title.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CONTENT, formData.content.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_STATUS, formData.status.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CREATEDDATE, new Date(created_date).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDDATE, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_UPDATEDBY, this._globalAppFilterService.getUser().getEID());

        return this._http.post(URI, Params).map(data => {
                return 'Announcement has been updated!';
        });
    }

    deleteAnnouncement(id) {
        const URI = this._appConfig.URI.deleteAnnouncements;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, id);

        return this._http.delete<AnnouncementsData>(URI, {
            params: Params
            }).map(data => {
                return 'Announcement has been successfully deleted!';
        });
    }

    getNotifCount(user:UserModel):Observable<number> {
        // console.log(data);
   
        const URI = this._appConfig.URI.notifications;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, user.getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, user.getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TIMEZONE, encodeURIComponent(this._globalAppFilterService.getDateRangeService().getTimezone()));
        //console.log(URI+Params);
        return this._http.get<NotifCount>(URI, {params: Params}).map(
            res => {
                this.setCount(res.total);
                //console.log("ANNOUNCEMENT COUNT",res);
                return res.total;
        });
    }

    getAnnouncementBeforeDueDate() {
        const URI = this._appConfig.URI.getAnnouncementsBeforeDueDate;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TIMEZONE, encodeURIComponent(this._globalAppFilterService.getDateRangeService().getTimezone()));

        return this._http.get<AnnouncementsBeforeDueDateData>(URI, {
            params: Params
            }).map(data => {
                //console.log('Data: ', data);
                let result;
                this.totalData = data.total;
                result = data.announcements;
                return result;
        });
    }

    getAnnouncementAllLive() {
        const URI = this._appConfig.URI.getAllAnnounceLive;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_CUSTRPTTYPE, 'announcelive');
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_FROM, '0');
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SIZE, '5');
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_SEARCH, '*');
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TIMEZONE, encodeURIComponent(this._globalAppFilterService.getDateRangeService().getTimezone()));

        return this._http.get<AnnouncementsLiveData>(URI, {
            params: Params
            }).map(data => {
                //console.log('Data: ', data);
                let result;
                this.totalData = data.total;
                result = data.announcements;
                return result;
        });
    }

    postReadAction(formData) {
        const URI = this._appConfig.URI.postReadAction;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TYPE, formData.TYPE.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_DOCID, formData.ID.toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_DUE_DATE, new Date(formData.DUE_DATE).getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ACTION_TYPE, 'read');
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_TIMEZONE, encodeURIComponent(this._globalAppFilterService.getDateRangeService().getTimezone()));
            
        return this._http.post(URI, Params).map(data => {
                return 'Post Read action has been created!';
        });
    }

    getHistoryIdData(id){
        const URI = this._appConfig.URI.getHistoryId;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, id);

        return this._http.get<HistoryId>(URI, {
            params: Params
            }).map(data => {
                console.log('History Id: ', data.id);
                this.setHistoryId(data.id);
                return data.id;
        });
    }

    deleteHistoryId(id){
        const URI = this._appConfig.URI.deleteHistory;
        let Params = new HttpParams();
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_EID, this._globalAppFilterService.getUser().getEID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NOW, new Date().getTime().toString());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_NONCE, this._globalAppFilterService.getUser().getNonceID());
            Params = Params.append( this._appConfig.HTTP_PARAMS.PARAMETERS.PARAM_ID, id);

        return this._http.delete<HistoryId>(URI, {
            params: Params
            }).map(data => {
                console.log("History delete status: "+ data.status);
                return 'History ID deleted.';
        });
    }
}
