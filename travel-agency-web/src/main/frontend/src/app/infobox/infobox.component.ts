import {Component, Input, OnInit} from '@angular/core';
import {InfoboxStateEnum} from "../../models/infobox-state.enum";

@Component({
  selector: 'app-infobox',
  templateUrl: './infobox.component.html',
  styleUrls: ['./infobox.component.css']
})
export class InfoboxComponent implements OnInit {
  InfoboxStateEnum = InfoboxStateEnum;

  @Input() state: InfoboxStateEnum | null = null;

  constructor() { }

  ngOnInit(): void {
  }

}
