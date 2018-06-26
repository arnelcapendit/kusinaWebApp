import { Injectable } from '@angular/core';
// tslint:disable-next-line:import-blacklist
import { Observable } from 'rxjs';
import { CanActivate } from '@angular/router';
import { Adal5Service } from 'adal-angular5';
import { ToolBarService } from '../services/toolbar.service';

@Injectable()
export class AuthenticationGuard implements CanActivate {

    constructor(private _adalService: Adal5Service, private _toolbarService: ToolBarService) { }

    canActivate(): Observable<boolean> | boolean {

        if (!this._adalService.userInfo.authenticated) {
            this._adalService.login();
            return false;
        } else {
            this._toolbarService.getComponent().loadCompleted = true;
            return true;
        }
    }

}
