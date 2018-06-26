import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavDisplayNameComponent } from './sidenav-display-name.component';

describe('SidenavDisplayNameComponent', () => {
  let component: SidenavDisplayNameComponent;
  let fixture: ComponentFixture<SidenavDisplayNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidenavDisplayNameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavDisplayNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
