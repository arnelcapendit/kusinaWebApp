import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { PagesReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { UsageReportService } from '../services/custom-reports/usage-report.service';
import { PageReportService } from '../services/custom-reports/page-report.service';

export class PagesDataSource extends DataSource<PagesReport> {

    private dataSubject = new BehaviorSubject<PagesReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    totalData;
    dataLength;

    constructor(private _CustomReportService: PageReportService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<PagesReport[]> {
        return this.dataSubject.asObservable();
        // const displayDataChanges =  [
        //     this.dataSubject
        //     ];
        // return Observable.merge(...displayDataChanges);
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadPagesReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        // console.log('test');
        this.loadingSubject.next(true);
        if (currentParam !== undefined) {
            // tslint:disable-next-line:max-line-length
            this._CustomReportService.getPageDataByChild(search, sortDirection, pageIndex, pageSize, dateRange, reportType, currentParam, filter).pipe(
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
            this._CustomReportService.getPagesData(search, sortDirection, pageIndex, pageSize, dateRange, reportType, filter).pipe(
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

    sortData(data: PagesReport[], sortDirection, sortActive): PagesReport[] {
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
                case 'PAGE_URL': [propertyA, propertyB] = [a.PAGE_URL, b.PAGE_URL]; break;
                case 'VISITS': [propertyA, propertyB] = [a.VISITS, b.VISITS]; break;
                case 'USERNAME': [propertyA, propertyB] = [a.USERNAME, b.USERNAME]; break;
                case 'CAREER_LEVEL': [propertyA, propertyB] = [a.CAREER_LEVEL, b.CAREER_LEVEL]; break;
                case 'CAREER_TRACKS': [propertyA, propertyB] = [a.CAREER_TRACKS, b.CAREER_TRACKS]; break;
                case 'GEOGRAPHY': [propertyA, propertyB] = [a.GEOGRAPHY, b.GEOGRAPHY]; break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
