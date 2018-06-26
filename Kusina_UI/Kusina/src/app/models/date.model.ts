import * as moment from 'moment';
interface Serializable<T> {
    deserialize(input: Object, format:string): T;
}
export class DateModel implements Serializable<DateModel> {
    private year: number;
    private month: number;
    private day: number;
    private epoc:number;
    private formatted:string;
    private jsonValue:any;
    private timezone:any;

    public constructor(date:any,format:string) {
      this.deserialize(date,format);
    }
    public getYear():number{
        return this.year;
    }
    public getMonth():number{
        return this.month;
    }
    public getDay():number{
        return this.day;
    }
    public getJsonValue():any{
        return this.jsonValue;
    }
    public getFormatted():string{
        return this.formatted;
    }
    public getEpoc():number{
        return this.epoc*1000;
    }
    public getTimeZone():string{
        return this.timezone;
    }

    public deserialize(input,format) {
        this.year = input.date.year;
        this.month = input.date.month;
        this.day=input.date.day;
        let temp = new Date(input.date.year,input.date.month-1,input.date.day);
        this.epoc=temp.getTime()/1000;
        this.formatted= moment(temp).format(format);
        this.jsonValue=input;
        this.timezone=moment(temp).format("ZZ");
        //console.log("DATE: "+ this.epoc);
        //console.log("TIMEZONE VALUE: "+ this.timezone);
        return this;
    }
    

}
    