import { Component, OnInit } from '@angular/core';
import { TripViewModel } from '../../models/trip-view.model';

@Component({
  selector: 'app-trip-detail',
  templateUrl: './trip-detail.component.html',
  styleUrls: ['./trip-detail.component.css']
})
export class TripDetailComponent implements OnInit {
  tripId: number = 0;
  trip: any; // TODO: potřeba si udělat service a získat tento objekt pomocí tripId (ideálně v ngOnInit)

  constructor() { }

  ngOnInit() {
    this.tripId = window.history.state.tripId;
  }

}
