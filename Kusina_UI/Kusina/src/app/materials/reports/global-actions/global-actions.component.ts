import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { MatTabChangeEvent,MatTabsModule,MatTabGroup } from '@angular/material'; 
import { AppConfig } from '../../../globals/app.config'; 

@Component({
  selector: 'app-global-actions',
  templateUrl: './global-actions.component.html',
  styleUrls: ['./global-actions.component.css']
})
export class GlobalActionsComponent implements OnInit {

  actionTab=[];
  selectedCategory;

  @ViewChild('tabGroup') tabGroup;
  constructor(
    private _appConfig: AppConfig,
    private _expressionChangeService: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.actionTab = this._appConfig.CUSTOM_REPORTS.REPORTS.EVENTS;
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
