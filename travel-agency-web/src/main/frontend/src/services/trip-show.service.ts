import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class TripShowService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }
  public showTrips(): Observable<any> {
    return this.http.get("/pa165/rest/trips",{ headers : this.authService.getAuthHeader() });
  }
}
