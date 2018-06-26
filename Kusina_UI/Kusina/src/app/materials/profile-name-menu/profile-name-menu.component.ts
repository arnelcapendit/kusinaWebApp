import { Component, OnInit, Input, Inject, ElementRef, Renderer, ViewChild, EventEmitter, Directive } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';
import { FormControl } from '@angular/forms';
import { ViewEncapsulation } from '@angular/core';
import { DateRangePickerService } from '../../services/daterange.service';
import { AppProfileService } from '../../services/app-profile.service';
import { AppProfileModel } from '../../models/app-profile.model';
import { ChartService } from '../../services/chart.service';
import { Router } from '@angular/router';
import { AppConfig } from '../../globals/app.config';
import { DataSourceService } from '../../services/datasource.service';
import { ReportCategoryService } from '../../services/report-category.service';

@Component({
  selector: 'app-profile-name-menu',
  templateUrl: './profile-name-menu.component.html',
  styleUrls: ['./profile-name-menu.component.css']
})
export class ProfileNameMenuComponent implements OnInit {
  myControl: FormControl = new FormControl();
  options: Array<AppProfileModel> = [];
  searchResult = [];
  filteredOptions: Observable<AppProfileModel[]>;
  temp: Array<AppProfileModel>;
  selectedAppModel: AppProfileModel;
  selectedApp: string;
  styleWidth;
  styleOverflow = 'hidden';

  constructor(
    private _daterange: DateRangePickerService,
    public _appProfileService: AppProfileService,
    private _chartService: ChartService,
    private _router: Router,
    private _appConfig: AppConfig,
    private _tableService: DataSourceService,
    private _toggleButtonService: ReportCategoryService
  ) {

  }
  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges.startWith('').map(val => val ? this.filter(val) : this.options.slice());
  }
  public setOptions(options) {
    this.options = options;
  }
  public setSelectedApp(selection: AppProfileModel) {
    this.selectedAppModel = selection;
    this.selectedApp = this.selectedAppModel.getFormattedValue();
  }
  public hasNoSelectedApp(): boolean {
    return !this.selectedAppModel ? true : false;
  }
  dropAllApp() {
    this.myControl.reset();
    this.filteredOptions =  this.myControl.valueChanges.startWith('').map(val => val ? this.filter(val) : this.options.slice());
  }
  resetInput() {
    const temp = this.myControl.value;
    this.myControl.valueChanges
    .pipe(
      startWith(''),
      map(val => this.filter(val))
    );
  }
  filter(val: string): AppProfileModel[] {
    return this.options.filter(app => app.getFormattedValue().toLowerCase().indexOf(val.toLowerCase()) === 0 );
  }
  removeFocus() {
    this.myControl.disable();
    this.myControl.enable();
  }
  selectApp(option: AppProfileModel) {
    this.removeFocus();
    this.selectedAppModel = option;
    this.selectedApp = option.getFormattedValue();
    this._appProfileService.setSelectedApp(option);
    if (this._router.url === this._appConfig.WEB_PAGES.DASHBOARD_PAGE) {
      this._chartService.updateAllCharts();
    }else if (this._router.url === this._appConfig.WEB_PAGES.CUSTOMREPORT_PAGE) {
      this._tableService.getTable().refresh();
      this._toggleButtonService.getToggleButton().updateCategoryList(option);
    }

  }
}
