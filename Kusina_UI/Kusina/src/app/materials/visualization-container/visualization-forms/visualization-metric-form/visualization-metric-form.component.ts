import { Component, OnInit, Output, Input, ViewChild, ViewContainerRef } from '@angular/core';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';
import { EventEmitter } from 'protractor';
import { MetricsVisualization, ConditionalFormat } from '../../../../models/custom-dashboard-visualizations/visualizations.model';
import { destroyView } from '@angular/core/src/view/view';

@Component({
  selector: 'app-visualization-metric-form',
  templateUrl: './visualization-metric-form.component.html',
  styleUrls: ['./visualization-metric-form.component.css']
})
export class VisualizationMetricFormComponent implements OnInit {

  parameter:MetricsVisualization = new MetricsVisualization();
  
  constructor(
    private _visualizationService: VisualizationService
  ) { }

  ngOnInit() {
  }

  add(){
    this._visualizationService.addComponent(this.parameter,'metrics');
    this._visualizationService.removePreviewComponent();
    this._visualizationService.toggleSideNavigation('addVisualization');
    this.parameter = new MetricsVisualization();
    console.log("value", this.parameter);
  }
  addRule(){
    this.parameter.conditions.push(new ConditionalFormat());
    
  }
  refreshPreview(data:any){
    console.log("CHILD DATA FROM FORM: ", data);
    console.log("CHILD DATA FROM MODEL: ",this.parameter);
    this._visualizationService.prevComponent.metricsData =  this.parameter;

  }
  removeCondition(data:any){
    console.log("CHILD DATA FROM FORM: ", data);
    console.log("CHILD DATA FROM MODEL: ",this.parameter);
    const preview = this.parameter.conditions.find((child) => child.id == data.id);
    const previewIndex = this.parameter.conditions.indexOf(preview);
    if (previewIndex !== -1) {
        this.parameter.conditions.splice(previewIndex, 1);
    }
  }
}
