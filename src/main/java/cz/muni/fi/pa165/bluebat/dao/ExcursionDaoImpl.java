package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Excursion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */

@Repository
public class ExcursionDaoImpl implements ExcursionDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Excursion excursion) {
        em.persist(excursion);
    }

    @Override
    public void update(Excursion excursion) {
        em.merge(excursion);
    }

    @Override
    public void delete(Excursion excursion) {
        em.remove(em.contains(excursion) ? excursion : em.merge(excursion));
    }

    @Override
    public Excursion findById(Long id) {
        return em.find(Excursion.class, id);
    }
}
