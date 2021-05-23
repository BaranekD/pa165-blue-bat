import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(
    private http: HttpClient,
    private authService: AuthService) { }

  public createReservation(model: any): Observable<any> {
    return this.http.post("/pa165/rest/reservations/create", model, {headers:this.authService.getAuthHeader()});
  }
}
