import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppConfig } from '../globals/app.config';
import { UserModel } from '../models/user.model';

@Injectable()
export class UserService {
    user: UserModel;

    constructor(private _router: Router, private _appConfig: AppConfig) {

    }
    init(user: UserModel){
        this.user = user;
    }

    getUser() {
        return this.user;
    }
}