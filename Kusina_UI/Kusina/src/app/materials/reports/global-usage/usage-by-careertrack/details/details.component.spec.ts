import { MatDialogRef } from '@angular/material';
import { UsageByCareertrackDetailsComponent } from './details.component';
import { AppConfig } from '../../../../../globals/app.config';
import { DataSourceService } from '../../../../../services/datasource.service';

describe('Component: USAGE BY CAREERTRACKS DETAILS', () => {
    let _dialog: MatDialogRef<UsageByCareertrackDetailsComponent>;
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByCareertrackDetailsComponent = new UsageByCareertrackDetailsComponent(_dialog, dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByCareertrackDetailsComponent).toBeTruthy();
    });

    it('Placeholder should be careertracks description', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_CAREERTRACKS_DETAILS).toBe('careertracks description');
    });

    it('Displayed columns should be 5', () => {
        expect(_appConfig.COLUMNS.DETAILSUSAGEBYSCAREERTRACKS.length).toBe(5);
    });

    it('Current report should be DETAILS USAGE BY CAREERTRACKS', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.DETAILS_USAGE_BY_CAREERTRACKS).toBe('DETAILS USAGE BY CAREERTRACKS');
    });

    it('Should open a dialog with a component', () => {
        expect(usageByCareertrackDetailsComponent).toBeTruthy();
    });

});
