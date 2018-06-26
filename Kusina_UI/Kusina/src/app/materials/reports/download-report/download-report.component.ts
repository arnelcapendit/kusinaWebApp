import { Component, OnInit, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { ExportService } from '../../../services/export.service';

@Component({
  selector: 'app-download-report',
  templateUrl: './download-report.component.html',
  styleUrls: ['./download-report.component.css']
})
export class DownloadReportComponent implements OnInit {

  private exportURL: string;
  private report_type: string;
  private filter_data: string;
  private currentReport: string;
  private dataCount: number;

  constructor(private snackBar: MatSnackBar, private exportService: ExportService ) { }

  ngOnInit() {}

  @Input()
  set url(url: string) {
    this.exportURL = url;
  }

  @Input()
  set report(val) {
      this.currentReport = val;
  }

  @Input()
  set count(count: number) {
    this.dataCount = count;
  }

  @Input()
  set reportType(val) {
    this.report_type = val;
  }

  @Input()
  set filterData(val) {
    this.filter_data = val;
  }

  isDisabled() {
    return this.dataCount > 0 ? false : true;
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }


  exportFile() {
      this.exportService.getExportData(this.report_type, this.filter_data);
      const uri = this.exportService.exportURL;
      console.log(uri);
      this.openSnackBar('Now Exporting...', this.currentReport);
      setTimeout(function(){  window.open(uri, '_blank'); }, 100);
  }

}
