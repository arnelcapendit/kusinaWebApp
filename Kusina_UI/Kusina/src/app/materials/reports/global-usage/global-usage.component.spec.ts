import { GlobalUsageComponent } from './global-usage.component';
import { AppConfig } from '../../../globals/app.config';

describe('Component: GLOBAL USAGE', () => {
    const _appConfig = new AppConfig();
    const globalUsageComponent = new GlobalUsageComponent(_appConfig);

    it('should create', () => {
        expect(globalUsageComponent).toBeTruthy();
    });

    it('selectedCategory sould not empty', () => {
        expect(globalUsageComponent.selectedCategory).toBe('USAGE BY USER');
    });

});
