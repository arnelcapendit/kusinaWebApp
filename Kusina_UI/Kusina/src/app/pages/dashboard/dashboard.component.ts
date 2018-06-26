import { Component, OnInit, Output, EventEmitter,ViewChild, AfterViewInit, ChangeDetectorRef} from '@angular/core';
import { HighchartComponent } from '../../materials/highchart/highchart.component';
import { MetricComponent} from '../../materials/metric/metric.component';
import { Router, RouterStateSnapshot } from '@angular/router';
import { ChartService } from '../../services/chart.service';
import { DateRangePickerService } from '../../services/daterange.service';
import { AppProfileService } from '../../services/app-profile.service'
import { AppConfig } from '../../globals/app.config';
import { AppProfileModel } from '../../models/app-profile.model';
import { DateRangeModel } from '../../models/daterange.model'
import { UsePiwikTracker, ConfigurePiwikTracker } from 'angular2piwik';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { UserModel } from '../../models/user.model';
import { AnnouncementsService } from '../../services/announcement.service';
import { DialogComponent } from '../../materials/dialog/dialog.component';
import { MatDialog } from '@angular/material';
import { ToolBarService } from '../../services/toolbar.service';
import { Adal5Service } from 'adal-angular5';
import { AuthenticationService } from '../../services/eso-authentication/authentication.service';


//import { ComponentsDataHandlerService } from '../../services/components-data-handler.service';
//import { ProfileNameMenuComponent} from '../../materials/dashboard-profile-name-menu/profile-name-menu.component';
//import { NavbarComponent} from '../../materials/app-navigation-toolbar/navbar.component';
//import { ErrorpageComponent } from '../../pages/errorpage/errorpage.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit,AfterViewInit{


    isShowLoader: boolean;
    loading = false;
    private user: UserModel;

    color = 'warn';
    mode =  'indeterminate';
    value = 0;
    bufferValue = 0;
    acceptedDataPrivacy = false;

  @ViewChild('visitsOverTime') public visitsOverTime: HighchartComponent;
  @ViewChild('topPages') public topPages: HighchartComponent;
  @ViewChild('visitLocation') public visitLocation: HighchartComponent;
  @ViewChild('browserType') public browserType: HighchartComponent;
  @ViewChild('deviceType') public deviceType: HighchartComponent;
  @ViewChild('metric') public metric: MetricComponent;
  //@ViewChild('appFilters') public appFilters: ProfileNameMenuComponent;
  //@ViewChild('appNavBar') public appNavBar: NavbarComponent; 

   //color = 'warn';
  // // mode = 'query';
  // mode = 'indeterminate';
  // value = 0;
  // bufferValue = 0;

  private selectedDaterange:DateRangeModel;
  private selectedAppProfile:AppProfileModel;
  private retries:number = 0;
  

  chartNames = ['VisitsOvertimes',  'TopPages', 'VisitLocation', 'BrowserType', 'DeviceType'];
  constructor(
    private _appConfig: AppConfig,
    private _chartService: ChartService,
    private _router: Router,
    private _adalService: Adal5Service,
    private _authenticationService: AuthenticationService,
    private _usePiwikTracker: UsePiwikTracker,
    private _globalFilterService: GlobalFilterService,
    private _announcementService: AnnouncementsService,
    private _toolbarService: ToolBarService,
    private _expressionChangeService: ChangeDetectorRef,
    public dialog: MatDialog
  ) {  
      //track page
      const url = this._appConfig.getActiveTracker() + this._appConfig.WEB_PAGES.DASHBOARD_PAGE;
      _usePiwikTracker.setPageAndTrack(url, 'Inframetrics - Dashboard');
      this._router.events.subscribe((res)=>{
        console.log("guard events",res);
      });
  }       

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    this.getNotifCount(this._globalFilterService.getUser());
    this._chartService.initCharts([this.visitsOverTime,this.topPages,this.visitLocation,this.browserType,this.deviceType],this.metric);
    this._chartService.setCharts(this._globalFilterService.getUser(),this._globalFilterService.getDateRangeService(),this._globalFilterService.getSelectedApp());
    this._chartService.refreshCharts();
    this._expressionChangeService.detectChanges();
  }

  public getIsShowLoader(): boolean {
      return this.isShowLoader = this._chartService.loadingData;
  }

  getNotifCount(user) {
    this._announcementService.getNotifCount(user).subscribe( data => {
      console.log('Notif Count: ', data);
      
     
    });
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
                this._router.navigate(['/dashboard']);
            } else {
                // login successful && user is NOT onboarded
                console.log('Dito ka dapat:');
                this._toolbarService.getComponent().loadCompleted = true;
                this._router.navigate(['/onboarding']);
            }
            this.loading = false;
        });
}
  

  // public getIsShowLoader():boolean{
  //  return this.componentHandler.isShowLoader;
  // }

}
