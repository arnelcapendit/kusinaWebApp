import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppConfig } from '../globals/app.config';
import { MytePageVsCountryDataSource } from '../datasources/myte-pageVsCountries.datasource';
import { TableModel } from '../models/table.model';
import { ToggleButtonComponent } from '../materials/reports/toggle-button/toggle-button.component';

@Injectable()
export class ReportCategoryService {
    toggleButton: ToggleButtonComponent | null;

    constructor() {
    }
    init(toggleButton: ToggleButtonComponent) {
        this.toggleButton = toggleButton;
    }

    getToggleButton() {
        return this.toggleButton;
    }



}
