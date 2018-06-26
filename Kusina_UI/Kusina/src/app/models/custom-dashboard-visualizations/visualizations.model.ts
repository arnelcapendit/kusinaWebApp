import { SimpleChanges, OnChanges, Input } from "@angular/core";
import { Chart } from "angular-highcharts";

export class MetricsVisualization {
    id:number =Math.random();
    title:string ="Metric Title";
    value:string ="0";
    background:string = "#ffffff";
    foreground:string = "#000000";
    subtitle:string = "subtitle";
    conditions:ConditionalFormat []=[];
    constructor(){ }
}

export class ConditionalFormat {
    id:number=Math.random();
    background:string = "#ffffff";
    compareTo:string = "0";
    logic:string='=';
    constructor(){ }
}

export class CardsVisualization {
    id:string = Math.random().toString();
    style:string;
    value:string ="Card Value";
    children: CardsVisualization[] = [];
    constructor(){ }

    set styleModel(style:string){
        try {
            this.style=JSON.parse(style);    
        } catch (error) {
            
        }
    }

}
export class MarkdownVisualizationModel {
    id:number = Math.random();
    content:string;
}

export class HighchartVisualizationModel{
    id:number = Math.random();
    chart:ChartModel;
    constructor(type:string,title:string,series:SeriesModel[]){
        this.chart=new ChartModel(type,title,series);
    }
}

export class ChartModel{
    private type:string = 'line';
    private title:string = 'my linehcart';
    private series:SeriesModel[] = [];
    private chartOptions:Highcharts.Options;

    constructor(type:string,title:string,series:SeriesModel[]){
        this.type=type;
        this.title=title;
        this.chartOptions = {
            chart: {
              type: this.type
            },
            title: {
              text: this.title
            },
            credits: {
              enabled: false
            }
            
          };

    }
    getSeries(data:SeriesModel[]){
        var obj = [];
        data.forEach(element => {
            obj.push(element.getObject());
        });
        return obj;
    }
    getChartOptions(){
        return this.chartOptions;
    }
    getChart(){
        var chart:Chart=null;
        var series:Highcharts.SeriesOptions=null;
        try{
            chart = new Chart(this.chartOptions);
           // chart.
           series.data=[1,2,3];
           series.name="title";
           
            chart.addSerie(series);
            
    
        }catch(e){
            //handle null options
        }
       
        return chart;
    }
}

export class SeriesModel{
    name:string;
    data:number[] = [];
    constructor(name:string,data:number[]){
        this.name= name;
        this.data=data;
    }
    getObject():Highcharts.IndividualSeriesOptions{
        return {"name":"e","data":this.data}
    }
}