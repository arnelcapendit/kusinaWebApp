import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { AppConfig } from '../../../globals/app.config';
import { MatTabChangeEvent, MatTabGroup } from '@angular/material';
import { CustomReportService } from '../../../services/custom-report.service';

@Component({
  selector: 'app-global-overview',
  templateUrl: './global-overview.component.html',
  styleUrls: ['./global-overview.component.css']
})
export class GlobalOverviewComponent implements AfterViewInit, OnInit {

  overviewTab = [];
  selectedCategory;

  @ViewChild('tabGroup') tabGroup: MatTabGroup;
  constructor(
    private _appConfig: AppConfig,
    private _expressionChangeService: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.overviewTab = this._appConfig.CUSTOM_REPORTS.REPORTS.OVERVIEW_ARRAY_TAB;
  }

  ngAfterViewInit() {
      if (this.tabGroup.selectedIndex === 0) {
          this.selectedCategory = this.tabGroup._tabs.first.textLabel;
      }
  }
  ngAfterViewChecked() {
    this._expressionChangeService.detectChanges();
  }
  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    this.selectedCategory = tabChangeEvent.tab.textLabel;
  }


}
