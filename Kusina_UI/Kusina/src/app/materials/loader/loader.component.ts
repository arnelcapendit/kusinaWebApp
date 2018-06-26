import { Component, OnInit } from '@angular/core';
import { CustomReportService } from '../../services/custom-report.service';


@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent implements OnInit {

    isShowLoader: boolean;

    // Loader Settings
    color = 'warn';
    mode =  'indeterminate';
    value = 0;
    bufferValue = 0;



    constructor(private _customReportService: CustomReportService) { }

    ngOnInit() {
        this.isShowLoader = false;
    }

    public getIsShowLoader(): boolean {
        return this.isShowLoader = this._customReportService.loadingData;
    }

}
