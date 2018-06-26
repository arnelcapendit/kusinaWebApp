import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItfClientComponent } from './itf-client.component';

describe('ItfClientComponent', () => {
  let component: ItfClientComponent;
  let fixture: ComponentFixture<ItfClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItfClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItfClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
