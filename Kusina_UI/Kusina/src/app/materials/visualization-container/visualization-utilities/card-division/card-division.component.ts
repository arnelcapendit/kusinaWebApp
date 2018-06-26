import { Component, OnInit, Input, ComponentFactoryResolver, Output, EventEmitter } from '@angular/core';
import { CardsVisualization } from '../../../../models/custom-dashboard-visualizations/visualizations.model';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';

@Component({
  selector: 'app-card-division',
  templateUrl: './card-division.component.html',
  styleUrls: ['./card-division.component.css']
})
export class CardDivisionComponent implements OnInit {
  style:string;
  @Input() data:CardsVisualization;
  @Output() onFormChanged: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    private _visualizationService:VisualizationService
  ) { }

  ngOnInit() {
  
  }

  update(){
    //this.data.setStyle(this.style);
  }

  remove() {
    this._visualizationService.removeComponent(this.data.id,"card");
  }

  formChanged(){
    //this.data.styleModel
    //this.data.setStyle(this.data.style.);
    this.onFormChanged.emit(this.data);
  }

}
