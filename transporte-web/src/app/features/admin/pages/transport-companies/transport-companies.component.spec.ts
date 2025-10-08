import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransportCompaniesComponent } from './transport-companies.component';

describe('TransportCompaniesComponent', () => {
  let component: TransportCompaniesComponent;
  let fixture: ComponentFixture<TransportCompaniesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransportCompaniesComponent]
    });
    fixture = TestBed.createComponent(TransportCompaniesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
