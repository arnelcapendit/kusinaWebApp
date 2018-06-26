import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppConfig } from '../globals/app.config';

@Injectable()
export class MsgHandlerService {
  private ERROR_MSG : string = "General Error";

  constructor(private router: Router, private appConfig: AppConfig) {
      // this.uri = new UriConstantsService();
   }

  public handleComponentError(errorMsg){
    if(errorMsg!=undefined && errorMsg!='created'){
      this.ERROR_MSG = errorMsg;
      this.router.navigateByUrl(this.appConfig.WEB_PAGES.ERROR_PAGE);
    } 
    this.ERROR_MSG = errorMsg;
    this.router.navigateByUrl(this.appConfig.WEB_PAGES.ERROR_PAGE);
  }

  public getErrorMsg():string{
    return this.ERROR_MSG;
  }

}
