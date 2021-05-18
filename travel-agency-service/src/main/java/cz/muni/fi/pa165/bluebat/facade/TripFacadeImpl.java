package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.dto.PriceCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.facade.TripFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.PriceService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/
@Service
@Transactional
public class TripFacadeImpl implements TripFacade {



    private TripService tripService;
    private final BeanMappingService beanMappingService;


    @Autowired
    public TripFacadeImpl(TripService tripService,BeanMappingService beanMappingService) {
        this.tripService = tripService;
        this.beanMappingService = beanMappingService;

    }

    @Override
    public void createTrip(TripCreateDTO dto) {
        Validator.NotNull(dto,"TripCreateDTO");
        Trip newTrip = new Trip();
        newTrip.setName(dto.getName());
        newTrip.setDestination(dto.getDestination());
        newTrip.setDateFrom(dto.getDateFrom());
        newTrip.setDateTo(dto.getDateTo());
        newTrip.setAvailableTrips(dto.getAvailableTrips());
        tripService.create(newTrip);

    }


    @Override
    public void updateTrip(TripDTO dto) {
        Validator.NotNull(dto,"TripDTO");
        Trip update = tripService.findById(dto.getId());
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
    public void deleteTrip(Long tripId) {
        Validator.Positive(tripId, "Trip Id");
        tripService.delete(tripService.findById(tripId));
    }

    @Override
    public TripDTO getTripDTO(Long id) {
        Validator.Positive(id, "Trip id");
        Trip found = tripService.findById(id);
        if (found == null) {
            return null;
        }
        TripDTO result = beanMappingService.mapTo(found, TripDTO.class);
        return result;
    }


}
