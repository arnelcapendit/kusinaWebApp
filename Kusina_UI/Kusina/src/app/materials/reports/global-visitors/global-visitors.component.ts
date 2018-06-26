import { Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { MatTabChangeEvent, MatTabsModule, MatTabGroup } from '@angular/material';
import { AppConfig } from '../../../globals/app.config';

@Component({
    selector: 'app-global-visitors',
    templateUrl: './global-visitors.component.html',
    styleUrls: ['./global-visitors.component.css']
})
export class GlobalVisitorsComponent implements OnInit {
    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('visitorsTable') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig,
        private _expressionChangeService: ChangeDetectorRef
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.VISITORS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.REPORTS.VISITORS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.VISITORLOGS;
        this.reportType = this._appConfig.REPORT_TYPE.VISITOR_LOGS;
        this._tableService.init(this.table);
    }

}
