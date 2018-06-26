import { Component, OnInit } from '@angular/core';
import { AppConfig } from '../../globals/app.config';
import { UsePiwikTracker } from 'angular2piwik';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';

@Component({
  selector: 'app-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent implements OnInit {

  constructor(  
    private _appConfig: AppConfig,
    private _usePiwikTracker: UsePiwikTracker,
    private _globalFilterService: GlobalFilterService
  ) { }

  ngOnInit() {
    //const url = this._appConfig.getActiveTracker() + this._appConfig.WEB_PAGES;
    //this._usePiwikTracker.setPageAndTrack(url, 'Inframetrics - Error');
  }

}
