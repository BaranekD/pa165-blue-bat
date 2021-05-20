package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vaca
 */
@Service
public class TripServiceImpl implements TripService{

    private final TripDao tripDao;

    private PriceService priceService;

    private ExcursionService excursionService;


    @Autowired
    public TripServiceImpl(TripDao tripDao,PriceService priceService) {
        this.tripDao = tripDao;
        this.priceService = priceService;

    }

    @Override
    public void create(Trip trip) {
        Validator.NotNull(trip,"Trip");
        try{tripDao.create(trip);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex);}
    }

    @Override
    public void update(Trip trip) {
        Validator.NotNull(trip,"Trip");
        Trip previous = null;
        try{previous = tripDao.findById(trip.getId());} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }

        Validator.Found(trip,"Trip");
        priceService.updatePrices(previous.getPrices(), trip.getPrices());
        try{tripDao.update(trip);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }

    }

    @Override
    public void delete(Trip trip) {
        Validator.NotNull(trip,"Trip");
        try{tripDao.delete(trip);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex);}

    }

    @Override
    public Trip findById(Long id) {
        Validator.NotNull(id,"Trip id");
        try{return tripDao.findById(id);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }

    }

    @Override
    public List<Trip> findAll(){
        try{return tripDao.findAll();} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }
    }
}
