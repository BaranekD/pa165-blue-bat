import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient) { }

  public createReservation(model: any): Observable<any> {
    return this.http.post("/pa165/rest/reservation/create", model);
  }
}
