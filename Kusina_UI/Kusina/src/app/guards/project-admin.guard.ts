import { Injectable, EventEmitter, Output } from '@angular/core';
import { Router, CanActivate, ActivatedRoute, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { GlobalFilterService } from '../services/global-filters/global-filters.service';
import { UserModel } from '../models/user.model';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material';
import { DialogComponent } from '../materials/dialog/dialog.component';
import { ToolBarService } from '../services/toolbar.service';


 
@Injectable()
export class ProjectAdminGuard implements CanActivate {
    private user:UserModel;
    private acceptedDataPrivacy:boolean=false;
    private retries =0;
    private route;
    private state;

    constructor(
        private router: Router,
        private _globalFilterService:GlobalFilterService,
        public dialog: MatDialog,
        private _toolbarService: ToolBarService
    ) { }
 
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):Observable<boolean> | boolean {
        this.route = route;
        this.state = state;
        return this.helper();
    }

    helper(){
        this._toolbarService.getComponent().loadCompleted = true;
        this.setToolBarVisibility(this.state.url,'before');
        return new Observable<boolean>(observer=>{
            this._globalFilterService.refreshFilters().subscribe((result) => {
                console.log('Result: ', result);
                if (this._globalFilterService.isProjectAdmin()||this._globalFilterService.isOperationsAdmin()||this._globalFilterService.isSuperAdmin()) {
                    if(sessionStorage.getItem("acceptedDataPrivacy") ===null){
                        this.viewDialog("data-privacy").subscribe((re)=>{
                            sessionStorage.setItem("acceptedDataPrivacy",Date.now().toString());
                            this.acceptedDataPrivacy=true;
                            this.setToolBarVisibility(this.state.url,'after');
                            observer.next(true);
                        });
                    } else {
                        this.acceptedDataPrivacy=true;
                        this.setToolBarVisibility(this.state.url,'after');
                        observer.next(true);
                    };
                }else{
                    this.router.navigate(['/onboarding']);
                    // observer.next(false);
                }
            },(err)=>{
                this.router.navigate(['/onboarding']);
                observer.error(true);
                
            });
        }).retry(1).delay(1000);

    }

    viewDialog(url) {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '1024px',
            panelClass: 'view-dialog',
            disableClose: true,
            data: { url: url, dialogName: 'data-privacy' }
        });
    
        return dialogRef.afterClosed().map(result => {
           return true;
           //sessionStorage.setItem("acceptedDataPrivacy",Date.now().toString());
           //console.log(this.acceptedDataPrivacy);
        });
    }

    setToolBarVisibility(path:string,event:string){
        if(event ==='before'){
            this._toolbarService.getComponent().setActionVisibility(true);
        }else{
            if(path==='/dashboard' || path==='/custom'){
                this._toolbarService.getComponent().setActionVisibility(false);
            }
        }

    }
}