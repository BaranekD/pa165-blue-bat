package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.PriceService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/
@Service
@Transactional
public class TripFacadeImpl implements TripFacade{


    @Inject
    private TripService tripService;

    @Inject
    private ExcursionService excursionService;

    @Inject
    private PriceService priceService;


    @Override
    public void createTrip(TripCreateDTO dto) {
        Trip newTrip = new Trip();
        newTrip.setName(dto.getName());
        newTrip.setDestination(dto.getDestination());
        newTrip.setDateFrom(dto.getDateFrom());
        newTrip.setDateTo(dto.getDateTo());
        newTrip.setAvailableTrips(dto.getAvailableTrips());
        tripService.create(newTrip);

    }


    @Override
    public void updateTrip(Long tripId, TripCreateDTO dto) {
        Trip update = tripService.findById(tripId);
        update.setName(dto.getName());
        update.setDestination(dto.getDestination());
        update.setDateFrom(dto.getDateFrom());
        update.setDateTo(dto.getDateTo());
        update.setAvailableTrips(dto.getAvailableTrips());
        tripService.update(update);

    }

    @Override
    public void addExcursion(Long tripId, Long excursionId) {
        tripService.findById(tripId).addExcursion(excursionService.findById(excursionId));

    }

    @Override
    public void removeExcursion(Long tripId, Long excursionId) {
        tripService.findById(tripId).removeExcursion(excursionService.findById(excursionId));
    }

    @Override
    public void addPrice(Long tripId, Long priceId) {
        tripService.findById(tripId).addPrice(priceService.findById(priceId));
    }

    @Override
    public void removePrice(Long tripId, Long priceId) {
        tripService.findById(tripId).removePrice(priceService.findById(priceId));
    }


    @Override
    public void deleteTrip(Long tripId) {
        tripService.delete(tripService.findById(tripId));
    }


}
