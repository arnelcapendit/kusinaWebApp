import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { MytePageVsCountry } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { VisitorReportService } from '../services/custom-reports/visitor-report.service';
import { MyteReportService } from '../services/custom-reports/myte-report.service';

export class MytePageVsCountryDataSource extends DataSource<MytePageVsCountry> {

    private dataSubject = new BehaviorSubject<MytePageVsCountry[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    totalData;
    dataLength;

    constructor(private _CustomReportService: MyteReportService) {
      super();
    }

    connect(collectionViewer: CollectionViewer): Observable<MytePageVsCountry[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadMytePageVsCountry(filter, sortDirection, sortActive, pageIndex, pageSize, dateRange) {
        const reportType = 'myTE';
        this.loadingSubject.next(true);
        this._CustomReportService.getMyTEData(filter, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
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

    sortData(data: MytePageVsCountry[], sortDirection, sortActive): MytePageVsCountry[] {
        if (!sortActive || sortDirection === '') {
          return data;
        }

        return data.sort((a, b) => {
          let propertyA: number | string = '';
          let propertyB: number | string = '';

          switch (sortActive) {
            case 'AVG_GENERATION_TIME': [propertyA, propertyB] = [a.AVG_GENERATION_TIME, b.AVG_GENERATION_TIME]; break;
            case 'COUNTRY': [propertyA, propertyB] = [a.COUNTRY, b.COUNTRY]; break;
            case 'PAGE_URL': [propertyA, propertyB] = [a.PAGE_URL, b.PAGE_URL]; break;
          }

          const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
          const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

          return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
