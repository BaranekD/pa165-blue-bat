package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.*;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import cz.muni.fi.pa165.bluebat.utils.PriceUtils;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/
@Service
@Transactional
public class TripFacadeImpl implements TripFacade {

    private final TripService tripService;
    private final BeanMappingService beanMappingService;


    @Autowired
    public TripFacadeImpl(TripService tripService,BeanMappingService beanMappingService) {
        this.tripService = tripService;
        this.beanMappingService = beanMappingService;

    }

    @Override
    public TripDTO createTrip(TripCreateDTO dto) {
        Validator.NotNull(dto,"TripCreateDTO");
        Trip newTrip = beanMappingService.mapTo(dto,Trip.class);
        tripService.create(newTrip);
        return beanMappingService.mapTo(newTrip,TripDTO.class);

    }

    @Override
    public TripDTO updateTrip(TripDTO dto) {
        Validator.NotNull(dto,"TripDTO");
        Trip update = beanMappingService.mapTo(dto,Trip.class);
        Validator.Found(update,"TripDTO");
        tripService.update(update);
        return beanMappingService.mapTo(update,TripDTO.class);

    }

    @Override
    public void deleteTrip(Long tripId) {
        Validator.Positive(tripId, "Trip Id");
        tripService.delete(tripService.findById(tripId));
    }

    @Override
    public TripDTO getTripDTO(Long id) {
        Validator.Positive(id, "Trip id");
        Trip found = tripService.findById(id);
        Validator.Found(found,"TripDTO");
        return beanMappingService.mapTo(found, TripDTO.class);
    }

    @Override
    public List<TripShowDTO> getAllTrips() {
        List<TripShowDTO> result = new ArrayList<>();
        for (Trip trip:tripService.findAll()) {
            BigDecimal currentPrice;
            List<Price> prices = trip.getPrices();
            if(prices== null || prices.isEmpty()){
                currentPrice = new BigDecimal(0L);
            } else {
                currentPrice = PriceUtils.getCurrentPrice(trip.getPrices());
            }

            TripShowDTO showTrip =  beanMappingService.mapTo(trip, TripShowDTO.class);
            showTrip.setCurrentPrice(currentPrice);
            result.add(showTrip);
        }
        return result;
    }
}
