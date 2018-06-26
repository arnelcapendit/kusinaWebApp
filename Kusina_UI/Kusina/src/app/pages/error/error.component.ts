import { Component, OnInit, OnDestroy} from '@angular/core';
import { MsgHandlerService } from '../../services/msg-handler.service';
import { ToolBarService } from '../../services/toolbar.service';
import { UsePiwikTracker } from 'angular2piwik';
import { GlobalFilterService } from '../../services/global-filters/global-filters.service';
import { AppConfig } from '../../globals/app.config';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit, OnDestroy {

  constructor(
    private _appConfig: AppConfig,
    private _msgHandlerService: MsgHandlerService, 
    private _toolBarService:ToolBarService,
    private _usePiwikTracker: UsePiwikTracker,
    private _globalFilterService: GlobalFilterService,
  ) { }

  public USER_ERROR:string ="Seems you don’t have any permission yet.";
  // +"Contact us<embed mailto: link>"
  // +" to give you access to inframetrics piwik.”"

  public SESSION_ERROR:string ="No session found. "
  +"Please re-login.";

  public SESSION_EXPIRED:string ="Your session is expired. "
  +"Please re-login.";

  public API_ERROR:string ="Problem webservice connection.";
  //+"Please contact us<embed mailto: > to resolve the issue."

  public setErrorMsg():string{
   //let errMsg =  this.compHandler.getErrorMsg();
   let errMsg = this._msgHandlerService.getErrorMsg();
   if (errMsg=="unauthorized user."){
      errMsg = this.USER_ERROR;
    } else if(errMsg =="no session found"){
    errMsg = this.SESSION_ERROR;
    } else if(errMsg =="session expired"){
      errMsg = this.SESSION_EXPIRED;
      }
      else if(errMsg.length >22 || errMsg==undefined){
        const temp = errMsg;
        errMsg = this.API_ERROR+" ["+temp+"]";
        }
    return errMsg;
  }
  ngOnInit() {
    this._toolBarService.getComponent().isHidden=true;
    //track page
    const url = this._appConfig.getActiveTracker() + this._appConfig.WEB_PAGES.ERROR_PAGE;
    this._usePiwikTracker.setPageAndTrack(url, 'Inframetrics - Error');
  }
  ngOnDestroy(){
    this._toolBarService.getComponent().isHidden=false;
  }

}
