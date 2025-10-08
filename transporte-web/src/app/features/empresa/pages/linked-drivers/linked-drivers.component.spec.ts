import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkedDriversComponent } from './linked-drivers.component';

describe('LinkedDriversComponent', () => {
  let component: LinkedDriversComponent;
  let fixture: ComponentFixture<LinkedDriversComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LinkedDriversComponent]
    });
    fixture = TestBed.createComponent(LinkedDriversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
