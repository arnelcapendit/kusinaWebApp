import { Component, OnInit, ViewChild } from '@angular/core';
import { AppConfig } from '../../../globals/app.config';
import { UserAccessService } from '../../../services/user-access.service';
import { MatTabChangeEvent } from '@angular/material';
import { TableFilterComponent } from '../../../materials/table-filter/table-filter.component';
import { DataSourceService } from '../../../services/datasource.service';

@Component({
  selector: 'app-feedbacks',
  templateUrl: './feedbacks.component.html',
  styleUrls: ['./feedbacks.component.css']
})
export class FeedbacksComponent implements OnInit {

    feedbacksTab = [];
    selectedCategory;

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('feedbacks') public tableFilter: TableFilterComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        this.feedbacksTab = this._appConfig.FEEDBACKS.FEEDBACKS_ARRAY_TAB;
        this.displayedColumns = this._appConfig.COLUMNS.FEEDBACKS;
        this.currentReport = this._appConfig.FEEDBACKS.FEEDBACKS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.FEEDBACKS;
        this.reportType = this._appConfig.REPORT_TYPE.FEEDBACKS;
        this.filter = this._appConfig.FILTER.FEEDBACKS;
        this._tableService.init(this.tableFilter);
    }


}
