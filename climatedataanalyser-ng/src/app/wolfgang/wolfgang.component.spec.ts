import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WolfgangComponent } from './wolfgang.component';

describe('WolfgangComponent', () => {
  let component: WolfgangComponent;
  let fixture: ComponentFixture<WolfgangComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WolfgangComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WolfgangComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
