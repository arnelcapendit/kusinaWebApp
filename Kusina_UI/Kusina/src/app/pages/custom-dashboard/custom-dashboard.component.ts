import { Component, OnInit, ViewContainerRef, Inject, ViewChild, AfterViewInit } from '@angular/core';
import { VisualizationService } from '../../services/custom-dashboard/visualization.service';
import { VisualizationBoundsService } from '../../services/custom-dashboard/visualization-bounds.service';
import { trigger,state,style,transition,animate,keyframes } from '@angular/animations';
import { VisualizationSideNavService } from '../../services/custom-dashboard/visualization-sidenav.service';

@Component({
  selector: 'app-custom-dashboard',
  templateUrl: './custom-dashboard.component.html',
  styleUrls: ['./custom-dashboard.component.css'],
  animations: [
    trigger('addAction', [
      state('small', style({
        bottom:'15px',
        display:'none'
      })),
      state('large', style({
        bottom:'70px',
        display:'block'
      })),
      transition('small <=> large', animate('100ms ease-in')),
    ]),
    trigger('saveAction', [
      state('edit', style({
        display:'none'
      })),
      state('save', style({
        display:'block'
      })),
      transition('edit <=> save', animate('100ms ease-in')),
    ]),
    
  ]
})
export class CustomDashboardComponent implements OnInit, AfterViewInit {

  gridSize = 50;
  gridSize2 = 50;
  grids = [0, 50, 100, 150, 200,250,300,350];
  edge = {
    top: true,
    bottom: true,
    left: true,
    right: false
  };
  outOfBounds = { top: false, right: true, bottom: false, left: false };
  addState: string = 'small';
  saveState: string ='edit';
  editState: string ='save';


  @ViewChild('cardsContainer', {read: ViewContainerRef}) container: ViewContainerRef;
  @ViewChild('myBounds') bounds: any;

  constructor(
    private _visualizationService:VisualizationService,
    private _boundsService:VisualizationBoundsService
  ) { 
      
    
    }

  ngOnInit() {
    //console.log(this.bounds.nativeElement);

  }
  ngAfterViewInit(): void {
    this._visualizationService.setDashboardContainer(this.container);
    this._visualizationService.setDashboardBounds(this.bounds.nativeElement);
    this._boundsService.setBounds(this.bounds.nativeElement);
  }

  addMode(){
    this._visualizationService.toggleSideNavigation('addVisualization');
  }
  editMode(){
    this.addState = (this.addState === 'small' ? 'large' : 'small');
    this.saveState = (this.saveState === 'edit' ? 'save' : 'edit');
    this.editState = (this.editState === 'save' ? 'edit' : 'save');
    //make the dashboard draggable and configurable
  }
  saveChanges(){
    this.addState = (this.addState === 'small' ? 'large' : 'small');
    this.saveState = (this.saveState === 'edit' ? 'save' : 'edit');
    this.editState = (this.editState === 'save' ? 'edit' : 'save');
    //save changes
  }


}
