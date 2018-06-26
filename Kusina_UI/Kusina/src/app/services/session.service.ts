import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpInterceptor, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AppConfig } from '../globals/app.config';
import { UserModel } from '../models/user.model';
import { MsgHandlerService } from  '../services/msg-handler.service';
import { SidenavDisplayNameComponent } from '../materials/sidenav-display-name/sidenav-display-name.component';
import { AppProfileModel } from '../models/app-profile.model';
import { AppProfileService } from  '../services/app-profile.service';
import { ChartService } from  '../services/chart.service';
import { DateRangePickerService } from '../services/daterange.service';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import { Users } from '../models/api.model';
import { AnnouncementsService } from './announcement.service';


@Injectable()
export class SessionDataService {

    //private EID_HEADERKEY:string = "enterpriseid";
    private userModel:UserModel;
    private appProfileList:Array<AppProfileModel>=[];
    private displayNameContainer:SidenavDisplayNameComponent;
    isAdmin:boolean;
    NotifCount:number;

    constructor( 
        private _http: HttpClient , 
        private _appConfig: AppConfig , 
        private _msgHandler: MsgHandlerService, 
        private _appProfileService: AppProfileService, 
        private _chartService:ChartService, 
        private _daterangeService:DateRangePickerService, 
        private _routerService:Router, 
        private _userService:UserService) {}

    public init(displayNameContainer:SidenavDisplayNameComponent){
        this.displayNameContainer = displayNameContainer;
    }

    private showDisplayName(name:string){
        this.displayNameContainer.username =name;
    }
    public setUser(user:UserModel){
        this._userService.init(user);
        this.userModel=user;
    }
    public getUser(){
        return this.userModel;
    }

    saveToEsSessionDetails(user:UserModel){
        const params = 
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID+user.getEID()+"&"+
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE+user.getNonceID()+"&"+
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AUTHTIME+user.getAuthTime()+"&"+
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EXP+user.getExpTime();
    
        const url = this._appConfig.URI.saveUserSessionDetails+params; 
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);

        return this._http.get(url,{headers})
        .catch(
            (err) => {
                return Observable.throw(err)
             }
        ).subscribe(
            data => {
                this.setUser(user);
                this.showDisplayName(user.getDisplayName());
                this.setAppProfileList(user);
            },
            err => {
                //console.log(err.message);
                this._msgHandler.handleComponentError(err.message);
            }
        );
     }

    setAppProfileList(user: UserModel) {
        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + user.getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        const params = "&" + nonce + "&" + now;
        const inputEid = user.getEID();
        const url = this._appConfig.URI.getAllDataByEid + inputEid + params;
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
        

        // console.log('setAppProfileList URL: ', url);
        this._http.get( url,{headers})
        .catch(
            (err) => {
                return Observable.throw(err)
             }
        )
        .subscribe(
                data => {
                    
                    for (const key in data.AppNameList) {
                        if (data.AppNameList.hasOwnProperty(key)) {
                            this.appProfileList[key]  = new AppProfileModel(data.AppNameList[key].appName,data.AppNameList[key].airId,data.AppNameList[key].id);
                        }
                    }
                    this._appProfileService.initModel(this.appProfileList); 
                    this._appProfileService.getComponent().setOptions(this._appProfileService.getAppProfileList());
                    if( this._appProfileService.getComponent().hasNoSelectedApp() ){
                        this._appProfileService.getComponent().setSelectedApp(this.appProfileList[0]);
                        //console.log('id', this._appProfileService.getComponent().setSelectedApp(this.appProfileList[0]));
                    }

                    if(this._routerService.url == this._appConfig.WEB_PAGES.DASHBOARD_PAGE){
                        this._chartService.setCharts(this.getUser(),this._daterangeService.getDateRangeModel(),this._appProfileService.getSelectedApp());
                        this._chartService.refreshCharts();
                    }
                    let adminValue = data.UserDetails.user_access;
                    if(adminValue == "Super Administrator"){
                        this.isAdmin = true;
                    }else{
                        this.isAdmin = false;
                    }
                    this.NotifCount = data.Notifications;
                },
                err => {
                    this._msgHandler.handleComponentError(err.message);
                }
        );
    }

 }

