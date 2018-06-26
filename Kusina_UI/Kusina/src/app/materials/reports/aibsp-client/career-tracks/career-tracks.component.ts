import { Component, OnInit, ViewChild } from '@angular/core';
import { TableComponent } from '../../../table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';

@Component({
    selector: 'app-career-tracks',
    templateUrl: './career-tracks.component.html',
    styleUrls: ['./career-tracks.component.css']
})
export class AibspCareerTracksComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;
    searchPlaceholder: string;
    reportType: string;
    filter: string;

    @ViewChild('careerTracks') public tableFilter: TableFilterComponent;
    constructor(private _tableService: DataSourceService, private _appConfig: AppConfig) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.CAREERTRACKSBYSEGMENTNAME;
        this.currentReport = this._appConfig.CUSTOM_REPORTS.AIBSP_TAB.CAREERTRACKS_BY_SEGMENTNAME;
        this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.CAREERTRACKS_BY_SEGMENTNAME;
        this.reportType = this._appConfig.REPORT_TYPE.CAREERTRACKS_BY_SEGMENTNAME;
        this.filter = this._appConfig.FILTER.CAREERTRACKS_BY_SEGMENTNAME;
        this._tableService.init(this.tableFilter);
    }


}
