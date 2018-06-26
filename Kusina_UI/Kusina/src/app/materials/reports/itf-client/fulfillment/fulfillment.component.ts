import { Component, OnInit, ViewChild } from '@angular/core';
import { TableFilterComponent } from '../../../table-filter/table-filter.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';

@Component({
  selector: 'app-fulfillment',
  templateUrl: './fulfillment.component.html',
  styleUrls: ['./fulfillment.component.css']
})
export class FulfillmentComponent implements OnInit {

  displayedColumns = [];
  currentReport: string;
  searchPlaceholder: string;
  reportType: string;
  filter: string;

  @ViewChild('fulfillment') public tableFilter: TableFilterComponent;
  constructor(
    private _tableService: DataSourceService, 
    private _appConfig: AppConfig) { }

  ngOnInit() {
    this.displayedColumns = this._appConfig.COLUMNS.ITF;
    this.currentReport = this._appConfig.CUSTOM_REPORTS.ITF_TAB.FULFILLMENT_CONNECT_USAGE;
    this.searchPlaceholder = this._appConfig.SEARCH_PLACEHOLDER.ITF;
    this.reportType = this._appConfig.REPORT_TYPE.FULFILLMENT;
    this.filter = this._appConfig.FILTER.PAGES_CUSTOM_INFO;
    this._tableService.init(this.tableFilter);
  }
}
