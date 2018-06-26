import { Component, OnInit } from '@angular/core';
import { MarkdownVisualizationModel } from '../../../../models/custom-dashboard-visualizations/visualizations.model';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';

@Component({
  selector: 'app-visualization-markdown-form',
  templateUrl: './visualization-markdown-form.component.html',
  styleUrls: ['./visualization-markdown-form.component.css']
})
export class VisualizationMarkdownFormComponent implements OnInit {

  parameter:MarkdownVisualizationModel = new MarkdownVisualizationModel();

  constructor(
    private _visualizationService:VisualizationService
  ) { }

  ngOnInit() {
  }

  refreshPreview(data:any){
    console.log("CHILD DATA FROM FORM: ", data);
    console.log("CHILD DATA FROM MODEL: ",this.parameter);
    this._visualizationService.prevComponent.markdownData =  this.parameter;

  }
  add(){
    this._visualizationService.addComponent(this.parameter,'markdown');
    this._visualizationService.removePreviewComponent();
    this._visualizationService.toggleSideNavigation('addVisualization');
    this.parameter = new MarkdownVisualizationModel();
    console.log("value", this.parameter);
  }

}
