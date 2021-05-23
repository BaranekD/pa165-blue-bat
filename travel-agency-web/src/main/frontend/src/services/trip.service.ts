import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";
import {TripViewModel} from "../models/trip-view.model";

@Injectable({
  providedIn: 'root'
})
export class TripService {

  constructor(
    private http: HttpClient) { }

  public findTripById(id: number): Observable<any> {
    return this.http.get<TripViewModel>("/pa165/rest/main/trips/" + id);
  }
}
