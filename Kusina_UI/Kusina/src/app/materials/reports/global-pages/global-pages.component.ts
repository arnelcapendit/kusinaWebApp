import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { AppConfig } from '../../../globals/app.config';
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: 'app-global-pages',
  templateUrl: './global-pages.component.html',
  styleUrls: ['./global-pages.component.css']
})
export class GlobalPagesComponent implements AfterViewInit, OnInit {

    overviewTab = [];
    selectedCategory;

    @ViewChild('tabGroup') tabGroup;
    constructor( 
        private _appConfig: AppConfig,
        private _expressionChangeService: ChangeDetectorRef
    ) { }

    ngOnInit() {
        this.overviewTab = this._appConfig.CUSTOM_REPORTS.REPORTS.PAGES_ARRAY_TAB;
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
