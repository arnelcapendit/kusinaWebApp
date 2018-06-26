import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationMetricFormComponent } from './visualization-metric-form.component';

describe('VisualizationMetricFormComponent', () => {
  let component: VisualizationMetricFormComponent;
  let fixture: ComponentFixture<VisualizationMetricFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationMetricFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationMetricFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
