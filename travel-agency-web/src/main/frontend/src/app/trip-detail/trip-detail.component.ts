import { Component, OnInit } from '@angular/core';
import { TripViewModel } from '../../models/trip-view.model';
import {TripService} from "../../services/trip.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-trip-detail',
  templateUrl: './trip-detail.component.html',
  styleUrls: ['./trip-detail.component.css']
})
export class TripDetailComponent implements OnInit {
  tripId: number = 0;
  trip: any; // TODO: potřeba si udělat service a získat tento objekt pomocí tripId (ideálně v ngOnInit)
  isAuthenticated: boolean = false;

  constructor(
    public tripService: TripService,
    public authService: AuthService ) { }

  ngOnInit() {
    this.tripId = window.history.state.tripId;

    this.tripService.findTripById(this.tripId)
      .subscribe(
      result => {
        this.trip = result;
      },
      error => {
        console.log(error);
      }
    );

    this.isAuthenticated = this.authService.IsAuthenticated()
  }
}
