import { DetailsComponent } from './details.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { MatDialogRef } from '@angular/material';

describe('Component: VISITOR LOGS DETAILS', () => {
    let _dialog: MatDialogRef<DetailsComponent>;
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const visitorLogsDetailsComponent = new DetailsComponent(_dialog, dataSourceService, _appConfig);

    it('should create', () => {
        expect(visitorLogsDetailsComponent).toBeTruthy();
    });

    it('Placeholder should be visitor', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.VISITORLOGS).toBe('visitor');
    });

    it('Displayed columns should be 5', () => {
        expect(_appConfig.COLUMNS.VISITORSDETAILS.length).toBe(5);
    });

    it('Current report should be VISITOR LOGS DETAILS', () => {
        expect(_appConfig.CUSTOM_REPORTS.REPORTS.VISITORSDETAILS).toBe('VISITOR LOGS DETAILS');
    });

    it('Should open a dialog with a component', () => {
        expect(visitorLogsDetailsComponent).toBeTruthy();
    });

});
