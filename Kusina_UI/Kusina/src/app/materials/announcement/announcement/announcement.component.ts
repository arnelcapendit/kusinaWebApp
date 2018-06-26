import { Component, OnInit } from '@angular/core';
import { AnnouncementsService } from '../../../services/announcement.service';
import { SessionDataService } from '../../../services/session.service';
import { Router } from '@angular/router';
import { DateRangePickerService } from '../../../services/daterange.service';
import { MatDialog } from '@angular/material';
import { DialogComponent } from '../../dialog/dialog.component';
import { Adal5Service } from 'adal-angular5';
import { GlobalFilterService } from '../../../services/global-filters/global-filters.service';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {

  title=[];
  content=[];
  notifCount;

  constructor(
    private  _router: Router,
    private _dateRangeService: DateRangePickerService,
    private _announceService: AnnouncementsService,
    private _adalService: Adal5Service,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    //this.getAnnouncementsBeforeDueDateData();
    this.getAnnouncementAllLive();
  }
  
  getAnnouncementAllLive(){
    this._announceService.getAnnouncementAllLive().subscribe(data => {
      //console.log('This is Data for subscribe: ', data);
      //console.log('This is Data for subscribe2: ', data.TITLE);
      this.title = data;
      //console.log("This is Data Title: ", this.title);
    });
  }

  postReadAction(announceData){
    this._announceService.postReadAction(announceData).subscribe(data => {
      //console.log("post action completed...");
    });
  }


  viewContent(data): void {
    //console.log(data);
     if(data.READ_STATUS === '0'){
      //console.log("post request");
      this.postReadAction(data);
      }
    const dialogRef = this.dialog.open(DialogComponent, {
        width: '1000px',
        panelClass: 'view-dialog',
        data: { dialogName: 'notification-details', data: data }
    });
    dialogRef.afterClosed().subscribe(result => {
        //this._dataSourceService.getTable().refresh();
        this.getNotifCount(this._adalService.userInfo.profile);
        //console.log("user notifcount updated");
    });
}


getNotifCount(user) {
  this._announceService.getNotifCount(user).subscribe( data => {
    //console.log('Notif Count: ', data);
    this.notifCount = data;
  });
}


}
