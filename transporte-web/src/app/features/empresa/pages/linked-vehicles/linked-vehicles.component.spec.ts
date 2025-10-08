import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkedVehiclesComponent } from './linked-vehicles.component';

describe('LinkedVehiclesComponent', () => {
  let component: LinkedVehiclesComponent;
  let fixture: ComponentFixture<LinkedVehiclesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LinkedVehiclesComponent]
    });
    fixture = TestBed.createComponent(LinkedVehiclesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
