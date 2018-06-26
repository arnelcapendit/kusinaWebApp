import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { UsageReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { UsageReportService } from '../services/custom-reports/usage-report.service';

export class UsageDataSource extends DataSource<UsageReport> {

    private dataSubject = new BehaviorSubject<UsageReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public loading$ = this.loadingSubject.asObservable();

    totalData;
    dataLength;

    constructor(private _CustomReportService: UsageReportService) {
      super();
    }

    connect(collectionViewer: CollectionViewer): Observable<UsageReport[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadUsageReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam, filterMetrics) {
        this.loadingSubject.next(true);
        if (currentParam !== undefined) {
            // tslint:disable-next-line:max-line-length
            this._CustomReportService.getUsageDataByChild(search, sortDirection, pageIndex, pageSize, dateRange, reportType, currentParam, filter, filterMetrics).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)),
            )
            .subscribe(data => {
                this.totalData = this._CustomReportService.totalData;
                const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
                this.dataLength = Object.keys(data).length;
                this.dataSubject.next(sortedData);
            });
        } else {
            this._CustomReportService.getUsageData(search, sortDirection, pageIndex, pageSize, dateRange, reportType, filter).pipe(
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
    }

    sortData(data: UsageReport[], sortDirection, sortActive): UsageReport[] {
        if (!sortActive || sortDirection === '') {
          return data;
        }

        return data.sort((a, b) => {
          let propertyA: number | string = '';
          let propertyB: number | string = '';

          switch (sortActive) {
            case 'AVG_PAGES_PER_VISIT': [propertyA, propertyB] = [a.AVG_PAGES_PER_VISIT, b.AVG_PAGES_PER_VISIT]; break;
            case 'AVG_VISIT_DURATION': [propertyA, propertyB] = [a.AVG_VISIT_DURATION, b.AVG_VISIT_DURATION]; break;
            case 'HITS': [propertyA, propertyB] = [a.HITS, b.HITS]; break;
            case 'USERNAME': [propertyA, propertyB] = [a.USERNAME, b.USERNAME]; break;
            case 'VISITS': [propertyA, propertyB] = [a.VISITS, b.VISITS]; break;
            case 'CAREERLEVEL': [propertyA, propertyB] = [a.CAREERLEVEL, b.CAREERLEVEL]; break;
            case 'CAREERLEVELDESCRIPTION': [propertyA, propertyB] = [a.CAREERLEVELDESCRIPTION, b.CAREERLEVELDESCRIPTION]; break;
            case 'CAREERTRACKS': [propertyA, propertyB] = [a.CAREERTRACKS, b.CAREERTRACKS]; break;
            case 'COUNTRY': [propertyA, propertyB] = [a.COUNTRY, b.COUNTRY]; break;
            case 'GEOGRAPHY': [propertyA, propertyB] = [a.GEOGRAPHY, b.GEOGRAPHY]; break;
            case 'CAREERTRACKDESCRIPTION': [propertyA, propertyB] = [a.CAREERTRACKDESCRIPTION, b.CAREERTRACKDESCRIPTION]; break;
          }

          const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
          const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

          return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
