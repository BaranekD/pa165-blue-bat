package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Price;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */

@Repository
public class PriceDaoImpl implements PriceDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Price price) {
        em.persist(price);
    }

    @Override
    public void update(Price price) {
        em.merge(price);
    }

    @Override
    public void delete(Price price) {
        em.remove(em.contains(price) ? price : em.merge(price));
    }

    @Override
    public Price findById(Long id) {
        return em.find(Price.class, id);
    }
}
