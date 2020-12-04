import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClimatesComponent } from './climates.component';

describe('ClimatesComponent', () => {
  let component: ClimatesComponent;
  let fixture: ComponentFixture<ClimatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClimatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClimatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
