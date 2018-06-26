import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-announcement-icon',
  templateUrl: './announcement-icon.component.html',
  styleUrls: ['./announcement-icon.component.css']
})
export class AnnouncementIconComponent implements OnInit {
  count = 0;
  private showAnnouncement:boolean=false;

  @Input()
  set notificationCount(val) {
      this.count = val;
  }

  constructor() { }

  ngOnInit() {
  }

  annouceClick() {
    this.showAnnouncement = !this.showAnnouncement;
  }

  


}
