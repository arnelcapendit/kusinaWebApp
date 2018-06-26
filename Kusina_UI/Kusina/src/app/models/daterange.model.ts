import {DateModel} from '../models/date.model';

export class DateRangeModel {
    private dayModel: DateModel;
    private customStartModel: DateModel;
    private customEndModel: DateModel;
    private selectedPeriodModel: string;
    private timezone:string;

    public constructor(dayModel:DateModel, customStartModel:DateModel, customEndModel: DateModel, selectedPeriodModel:string) {
      this.dayModel=dayModel;
      this.customStartModel=customStartModel;
      this.customEndModel=customEndModel;
      this.selectedPeriodModel=selectedPeriodModel;
      this.timezone=this.dayModel.getTimeZone();
      
    }
    public getTimezone():string{
        return this.timezone;
    }
    public getDayModel():DateModel{
        return this.dayModel;
    }
    public getCustomStartModel():DateModel{
        return this.customStartModel;
    }
    public getCustomEndModel():DateModel{
        return this.customEndModel;
    }
    public getSelectedPeriodModel():string{
        return this.selectedPeriodModel;
    }

    public setDayModel(dayModel:DateModel):void{
        this.dayModel=dayModel;
    }
    public setCustomStartModel(customStartModel:DateModel):void{
        this.customStartModel=customStartModel;
    }
    public setCustomEndModel(customEndModel:DateModel):void{
        this.customEndModel=customEndModel;
    }
    public setSelectedPeriodModel(selectedPeriodModel:string):void{
        this.selectedPeriodModel=selectedPeriodModel;
    }
    public setAll(dayModel:any, customStartModel:any, customEndModel: any, selectedPeriodModel:string, format:string):void{
        this.dayModel.deserialize(dayModel,format);
        this.customStartModel.deserialize(customStartModel,format);
        this.customEndModel.deserialize(customEndModel,format);
        this.selectedPeriodModel=selectedPeriodModel;
    }

    
    
}