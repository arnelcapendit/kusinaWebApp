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
import { VisitorReportService } from '../services/custom-reports/visitor-report.service';

export class VisitorLogDataSource extends DataSource<VisitorLogMetrics> {

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

    loadVisitorLogMetrics(filter, sortDirection, sortActive, pageIndex, pageSize, dateRange) {
        const reportType = 'visitorLogs';
        this.loadingSubject.next(true);
        this._CustomReportService.getUsageData(filter, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
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
            case 'VISITS': [propertyA, propertyB] = [a.VISITS, b.VISITS]; break;
            case 'LAST_ACTION_TIME': [propertyA, propertyB] = [a.LAST_ACTION_TIME, b.LAST_ACTION_TIME]; break;
            case 'USER': [propertyA, propertyB] = [a.USER, b.USER]; break;
            case 'COUNTRY': [propertyA, propertyB] = [a.COUNTRY, b.COUNTRY]; break;
            case 'ADDRESS': [propertyA, propertyB] = [a.ADDRESS, b.ADDRESS]; break;
          }

          const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
          const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

          return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
