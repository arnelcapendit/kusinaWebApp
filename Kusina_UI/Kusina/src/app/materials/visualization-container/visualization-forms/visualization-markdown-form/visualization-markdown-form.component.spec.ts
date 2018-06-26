import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationMarkdownFormComponent } from './visualization-markdown-form.component';

describe('VisualizationMarkdownFormComponent', () => {
  let component: VisualizationMarkdownFormComponent;
  let fixture: ComponentFixture<VisualizationMarkdownFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationMarkdownFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationMarkdownFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
