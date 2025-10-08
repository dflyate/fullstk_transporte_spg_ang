import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'src/app/core/models/menu-item.model';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent {

  @Input() title: string = 'Panel';
  @Input() menuItems: MenuItem[] = [];

  constructor(private router: Router){}

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

}
 