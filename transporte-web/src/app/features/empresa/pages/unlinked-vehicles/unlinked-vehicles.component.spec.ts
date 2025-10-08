import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnlinkedVehiclesComponent } from './unlinked-vehicles.component';

describe('UnlinkedVehiclesComponent', () => {
  let component: UnlinkedVehiclesComponent;
  let fixture: ComponentFixture<UnlinkedVehiclesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnlinkedVehiclesComponent]
    });
    fixture = TestBed.createComponent(UnlinkedVehiclesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
