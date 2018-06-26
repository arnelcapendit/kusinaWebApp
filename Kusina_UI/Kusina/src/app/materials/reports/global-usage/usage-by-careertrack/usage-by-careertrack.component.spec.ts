import { AppConfig } from '../../../../globals/app.config';
import { UsageByCareertrackComponent } from './usage-by-careertrack.component';
import { DataSourceService } from '../../../../services/datasource.service';

describe('Component: USAGE BY CAREERTRACKS', () => {
    const _appConfig = new AppConfig();
    const dataSourceService = new DataSourceService(_appConfig);
    const usageByCareertrackComponent = new UsageByCareertrackComponent(dataSourceService, _appConfig);

    it('should create', () => {
        expect(usageByCareertrackComponent).toBeTruthy();
    });

    it('Placeholder should be careertracks', () => {
        expect(_appConfig.SEARCH_PLACEHOLDER.USAGE_BY_CAREERTRACKS).toBe('careertracks');
    });

    it('Displayed columns should be 6', () => {
        expect(_appConfig.COLUMNS.USAGEBYSCAREERTRACKS.length).toBe(6);
    });

    it('Current report should be USAGE BY CAREERTRACKS', () => {
        expect(_appConfig.CUSTOM_REPORTS.USAGE_TAB.USAGE_BY_CAREERTRACKS).toBe('USAGE BY CAREERTRACKS');
    });

});
