import { AppConfig } from '../../../../globals/app.config';
import { UsageByUserComponent } from './usage-by-user.component';
import { DataSourceService } from '../../../../services/datasource.service';

describe('Component: USAGE BY USER', () => {
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByUserComponent = new UsageByUserComponent(dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByUserComponent).toBeTruthy();
    });

    it('Placeholder should be username', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_USER).toBe('username');
    });

    it('Displayed columns should be 5', () => {
        expect(_appConfig.COLUMNS.USAGEBYSUSER.length).toBe(5);
    });

    it('Current report should be USAGE BY USER', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_USER).toBe('USAGE BY USER');
    });

});
