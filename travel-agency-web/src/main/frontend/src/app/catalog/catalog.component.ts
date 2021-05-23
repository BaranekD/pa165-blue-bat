import { Component, OnInit } from '@angular/core';
import {TripShowService} from "../../services/trip-show.service";





@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {
  stringTrips : string[] = new Array(0);
  trips: { id:number,name:string,dateFrom: Date,dateTo:Date,destination:string,currentPrice:number }[] = new Array(0);
  constructor(public tripShowService: TripShowService) { }


  ngOnInit(): void {

    this.tripShowService.showTrips().subscribe(
      result => {


        for(let trip of result){
          this.trips.push({
            id:trip.id,
            name:trip.name,
            dateFrom:new Date(trip.dateFrom),
            dateTo: new Date(trip.dateTo),
            destination:trip.destination,
            currentPrice:trip.currentPrice,
          });
        }


        console.log(result)
        console.log(result.length)
        console.log(typeof result)
        console.log(this.trips);
      },
      error => {
        console.log(error);
      }
    )


  }

}
