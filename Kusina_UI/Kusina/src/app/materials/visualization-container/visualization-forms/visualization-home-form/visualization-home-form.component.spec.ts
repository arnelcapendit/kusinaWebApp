import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationHomeFormComponent } from './visualization-home-form.component';

describe('VisualizationHomeFormComponent', () => {
  let component: VisualizationHomeFormComponent;
  let fixture: ComponentFixture<VisualizationHomeFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationHomeFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationHomeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
