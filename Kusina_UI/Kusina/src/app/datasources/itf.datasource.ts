import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { ITFReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { ItfReportService } from '../services/custom-reports/itf-report.service';

export class ITFDataSource extends DataSource<ITFReport> {

    private dataSubject = new BehaviorSubject<ITFReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    totalData;
    dataLength;

    constructor(private _CustomReportService: ItfReportService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<ITFReport[]> {
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

    loadITFReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        // console.log('test');
        this.loadingSubject.next(true);
            this._CustomReportService.getITFData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)),
            )
            .subscribe(data => {
                // console.log("This is itfreport data: "+ data);
                this.totalData = this._CustomReportService.totalData;
                const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
                this.dataLength = Object.keys(data).length;
                this.dataSubject.next(sortedData);
            });
    }

    sortData(data: ITFReport[], sortDirection, sortActive): ITFReport[] {
        if (!sortActive || sortDirection === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (sortActive) {
                case 'USERNAME': [propertyA, propertyB] = [a.USERNAME, b.USERNAME]; break;
                case 'APPLICATION': [propertyA, propertyB] = [a.APPLICATION, b.APPLICATION]; break;
                case 'USER_ROLE': [propertyA, propertyB] = [a.USER_ROLE, b.USER_ROLE]; break;
                case 'GEOGRAPHY': [propertyA, propertyB] = [a.GEOGRAPHY, b.GEOGRAPHY]; break;
                case 'COUNTRY': [propertyA, propertyB] = [a.COUNTRY, b.COUNTRY]; break;
                case 'CAREER_LEVEL': [propertyA, propertyB] = [a.CAREER_LEVEL, b.CAREER_LEVEL]; break;
                case 'CAREER_TRACKS': [propertyA, propertyB] = [a.CAREER_TRACKS, b.CAREER_TRACKS]; break;
                case 'VISITS': [propertyA, propertyB] = [a.VISITS, b.VISITS]; break;
                case 'HITS': [propertyA, propertyB] = [a.HITS, b.HITS]; break;
                case 'TOTAL_TIME_SPENT': [propertyA, propertyB] = [a.TOTAL_TIME_SPENT, b.TOTAL_TIME_SPENT]; break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
