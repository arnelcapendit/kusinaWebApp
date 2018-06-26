import { TestBed, inject } from '@angular/core/testing';

import { ItfReportService } from './itf-report.service';

describe('ItfReportService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ItfReportService]
    });
  });

  it('should be created', inject([ItfReportService], (service: ItfReportService) => {
    expect(service).toBeTruthy();
  }));
});
