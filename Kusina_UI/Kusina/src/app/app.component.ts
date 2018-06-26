import { Component, ViewChild, OnInit, AfterViewInit} from '@angular/core';
import { Adal5Service } from 'adal-angular5';
import { AppConfig } from './globals/app.config';
import { SidenavService } from './services/sidenav.service';
import { DateRangePickerService } from './services/daterange.service';
import { DateRangeModel } from './models/daterange.model';
import { DateModel } from './models/date.model';
import { InitializePiwik , ConfigurePiwikTracker, UsePiwikTracker} from 'angular2piwik';
import { SidenavComponent } from './materials/sidenav/sidenav.component';
import { LoginLoadingService } from './services/eso-authentication/loading-login.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  providers: [ InitializePiwik,ConfigurePiwikTracker,UsePiwikTracker ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

  @ViewChild('sidenav') public sidenav: SidenavComponent;

  //model
  loadingCompleted:boolean = false;

  constructor(
      private _adalService: Adal5Service,
      public loadingLoginService:LoginLoadingService,
      private _appConfig: AppConfig,
      private _sideNavService: SidenavService,
      private _dateRangeService: DateRangePickerService,
      private _initializePiwik: InitializePiwik,
      private configurePiwikTracker: ConfigurePiwikTracker,
      private _router: Router,
      private usePiwikTracker: UsePiwikTracker
  ) {}

  public ngOnInit(): void {
    this._adalService.init(this._appConfig.ESO_CONFIG.DEV);
    this._sideNavService.init(this.sidenav);
    this._dateRangeService.init(
      new DateRangeModel(
        new DateModel(
          this._appConfig.DATERANGEPICKER.DEFAULT_DATE,
          this._appConfig.DATEPICKEROPTIONS.MOMENTFORMAT),
        new DateModel(
          this._appConfig.DATERANGEPICKER.DEFAULT_DATE,
          this._appConfig.DATEPICKEROPTIONS.MOMENTFORMAT),
        new DateModel(
          this._appConfig.DATERANGEPICKER.DEFAULT_DATE,
          this._appConfig.DATEPICKEROPTIONS.MOMENTFORMAT),
        this._appConfig.DATERANGEPICKER.PERIODS.DAY)
    );

    this._router.events.subscribe((res)=>{
      console.log("guard events",res);
    });
  }

  ngAfterViewInit(): void {
    const userId = this._adalService.userInfo.profile.samaccount_name;
    this._initializePiwik.init(this._appConfig.PIWIK_ENVIRONMENTS.STAGING.URL, this._appConfig.PIWIK_ENVIRONMENTS.STAGING.ID);
    this.configurePiwikTracker.setUserId(userId);
  }


}
