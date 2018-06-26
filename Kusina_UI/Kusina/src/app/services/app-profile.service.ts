import { Injectable} from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpInterceptor} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AppConfig } from '../globals/app.config';
import { MsgHandlerService } from  '../services/msg-handler.service';
import { AppProfileModel } from '../models/app-profile.model';
import { UserModel } from '../models/user.model';
import { ProfileNameMenuComponent} from '../materials/profile-name-menu/profile-name-menu.component';

@Injectable()
export class AppProfileService {

    private appProfileList: Array<AppProfileModel>=[];
    private selectedApp: AppProfileModel;
    private appProfileOptions= [];
    private appProfileComponent: ProfileNameMenuComponent;

    public constructor(private _appConfig:AppConfig ){
    }
    public initModel(appProfileList:Array<AppProfileModel>){
        this.appProfileList=appProfileList;
        for (var key in appProfileList) {
            if (appProfileList.hasOwnProperty(key)) {
                //console.log("meow",appProfileList[key].getFormattedValue());
                this.appProfileOptions[key]  = appProfileList[key].getFormattedValue();
            }
        }    
    }
    public setSelectedApp(selectedApp: AppProfileModel) {
        this.selectedApp = selectedApp;
        sessionStorage.setItem("selectedProfile",btoa(JSON.stringify(this.selectedApp)));
    }
    public initComponent(component?:ProfileNameMenuComponent){
        this.appProfileComponent=component;
    }
    public setDefaultApplication(index:number){
        this.selectedApp=this.appProfileList[index];
        sessionStorage.setItem("selectedProfile",btoa(JSON.stringify(this.selectedApp)));
    }
    public getDefaultApplication(){
        return this.appProfileList[0];
    }
    public getComponent():ProfileNameMenuComponent{
        return this.appProfileComponent;
    }
    public getAppProfileList():Array<AppProfileModel>{
        return this.appProfileList;
    }
    public getSelectedApp():AppProfileModel{
        this.selectedApp = Object.setPrototypeOf(JSON.parse(atob(sessionStorage.getItem("selectedProfile"))),AppProfileModel.prototype);
        return this.selectedApp;
    }
    public getAppProfileOptions():Array<string>{
        return this.appProfileOptions;
    }
    public setAppProfileOptions(value:Array<string>){
        this.appProfileOptions=value;
    }

}
