import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { AibspBviReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { AibspBviReportService } from '../services/custom-reports/aibspbvi-report.service';

export class AibspBviDataSource extends DataSource<AibspBviReport> {

    private dataSubject = new BehaviorSubject<AibspBviReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    
    totalData;
    dataLength;

    constructor(private _CustomReportService: AibspBviReportService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<AibspBviReport[]> {
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

    loadAibspBviReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        // console.log('test');
        this.loadingSubject.next(true);
            this._CustomReportService.getAibspBviData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false)),
            )
            .subscribe(data => {
                // console.log("THIS IS AIBSPBVI DATASOURCE: "+ data);
                this.totalData = this._CustomReportService.totalData;
                const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
                this.dataLength = Object.keys(data).length;
                this.dataSubject.next(sortedData);
            });
    }


    sortData(data: AibspBviReport[], sortDirection, sortActive): AibspBviReport[] {
        if (!sortActive || sortDirection === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (sortActive) {
                case 'SEGMENT_NAME': [propertyA, propertyB] = [a.SEGMENT_NAME, b.SEGMENT_NAME]; break;
                case 'UNIQUE_VISITOR_COUNT': [propertyA, propertyB] = [a.UNIQUE_VISITOR_COUNT, b.UNIQUE_VISITOR_COUNT]; break;
                case 'HITS': [propertyA, propertyB] = [a.HITS, b.HITS]; break;
                case 'PAGE_URL': [propertyA, propertyB] = [a.PAGE_URL, b.PAGE_URL]; break;
                case 'ACCESS_DATE_TIME': [propertyA, propertyB] = [a.ACCESS_DATE_TIME, b.ACCESS_DATE_TIME]; break;
                case 'USERNAME': [propertyA, propertyB] = [a.USERNAME, b.USERNAME]; break;
                case 'CAREER_LEVEL': [propertyA, propertyB] = [a.CAREER_LEVEL, b.CAREER_LEVEL]; break;
                case 'CAREER_TRACKS': [propertyA, propertyB] = [a.CAREER_TRACKS, b.CAREER_TRACKS]; break;
                case 'GEOGRAPHY': [propertyA, propertyB] = [a.GEOGRAPHY, b.GEOGRAPHY]; break;
                case 'BROWSER': [propertyA, propertyB] = [a.BROWSER, b.BROWSER]; break;
                case 'OS': [propertyA, propertyB] = [a.OS, b.OS]; break;
                case 'ASSET_NAME': [propertyA, propertyB] = [a.ASSET_NAME, b.ASSET_NAME]; break;
                case 'DEVICE': [propertyA, propertyB] = [a.DEVICE, b.DEVICE]; break;
                case 'ASSET_TYPE': [propertyA, propertyB] = [a.ASSET_TYPE, b.ASSET_TYPE]; break;
                case 'INDUSTRY_NAME': [propertyA, propertyB] = [a.INDUSTRY_NAME, b.INDUSTRY_NAME]; break;
                
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
