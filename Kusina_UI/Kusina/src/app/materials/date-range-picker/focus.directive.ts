import { Input, Inject, ElementRef,Renderer, ViewChild, EventEmitter, Directive } from '@angular/core';

@Directive({
    selector: '[focus]'
  })
  export class FocusDirective {
    @ViewChild('focus') focusEvent: EventEmitter<boolean>;
    constructor(@Inject(ElementRef) private element: ElementRef, private renderer: Renderer) {
    }
    ngOnInit() {
      this.focusEvent.subscribe(event => {
        this.renderer.invokeElementMethod(this.element.nativeElement, 'focus', []);
      });
    }
  }
  