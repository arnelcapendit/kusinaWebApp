import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { AnnouncementsReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { AnnouncementsService } from '../services/announcement.service';
import { NotificationService } from '../services/notification.service';

export class NotificationsDataSource extends DataSource<AnnouncementsReport> {

    private dataSubject = new BehaviorSubject<AnnouncementsReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    totalData;
    dataLength;

    constructor(private notificationService: NotificationService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<AnnouncementsReport[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadAnnouncementReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        this.loadingSubject.next(true);

        this.notificationService.getAnnouncementsData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false)),
        )
        .subscribe(data => {
            //console.log(data);
            this.totalData = this.notificationService.totalData;
            const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
            this.dataLength = Object.keys(data).length;
            this.dataSubject.next(sortedData);
        });
    }


    sortData(data: AnnouncementsReport[], sortDirection, sortActive): AnnouncementsReport[] {
        if (!sortActive || sortDirection === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (sortActive) {
                case 'CONTENT': [propertyA, propertyB] = [a.CONTENT, b.CONTENT]; break;
                case 'DUE_DATE': [propertyA, propertyB] = [a.DUE_DATE, b.DUE_DATE]; break;
                case 'STATUS': [propertyA, propertyB] = [a.STATUS, b.STATUS]; break;
                case 'TITLE': [propertyA, propertyB] = [a.TITLE, b.TITLE]; break;
                case 'TOTAL_READ_COUNT': [propertyA, propertyB] = [a.TOTAL_READ_COUNT, b.TOTAL_READ_COUNT]; break;
                case 'TYPE': [propertyA, propertyB] = [a.TYPE, b.TYPE]; break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
