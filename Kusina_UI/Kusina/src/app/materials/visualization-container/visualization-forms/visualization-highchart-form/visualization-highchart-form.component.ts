import { Component, OnInit } from '@angular/core';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';
import { HighchartVisualizationModel, SeriesModel } from '../../../../models/custom-dashboard-visualizations/visualizations.model';

@Component({
  selector: 'app-visualization-highchart-form',
  templateUrl: './visualization-highchart-form.component.html',
  styleUrls: ['./visualization-highchart-form.component.css']
})
export class VisualizationHighchartFormComponent implements OnInit {
  type:any;
  parameter:HighchartVisualizationModel = new HighchartVisualizationModel('','',[]);

  constructor(
    private _visualizationService:VisualizationService
  ) { }

  ngOnInit() {
  }

  add(){  
    // this.parameter = new HighchartVisualizationModel('line','test',[new SeriesModel("line1",[1,2,3])]);

    // console.log("TO BE ADDED: ",this.parameter);
    // this._visualizationService.addComponent(this.parameter,'chart');
    // this._visualizationService.removePreviewComponent();
    // this._visualizationService.toggleSideNavigation('addVisualization');
    // this.parameter = new HighchartVisualizationModel('','',[]);
  }


}
