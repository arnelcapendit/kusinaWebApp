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
import { FeedbackService } from '../services/feedback.service';
import { FeedbacksReport } from '../models/api.model';

export class FeedbacksDataSource extends DataSource<FeedbacksReport> {
    private dataSubject = new BehaviorSubject<FeedbacksReport[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();
    totalData;
    dataLength;

    constructor(
      private _feedbackService: FeedbackService){
        super();
      }

    connect(collectionViewer: CollectionViewer): Observable<FeedbacksReport[]> {
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

  loadFeedbacksReport(search, sortDirection, sortActive, pageIndex, pageSize, dateRange, reportType, filter, currentParam) {
    // console.log('test');
    this.loadingSubject.next(true);
        this._feedbackService.getFeedbacksData(search, sortDirection, pageIndex, pageSize, dateRange, reportType).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false)),
        )
        .subscribe(data => {
            // console.log("THIS IS AIBSPBVI DATASOURCE: "+ data);
            this.totalData = this._feedbackService.totalData;
            const sortedData = this.sortData(data.slice(), sortDirection, sortActive);
            this.dataLength = Object.keys(data).length;
            this.dataSubject.next(sortedData);
        });
}

  
  /** Returns a sorted copy of the database data. */
  sortData(data: FeedbacksReport[], sortDirection, sortActive): FeedbacksReport[] {
      if (!sortActive || sortDirection === '') {
          return data;
      }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (sortActive) {
        case 'EID': [propertyA, propertyB] = [a.EID, b.EID]; break;
        case 'AIRID': [propertyA, propertyB] = [a.AIRID, b.AIRID]; break;
        case 'ID': [propertyA, propertyB] = [a.ID, b.ID]; break;
        case 'RATING_MODULE': [propertyA, propertyB] = [a.RATING_MODULE, b.RATING_MODULE]; break;
        case 'RATING_SCORE': [propertyA, propertyB] = [a.RATING_SCORE, b.RATING_SCORE]; break;
        case 'COMMENT': [propertyA, propertyB] = [a.COMMENT, b.COMMENT]; break;
        case 'STATUS': [propertyA, propertyB] = [a.STATUS, b.STATUS]; break;
        case 'CREATED_DATE': [propertyA, propertyB] = [a.CREATED_DATE, b.CREATED_DATE]; break;
        case 'LAST_UPDATE_DATE': [propertyA, propertyB] = [a.LAST_UPDATE_DATE, b.LAST_UPDATE_DATE]; break;
        case 'LAST_UPDATE_BY': [propertyA, propertyB] = [a.LAST_UPDATE_BY, b.LAST_UPDATE_BY]; break;
      }

      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (sortDirection === 'asc' ? 1 : -1);
    });
  }
}
