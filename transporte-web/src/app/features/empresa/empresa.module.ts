import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpresaRoutingModule } from './empresa-routing.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { TransportCompanyComponent } from './pages/transport-company/transport-company.component';
import { DriversComponent } from './pages/drivers/drivers.component';
import { DriverComponent } from './pages/driver/driver.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UnlinkedVehiclesComponent } from './pages/unlinked-vehicles/unlinked-vehicles.component';
import { LinkedVehiclesComponent } from './pages/linked-vehicles/linked-vehicles.component';
import { VehicleComponent } from './pages/vehicle/vehicle.component';
import { LinkedDriversComponent } from './pages/linked-drivers/linked-drivers.component';
import { UnlinkedDriversComponent } from './pages/unlinked-drivers/unlinked-drivers.component';
import { DriverDetailsComponent } from './pages/driver-details/driver-details.component';

@NgModule({
  declarations: [DashboardComponent, TransportCompanyComponent, DriversComponent, DriverComponent, UnlinkedVehiclesComponent, LinkedVehiclesComponent, VehicleComponent, LinkedDriversComponent, UnlinkedDriversComponent, DriverDetailsComponent],
  imports: [
    CommonModule,
    EmpresaRoutingModule,
    MatSidenavModule,
    SharedModule,
    ReactiveFormsModule
  ]
})
export class EmpresaModule {}
