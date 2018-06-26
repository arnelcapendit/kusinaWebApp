import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { TableComponent } from '../../../../table/table.component';
import { DataSourceService } from '../../../../../services/datasource.service';
import { AppConfig } from '../../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-careertrack-details',
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class PagesVsCareertrackDetailsComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    currentParam: string | null;
    searchPlaceholder: string;
    filterMetrics: string;
    reportType: string;
    filter: string;

    @ViewChild('detailsPageVsCareertracks') public table: TableComponent;

    @Input()
    set uniqueParam(val) {
        this.currentParam = val;
    }

    constructor(
        public dialogRef: MatDialogRef<PagesVsCareertrackDetailsComponent>,
        private _tableService: DataSourceService,
        private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.DETAILSPAGESVSCAREERTRACKS;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.PAGES_TAB.DETAILS_PAGES_VS_CAREERTRACKS;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.PAGES_BY_CAREERTRACKS_DETAILS;
        this.reportType = this._appConfig.REPORT_TYPE.PAGES_BY_CAREERTRACKS;
        this.filter = this._appConfig.FILTER.PAGES_BY_CAREERTRACKS;
        this._tableService.init(this.table);
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

}
