import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";
import {TripViewModel} from "../models/trip-view.model";
import {TripCreateModel} from "../models/trip-create.model";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class TripService {

  constructor(
    private http: HttpClient,
    private authService: AuthService) { }

  public findTripById(id: number): Observable<any> {
    return this.http.get<TripViewModel>("/pa165/rest/main/trips/" + id);
  }

  public createTrip(model: TripCreateModel): Observable<any> {
    return this.http.post("/pa165/rest/main/trips/", model, { headers: this.authService.getAuthHeader() });
  }
}
