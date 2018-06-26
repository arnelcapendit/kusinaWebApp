import { MatDialogRef } from '@angular/material';
import { UsageByGeographyDetailsComponent } from './details.component';
import { AppConfig } from '../../../../../globals/app.config';
import { DataSourceService } from '../../../../../services/datasource.service';

describe('Component: USAGE BY GEOGRAPHY DETAILS', () => {
    let _dialog: MatDialogRef<UsageByGeographyDetailsComponent>;
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByGeographyDetailsComponent = new UsageByGeographyDetailsComponent(_dialog, dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByGeographyDetailsComponent).toBeTruthy();
    });

    it('Placeholder should be country', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_GEOGRAPHY_DETAILS).toBe('country');
    });

    it('Displayed columns should be 5', () => {
        expect(_appConfig.COLUMNS.DETAILSUSAGEBYSGEOGRAPHY.length).toBe(5);
    });

    it('Current report should be DETAILS USAGE BY GEOGRAPHY', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_GEOGRAPHY).toBe('DETAILS USAGE BY GEOGRAPHY');
    });

    it('Should open a dialog with a component', () => {
        expect(usageByGeographyDetailsComponent).toBeTruthy();
    });

});
