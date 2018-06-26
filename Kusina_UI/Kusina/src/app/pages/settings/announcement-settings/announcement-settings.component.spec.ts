import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnouncementSettingsComponent } from './announcement-settings.component';

describe('AnnouncementSettingsComponent', () => {
  let component: AnnouncementSettingsComponent;
  let fixture: ComponentFixture<AnnouncementSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnnouncementSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnnouncementSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
