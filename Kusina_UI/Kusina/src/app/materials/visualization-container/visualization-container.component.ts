import { Component, OnInit, AfterViewInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import { VisualizationBoundsService } from '../../services/custom-dashboard/visualization-bounds.service';
import { MetricsVisualization, CardsVisualization, MarkdownVisualizationModel, HighchartVisualizationModel } from '../../models/custom-dashboard-visualizations/visualizations.model';
import { VisualizationCardOptionComponent } from './visualization-options/visualization-card-option/visualization-card-option.component';

@Component({
  selector: 'app-visualization-container',
  templateUrl: './visualization-container.component.html',
  styleUrls: ['./visualization-container.component.css']
})
export class VisualizationContainerComponent implements OnInit, AfterViewInit{

  @ViewChild('card') cardComponent:VisualizationCardOptionComponent;

  inBounds = true;
  gridSize2 = 15;
  myBounds: any;
  outOfBounds = { top: false, right: true, bottom: true, left: false };

  //default when creating a new visualization
  state = "preview";
  draggable = false;
  configurePosition = 'absolute';
  configureTop = '35%';
  configureLeft = '35%';
  id=0;

  //visualization = metric
  type = "metrics";
  title = "Title";
  value = "3";
  backgroundColor = "#fff";

  data: MetricsVisualization = new MetricsVisualization();;
  metricsData: MetricsVisualization = new MetricsVisualization();
  cardsData: CardsVisualization = new CardsVisualization();
  markdownData: MarkdownVisualizationModel = new MarkdownVisualizationModel();
  highchartData: HighchartVisualizationModel = new HighchartVisualizationModel('','',[]);

  //not important
  edge = { top: true, bottom: true, left: true, right: true };

  constructor(
    private _boundsService:VisualizationBoundsService,
    private _expressionChangeService: ChangeDetectorRef
  ) {   }

  ngOnInit() {
    
  }
  ngAfterViewInit(): void {
    this.myBounds=this._boundsService.getBounds();
  }
  ngAfterViewChecked() {
    this._expressionChangeService.detectChanges();
  }
  createDraggableVisualization(parameter:any){
    this.draggable = true;
    if(parameter instanceof MetricsVisualization){
      this.metricsData = parameter;
      console.log("CREATED METRIC: ",this.metricsData);
    }
    if(parameter instanceof CardsVisualization){
      this.cardsData = parameter;
      console.log("CREATED CARD: ",this.cardsData);
    }
    if(parameter instanceof MarkdownVisualizationModel){
      this.markdownData = parameter;
      console.log("CREATED MARKDOWN: ",this.markdownData);
    }
    if(parameter instanceof HighchartVisualizationModel){
      this.highchartData = parameter;
      console.log("CREATED CHART: ",this.highchartData);
    }

  }
  checkEdge(event) {
    this.edge = event;
    //console.log('edge:', event);
  }
  

}
