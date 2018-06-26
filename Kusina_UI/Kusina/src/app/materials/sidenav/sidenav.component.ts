import { Component, OnInit, ViewChild, ElementRef, HostListener, AfterViewInit, ViewContainerRef, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { SidenavDisplayNameComponent } from '../sidenav-display-name/sidenav-display-name.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { ToolBarService } from '../../services/toolbar.service';
import { VisualizationBoundsService } from '../../services/custom-dashboard/visualization-bounds.service';
import { VisualizationService } from '../../services/custom-dashboard/visualization.service';
import { VisualizationAddComponent } from '../visualization-container/visualization-events/visualization-add/visualization-add.component';
import { VisualizationSideNavService } from '../../services/custom-dashboard/visualization-sidenav.service';
import { SidenavService } from '../../services/sidenav.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit, AfterViewInit{


  divWidth = 0;
  notifCount: number;
  action="";

  @ViewChild('sidenav') public sidenav: MatSidenav;
  @ViewChild('endnav') public endnav: MatSidenav;
  @ViewChild('displayName') public displayName: SidenavDisplayNameComponent;
  @ViewChild('toolbar') public toolbar: NavbarComponent;
  @ViewChild('parentContainer') parentDiv: ElementRef;
  @ViewChild('temporaryContainer', {read: ViewContainerRef}) temporaryContainer: ViewContainerRef;
  @ViewChild('addVisualization') public visualizationSideNav: VisualizationAddComponent;

  @HostListener('window:resize') onResize() {
    this.divWidth = this.parentDiv.nativeElement.clientWidth;

    if (this.divWidth < 800) {
      //this._dateRangeService.getDateRangeComponent().isHidden = true;
      this.toolbar.hideTitle = true;
    }
    if (this.divWidth > 800) {
      //this._dateRangeService.getDateRangeComponent().isHidden = false;
      this.toolbar.hideTitle = false;
      this.toolbar.appFilters.styleWidth = null;
    }
    if (this.divWidth < 600) {
      this.toolbar.appFilters.styleWidth = this.divWidth - 170;
    }

}
  constructor(
    private _globalService: GlobalFilterService,
    private _toolBarService: ToolBarService,
    private _visualizationService: VisualizationService
  ) { 

  }

  ngOnInit() {
    this._globalService.init(this.displayName);
    this._toolBarService.init(this.toolbar);
 
    setTimeout(_ => this.divWidth = this.parentDiv.nativeElement.clientWidth);
  }

  public onlickItem() {
    this.sidenav.close();
  }

  // Checking If User has Super Administrator access...
  isAdmin(): boolean {
    //return this._userService.getIsAdminVal();
    return this._globalService.isSuperAdmin();
  }
  ngAfterViewInit() {
    setTimeout(_ => this.divWidth = this.parentDiv.nativeElement.clientWidth);
    this._visualizationService.setPreviewContainer(this.temporaryContainer);
    
  }
  removePreview(){
    this._visualizationService.removePreviewComponent();
    
  }
  // Customizing top navbar removing search and date picker icons...
  customNav(): void {
    //this.toolbar.configureVisibility();
  }
  setAction(action:string){
    this.action=action;
  }

}
