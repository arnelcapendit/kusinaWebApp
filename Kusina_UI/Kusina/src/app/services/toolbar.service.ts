import { Injectable } from '@angular/core';
import { MatSidenav, MatDrawerToggleResult } from '@angular/material';
import { NavbarComponent } from '../materials/navbar/navbar.component';

@Injectable()
export class ToolBarService {
    private component: NavbarComponent;

    public init(navbar: NavbarComponent) {
        this.component = navbar;
    }

    public getComponent():NavbarComponent{
        return this.component;
    }
}