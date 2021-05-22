import { ExcursionViewModel } from "./excursion-view.model";

export interface TripViewModel {
  id: number;
  name: string;
  dateFrom: Date;
  dateTo: Date;
  destination: string;
  availableTrips: number;
  price: number;
  excursions: ExcursionViewModel[];
}
