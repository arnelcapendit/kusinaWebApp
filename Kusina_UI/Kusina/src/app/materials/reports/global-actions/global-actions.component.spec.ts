import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalActionsComponent } from './global-actions.component';

describe('GlobalActionsComponent', () => {
  let component: GlobalActionsComponent;
  let fixture: ComponentFixture<GlobalActionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalActionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
