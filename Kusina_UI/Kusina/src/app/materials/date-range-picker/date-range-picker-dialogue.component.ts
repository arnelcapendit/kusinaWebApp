import { Component, Inject, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IMyDpOptions,IMyDateModel,IMyDate } from 'mydatepicker';
import { DateRangeModel } from '../../models/daterange.model';
import { DateRangePickerService } from '../../services/daterange.service';
import { AppConfig } from '../../globals/app.config';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { DateModel } from '../../models/date.model';

@Component({
    selector: 'date-range-picker-dialogue',
    templateUrl: './date-range-picker-dialogue.html',
    styleUrls: ['./date-range-picker-dialogue.component.css']
  })
export class DateRangePickerDialogueComponent implements OnInit{
    public optionForDay: IMyDpOptions;
    public optionForCustomStart: IMyDpOptions;
    public optionForCustomEnd: IMyDpOptions;
    public periodDayVisibility: boolean;
    public periodCustomVisibility: boolean;
    public periods:Array<string>;
    public myForme: FormGroup;

    public dayModel:any;
    public customStartModel:any;
    public customEndModel:any;
    public selectedPeriodModel:string;
    public dateNow: Date;

    public sample;

    
    public constructor(public dialogRef: MatDialogRef<DateRangePickerDialogueComponent>,@Inject(MAT_DIALOG_DATA) public data: any, private _dateRangeService:DateRangePickerService, private _appConfig:AppConfig, private formBuilder: FormBuilder) { 
    }
    public ngOnInit(){
        this.dateNow = new Date();
         
        this.dayModel = this._dateRangeService.getDateRangeModel().getDayModel().getJsonValue();
        this.customStartModel = this._dateRangeService.getDateRangeModel().getCustomStartModel().getJsonValue();
        this.customEndModel = this._dateRangeService.getDateRangeModel().getCustomEndModel().getJsonValue();
        this.selectedPeriodModel=this._dateRangeService.getDateRangeModel().getSelectedPeriodModel();

        this.optionForDay = {
            dateFormat: this._appConfig.DATEPICKEROPTIONS.CALENDARFORMAT,
            inline:this._appConfig.DATEPICKEROPTIONS.INLINE,
            showInputField:this._appConfig.DATEPICKEROPTIONS.SHOWINPUTFIELD,
            selectorHeight:this._appConfig.DATEPICKEROPTIONS.SELECTORHEIGHT,
            selectorWidth:this._appConfig.DATEPICKEROPTIONS.SELECTORWIDTH,
            maxYear: this.dateNow.getUTCFullYear(),
            minYear: this.dateNow.getUTCFullYear() - 1,
            // disableSince:  {year: this.dateNow.getUTCFullYear(), month:this.dateNow.getMonth()+1, day:this.dateNow.getDate()+1},
            // disableUntil:  this.compute()
        }
        this.optionForCustomStart = {
            dateFormat: this._appConfig.DATEPICKEROPTIONS.CALENDARFORMAT,
            inline:this._appConfig.DATEPICKEROPTIONS.INLINE,
            showInputField:this._appConfig.DATEPICKEROPTIONS.SHOWINPUTFIELD,
            selectorHeight:this._appConfig.DATEPICKEROPTIONS.SELECTORHEIGHT,
            selectorWidth:this._appConfig.DATEPICKEROPTIONS.SELECTORWIDTH,
            maxYear: this.dateNow.getUTCFullYear(),
            minYear: this.dateNow.getUTCFullYear()-1,
            // disableSince:  {year: this.dateNow.getUTCFullYear(), month: this.dateNow.getMonth() + 1, day: this.dateNow.getDate() + 1},
            // disableUntil:  this.compute()
        }
        this.optionForCustomEnd = {
            dateFormat: this._appConfig.DATEPICKEROPTIONS.CALENDARFORMAT,
            inline:this._appConfig.DATEPICKEROPTIONS.INLINE,
            showInputField:this._appConfig.DATEPICKEROPTIONS.SHOWINPUTFIELD,
            selectorHeight:this._appConfig.DATEPICKEROPTIONS.SELECTORHEIGHT,
            selectorWidth:this._appConfig.DATEPICKEROPTIONS.SELECTORWIDTH,
            maxYear: this.dateNow.getUTCFullYear(),
            minYear: this.dateNow.getUTCFullYear()-1,
            // disableSince:  {year: this.dateNow.getUTCFullYear(), month:this.dateNow.getMonth()+1, day:this.dateNow.getDate()+1},
            // disableUntil: this.compute()
        }
        this.periods = [this._appConfig.DATERANGEPICKER.PERIODS.DAY,this._appConfig.DATERANGEPICKER.PERIODS.CUSTOM];
        this.periodCustomVisibility = true;
        this.periodDayVisibility = false
    }
    public computeYear(start,gap,year){
        var y = year;
        if(gap>start){
            y--;
        }
        return y;
    }

    public compute():IMyDate{
        var y = this.dateNow.getUTCFullYear();
        var m = this.dateNow.getMonth()+1;
        var d = 30;
        var retention = 6;

        if (retention>=m){
            y=y-1;
            m = 12-(retention-m);
        }else{
            m=m-retention;
        }
        
        if([1,3,5,7,8,10,12].includes(m)){
            d=31
        }else if(m==2){
            if(y%4==0){
                d=29
            }
            else{
                d=28
            }
        }
        return {year: y, month: m, day: d};
    }
    public configureDatepicker(){
        if(this.selectedPeriodModel==this._appConfig.DATERANGEPICKER.PERIODS.DAY){
            this.periodCustomVisibility=true;
            this.periodDayVisibility=false;
        }else{
            this.periodCustomVisibility=false;
            this.periodDayVisibility=true;
        }
    }
    public cancelChanges(): void {
        this.dialogRef.close();
    }
    public applyChanges():void{
        this._dateRangeService.getDateRangeModel().setAll(this.dayModel,this.customStartModel,this.customEndModel,this.selectedPeriodModel,this._appConfig.DATEPICKEROPTIONS.MOMENTFORMAT);
        this.dialogRef.close();
    }
    onStartDateChanged(event: IMyDateModel) {
        this.optionForCustomEnd = {
            dateFormat: this._appConfig.DATEPICKEROPTIONS.CALENDARFORMAT,
            inline:this._appConfig.DATEPICKEROPTIONS.INLINE,
            showInputField:this._appConfig.DATEPICKEROPTIONS.SHOWINPUTFIELD,
            selectorHeight:this._appConfig.DATEPICKEROPTIONS.SELECTORHEIGHT,
            selectorWidth:this._appConfig.DATEPICKEROPTIONS.SELECTORWIDTH,
            maxYear: this.dateNow.getUTCFullYear(),
            minYear: this.dateNow.getUTCFullYear()-1,
            disableUntil:  {year: event.date.year, month:event.date.month, day:event.date.day-1}
        }
        
        if(event.epoc > this.customEndModel.epoc){
            this.customEndModel.date.day=event.date.day;
        }
    }
    onEndDateChanged(event: IMyDateModel) {
        if(event.epoc < this.customStartModel.epoc){
            this.customStartModel.date.day=event.date.day;
        }
    }
  }