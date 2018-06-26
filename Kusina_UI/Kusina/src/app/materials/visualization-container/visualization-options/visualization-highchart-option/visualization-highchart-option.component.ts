import { Component, OnInit, Input } from '@angular/core';
import { HighchartVisualizationModel } from '../../../../models/custom-dashboard-visualizations/visualizations.model';

@Component({
  selector: 'app-visualization-highchart-option',
  templateUrl: './visualization-highchart-option.component.html',
  styleUrls: ['./visualization-highchart-option.component.css']
})
export class VisualizationHighchartOptionComponent implements OnInit {

  @Input() data:HighchartVisualizationModel = new HighchartVisualizationModel('','',[]);

  constructor() { }

  ngOnInit() {
  }

}
