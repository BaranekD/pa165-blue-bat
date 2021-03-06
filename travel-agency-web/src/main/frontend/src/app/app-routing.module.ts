  import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TripDetailComponent } from './trip-detail/trip-detail.component';
import { ReservationComponent } from "./reservation/reservation.component";
import { LoginComponent } from "./login/login.component";
import { CatalogComponent } from "./catalog/catalog.component";
import { LogoutComponent } from "./logout/logout.component";
import { TripCreateComponent } from "./trip-create/trip-create.component";

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
    path: 'trip-create',
    component: TripCreateComponent
  },
  {
    path: 'trip-detail/reservation',
    component: ReservationComponent,
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

