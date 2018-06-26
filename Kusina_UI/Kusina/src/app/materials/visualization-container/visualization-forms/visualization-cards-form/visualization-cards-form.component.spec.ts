import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationCardsFormComponent } from './visualization-cards-form.component';

describe('VisualizationCardsFormComponent', () => {
  let component: VisualizationCardsFormComponent;
  let fixture: ComponentFixture<VisualizationCardsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationCardsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationCardsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
