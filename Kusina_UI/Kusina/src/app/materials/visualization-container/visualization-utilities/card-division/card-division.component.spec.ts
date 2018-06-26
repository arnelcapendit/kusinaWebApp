import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardDivisionComponent } from './card-division.component';

describe('CardDivisionComponent', () => {
  let component: CardDivisionComponent;
  let fixture: ComponentFixture<CardDivisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardDivisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardDivisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
