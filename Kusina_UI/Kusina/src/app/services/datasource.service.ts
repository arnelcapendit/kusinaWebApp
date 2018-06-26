import { Injectable } from '@angular/core';
import { AppConfig } from '../globals/app.config';
import { MytePageVsCountryDataSource } from '../datasources/myte-pageVsCountries.datasource';
import { TableModel } from '../models/table.model';
import { TableFilterModel } from '../models/table-filter.model';

@Injectable()
export class DataSourceService {
    table: TableModel | null;
    tableFilter: TableFilterModel;

    constructor( private appConfig: AppConfig) {

    }
    init(table: TableModel) {
        this.table = table;
    }

    getTable() {
        return this.table;
    }




}
