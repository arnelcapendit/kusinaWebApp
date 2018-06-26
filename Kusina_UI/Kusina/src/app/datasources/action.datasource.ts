import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { EventsReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { ActionReportService } from '../services/custom-reports/action-report.service';

export class ActionDataSource extends DataSource<EventsReport> {

    private dataSubject = new BehaviorSubject<EventsReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    totalData;
    dataLength;

    constructor(private _CustomReportService: ActionReportService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<EventsReport[]> {
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

    loadActionReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        // console.log('test');
        this.loadingSubject.next(true);
            this._CustomReportService.getActionData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)),
            )
            .subscribe(data => {
                // console.log("This is actionreport testing: "+ data);
                this.totalData = this._CustomReportService.totalData;
                const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
                this.dataLength = Object.keys(data).length;
                this.dataSubject.next(sortedData);
            });
    }

    sortData(data: EventsReport[], sortDirection, sortActive): EventsReport[] {
        if (!sortActive || sortDirection === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (sortActive) {
                case 'EVENT_CATEGORIES': [propertyA, propertyB] = [a.EVENT_CATEGORIES, b.EVENT_CATEGORIES]; break;
                case 'EVENT_ACTION': [propertyA, propertyB] = [a.EVENT_ACTION, b.EVENT_ACTION]; break;
                case 'EVENT_NAME': [propertyA, propertyB] = [a.EVENT_NAME, b.EVENT_NAME]; break;
                case 'TOTAL_EVENTS': [propertyA, propertyB] = [a.TOTAL_EVENTS, b.TOTAL_EVENTS]; break;
                case 'TOTAL_VALUE': [propertyA, propertyB] = [a.TOTAL_VALUE, b.TOTAL_VALUE]; break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
