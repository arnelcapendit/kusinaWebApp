import { ComponentFactoryResolver,Injectable,Inject,ReflectiveInjector, Type } from '@angular/core';
import { VisualizationContainerComponent } from '../../materials/visualization-container/visualization-container.component';


@Injectable()
export class VisualizationBoundsService {
    private bounds;

    constructor( private _factoryResolver:ComponentFactoryResolver) {

    }
    setBounds(bounds:any){
        this.bounds=bounds;
    }

    getBounds(){
        return this.bounds;
    }

}