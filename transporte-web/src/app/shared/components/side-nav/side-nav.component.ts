import { Component, Input } from '@angular/core';
import { MenuItem } from 'src/app/core/models/menu-item.model';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent {

  @Input() menuItems: MenuItem[] = [];
}
