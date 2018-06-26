import { AppConfig } from '../../../../globals/app.config';
import { UsageByLevelComponent } from './usage-by-level.component';
import { DataSourceService } from '../../../../services/datasource.service';

describe('Component: USAGE BY LEVEL', () => {
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByLevelComponent = new UsageByLevelComponent(dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByLevelComponent).toBeTruthy();
    });

    it('Placeholder should be career level', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_LEVEL).toBe('career level');
    });

    it('Displayed columns should be 6', () => {
        expect(_appConfig.COLUMNS.USAGEBYSLEVEL.length).toBe(6);
    });

    it('Current report should be USAGE BY LEVEL', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_LEVEL).toBe('USAGE BY LEVEL');
    });

});
