import { Component, OnInit, Inject } from '@angular/core';
import { NguCarousel } from '@ngu/carousel';
import { DOCUMENT} from '@angular/common';
import { ToolBarService } from '../../services/toolbar.service';
import { MatDialog } from '@angular/material';
import { DialogComponent } from '../../materials/dialog/dialog.component';

@Component({
  selector: 'app-onboarding',
  templateUrl: './onboarding.component.html',
  styleUrls: ['./onboarding.component.css']
})
export class OnboardingComponent implements OnInit {

  public carouselOne: NguCarousel;
  grafana = "./assets/images/Grafana.jpg";
  kibana = "./assets/images/Kibana.jpg";
  inframetrics = "./assets/images/InfraMetrics.jpg";
  app358 = "./assets/images/App358.png";
  acceptedDataPrivacy:boolean=false;

  constructor(
    @Inject(DOCUMENT) private document:any,
    // private _toolbarService: ToolBarService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.carouselOne = {
      grid: {xs: 1, sm: 1, md: 1, lg: 1, all: 0},
      slide: 1,
      speed: 400,
      interval: 4000,
      point: {
        visible: true
      },
      load: 2,
      touch: true,
      loop: true,
      custom: 'banner'
    }
    // this._toolbarService.getComponent().setActionVisibility(true);
    if(sessionStorage.getItem("acceptedDataPrivacy") ===null){
      this.viewDialog("data-privacy");
    }else{
      this.acceptedDataPrivacy=true;
    }
    
  }
  public myfunc(event: Event) {
    // carouselLoad will trigger this funnction when your load value reaches
    // it is helps to load the data by parts to increase the performance of the app
    // must use feature to all carousel
 }
 public goToUrl(){
    this.document.location.href = 'mailto:CIO.IS.EMO.OPS.Metrics.Operations@accenture.com?subject=Onboard to InfraMetrics';
 }
 
 viewDialog(url): void {
  const dialogRef = this.dialog.open(DialogComponent, {
      width: '1024px',
      panelClass: 'view-dialog',
      disableClose: true,
      data: { url: url, dialogName: 'data-privacy' }
  });

  dialogRef.afterClosed().subscribe(result => {
     this.acceptedDataPrivacy=true;
     sessionStorage.setItem('acceptedDataPrivacy',Date.now().toString());
     console.log(this.acceptedDataPrivacy);
  });
}



}
