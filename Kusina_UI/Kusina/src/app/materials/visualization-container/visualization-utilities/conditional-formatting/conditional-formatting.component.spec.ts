import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConditionalFormattingComponent } from './conditional-formatting.component';

describe('ConditionalFormattingComponent', () => {
  let component: ConditionalFormattingComponent;
  let fixture: ComponentFixture<ConditionalFormattingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConditionalFormattingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConditionalFormattingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
