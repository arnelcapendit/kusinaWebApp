import { ComponentFactoryResolver,Injectable,Inject,ReflectiveInjector, Type } from '@angular/core';
import { VisualizationContainerComponent } from '../../materials/visualization-container/visualization-container.component';
import { SidenavService } from '../sidenav.service';
import { MetricsVisualization } from '../../models/custom-dashboard-visualizations/visualizations.model';


@Injectable()
export class VisualizationService {
    private previewContainer;
    private dashboardContainer;
    private dashboardBounds;
 

    components = [];
    prevComponent:VisualizationContainerComponent;

    action:string;
    type:string;
    
    title:string ='My Title';

    constructor( 
        private _factoryResolver:ComponentFactoryResolver,
        private _sidenavService: SidenavService
    ) {

    }
    setPreviewContainer(viewContainerRef) {
        this.previewContainer = viewContainerRef;
        
    }
    getPreviewContainer() {
        return this.previewContainer();
        
    }
    setDashboardContainer(viewContainerRef) {
        this.dashboardContainer = viewContainerRef;
    }
    setDashboardBounds(bounds){
        this.dashboardBounds=bounds;
    }
    toggleSideNavigation(action:string){
         //load and open the side navigation
         //'addVisualization'
         this._sidenavService.getComponent().setAction(action);
         
         this._sidenavService.getComponent().sidenav.toggle().then(() => { 
           this._sidenavService.getComponent().visualizationSideNav.visualization='home';
         });
    }
    previewComponent(type:string) {
        // Create component dynamically inside the ng-template
        console.log("PREVIEW COMPONENT");
        const componentFactory = this._factoryResolver.resolveComponentFactory(VisualizationContainerComponent);
        const component = this.previewContainer.createComponent(componentFactory).instance as VisualizationContainerComponent
        component.type = type;
        if(type==='chart'){
            component.configureLeft = "10%";
            component.configureTop = "10%";
        }
        //e:VisualizationContainerComponent.prototype = component.instance;
        // Push the component so that we can keep track of which components are created
        this.prevComponent = component;
    }
    addNewPreviewComponent(type:string) {
        // Create component dynamically inside the ng-template
        console.log("PREVIEW COMPONENT");
        const componentFactory = this._factoryResolver.resolveComponentFactory(VisualizationContainerComponent);
        const component = this.previewContainer.createComponent(componentFactory);
        component.instance.type = type;
        //e:VisualizationContainerComponent.prototype = component.instance;
        // Push the component so that we can keep track of which components are created
        this.prevComponent = component.instance;
        //var e = (VisualizationContainerComponent) component
        component.instance.id = Math.random();
        //component.instance.title = this.title;
        //this.components.push(component)
        //console.log(component.instance);
        //console.log(this.components);
    }
    getRules(id:number) {
        // const component = this.components.find((component) => component.instance instanceof VisualizationContainerComponent && component.instance.id == index);
        // const componentIndex = this.components.indexOf(component);
        // console.log("COMPONENT INDEX: ",componentIndex);
        // if (componentIndex !== -1) {
        //   // Remove component from both view and array
          
        //   console.log("DELETING",component.instance);
        //   this.dashboardContainer.remove(this.dashboardContainer.indexOf(component));
        //   this.components.splice(componentIndex, 1);
        //   console.log("REMAINING",this.components);
        // }
    }
    
    removePreviewComponent() {
          // Remove component from both view and array
          this.previewContainer.remove(0);
    }
    addComponent(parameter:any, type:string) {
        // Create component dynamically inside the ng-template
        const componentFactory = this._factoryResolver.resolveComponentFactory(VisualizationContainerComponent);
        //const component = this.dashboardContainer.createComponent(componentFactory).instance;
        const component = this.dashboardContainer.createComponent(componentFactory).instance as VisualizationContainerComponent;

        // Push the component so that we can keep track of which components are created
       
        component.type = type;
        component.createDraggableVisualization(parameter);
        
        this.components.push(component)
        
        console.log(this.components);
        
    }
       
    removeComponent(id:any,type:string) {
        console.log("REMOVING ID: ",id);
        console.log("REMOVING TYPE: ",type)
        if(type==='card'){
            const preview = this.prevComponent.cardsData.children.find((child) => child.id == id);
            const previewIndex = this.prevComponent.cardsData.children.indexOf(preview);
            if (previewIndex !== -1) {
                this.prevComponent.cardsData.children.splice(previewIndex, 1);
            }
        }else if(type==='metrics'){

        }
    }
}