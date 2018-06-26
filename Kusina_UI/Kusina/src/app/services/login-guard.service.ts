import { Injectable } from '@angular/core';
import { Adal5Service } from 'adal-angular5';
import { AppConfig } from '../globals/app.config';
import { SessionDataService } from '../services/session.service';
import { Router } from '@angular/router';
import { UserModel } from '../models/user.model';
import { ActivatedRouteSnapshot, CanActivate} from '@angular/router';
import { MsgHandlerService } from '../services/msg-handler.service';
import { UserAccessService } from '../services/user-access.service';
import { UserService } from './user.service';

@Injectable()
export class OnlyLoggedInUsersGuard implements CanActivate {
    private user: UserModel;
    countNotif;

    constructor(
        private _adalService: Adal5Service,
        private _sessionService: SessionDataService,
        private _msgHandlerService: MsgHandlerService,
        private _userService: UserAccessService) {}

    public getUser(): UserModel {
        return  this.user;
    }

    canActivate() {
        this._adalService.handleWindowCallback();
        if (!this._adalService.userInfo.authenticated) {
            this._adalService.login();
            return false;
        } else {
            if (this._adalService.userInfo.profile.samaccount_name !== undefined) {
                //console.log('EID: ', this._adalService.userInfo.profile.samaccount_name);
                this.user = new UserModel(
                    this._adalService.userInfo.profile.samaccount_name,
                    this._adalService.userInfo.profile.auth_time,
                    this._adalService.userInfo.profile.exp,
                    this._adalService.userInfo.profile.nonce,
                    this._adalService.userInfo.profile.display_name
                );
                // console.log('auth user: ', this.user);
                this._sessionService.saveToEsSessionDetails(this.user);
                return true;
            }else {
                // console.log('Problem retrieving' + 'user credential from ESO.');
                this._msgHandlerService.handleComponentError('Problem retrieving' + 'user credential from ESO.');
            }
        }
    }
}

@Injectable()
export class UserHasSession implements CanActivate {
    constructor(
        private _userService: UserService,
        private _router: Router,
        private _appConfig: AppConfig) {
    }

    canActivate() {
        if (!this._userService.getUser()) {
            this._router.navigateByUrl(this._appConfig.WEB_PAGES.ROOT_PAGE);
            return false;
        } else {
            return true;
        }
    }
}


@Injectable()
export class UserTriedToLogin implements CanActivate {
    constructor( private _adalService: Adal5Service ) { }

    canActivate() {
        if (this._adalService.userInfo.authenticated) {
            return true;
        } else {
            return false;
        }
    }
}

