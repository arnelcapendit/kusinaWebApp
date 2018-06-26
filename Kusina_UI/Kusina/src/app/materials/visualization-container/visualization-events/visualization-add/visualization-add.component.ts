import { Component, OnInit, Input, AfterViewInit, ViewChild } from '@angular/core';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';
import { VisualizationMetricFormComponent } from '../../visualization-forms/visualization-metric-form/visualization-metric-form.component';

@Component({
  selector: 'app-visualization-add',
  templateUrl: './visualization-add.component.html',
  styleUrls: ['./visualization-add.component.css']
})
export class VisualizationAddComponent implements OnInit, AfterViewInit {

  @Input() visualization:string ="";
  
  constructor(
    private _visualizationService: VisualizationService
  ) { }

  ngOnInit() {
    this.visualization = 'home';
  }
  ngAfterViewInit(): void {
    
  }
  add(){

  }
  e(){
    console.log("SOMETHING CHANGED");
  }
  test(){
    console.log("After Loaded!");
  }

  changeSelection(option:string){
    this.visualization=option;
    this._visualizationService.removePreviewComponent();
  }

}
