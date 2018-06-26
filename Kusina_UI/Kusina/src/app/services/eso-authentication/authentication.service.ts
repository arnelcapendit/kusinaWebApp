import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { UserModel } from '../../models/user.model';
import { AppConfig } from '../../globals/app.config';
import { UserService } from '../user.service';
import { SidenavDisplayNameComponent } from '../../materials/sidenav-display-name/sidenav-display-name.component';
import { AppProfileModel } from '../../models/app-profile.model';
import { AppProfileService } from '../app-profile.service';
import { ToolBarService } from '../toolbar.service';
 
@Injectable()
export class AuthenticationService {
    public token: string;
    private user: UserModel;
 
    constructor(
        private _http: HttpClient, 
        private _appConfig: AppConfig,
        private _userService: UserService,
        private _appProfileService: AppProfileService,
        private _toolbarService: ToolBarService
    ) {
    }
 
    login(user:UserModel): Observable<boolean>{

          //create item in kusina-sessions
        const params = 
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID+user.getEID()+"&"+
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE+user.getNonceID()+"&"+
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AUTHTIME+user.getAuthTime()+"&"+
            this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EXP+user.getExpTime();
    
        const url = this._appConfig.URI.saveUserSessionDetails+params; 
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
        headers.append('Access-Control-Allow-Origin','*');
        return this._http.get(url, {headers})
        .map((response:Response)=>{
            console.log("RESPONSE: ",response);
            if(response != null && response.status.toString() == 'created'){
                //this._toolbarService.getComponent().loadCompleted=true;
               /// x
                return true;
            }
            return false;
        });
    }

    
 
    logout(): void {
        // clear token remove user from local storage to log user out
        this.token = null;
    }
}