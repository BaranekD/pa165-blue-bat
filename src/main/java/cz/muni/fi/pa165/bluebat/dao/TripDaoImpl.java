package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ondrej Vaca
 */

@Repository
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
    public void delete(Trip trip) {
        em.remove(em.contains(trip) ? trip : em.merge(trip));
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class, id);
    }

    @Override
    public List<Trip> findAll(){
        return em.createQuery("select t from Trip t",Trip.class).getResultList();
    }
}
