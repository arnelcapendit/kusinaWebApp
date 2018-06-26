import { Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
import { TableComponent } from '../../materials/table/table.component';
import { DataSourceService } from '../../services/datasource.service';
import { AppConfig } from '../../globals/app.config';
import { AppProfileService } from '../../services/app-profile.service';
import { ToggleButtonComponent } from '../../materials/reports/toggle-button/toggle-button.component';
import { ReportCategoryService } from '../../services/report-category.service';
import { UsePiwikTracker } from 'angular2piwik';
import { Router } from '@angular/router';
import { UserModel } from '../../models/user.model';
import { AnnouncementsService } from '../../services/announcement.service';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { MatDialog } from '@angular/material';
import { DialogComponent } from '../../materials/dialog/dialog.component';
import { ToolBarService } from '../../services/toolbar.service';
@Component({
  selector: 'app-custom',
  templateUrl: './custom.component.html',
  styleUrls: ['./custom.component.css']
})
export class CustomComponent implements OnInit {

  @ViewChild('toggleButton') public toggle: ToggleButtonComponent;

  selectedCategory: string;
  acceptedDataPrivacy:boolean = false;
  private user: UserModel;

  constructor(
      private _appConfig: AppConfig,
      private _router: Router,
      private _appProfile: AppProfileService,
      private _toggleButtonService: ReportCategoryService,
      private usePiwikTracker: UsePiwikTracker,
      private _globalFilterService: GlobalFilterService,
      private _announcementService: AnnouncementsService,
      private _expressionChangeService: ChangeDetectorRef,
      private _toolbarService: ToolBarService,
      public dialog: MatDialog
    ) { 
      //Piwik tracker
      const url = this._appConfig.getActiveTracker() + this._appConfig.WEB_PAGES.CUSTOMREPORT_PAGE;
      usePiwikTracker.setPageAndTrack(url, 'Inframetrics - Custom Reports');
    }

  ngOnInit() {
    this._announcementService.getNotifCount(this._globalFilterService.getUser());
    this.selectedCategory = 'OVERVIEW';
    this._toggleButtonService.init(this.toggle);
    console.log("tesing lang: "+ this._appProfile.getSelectedApp().getAirId());
  }

  ngAfterViewChecked() {
    this._expressionChangeService.detectChanges();
  }

  ifClient() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.CLIENT ? true : false;
  }
  ifMyTEClient() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.CLIENT ?
           this._appProfile.getSelectedApp().getAirId() === '2700' ? true : false : false;
  }

  ifITFClient(){
    // return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.CLIENT ?
    // this._appProfile.getSelectedApp().getAirId() === '5238' ? true : false : false;
    const airID = this._appProfile.getSelectedApp().getAirId();
    if (airID === this._appConfig.SPECIAL_CLIENTS.ITF && this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.CLIENT) {
        return true;
    } else {
        return false;
    }
  }

  ifAibspClient() {
    const airID = this._appProfile.getSelectedApp().getAirId();
    // console.log('airID: ', airID);
    if (
      (airID === this._appConfig.SPECIAL_CLIENTS.AIBSP || airID === this._appConfig.SPECIAL_CLIENTS.BVI) &&
      this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.CLIENT) {
        return true;
    } else {
        return false;
    }
  }
  ifOverview() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.OVERVIEW ? true : false;
  }
  ifUsage() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.USAGE ? true : false;
  }

  ifPages() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.PAGES ? true : false;
  }

  ifVisitors() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.VISITORS ? true : false;
  }
  ifActions() {
    return this.selectedCategory === this._appConfig.CUSTOM_REPORTS.CATEGORY_ITEMS.ACTIONS ? true : false;
  }

}


