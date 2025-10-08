import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {

  @Input() text: string = 'Button';
  @Input() color: 'primary' | 'accent' | 'warn' = 'primary';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() disabled: boolean = false;
  @Input() align: 'left' | 'center' | 'right' = 'center';


  @Output() clicked = new EventEmitter<void>();

  onClick(){
    this.clicked.emit();
  }
}
