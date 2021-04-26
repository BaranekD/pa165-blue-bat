package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@Repository
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void create(Reservation r) {
        em.persist(r);
    }

    @Override
    public void update(Reservation r) {
        em.merge(r);
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class).getResultList();
    }

    @Override
    public void delete(Reservation r) {
        em.remove(em.contains(r) ? r : em.merge(r));
    }
}
