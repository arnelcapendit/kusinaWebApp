import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { AppConfig } from '../../../globals/app.config';
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: 'app-global-usage',
  templateUrl: './global-usage.component.html',
  styleUrls: ['./global-usage.component.css']
})
export class GlobalUsageComponent implements AfterViewInit, OnInit {

    overviewTab = [];
    selectedCategory = 'USAGE BY USER';

    @ViewChild('tabGroup') tabGroup;
    constructor( 
        private _appConfig: AppConfig,
        private _expressionChangeService: ChangeDetectorRef
    ) { }

    ngOnInit() {
        this.overviewTab = this._appConfig.CUSTOM_REPORTS.REPORTS.USAGE_ARRAY_TAB;
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
