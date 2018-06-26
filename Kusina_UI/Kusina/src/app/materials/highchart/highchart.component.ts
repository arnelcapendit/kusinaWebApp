import { Component, OnInit } from '@angular/core';
import { Chart, Highcharts } from 'angular-highcharts';

@Component({
  selector: 'app-highchart',
  templateUrl: './highchart.component.html',
  styleUrls: ['./highchart.component.css']
})
export class HighchartComponent implements OnInit {
  public dataChart:Chart;
  public dataArray:any;//= [{name:"Hey"}];

  constructor() { }

  ngOnInit() {
  }

}
