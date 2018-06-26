import { Component, OnInit, Output, EventEmitter, ViewChild, AfterViewInit, ChangeDetectorRef, AfterViewChecked} from '@angular/core';
import { HighchartComponent } from '../../materials/highchart/highchart.component';
import { MetricComponent} from '../../materials/metric/metric.component';

import { Router, ActivatedRoute } from '@angular/router';
import { ChartService } from '../../services/chart.service';
import { DateRangePickerService } from '../../services/daterange.service';
import { AppProfileService } from '../../services/app-profile.service';
import { AppConfig } from '../../globals/app.config';
import { AppProfileModel } from '../../models/app-profile.model';
import { DateRangeModel } from '../../models/daterange.model';
import { UserAccessService } from '../../services/user-access.service';
import { UserModel } from '../../models/user.model';
import { NavbarComponent } from '../../materials/navbar/navbar.component';
import { MatTabChangeEvent, MatDialog } from '@angular/material';
import { UsePiwikTracker } from 'angular2piwik';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { AnnouncementsService } from '../../services/announcement.service';
import { DialogComponent } from '../../materials/dialog/dialog.component';
import { ToolBarService } from '../../services/toolbar.service';


@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit, AfterViewInit, AfterViewChecked {

  settingsTab = [];
  selectedCategory;
  acceptedDataPrivacy:boolean=false;
  private user: UserModel;

  @ViewChild('tabGroup') tabGroup;
  constructor(
      private _userService: UserAccessService,
      private _appConfig: AppConfig,
      private _router: Router,
      private usePiwikTracker: UsePiwikTracker,
      private _globalFilterService: GlobalFilterService,
      private _announcementService: AnnouncementsService,
      private _expressionChangeService: ChangeDetectorRef,
      private _toolbarService: ToolBarService,
      public dialog: MatDialog,
      private route: ActivatedRoute
  ) {
        const url = _appConfig.getActiveTracker() + _appConfig.WEB_PAGES.SETTINGS;
        this.usePiwikTracker.setPageAndTrack(url, 'Inframetrics - Settings');
  }


  ngOnInit() {
  }

  ngAfterViewInit() {
      if (this.tabGroup.selectedIndex === 0) {
          this.selectedCategory = this.tabGroup._tabs.first.textLabel;
      }
  }

  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    this.selectedCategory = tabChangeEvent.tab.textLabel;
  }

  ngAfterViewChecked() {
    this._expressionChangeService.detectChanges();
  }
  

}
