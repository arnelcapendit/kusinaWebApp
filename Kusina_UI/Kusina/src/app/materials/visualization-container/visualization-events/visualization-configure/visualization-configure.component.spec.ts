import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationConfigureComponent } from './visualization-configure.component';

describe('VisualizationConfigureComponent', () => {
  let component: VisualizationConfigureComponent;
  let fixture: ComponentFixture<VisualizationConfigureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationConfigureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationConfigureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
