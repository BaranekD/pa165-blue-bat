package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
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



    @Autowired
    public TripServiceImpl(TripDao tripDao) {
        this.tripDao = tripDao;

    }

    @Override
    public void create(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip can not be null");
        }
        try{tripDao.create(trip);} catch (Exception ex){ throw new DataAccessException("Wrong data access"){}; }



    }

    @Override
    public void update(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip can not be null");
        }
        Trip previous = null;
        try{previous = tripDao.findById(trip.getId());} catch (Exception ex){ throw new DataAccessException("Wrong data access"){}; }

        if (previous == null) {
            throw new IllegalArgumentException("Trip has not been found in database");
        }
        try{tripDao.update(trip);} catch (Exception ex){ throw new DataAccessException("Wrong data access"){}; }

    }

    @Override
    public void delete(Trip trip) {

        try{tripDao.delete(trip);} catch (Exception ex){ throw new DataAccessException("Wrong data access"){}; }

    }

    @Override
    public Trip findById(Long id) {
        try{return tripDao.findById(id);} catch (Exception ex){ throw new DataAccessException("Wrong data access"){}; }

    }

    @Override
    public List<Trip> findAll(){
        try{return tripDao.findAll();} catch (Exception ex){ throw new DataAccessException("Wrong data access"){}; }
    }


    @Override
    public void addPrice(Trip trip, Price price) {

        trip.addPrice(price);
    }

    @Override
    public void removePrice(Trip trip, Price price) {
        trip.removePrice(price);
    }
}
