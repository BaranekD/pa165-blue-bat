package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
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
        tripDao.create(trip);
    }

    @Override
    public void update(Trip trip) {
        tripDao.update(trip);
    }

    @Override
    public void delete(Trip trip) {
        tripDao.delete(trip);
    }

    @Override
    public Trip findById(Long id) {
        return tripDao.findById(id);
    }

    @Override
    public List<Trip> findAll(){
        return tripDao.findAll();
    }
}
