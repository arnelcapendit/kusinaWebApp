import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationMetricOptionComponent } from './visualization-metric-option.component';

describe('VisualizationMetricOptionComponent', () => {
  let component: VisualizationMetricOptionComponent;
  let fixture: ComponentFixture<VisualizationMetricOptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationMetricOptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationMetricOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
