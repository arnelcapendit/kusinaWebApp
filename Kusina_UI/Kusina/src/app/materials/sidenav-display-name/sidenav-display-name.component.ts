import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../models/user.model';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';

@Component({
  selector: 'app-sidenav-display-name',
  templateUrl: './sidenav-display-name.component.html',
  styleUrls: ['./sidenav-display-name.component.css']
})
export class SidenavDisplayNameComponent implements OnInit {
  username:string;

  constructor(private _globalService:GlobalFilterService) { }

  ngOnInit() {
      this.username = this._globalService.getUser().getDisplayName();
    
  }

}
