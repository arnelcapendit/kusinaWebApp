import { MatDialogRef } from '@angular/material';
import { UsageByLevelDetailsComponent } from './details.component';
import { AppConfig } from '../../../../../globals/app.config';
import { DataSourceService } from '../../../../../services/datasource.service';

describe('Component: USAGE BY GEOGRAPHY DETAILS', () => {
    let _dialog: MatDialogRef<UsageByLevelDetailsComponent>;
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByLevelDetailsComponent = new UsageByLevelDetailsComponent(_dialog, dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByLevelDetailsComponent).toBeTruthy();
    });

    it('Placeholder should be career level', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_LEVEL_DETAILS).toBe('career level');
    });

    it('Displayed columns should be 5', () => {
        expect(_appConfig.COLUMNS.DETAILSUSAGEBYSGEOGRAPHY.length).toBe(5);
    });

    it('Current report should be DETAILS USAGE BY LEVEL', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_LEVEL).toBe('DETAILS USAGE BY LEVEL');
    });

    it('Should open a dialog with a component', () => {
        expect(usageByLevelDetailsComponent).toBeTruthy();
    });

});
