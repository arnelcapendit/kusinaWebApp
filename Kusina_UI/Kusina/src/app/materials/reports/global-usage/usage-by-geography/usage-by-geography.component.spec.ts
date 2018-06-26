import { AppConfig } from '../../../../globals/app.config';
import { UsageByGeographyComponent } from './usage-by-geography.component';
import { DataSourceService } from '../../../../services/datasource.service';

describe('Component: USAGE BY GEOGRAPHY', () => {
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByGeographyComponent = new UsageByGeographyComponent(dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByGeographyComponent).toBeTruthy();
    });

    it('Placeholder should be geography', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_GEOGRAPHY).toBe('geography');
    });

    it('Displayed columns should be 6', () => {
        expect(_appConfig.COLUMNS.USAGEBYSGEOGRAPHY.length).toBe(6);
    });

    it('Current report should be USAGE BY GEOGRAPHY', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_GEOGRAPHY).toBe('USAGE BY GEOGRAPHY');
    });

});
