import { Component } from '@angular/core';
import { MenuItem } from 'src/app/core/models/menu-item.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  title: string = '';

  adminMenu: MenuItem[] = [
    {
      icon: 'business',
      label: 'Empresas',
      route: '/admin',
    },
    {
        icon: 'directions_car',
        label: 'Vehículos',
        route: '/admin/vehicles',
      },
  ];

}
