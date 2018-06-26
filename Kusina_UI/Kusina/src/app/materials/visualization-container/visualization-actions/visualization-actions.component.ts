import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { VisualizationService } from '../../../services/custom-dashboard/visualization.service';
import { SidenavService } from '../../../services/sidenav.service';

@Component({
  selector: 'app-visualization-actions',
  templateUrl: './visualization-actions.component.html',
  styleUrls: ['./visualization-actions.component.css']
})
export class VisualizationActionsComponent implements OnInit {

  @Input() id:number;

  constructor(
    private _visualizationService:VisualizationService,
    //private _sidenavService: SidenavService
    
  ) { }

  ngOnInit() {
  }

  delete(id){
    //this._visualizationService.removeComponent(id);
  }
  configure(id){
    this._visualizationService.toggleSideNavigation('configureVisualization');
    //todo
  }


}
