import { Component, OnInit, ViewEncapsulation, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { AppConfig } from '../../../globals/app.config';
import { Router } from '@angular/router';
import { AppProfileService } from '../../../services/app-profile.service';
import { AppProfileModel } from '../../../models/app-profile.model';

@Component({
  selector: 'app-toggle-button',
  templateUrl: './toggle-button.component.html',
  styleUrls: ['./toggle-button.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ToggleButtonComponent implements OnInit {
  public customReportList: Array<string>;
  public categoryValue: string;

  @Output() categoryChange = new EventEmitter();

  @Input()
  get category() {
    return this.categoryValue;
  }

  set category(val) {
    this.categoryValue = val;
    this.categoryChange.emit(this.categoryValue);
  }

  constructor(private _appConfig:AppConfig, private _router:Router,private _appProfile:AppProfileService) {
  }

  ngOnInit() {
    // this.updateCategoryList();
    // consol
          this.updateCategoryList(this._appProfile.getSelectedApp());

  }

  updateCategoryList(option:AppProfileModel){
    if(this._appConfig.SPECIAL_CLIENTS.AIRID_LIST.includes(option.getAirId())){
      this.customReportList = this._appConfig.CUSTOM_REPORTS.CATEGORIES_SPECIAL;
      this.categoryValue = this._appConfig.CUSTOM_REPORTS.CATEGORIES_SPECIAL[0];
    }else{
      this.customReportList = this._appConfig.CUSTOM_REPORTS.CATEGORIES_GLOBAL;
      this.categoryValue = this._appConfig.CUSTOM_REPORTS.CATEGORIES_GLOBAL[0];
    }
    this.categoryChange.emit(this.categoryValue);
  }
  

  // setDropListData(){
  //   this.defaultCustomRpt = this._appConfig.CUSTOM_REPORTS.DEFAULT;
  //   this.customReportList = this._appConfig.CUSTOM_REPORTS.CATEGORIES;
  //   this.rptSelected = this.defaultCustomRpt;
  // }

  // getLength(data){
  //   return data.length;
  // }

  selectedReport(data:string){
    this.categoryValue = data;
    this.categoryChange.emit(this.categoryValue);

    // console.log('this categoryValue', this.categoryValue);
    if(this._router.url === this._appConfig.WEB_PAGES.CUSTOMREPORT_PAGE){
        //this.service.setSelectedCustomReport(data);
    }
  }

}
