package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.PriceService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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



    private TripService tripService;



    private PriceService priceService;

    @Autowired
    public TripFacadeImpl(TripService tripService,PriceService priceService) {
        this.tripService = tripService;
        this.priceService = priceService;

    }

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
        if(update == null){
            throw new IllegalArgumentException() {};
        }
        update.setName(dto.getName());
        update.setDestination(dto.getDestination());
        update.setDateFrom(dto.getDateFrom());
        update.setDateTo(dto.getDateTo());
        update.setAvailableTrips(dto.getAvailableTrips());
        tripService.update(update);

    }


    @Override
    public void addPrice(Long tripId, Long priceId) {
        tripService.addPrice(tripService.findById(tripId),priceService.findById(priceId));
    }

    @Override
    public void removePrice(Long tripId, Long priceId) {
        tripService.removePrice(tripService.findById(tripId),priceService.findById(priceId));
    }


    @Override
    public void deleteTrip(Long tripId) {
        tripService.delete(tripService.findById(tripId));
    }


}
