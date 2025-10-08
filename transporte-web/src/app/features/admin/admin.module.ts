import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { SharedModule } from 'src/app/shared/shared.module';
import { TransportCompaniesComponent } from './pages/transport-companies/transport-companies.component';
import { TransportCompanyComponent } from './pages/transport-company/transport-company.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { ReactiveFormsModule } from '@angular/forms';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';
import { VehicleComponent } from './pages/vehicle/vehicle.component';

@NgModule({
  declarations: [DashboardComponent, TransportCompaniesComponent, TransportCompanyComponent, VehiclesComponent, VehicleComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatSidenavModule,
    SharedModule,
    MatGridListModule,
    ReactiveFormsModule
  ]
})
export class AdminModule {}
