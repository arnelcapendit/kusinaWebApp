import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef} from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { MatTabChangeEvent,MatTabsModule,MatTabGroup } from '@angular/material'; 
import { AppConfig } from '../../../globals/app.config'; 

@Component({
  selector: 'app-aibsp-client',
  templateUrl: './aibsp-client.component.html',
  styleUrls: ['./aibsp-client.component.css']
})
export class AibspClientComponent implements AfterViewInit, OnInit {
    overviewTab = [];
    selectedCategory;

    @ViewChild('tabGroup') tabGroup;
    constructor( 
        private _appConfig: AppConfig,
        private _expressionChangeService: ChangeDetectorRef
    ) { }

    ngOnInit() {
        this.overviewTab = this._appConfig.CUSTOM_REPORTS.REPORTS.AIBSP_ARRAY_TAB;
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
