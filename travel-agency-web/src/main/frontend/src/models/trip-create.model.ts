import {PriceCreateModel} from "./price-create.model";

export interface TripCreateModel {
  name: string;
  dateFrom: string;
  dateTo: string;
  destination: string;
  availableTrips: number;
  prices: PriceCreateModel[];
}
