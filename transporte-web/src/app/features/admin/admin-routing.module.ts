import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TransportCompaniesComponent } from './pages/transport-companies/transport-companies.component';
import { TransportCompanyComponent } from './pages/transport-company/transport-company.component';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';
import { VehicleComponent } from './pages/vehicle/vehicle.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'admin', pathMatch: 'full' },
      { path: '', component: TransportCompaniesComponent },
      { path: 'new-transport-company', component: TransportCompanyComponent },
      { path: 'edit-transport-company/:id', component: TransportCompanyComponent }
    ]
  },

  {
      path: 'vehicles',
      component: DashboardComponent,
      children: [
        { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
        { path: 'vehicles', component: VehiclesComponent },
        { path: 'new-vehicle', component: VehicleComponent },
        { path: 'edit-vehicle/:id', component: VehicleComponent },
      ]
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }