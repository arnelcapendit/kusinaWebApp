import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { TableComponent } from '../../../materials/table/table.component';
import { DataSourceService } from '../../../services/datasource.service';
import { AppConfig } from '../../../globals/app.config';

@Component({
    selector: 'app-project-preferences',
    templateUrl: './project-preferences.component.html',
    styleUrls: ['./project-preferences.component.css']
})
export class ProjectPreferencesComponent implements OnInit {

    displayedColumns = [];
    currentReport: string;

    @ViewChild('projectpref') public table: TableComponent;
    constructor(
        private _tableService: DataSourceService, 
        private _appConfig: AppConfig
    ) { }

    ngOnInit() {
        this.displayedColumns = this._appConfig.COLUMNS.PROJECTPREFERENCES;
        this.currentReport = this._appConfig.SETTINGS.SETTINGS_TAB.PROJECT_PREFERENCES;
        this._tableService.init(this.table);
    }

}
