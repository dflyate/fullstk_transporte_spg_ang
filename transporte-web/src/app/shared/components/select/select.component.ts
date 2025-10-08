import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { SelectOption } from 'src/app/core/services/catalog.service';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.css']
})
export class SelectComponent {

  @Input() label!: string;
  @Input() control!: FormControl;
  @Input() options: SelectOption[] = [];
  @Input() placeholder: string = '';
  @Input() required!: string;
  
}
