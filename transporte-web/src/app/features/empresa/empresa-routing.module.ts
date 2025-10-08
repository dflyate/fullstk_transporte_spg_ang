import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TransportCompanyComponent } from './pages/transport-company/transport-company.component';
import { DriversComponent } from './pages/drivers/drivers.component';
import { DriverComponent } from './pages/driver/driver.component';
import { LinkedVehiclesComponent } from './pages/linked-vehicles/linked-vehicles.component';
import { UnlinkedVehiclesComponent } from './pages/unlinked-vehicles/unlinked-vehicles.component';
import { VehicleComponent } from './pages/vehicle/vehicle.component';
import { DriverDetailsComponent } from './pages/driver-details/driver-details.component';
import { LinkedDriversComponent } from './pages/linked-drivers/linked-drivers.component';
import { UnlinkedDriversComponent } from './pages/unlinked-drivers/unlinked-drivers.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'transport-company', 
    pathMatch: 'full'
  },
  {
    path: 'transport-company',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'detail', pathMatch: 'full' },
      { path: 'detail', component: TransportCompanyComponent } 
    ]
  },
  {
    path: 'drivers',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'drivers', pathMatch: 'full' },
      { path: 'drivers', component: DriversComponent },
      { path: 'new-driver', component: DriverComponent },
      { path: 'edit-driver/:id', component: DriverComponent },
    ]
  },
  {
    path: 'vehicles',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
      { path: 'unlinked-vehicles', component: UnlinkedVehiclesComponent},
      { path: 'linked-vehicles', component: LinkedVehiclesComponent},
      { path: 'unlinked-drivers/:id', component: UnlinkedDriversComponent},
      { path: 'linked-drivers/:id', component: LinkedDriversComponent },
      { path: 'vehicle-details/:id', component: VehicleComponent },
      { path: 'driver-details/:id', component: DriverDetailsComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpresaRoutingModule { } 
