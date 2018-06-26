import { Component, OnInit, ViewChild, Output, EventEmitter, Input, AfterViewInit } from '@angular/core';
import { ProfileNameMenuComponent} from '../../materials/profile-name-menu/profile-name-menu.component';
import { SidenavService } from '../../services/sidenav.service';
import { AppProfileService } from '../../services/app-profile.service';
import { AppProfileModel } from '../../models/app-profile.model';
import { Router } from '@angular/router';
import { AppConfig } from '../../globals/app.config';
import { DateRangePickerComponent } from '../date-range-picker/date-range-picker.component';
import { DateRangePickerService } from '../../services/daterange.service';
import { MatButton } from '@angular/material';
import { AnnouncementsService } from '../../services/announcement.service';
import { SessionDataService } from '../../services/session.service';
import { AnnouncementIconComponent } from '../announcement/announcement-icon/announcement-icon.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  title=[];
  isHidden = false;
  hideTitle = false;
  loadCompleted = false;

  @ViewChild('appFilters') public appFilters: ProfileNameMenuComponent;
  @ViewChild('dateRangePicker') public dateRangePickerComponent: DateRangePickerComponent;
  @ViewChild('appTitle') public appTitleComponent: MatButton;
  @ViewChild('announcementIcon') public announcementIconComponent: AnnouncementIconComponent;
  @Output() selectedApp = new EventEmitter<AppProfileModel>();

  private selectedAppName:string;

  constructor(
    private _sidenavService: SidenavService,
    private _appProfileService: AppProfileService,
    private  _router: Router,
    private _dateRangeService: DateRangePickerService,
    private _announceService: AnnouncementsService
  ) { }

  ngOnInit() {
    this._appProfileService.initComponent(this.appFilters);
    this._dateRangeService.initComponent(this.dateRangePickerComponent);
    this._announceService.initComponent(this.announcementIconComponent);

    // this.loadCompleted =true;
     
    
    //console.log('navbar notif');
  }

  ngAfterViewInit(){

  }


  public toggleSidenav(action:string) {
    //console.log(action);
    this._sidenavService.getComponent().setAction(action);
    //console.log( this._sidenavService.getComponent().action);
    this._sidenavService.getComponent().sidenav.toggle().then(() => { });
  }

  // public toggleEndnav() {
  //   this._sidenavService.getComponent().toggleEnd().then(() => { });
  // }

  public configureVisibility() {

    if (this._router.url === '/onboarding') {
      this.isHidden = true;
    } else {
      this.isHidden = false;
    }
  }

  public setActionVisibility(val:boolean){
    this.isHidden=val;
  }
}
