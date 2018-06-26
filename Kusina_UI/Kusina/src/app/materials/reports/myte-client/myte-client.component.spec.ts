import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyteClientComponent } from './myte-client.component';

describe('MyteClientComponent', () => {
  let component: MyteClientComponent;
  let fixture: ComponentFixture<MyteClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyteClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyteClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
