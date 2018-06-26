import { Injectable } from '@angular/core';
import { MatSidenav, MatDrawerToggleResult } from '@angular/material';
import { SidenavComponent } from '../materials/sidenav/sidenav.component';

@Injectable()
export class SidenavService {
    private sidenav: SidenavComponent;
    private endnav: MatSidenav;
    private action = "";

    // public init(sidenav: MatSidenav, endnav: MatSidenav) {
    //     this.sidenav = sidenav;
    //     this.endnav = endnav;
    // }
    public init(sidenav: SidenavComponent) {
        this.sidenav = sidenav;
       // this.endnav = endnav;
    }

    public open() {
        return this.sidenav.sidenav.open();
    }

    public close() {
        return this.sidenav.sidenav.close();
    }

    public toggle(isOpen?: boolean) {
        return this.sidenav.sidenav.toggle(isOpen);
    }
    public setAction(action:string){
        
        this.action=action;
    }
    public getAction(){
        return this.action;
    }
    public getComponent(){
        return this.sidenav;
    }

}

