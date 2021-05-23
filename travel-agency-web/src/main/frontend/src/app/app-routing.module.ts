  import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TripDetailComponent } from './trip-detail/trip-detail.component';
import {ReservationComponent} from "./reservation/reservation.component";
import {LoginComponent} from "./login/login.component";
import {CatalogComponent} from "./catalog/catalog.component";

const routes: Routes = [
  {
    path: '',
    component: CatalogComponent,
  },
  {
    path: 'trip-detail',
    component: TripDetailComponent
  },
  {
    path: 'trip-detail/reservation',
    component: ReservationComponent,
  },
  {
    path: 'login',
    component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

