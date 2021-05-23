import { TestBed } from '@angular/core/testing';
import { TripShowService } from './trip-show.service';

describe('TripShowService', () => {
  let service: TripShowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TripShowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
