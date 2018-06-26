import { Injectable,EventEmitter, ElementRef} from '@angular/core';
import { DateRangeModel } from '../models/daterange.model';
import { DateRangePickerComponent } from '../materials/date-range-picker/date-range-picker.component';

@Injectable()
export class DateRangePickerService {

    private dateRangeModel: DateRangeModel;
    private component: DateRangePickerComponent;

    public init(dateRangeModel:DateRangeModel){
        this.dateRangeModel=dateRangeModel;
    }
    public initComponent(component:DateRangePickerComponent){
        this.component=component;
    }

    public getDateRangeModel():DateRangeModel{
        return this.dateRangeModel;
    }
    public getDateRangeComponent():DateRangePickerComponent{
        return this.component;
    }
    public setDateRangeModel(dateRangeModel:DateRangeModel):void{
        this.dateRangeModel=dateRangeModel;
    }

}