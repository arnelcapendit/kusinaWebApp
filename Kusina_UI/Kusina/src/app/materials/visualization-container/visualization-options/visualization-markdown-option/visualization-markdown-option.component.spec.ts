import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizationMarkdownOptionComponent } from './visualization-markdown-option.component';

describe('VisualizationMarkdownOptionComponent', () => {
  let component: VisualizationMarkdownOptionComponent;
  let fixture: ComponentFixture<VisualizationMarkdownOptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizationMarkdownOptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizationMarkdownOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
