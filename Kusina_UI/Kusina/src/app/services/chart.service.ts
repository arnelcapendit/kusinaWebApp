import { Injectable,ViewChild } from '@angular/core';
import { Chart,Highcharts } from 'angular-highcharts';
import { HttpClient, HttpHeaders, HttpResponse, HttpInterceptor} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { HighchartComponent } from '../materials/highchart/highchart.component';
import { MetricComponent } from '../materials/metric/metric.component';
import { AppConfig } from '../globals/app.config';
import { UserModel } from '../models/user.model';
import { DateRangeModel } from '../models/daterange.model';
import { AppProfileModel } from '../models/app-profile.model';
import { MsgHandlerService } from  '../services/msg-handler.service';
import { AppProfileService } from  '../services/app-profile.service';
import { DateRangePickerService } from  '../services/daterange.service';
//import { OnlyLoggedInUsersGuard } from  '../services/login-guard.service';
//import { ApiDataHandlerService } from '../services/api-data-handler.service';

//import { ComponentsDataHandlerService } from '../services/components-data-handler.service';

//import { ProfileNameMenuComponent} from '../materials/dashboard-profile-name-menu/profile-name-menu.component';
//import { NavbarComponent} from '../materials/app-navigation-toolbar/navbar.component';

//import { ErrorpageComponent } from '../pages/errorpage/errorpage.component';

@Injectable()
export class ChartService {
  //private errorPage: ErrorpageComponent;

  loadingData: boolean;

  private visitsOvertimeChart: HighchartComponent;
  private topPages: HighchartComponent;
  private visitLocation: HighchartComponent;
  private browserType: HighchartComponent;
  private deviceType: HighchartComponent;
  private metricComponent: MetricComponent;

 // private appFilterList: ProfileNameMenuComponent;
  //private appNavBar: NavbarComponent;
 

  private visualizationData:any;

    private selectedStartDateEpoc:number;
    private user_timezone:string;
    private selectedEndDateEpoc:number;
    private selectedApplication:AppProfileModel;
    private activeUser: UserModel;

  /****** chart data params */
    private topCountryNameArr = new Array();
    private topCountryCountArr = new Array();;
    private topPageNameArr = new Array();
    private topPageCountArr = new Array();
    private browserTypeJsonArr = new Array();
    private deviceTypeJsonArr = new Array();
    private visitOvertimeNameArr = new Array();
    private visitOvertimeVisitsArr = new Array();
    private visitOvertimeUserArr = new Array();;

    private appListArray: Array<any> = [
        {id: '1055', airid: '2700', profileName: 'MyTimeandExpenses'},
        {id: '1056', airid: '1720', profileName: 'myLearning'},
        {id: '1057', airid: '1964', profileName: 'myScheduling'},
        {id: '1058', airid: '1064', profileName: 'myHoldings'}
    ];

    constructor(private _appConfig: AppConfig, private _http: HttpClient, private _msgHandler:MsgHandlerService, private _daterangeService:DateRangePickerService, private _appProfileService:AppProfileService){ 
    }

    init(appProfile:AppProfileModel,dateRange:DateRangeModel){
        this.selectedApplication=appProfile;
        this.setDate(dateRange);
    }

    visitOvertimeChartData:Highcharts.Options = {
        chart:{
            height:180,
            style: {
                fontFamily: 'Open Sans Light'
            }
        },
        credits: {
            enabled: false
        },
        title: {
            text: 'Visits Over Time'
        }, 
        xAxis: {
            categories: this.visitOvertimeNameArr,
            title: {
                text: null
            }
        },
        yAxis: {
            title: {
                text: 'Unique Count'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        series: [{
        name: 'Unique Visits',
        data: this.visitOvertimeVisitsArr
        }, {
            name: 'Unique Users',
            data: this.visitOvertimeUserArr
        }],
    };
    topPagesChartData:Highcharts.Options = {
        chart: {
            type: 'bar',
            height: 250,
            style: {
            fontFamily: 'Open Sans Light'
            }
        },
        title: {
            align: "center",
            text: 'Top 10 Visited Pages'
    
        },
        xAxis: {
        // categories: ['mylearning.accenture.com', 'myte.accenture.com', 'myScheduling.accenture.com', 'myHoldings.accenture.com', 'buhayaccenture.accenture.com'],
            categories: this.topPageNameArr,
            title: {
            text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
            // text: 'Population (millions)',
            // align: 'high'
            },
            labels: {
            overflow: 'justify'
            }
        },
        tooltip: {
            // valueSuffix: ' millions'
        },
        plotOptions: {
            bar: {
            dataLabels: {
                enabled: true
            }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'bottom',
            x: -40,
            y: 80,
            floating: true,
            borderWidth: 1,
            backgroundColor: (
            // (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || 
            '#FFFFFF'),
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            data: this.topPageCountArr
            // data: [222, 87, 788, 711, 345]
        }
        ]
    };
    visitLocationChartData:Highcharts.Options = {
        chart: {
            type: 'bar',
            height: 250,
            style: {
            fontFamily: 'Open Sans Light'
            }
        },
        title: {
            align: "center",
            text: 'Top 10 Visits Location'
    
        },
        subtitle: {
            // text: 'Source: <a href="https://en.wikipedia.org/wiki/World_population">Wikipedia.org</a>'
        },
        xAxis: {
        // categories: ['Philippines', 'Germany', 'China', 'India', 'Argentina'],
            categories: this.topCountryNameArr,
            title: {
            text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
            // text: 'Population (millions)',
            // align: 'high'
            },
            labels: {
            overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: ' '
        },
        plotOptions: {
            bar: {
            dataLabels: {
                enabled: true
            },
            showInLegend: true,
            selected: true,
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'bottom',
            x: -40,
            y: 80,
            floating: true,
            borderWidth: 1,
            backgroundColor: (
            // (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || 
            '#FFFFFF'),
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            data: this.topCountryCountArr
        // data: [635, 31, 107, 203, 12]
        }],
        
    };
    browserTypeChartData:Highcharts.Options ={
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
            height: 250,
            style: {
                fontFamily: 'Open Sans Light'
            }
        },
        title: {
            text: 'Top 10 Used Browsers',
            align : "center",
            margin : 0
        },
        tooltip: {
            // pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        credits: {
            enabled: false
            },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        // color: (Chart.theme && Chart.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        noData: {
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#303030'
            }},
        series: [{
            name: 'Brands',
            // data: [
            //     { name: 'Microsoft Internet Explorer', y: 56.33 },
            //     { name: 'Chrome', y: 24.03 },
            //     { name: 'Firefox', y: 10.38 },
            //     { name: 'Safari', y: 4.77 },
            //     { name: 'Opera', y: 0.91 },
            //     { name: 'Proprietary or Undetectable', y: 0.2 }
            // ]
            data: this.browserTypeJsonArr
        }]
    };
    deviceTypeChartData:Highcharts.Options = {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: null,
            type: 'pie',
            height: 250,
            style: {
            fontFamily: 'Open Sans Light'
            }
        },
        legend:{
            enabled: false
        },
        title: {
            align: "center",
            text: 'Top 10 Used Devices'
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Device',
            // data: [
            //   {name: 'Laptop',y: 40.35}, 
            //   {name: 'Desktop', y: 30.96}, 
            //   {name: 'Mobile', y: 16.9}, 
            //   {name: 'Tablet', y: 10.79},
            //   {name: 'Uknown', y: 1},
            // ]
            data: this.deviceTypeJsonArr
        
        }],
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                enabled: true        
                },
                showInLegend: true,
                selected: true,
                
            }
        },
        colors: ['#c2e6ff','#91d2ff','#79c8ff','#5992ba','#2c495d']
    };
    public initCharts(charts: HighchartComponent[],metric: MetricComponent){
        this.visitsOvertimeChart = charts[0];
        this.topPages = charts[1];
        this.visitLocation = charts[2];
        this.browserType = charts[3];
        this.deviceType = charts[4];
        this.metricComponent =  metric;
    }
    public setCharts(user: UserModel, dateRange: DateRangeModel,application:AppProfileModel){
        this.selectedApplication = application;
        this.activeUser=user;
        this.setDate(dateRange);
        this.updateAllCharts();
        //console.log("set charts");
    }
    public setDate(dateRange:DateRangeModel){
        if (dateRange.getSelectedPeriodModel() == this._appConfig.DATERANGEPICKER.PERIODS.CUSTOM){
            this.selectedStartDateEpoc  = dateRange.getCustomStartModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getCustomEndModel().getEpoc()+86399000;
        }else{
            this.selectedStartDateEpoc = dateRange.getDayModel().getEpoc();
            this.selectedEndDateEpoc = dateRange.getDayModel().getEpoc()+86399000;
        }
        this.user_timezone = dateRange.getTimezone();
    }

    public refreshCharts(){
        this.visitsOvertimeChart.dataChart =new Chart(this.visitOvertimeChartData);
        this.topPages.dataChart = new Chart(this.topPagesChartData);
        this.visitLocation.dataChart = new Chart(this.visitLocationChartData);
        this.browserType.dataChart = new Chart(this.browserTypeChartData);
        this.deviceType.dataChart = new Chart(this.deviceTypeChartData);

        this.visitsOvertimeChart.dataArray = this.visitOvertimeVisitsArr;
        this.topPages.dataArray = this.topPageCountArr;
        this.visitLocation.dataArray = this.topCountryCountArr;
        this.browserType.dataArray = this.browserTypeJsonArr;
        this.deviceType.dataArray = this.deviceTypeJsonArr;
    }

    public resetCharts(charts: HighchartComponent[]){
        charts[0].dataChart =new Chart(this.visitOvertimeChartData);
        charts[1].dataChart = new Chart(this.topPagesChartData);
        charts[2].dataChart = new Chart(this.visitLocationChartData);
        charts[3].dataChart = new Chart(this.browserTypeChartData);
        charts[4].dataChart = new Chart(this.deviceTypeChartData);

        charts[0].dataArray = new Array();
        charts[1].dataArray = new Array();
        charts[2].dataArray = new Array();
        charts[3].dataArray = new Array();
        charts[4].dataArray = new Array();
    }
  
    public updateAllCharts(){
        this.loadingData = true;
        this.setDate(this._daterangeService.getDateRangeModel());
        const eid = this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_EID + this.activeUser.getEID();
        const airid = "&"+this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_AIRID + this._appProfileService.getSelectedApp().getAirId();
        const id = "&"+ this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_ID + this._appProfileService.getSelectedApp().getId();
        const gte = "&"+ this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_GTE + this.selectedStartDateEpoc;
        const lte = "&"+ this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_LTE + this.selectedEndDateEpoc;
        const nonceID = "&"+this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_NONCE + this.activeUser.getNonceID();
        const now = "&"+this._appConfig.HTTP_CONFIG.PARAMETERS.PARAM_TIMENOW + new Date().getTime();
        
        const zone = "&zone="+encodeURIComponent(this.user_timezone);
        const params = eid+airid+gte+lte+id+nonceID+now+zone;
        const url = this._appConfig.URI.getAllDataByParams+params;
        const headers = new HttpHeaders(this._appConfig.HTTP_CONFIG.HEADER);
//console.log("REFRESH CHARTS",url);
  this._http.get(url,{headers})
  .subscribe((data:any) => {
             this.visualizationData = data.Visualization;

            // processing metric component
            this.metricComponent.refreshMetricValues(this.visualizationData);
            // processing topCountries
            this.topCountryNameArr.length = 0;
            this.topCountryCountArr.length = 0;
            let topCountryJsonArr = data.Visualization.topCountry;
            for (let country of topCountryJsonArr){
                this.topCountryNameArr.push(country.key);
                this.topCountryCountArr.push(country.hash);
            }
            this.visitLocation.dataChart.ref.update({
                xAxis: {categories: this.topCountryNameArr},
                series: [{
                    name: '',
                    data: this.topCountryCountArr
                }]
            },true);
            // processing topPages
            this.topPageNameArr.length = 0;
            this.topPageCountArr.length = 0;
            let topPagesJsonArr = data.Visualization.topPageVisits;
            for (let pages of topPagesJsonArr) {
                this.topPageNameArr.push(pages.key);
                this.topPageCountArr.push(pages.hash);
            }
            this.topPages.dataChart.ref.update({
                xAxis: {categories: this.topPageNameArr},
                series: [{
                    name: '',
                    data: this.topPageCountArr
                }]
            },true);
            // // processing browserType
            this.browserTypeJsonArr.length = 0;
            let topBrowserJsonArr =  data.Visualization.topBrowser;
            for (let browser of topBrowserJsonArr) {
                const obj = {name:browser.key, y:browser.doc_count};
                this.browserTypeJsonArr.push(obj);       
            }
            this.browserType.dataChart.ref.update({
                series: [{
                    name: 'Browser',
                    data: this.browserTypeJsonArr
                }]
            },true);
            // // processing deviceType
            this.deviceTypeJsonArr.length = 0;
            let topDeviceJsonArr =  data.Visualization.topDevices;
            for (let device of topDeviceJsonArr) {
                const obj = {name:device.key, y:device.doc_count};
                this.deviceTypeJsonArr.push(obj);       
            }
            this.deviceType.dataChart.ref.update({
                series: [{
                    name: 'Device',
                    data: this.deviceTypeJsonArr
                }]
            },true);
    
            // processing visitOvertime
            this.visitOvertimeVisitsArr.length = 0;
            this.visitOvertimeUserArr.length = 0;
            this.visitOvertimeNameArr.length = 0;
            let visitOvertimeJsonArr = data.Visualization.visitOvertime;
            for (let visits of visitOvertimeJsonArr) {
                this.visitOvertimeNameArr.push(visits.key_as_string);
                this.visitOvertimeVisitsArr.push(visits.visits);
                this.visitOvertimeUserArr.push(visits.user);
            }
            this.visitsOvertimeChart.dataChart.ref.update({
                xAxis: {categories: this.visitOvertimeNameArr},
                series: [{
                    name: 'Unique Visits',
                    data: this.visitOvertimeVisitsArr
                },
                {
                    name: 'Unique Users',
                    data: this.visitOvertimeUserArr
                }
            ]},true);
            this.loadingData = false;
         },
         err => {
            this._msgHandler.handleComponentError(err.message);
         }
  );

  }
   
}