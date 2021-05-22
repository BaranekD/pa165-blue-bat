import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ButtonStateEnum} from "../../models/button-state.enum";

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {
  ButtonStateEnum = ButtonStateEnum;
  private _state: ButtonStateEnum = ButtonStateEnum.init;

  @Input() label: string = "";
  @Input() submit: boolean = true;

  @Output() click: EventEmitter<any> = new EventEmitter();
  @Output() stateChange: EventEmitter<ButtonStateEnum> = new EventEmitter();

  @Input()
  set state(state: ButtonStateEnum) {
    this._state = state;
    if (state === ButtonStateEnum.error || state === ButtonStateEnum.success) {
      setTimeout(() => {
        this._state = ButtonStateEnum.init;
        this.stateChange.emit(this._state);
      }, 2000);
    }
  }
  get state(): ButtonStateEnum { return this._state; }


  constructor() { }

  ngOnInit(): void {
  }

  onClick(event: any) {
    event.stopPropagation();
    if (this._state === ButtonStateEnum.init) {
      this.click.emit();
    }
  }

}
