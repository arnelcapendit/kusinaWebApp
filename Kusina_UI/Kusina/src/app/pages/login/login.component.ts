import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthenticationService } from  '../../services/eso-authentication/authentication.service';
import { Adal5Service } from 'adal-angular5';
import { UserModel } from '../../models/user.model';
import { Router } from '@angular/router';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { ChartService } from '../../services/chart.service';
import { ConfigurePiwikTracker } from 'angular2piwik';
import { ToolBarService } from '../../services/toolbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {
    model: any = {};
    loading = false;
    error = '';
    noAccess: boolean;
    private user: UserModel;

    constructor(
        private _router: Router,
        private _adalService: Adal5Service,
        private _authenticationService: AuthenticationService,
        private _configurePiwikTracker: ConfigurePiwikTracker,
        private _toolbarService: ToolBarService
    ) { 
        //configure piwik user id
        // _configurePiwikTracker.setUserId('');
    }

    ngOnInit() {
        this._adalService.handleWindowCallback();
        
        // console.log( 'authenticated', this._adalService.userInfo.authenticated);
        // console.log('Nonce: ', this._adalService.userInfo.profile.nonce);
        // console.log('Local', currentUser.nonce_id);
        if (!this._adalService.userInfo.authenticated) {
            this._authenticationService.logout();
            this._adalService.login();
        } else {
            // when user is logged in, create app session
            this.createKusinaSession();
        }
        //keeping this line of code for future purposes
        //this._authenticationService.logout();
    }

    ngAfterViewInit() {
        //configure piwik user id
        // this._configurePiwikTracker.setUserId(this._adalService.userInfo.profile.samaccount_name);
        // this._configurePiwikTracker.setDocumentTitle();
    }

    createKusinaSession() {
        this.loading = true;
        //create user object
        this.user = new UserModel(
            this._adalService.userInfo.profile.samaccount_name,
            this._adalService.userInfo.profile.auth_time,
            this._adalService.userInfo.profile.exp,
            this._adalService.userInfo.profile.nonce,
            this._adalService.userInfo.profile.display_name
        );

        //authenticate login
        this._authenticationService.login(this.user)
            .subscribe(result => {
                if (result === true) {
                    // login successful && user is onboarded
                    this.noAccess = false;
                    this._router.navigate(['/dashboard']);
                } else {
                    // login successful && user is NOT onboarded
                    console.log('Dito ka dapat:');
                    this.noAccess = true;
                    this._toolbarService.getComponent().loadCompleted = true;
                    this._router.navigate(['/onboarding']);
                }
                this.loading = false;
            });
    }

}
