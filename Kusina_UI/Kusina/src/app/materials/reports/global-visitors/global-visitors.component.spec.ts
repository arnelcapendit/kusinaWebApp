import { GlobalVisitorsComponent } from './global-visitors.component';
import { DataSourceService } from '../../../services/datasource.service';
import { AppConfig } from '../../../globals/app.config';

describe('Component: Global Visitors', () => {
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const globalVisitorsComponent = new GlobalVisitorsComponent(dataSourceService, _appConfig);

    it('should create', () => {
        expect(globalVisitorsComponent).toBeTruthy();
    });

    it('Placeholder should be visitor', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.VISITORLOGS).toBe('visitor');
    });

    it('Displayed columns should be 5', () => {
        expect(_appConfig.COLUMNS.VISITORS.length).toBe(5);
    });

    it('Current report should be VISITOR LOGS', () => {
        expect(_appConfig.CUSTOM_REPORTS.REPORTS.VISITORS).toBe('VISITOR LOGS');
    });

});
