import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationActionsComponent } from './visualization-actions.component';

describe('VisualizationActionsComponent', () => {
  let component: VisualizationActionsComponent;
  let fixture: ComponentFixture<VisualizationActionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationActionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
