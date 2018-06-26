import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationHighchartFormComponent } from './visualization-highchart-form.component';

describe('VisualizationHighchartFormComponent', () => {
  let component: VisualizationHighchartFormComponent;
  let fixture: ComponentFixture<VisualizationHighchartFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationHighchartFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationHighchartFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
