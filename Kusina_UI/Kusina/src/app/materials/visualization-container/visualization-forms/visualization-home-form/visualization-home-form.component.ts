import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { VisualizationService } from '../../../../services/custom-dashboard/visualization.service';
import { SidenavService } from '../../../../services/sidenav.service';

@Component({
  selector: 'app-visualization-home-form',
  templateUrl: './visualization-home-form.component.html',
  styleUrls: ['./visualization-home-form.component.css']
})
export class VisualizationHomeFormComponent implements OnInit {

  @Input() visualization:string = 'home';
  @Output() visualizationChange = new EventEmitter();

  set visualizationUpdate(val:string) {
    this.visualization = val;
    this.visualizationChange.emit(this.visualization);
  }
  constructor(
    private _visualizationService:VisualizationService,
    private _sideNavService:SidenavService
  ) { }

  ngOnInit() {
  }

  selectOption(option:string){
    this.visualization=option;
    this.visualizationChange.emit(this.visualization);
    this._visualizationService.previewComponent(this.visualization);
    this._sideNavService.getComponent().visualizationSideNav
  }

}
