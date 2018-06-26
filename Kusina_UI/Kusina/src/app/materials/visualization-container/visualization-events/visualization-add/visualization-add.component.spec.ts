import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationAddComponent } from './visualization-add.component';

describe('VisualizationAddComponent', () => {
  let component: VisualizationAddComponent;
  let fixture: ComponentFixture<VisualizationAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
