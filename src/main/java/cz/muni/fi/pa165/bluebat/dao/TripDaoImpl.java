package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Trip;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ondrej Vaca
 */

public class TripDaoImpl implements TripDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Trip trip) {
        em.persist(trip);
    }

    @Override
    public void update(Trip trip) {
        em.merge(trip);
    }

    @Override
    public void remove(Trip trip) {
        em.remove(trip);
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class, id);
    }

}
