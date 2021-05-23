import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { CatalogComponent } from './catalog/catalog.component';
import { ReservationComponent } from './reservation/reservation.component';
import { TripDetailComponent } from './trip-detail/trip-detail.component';
import {HttpClientModule} from "@angular/common/http";
import { ButtonComponent } from './button/button.component';
import {ReactiveFormsModule} from "@angular/forms";
import { InfoboxComponent } from './infobox/infobox.component';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CatalogComponent,
    ReservationComponent,
    TripDetailComponent,
    ButtonComponent,
    InfoboxComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
