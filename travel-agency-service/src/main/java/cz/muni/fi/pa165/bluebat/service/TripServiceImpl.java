package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vaca
 */
@Service
@Transactional
public class TripServiceImpl implements TripService{

    private final TripDao tripDao;

    private PriceService priceService;


    @Autowired
    public TripServiceImpl(TripDao tripDao,PriceService priceService) {
        this.tripDao = tripDao;
        this.priceService = priceService;

    }

    @Override
    public void create(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip can not be null");
        }
        try{tripDao.create(trip);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex);}



    }


    @Override
    public void update(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip can not be null");
        }
        Trip previous = null;
        try{previous = tripDao.findById(trip.getId());} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }

        if (previous == null) {
            throw new IllegalArgumentException("Trip has not been found in database");
        }
        priceService.updatePrices(previous.getPrices(), trip.getPrices());
        try{tripDao.update(trip);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }

    }

    @Override
    public void delete(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip can not be null");
        }

        try{tripDao.delete(trip);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex);}

    }

    @Override
    public Trip findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        try{return tripDao.findById(id);} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }

    }

    @Override
    public List<Trip> findAll(){
        try{return tripDao.findAll();} catch (Exception ex){ throw new WrongDataAccessException("Wrong data access",ex); }
    }
}
