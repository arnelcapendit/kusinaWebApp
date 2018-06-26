import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { OverviewReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { OverviewReportService } from '../services/custom-reports/overview-report.service';

export class OverviewDataSource extends DataSource<OverviewReport> {

    private dataSubject = new BehaviorSubject<OverviewReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    totalData;
    dataLength;

    constructor(private _CustomReportService: OverviewReportService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<OverviewReport[]> {
        // return this.dataSubject.asObservable();
        const displayDataChanges =  [
            this.dataSubject
            ];
        return Observable.merge(...displayDataChanges);
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadOverviewReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        // console.log('overview testing');
        this.loadingSubject.next(true);

        if (currentParam !== undefined) {
            // tslint:disable-next-line:max-line-length
            this._CustomReportService.getOverviewDataByChild(search, sortDirection, pageIndex, pageSize, dateRange, reportType, currentParam).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)),
            )
            .subscribe(data => {
                //console.log("THIS IS OVERVIEW DATASOURCE CHILD");
                this.totalData = this._CustomReportService.totalData;
                const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
                this.dataLength = Object.keys(data).length;
                this.dataSubject.next(sortedData);
            });
        }else {
            this._CustomReportService.getOverviewData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)),
            )
            .subscribe(data => {
                // console.log("THIS IS OVERVIEW DATASOURCE: "+ data);
                this.totalData = this._CustomReportService.totalData;
                const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
                this.dataLength = Object.keys(data).length;
                this.dataSubject.next(sortedData);
            });
        }
    }


    sortData(data: OverviewReport[], sortDirection, sortActive): OverviewReport[] {
        if (!sortActive || sortDirection === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (sortActive) {
                case 'VISITS': [propertyA, propertyB] = [a.VISITS, b.VISITS]; break;
                case 'UNIQUE_VISITORS': [propertyA, propertyB] = [a.UNIQUE_VISITORS, b.UNIQUE_VISITORS]; break;
                case 'HITS': [propertyA, propertyB] = [a.HITS, b.HITS]; break;
                case 'PAGE_URL': [propertyA, propertyB] = [a.PAGE_URL, b.PAGE_URL]; break;
                case 'PAGEVIEWS': [propertyA, propertyB] = [a.PAGEVIEWS, b.PAGEVIEWS]; break;
                case 'USERNAME': [propertyA, propertyB] = [a.USERNAME, b.USERNAME]; break;
                case 'CAREER_LEVEL': [propertyA, propertyB] = [a.CAREER_LEVEL, b.CAREER_LEVEL]; break;
                case 'CAREER_TRACKS': [propertyA, propertyB] = [a.CAREER_TRACKS, b.CAREER_TRACKS]; break;
                case 'GEOGRAPHY': [propertyA, propertyB] = [a.GEOGRAPHY, b.GEOGRAPHY]; break;
                case 'COUNTRY': [propertyA, propertyB] = [a.COUNTRY, b.COUNTRY]; break;
                case 'REFERRER_URL': [propertyA, propertyB] = [a.REFERRER_URL, b.REFERRER_URL]; break;
                case 'AVG_VISIT': [propertyA, propertyB] = [a.AVG_VISIT, b.AVG_VISIT]; break;
                case 'AVG_VISIT_DURATION': [propertyA, propertyB] = [a.AVG_VISIT_DURATION, b.AVG_VISIT_DURATION]; break;
                case 'AVG_PAGES_PER_VISIT': [propertyA, propertyB] = [a.AVG_PAGES_PER_VISIT, b.AVG_PAGES_PER_VISIT]; break;
                case 'HITS': [propertyA, propertyB] = [a.HITS, b.HITS]; break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
