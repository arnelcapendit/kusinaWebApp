import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { UserModel } from '../../models/user.model';
import { AppConfig } from '../../globals/app.config';
import { UserService } from '../user.service';
import { SidenavDisplayNameComponent } from '../../materials/sidenav-display-name/sidenav-display-name.component';
import { AppProfileModel, AppProfileList } from '../../models/app-profile.model';
import { AppProfileService } from '../app-profile.service';
import { Data } from '@angular/router';
import { DateRangePickerService } from '../daterange.service';
import { AnnouncementsService } from '../announcement.service';
import { Adal5Service } from 'adal-angular5';
 
@Injectable()
export class GlobalFilterService {
    //public token: string;
    private user: UserModel;
    private displayNameContainer:SidenavDisplayNameComponent;
    private appProfileList:Array<AppProfileModel>=[];
    private userAccess:string;

    
    constructor(
        private _http: HttpClient, 
        private _appConfig: AppConfig,
        private _appProfileService: AppProfileService,
        private _daterangeService: DateRangePickerService,
        private _adalService: Adal5Service
    ) {}
    
    getDateRangeService(){
        return this._daterangeService.getDateRangeModel();
    }
    getSelectedApp(){
        return this._appProfileService.getSelectedApp();
    }
    refreshFilters():Observable<boolean>{
        var profileList = this.setAppProfileList(this.getUser()).map((res)=>{
          
             return profileList = res;
        });
        return profileList;
    }

    getUser(){
        return this.user = new UserModel( 
            this._adalService.userInfo.profile.samaccount_name,
            this._adalService.userInfo.profile.auth_time,
            this._adalService.userInfo.profile.exp,
            this._adalService.userInfo.profile.nonce,
            this._adalService.userInfo.profile.display_name
        );
    }
    isSuperAdmin():boolean{
        if(this.userAccess==='Super Administrator'){
            return true;
        }
        return false;
    }
    isProjectAdmin():boolean{
        if(this.userAccess==='Project Administrator'){
            return true;
        }
        return false;
    }
    isOperationsAdmin():boolean{
        if(this.userAccess==='Operations Administrator'){
            return true;
        }
        return false;
    }

    public init(displayNameContainer:SidenavDisplayNameComponent){
        this.displayNameContainer = displayNameContainer;
    }
    private showDisplayName(name:string){
        ////console.log("USER NAME: " +name);
        this.displayNameContainer.username =name;
    }

    setAppProfileList(user: UserModel):Observable<boolean> {

        const nonce = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + user.getNonceID();
        const now = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        const params = "&" + nonce + "&" + now;
        const inputEid = user.getEID();
        const url = this._appConfig.URI.getAllDataByEid + inputEid + params;
        var headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
        //headers.set("Access-Control-Allow-Origin",'http://localhost:4200');
        //console.log('setAppProfileList URL hala: ', url);
        
        var test = this._http.get( url, {headers}).map((result:AppProfileList)=>{
            //Handle users owned profiles
            for (const key in result.AppNameList) {
                if (result.AppNameList.hasOwnProperty(key)) {
                    this.appProfileList[key]  = Object.setPrototypeOf(result.AppNameList[key],AppProfileModel.prototype)
                }
            }
            this._appProfileService.initModel(this.appProfileList);
            this._appProfileService.getComponent().setOptions(this._appProfileService.getAppProfileList());
            if(!sessionStorage.getItem("selectedProfile")){
                this._appProfileService.getComponent().setSelectedApp(result.AppNameList[0]);
                this._appProfileService.setDefaultApplication(0);
            }
            else{
                var e = Object.setPrototypeOf(JSON.parse(atob(sessionStorage.getItem("selectedProfile"))),AppProfileModel.prototype);
                this._appProfileService.getComponent().setSelectedApp(e);
                //this._appProfileService.setSelectedApp(e);
            }
            
            //Handle users access
            this.userAccess = result.UserDetails.user_access;
            return true;
        },(error)=>{
            return false;});

        return test;
    }

    logout(): void {
        // clear token remove user from local storage to log user out
        //this.token = null;

    }
}