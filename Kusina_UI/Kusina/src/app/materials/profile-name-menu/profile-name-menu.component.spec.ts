import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileNameMenuComponent } from './profile-name-menu.component';

describe('ProfileNameMenuComponent', () => {
  let component: ProfileNameMenuComponent;
  let fixture: ComponentFixture<ProfileNameMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileNameMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileNameMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
