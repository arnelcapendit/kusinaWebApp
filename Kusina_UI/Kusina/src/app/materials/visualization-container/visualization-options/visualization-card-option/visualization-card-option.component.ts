import { Component, OnInit, Input, ViewChild, ViewContainerRef } from '@angular/core';
import { CardsVisualization } from '../../../../models/custom-dashboard-visualizations/visualizations.model';

@Component({
  selector: 'app-visualization-card-option',
  templateUrl: './visualization-card-option.component.html',
  styleUrls: ['./visualization-card-option.component.css']
})
export class VisualizationCardOptionComponent implements OnInit {

  @Input() data:CardsVisualization = new CardsVisualization();
 
  constructor() { }
  
  ngOnInit() {
  
  }

}
