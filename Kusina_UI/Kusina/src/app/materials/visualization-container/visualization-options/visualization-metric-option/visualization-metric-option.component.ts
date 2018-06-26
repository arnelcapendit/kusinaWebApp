import { Component, OnInit, Input } from '@angular/core';
import { MetricsVisualization } from '../../../../models/custom-dashboard-visualizations/visualizations.model';

@Component({
  selector: 'app-visualization-metric-option',
  templateUrl: './visualization-metric-option.component.html',
  styleUrls: ['./visualization-metric-option.component.css']
})
export class VisualizationMetricOptionComponent implements OnInit {

  @Input() data:MetricsVisualization = new MetricsVisualization();
  cardHeight = '100px';
  cardWidth = '100px';
  
  constructor() { }

  ngOnInit() {
  }

  test(){

  }

}
