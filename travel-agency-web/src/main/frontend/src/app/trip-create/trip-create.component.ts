import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ButtonStateEnum} from "../../models/button-state.enum";
import {TripService} from "../../services/trip.service";
import {TripCreateModel} from "../../models/trip-create.model";
import {Router} from "@angular/router";
import { InfoboxStateEnum } from 'src/models/infobox-state.enum';
import {MinDateValidator} from "../../utils/min-date.directive";

@Component({
  selector: 'app-trip-create',
  templateUrl: './trip-create.component.html',
  styleUrls: ['./trip-create.component.css']
})
export class TripCreateComponent implements OnInit {
  InfoboxStateEnum = InfoboxStateEnum;
  created: boolean = false;
  attempt: boolean = false;
  submitState: ButtonStateEnum = ButtonStateEnum.init;
  error: any;
  today: string;
  todayDate: Date;

  tripForm: FormGroup;

  constructor(
    private router: Router,
    private tripService: TripService) {

    this.todayDate = new Date();
    this.todayDate.setHours(0, 0, 0, 0);
    this.today = (new Date()).toISOString().substring(0, 10);

    this.tripForm = new FormGroup({
      name: new FormControl('', Validators.required),
      dateFrom: new FormControl(null, [Validators.required, MinDateValidator(this.todayDate)]),
      dateTo: new FormControl(null, [Validators.required, MinDateValidator(this.todayDate)]),
      destination: new FormControl('', Validators.required),
      availableTrips: new FormControl(0, Validators.min(0)),
      prices: new FormArray([])
    })
  }

  ngOnInit(): void {
  }

  onClick(): void {
    this.attempt = true;
    this.submitState = ButtonStateEnum.loading;
    this.tripForm.controls.prices.updateValueAndValidity();
    if (this.tripForm.valid && this.tripForm.controls.prices.valid) {
      let resultPrices = [];
      for (let price of this.getPrices().controls) {
        let control = price as FormGroup;
        resultPrices.push({
          amount: control.controls.amount.value,
          validFrom: control.controls.validFrom.value,
        })
      }

      let result: TripCreateModel = {
        name: this.tripForm.controls.name.value,
        dateFrom: this.tripForm.controls.dateFrom.value,
        dateTo: this.tripForm.controls.dateTo.value,
        destination: this.tripForm.controls.destination.value,
        availableTrips: this.tripForm.controls.availableTrips.value,
        prices: resultPrices
      }

      this.tripService.createTrip(result).subscribe(
        () => {
          this.submitState = ButtonStateEnum.success;
          this.router.navigate(['/']);
        },
        error => {
          this.submitState = ButtonStateEnum.error;
          this.error = error;
        }
      );
    }
    else {
      this.submitState = ButtonStateEnum.error;
    }
  }

  addPrice() {
    let control = this.tripForm.controls.prices as FormArray;
    control.controls.push(new FormGroup({
      amount: new FormControl(0, Validators.min(0)),
      validFrom: new FormControl(null, [Validators.required, MinDateValidator(this.todayDate)]),
    }));
    console.log(control.controls);
  }

  removePrice(index: number) {
    let control = this.tripForm.controls.prices as FormArray;
    control.controls.splice(index, 1);
  }

  getPrices(): FormArray {
    return this.tripForm.controls.prices as FormArray;
  }

  isFieldInvalid(fieldName: string): boolean {
    return this.attempt && !(this.tripForm.get(fieldName)?.valid ?? false);
  }

  isPriceFieldInvalid(index: number, fieldName: string): boolean {
    let control = this.tripForm.controls.prices as FormArray;
    return this.attempt && !(control.controls[index].get(fieldName)?.valid ?? false);
  }
}
