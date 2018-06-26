import { merge } from 'rxjs/observable/merge';
import { of as observableOf, of } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { map } from 'rxjs/operators/map';
import { startWith } from 'rxjs/operators/startWith';
import { switchMap } from 'rxjs/operators/switchMap';
import { DataSource } from '@angular/cdk/collections';
import { MytePageVsCountry, MytePageVsCountryList, Users} from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs';
import { UserAccessService } from '../services/user-access.service';
import { CollectionViewer } from '@angular/cdk/collections';
import { finalize } from 'rxjs/operators';

export class UserAccessDataSource extends DataSource<Users> {
    
    private dataSubject = new BehaviorSubject<Users[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    
    totalData;
    dataLength;

    constructor(
      private _userService: UserAccessService){
        super();
      } 

    connect(collectionViewer: CollectionViewer): Observable<Users[]> {
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

  loadUserAccessReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
    // console.log('test');
    this.loadingSubject.next(true);
        this._userService.getUserAccessData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false)),
        )
        .subscribe(data => {
            // console.log("THIS IS AIBSPBVI DATASOURCE: "+ data);
            this.totalData = this._userService.totalData;
            const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
            this.dataLength = Object.keys(data).length;
            this.dataSubject.next(sortedData);
        });
}

  
  /** Returns a sorted copy of the database data. */
  sortData(data: Users[], sortDirection, sortActive): Users[] {
      if (!sortActive || sortDirection === '') {
          return data;
      }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (sortActive) {
        case 'ACCESS': [propertyA, propertyB] = [a.ACCESS, b.ACCESS]; break;
        case 'ID': [propertyA, propertyB] = [a.ID, b.ID]; break;
        case 'AIRID': [propertyA, propertyB] = [a.AIRID, b.AIRID]; break;
        case 'CREATED_DATE': [propertyA, propertyB] = [a.CREATED_DATE, b.CREATED_DATE]; break;
        case 'EID': [propertyA, propertyB] = [a.EID, b.EID]; break;
        case 'EXPIRY_DATE': [propertyA, propertyB] = [a.EXPIRY_DATE, b.EXPIRY_DATE]; break;
        case 'LAST_UPDATED_BY': [propertyA, propertyB] = [a.LAST_UPDATED_BY, b.LAST_UPDATED_BY]; break;
        case 'LAST_UPDATED_DATE': [propertyA, propertyB] = [a.LAST_UPDATED_DATE, b.LAST_UPDATED_DATE]; break;
        case 'STATUS': [propertyA, propertyB] = [a.STATUS, b.STATUS]; break;
      }

      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
    });
  }
}
