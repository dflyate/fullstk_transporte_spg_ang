import { Component } from '@angular/core';
import { MenuItem } from 'src/app/core/models/menu-item.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  title: string = '';
  
    companyMenu: MenuItem[] = [
      {
        icon: 'business',
        label: 'Compañía',
        route: '/company/transport-company',
      },
      {
        icon: 'directions_car',
        label: 'Vehículos',
        route: '/company/vehicles/linked-vehicles',
      },
      {
        icon: 'person',
        label: 'Conductores',
        route: '/company/drivers',
      },
    ];
    
}
