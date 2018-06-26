import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { VisitorLogMetrics } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { UsageReportService } from '../services/custom-reports/usage-report.service';
import { VisitorReportService } from '../services/custom-reports/visitor-report.service';

export class VisitorLogsDetailsDataSource extends DataSource<VisitorLogMetrics> {

    private dataSubject = new BehaviorSubject<VisitorLogMetrics[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    totalData;
    dataLength;

    constructor(private _CustomReportService: VisitorReportService) {
      super();
    }

    connect(collectionViewer: CollectionViewer): Observable<VisitorLogMetrics[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadVisitorLogMetricsDetails(filter, sortDirection, sortActive, pageIndex, pageSize, dateRange, uniqueParam) {
        this.loadingSubject.next(true);
        // tslint:disable-next-line:max-line-length
        this._CustomReportService.getUsageDataByChild(filter, sortDirection, pageIndex, pageSize, dateRange, uniqueParam).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false)),
        )
        .subscribe(data => {
            this.totalData = this._CustomReportService.totalData;
            const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
            this.dataLength = Object.keys(data).length;
            this.dataSubject.next(sortedData);
        });
    }

    sortData(data: VisitorLogMetrics[], sortDirection, sortActive): VisitorLogMetrics[] {
        if (!sortActive || sortDirection === '') {
          return data;
        }

        return data.sort((a, b) => {
          let propertyA: number | string = '';
          let propertyB: number | string = '';

          switch (sortActive) {
            // case 'BROWSER': [propertyA, propertyB] = [a.BROWSER, b.BROWSER]; break;
            // case 'CLEAN_PAGEURL': [propertyA, propertyB] = [a.CLEAN_PAGEURL, b.CLEAN_PAGEURL]; break;
            case 'LAST_ACTION_TIME': [propertyA, propertyB] = [a.LAST_ACTION_TIME, b.LAST_ACTION_TIME]; break;
            // case 'OS': [propertyA, propertyB] = [a.OS, b.OS]; break;
            // case 'PAGE_TITLE': [propertyA, propertyB] = [a.PAGE_TITLE, b.PAGE_TITLE]; break;
          }

          const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
          const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

          return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
