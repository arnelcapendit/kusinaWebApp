import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { DataSource } from '@angular/cdk/collections';
import { ProfileReport } from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { Observable } from 'rxjs/Observable';
// tslint:disable-next-line:import-blacklist
import { BehaviorSubject } from 'rxjs';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';
import { AnnouncementsService } from '../services/announcement.service';
import { ProfileService } from '../services/profile.service';

export class ProfileDataSource extends DataSource<ProfileReport> {

    private dataSubject = new BehaviorSubject<ProfileReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    totalData;
    dataLength;

    constructor(private profileService: ProfileService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<ProfileReport[]> {
        return this.dataSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.dataSubject.complete();
        this.loadingSubject.complete();
    }

    loadProfileReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
        this.loadingSubject.next(true);

        this.profileService.getProfileData(search, pageIndex, pageSize, reportType).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false)),
        )
        .subscribe(data => {
            // console.log(data);
            this.totalData = this.profileService.totalData;
            const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
            this.dataLength = Object.keys(data).length;
            this.dataSubject.next(sortedData);
        });
    }


    sortData(data: ProfileReport[], sortDirection, sortActive): ProfileReport[] {
        if (!sortActive || sortDirection === '') {
            return data;
        }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            switch (sortActive) {
                case 'AIRID': [propertyA, propertyB] = [a.AIRID, b.AIRID]; break;
                case 'APP_NAME': [propertyA, propertyB] = [a.APP_NAME, b.APP_NAME]; break;
                case 'CREATED_DATE': [propertyA, propertyB] = [a.CREATED_DATE, b.CREATED_DATE]; break;
                case 'LAST_UPDATE_BY': [propertyA, propertyB] = [a.LAST_UPDATE_BY, b.LAST_UPDATE_BY]; break;
                case 'LAST_UPDATE_DATE': [propertyA, propertyB] = [a.LAST_UPDATE_DATE, b.LAST_UPDATE_DATE]; break;
                case 'SITE_ID': [propertyA, propertyB] = [a.SITE_ID, b.SITE_ID]; break;
            }

            const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
        });
    }
}
