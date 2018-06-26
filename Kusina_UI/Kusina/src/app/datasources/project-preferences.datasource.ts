import { merge } from 'rxjs/observable/merge';
import { of as observableOf } from 'rxjs/observable/of';
import { catchError } from 'rxjs/operators/catchError';
import { map } from 'rxjs/operators/map';
import { startWith } from 'rxjs/operators/startWith';
import { switchMap } from 'rxjs/operators/switchMap';
import { DataSource } from '@angular/cdk/collections';
import { MytePageVsCountry, MytePageVsCountryList, ProjectPreferences} from '../models/api.model';
import { CustomReportService } from '../services/custom-report.service';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs';
import { UserAccessService } from '../services/user-access.service';

export class ProjectPreferencesDataSource extends DataSource<ProjectPreferences> {
    resultsLength = 0;
    isLoadingResults = false;
    isRateLimitReached = false;
    filteredData: ProjectPreferences[] = [];
    renderedData: ProjectPreferences[] = [];
    _filterChange = new BehaviorSubject('');
    exportURL:string ="";

    constructor(
      private _userService: UserAccessService, 
      private _paginator: MatPaginator, 
      private _sort: MatSort) {
      super();
     this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
    }

    get filter(): string {
      return this._filterChange.value;
    }

    set filter(filter: string) {
      this._filterChange.next(filter);
    }

    connect(): Observable<ProjectPreferences[]> {
      this.isLoadingResults = true;
      const displayDataChanges = [
        this._userService.dataProjectPreferences,
        this._sort.sortChange,
        this._filterChange,
        this._paginator.page
      ];
      this._userService.getProjectPreferences();
      this.exportURL=this._userService.exportURL;

     return Observable.merge(...displayDataChanges).map(() => {
      // Filter data
      this.filteredData = this._userService.dataProjPreferences.slice().filter((issue: ProjectPreferences) => {
        // console.log('12345', issue);
        const searchStr = (
            issue.air_id +
            issue.default_dashboard_id +
            issue.start_date +
            issue.end_date +
            issue.timezone +
            issue.interval + 
            issue.theme +
            issue.default_results_count
        ).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
      });

      // Sort filtered data
      const sortedData = this.sortData(this.filteredData.slice());

      // Grab the page's slice of the filtered sorted data.
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      this.renderedData = sortedData.splice(startIndex, this._paginator.pageSize);
      this.isLoadingResults = false;
      return this.renderedData;
    });

    }

    disconnect() {}

  /** Returns a sorted copy of the database data. */
  sortData(data: ProjectPreferences[]): ProjectPreferences[] {
    if (!this._sort.active || this._sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'air_id': [propertyA, propertyB] = [a.air_id, b.air_id]; break;
      }

      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1);
    });
  }
}
