import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ConditionalFormat } from '../../../../models/custom-dashboard-visualizations/visualizations.model';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';

@Component({
  selector: 'app-conditional-formatting',
  templateUrl: './conditional-formatting.component.html',
  styleUrls: ['./conditional-formatting.component.css']
})
export class ConditionalFormattingComponent implements OnInit {

  @Input() data : ConditionalFormat = new ConditionalFormat();
  @Output() onFormChanged: EventEmitter<any> = new EventEmitter<any>();
  @Output() onDeleteItem: EventEmitter<any> = new EventEmitter<any>();

  constructor( 
    private _visualizationService:VisualizationService
  ) { }

  ngOnInit() {
  }

  deleteRule(){
 

    console.log("DELETING");
    this._visualizationService.removeComponent(this.data.id,'metrics');
  }
  formChanged(){
    //this.data.styleModel
    //this.data.setStyle(this.data.style.);
    this.onFormChanged.emit(this.data);
  }

  deleteItem(){
    //this.data.styleModel
    //this.data.setStyle(this.data.style.);
    this.onDeleteItem.emit(this.data);
  }



}
