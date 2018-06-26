import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnouncementIconComponent } from './announcement-icon.component';

describe('AnnouncementIconComponent', () => {
  let component: AnnouncementIconComponent;
  let fixture: ComponentFixture<AnnouncementIconComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnnouncementIconComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnnouncementIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
