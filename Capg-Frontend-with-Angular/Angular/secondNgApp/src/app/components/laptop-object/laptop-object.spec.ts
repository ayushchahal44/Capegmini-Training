import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LaptopObject } from './laptop-object';

describe('LaptopObject', () => {
  let component: LaptopObject;
  let fixture: ComponentFixture<LaptopObject>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LaptopObject],
    }).compileComponents();

    fixture = TestBed.createComponent(LaptopObject);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
