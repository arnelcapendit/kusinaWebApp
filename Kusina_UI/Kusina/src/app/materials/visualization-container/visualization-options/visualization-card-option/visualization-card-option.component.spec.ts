import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationCardOptionComponent } from './visualization-card-option.component';

describe('VisualizationCardOptionComponent', () => {
  let component: VisualizationCardOptionComponent;
  let fixture: ComponentFixture<VisualizationCardOptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationCardOptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationCardOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
