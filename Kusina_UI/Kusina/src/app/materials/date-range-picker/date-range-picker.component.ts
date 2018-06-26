import { Component, OnInit, ElementRef, ViewChild, EventEmitter, Directive } from '@angular/core';
import { DateRangePickerService } from '../../services/daterange.service';
import { AppConfig } from '../../globals/app.config';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DateRangePickerDialogueComponent } from './date-range-picker-dialogue.component';
import { DateRangeModel } from '../../models/daterange.model';
import { ChartService } from '../../services/chart.service';
import { Router } from '@angular/router';
import { CustomReportService } from '../../services/custom-report.service';
import { DataSourceService } from '../../services/datasource.service';

@Component({
  selector: 'app-date-range-picker',
  templateUrl: './date-range-picker.component.html',
  styleUrls: ['./date-range-picker.component.css']
})
export class DateRangePickerComponent implements OnInit {
  public startDateModel: string;
  public endDateModel: string;
  public isHidden: boolean = false;

  constructor(
    private _dateRangeService: DateRangePickerService,
    private _appConfig: AppConfig,
    public dialog: MatDialog,
    private _chartService: ChartService,
    private _router: Router,
    private _customReportService: CustomReportService,
    private _dataSourceService: DataSourceService) { }

  public ngOnInit() {
    this.updateCalendarButton();
  }

  public updateCalendarButton() {
    if (this._dateRangeService.getDateRangeModel().getSelectedPeriodModel() === this._appConfig.DATERANGEPICKER.PERIODS.DAY) {
      this.startDateModel = this._dateRangeService.getDateRangeModel().getDayModel().getFormatted();
      this.endDateModel = this._dateRangeService.getDateRangeModel().getDayModel().getFormatted();
    } else {
      this.startDateModel = this._dateRangeService.getDateRangeModel().getCustomStartModel().getFormatted();
      this.endDateModel = this._dateRangeService.getDateRangeModel().getCustomEndModel().getFormatted();
    }
  }

  public openDialog(): void {
    const dialogRef = this.dialog.open(DateRangePickerDialogueComponent, {
      width: 'auto'
      // data: { name: this.name, animal: this.animal }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (this._router.url === this._appConfig.WEB_PAGES.DASHBOARD_PAGE) {
        this._chartService.updateAllCharts();
      }else if (this._router.url === this._appConfig.WEB_PAGES.CUSTOMREPORT_PAGE) {
        this._dataSourceService.getTable().refresh();
      }
      this.updateCalendarButton();
    });
  }


}
