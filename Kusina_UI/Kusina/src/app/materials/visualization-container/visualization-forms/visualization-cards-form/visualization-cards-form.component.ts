import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, AfterViewInit } from '@angular/core';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';
import { MetricsVisualization, CardsVisualization } from '../../../../models/custom-dashboard-visualizations/visualizations.model';
import { CardDivisionComponent } from '../../visualization-utilities/card-division/card-division.component';

@Component({
  selector: 'app-visualization-cards-form',
  templateUrl: './visualization-cards-form.component.html',
  styleUrls: ['./visualization-cards-form.component.css']
})
export class VisualizationCardsFormComponent implements OnInit, AfterViewInit{

  parameter:CardsVisualization = new CardsVisualization();

  //models
  cardContent:string;
  cardStyle:string;
  
  //arrays
  childrenData = [];


  constructor(
    private _visualizationService: VisualizationService,
    private _factoryResolver:ComponentFactoryResolver
  ) { }

  ngOnInit() {

  }

  ngAfterViewInit(): void {

  }

  addDivision(){
    this.parameter.children.push(new CardsVisualization());
  }

  removeAllDivision(){
    this.parameter.children.splice(0, this.parameter.children.length);
  }
  add(){
    this._visualizationService.addComponent(this.parameter,'card');
    this._visualizationService.removePreviewComponent();
    this._visualizationService.toggleSideNavigation('addVisualization');
    this.parameter = new CardsVisualization();
  }

  refreshPreview(data:any){
    console.log("CHILD DATA FROM FORM: ", data);
    console.log("CHILD DATA FROM MODEL: ",this.parameter.children);
    this._visualizationService.prevComponent.cardsData =  this.parameter;

  }

}
