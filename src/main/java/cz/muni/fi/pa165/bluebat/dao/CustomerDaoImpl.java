package cz.muni.fi.pa165.bluebat.dao;/* created by rudolf */

import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;


/**
 * @author rudolf
 */
@Repository
public class CustomerDaoImpl implements CustomerDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void remove(Customer customer) {
        em.remove(customer);

    }

    @Override
    public void update(Customer customer) {
        em.merge(customer);
    }


    @Override
    public Customer findById(Long id) {

        return em.find(Customer.class,id);
    }

    @Override
    public Customer findByPhoneNumber(Long number) {
        return em.createQuery("select e from Customer e where e.phoneNumber = :number", Customer.class).setParameter(Math.toIntExact(number),number).getSingleResult();
    }

    @Override
    public Customer findByName(String name, String surname) {
        return em.createQuery("select e from Customer e where e.name = :name and e.surname = :surname", Customer.class)
                .setParameter(name,name).setParameter(surname,surname).getSingleResult();
    }


}
