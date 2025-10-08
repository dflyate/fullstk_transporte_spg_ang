import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnlinkedDriversComponent } from './unlinked-drivers.component';

describe('UnlinkedDriversComponent', () => {
  let component: UnlinkedDriversComponent;
  let fixture: ComponentFixture<UnlinkedDriversComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnlinkedDriversComponent]
    });
    fixture = TestBed.createComponent(UnlinkedDriversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
