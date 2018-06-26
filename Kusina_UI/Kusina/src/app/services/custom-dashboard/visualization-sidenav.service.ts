import { ComponentFactoryResolver,Injectable,Inject,ReflectiveInjector, Type } from '@angular/core';
import { VisualizationAddComponent } from '../../materials/visualization-container/visualization-events/visualization-add/visualization-add.component';

@Injectable()
export class VisualizationSideNavService {
    private sideNavComponent:VisualizationAddComponent ;

    constructor( private _factoryResolver:ComponentFactoryResolver) {

    }
    initializeComponent(component:VisualizationAddComponent){
        this.sideNavComponent = component;
    }

    getComponent(){
        return this.sideNavComponent;
    }

}