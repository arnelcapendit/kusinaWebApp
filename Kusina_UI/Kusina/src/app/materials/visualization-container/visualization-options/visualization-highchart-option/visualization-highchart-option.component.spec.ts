import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationHighchartOptionComponent } from './visualization-highchart-option.component';

describe('VisualizationHighchartOptionComponent', () => {
  let component: VisualizationHighchartOptionComponent;
  let fixture: ComponentFixture<VisualizationHighchartOptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationHighchartOptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationHighchartOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
